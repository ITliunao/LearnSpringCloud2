package com.learn.ribbonclient.controller;

import com.learn.ribbonclient.hystrix.UserRibbonClientHystrixCommand;
import com.learn.userapi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * 用户 Ribbon Controller
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 0.0.1
 */
@RestController
public class UserRibbonController {

    /**
     * 负载均衡器客户端
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${service-provider.name}")
    private String providerServiceName;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("")
    public String index() throws IOException {

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("Saint");

        // 选择指定的 service Id
        ServiceInstance serviceInstance = loadBalancerClient.choose(providerServiceName);

        return loadBalancerClient.execute(providerServiceName, serviceInstance, instance -> {

            //服务器实例，获取 主机名（IP） 和 端口
            String host = instance.getHost();
            int port = instance.getPort();
            String url = "http://" + host + ":" + port + "/user";
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(url, user, String.class);

        });

    }

    /**
     * 调用 user-service-provider "/user/list" REST 接口，并且直接返回内容
     * 增加 短路功能
     */
    @GetMapping("/user-service-provider/user")
    public Collection<User> getUsersList() {
        return new UserRibbonClientHystrixCommand(providerServiceName, restTemplate).execute();
    }

}
package com.learn.ribbonclient.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserRibbonClientHystrixCommand extends HystrixCommand<Collection> {

    private final String providerServiceName;

    private final RestTemplate restTemplate;

    public UserRibbonClientHystrixCommand(String providerServiceName, RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey(
                "User-Ribbon-Client"),
                100);
        this.providerServiceName = providerServiceName;
        this.restTemplate = restTemplate;
    }

    /**
     * 主逻辑实现
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Collection run() throws Exception {
        return restTemplate.getForObject("http://" + providerServiceName + "/user", Collection.class);
    }

    /**
     * Fallback 实现
     *
     * @return 空集合
     */
    protected Collection getFallback() {
        return Collections.emptyList();
    }

}
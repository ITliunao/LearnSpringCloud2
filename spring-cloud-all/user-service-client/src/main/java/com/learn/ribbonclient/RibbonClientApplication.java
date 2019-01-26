package com.learn.ribbonclient;

import com.learn.userapi.api.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * RibbonClients定义多个Ribbon
 */
@SpringBootApplication
@RibbonClient("user-service-provider") // 指定目标应用名称
@EnableDiscoveryClient
@EnableCircuitBreaker //使用服务短路
@EnableFeignClients(clients = UserService.class) //申明UserService接口作为Feign Client调用
public class RibbonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RibbonClientApplication.class, args);
	}

	/**
	 * @LoadBalanced 激活负载均衡能力
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

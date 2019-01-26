package com.learn.service.provider;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class UserServiceProviderApplication {

	private final ContextRefresher contextRefresher;

	@Autowired
	public UserServiceProviderApplication(ContextRefresher contextRefresher) {
		this.contextRefresher = contextRefresher;
	}

	/**
	 * 1秒钟更新一次，可用于自动刷新配置，不需要手动post /refresh方法
	 * 
	 * @return
	 */
	// @Scheduled(fixedRate = 1000L)
	public void update() {
		Set<String> keys = contextRefresher.refresh();
		if (!keys.isEmpty()) {

		}

	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceProviderApplication.class, args);
	}
}

package com.zhengyuan.liunao.config.client;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ConfigClientApplication {

	private final ContextRefresher contextRefresher;

	@Autowired
	public ConfigClientApplication(ContextRefresher contextRefresher) {
		this.contextRefresher = contextRefresher;
	}

	/**
	 * 1���Ӹ���һ�Σ��������Զ�ˢ�����ã�����Ҫ�ֶ�post /refresh����
	 * 
	 * @return
	 */
	@Scheduled(fixedRate = 1000L)
	public void update() {
		Set<String> keys = contextRefresher.refresh();
		if (!keys.isEmpty()) {

			// log.info("Updated config keys:{}", keys);
			System.out.println("Updated config keys:{}" + keys);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}
}

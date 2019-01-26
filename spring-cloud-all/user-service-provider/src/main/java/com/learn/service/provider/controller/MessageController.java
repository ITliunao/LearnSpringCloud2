package com.learn.service.provider.controller;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageController {
	private static final Random random = new Random();
	@Value("${server.port}")
	public Integer port;

	@GetMapping("/getMessage")
	public String getMessage() {
		return LocalDateTime.now().toString() + ":" + port;
	}

	/**
	 * 执行某个长时间操作
	 * 
	 * @return
	 */
	@HystrixCommand(commandProperties = {
			// 设置超时时间为100ms
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100") }, fallbackMethod = "fallback" // 设置fallback方法
	)
	@GetMapping("/execute")
	public String longTimeOperation() {
		long executeTime = random.nextInt(200);
		try {
			TimeUnit.MILLISECONDS.sleep(executeTime);
		} catch (InterruptedException e) {
		}
		// log.debug("executeTime : {}", executeTime);
		return "success";
	}

	/**
	 * ${@link #longTimeOperation()}的fallback方法
	 * 
	 * @return
	 */
	public String fallback() {
		return "fallback";
	}
}

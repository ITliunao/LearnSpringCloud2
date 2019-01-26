package com.learn.userapi.api;

import com.learn.userapi.domain.User;
import com.learn.userapi.fallback.UserServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * feign支持REST风格
 */
@FeignClient(name = "${user.service.name}",fallback = UserServiceFallback.class)
public interface UserService {

    @PostMapping("/user")
    boolean saveUser(User user);

    @GetMapping("/user")
    List<User> findAll();
}

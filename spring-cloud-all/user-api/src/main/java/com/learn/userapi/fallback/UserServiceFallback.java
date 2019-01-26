package com.learn.userapi.fallback;

import com.learn.userapi.api.UserService;
import com.learn.userapi.domain.User;

import java.util.Collections;
import java.util.List;

/**
 * fallback实现
 */
public class UserServiceFallback implements UserService{

    @Override
    public boolean saveUser(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }
}

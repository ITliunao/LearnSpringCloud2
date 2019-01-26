package com.learn.service.provider.service;

import com.learn.userapi.api.UserService;
import com.learn.userapi.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("userServiceInMemory")
public class UserServiceInMemory implements UserService {
    private Map<String,User> map = new ConcurrentHashMap<>();
    @Override
    public boolean saveUser(User user) {
        return map.put(user.getId(),user) == null;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(map.values());
    }
}

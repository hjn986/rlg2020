package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;

public interface UserService {
    ServerResponse login(String username, String password);

    ServerResponse<User> register(User u);

    ServerResponse<User> check_valid(User u);
}

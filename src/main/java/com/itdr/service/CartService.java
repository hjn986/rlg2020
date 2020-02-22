package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;

public interface CartService {
    ServerResponse list( User user);

    ServerResponse add(Integer productId, Integer count, User user);
}

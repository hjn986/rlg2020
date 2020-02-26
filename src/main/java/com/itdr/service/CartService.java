package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartVO;

import javax.servlet.http.HttpSession;

public interface CartService {
    ServerResponse list( User user);

    ServerResponse add(Integer productId, User user,Integer count,Integer type );

    ServerResponse update(Integer productId, Integer count, Integer type, User user);

    ServerResponse delete(Integer productId,User user);

    ServerResponse deleteAll(User user);

    ServerResponse getCartProductCount(User user);

    ServerResponse checked(Integer productId,Integer type,User user);
}

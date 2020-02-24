package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartVO;

public interface CartService {
    ServerResponse list( User user);

    ServerResponse add(Integer productId, Integer count, User user);

    ServerResponse<CartVO> update(Integer productId, Integer count, Integer id);

    ServerResponse<CartVO> deleteProduct(String productIds, Integer id);

    ServerResponse<CartVO> select(Integer id, Integer productId, Integer check);
}

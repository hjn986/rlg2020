package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;

public interface ProductService {
    //获取商品直接分类
    ServerResponse<Category> baseCategory(Integer pid);
    //获取商品详情
    ServerResponse<ProductVO> detail(Integer productId);
    //商品模糊查询
    ServerResponse<ProductVO> list(String keyWord, Integer pageNum,Integer pageSize,String orderBy);
}

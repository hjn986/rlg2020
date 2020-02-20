package com.itdr.utils;

import com.itdr.pojo.Product;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.pojo.vo.UserVO;

public class ObjectToVOUtil {
    /**
     * 用户类封装
     * @param u
     * @return
     */
    public static UserVO UserToUserVO(User u){
        UserVO uv = new UserVO();
        uv.setId(u.getId());
        uv.setUsername(u.getUsername());
        uv.setEmail(u.getEmail());
        uv.setPhone(u.getPhone());
        uv.setCreateTime(u.getCreateTime());
        uv.setUpdateTime(u.getUpdateTime());
        return uv;
    }

    /**
     * 商品类封装
     * @param p
     * @return
     */
    public static ProductVO ProductToUserVO(Product p){
        ProductVO pv = new ProductVO();
        pv.setImageHost(PropertiesUtil.getValue("ImageHost"));
        pv.setNew(p.getNew());
        pv.setHot(p.getHot());
        pv.setBanner(p.getBanner());
        pv.setId(p.getId());
        pv.setCategoryId(p.getCategoryId());
        pv.setName(p.getName());
        pv.setSubtitle(p.getSubtitle());
        pv.setMainImage(p.getMainImage());
        pv.setSubImage(p.getSubImage());
        pv.setDetail(p.getDetail());
        pv.setPrice(p.getPrice());
        pv.setStock(p.getStock());
        pv.setStatus(p.getStatus());
        pv.setCreateTime(p.getCreateTime());
        pv.setUpdateTime(p.getUpdateTime());
        return pv;
    }
}

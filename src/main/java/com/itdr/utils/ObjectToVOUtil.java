package com.itdr.utils;

import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.pojo.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;

public class ObjectToVOUtil {
    /**
     * 用户类封装
     * @param u
     * @return
     */
    public static UserVO userToUserVO(User u){
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
    public static ProductVO productToUserVO(Product p){
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

    /**
     * 购物车和商品数据封装
     * @param c
     * @param p
     * @return
     */
    public static CartProductVO cartAndProductToVO(Cart c,Product p){
       CartProductVO cvo = new CartProductVO();
       cvo.setId(c.getId());
       cvo.setUserId(c.getUserId());
       cvo.setProductId(c.getProductId());
       cvo.setQuantity(c.getQuantity());
       cvo.setProductName(p.getName());
       cvo.setProductSubtitle(p.getSubtitle());
       cvo.setProductMainImage(p.getMainImage());
       cvo.setProductPrice(p.getPrice());
       cvo.setProductStock(p.getStock());
       cvo.setProductStatus(p.getStatus());

       //一条购物信息总价
        BigDecimal tp = BigDecimalUtil.mul(c.getQuantity(),p.getPrice().doubleValue());
        cvo.setProductTotalPrice(tp);

        //商品是否选中
        cvo.setProductChecked(c.getChecked());

        //商品是否超过库存
        String limitQuantity = "LIMIT_NUM_SUCCESS";
        if (c.getQuantity() > p.getStock()){
             limitQuantity = "LIMIT_NUM_FAILURE";
        }
        cvo.setLimitQuantity(limitQuantity);
       return cvo;
    }

    public static CartVO toCartVO(List<CartProductVO> li,boolean bol,BigDecimal cartTotalPrice){
        CartVO cvo = new CartVO();
        //购物车中商品数据
        cvo.setCartProductVOList(li);
        //购物车中商品是否全选
        cvo.setAllChecked(bol);
        //购物车总价
        cvo.setCartTotalPrice(cartTotalPrice);
        return cvo;
    }


}

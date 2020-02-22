package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.CartMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.service.CartService;
import com.itdr.utils.BigDecimalUtil;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;
    //获取cartVO对象
    private CartVO getCartVO(List<Cart> cartList) {
        //获取购物车中对应的商品信息
        List<CartProductVO> cartProductVOList = new ArrayList<CartProductVO>();
        boolean bol = true;
        BigDecimal cartTotalPrice = new BigDecimal(0);
        for (Cart cart : cartList) {
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());

            //把商品和购物车信息进行数据封装
            if (product != null) {
                CartProductVO cartProductVO = ObjectToVOUtil.cartAndProductToVO(cart, product);
                cartProductVOList.add(cartProductVO);

                //计算购物车总价,只计算选中的商品
                if (cartProductVO.getProductChecked() == 1) {
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVO.getProductTotalPrice().doubleValue());
                }
            }
            //判断购物车是否全选
            if (cart.getChecked() == 0) {
                bol = false;
            }
        }

        //返回数据
        CartVO cartVO = ObjectToVOUtil.toCartVO(cartProductVOList, bol, cartTotalPrice);
        return cartVO;
    }
    //获取用户购物车列表
    private ServerResponse<List<Cart>> getCarList(User user){
        //查询登录用户的购物车信息
        List<Cart> cartList = cartMapper.selectByUserID(user.getId());
        //用户购物车中是否有数据
        if (cartList.size() == 0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc());
        }
        return ServerResponse.successRS(cartList);
    }

    @Override
    public ServerResponse list( User user) {
        //查询登录用户的购物车信息
        ServerResponse<List<Cart>> carList = getCarList(user);
        if (!carList.isSuccess()){
            return carList;
        }
        CartVO cartVO = getCartVO(carList.getData());
        return ServerResponse.successRS(cartVO);
    }

    @Override
    public ServerResponse add(Integer productId, Integer count, User user) {
        //参数合法判断
        if (productId == null || productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULNESS_PARAM);
        }
        //添加的商品是否在售
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus() != 1){
            return ServerResponse.defeatedRS(
                ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getCode(),
                ConstCode.ProductEnum.INEXISTENCE_PRODUCT.getDesc());
    }
        //添加的商品数量是否超过库存
        if (count >product.getStock()){
            return ServerResponse.defeatedRS(
                    ConstCode.ProductEnum.BEYOND_STOCK.getCode(),
                    ConstCode.ProductEnum.BEYOND_STOCK.getDesc());
        }
        //向购物车中增加或更新一条数据
        Cart c = new Cart();
        c.setUserId(user.getId());
        c.setProductId(productId);
        c.setQuantity(count);

        Cart cart = cartMapper.selectByUserIDAndProductID(user.getId(),productId);
        if (cart == null){
            int insert = cartMapper.insert(c);
            if (insert <= 0){
                return ServerResponse.defeatedRS(
                        ConstCode.CartEnum.FAIL_ADDPRODUCT.getCode(),
                        ConstCode.CartEnum.FAIL_ADDPRODUCT.getDesc());
            }else{
                cart.setQuantity(count);
                int i = cartMapper.updateByPrimaryKey(cart);
                if (i <=0){
                    return ServerResponse.defeatedRS(
                            ConstCode.CartEnum.FAIL_ADDPRODUCT.getCode(),
                            ConstCode.CartEnum.FAIL_ADDPRODUCT.getDesc());
                }
            }
        }

        //返回封装好的CartVO数据
        //查询登录用户的购物车信息
        ServerResponse<List<Cart>> carList = getCarList(user);
        if (!carList.isSuccess()){
            return carList;
        }
        CartVO cartVO = getCartVO(carList.getData());
        return ServerResponse.successRS(cartVO);
    }
}

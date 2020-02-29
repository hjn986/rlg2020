package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.CartMapper;
import com.itdr.mapper.OrderItemMapper;
import com.itdr.mapper.OrderMapper;
import com.itdr.mapper.ProductMapper;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.CartVO;
import com.itdr.service.CartService;
import com.itdr.service.OrderService;
import com.itdr.utils.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    //订单编号生成规则
    private Long getNo(){
        long round = Math.round(Math.random() * 100);
        long l = System.currentTimeMillis() + round;
        return l;
    }

    @Override
    public ServerResponse create(User user, Integer shippingId) {
        //参数判断
        if (shippingId == null || shippingId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULNESS_PARAM);
        }
        //判断当前用户购物车中有没有被选中的数据
        ServerResponse settlement = cartService.settlement(user);
        if (!settlement.isSuccess()){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getCode(),
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getDesc());
        }
        //根据用户名获取购物车信息
        List<Cart> cartList = cartMapper.selectByUserID(user.getId());
        CartVO cartVO = ((CartServiceImpl) cartService).getCartVO(cartList);
        //创建一个订单
        Long no = getNo();
        Order o = new Order();
        o.setUserId(user.getId());
        o.setOrderNo(no);
        o.setShippingId(shippingId);
        o.setPayment(cartVO.getCartTotalPrice());
        o.setPaymentType(1);
        o.setPostage(0);
        o.setStatus(10);
        //把创建的订单对象存储到数据库中
        int i = orderMapper.insert(o);
        if (i <= 0){
            return ServerResponse.defeatedRS("订单创建失败");
        }
        //创建订单详情
        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(user.getId());
            orderItem.setOrderNo(o.getOrderNo());
            if (cart.getChecked() == 1){
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product.getStatus() == 1 && cart.getQuantity() <= product.getStock()){
                    orderItem.setProductId(cart.getProductId());
                    orderItem.setProductName(product.getName());
                    orderItem.setProductImage(product.getMainImage());
                    orderItem.setCurrentUnitPrice(product.getPrice());
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cart.getQuantity().doubleValue()));
                }
            }
            //把创建的订单详情对象存储到数据库中
            int i2 = orderItemMapper.insert(orderItem);
            if (i2 <= 0){
                return ServerResponse.defeatedRS("订单详情创建失败");
            }
        }
        //清空购物车数据
        int i3 = cartMapper.deleteByUserId(user.getId());
        //返回成功数据
        return null;
    }



}

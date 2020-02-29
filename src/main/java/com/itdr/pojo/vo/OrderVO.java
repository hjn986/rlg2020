package com.itdr.pojo.vo;

import com.itdr.pojo.OrderItem;
import com.itdr.pojo.Shipping;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderVO {
    private Long orderNo;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private List<OrderItemVO> orderItemVOList;

    private ShippingVO shippingVO;
    //图片服务器地址
    private String imageHost;


}

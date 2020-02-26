package com.itdr.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.itdr.common.ServerResponse;
import com.itdr.config.pay.Configs;
import com.itdr.mapper.*;
import com.itdr.pojo.Order;
import com.itdr.pojo.OrderItem;
import com.itdr.pojo.User;
import com.itdr.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliPayServiceImpl implements AliPayService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private PayInfoMapper payInFoMapper;
    private AlipayTradePrecreateResponse test_trade_precreate(Order order, List<OrderItem> orderItems) throws AlipayApiException {
        //读取配置文件信息
        Configs.init("zfbinfo.properties");

        //实例化支付宝客户端
        AlipayClient alipayClient = new DefaultAlipayClient(Configs.getOpenApiDomain(),
                Configs.getAppid(), Configs.getPrivateKey(), "json", "utf-8",
                Configs.getAlipayPublicKey(), Configs.getSignType());

        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //获取一个BizContent对象,并转换成json格式
        String str = JsonUtils.obj2String(POJOtoVOUtils.getBizContent(order, orderItems));
        request.setBizContent(str);
        //设置支付宝回调路径
        request.setNotifyUrl(Configs.getNotifyUrl_test());
        //获取响应,这里要处理一下异常
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        //返回响应的结果
        return response;
    }

    @Override
    public ServerResponse pay(User user, Long orderNo) {
        //参数非空判断
        //判断订单是否存在
        Order order = orderMapper.selectByOrderNo(orderno);
        //判断订单和用户是否匹配
        //根据订单号查询对应商品详情
        List<OrderItem> orderItems = orderItemMapper.selectByOrderNo(order.getOrderNo());
        //调用支付宝接口获取支付二维码
        //使用封装方法获得预下单成功后返回的二维码信息串
        //将二维码信息串生成图片，并保存，（需要修改为运行机器上的路径）
        //预下单成功获取返回信息（二维码字符串）
        //返回生成二维码后的图片地址和订单号信息给前端

        //后期图片会放到图片服务器上

        try {
            //使用封装方法获得预下单成功后返回的二维码信息串
            AlipayTradePrecreateResponse response = test_trade_precreate(order, orderItems);

            //成功执行下一步
            if (response.isSuccess()) {
                // 将二维码信息串生成图片，并保存，（需要修改为运行机器上的路径）
                String filePath = String.format(Configs.getSavecode_test()+"qr-%s.png",
                        response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);



                //预下单成功返回信息
                Map map = new HashMap();
                map.put("orderNo", order.getOrderNo());

                map.put("qrCode", filePath);
                return ServerResponse.successRS(map);

            } else {
                //预下单失败
                return ServerResponse.defeatedRS(Const.PaymentPlatformEnum.ALIPAY_FALSE.getCode(), Const.PaymentPlatformEnum.ALIPAY_FALSE.getDesc());

            }
        } catch (AlipayApiException e) {
            //出现异常，预下单失败
            e.printStackTrace();
            return ServerResponse.defeatedRS(Const.PaymentPlatformEnum.ALIPAY_FALSE.getCode(), Const.PaymentPlatformEnum.ALIPAY_FALSE.getDesc());
        }
    }
    }
}

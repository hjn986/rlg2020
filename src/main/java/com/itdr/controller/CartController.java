package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.User;
import com.itdr.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/cart/")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 查看购物车列表
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.list(user);
    }

    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "1") Integer count,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.add(productId,count,user);
    }
}

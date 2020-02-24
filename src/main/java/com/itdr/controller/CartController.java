package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.User;
import com.itdr.pojo.vo.CartVO;
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

    /**
     * 购物车添加商品
     * @param productId
     * @param count
     * @param session
     * @return
     */
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

    /**
     * 更新购物车某个产品数量
     * @param productId
     * @param count
     * @param session
     * @return
     */
    @RequestMapping("update.do")
    public ServerResponse<CartVO> update(Integer productId, Integer count,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.update(productId,count,user.getId());
    }
    //删除购物车中某个商品
    @RequestMapping("delete_product.do")
    public ServerResponse<CartVO> deleteProduct(String productIds,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.deleteProduct(productIds,user.getId());
    }

    @RequestMapping("select.do")
    public ServerResponse<CartVO> select(HttpSession session,Integer productId,Integer check){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.select(user.getId(),productId,check);
    }

}

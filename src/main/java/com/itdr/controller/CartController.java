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
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "1") Integer count,
                              @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.add(productId,user,count,type);
    }

    /**
     * 更新购物车某个产品数量
     * @param productId
     * @param count
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("update.do")
    public ServerResponse update(Integer productId,
                              @RequestParam(value = "count",required = false,defaultValue = "1") Integer count,
                              @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.update(productId,count,type,user);
    }

    /**
     * 删除购物车中某个商品
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping("delete.do")
    public ServerResponse delete(Integer productId,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.delete(productId,user);
    }

    /**
     * 删除购物车中全部选中数据
     * @param session
     * @return
     */
    @RequestMapping("deleteall.do")
    public ServerResponse deleteAll(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.deleteAll(user);
    }

    /**
     * 查询在购物车里的产品数量
     * @param session
     * @return
     */
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse getCartProductCount(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.getCartProductCount(user);
    }

    @RequestMapping("checked.do")
    public ServerResponse checked(Integer productId,
                                  @RequestParam(value = "type",required = false,defaultValue = "0")Integer type,
                                  HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.NO_LOGIN.getDesc());
        }
        return cartService.checked(productId,type,user);
    }



}

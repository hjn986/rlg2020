package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/user/")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session){
       ServerResponse sr = userService.login(username,password);
       //登录成功在session中保存用户数据
        if (sr.isSuccess()){
            session.setAttribute("user",sr.getData());
        }
       return sr;
    }

    /**
     * 用户注册
     * @param u
     * @return
     */
    @RequestMapping("register.do")
    public ServerResponse<User> register(User u) {
        return userService.register(u);
    }

    /**
     * 检查用户名是否有效
     * @param u
     * @return
     */
    @RequestMapping("check_valid.do")
    public ServerResponse<User> check_valid(User u) {
       return userService.register(u);

    }

    /**
     *获取登录用户信息
     * @return
     */
    @RequestMapping("get_user_info.do")
    public ServerResponse<User> get_user_info() {
        return null;

    }


}

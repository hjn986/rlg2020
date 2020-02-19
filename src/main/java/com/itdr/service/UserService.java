package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.User;

public interface UserService {
    //用户登录
    ServerResponse login(String username, String password);
    //用户注册
    ServerResponse<User> register(User u);
    //检查用户名是否有效
    ServerResponse<User> checkValid(String str,String type);
    //登录状态更新个人信息
    ServerResponse<User> updateInformation(String email, String phone, String question, String answer,User u);//
    //忘记密码
    ServerResponse<User> forgetGetQuestion(String username);
    //提交问题答案
    ServerResponse<User> forgetCheckAnswer(String username, String question, String answer);
    //忘记密码，重设密码
    ServerResponse<User> forgetResetPassword(String username, String passwordNew, String forgetToken);
    //登录状态中重置密码
    ServerResponse<User> resetPassword(User u,String passwordOld, String passwordNew);
}

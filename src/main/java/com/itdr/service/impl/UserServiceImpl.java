package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.UserMapper;
import com.itdr.pojo.User;
import com.itdr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

/*    private ServerResponse isEmpty(String parm){
        ServerResponse sr = null;
        if (StringUtils.isEmpty(parm)){
            sr = ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        return sr;
    }*/


    //用户登录
    @Override
    public ServerResponse login(String username, String password) {
        //参数非空判断
        if (StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if (StringUtils.isEmpty(password)){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        //查询用户
        User u = userMapper.selectByUserNameAndPassword(username,password);
        if (u == null){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.FAIL_LOGIN.getCode(),
                    ConstCode.UserEnum.FAIL_LOGIN.getDesc());
        }
        //返回成功数据
        return ServerResponse.successRS(u);
    }


    //用户注册
    @Override
    public ServerResponse<User> register(User u) {
        //参数非空判断
        if (StringUtils.isEmpty(u.getUsername())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.UserEnum.EMPTY_USERNAME.getDesc());
        }
        if (StringUtils.isEmpty(u.getPassword())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.UserEnum.EMPTY_PASSWORD.getDesc());
        }
        if (StringUtils.isEmpty(u.getAnswer())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_ANSWER.getCode(),
                    ConstCode.UserEnum.EMPTY_ANSWER.getDesc());
        }
        if (StringUtils.isEmpty(u.getQuestion())){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EMPTY_QUESTION.getCode(),
                    ConstCode.UserEnum.EMPTY_QUESTION.getDesc());
        }
        //根据用户名查找用户是否存在
        int i = userMapper.selectByUserName(u.getUsername());
        if (i>0){
            return ServerResponse.defeatedRS(
                    ConstCode.UserEnum.EXIST_USER.getCode(),
                    ConstCode.UserEnum.EXIST_USER.getDesc());
        }
        //注册用户信息
        int insert = userMapper.insert(u);
        if (insert<=0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.UserEnum.FAIL_USER.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.UserEnum.SUCCESS_USER.getDesc());
    }
    //检查用户名是否有效
    @Override
    public ServerResponse<User> check_valid(User u) {

        if (StringUtils.isEmpty(u.getUsername())){
            return ServerResponse.successRS(
                    ConstCode.UserEnum.SUCCESS_USER.getDesc());
        }
        return ServerResponse.defeatedRS(
                ConstCode.UserEnum.EXIST_USER.getCode(),
                ConstCode.UserEnum.EXIST_USER.getDesc());

     /*   if (StringUtils.isEmpty(u.getEmail())){
            return ServerResponse.successRS(
                    ConstCode.UserEnum.SUCCESS_USER.getDesc());
        }
        return ServerResponse.defeatedRS(
                ConstCode.UserEnum.EXIST_EMAIL.getCode(),
                ConstCode.UserEnum.EXIST_EMAIL.getDesc());*/


    }
}

package com.yutu.controller;

import com.alibaba.fastjson.JSONObject;
import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.service.ILoginService;
import com.yutu.util.RedisUtils;
import com.yutu.util.RestClientUtils;
import com.yutu.util.SessionUserManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName:LoginController
 * @Author:zhaobc
 * @Date:2019/4/16 11:51
 * @Description:登录操作控制层
 **/
@RestController
@RequestMapping(value = "login")
public class LoginController {
    Logger logger = Logger.getLogger(LoginController.class);

    @Resource
    private ILoginService loginService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SessionUserManager sessionUserManager;

    /**
     * @Author: zhaobc
     * @Date: 2019/4/20 21:55
     * @Description: 用户登录验证
     **/
    @RequestMapping(value = "getLoginVerification")
    public MsgPack getLoginVerification(HttpServletRequest request) {
        String userAccount = request.getParameter("userAccount");
        String userPwd = request.getParameter("userPwd");
        MsgPack msgPak = loginService.getLoginVerification(userAccount, userPwd);
        return msgPak;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019-12-19 9:46
     * @Description: 注销登录
     **/
    @RequestMapping(value = "logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        //注销session
        sessionUserManager.logoutSessionUser(session);
        //刷新页面
        response.sendRedirect("../login");
    }
}

package com.yutu.controller;

import com.yutu.configuration.SystemPropertiesConfig;
import com.yutu.entity.MsgPack;
import com.yutu.service.ILoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName:RedirectController
 * @Author:zhaobc
 * @Date:2019/4/16 11:48
 * @Description:全局通用跳转页
 **/
@Controller
public class RedirectController {
    @Resource
    private ILoginService loginService;

    /**
     * @Author:zhaobc
     * @Date:2019/4/16 11:50
     * @Description:登录页
     **/
    @RequestMapping("/")
    public String login() {
        return "login";
    }


    /**
     * @Author: zhaobc
     * @Date: 2019-12-19 9:05
     * @Description: 单点登录地址
     **/
    @RequestMapping(value = "/loginSSO")
    public void loginSSO(HttpServletRequest request, HttpServletResponse response) throws IOException {
        switch (SystemPropertiesConfig.System_LoginStorage_Type) {
            case "session":
                String token = request.getParameter("token");
                MsgPack msgPack = loginService.getLoginSSOVerification(token);
                //判断是否登录成功
                if(msgPack.getStatus()==1){
                    response.sendRedirect("/index");
                }else {
                    response.sendRedirect("/login");
                }
                break;
            case "redis":
                response.sendRedirect("/index");
                break;
        }
    }

    /**
     * @Author:zhaobc
     * @Date:2019/4/16 11:50
     * @Description:系统首页
     **/
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}

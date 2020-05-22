package com.yutu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.entity.api.ApiUser;
import com.yutu.mapper.mysql.TSysUserMapper;
import com.yutu.service.ILoginService;
import com.yutu.utils.RestClientUtils;
import com.yutu.utils.SessionUserManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName:LoginServiceImpl
 * @Author:zhaobc
 * @Date:2019/4/16 11:56
 * @Description:登录服务实现层
 **/
@Service
public class LoginServiceImpl implements ILoginService {
    Logger logger = Logger.getLogger(ILoginService.class);

    @Resource
    private TSysUserMapper sysUserMapper;
    @Resource
    private SessionUserManager sessionUserManager;
    @Resource
    private HttpServletRequest request;


    @Override
    public MsgPack getLoginVerification(String userAccount, String userPwd) {
        MsgPack msgPack = new MsgPack();
        //给密码md5加密
        userPwd = DigestUtils.md5Hex(userPwd + "yutu&zhaobc@2019");
        Map<String, String> userInfo = sysUserMapper.getLoginVerification(userAccount, userPwd);
        //获得客户端身份信息便于验证
        String address = request.getServletPath();
        String ip = request.getRemoteAddr();
        // sesson中存储客户端标识字段，需要进行验证是否是登陆着
        String security = ip + "<yutu_frame>" + request.getHeader("User-Agent");

        //日志实体类 并赋值
        HttpSession session = request.getSession();
        //判断登录是否成功
        if (userInfo != null) {
            //session存储用户信息操作
            SessionUser sessionUser = new SessionUser();
            sessionUser.setSessionId(session.getId());
            sessionUser.setUuid(userInfo.get("uuid"));
            sessionUser.setUserAccount(userInfo.get("user_account"));
            sessionUser.setUserName(userInfo.get("user_name"));
            sessionUser.setUserSafety(security);
            sessionUser.setRoleId(userInfo.get("role_uuid"));
            sessionUser.setOrgId(userInfo.get("org_uuid"));
            //判断session 存储sesion对象
            msgPack = sessionUserManager.setSessionUser(sessionUser);

        }
        System.out.print("=============================>" + msgPack.getMsg() + "系统登录-------------------------------\r\n");

        return msgPack;
    }

    @Override
    public MsgPack getLoginSSOVerification(String token) {
        MsgPack msgPack = new MsgPack();
        //获得appkey
        String appKey = ConfigConstants.Auth_AppKey;
        String authUrl = ConfigConstants.Auth_Service;
        //获得客户端身份信息便于验证
        String address = request.getServletPath();
        String ip = request.getRemoteAddr();
        // sesson中存储客户端标识字段，需要进行验证是否是登陆着
        String security = ip + "<yutu_frame>" + request.getHeader("User-Agent");

        //报头信息
        Map map = new HashMap();
        map.put("token", token);//链接参数
        map.put("appkey", appKey);//应用key，由系统管理员发放
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JacksonJsonProvider.class);
        JSONObject jsonObject = new JSONObject();
        String loginStatus = "";
        try {
            //判断登录接口
            msgPack = RestClientUtils.put(authUrl, "/webapi/auth/sso", map,MsgPack.class);
            if (msgPack.getStatus() < 1) {
                return msgPack;
            }
            ApiUser apiUser = JSONObject.parseObject(msgPack.getData().toString(), ApiUser.class);
            HttpSession session = request.getSession();
            SessionUser sessionUser = new SessionUser();
            sessionUser.setSessionId(session.getId());
            sessionUser.setUuid(apiUser.getUuid());
            sessionUser.setUserAccount(apiUser.getUserAccount());
            sessionUser.setUserName(apiUser.getUserName());
            sessionUser.setUserSafety(security);
            sessionUser.setToken(token);
            //判断session 存储sesion对象
            msgPack = sessionUserManager.setSessionUser(sessionUser);

            //接口记录日志
            Map<String, Object> mapLanding = new HashMap();
            mapLanding.put("token", token);//链接参数
            mapLanding.put("appkey", appKey);
            mapLanding.put("uuid", UUID.randomUUID().toString());
            mapLanding.put("loginUserid", sessionUser.getUuid());
            mapLanding.put("loginAccount", sessionUser.getUserAccount());
            mapLanding.put("loginIp", ip);
            mapLanding.put("loginSessionid", session.getId());
            mapLanding.put("loginDate", new Date());
            mapLanding.put("loginType", "登陆");
            mapLanding.put("loginResult", 1);
            mapLanding.put("loginAppname", "子系统一");
            mapLanding.put("loginAddress", address);
            mapLanding.put("remarks", null);

            MsgPack msgPackLog = RestClientUtils.post(authUrl, "/webapi/log/landing/add",mapLanding, MsgPack.class);
            if (msgPackLog.getStatus() == 1) {
                logger.info("==============>门户登录日志记录成功！---------------------");
            } else {
                logger.info("==============>门户登录日志记录失败！---------------------");
            }

        } catch (Exception e) {
            msgPack.setMsg(e.toString());
        }
        return msgPack;
    }
}

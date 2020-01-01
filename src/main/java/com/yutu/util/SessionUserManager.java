package com.yutu.util;

import com.alibaba.fastjson.JSONObject;
import com.yutu.configuration.SystemPropertiesConfig;
import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.filter.MyFilter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zhaobc
 * @Date: 2019-12-19 11:13
 * @Description:session获取管理类
 */
@Component
public class SessionUserManager {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    HttpServletRequest request;

    Logger logger = Logger.getLogger(SessionUserManager.class);

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 10:08
     * @Description: 验证session是否有效
     **/
    public MsgPack verificationSessionUser() {
        MsgPack msgPack = new MsgPack();
        SessionUser sessionUser = getSessionUser();
        if (sessionUser != null) {
            //增加延迟时间
            msgPack = expireSessionUser(sessionUser);
        }
        return msgPack;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 9:42
     * @Description: 获得session信息
     **/
    public SessionUser getSessionUser() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //判断用户登陆存储方式
            switch (SystemPropertiesConfig.System_LoginStorage_Type) {
                case "session":
                    if (session.getId() != null) {
                        //Session版获取数据
                        SessionUser sessionUs = (SessionUser) session.getAttribute(session.getId());
                        return sessionUs;
                    }
                    break;
                case "redis":
                    //判断是单点登录还是普通登录
                    if (SystemPropertiesConfig.System_Login_Type.equals("SSO")) {
                        String token = request.getSession().getAttribute(SystemPropertiesConfig.System_Auth_Token).toString();
                        SessionUser sessionUser = (SessionUser) redisUtils.get(token);
                        return sessionUser;
                    } else {
                        if (session.getId() != null) {
                            SessionUser sessionUser = (SessionUser) redisUtils.get(session.getId());
                            return sessionUser;
                        }
                    }
                    break;
                default:
                    if (session.getId() != null) {
                        //Session版获取数据
                        SessionUser sessionUser = (SessionUser) session.getAttribute(session.getId());
                        return sessionUser;
                    }
                    break;
            }
        }
        return null;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 9:42
     * @Description: 存储Session信息
     **/
    public MsgPack setSessionUser(SessionUser sessionUser) {
        MsgPack msgPack = new MsgPack();
        //判断session
        HttpSession session = request.getSession(false);
        //判断是ses否为空
        if (session != null && sessionUser != null && sessionUser.getSessionId().length() > 0) {
            if (session.isNew()) {
                switch (SystemPropertiesConfig.System_LoginStorage_Type) {
                    case "session":
                        //存储到session中去 并设置超时时间
                        request.getSession().setAttribute(sessionUser.getSessionId(), sessionUser);
                        request.getSession().setMaxInactiveInterval(Integer.parseInt(SystemPropertiesConfig.System_Token_TimeOut));
                        msgPack.setStatus(1);
                        break;
                    case "redis":
                        //存储到redis
                        redisUtils.set(session.getId(), sessionUser, Long.parseLong(SystemPropertiesConfig.System_Token_TimeOut));
                        msgPack.setStatus(1);
                        break;
                    default:
                        //存储到session中去
                        request.getSession().setAttribute(sessionUser.getSessionId(), sessionUser);
                        request.getSession().setMaxInactiveInterval(Integer.parseInt(SystemPropertiesConfig.System_Token_TimeOut));
                        msgPack.setStatus(1);
                        break;
                }
            } else {
                //如果session存储过，直接通过
                msgPack.setStatus(1);
            }
        }
        return msgPack;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 9:56
     * @Description: 登录成功为存储的session/Redis延长时间
     **/
    public MsgPack expireSessionUser(SessionUser sessionUser) {
        MsgPack msgPack = new MsgPack();
        if (sessionUser != null) {
            switch (SystemPropertiesConfig.System_LoginStorage_Type) {
                case "session":
                    //存储到session中去
                    request.getSession().setAttribute(sessionUser.getSessionId(), sessionUser);
                    request.getSession().setMaxInactiveInterval(Integer.parseInt(SystemPropertiesConfig.System_Token_TimeOut));
                    msgPack.setStatus(1);
                    msgPack.setData(sessionUser);
                    break;
                case "redis":
                    //存储到redis
                    redisUtils.expire(sessionUser.getSessionId(), Long.parseLong(SystemPropertiesConfig.System_Token_TimeOut));
                    msgPack.setStatus(1);
                    msgPack.setData(sessionUser);
                    break;
            }
        }
        return msgPack;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 9:42
     * @Description: 存储Session信息
     **/
    public void logoutSessionUser(HttpSession session) {
        SessionUser sessionUser = new SessionUser();
        //获得sessionId
        String sessionId = session.getId();
        //获得参数插入日志
        Map<String, Object> mapLanding = new HashMap<>();
        if (request.getSession(false) != null) {
            if (sessionId != null) {
                sessionUser = getSessionUser();
                mapLanding.put("token", sessionUser.getToken());//链接参数
                mapLanding.put("appkey", ConfigConstants.Auth_AppKey);
                mapLanding.put("uuid", UUID.randomUUID().toString());
                mapLanding.put("loginUserid", sessionUser.getUuid());
                mapLanding.put("loginAccount", sessionUser.getUserAccount());
                mapLanding.put("loginIp", request.getRemoteAddr());
                mapLanding.put("loginSessionid", session.getId());
                mapLanding.put("loginDate", new Date());
                mapLanding.put("loginType", "注销");
                mapLanding.put("loginAppname", "子系统一");
                mapLanding.put("loginAddress", request.getServletPath());
            }
        }
        try {
            //清空本地session
            session.invalidate();
            if (SystemPropertiesConfig.System_LoginStorage_Type.equals("redis")) {
                //清空redis里数据
                redisUtils.del(sessionUser.getSessionId());
            }
        } catch (Exception e) {
            mapLanding.put("loginResult", 1);
            mapLanding.put("remarks", e.toString());
        } finally {
            mapLanding.put("loginResult", 1);
            //插入日志
            JSONObject jsonLog = new JSONObject();
            //退出日志
            MsgPack msgPack = RestClientUtils.post(ConfigConstants.Auth_Service, "/log/landing/add", mapLanding, MsgPack.class);
            if (msgPack.getStatus() == 1) {
                logger.info("==============>门户登录日志记录成功！---------------------");
            } else {
                logger.info("==============>门户登录日志记录失败！---------------------");
            }
        }
    }
}

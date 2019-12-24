package com.yutu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.entity.api.ApiUser;
import com.yutu.entity.table.TLogLanding;
import com.yutu.mapper.mysql.TLogLandingMapper;
import com.yutu.mapper.mysql.TSysUserMapper;
import com.yutu.service.ILoginService;
import com.yutu.util.PortalIntegratedManager;
import com.yutu.util.SessionUserManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private TLogLandingMapper logLandingMapper;
    @Resource
    private SessionUserManager sessionUserManager;
    @Resource
    private HttpServletRequest request;


    @Override
    public MsgPack getLoginVerification(String userAccount, String userPwd) {
        MsgPack msgPack = new MsgPack();
        //给密码md5加密
        userPwd = DigestUtils.md5Hex(userPwd + "yutu&zhaobc@2019");
        Map<String,String> userInfo = sysUserMapper.getLoginVerification(userAccount, userPwd);

        //获得客户端身份信息便于验证
        String address = request.getServletPath();
        String ip = request.getRemoteAddr();
        // sesson中存储客户端标识字段，需要进行验证是否是登陆着
        String security = ip + "<yutu_frame>" + request.getHeader("User-Agent");

        //日志实体类 并赋值
        TLogLanding landing = new TLogLanding();
        landing.setUuid(UUID.randomUUID().toString());
        landing.setLoginAddress(address);
        landing.setLoginAccount(userAccount);
        landing.setLoginDate(new Date());
        landing.setLoginIp(ip);
        landing.setLoginType("子系统一登陆");
        landing.setLoginAppname("子系统一");
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
            msgPack= sessionUserManager.setSessionUser(sessionUser);
            //记录日志
            landing.setLoginUserid(userInfo.get("uuid"));
            landing.setLoginSessionid(session.getId());
            landing.setLoginResult(msgPack.getStatus());
        }

        //数据库存储登录日志
        int landingCount = logLandingMapper.insert(landing);
        System.out.print("=============================>"+msgPack.getMsg()+"门户登陆日志插入" + landingCount + "条-------------------------------\r\n");

        return msgPack;
    }

    @Override
    public MsgPack getLoginSSOVerification(String token) {
        MsgPack msgPack=new MsgPack();
        //获得appkey
        String appKey=ConfigConstants.Auth_AppKey;
        String authUrl=ConfigConstants.Auth_Service;
        //获得客户端身份信息便于验证
        String address = request.getServletPath();
        String ip = request.getRemoteAddr();
        // sesson中存储客户端标识字段，需要进行验证是否是登陆着
        String security = ip + "<yutu_frame>" + request.getHeader("User-Agent");

        //报头信息
        JSONObject jsonAuth = new JSONObject();
        jsonAuth.put("token", token);//链接参数
        jsonAuth.put("appKey", appKey);//应用key，由系统管理员发放

        JSONObject jsonObject=new JSONObject();
        String loginStatus="";
        try {
            //判断登录接口
            String resultAuth =PortalIntegratedManager.getInterfaceValue(authUrl, jsonAuth, "/auth/loginSSO");
            msgPack=JSONObject.parseObject(resultAuth,MsgPack.class);

            ApiUser apiUser=(ApiUser)msgPack.getData();
            HttpSession session = request.getSession();
            SessionUser sessionUser = new SessionUser();
            sessionUser.setSessionId(session.getId());
            sessionUser.setUuid(apiUser.getUuid());
            sessionUser.setUserAccount(apiUser.getUserAccount());
            sessionUser.setUserName(apiUser.getUserName());
            sessionUser.setUserSafety(security);
            sessionUser.setToken(token);
            //判断session 存储sesion对象
            msgPack= sessionUserManager.setSessionUser(sessionUser);

            //记录日志
            TLogLanding landing = new TLogLanding();
            landing.setUuid(UUID.randomUUID().toString());
            landing.setLoginAddress(address);
            landing.setLoginAccount(sessionUser.getUserAccount());
            landing.setLoginDate(new Date());
            landing.setLoginIp(ip);
            landing.setLoginType("子系统一登陆");
            landing.setLoginAppname("子系统一");
            landing.setLoginUserid(sessionUser.getUuid());
            landing.setLoginSessionid(session.getId());
            landing.setLoginResult(msgPack.getStatus());
            //接口记录日志
            //报头信息
            JSONObject jsonLog = new JSONObject();
            jsonLog.put("token", token);//链接参数
            jsonLog.put("appKey", appKey);//应用key，由系统管理员发放
            jsonLog.put("landing", landing);//应用key，由系统管理员发放

            String resultLog =PortalIntegratedManager.getInterfaceValue(authUrl, jsonLog, "/auth/loginSSO");
            MsgPack msgPackLog= JSONObject.parseObject(resultLog,MsgPack.class);
            if(msgPackLog.getStatus()==1){
                logger.info("==============>门户登录日志记录成功！---------------------");
            }else {
                logger.info("==============>门户登录日志记录失败！---------------------");
            }

        } catch (Exception e) {
            msgPack.setData(0);
        }
        return msgPack;
    }


}

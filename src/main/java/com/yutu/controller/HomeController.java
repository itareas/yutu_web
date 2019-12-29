package com.yutu.controller;

import com.alibaba.fastjson.JSONObject;
import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.entity.api.ApiMenuBusiness;
import com.yutu.entity.api.ApiUser;
import com.yutu.service.IHomeService;
import com.yutu.util.RestClientUtils;
import com.yutu.util.SessionUserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: HomeController
 * @Author: zhaobc
 * @Date: 2019/6/26 17:34
 * @Description:框架主页数据测试
 **/
@RestController
@RequestMapping(value = "home")
public class HomeController {
    @Resource
    private IHomeService homeService;
    @Resource
    private SessionUserManager sessionUserManager;

    /**
     * @Author: zhaobc
     * @Date: 2019/12/15 18:07
     * @Description: 获得系统菜单列表
     **/
    @RequestMapping(value = "getSysMenuList")
    public MsgPack getSysMenuList(HttpServletRequest request) {
        MsgPack msgPack = new MsgPack();
        SessionUser sessionUser = sessionUserManager.getSessionUser();
        if (sessionUser.getMenu()==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("TOKEN", sessionUser.getToken());//链接参数
            map.put("APPKEY", ConfigConstants.Auth_AppKey);//应用key，由系统管理员发放
            msgPack = RestClientUtils.put(ConfigConstants.Auth_Service, "/menu/business", map, MsgPack.class);
            if (msgPack.getStatus() > 0) {
                msgPack.setStatus(1);
                msgPack.setData(msgPack.getData().toString());
                sessionUser.setMenu(msgPack.getData().toString());
                msgPack = sessionUserManager.setSessionUser(sessionUser);
            }
        } else {
            msgPack.setStatus(1);
            msgPack.setData(sessionUser.getMenu());
        }
        return msgPack;
    }
}

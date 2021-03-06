package com.yutu.controller;

import com.yutu.entity.ConfigConstants;
import com.yutu.entity.MsgPack;
import com.yutu.entity.MsgStatus;
import com.yutu.entity.SessionUser;
import com.yutu.service.IHomeService;
import com.yutu.utils.RestClientUtils;
import com.yutu.utils.SessionUserManager;
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
        if (sessionUser.getMenuBus()==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("token", sessionUser.getToken());//链接参数
            map.put("appkey", ConfigConstants.Auth_AppKey);//应用key，由系统管理员发放
            msgPack = RestClientUtils.put(ConfigConstants.Auth_Service, "/webapi/menu/business", map, MsgPack.class);
            if (msgPack.getStatus() > 0) {
                msgPack.setStatus(MsgStatus.SUCCESS.getCode());
                msgPack.setData(msgPack.getData().toString());
                sessionUser.setMenuBus(msgPack.getData().toString());
                msgPack = sessionUserManager.setSessionUser(sessionUser);
            }
        } else {
            msgPack.setStatus(MsgStatus.SUCCESS.getCode());
            msgPack.setData(sessionUser.getMenuBus());
        }
        return msgPack;
    }
}

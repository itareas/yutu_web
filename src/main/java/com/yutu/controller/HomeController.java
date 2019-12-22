package com.yutu.controller;

import com.yutu.entity.MsgPack;
import com.yutu.entity.SessionUser;
import com.yutu.entity.table.TMenuSystem;
import com.yutu.service.IHomeService;
import com.yutu.util.JsonListUtil;
import com.yutu.util.RedisUtils;
import com.yutu.util.SessionUserManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        MsgPack<List<TMenuSystem>> msgPask = new MsgPack<List<TMenuSystem>>();
        SessionUser user =(SessionUser) sessionUserManager.getSessionUser();
        if(user!=null){
            msgPask.setStatus(1);
            String strMenu=user.getMenu();
            List<TMenuSystem> menu= JsonListUtil.jsonToList(strMenu,TMenuSystem.class);
            List<TMenuSystem> menuReturn=new ArrayList<>();
            //便利菜单添加token
            for ( TMenuSystem tMenuSystem  : menu){
                String menuUrl=tMenuSystem.getMenuUrl();
                tMenuSystem.setMenuUrl(menuUrl+"?token="+request.getSession().getId());
                menuReturn.add(tMenuSystem);
            }
            msgPask.setData(menuReturn);
        }else {
            msgPask.setStatus(0);
        }
        return msgPask;
    }
}

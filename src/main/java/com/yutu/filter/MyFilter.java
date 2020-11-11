package com.yutu.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yutu.configuration.SystemPropertiesConfig;
import com.yutu.entity.MsgPack;
import com.yutu.listener.MyServletContextListener;
import com.yutu.utils.RedisUtils;
import com.yutu.utils.RestClientUtils;
import com.yutu.utils.SessionUserManager;
import org.apache.commons.lang3.StringUtils;

import com.yutu.entity.ConfigConstants;
import com.yutu.entity.SessionUser;
import com.yutu.utils.BlacklistUitls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyFilter
 * @Author: zhaobc
 * @Date: 2019/4/18
 * @Description:
 **/
@Component
@Order(2)
public class MyFilter implements Filter {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private SessionUserManager sessionUserManager;

    private long overtime;

    private final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String security = request.getRemoteAddr() + "<yutu_frame>" + request.getHeader("User-Agent");
            //判断是否启动黑名单
            if (SystemPropertiesConfig.System_Blacklist_StartUp.equals("true")) {
                //开始启动黑名单
                BlacklistUitls black = new BlacklistUitls();
                if (!black.judgeBlacklist(request)) {
                    return;
                }
            }
            //活动url地址和token
            String url = request.getServletPath();
            String token = request.getParameter("token");
            //获得session用户信息
            HttpSession session = request.getSession(false);
            SessionUser sessionUser = new SessionUser();
            //判断是否有数据
            boolean isUserData = false;
            //判断是否为redis单点登录
            if (SystemPropertiesConfig.System_LoginStorage_Type.equals("redis") && token != null && url.contains("loginSSO")) {
                //Redis版获取数据
                sessionUser = (SessionUser) redisUtils.get(token);
                //判断sessionUser是否有值
                if (sessionUser != null) {
                    isUserData = true;
                    request.getSession().setAttribute(SystemPropertiesConfig.System_Auth_Token, token);
                }
            } else {
                //正常登录后  验证session过期时间
                MsgPack msgPack = sessionUserManager.verificationSessionUser();
                isUserData = msgPack.getStatus() == 1 ? true : false;
                sessionUser = (SessionUser) msgPack.getData();
            }
            //判断session是否为null  sesson中存储客户端标识字段，需要进行验证是否是登陆着
            if (isUserData && sessionUser.getUserSafety().equals(security)) {
                if (url.equals("/")) {
                    //重定向到首页
                    response.sendRedirect(SystemPropertiesConfig.System_Home_Page);
                }
                //判断是否为单点登录，单点登录要要进行token验证
                if (SystemPropertiesConfig.System_LoginStorage_Type.equals("session") && SystemPropertiesConfig.System_Login_Type.equals("SSO")) {
                    if (remoteVerify(session, sessionUser.getToken())) {
                        chain.doFilter(request, response);
                    } else {
                        //重定向到登录页(需要在static文件夹下建立此html文件)
                        redirectHome(ConfigConstants.Auth_Service, response);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                //判断是否需要白名单
                if (StringUtils.isNotBlank(SystemPropertiesConfig.System_Filter_Path)) {
                    //白名单判断
                    String[] whiteUrl = SystemPropertiesConfig.System_Filter_Path.split(",");
                    //如果是白名单里的，放行
                    if (isWhiteListUrl(url, whiteUrl)) {
                        chain.doFilter(request, response);
                        return;
                    }
                }
                //未登录也不在白名单
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求 是ajax请求，返回错误信息
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    response.getWriter().write(SystemPropertiesConfig.System_Filter_Path);
                } else {
                    if (SystemPropertiesConfig.System_Login_Type.equals("SSO")) {
                        redirectHome(ConfigConstants.Auth_Service, response);
                    } else {
                        //重定向到登录页(需要在static文件夹下建立此html文件)
                        redirectHome(request.getContextPath() + SystemPropertiesConfig.System_Login_Page, response);
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("异常：",e);
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/4/21
     * @Description: 判断url是否属于白名单
     **/
    private boolean isWhiteListUrl(String httpUrl, String[] whiteUrl) {
        if (whiteUrl != null) {
            for (String eu : whiteUrl) {
                if (httpUrl.contains(eu.trim()) || httpUrl.equals("/") || (httpUrl.contains(".css") || httpUrl.contains(".js") || httpUrl.contains(".png") || httpUrl.contains(".jpg"))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Author: zhaobc
     * @Date: 2020/1/1 10:42
     * @Description: 核心方法，通过接口验证token是否过期
     **/
    private boolean remoteVerify(HttpSession session, String token) throws Exception {
        int interceptorOverTime = Integer.parseInt(SystemPropertiesConfig.System_Interceptor_OverTime);
        //定时  在一定时间段 只能访问一次，防止请求过于频繁 时间段内直接返回true
        if (new Date().getTime() - overtime <= interceptorOverTime) {
            return true;
        }
        //判断token
        if (StringUtils.isNotBlank(StringUtils.trimToNull(token))) {
            //访问token接口
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);//链接参数
            MsgPack msgPack = RestClientUtils.put(ConfigConstants.Auth_Service, "/webapi/auth/token", map, MsgPack.class);
            if (msgPack.getStatus() == 1) {
                overtime = new Date().getTime();
                return true;
            } else {
                //清空本地session
                sessionUserManager.logoutSessionUser(session);
            }
        }
        return false;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 9:28
     * @Description: 重定向首页
     **/
    private void redirectHome(String redirectUrl, HttpServletResponse response) throws IOException {
        //重定向到登录页(需要在static文件夹下建立此html文件)
        //response.sendRedirect(SystemPropertiesConfig.System_Login_Page);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("<html>");
        out.write("<body>");
        out.write("<script type=\"text/javascript\">");
        out.println("window.open ('" + redirectUrl + "','_top')");
        out.write("</script>");
        out.write("</body>");
        out.write("</html>");
    }
}
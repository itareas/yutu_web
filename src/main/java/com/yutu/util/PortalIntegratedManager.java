package com.yutu.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.http.MediaType;

/**
 * 门户集成接口
 *
 * @author:zhaobc
 * @creatTime:2018-11-30-上午9:38:46
 * @description:
 **/
public class PortalIntegratedManager {

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 16:47
     * @Description: 通用访问门户集成接口  type:类型
     **/
    public static String getInterfaceValue(String url, JSON json, String type) {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        String resultSync = webResource.path(type).entity(json, MediaType.APPLICATION_JSON_VALUE)
                .put(String.class);
        return resultSync;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 16:49
     * @Description: 通用访问门户集成接口返回传送参数  统一日志
     **/
    public static JSONObject getParameterValue(String APPKEY, String UUID, String USERNAME, String LOGNAME, String APPNAME,
                                               String IP, String LOGTYPE, String LOGBODY, Date CREATETIMES, String RID) {
        //将当前的时间转换成字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CREATETIME = format.format(CREATETIMES);

        JSONObject param = new JSONObject();
        param.put("APPKEY", APPKEY);
        param.put("UUID", UUID);
        param.put("USERNAME", USERNAME);
        param.put("LOGNAME", LOGNAME);
        param.put("APPNAME", APPNAME);
        param.put("LOGTYPE", LOGTYPE);
        param.put("IP", IP);
        param.put("LOGBODY", LOGBODY);
        param.put("CREATETIME", CREATETIME);
        param.put("RID", RID);
        return param;
    }

    /**
     * @Author: zhaobc
     * @Date: 2019/12/22 16:49
     * @Description: 通用访问门户集成接口返回delete参数  统一权限
     **/
    public static JSONObject getRootDelValue(String APPKEY, String UUID, String LOGNAME, String GRANT) {
			/*//将当前的时间转换成字符串
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String CREATETIME = format.format(CREATETIMES);
			*/
        JSONObject param = new JSONObject();
        param.put("APPKEY", APPKEY);
        param.put("UUID", UUID);
        param.put("LOGNAME", LOGNAME);
        param.put("GRANT", GRANT);
        return param;
    }

    //通用访问门户集成接口返回传送参数  统一权限
    public static JSONObject getRootAddValue(String APPKEY, String APPNAME, String userId, String LOGNAME, String USERNAME, String GRANT, Date date) {
        //将当前的时间转换成字符串
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String CREATETIME = format.format(date);
        JSONObject param = new JSONObject();
        param.put("APPKEY", APPKEY);
        param.put("APPNAME", APPNAME);
        param.put("UUID", userId);
        param.put("LOGNAME", LOGNAME);
        param.put("USERNAME", USERNAME);
        param.put("GRANT", GRANT);
        param.put("CREATETIME", CREATETIME);
        return param;
    }

    /**
     * 获取本机的ip地址
     *
     * @param args
     */
    public static String getIP() {
        // TODO Auto-generated method stub
        InetAddress ia = null;
        String IP = null;
        try {
            ia = ia.getLocalHost();
            IP = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IP;

    }

    //获取用户访问的ip
    public static String getIpuser(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    //将时间格式变成字符串格式
    public static String getStringDate(Date date) {
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取String类型的时间
        String createdate = sdf.format(date);
        return createdate;
    }

}

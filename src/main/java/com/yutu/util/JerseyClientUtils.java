package com.yutu.util;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * @Author: zhaobc
 * @Date: 2019-12-27 14:12
 * @Description:
 */
public class JerseyClientUtils {
    private Client client;

    JerseyClientUtils(){}

    /**
     * 静态内部类实现单例模式
     */
    private static class Singleton{
        private static final JerseyClientUtils jerseyClientUtil = new JerseyClientUtils();
    }

    /**
     * 获取单例JerseyClientUtil
     * @return
     */
    public static JerseyClientUtils getJerseyClientUtil(){
        return Singleton.jerseyClientUtil;
    }


    /**
     * 初始化默认的clint对象
     */
    private void setDefaultClient(){
        this.client =  ClientBuilder.newClient();
    }


    /**
     * 根据传入的ClientConfig对象初始化client对象
     * @param config
     */
    private void setConfigClient(ClientConfig config){
        this.client =  ClientBuilder.newClient(config);
    }

    /**
     * 根据ClientConfig初始化Client
     * @param config
     */
    private void initClient(ClientConfig config){
        if(config != null){
            this.setConfigClient(config);
        }else {
            this.setDefaultClient();
        }
    }

    /**
     * 发送http post请求，返回服务器响应信息
     * @param url 请求的URL
     * @param form 发送的form对象
     * @param config jerseyclient实例的配置信息，可以为空
     * @param tClass 根据此类型处理返回实体
     * @return response Response对象
     */
    public <T> T postInvoke(String url, Form form, ClientConfig config, Class<T> tClass){

        //初始化Client
        this.initClient(config);

        Response response = client.target(url).request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        T resultObject = response.readEntity(tClass);
        return resultObject;
    }

    /**
     * 发送http get请求，返回服务器响应信息
     * @param url 请求的URL
     * @param param 参数map
     * @param config jerseyclient实例的配置信息，可以为空
     * @param tClass 根据此类型处理返回实体
     * @return response Response对象
     */
    public <T> T getInvoke(String url, Map<String,Object> param, ClientConfig config, Class<T> tClass){

        //初始化Client
        this.initClient(config);
        WebTarget webTarget = client.target(url);
        //添加参数
        for(String key : param.keySet()){
            webTarget = webTarget.queryParam(key,param.get(key));
        }

        T resultObject = webTarget.request().get(tClass);

        return resultObject;

    }
}

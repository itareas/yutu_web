package com.yutu.webapi;

import com.yutu.entity.MsgPack;
import com.yutu.entity.table.TSysOrganization;
import com.yutu.entity.table.TSysUser;
import com.yutu.service.IOrganizationService;
import com.yutu.service.IUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author: zhaobc
 * @Date: 2019-12-19 13:56
 * @Description:数据同步接口服务
 */
@Component
@Path("sync")
public class SyncApiService {
    @Resource
    private IUserService iUserService;
    @Resource
    private IOrganizationService iOrganizationService;

    /**
     * @Author: zhaobc
     * @Date: 2019-12-19 14:01
     * @Description: 同步用户接口
     **/
    @POST
    @Path(value = "user/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public MsgPack user(@PathParam("type") String type, TSysUser user) {
        MsgPack msgPack = new MsgPack();
        switch (type) {
            case "add":
//                添加用户
                msgPack = iUserService.insertUser(user);
                break;
            case "edit":
//                修改用户
                msgPack = iUserService.updateUser(user);
                break;
            case "del":
//                删除用户
                msgPack = iUserService.delteUser(user.getUuid());
                break;
        }
        return msgPack;
    }


    /**
     * @Author: zhaobc
     * @Date: 2019-12-19 14:01
     * @Description: 同步用户接口
     **/
    @POST
    @Path(value = "organize/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public MsgPack organize(@PathParam("type") String type, TSysOrganization organization) {
        MsgPack msgPack = new MsgPack();
        switch (type) {
            case "add":
//                添加部门
                msgPack = iOrganizationService.insertOrganization(organization);
                break;
            case "edit":
//                修改部门
                msgPack = iOrganizationService.updateOrganization(organization);
                break;
            case "del":
//                删除删除
                msgPack = iOrganizationService.delteOrganization(organization.getUuid());
                break;
        }
        return msgPack;
    }
}

package com.yutu.service;

import com.yutu.entity.MsgPack;
import com.yutu.entity.table.TLogLanding;
import com.yutu.entity.table.TLogOperation;

/**
 * @ClassName: ILogService
 * @Author: zhaobc
 * @Date: 2019/4/21 17:30
 * @Description: 日志操作类
 **/
public interface ILogManagerService {
    /**
     * @Author: zhaobc
     * @Date: 2019/4/21 17:32
     * @Description: 插入登陆日志
     **/
    MsgPack insertLandingLog(TLogLanding landing);


    /**
     * @Author: zhaobc
     * @Date: 2019/4/21 17:32
     * @Description: 插入登陆日志
     **/
    MsgPack insertOperationLog(TLogOperation operation);

}

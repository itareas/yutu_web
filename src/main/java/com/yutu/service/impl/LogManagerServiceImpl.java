package com.yutu.service.impl;

import com.yutu.entity.MsgPack;
import com.yutu.entity.table.TLogLanding;
import com.yutu.mapper.mysql.TLogLandingMapper;
import com.yutu.service.ILogManagerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: LogServiceImpl
 * @Author: zhaobc
 * @Date: 2019/4/21 17:30
 * @Description: 日志操作类
 **/
@Service
public class LogManagerServiceImpl implements ILogManagerService {
    @Resource
    private TLogLandingMapper logLandingMapper;

    Logger logger = Logger.getLogger(LogManagerServiceImpl.class);
    /**
     * @Author: zhaobc
     * @Date: 2019/5/20 11:20
     * @Description: 插入登陆日志功能
     **/
    @Override
    public MsgPack insertLandingLog(TLogLanding landing) {
        MsgPack msgPack =new MsgPack();
        int landingCount= logLandingMapper.insert(landing);
        if(landingCount>0){
            msgPack.setStatus(1);
        }else{
            msgPack.setStatus(0);
        }
        logger.info("=============================>"+landing.getLoginAppname()+"日志插入" + landingCount + "条-------------------------------\r\n");
        return msgPack;
    }


}

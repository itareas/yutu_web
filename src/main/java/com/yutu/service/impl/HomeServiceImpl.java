package com.yutu.service.impl;

import com.yutu.entity.MsgPack;
import com.yutu.entity.MsgStatus;
import com.yutu.mapper.IMySqlMapper;
import com.yutu.service.IHomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HomeServiceImpl
 * @Author: zhaobc
 * @Date: 2019/6/26 17:43
 * @Description:
 **/
@Service
public class HomeServiceImpl implements IHomeService {
    @Resource
    private IMySqlMapper mySqlMapper;


    @Override
    public MsgPack getDataSource() {
        //声明返回数据
        MsgPack msgPack = new MsgPack();

        //获得不同数据源信息
        List<Map<String, Object>> listMySql = mySqlMapper.getMySqlTest("yt");

        //判断是否成功
        if (listMySql.size() > 0) {
            msgPack.setStatus(MsgStatus.SUCCESS.getCode());
            msgPack.setMsg("获取数据成功");
        } else {
            msgPack.setStatus(MsgStatus.FAIL.getCode());
            msgPack.setMsg("获取数据失败");
        }

        //集成数据
        Map<String, Object> mapData = new HashMap<String, Object>();
        mapData.put("MySql", listMySql);
        msgPack.setData(mapData);

        return msgPack;
    }
}

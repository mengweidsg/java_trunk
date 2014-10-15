package com.esunny.satellite.remote.dao.interf;

import java.util.Date;

import com.esunny.satellite.remote.entity.DwHomePageShowInfo;

public interface DwHomePageShowInfoDao {

    /**
     * 根据日期获取首页信息
     * 
     * @param time 时间
     * @return 首页信息
     */
    DwHomePageShowInfo selectHomePageInfoByTime(Date time);
}

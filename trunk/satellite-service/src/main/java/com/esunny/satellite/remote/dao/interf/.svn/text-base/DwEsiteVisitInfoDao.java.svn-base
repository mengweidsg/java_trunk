package com.esunny.satellite.remote.dao.interf;

import java.util.List;

import com.esunny.satellite.remote.entity.DwEsiteVisitInfo;

public interface DwEsiteVisitInfoDao {

    /**
     * 查询某个商铺最近N天访问记录（倒序返回）
     * 
     * @param userId 用户ID
     * @param num 最近N天
     * @return 最近N条访问记录
     */
    List<DwEsiteVisitInfo> queryLatestNVisitListFromCache(long userId, long num);

}

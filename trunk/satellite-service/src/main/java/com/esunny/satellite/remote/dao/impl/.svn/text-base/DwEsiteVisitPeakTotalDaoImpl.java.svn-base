package com.esunny.satellite.remote.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.cache.Cache;
import com.esunny.satellite.remote.dao.interf.DwEsiteVisitPeakTotalDao;
import com.esunny.satellite.remote.entity.DwEsiteVisitPeakTotal;

public class DwEsiteVisitPeakTotalDaoImpl extends SqlMapClientDaoSupport implements DwEsiteVisitPeakTotalDao {

    /** 用户的历史PV总量和峰值缓存（1天） */
    private Cache esiteVisitPeakTotalCache;

    /**
     * 查询某个用户的历史PV总量和峰值
     * 
     * @param userId 用户ID
     * @return 用户的历史PV总量和峰值
     */
    @SuppressWarnings("unchecked")
    @Override
    public DwEsiteVisitPeakTotal selectPeakTotalByUserIdFromCache(long userId) {
        DwEsiteVisitPeakTotal peakTotal = (DwEsiteVisitPeakTotal) esiteVisitPeakTotalCache.get(String.valueOf(userId));
        if (null != peakTotal) {
            return peakTotal;
        }
        List<DwEsiteVisitPeakTotal> list = getSqlMapClientTemplate().queryForList("DW_ESITE_VISIT_PEAK_TOTAL.selectPeakTotalByUserId",
                                                                                  userId);
        if (CollectionUtils.isNotEmpty(list)) {
            peakTotal = list.get(0);
        } else {
            peakTotal = new DwEsiteVisitPeakTotal();
        }
        esiteVisitPeakTotalCache.put(String.valueOf(userId), peakTotal);
        return peakTotal;
    }

    public void setEsiteVisitPeakTotalCache(Cache esiteVisitPeakTotalCache) {
        this.esiteVisitPeakTotalCache = esiteVisitPeakTotalCache;
    }

}

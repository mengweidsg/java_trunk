package com.esunny.satellite.remote.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.remote.dao.interf.DwHomePageShowInfoDao;
import com.esunny.satellite.remote.entity.DwHomePageShowInfo;

public class DwHomePageShowInfoDaoImpl extends SqlMapClientDaoSupport implements DwHomePageShowInfoDao {

    /** 首页信息缓存 */
    private Cache homePageInfoCache;

    @SuppressWarnings("unchecked")
    @Override
    public DwHomePageShowInfo selectHomePageInfoByTime(Date time) {
        String cacheKey = getHomePageInfoCacheKey(time);
        DwHomePageShowInfo info = null;
        if (StringUtil.isNotBlank(cacheKey)) {
            info = (DwHomePageShowInfo) homePageInfoCache.get(cacheKey);
            if (null != info) {
                return info;
            }
        }
        List<DwHomePageShowInfo> list = getSqlMapClientTemplate().queryForList("DW_HOME_PAGE_SHOW_INFO.selectHomePageInfoByTime",
                                                                               time);
        if (CollectionUtils.isNotEmpty(list)) {
            info = list.get(0);
        }
        // 放入缓存
        if (StringUtil.isNotBlank(cacheKey)) {
            if (null == info) {
                info = new DwHomePageShowInfo();
                info.setTime(time);
            } else {
                // 数据库不存在不做缓存
                homePageInfoCache.put(cacheKey, info);
            }
        }
        return info;
    }

    /**
     * 获取首页信息缓存键值
     * 
     * @param time 日期
     * @return 缓存键值
     */
    private String getHomePageInfoCacheKey(Date time) {
        return DateUtils.format(time, "yyyyMMdd");
    }

    public void setHomePageInfoCache(Cache homePageInfoCache) {
        this.homePageInfoCache = homePageInfoCache;
    }

}

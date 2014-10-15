package com.esunny.satellite.remote.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.cache.Cache;
import com.esunny.satellite.remote.dao.interf.DwEsitePagePvInfoDao;
import com.esunny.satellite.remote.entity.DwEsitePagePvInfo;

public class DwEsitePagePvInfoDaoImpl extends SqlMapClientDaoSupport implements DwEsitePagePvInfoDao {

    /** 商铺前10的页面PV信息缓存 */
    private Cache esiteTop10PagePvCache;

    /**
     * 查询商铺PV前10的页面PV信息
     * 
     * @param userId 用户ID
     * @return 页面PV信息
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DwEsitePagePvInfo> queryTop10PagePvByUserIdFromCache(long userId) {
        String cacheKey = getEsiteTop10PagePvCacheKey(userId);
        List<DwEsitePagePvInfo> topPagePvList = null;
        if (StringUtils.isNotBlank(cacheKey)) {
            topPagePvList = (List<DwEsitePagePvInfo>) esiteTop10PagePvCache.get(cacheKey);
            if (null != topPagePvList) {
                return topPagePvList;
            }
        }
        topPagePvList = getSqlMapClientTemplate().queryForList("DW_ESITE_PAGE_PV_INFO.queryTop10PagePvInfoByUserId",
                                                               userId);
        esiteTop10PagePvCache.put(cacheKey, topPagePvList);
        return topPagePvList;
    }

    private String getEsiteTop10PagePvCacheKey(long userId) {
        return String.valueOf(userId);
    }

    /**
     * @param esiteTop10PagePvCache the esiteTop10PagePvCache to set
     */
    public void setEsiteTop10PagePvCache(Cache esiteTop10PagePvCache) {
        this.esiteTop10PagePvCache = esiteTop10PagePvCache;
    }

}

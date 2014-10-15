package com.esunny.satellite.remote.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.remote.dao.interf.DwEsiteVisitInfoDao;
import com.esunny.satellite.remote.entity.DwEsiteVisitInfo;

public class DwEsiteVisitInfoDaoImpl extends SqlMapClientDaoSupport implements DwEsiteVisitInfoDao {

    /** 商铺最近N天访问记录缓存 */
    private Cache latestNVisitListCache;

    /**
     * 查询某个商铺最近N天访问记录
     * 
     * @param userId 用户ID
     * @param num 最近N天
     * @return 最近N条访问记录
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DwEsiteVisitInfo> queryLatestNVisitListFromCache(long userId, long num) {
        String cacheKey = getLatestNVisitListCacheKey(userId, num);
        List<DwEsiteVisitInfo> list = null;
        if (StringUtil.isNotBlank(cacheKey)) {
            list = (List<DwEsiteVisitInfo>) latestNVisitListCache.get(cacheKey);
            if (null != list) {
                return list;
            }
        }
        Map<String, Long> paramMap = new HashMap<String, Long>();
        paramMap.put("USERID", userId);
        paramMap.put("NUM", num);
        list = getSqlMapClientTemplate().queryForList("DW_ESITE_VISIT_INFO.queryLatestNVisitList", paramMap);
        latestNVisitListCache.put(cacheKey, list);
        return list;
    }

    /**
     * 商铺最近N天访问记录缓存key
     * 
     * @param userId 用户ID
     * @param num 最近N天
     * @return 缓存key
     */
    private String getLatestNVisitListCacheKey(long userId, long num) {
        return userId + "_" + num;
    }

    public void setLatestNVisitListCache(Cache latestNVisitListCache) {
        this.latestNVisitListCache = latestNVisitListCache;
    }

}

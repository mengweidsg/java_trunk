package com.esunny.satellite.remote.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.remote.dao.interf.DwEsitePvDayDao;
import com.esunny.satellite.remote.entity.DwEsitePvDay;

public class DwEsitePvDayDaoImpl extends SqlMapClientDaoSupport implements DwEsitePvDayDao {

    private Cache esitePvDayCache;

    /**
     * 检索指定日期的商铺的PV信息
     * 
     * @param userId 用户ID
     * @param date 指定日期
     * @return 商铺的PV信息
     */
    @SuppressWarnings("unchecked")
    public DwEsitePvDay selectPvDayInfoByUserIdDateFromCache(long userId, Date date) {
        try {
            String dataDate = DateUtils.format(date, "yyyyMMdd");
            if (StringUtils.isBlank(dataDate)) {
                return null;
            }
            String cacheKey = getEsitePvDayCacheKey(userId, dataDate);
            DwEsitePvDay dwEsitePvDay = null;
            if (StringUtil.isNotBlank(cacheKey)) {
                // 从缓存中获取
                dwEsitePvDay = (DwEsitePvDay) esitePvDayCache.get(cacheKey);
                if (null != dwEsitePvDay) {
                    return dwEsitePvDay;
                }
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("TABLENAME", "TMP_ESITE_PV_DAY_" + dataDate);
            paramMap.put("USERID", userId);
            List<DwEsitePvDay> list = getSqlMapClientTemplate().queryForList("DW_ESITE_PV_DAY.selectPvDayInfoByUserId",
                                                                             paramMap);
            if (CollectionUtils.isNotEmpty(list)) {
                dwEsitePvDay = list.get(0);
            } else {
                dwEsitePvDay = new DwEsitePvDay();
            }
            // 放入缓存
            esitePvDayCache.put(cacheKey, dwEsitePvDay);
            return dwEsitePvDay;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取商铺PV（按天）的缓存值
     * 
     * @param userId 用户ID
     * @param dataDate 日期（yyyymmdd）
     * @return 缓存值
     */
    private String getEsitePvDayCacheKey(long userId, String dataDate) {
        if (StringUtils.isBlank(dataDate)) {
            return null;
        }
        return String.valueOf(userId) + "_" + dataDate;
    }

    /**
     * @param esitePvDayCache the esitePvDayCache to set
     */
    public void setEsitePvDayCache(Cache esitePvDayCache) {
        this.esitePvDayCache = esitePvDayCache;
    }

}

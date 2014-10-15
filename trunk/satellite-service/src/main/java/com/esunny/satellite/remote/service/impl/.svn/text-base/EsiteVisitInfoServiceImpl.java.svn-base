/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.remote.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.satellite.common.model.DailyEsiteVisitModel;
import com.esunny.platform.satellite.common.model.EsiteHistoryPVInfoModel;
import com.esunny.platform.satellite.common.model.EsitePagePVInfoModel;
import com.esunny.platform.satellite.common.model.EsiteVisitInfoModel;
import com.esunny.platform.satellite.common.model.SatelliteQuery;
import com.esunny.platform.satellite.service.interf.EsiteVisitInfoService;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.remote.dao.interf.DwEsitePagePvInfoDao;
import com.esunny.satellite.remote.dao.interf.DwEsitePvDayDao;
import com.esunny.satellite.remote.dao.interf.DwEsiteVisitInfoDao;
import com.esunny.satellite.remote.dao.interf.DwEsiteVisitPeakTotalDao;
import com.esunny.satellite.remote.entity.DwEsitePagePvInfo;
import com.esunny.satellite.remote.entity.DwEsitePvDay;
import com.esunny.satellite.remote.entity.DwEsiteVisitInfo;
import com.esunny.satellite.remote.entity.DwEsiteVisitPeakTotal;

/**
 * @author Jet Xu 2012-3-29 下午4:43:25
 */
public class EsiteVisitInfoServiceImpl implements EsiteVisitInfoService {

    private DwEsiteVisitPeakTotalDao dwEsiteVisitPeakTotalDao;

    private DwEsiteVisitInfoDao      dwEsiteVisitInfoDao;

    private DwEsitePagePvInfoDao     dwEsitePagePvInfoDao;

    private DwEsitePvDayDao          dwEsitePvDayDao;

    private Cache                    esiteHistoryVisitCache;

    /**
     * 查询当天的访客信息（同时返回历史总量和历史最高量）
     * 
     * @param userId 用户ID
     * @return 商铺访客信息
     */
    public DailyEsiteVisitModel queryTodayVisitInfo(long userId) {
        DailyEsiteVisitModel model = new DailyEsiteVisitModel();
        DwEsiteVisitPeakTotal visitPeakTotal = dwEsiteVisitPeakTotalDao.selectPeakTotalByUserIdFromCache(userId);
        EsiteVisitInfoModel all = new EsiteVisitInfoModel();
        EsiteVisitInfoModel peak = new EsiteVisitInfoModel();
        EsiteVisitInfoModel today = new EsiteVisitInfoModel();
        if (null != visitPeakTotal) {
            if (null != visitPeakTotal.getPvAll()) {
                all.setPv(visitPeakTotal.getPvAll().longValue());
            }
            if (null != visitPeakTotal.getVisitAll()) {
                all.setVisit(visitPeakTotal.getVisitAll().longValue());
            }
            all.setDate(visitPeakTotal.getAllStatDate());
            if (null != visitPeakTotal.getPvPeak()) {
                peak.setPv(visitPeakTotal.getPvPeak().longValue());
            }
            if (null != visitPeakTotal.getVisitPeak()) {
                peak.setVisit(visitPeakTotal.getVisitPeak().longValue());
            }
            peak.setDate(visitPeakTotal.getDatePeak());
        }
        DwEsitePvDay esitePvDay = dwEsitePvDayDao.selectPvDayInfoByUserIdDateFromCache(userId, new Date());
        if (null != esitePvDay) {
            today.setDate(new Date());
            if (null != esitePvDay.getPv()) {
                today.setPv(esitePvDay.getPv());
            }
            // 访客数
            if (null != esitePvDay.getVisit()) {
                today.setVisit(esitePvDay.getVisit());
            }
        }
        // 总量
        model.setAll(all);
        // 峰值
        model.setMax(peak);
        // 当天
        model.setToday(today);
        return model;
    }

    /**
     * 查询历史访客信息列表（返回一个月的历史数据,支持分页）
     * 
     * @param userId 用户ID
     * @param query 分页信息：每页显示条数/返回第几页
     * @return 历史访客信息列表
     */
    public EsiteHistoryPVInfoModel queryHistoryVisitInfo(long userId, SatelliteQuery query) {
        EsiteHistoryPVInfoModel returnModel = new EsiteHistoryPVInfoModel();
        List<EsiteVisitInfoModel> historyInfoList = new ArrayList<EsiteVisitInfoModel>();
        if (null == query) {
            query = new SatelliteQuery();
        }
        List<EsiteVisitInfoModel> list = getLatestNVisitList(userId, 30);
        if (CollectionUtils.isNotEmpty(list)) {
            query.doPage(list.size());
            int startRow = query.getStartRow();
            int endRow = query.getEndRow();
            if (startRow > list.size()) {
                startRow = list.size();
            }
            if (endRow > list.size()) {
                endRow = list.size();
            }
            for (int i = startRow; i < endRow; i++) {
                historyInfoList.add(list.get(i));
            }
        }
        returnModel.setQuery(query);
        returnModel.setHistoryInfoList(historyInfoList);
        return returnModel;
    }

    @SuppressWarnings("unchecked")
    private List<EsiteVisitInfoModel> getLatestNVisitList(long userId, int days) {
        Date now = new Date();
        String nowStr = DateUtils.format(now, "yyyyMMdd");
        List<EsiteVisitInfoModel> resultList = null;
        String cacheKey = getEsiteHistoryVisitCacheKey(userId, nowStr);
        resultList = (List<EsiteVisitInfoModel>) esiteHistoryVisitCache.get(cacheKey);
        if (null != resultList) {
            return resultList;
        }
        List<DwEsiteVisitInfo> dbList = dwEsiteVisitInfoDao.queryLatestNVisitListFromCache(userId, days);

        Map<String, DwEsiteVisitInfo> visitInfoMap = new HashMap<String, DwEsiteVisitInfo>();
        if (CollectionUtils.isNotEmpty(dbList)) {
            for (DwEsiteVisitInfo info : dbList) {
                if (null == info || null == info.getTime()) {
                    continue;
                }
                visitInfoMap.put(DateUtils.format(info.getTime(), "yyyyMMdd"), info);
            }
        }
        resultList = new ArrayList<EsiteVisitInfoModel>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        // 处理前一天（考虑到数据仓库统计延迟，前一天的数据不存在的话从每天的表中获取）
        DwEsiteVisitInfo yesVisitInfo = visitInfoMap.get(DateUtils.format(cal.getTime(), "yyyyMMdd"));
        EsiteVisitInfoModel yesVisitModel = new EsiteVisitInfoModel();
        yesVisitModel.setDate(cal.getTime());
        if (null == yesVisitInfo) {
            DwEsitePvDay pvDayCur = dwEsitePvDayDao.selectPvDayInfoByUserIdDateFromCache(userId, cal.getTime());
            if (null != pvDayCur) {
                if (null != pvDayCur.getPv()) {
                    yesVisitModel.setPv(pvDayCur.getPv());
                }
                if (null != pvDayCur.getVisit()) {
                    yesVisitModel.setVisit(pvDayCur.getVisit());
                }
            }
        } else {
            if (null != yesVisitInfo.getPv()) {
                yesVisitModel.setPv(yesVisitInfo.getPv());
            }
            if (null != yesVisitInfo.getVisit()) {
                yesVisitModel.setVisit(yesVisitInfo.getVisit());
            }
        }
        resultList.add(yesVisitModel);
        // 转化前N天的历史数据，如果不存在，则返回当天PV为0的数据
        for (int i = 1; i < days; i++) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
            DwEsiteVisitInfo hisVisitInfo = visitInfoMap.get(DateUtils.format(cal.getTime(), "yyyyMMdd"));
            EsiteVisitInfoModel hisVisitModel = new EsiteVisitInfoModel();
            hisVisitModel.setDate(cal.getTime());
            if (null != hisVisitInfo) {
                if (null != hisVisitInfo.getPv()) {
                    hisVisitModel.setPv(hisVisitInfo.getPv());
                }
                if (null != hisVisitInfo.getVisit()) {
                    hisVisitModel.setVisit(hisVisitInfo.getVisit());
                }
            }
            resultList.add(hisVisitModel);
        }

        esiteHistoryVisitCache.put(cacheKey, resultList);
        return resultList;
    }

    private String getEsiteHistoryVisitCacheKey(long userId, String dateStr) {
        return userId + "_" + dateStr;
    }

    /**
     * 获取用户商铺PV最高的10个页面（返回数量小于等于10）
     * 
     * @param userId 用户ID
     * @return 商铺PV最高的10个页面
     */
    public List<EsitePagePVInfoModel> queryTop10EsitePagePvInfo(long userId) {
        List<DwEsitePagePvInfo> infoList = dwEsitePagePvInfoDao.queryTop10PagePvByUserIdFromCache(userId);
        List<EsitePagePVInfoModel> resultInfo = new ArrayList<EsitePagePVInfoModel>();
        if (CollectionUtils.isNotEmpty(infoList)) {
            for (int i = 0; i < infoList.size(); i++) {
                resultInfo.add(convertToEsitePagePVInfoModel(infoList.get(i)));
            }
        }
        return resultInfo;
    }

    /**
     * 转换为商铺页面PV信息model
     * 
     * @param info 商铺页面PV信息实体
     * @return 商铺页面PV信息model
     */
    private EsitePagePVInfoModel convertToEsitePagePVInfoModel(DwEsitePagePvInfo info) {
        if (null == info) {
            return null;
        }
        EsitePagePVInfoModel model = new EsitePagePVInfoModel();
        model.setUrl(info.getUrl());
        if (null != info.getPv()) {
            model.setPv(info.getPv());
        }
        if (null != info.getUserId()) {
            model.setUserId(info.getUserId());
        }
        return model;
    }

    /**
     * @param dwEsiteVisitPeakTotalDao the dwEsiteVisitPeakTotalDao to set
     */
    public void setDwEsiteVisitPeakTotalDao(DwEsiteVisitPeakTotalDao dwEsiteVisitPeakTotalDao) {
        this.dwEsiteVisitPeakTotalDao = dwEsiteVisitPeakTotalDao;
    }

    /**
     * @param dwEsiteVisitInfoDao the dwEsiteVisitInfoDao to set
     */
    public void setDwEsiteVisitInfoDao(DwEsiteVisitInfoDao dwEsiteVisitInfoDao) {
        this.dwEsiteVisitInfoDao = dwEsiteVisitInfoDao;
    }

    /**
     * @param dwEsitePagePvInfoDao the dwEsitePagePvInfoDao to set
     */
    public void setDwEsitePagePvInfoDao(DwEsitePagePvInfoDao dwEsitePagePvInfoDao) {
        this.dwEsitePagePvInfoDao = dwEsitePagePvInfoDao;
    }

    /**
     * @param dwEsitePvDayDao the dwEsitePvDayDao to set
     */
    public void setDwEsitePvDayDao(DwEsitePvDayDao dwEsitePvDayDao) {
        this.dwEsitePvDayDao = dwEsitePvDayDao;
    }

    /**
     * @param esiteHistoryVisitCache the esiteHistoryVisitCache to set
     */
    public void setEsiteHistoryVisitCache(Cache esiteHistoryVisitCache) {
        this.esiteHistoryVisitCache = esiteHistoryVisitCache;
    }

}

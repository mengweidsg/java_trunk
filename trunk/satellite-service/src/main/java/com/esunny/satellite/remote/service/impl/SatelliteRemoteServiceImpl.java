/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the
 * confidential and proprietary information of ESunny.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ESunny.com.
 */
package com.esunny.satellite.remote.service.impl;

import java.util.Date;

import com.esunny.platform.satellite.common.model.HomePageShowInfoModel;
import com.esunny.platform.satellite.service.interf.SatelliteRemoteService;
import com.esunny.satellite.remote.dao.interf.DwHomePageShowInfoDao;
import com.esunny.satellite.remote.entity.DwHomePageShowInfo;


/**
 * 数据仓库对外提供的hessian接口实现
 * @author Jet Xu 2012-6-19 上午9:46:08
 */
public class SatelliteRemoteServiceImpl implements SatelliteRemoteService{


    private DwHomePageShowInfoDao    dwHomePageShowInfoDao;

    @Override
    public HomePageShowInfoModel selectHomePageShowInfo(Date date) {
        DwHomePageShowInfo info = dwHomePageShowInfoDao.selectHomePageInfoByTime(date);
        HomePageShowInfoModel model = null;
        if (null != info) {
            model = new HomePageShowInfoModel();
            model.setTime(info.getTime());
            model.setMemberRegDailyNum(info.getMemberRegDailyNum());
            model.setPublishBuyOfferDailyNum(info.getPublishBuyOfferDailyNum());
            model.setPublishSellOfferDailyNum(info.getPublishSellOfferDailyNum());
        }
        return model;
    }

    /**
     * @param dwHomePageShowInfoDao the dwHomePageShowInfoDao to set
     */
    public void setDwHomePageShowInfoDao(DwHomePageShowInfoDao dwHomePageShowInfoDao) {
        this.dwHomePageShowInfoDao = dwHomePageShowInfoDao;
    }
}

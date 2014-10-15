/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.impl;

import org.apache.commons.lang.StringUtils;

import com.esunny.satellite.report.dao.interf.DwUserInfoDao;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.service.intef.LoginService;

/**
 * 登录逻辑
 * 
 * @author Jet Xu 2012-2-21 下午8:09:10
 */
public class LoginServiceImpl implements LoginService {

    private DwUserInfoDao dwUserInfoDao;

    @Override
    public DwUserInfo getUserInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return dwUserInfoDao.selectDwUserInfoByUserId(userId);
    }

    @Override
    public DwUserInfo getUserInfoById(Long id) {
        if (null == id) {
            return null;
        }
        return dwUserInfoDao.selectDwUserInfoById(id);
    }

    /**
     * @param dwUserInfoDao the dwUserInfoDao to set
     */
    public void setDwUserInfoDao(DwUserInfoDao dwUserInfoDao) {
        this.dwUserInfoDao = dwUserInfoDao;
    }

}

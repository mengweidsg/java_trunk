/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.intef;

import com.esunny.satellite.report.entity.DwUserInfo;

/**
 * 登录逻辑
 * 
 * @author Jet Xu 2012-2-21 下午8:07:21
 */
public interface LoginService {

    /**
     * 获取用户信息
     * 
     * @param userId
     * @return
     */
    public DwUserInfo getUserInfo(String userId);

    /**
     * 获取用户信息
     * 
     * @param userId
     * @return
     */
    public DwUserInfo getUserInfoById(Long id);
}

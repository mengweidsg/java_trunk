/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.intef;

import java.util.Map;

import com.esunny.satellite.report.entity.ReportMenuModel;

/**
 * 报表目录业务接口
 * 
 * @author Jet Xu 2012-5-2 下午3:21:46
 */
public interface ReportMenuService {

    Map<Long, ReportMenuModel> queryAllReportMenuList();
}

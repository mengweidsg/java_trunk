/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.esunny.satellite.report.common.ReportSqlException;
import com.esunny.satellite.report.dao.CommonDao;
import com.esunny.satellite.report.service.intef.QueryInputSqlResultService;

/**
 * 根据页面上输入的SQL，查询出结果业务实现
 * 
 * @author Jet Xu 2012-4-9 上午11:42:24
 */
public class QueryInputSqlResultServiceImpl implements QueryInputSqlResultService {

    /** 日志类 */
    private static Logger log = Logger.getLogger(QueryInputSqlResultServiceImpl.class);

    private CommonDao     commonDao;

    @Override
    public List<Map<String, String>> querySearchCondition(String sql) {
        try {
            return commonDao.queryForListWithSql(sql, null, null);
        } catch (ReportSqlException e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * @param commonDao the commonDao to set
     */
    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

}

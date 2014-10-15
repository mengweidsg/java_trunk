/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.intef;

import java.util.List;
import java.util.Map;

/**
 * 根据页面上输入的SQL，查询出结果业务接口
 * 
 * @author Jet Xu 2012-4-9 上午11:40:47
 */
public interface QueryInputSqlResultService {

    List<Map<String, String>> querySearchCondition(String sql);
}

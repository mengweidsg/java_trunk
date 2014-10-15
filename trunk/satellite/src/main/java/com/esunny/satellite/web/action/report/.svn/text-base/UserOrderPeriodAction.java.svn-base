/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.web.action.report;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.vo.ReportSearchConditionVo;
import com.esunny.satellite.vo.SearchConditonVo;

/**
 * 用户购买时段报表
 * 
 * @author Jet Xu 2012-6-27 上午10:59:44
 */
public class UserOrderPeriodAction extends ReportRealTimeQueryAction {

    private static final long serialVersionUID = 690432938664199340L;

    /**
     * 对检索出来的结果统计日支付成功会员数和平均支付成功额
     */
    @Override
    public String queryRealTimeList() {
        String result = super.queryRealTimeList();
        long lMaxDate = Long.MIN_VALUE;
        long lMinDate = Long.MAX_VALUE;
        if (CollectionUtils.isNotEmpty(this.getReportResult())) {
            // 获取日期差
            long sub = -1;
            SearchConditonVo searchCond = this.getSearchConditonVo();
            if (null != searchCond && null != searchCond.getDoubleDateInfo()) {
                ReportSearchConditionVo doubleDateInfo = searchCond.getDoubleDateInfo();
                if (CollectionUtils.isNotEmpty(doubleDateInfo.getValueList())
                    && doubleDateInfo.getValueList().size() == 2) {
                    Date min = DateUtils.toDate(doubleDateInfo.getValueList().get(0), "yyyy-MM-dd");
                    Date max = DateUtils.toDate(doubleDateInfo.getValueList().get(1), "yyyy-MM-dd");
                    if (null != min && null != max) {
                        sub = (max.getTime() - min.getTime()) / (24 * 60 * 60 * 1000) + 1;
                    }
                }
            }
            if (sub <= 0) {
                try {
                    for (Map<String, String> map : this.getReportResult()) {
                        if (null == map) {
                            continue;
                        }
                        Long tmpMaxDate = Long.valueOf(map.get("MAX_DATE"));
                        Long tmpMinDate = Long.valueOf(map.get("MIN_DATE"));
                        if (null != tmpMaxDate && tmpMaxDate.longValue() > lMaxDate) {
                            lMaxDate = tmpMaxDate.longValue();
                        }
                        if (null != tmpMinDate && tmpMinDate.longValue() < lMinDate) {
                            lMinDate = tmpMinDate.longValue();
                        }
                    }
                } catch (Exception ex) {
                    lMaxDate = Long.MIN_VALUE;
                    lMinDate = Long.MAX_VALUE;
                }
                if (lMaxDate != Long.MIN_VALUE && lMinDate != Long.MAX_VALUE) {
                    Date max = DateUtils.toDate(String.valueOf(lMaxDate), "yyyyMMddhhmmss");
                    Date min = DateUtils.toDate(String.valueOf(lMinDate), "yyyyMMddhhmmss");
                    if (null != min && null != max) {
                        sub = (max.getTime() - min.getTime()) / (24 * 60 * 60 * 1000) + 1;
                    }
                }
            }
            if (sub <= 0) {
                sub = 1;
            }
            //
            for (Map<String, String> map : this.getReportResult()) {
                int dailyUser = 0;
                float aveAmount = 0.0f;
                try {
                    long sumUser = Long.valueOf(map.get("SUM_USER"));
                    float sumAmount = Float.valueOf(map.get("SUM_AMOUNT"));
                    dailyUser = Math.round((float) sumUser / sub);
                    if (sumUser != 0) {
                        aveAmount = sumAmount / sumUser;
                    }
                } catch (Exception ex) {
                    dailyUser = 0;
                }
                map.put("DAILY_USER", String.valueOf(dailyUser));
                map.put("AVE_AMOUNT", String.valueOf((float) (Math.round(aveAmount * 100)) / 100));
            }
        }

        return result;
    }
}

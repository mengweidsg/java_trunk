/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.vo;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.entity.ReportSearchCondition;

/**
 * 类ReportSearchConditionVo.java的实现描述：TODO 类实现描述
 * 
 * @author Jet Xu 2012-4-20 上午11:04:47
 */
public class ReportSearchConditionVo extends ReportSearchCondition {

    private static final long serialVersionUID = 992293948064502739L;

    /** 输入值 */
    private String            value;

    /** 输入值列表（特殊控件用） */
    private List<String>      valueList;

    /**
     * @return the value
     */
    public String getValue() {
        if (null != this.getInputType() && this.getInputType() == INPUT_TYPE.DOUBLE_DATE) {
            if (CollectionUtils.isNotEmpty(valueList)) {
                String from = valueList.get(0) == null ? "" : valueList.get(0);
                String to = "";
                if (valueList.size() > 0) {
                    to = valueList.get(1) == null ? "" : valueList.get(1);
                }
                return from + " - " + to;
            }
        }
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the valueList
     */
    public List<String> getValueList() {
        return valueList;
    }

    /**
     * @param valueList the valueList to set
     */
    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

}

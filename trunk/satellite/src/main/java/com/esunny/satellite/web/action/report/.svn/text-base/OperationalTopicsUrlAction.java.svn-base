/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.web.action.report;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.dao.interf.DwOperationalTopicsUrlDao;
import com.esunny.satellite.report.entity.DwOperationalTopicsUrl;
import com.esunny.satellite.web.action.MenuBaseAction;

/**
 * 新增专题页面url的Action
 * 
 * @author Jet Xu 2012-7-16 下午2:48:47
 */
public class OperationalTopicsUrlAction extends MenuBaseAction {

    private static final long         serialVersionUID = 7149440541845786277L;

    private String                    operaTopicsUrl;

    private String                    effectStaDate;

    private String                    effectEndDate;

    private String                    errMsg;

    private DwOperationalTopicsUrlDao dwOperationalTopicsUrlDao;

    public String showAddOperaTopicsUrl() {
        return SUCCESS;
    }

    public String addOperaTopicsUrl() {
        Date dEffectSta = DateUtils.toDate(effectStaDate, "yyyy-MM-dd");
        Date dEffectEnd = DateUtils.toDate(effectEndDate, "yyyy-MM-dd");

        if (StringUtils.isBlank(operaTopicsUrl)) {
            errMsg = "专题页面URL不能为空！";
        } else if (null == dEffectSta || null == dEffectEnd) {
            errMsg = "预期统计时间范围必须输入！";
        } else {
            DwOperationalTopicsUrl dwOperationalTopicsUrl = new DwOperationalTopicsUrl();
            dwOperationalTopicsUrl.setUrl(operaTopicsUrl);
            dwOperationalTopicsUrl.setEffectStaDate(dEffectSta);
            dwOperationalTopicsUrl.setEffectEndDate(dEffectEnd);
            dwOperationalTopicsUrlDao.insertDwOperationalTopicsUrl(dwOperationalTopicsUrl);
            errMsg = "添加成功";
        }
        return SUCCESS;
    }

    /**
     * @return the operaTopicsUrl
     */
    public String getOperaTopicsUrl() {
        return operaTopicsUrl;
    }

    /**
     * @param operaTopicsUrl the operaTopicsUrl to set
     */
    public void setOperaTopicsUrl(String operaTopicsUrl) {
        this.operaTopicsUrl = operaTopicsUrl;
    }

    /**
     * @return the effectStaDate
     */
    public String getEffectStaDate() {
        return effectStaDate;
    }

    /**
     * @param effectStaDate the effectStaDate to set
     */
    public void setEffectStaDate(String effectStaDate) {
        this.effectStaDate = effectStaDate;
    }

    /**
     * @return the effectEndDate
     */
    public String getEffectEndDate() {
        return effectEndDate;
    }

    /**
     * @param effectEndDate the effectEndDate to set
     */
    public void setEffectEndDate(String effectEndDate) {
        this.effectEndDate = effectEndDate;
    }

    /**
     * @return the errMsg
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * @param dwOperationalTopicsUrlDao the dwOperationalTopicsUrlDao to set
     */
    public void setDwOperationalTopicsUrlDao(DwOperationalTopicsUrlDao dwOperationalTopicsUrlDao) {
        this.dwOperationalTopicsUrlDao = dwOperationalTopicsUrlDao;
    }

}

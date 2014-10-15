/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.CookieUtils;
import com.esunny.platform.web.handler.action.PermitBaseAction;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.entity.ReportMenu;
import com.esunny.satellite.util.SatelliteConstants;
import com.esunny.satellite.util.SatelliteUtil;

/**
 * 菜单相关Action基类
 * 
 * @author Jet Xu 2012-4-9 下午3:03:02
 */
public abstract class MenuBaseAction extends PermitBaseAction implements ServletRequestAware {

    private static final long  serialVersionUID = 1028411572364551316L;

    public static final String AUTHORITY_DENY   = "authorityDeny";

    private ReportMenu         currentMenu;

    private DwUserInfo         dwUserInfo;

    private Cache              loginCacheTemplate;

    private HttpServletRequest request;

    protected String           downloadFlag;

    public Long getLogonUserId() {
        String logonKey = CookieUtils.getCookieValue(ServletActionContext.getRequest(),
                                                     SatelliteConstants.SATELLITE_COOKIE_LOGON);
        Long id = null;
        // // 该用户是否已经登录
        if (StringUtil.isNotBlank(logonKey)) {
            // 获取用户ID
            id = (Long) loginCacheTemplate.get(logonKey);
        }
        return id;
    }

    protected String getReportKeyWord(String prefix) {
        if (null != this.getCurrentMenu()) {
            return this.getCurrentMenu().getTableName();
        } else {
            return SatelliteUtil.fetchTableNameFromUri(request.getRequestURI(), prefix);
        }
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;

    }

    /**
     * @return the currentMenu
     */
    public ReportMenu getCurrentMenu() {
        return currentMenu;
    }

    /**
     * @param currentMenu the currentMenu to set
     */
    public void setCurrentMenu(ReportMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    /**
     * @return the dwUserInfo
     */
    public DwUserInfo getDwUserInfo() {
        return dwUserInfo;
    }

    /**
     * @param dwUserInfo the dwUserInfo to set
     */
    public void setDwUserInfo(DwUserInfo dwUserInfo) {
        this.dwUserInfo = dwUserInfo;
    }

    /**
     * @param loginCacheTemplate the loginCacheTemplate to set
     */
    public void setLoginCacheTemplate(Cache loginCacheTemplate) {
        this.loginCacheTemplate = loginCacheTemplate;
    }

    /**
     * @return the downloadFlag
     */
    public String getDownloadFlag() {
        return downloadFlag;
    }

    /**
     * @param downloadFlag the downloadFlag to set
     */
    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }
}

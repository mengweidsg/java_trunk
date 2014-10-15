/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.web.action.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.constant.ReportConstant;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.entity.ReportMenuModel;
import com.esunny.satellite.report.service.intef.DwUserInfoService;
import com.esunny.satellite.report.service.intef.ReportMenuService;
import com.esunny.satellite.web.action.MenuBaseAction;

/**
 * 用户权限管理
 * 
 * @author Jet Xu 2012-5-2 下午2:25:00
 */
public class UserAuthorityAction extends MenuBaseAction {

    private static final long          serialVersionUID = -494152511991566878L;

    private ReportMenuService          reportMenuService;

    private DwUserInfoService          dwUserInfoService;

    private Map<Long, ReportMenuModel> menuModelMap;

    private List<String>               selMenuList;

    private DwUserInfo                 query;

    /**
     * 显示用户报表管理页面
     * 
     * @return
     */
    public String showUserReportManager() {
        menuModelMap = reportMenuService.queryAllReportMenuList();
        if (null != query.getId()) {
            if (!dwUserInfoService.canOperateUser(this.getDwUserInfo(), query.getId())) {
                return AUTHORITY_DENY;
            }
            DwUserInfo dwUserInfo = dwUserInfoService.selectDwUserInfoById(query.getId());
            if (null != dwUserInfo && StringUtil.isNotBlank(dwUserInfo.getReportAuthorityStr())) {
                String[] selMenuArr = dwUserInfo.getReportAuthorityStr().split(ReportConstant.DEFULT_SPLIT_STR);
                for (String selMenu : selMenuArr) {
                    try {
                        Long menuId = Long.valueOf(selMenu);
                        ReportMenuModel model = menuModelMap.get(menuId);
                        if (null != model) {
                            model.setNeedSelect("1");
                        }
                    } catch (Exception ex) {
                        continue;
                    }
                }
            }
        }
        return SUCCESS;
    }

    public String editUserReportAuthority() {
        if (null != query.getId()) {
            if (!dwUserInfoService.canOperateUser(this.getDwUserInfo(), query.getId())) {
                return AUTHORITY_DENY;
            }
            StringBuilder sbMenuStr = new StringBuilder();
            if (CollectionUtils.isNotEmpty(selMenuList)) {
                for (String str : selMenuList) {
                    if (sbMenuStr.length() > 0) {
                        sbMenuStr.append(ReportConstant.DEFULT_SPLIT_STR);
                    }
                    sbMenuStr.append(str);
                }
            }
            DwUserInfo updateUserInfo = new DwUserInfo();
            updateUserInfo.setId(query.getId());
            updateUserInfo.setReportAuthorityStr(sbMenuStr.toString());
            dwUserInfoService.updateDwUserInfoById(updateUserInfo);
        }
        return SUCCESS;
    }

    /**
     * @param reportMenuService the reportMenuService to set
     */
    public void setReportMenuService(ReportMenuService reportMenuService) {
        this.reportMenuService = reportMenuService;
    }

    /**
     * @param dwUserInfoService the dwUserInfoService to set
     */
    public void setDwUserInfoService(DwUserInfoService dwUserInfoService) {
        this.dwUserInfoService = dwUserInfoService;
    }

    /**
     * @return the menuModelMap
     */
    public Map<Long, ReportMenuModel> getMenuModelMap() {
        return menuModelMap;
    }

    /**
     * @param menuModelMap the menuModelMap to set
     */
    public void setMenuModelMap(Map<Long, ReportMenuModel> menuModelMap) {
        this.menuModelMap = menuModelMap;
    }

    /**
     * @return the selMenuList
     */
    public List<String> getSelMenuList() {
        return selMenuList;
    }

    /**
     * @param selMenuList the selMenuList to set
     */
    public void setSelMenuList(List<String> selMenuList) {
        this.selMenuList = selMenuList;
    }

    /**
     * @return the query
     */
    public DwUserInfo getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(DwUserInfo query) {
        this.query = query;
    }

}

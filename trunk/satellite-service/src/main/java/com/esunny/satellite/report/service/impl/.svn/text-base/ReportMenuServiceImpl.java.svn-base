/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.satellite.report.dao.interf.ReportMenuDao;
import com.esunny.satellite.report.entity.ReportMenu;
import com.esunny.satellite.report.entity.ReportMenuModel;
import com.esunny.satellite.report.service.intef.ReportMenuService;

/**
 * 报表目录业务接类
 * 
 * @author Jet Xu 2012-5-2 下午3:23:08
 */
public class ReportMenuServiceImpl implements ReportMenuService {

    private ReportMenuDao reportMenuDao;

    @Override
    public Map<Long, ReportMenuModel> queryAllReportMenuList() {
        Map<Long, ReportMenuModel> resultMap = new LinkedHashMap<Long, ReportMenuModel>();
        ReportMenu condition = new ReportMenu();
        condition.setOrderByClause("menu_level, id");
        List<ReportMenu> menuList = reportMenuDao.queryReportMenuList(condition);
        if (CollectionUtils.isNotEmpty(menuList)) {
            for (int i = 0; i < menuList.size(); i++) {
                ReportMenu reportMenu = menuList.get(i);
                if (null == reportMenu) {
                    continue;
                }
                resultMap.put(reportMenu.getId(), convertToMenuModel(reportMenu));
                ReportMenuModel parentModel = resultMap.get(reportMenu.getParentId());
                // 如果父目录存在，则添加进父目录的子列表中
                if (null != parentModel) {
                    List<Long> children = parentModel.getChildren();
                    if (null == children) {
                        children = new ArrayList<Long>();
                        parentModel.setChildren(children);
                    }
                    children.add(reportMenu.getId());
                }
            }
        }
        return resultMap;
    }

    private ReportMenuModel convertToMenuModel(ReportMenu reportMenu) {
        if (null == reportMenu) {
            return null;
        }
        ReportMenuModel model = new ReportMenuModel();
        model.setId(reportMenu.getId());
        model.setName(reportMenu.getName());
        model.setTableName(reportMenu.getTableName());
        model.setUrl(reportMenu.getUrl());
        model.setMenuLevel(reportMenu.getMenuLevel());
        model.setParentId(reportMenu.getParentId());
        model.setReportName(reportMenu.getReportName());
        return model;
    }

    /**
     * @param reportMenuDao the reportMenuDao to set
     */
    public void setReportMenuDao(ReportMenuDao reportMenuDao) {
        this.reportMenuDao = reportMenuDao;
    }

}

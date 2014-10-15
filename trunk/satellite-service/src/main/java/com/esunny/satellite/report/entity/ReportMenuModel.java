/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.entity;

import java.util.List;

/**
 * 目录信息model
 * 
 * @author Jet Xu 2012-5-2 下午3:19:09
 */
public class ReportMenuModel extends ReportMenu {

    private static final long serialVersionUID = 7941724301139340948L;

    /**
     * 下一级目录列表
     */
    private List<Long>        children;

    /**
     * 是否需要选中
     */
    private String            needSelect;

    /**
     * @return the children
     */
    public List<Long> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<Long> children) {
        this.children = children;
    }

    /**
     * @return the needSelect
     */
    public String getNeedSelect() {
        return needSelect;
    }

    /**
     * @param needSelect the needSelect to set
     */
    public void setNeedSelect(String needSelect) {
        this.needSelect = needSelect;
    }

}

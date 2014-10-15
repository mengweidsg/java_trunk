package com.esunny.satellite.report.entity;

import com.esunny.platform.util.page.Query;

public class ReportMenu extends Query {

    private static final long serialVersionUID = -2818648436004092107L;

    private Long              id;

    private String            name;

    private String            tableName;

    private String            url;

    private String            menuLevel;

    private Long              parentId;

    private String            reportName;

    /** 权限校验方式。1.报表权限 2.管理员权限 3.全员可访问（为空时为1.报表权限）99.超级管理员权限 */
    private Integer           authorityType;

    /** 是否在菜单栏隐藏。1.隐藏 其他.显示 */
    private Integer           hideFlag;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the menuLevel
     */
    public String getMenuLevel() {
        return menuLevel;
    }

    /**
     * @param menuLevel the menuLevel to set
     */
    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    /**
     * @return the parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the authorityType
     */
    public Integer getAuthorityType() {
        return authorityType;
    }

    /**
     * @param authorityType the authorityType to set
     */
    public void setAuthorityType(Integer authorityType) {
        this.authorityType = authorityType;
    }

    /**
     * @return the hideFlag
     */
    public Integer getHideFlag() {
        return hideFlag;
    }

    /**
     * @param hideFlag the hideFlag to set
     */
    public void setHideFlag(Integer hideFlag) {
        this.hideFlag = hideFlag;
    }

}

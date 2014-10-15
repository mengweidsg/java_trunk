package com.esunny.satellite.report.entity;

import java.io.Serializable;

public class ReportInfoPlus implements Serializable {

    private static final long serialVersionUID = -5255557112649520440L;

    private Long              id;

    private String            tableName;

    private String            needIndex;

    private String            sortColumn;

    private String            sortDeriction;

    private String            sortColumnList;

    private String            hideSort;

    private String            selectSql;

    private Integer           pageSize;

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
     * @return the needIndex
     */
    public String getNeedIndex() {
        return needIndex;
    }

    /**
     * @param needIndex the needIndex to set
     */
    public void setNeedIndex(String needIndex) {
        this.needIndex = needIndex;
    }

    /**
     * @return the sortColumn
     */
    public String getSortColumn() {
        return sortColumn;
    }

    /**
     * @param sortColumn the sortColumn to set
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * @return the sortDeriction
     */
    public String getSortDeriction() {
        return sortDeriction;
    }

    /**
     * @param sortDeriction the sortDeriction to set
     */
    public void setSortDeriction(String sortDeriction) {
        this.sortDeriction = sortDeriction;
    }

    /**
     * @return the sortColumnList
     */
    public String getSortColumnList() {
        return sortColumnList;
    }

    /**
     * @param sortColumnList the sortColumnList to set
     */
    public void setSortColumnList(String sortColumnList) {
        this.sortColumnList = sortColumnList;
    }

    /**
     * @return the hideSort
     */
    public String getHideSort() {
        return hideSort;
    }

    /**
     * @param hideSort the hideSort to set
     */
    public void setHideSort(String hideSort) {
        this.hideSort = hideSort;
    }

    /**
     * @return the selectSql
     */
    public String getSelectSql() {
        return selectSql;
    }

    /**
     * @param selectSql the selectSql to set
     */
    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}

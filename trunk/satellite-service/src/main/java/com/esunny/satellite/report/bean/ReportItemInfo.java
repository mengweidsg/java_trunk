package com.esunny.satellite.report.bean;

/**
 */
public class ReportItemInfo {

    /** 序号 */
    private Integer id;

    /** 表名 */
    private String  tableName;

    /** 列名 */
    private String  columnName;

    /** 列名（显示用） */
    private String  columnDisplayName;

    /** 控件类型 */
    private Integer inputType;

    /** 枚举表名 */
    private String  enumTableName;

    /** 枚举表名 */
    private String  isEnum;

    /** 是否完全匹配 */
    private Boolean isCompleteMatch;

    /** 列的类型 */
    private String  columnDataType;

    /** 是否需要sum */
    private String  isSum;

    /** 是否需要groupBy */
    private String  isGroupBy;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
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
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the columnDisplayName
     */
    public String getColumnDisplayName() {
        return columnDisplayName;
    }

    /**
     * @param columnDisplayName the columnDisplayName to set
     */
    public void setColumnDisplayName(String columnDisplayName) {
        this.columnDisplayName = columnDisplayName;
    }

    /**
     * @return the inputType
     */
    public Integer getInputType() {
        return inputType;
    }

    /**
     * @param inputType the inputType to set
     */
    public void setInputType(Integer inputType) {
        this.inputType = inputType;
    }

    /**
     * @return the enumTableName
     */
    public String getEnumTableName() {
        return enumTableName;
    }

    /**
     * @param enumTableName the enumTableName to set
     */
    public void setEnumTableName(String enumTableName) {
        this.enumTableName = enumTableName;
    }

    /**
     * @return the isCompleteMatch
     */
    public Boolean getIsCompleteMatch() {
        return isCompleteMatch;
    }

    /**
     * @param isCompleteMatch the isCompleteMatch to set
     */
    public void setIsCompleteMatch(Boolean isCompleteMatch) {
        this.isCompleteMatch = isCompleteMatch;
    }

    /**
     * @return the columnDataType
     */
    public String getColumnDataType() {
        return columnDataType;
    }

    /**
     * @param columnDataType the columnDataType to set
     */
    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType;
    }

    /**
     * @return the isSum
     */
    public String getIsSum() {
        return isSum;
    }

    /**
     * @param isSum the isSum to set
     */
    public void setIsSum(String isSum) {
        this.isSum = isSum;
    }

    /**
     * @return the isGroupBy
     */
    public String getIsGroupBy() {
        return isGroupBy;
    }

    /**
     * @param isGroupBy the isGroupBy to set
     */
    public void setIsGroupBy(String isGroupBy) {
        this.isGroupBy = isGroupBy;
    }

    /**
     * @return the isEnum
     */
    public String getIsEnum() {
        return isEnum;
    }

    /**
     * @param isEnum the isEnum to set
     */
    public void setIsEnum(String isEnum) {
        this.isEnum = isEnum;
    }

}

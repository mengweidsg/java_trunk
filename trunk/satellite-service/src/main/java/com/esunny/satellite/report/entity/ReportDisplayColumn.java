package com.esunny.satellite.report.entity;

import com.esunny.platform.util.page.Query;

public class ReportDisplayColumn extends Query {

    private static final long serialVersionUID = 5759796865739566820L;

    /**
     * REPORT_DISPLAY_COLUMN.id
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private Long              id;

    /**
     * REPORT_DISPLAY_COLUMN.table_name
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private String            tableName;

    /**
     * REPORT_DISPLAY_COLUMN.column_name
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private String            columnName;

    /**
     * REPORT_DISPLAY_COLUMN.column_display_name
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private String            columnDisplayName;

    /**
     * REPORT_DISPLAY_COLUMN.is_enum
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private Integer           isEnum;

    /**
     * REPORT_DISPLAY_COLUMN.enum_table_name
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private String            enumTableName;

    /**
     * REPORT_DISPLAY_COLUMN.sort
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private Integer           sort;

    /**
     * REPORT_DISPLAY_COLUMN.is_sum
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private Short             isSum;

    /**
     * REPORT_DISPLAY_COLUMN.column_data_type
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private String            columnDataType;

    /**
     * REPORT_DISPLAY_COLUMN.is_group_by
     * 
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    private Short             isGroupBy;

    /**
     * column REPORT_DISPLAY_COLUMN.id
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.id
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.id
     * 
     * @param id the value for REPORT_DISPLAY_COLUMN.id
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.table_name
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.table_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.table_name
     * 
     * @param tableName the value for REPORT_DISPLAY_COLUMN.table_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_name
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.column_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_name
     * 
     * @param columnName the value for REPORT_DISPLAY_COLUMN.column_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_display_name
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.column_display_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public String getColumnDisplayName() {
        return columnDisplayName;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_display_name
     * 
     * @param columnDisplayName the value for REPORT_DISPLAY_COLUMN.column_display_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setColumnDisplayName(String columnDisplayName) {
        this.columnDisplayName = columnDisplayName == null ? null : columnDisplayName.trim();
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_enum
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.is_enum
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public Integer getIsEnum() {
        return isEnum;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_enum
     * 
     * @param isEnum the value for REPORT_DISPLAY_COLUMN.is_enum
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setIsEnum(Integer isEnum) {
        this.isEnum = isEnum;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.enum_table_name
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.enum_table_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public String getEnumTableName() {
        return enumTableName;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.enum_table_name
     * 
     * @param enumTableName the value for REPORT_DISPLAY_COLUMN.enum_table_name
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setEnumTableName(String enumTableName) {
        this.enumTableName = enumTableName == null ? null : enumTableName.trim();
    }

    /**
     * column REPORT_DISPLAY_COLUMN.sort
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.sort
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.sort
     * 
     * @param sort the value for REPORT_DISPLAY_COLUMN.sort
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_sum
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.is_sum
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public Short getIsSum() {
        return isSum;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_sum
     * 
     * @param isSum the value for REPORT_DISPLAY_COLUMN.is_sum
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setIsSum(Short isSum) {
        this.isSum = isSum;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_data_type
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.column_data_type
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public String getColumnDataType() {
        return columnDataType;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.column_data_type
     * 
     * @param columnDataType the value for REPORT_DISPLAY_COLUMN.column_data_type
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setColumnDataType(String columnDataType) {
        this.columnDataType = columnDataType == null ? null : columnDataType.trim();
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_group_by
     * 
     * @return the value of REPORT_DISPLAY_COLUMN.is_group_by
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public Short getIsGroupBy() {
        return isGroupBy;
    }

    /**
     * column REPORT_DISPLAY_COLUMN.is_group_by
     * 
     * @param isGroupBy the value for REPORT_DISPLAY_COLUMN.is_group_by
     * @hujf Tue May 29 13:53:30 CST 2012
     */
    public void setIsGroupBy(Short isGroupBy) {
        this.isGroupBy = isGroupBy;
    }
}

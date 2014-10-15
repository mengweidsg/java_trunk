package com.esunny.satellite.report.entity;

public class ReportEnum {
    /**
     * REPORT_ENUM.id
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    private Long id;

    /**
     * REPORT_ENUM.table_name
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    private String tableName;

    /**
     * REPORT_ENUM.enum_code_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    private String enumCodeColumn;

    /**
     * REPORT_ENUM.enum_value_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    private String enumValueColumn;

    /**
     * column REPORT_ENUM.id
     *
     * @return the value of REPORT_ENUM.id
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * column REPORT_ENUM.id
     *
     * @param id the value for REPORT_ENUM.id
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * column REPORT_ENUM.table_name
     *
     * @return the value of REPORT_ENUM.table_name
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * column REPORT_ENUM.table_name
     *
     * @param tableName the value for REPORT_ENUM.table_name
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * column REPORT_ENUM.enum_code_column
     *
     * @return the value of REPORT_ENUM.enum_code_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public String getEnumCodeColumn() {
        return enumCodeColumn;
    }

    /**
     * column REPORT_ENUM.enum_code_column
     *
     * @param enumCodeColumn the value for REPORT_ENUM.enum_code_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public void setEnumCodeColumn(String enumCodeColumn) {
        this.enumCodeColumn = enumCodeColumn == null ? null : enumCodeColumn.trim();
    }

    /**
     * column REPORT_ENUM.enum_value_column
     *
     * @return the value of REPORT_ENUM.enum_value_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public String getEnumValueColumn() {
        return enumValueColumn;
    }

    /**
     * column REPORT_ENUM.enum_value_column
     *
     * @param enumValueColumn the value for REPORT_ENUM.enum_value_column
     *
     * @hujf  Thu Apr 19 11:15:19 CST 2012
     */
    public void setEnumValueColumn(String enumValueColumn) {
        this.enumValueColumn = enumValueColumn == null ? null : enumValueColumn.trim();
    }
}
/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.web.action.tool;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.bean.ReportItemInfo;
import com.esunny.satellite.report.service.intef.QueryInputSqlResultService;
import com.esunny.satellite.web.action.MenuBaseAction;

/**
 * 根据页面上输入的SQL，查询出结果
 * 
 * @author Jet Xu 2012-4-9 上午11:29:06
 */
public class QueryInputSqlResultAction extends MenuBaseAction {

    private static final long          serialVersionUID = 1830661969141565272L;

    private static final String[]      sqlKeys          = { ";", "delete", "update", "create", "drop", "insert",
            "alter", "truncate", "declare", "\\", "/"  };

    private String                     sql;

    private String                     sqlHidden;

    private List<Map<String, String>>  reportResult;

    private List<ReportItemInfo>       displayColumnInfo;

    private QueryInputSqlResultService queryInputSqlResultService;

    private String                     errorMessage;

    public String doSearch() {
        queryData(sql);
        return SUCCESS;
    }

    private void queryData(String sql) {
        if (StringUtil.isNotBlank(sql)) {
            String chkMsg = checkSql(sql);
            // 校验SQL
            if (StringUtil.isBlank(chkMsg)) {
                reportResult = queryInputSqlResultService.querySearchCondition("select * from (" + sql
                                                                               + ") where rownum <= 1000");
                if (null != reportResult && !reportResult.isEmpty() && null != reportResult.get(0)) {
                    displayColumnInfo = new ArrayList<ReportItemInfo>();
                    for (Map.Entry<String, String> entry : reportResult.get(0).entrySet()) {
                        ReportItemInfo itemInfo = new ReportItemInfo();
                        itemInfo.setIsEnum("0");
                        itemInfo.setColumnDisplayName(entry.getKey());
                        itemInfo.setColumnName(entry.getKey());
                        displayColumnInfo.add(itemInfo);
                    }
                }
            } else {
                errorMessage = chkMsg;
            }
        }
    }

    public String checkSql(String sql) {
        if (StringUtils.isBlank(sql)) {
            return "sql 不能为空";
        }
        sql = sql.trim().toLowerCase();
        // 精确查找
        for (int i = 0; i < sqlKeys.length; i++) {
            String[] sqlArr = sql.split(" ");
            for (String str : sqlArr) {
                if (str.trim().equalsIgnoreCase(sqlKeys[i])) {
                    return "sql 包含禁用的单词。比如：create，drop。。";
                }
            }
        }
        if (!sql.startsWith("select")) {
            return "请检查sql，只能处理select开头的查询语句。";
        }
        return null;
    }

    public void exportExcelFile() {
        String reportTableName = "temp_";
        queryData(sqlHidden);
        List<Map<String, String>> sourceDataList = reportResult;
        OutputStream outputStream = null;
        try {
            HttpServletResponse response = getResponse();

            String date = DateUtils.format(new Date(), "yyyyMMddHH");
            String tempfileName = reportTableName + date + ".xlsx";
            String filename = URLEncoder.encode(tempfileName, "UTF-8");

            response.setContentType("application/force-download");
            response.setHeader("Location", filename);
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);

            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet2 = wb.createSheet(reportTableName);
            XSSFRichTextString xssfValue;
            if (reportResult != null && displayColumnInfo != null) {
                // 打印表头
                XSSFRow row = sheet2.createRow(0);
                for (int i = 0; i < displayColumnInfo.size(); i++) {
                    XSSFCell cell = row.createCell(i);
                    xssfValue = new XSSFRichTextString(displayColumnInfo.get(i).getColumnDisplayName());
                    cell.setCellValue(xssfValue);
                }
                // 打印数据
                // 数据答应开始行。
                int dataStart = 1;

                int rowCount = sourceDataList.size();
                int columnCount = displayColumnInfo.size();
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    row = sheet2.createRow(rowIndex + dataStart);
                    for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                        XSSFCell cell = row.createCell(columnIndex);
                        String tempStr = sourceDataList.get(rowIndex).get(displayColumnInfo.get(columnIndex).getColumnName());
                        xssfValue = new XSSFRichTextString(tempStr);
                        cell.setCellValue(xssfValue);
                    }
                }
            }

            outputStream = response.getOutputStream();

            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the reportResult
     */
    public List<Map<String, String>> getReportResult() {
        return reportResult;
    }

    /**
     * @param reportResult the reportResult to set
     */
    public void setReportResult(List<Map<String, String>> reportResult) {
        this.reportResult = reportResult;
    }

    /**
     * @param queryInputSqlResultService the queryInputSqlResultService to set
     */
    public void setQueryInputSqlResultService(QueryInputSqlResultService queryInputSqlResultService) {
        this.queryInputSqlResultService = queryInputSqlResultService;
    }

    /**
     * @return the displayColumnInfo
     */
    public List<ReportItemInfo> getDisplayColumnInfo() {
        return displayColumnInfo;
    }

    /**
     * @param displayColumnInfo the displayColumnInfo to set
     */
    public void setDisplayColumnInfo(List<ReportItemInfo> displayColumnInfo) {
        this.displayColumnInfo = displayColumnInfo;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSqlHidden() {
        return sqlHidden;
    }

    public void setSqlHidden(String sqlHidden) {
        this.sqlHidden = sqlHidden;
    }

}

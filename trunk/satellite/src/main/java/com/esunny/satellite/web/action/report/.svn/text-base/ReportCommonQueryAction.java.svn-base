package com.esunny.satellite.web.action.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.page.Query;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.bean.ReportItemInfo;
import com.esunny.satellite.report.common.CommonUtils;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.constant.ReportConstant.IsEnum;
import com.esunny.satellite.report.dao.interf.ReportInfoPlusDao;
import com.esunny.satellite.report.entity.ReportDisplayColumn;
import com.esunny.satellite.report.entity.ReportInfoPlus;
import com.esunny.satellite.report.entity.ReportSearchCondition;
import com.esunny.satellite.report.service.intef.ReportCommonQueryService;
import com.esunny.satellite.web.action.MenuBaseAction;

/**
 * 类ReportCommonQueryAction.java的实现描述：报表公共检索 类实现描述
 * 
 * @author Administrator 2012-1-10 下午5:16:17
 */

public class ReportCommonQueryAction extends MenuBaseAction {

    /** 序列号 */
    private static final long                serialVersionUID = -543069460122513851L;
    /** 日志类 */
    private static final Logger              log              = Logger.getLogger("report_storage");
    private static final int                 MAX_EXCEL_NUMBER = 1000;
    private ReportCommonQueryService         reportCommonQueryService;
    private List<ReportSearchCondition>      searchCondition;
    private List<Map<String, String>>        reportResult;
    /** 当前页 */
    private Integer                          pageNo;
    private Integer                          pageSize;
    private Page                             query            = new Page();
    private List<ReportDisplayColumn>        displayColumnInfo;
    private Map<String, Map<String, String>> enumValueMap;
    private ReportInfoPlus                   reportInfoPlus;
    private ReportInfoPlusDao                reportInfoPlusDao;

    /**
     * 画面初始化
     * 
     * @return
     */
    // public String showInit() {
    // if (StringUtils.isBlank(reportTableName)) {
    // return SUCCESS;
    // }
    // Map<String, Object> params = new HashMap<String, Object>();
    // params.put(ReportSearchCondition.TABLE_NAME, reportTableName);
    // searchCondition = reportCommonQueryService.querySearchCondition(params);
    // displayColumnInfo = reportCommonQueryService.getDisplayColumnInfo(reportTableName);
    // reportInfoPlus = reportInfoPlusDao.selectInfoPlusByTableName(reportTableName);
    // // 设置排序方式
    // if (null != reportInfoPlus) {
    // Map<String, Object> pageValueMap = new HashMap<String, Object>();
    // if (StringUtil.isNotBlank(reportInfoPlus.getSortColumn())) {
    // pageValueMap.put("orderBy", reportInfoPlus.getSortColumn());
    // }
    // if (StringUtil.equals("1", reportInfoPlus.getSortDeriction())) {
    // pageValueMap.put("orderBy_2", "asc");
    // } else {
    // pageValueMap.put("orderBy_2", "desc");
    // }
    // // 页面检索条件的值
    // getRequest().setAttribute("pageValueMap", pageValueMap);
    // }
    // // 页面初始值
    // pageInit();
    // return SUCCESS;
    // }

    /**
     * 页面初始值
     */
    private void pageInit() {
        if (searchCondition != null) {
            List<String> enumNameList = new ArrayList<String>();
            for (ReportSearchCondition condition : searchCondition) {
                // 如果是下拉框
                if (null != condition.getInputType() && INPUT_TYPE.SELECT == condition.getInputType()) {
                    enumNameList.add(condition.getEnumTableName());
                }
            }
            enumValueMap = reportCommonQueryService.queryEnumValues(enumNameList);
        }
    }

    /**
     * 点击查询按钮
     * 
     * @return
     */
    public String queryList() {
        String reportTableName = this.getReportKeyWord("query_list_");
        if (StringUtils.isBlank(reportTableName)) {
            return SUCCESS;
        }
        if (StringUtil.equals(downloadFlag, "true")) {
            exportExcelFile();
        } else {
            queryData(false, reportTableName.toUpperCase());
        }
        return SUCCESS;
    }

    /**
     * 查询数据
     * 
     * @param flag true：excel导出按钮 false：查询按钮
     * @return
     */
    private Map<String, Object> queryData(boolean excelFlag, String reportTableName) {
        Map<String, Object> pageValueMap = new HashMap<String, Object>();
        List<ReportItemInfo> searchinfoList = new ArrayList<ReportItemInfo>();
        // 检索条件赋值
        searchCondition = reportCommonQueryService.querySearchCondition(reportTableName);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TABLE_NAME", reportTableName);
        for (ReportSearchCondition conditionList : searchCondition) {
            ReportItemInfo searchItemInfo = new ReportItemInfo();
            String temp = null;
            // 判断点击哪个按钮
            if (excelFlag) {
                // 导出按钮
                temp = getRequest().getParameter("excel_" + conditionList.getColumnName());
            } else {
                // 查询按钮
                temp = getRequest().getParameter(conditionList.getColumnName());
            }
            pageValueMap.put(conditionList.getColumnName(), temp);
            // 当下拉框为 选择全部
            if (StringUtils.isNotBlank(temp)) {
                if (null != conditionList.getInputType() && conditionList.getInputType() == INPUT_TYPE.DATE) {
                    params.put(conditionList.getColumnName(), CommonUtils.stringToDate(temp));
                } else {
                    params.put(conditionList.getColumnName(), temp);
                }
                searchItemInfo.setColumnName(conditionList.getColumnName());
                searchItemInfo.setInputType(conditionList.getInputType());
                boolean completeMatch = (null != conditionList.getIsCompleteMatch())
                                        && (conditionList.getIsCompleteMatch() == 1);
                searchItemInfo.setIsCompleteMatch(completeMatch);
                getRequest().setAttribute(conditionList.getColumnName(), temp);
                searchinfoList.add(searchItemInfo);
            }
        }
        // 要查看的列
        displayColumnInfo = reportCommonQueryService.getDisplayColumnInfo(reportTableName);
        reportInfoPlus = reportInfoPlusDao.selectInfoPlusByTableName(reportTableName);

        boolean needSearch = true;
        // 默认值为1
        if (pageNo == null) {
            needSearch = false;
            pageNo = 1;
        }
        query.setPageNo(pageNo);
        if (pageSize == null) {
            pageSize = Query.DEFAULT_PAGE_SIZE;
        }
        query.setPageSize(pageSize);
        // 排序
        String orderBy = getRequest().getParameter("orderBy");
        String ascDesc = getRequest().getParameter("orderBy_2");
        pageValueMap.put("orderBy", orderBy);
        pageValueMap.put("orderBy_2", ascDesc);
        if (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(ascDesc)) {
            query.setOrderByClause(orderBy + " " + ascDesc);
        }
        if (needSearch) {
            reportResult = reportCommonQueryService.queryReportListValue(searchinfoList, params, displayColumnInfo,
                                                                         query, false);
        }
        if (CollectionUtils.isEmpty(displayColumnInfo) && CollectionUtils.isNotEmpty(reportResult)) {
            displayColumnInfo = new ArrayList<ReportDisplayColumn>();
            Map<String, String> dataMap = reportResult.get(0);
            if (null != dataMap) {
                for (Map.Entry<String, String> reportItemInfo : dataMap.entrySet()) {
                    if (null == reportItemInfo) {
                        continue;
                    }
                    ReportDisplayColumn itemInfo = new ReportDisplayColumn();
                    itemInfo.setColumnDisplayName(reportItemInfo.getKey());
                    itemInfo.setColumnName(reportItemInfo.getKey());
                    itemInfo.setIsEnum(0);
                    displayColumnInfo.add(itemInfo);
                }
            }
        }
        // 页面检索条件的值
        getRequest().setAttribute("pageValueMap", pageValueMap);
        // 页面初始值
        pageInit();
        return params;
    }

    /**
     * 向客户端下载文件，弹出下载框.
     */
    public void exportExcelFile() {
        String reportTableName = this.getReportKeyWord("query_list_");
        // 得到检索条件的值
        query.setPageNo(1);
        pageSize = MAX_EXCEL_NUMBER;
        Map<String, Object> params = queryData(true, reportTableName);
        if (displayColumnInfo == null || reportResult == null || searchCondition == null || params == null) {
            return;
        }
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

            XSSFRow row = sheet2.createRow(0);
            XSSFCell conditionTitle = row.createCell(0);
            xssfValue = new XSSFRichTextString("检索条件：");
            conditionTitle.setCellValue(xssfValue);
            // 打印 检索条件
            for (int i = 0; i < searchCondition.size(); i++) {
                XSSFCell cell = row.createCell(i + 1);
                String columnName = searchCondition.get(i).getColumnName();
                xssfValue = new XSSFRichTextString(searchCondition.get(i).getColumnDisplayName() + ":"
                                                   + CommonUtils.delNull(params.get(columnName)));
                cell.setCellValue(xssfValue);
            }
            // 打印排序
            row = sheet2.createRow(1);
            XSSFCell orderby = row.createCell(0);
            xssfValue = new XSSFRichTextString("排序：");
            orderby.setCellValue(xssfValue);

            orderby = row.createCell(1);
            xssfValue = new XSSFRichTextString(query.getOrderByClause());
            orderby.setCellValue(xssfValue);

            // 打印表头
            row = sheet2.createRow(3);
            for (int i = 0; i < displayColumnInfo.size(); i++) {
                XSSFCell cell = row.createCell(i);
                xssfValue = new XSSFRichTextString(displayColumnInfo.get(i).getColumnDisplayName());
                cell.setCellValue(xssfValue);
            }
            // 打印数据
            // 数据答应开始行。
            int dataStart = 4;

            int rowCount = sourceDataList.size();
            int columnCount = displayColumnInfo.size();
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                row = sheet2.createRow(rowIndex + dataStart);
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    XSSFCell cell = row.createCell(columnIndex);
                    String tempStr = sourceDataList.get(rowIndex).get(displayColumnInfo.get(columnIndex).getColumnName());
                    // 判断是否为枚举类
                    if (String.valueOf(IsEnum.YES).equals(displayColumnInfo.get(columnIndex).getIsEnum())) {
                        String value = enumValueMap.get(displayColumnInfo.get(columnIndex).getEnumTableName()).get(tempStr);
                        if (StringUtils.isBlank(value)) {
                            xssfValue = new XSSFRichTextString(tempStr);
                        } else {
                            xssfValue = new XSSFRichTextString(value);
                        }

                    } else {
                        xssfValue = new XSSFRichTextString(tempStr);
                    }
                    cell.setCellValue(xssfValue);
                }
            }
            outputStream = response.getOutputStream();

            wb.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

    }

    /**
     * @return the searchCondition
     */
    public List<ReportSearchCondition> getSearchCondition() {
        return searchCondition;
    }

    /**
     * @param searchCondition the searchCondition to set
     */
    public void setSearchCondition(List<ReportSearchCondition> searchCondition) {
        this.searchCondition = searchCondition;
    }

    public List<Map<String, String>> getReportResult() {
        return reportResult;
    }

    public void setReportResult(List<Map<String, String>> reportResult) {
        this.reportResult = reportResult;
    }

    /**
     * @return the reportCommonQueryService
     */
    public ReportCommonQueryService getReportCommonQueryService() {
        return reportCommonQueryService;
    }

    /**
     * @param reportCommonQueryService the reportCommonQueryService to set
     */
    public void setReportCommonQueryService(ReportCommonQueryService reportCommonQueryService) {
        this.reportCommonQueryService = reportCommonQueryService;
    }

    /**
     * @return the displayColumnInfo
     */
    public List<ReportDisplayColumn> getDisplayColumnInfo() {
        return displayColumnInfo;
    }

    /**
     * @param displayColumnInfo the displayColumnInfo to set
     */
    public void setDisplayColumnInfo(List<ReportDisplayColumn> displayColumnInfo) {
        this.displayColumnInfo = displayColumnInfo;
    }

    /**
     * @return the pageNo
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the query
     */
    public Page getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(Page query) {
        this.query = query;
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

    /**
     * @return the enumValueMap
     */
    public Map<String, Map<String, String>> getEnumValueMap() {
        return enumValueMap;
    }

    /**
     * @param enumValueMap the enumValueMap to set
     */
    public void setEnumValueMap(Map<String, Map<String, String>> enumValueMap) {
        this.enumValueMap = enumValueMap;
    }

    /**
     * @param reportInfoPlusDao the reportInfoPlusDao to set
     */
    public void setReportInfoPlusDao(ReportInfoPlusDao reportInfoPlusDao) {
        this.reportInfoPlusDao = reportInfoPlusDao;
    }

    /**
     * @return the reportInfoPlus
     */
    public ReportInfoPlus getReportInfoPlus() {
        return reportInfoPlus;
    }

    /**
     * @param reportInfoPlus the reportInfoPlus to set
     */
    public void setReportInfoPlus(ReportInfoPlus reportInfoPlus) {
        this.reportInfoPlus = reportInfoPlus;
    }

}

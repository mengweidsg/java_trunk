package com.esunny.satellite.web.action.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.cglib.beans.BeanCopier;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.common.ReportSqlException;
import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.constant.ReportConstant.IsEnum;
import com.esunny.satellite.report.dao.interf.AllTabColumnsDao;
import com.esunny.satellite.report.dao.interf.ReportEnumDao;
import com.esunny.satellite.report.dao.interf.ReportInfoPlusDao;
import com.esunny.satellite.report.dao.interf.ReportSearchConditionDao;
import com.esunny.satellite.report.entity.AllTabColumns;
import com.esunny.satellite.report.entity.ReportDisplayColumn;
import com.esunny.satellite.report.entity.ReportEnum;
import com.esunny.satellite.report.entity.ReportInfoPlus;
import com.esunny.satellite.report.entity.ReportSearchCondition;
import com.esunny.satellite.report.service.intef.ReportCommonQueryService;
import com.esunny.satellite.report.service.intef.ReportRealTimeQueryService;
import com.esunny.satellite.util.SatelliteUtil;
import com.esunny.satellite.vo.ReportSearchConditionVo;
import com.esunny.satellite.vo.SearchConditonVo;
import com.esunny.satellite.web.action.MenuBaseAction;

/**
 * 类ReportCommonQueryAction.java的实现描述：报表公共检索 类实现描述
 * 
 * @author Administrator 2012-1-10 下午5:16:17
 */

public class ReportRealTimeQueryAction extends MenuBaseAction {

    /** 序列号 */
    private static final long                serialVersionUID        = -543069460122513851L;
    /** 日志类 */
    private static final Logger              log                     = Logger.getLogger("report_storage");
    private static final int                 MAX_EXCEL_NUMBER        = 1000;
    private static final String              DEFAULT_SQL             = "select $condition$ from $tableName$ $where$ $orderBy$";
    private List<Map<String, String>>        reportResult;
    private ReportInfoPlus                   reportInfoPlus;
    private ReportInfoPlusDao                reportInfoPlusDao;
    private ReportSearchConditionDao         reportSearchConditionDao;
    private AllTabColumnsDao                 allTabColumnsDao;
    private ReportEnumDao                    reportEnumDao;
    private ReportRealTimeQueryService       reportRealTimeQueryService;
    private ReportCommonQueryService         reportCommonQueryService;
    private List<ReportDisplayColumn>        displayColumnInfo;
    private SearchConditonVo                 searchConditonVo;
    private Map<String, Map<String, String>> enumMap;
    /** 将VO转换成Entity的拷贝 **/
    private static final BeanCopier          searchConditionVoCopier = BeanCopier.create(ReportSearchCondition.class,
                                                                                         ReportSearchConditionVo.class,
                                                                                         false);
    private Page                             query;

    /**
     * 画面初始化
     * 
     * @return
     */
    public String showInit() {
        queryData();
        return SUCCESS;
    }

    /**
     * 检索数据（并获取结果列的列表）
     * 
     * @param isInit 是否初始化
     */
    private void queryData() {
        String reportTableName = this.getReportKeyWord("query_real_time_");
        if (StringUtil.isBlank(reportTableName)) {
            return;
        }
        boolean isInit = false;
        if (null == query) {
            query = new Page();
            isInit = true;
        }
        try {
            List<ReportSearchCondition> conditionList = reportSearchConditionDao.queryConditionListByTableName(reportTableName);
            searchConditonVo = new SearchConditonVo();
            if (CollectionUtils.isNotEmpty(conditionList)) {
                for (ReportSearchCondition condition : conditionList) {
                    if (null == condition || null == condition.getInputType()) {
                        continue;
                    }
                    // 如果为第一个链接控件
                    if (condition.getInputType() == INPUT_TYPE.LI_LINK && null == searchConditonVo.getLiLinkInfo()) {
                        searchConditonVo.setLiLinkInfo(fillSearchConditionVo(condition, isInit));
                    }
                    // 如果为第一个双时间控件
                    else if (condition.getInputType() == INPUT_TYPE.DOUBLE_DATE
                             && null == searchConditonVo.getDoubleDateInfo()) {
                        searchConditonVo.setDoubleDateInfo(fillSearchConditionVo(condition, isInit));
                    }
                    // 其他情况
                    else {
                        List<ReportSearchConditionVo> detailList = searchConditonVo.getDetailList();
                        if (null == detailList) {
                            detailList = new ArrayList<ReportSearchConditionVo>();
                            searchConditonVo.setDetailList(detailList);
                        }
                        detailList.add(fillSearchConditionVo(condition, isInit));
                    }
                }
            }
            List<ReportEnum> reportEnumList = reportEnumDao.queryAllReportEnum();
            if (CollectionUtils.isNotEmpty(reportEnumList)) {
                enumMap = new HashMap<String, Map<String, String>>();
                for (ReportEnum reportEnum : reportEnumList) {
                    if (null == reportEnum || StringUtil.isBlank(reportEnum.getTableName())) {
                        continue;
                    }
                    Map<String, String> valueMap = enumMap.get(reportEnum.getTableName());
                    if (null == valueMap) {
                        valueMap = new LinkedHashMap<String, String>();
                        enumMap.put(reportEnum.getTableName(), valueMap);
                    }
                    valueMap.put(reportEnum.getEnumCodeColumn(), reportEnum.getEnumValueColumn());
                }
            }
            // 要查看的列
            displayColumnInfo = reportCommonQueryService.getDisplayColumnInfo(reportTableName);
            reportInfoPlus = reportInfoPlusDao.selectInfoPlusByTableName(reportTableName);
            String sql = getSelectSql(reportInfoPlus, reportTableName);
            if (null != reportInfoPlus && null != reportInfoPlus.getPageSize()) {
                query.setPageSize(reportInfoPlus.getPageSize().intValue());
            }
            if (!isInit) {
                reportResult = reportRealTimeQueryService.queryRealTimeList(sql, searchConditonVo, query);
            }
            if (CollectionUtils.isEmpty(displayColumnInfo)) {
                if (CollectionUtils.isNotEmpty(reportResult)) {
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
                } else {
                    displayColumnInfo = fetchReportItemInfoListFromSql(sql);
                }
            }
        } catch (ReportSqlException e) {
            log.error("", e);
        }
    }

    private String getSelectCondition() {
        if (CollectionUtils.isEmpty(displayColumnInfo)) {
            return " * ";
        }
        StringBuilder sqlCondition = new StringBuilder();
        for (ReportDisplayColumn sInfo : displayColumnInfo) {
            if (StringUtils.isNotBlank(sqlCondition.toString())) {
                sqlCondition.append(",");
            }
            if ("DATE".equals(sInfo.getColumnDataType())) {
                sqlCondition.append("to_char(").append(sInfo.getColumnName()).append(",'yyyy-mm-dd') as ").append(sInfo.getColumnName());
            } else {
                sqlCondition.append(sInfo.getColumnName());
            }
        }
        return sqlCondition.toString();
    }

    protected String getSelectSql(ReportInfoPlus reportInfoPlus, String reportTableName) {
        if (null == reportInfoPlus && StringUtil.isEmpty(reportTableName)) {
            return "select * from dual";
        }
        String tableName = SatelliteUtil.filterTableNameSuffix(reportTableName);
        String orderBy = "";
        if (null != reportInfoPlus) {
            if (StringUtil.isNotEmpty(reportInfoPlus.getSelectSql())) {
                // 如果SQL存在，则直接使用SQL
                return reportInfoPlus.getSelectSql();
            }
            if (StringUtil.isNotEmpty(reportInfoPlus.getSortColumn())) {
                orderBy = " order by " + reportInfoPlus.getSortColumn();
                if (StringUtil.isNotEmpty(reportInfoPlus.getSortDeriction())) {
                    orderBy = orderBy + " " + reportInfoPlus.getSortDeriction();
                }
            }
        } else {
            // 如果该表存在time字段，则按照time字段倒序
            AllTabColumns colInfo = allTabColumnsDao.selectColInfoByTableColumn(tableName, "time");
            if (null != colInfo) {
                orderBy = " order by time desc ";
            }
        }
        return DEFAULT_SQL.replace("$condition$", getSelectCondition()).replace("$tableName$", tableName).replace("$orderBy$",
                                                                                                                  orderBy);
    }

    /**
     * 填充检索条件VO（把页面输入值设置进来）
     * 
     * @param condition 检索条件
     * @return
     */
    private ReportSearchConditionVo fillSearchConditionVo(ReportSearchCondition condition, boolean isInit) {
        if (null == condition) {
            return null;
        }
        ReportSearchConditionVo vo = new ReportSearchConditionVo();
        searchConditionVoCopier.copy(condition, vo, null);
        if (!isInit) {
            if (condition.getInputType() == INPUT_TYPE.DOUBLE_DATE) {
                String from = getRequest().getParameter(condition.getColumnName() + "From");
                String to = getRequest().getParameter(condition.getColumnName() + "To");
                List<String> dateList = new ArrayList<String>();
                dateList.add(from);
                dateList.add(to);
                vo.setValueList(dateList);
            } else {
                vo.setValue(getRequest().getParameter(condition.getColumnName()));
            }
        }
        return vo;
    }

    /**
     * 从sql中获取结果列的列表
     * 
     * @param sql sql文
     * @return 结果列的列表
     */
    private List<ReportDisplayColumn> fetchReportItemInfoListFromSql(String sql) {
        List<ReportDisplayColumn> resultList = new ArrayList<ReportDisplayColumn>();
        if (StringUtil.isBlank(sql)) {
            return resultList;
        }
        //sql = sql.toLowerCase();
        int startIdx = sql.indexOf("select");
        int endIdx = sql.indexOf("from");
        if (0 <= startIdx && startIdx < endIdx) {
            String condStr = sql.substring(startIdx + 6, endIdx);
            String[] condArr = condStr.split(",");
            if (condArr != null && condArr.length > 0) {
                int bracketsIdx = 0;
                for (String str : condArr) {
                    if (StringUtil.isBlank(str)) {
                        continue;
                    }
                    for (char chr : str.toCharArray()) {
                        switch (chr) {
                            case '(':
                                bracketsIdx++;
                                break;
                            case ')':
                                if (bracketsIdx > 0) {
                                    bracketsIdx--;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    // 如果逗号是括号中的，则这个不是一列
                    if (bracketsIdx > 0) {
                        continue;
                    }
                    String columnName = str;
                    int asIdx = str.indexOf("as");
                    if (asIdx >= 0) {
                        columnName = str.substring(asIdx + 2);
                    }
                    columnName = columnName.trim();
                    ReportDisplayColumn itemInfo = new ReportDisplayColumn();
                    itemInfo.setColumnDisplayName(columnName);
                    itemInfo.setColumnName(columnName);
                    itemInfo.setIsEnum(0);
                    resultList.add(itemInfo);
                }
            }
        }
        return resultList;
    }

    /**
     * 向客户端下载文件，弹出下载框.
     */
    public void exportExcelFile() {
        String reportTableName = this.getReportKeyWord("query_real_time_");
        // 得到检索条件的值
        query.setPageNo(1);
        query.setPageSize(MAX_EXCEL_NUMBER);
        queryData();
        if (displayColumnInfo == null || reportResult == null || searchConditonVo == null) {
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
            int j = 1;
            // 打印 检索条件
            if (null != searchConditonVo.getDoubleDateInfo()) {
                XSSFCell cell = row.createCell(j);
                xssfValue = new XSSFRichTextString(searchConditonVo.getDoubleDateInfo().getColumnDisplayName() + ":"
                                                   + searchConditonVo.getDoubleDateInfo().getValue());
                cell.setCellValue(xssfValue);
                j++;
            }
            if (null != searchConditonVo.getLiLinkInfo()) {
                XSSFCell cell = row.createCell(j);
                xssfValue = new XSSFRichTextString(searchConditonVo.getLiLinkInfo().getColumnDisplayName() + ":"
                                                   + searchConditonVo.getLiLinkInfo().getValue());
                cell.setCellValue(xssfValue);
                j++;
            }
            if (CollectionUtils.isNotEmpty(searchConditonVo.getDetailList())) {
                for (ReportSearchConditionVo condVo : searchConditonVo.getDetailList()) {
                    XSSFCell cell = row.createCell(j);
                    // 判断是否为枚举类
                    if (null != condVo.getInputType() && condVo.getInputType() == INPUT_TYPE.SELECT) {
                        xssfValue = new XSSFRichTextString(condVo.getColumnDisplayName() + ":"
                                                           + getEnumValue(condVo.getEnumTableName(), condVo.getValue()));
                    } else {
                        xssfValue = new XSSFRichTextString(condVo.getColumnDisplayName() + ":" + condVo.getValue());
                    }
                    cell.setCellValue(xssfValue);
                    j++;
                }
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
                        String value = enumMap.get(displayColumnInfo.get(columnIndex).getEnumTableName()).get(tempStr);
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

    private String getEnumValue(String enumTableName, String key) {
        String value = "";
        if (null != enumMap) {
            Map<String, String> enumValueMap = enumMap.get(enumTableName);
            if (null != enumValueMap) {
                value = enumValueMap.get(key);
            }
        }
        return StringUtil.trimToEmpty(value);
    }

    /**
     * 点击查询按钮
     * 
     * @return
     */
    public String queryRealTimeList() {
        if (StringUtil.equals(downloadFlag, "true")) {
            exportExcelFile();
        } else {
            queryData();
        }
        return SUCCESS;
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

    /**
     * @return the reportInfoPlusDao
     */
    public ReportInfoPlusDao getReportInfoPlusDao() {
        return reportInfoPlusDao;
    }

    /**
     * @param reportInfoPlusDao the reportInfoPlusDao to set
     */
    public void setReportInfoPlusDao(ReportInfoPlusDao reportInfoPlusDao) {
        this.reportInfoPlusDao = reportInfoPlusDao;
    }

    /**
     * @param reportRealTimeQueryService the reportRealTimeQueryService to set
     */
    public void setReportRealTimeQueryService(ReportRealTimeQueryService reportRealTimeQueryService) {
        this.reportRealTimeQueryService = reportRealTimeQueryService;
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
     * @param reportSearchConditionDao the reportSearchConditionDao to set
     */
    public void setReportSearchConditionDao(ReportSearchConditionDao reportSearchConditionDao) {
        this.reportSearchConditionDao = reportSearchConditionDao;
    }

    /**
     * @return the searchConditonVo
     */
    public SearchConditonVo getSearchConditonVo() {
        return searchConditonVo;
    }

    /**
     * @param searchConditonVo the searchConditonVo to set
     */
    public void setSearchConditonVo(SearchConditonVo searchConditonVo) {
        this.searchConditonVo = searchConditonVo;
    }

    /**
     * @return the reportSearchConditionDao
     */
    public ReportSearchConditionDao getReportSearchConditionDao() {
        return reportSearchConditionDao;
    }

    /**
     * @return the reportRealTimeQueryService
     */
    public ReportRealTimeQueryService getReportRealTimeQueryService() {
        return reportRealTimeQueryService;
    }

    /**
     * @return the reportEnumDao
     */
    public ReportEnumDao getReportEnumDao() {
        return reportEnumDao;
    }

    /**
     * @param reportEnumDao the reportEnumDao to set
     */
    public void setReportEnumDao(ReportEnumDao reportEnumDao) {
        this.reportEnumDao = reportEnumDao;
    }

    /**
     * @return the enumMap
     */
    public Map<String, Map<String, String>> getEnumMap() {
        return enumMap;
    }

    /**
     * @param enumMap the enumMap to set
     */
    public void setEnumMap(Map<String, Map<String, String>> enumMap) {
        this.enumMap = enumMap;
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
     * @param allTabColumnsDao the allTabColumnsDao to set
     */
    public void setAllTabColumnsDao(AllTabColumnsDao allTabColumnsDao) {
        this.allTabColumnsDao = allTabColumnsDao;
    }

    /**
     * @param reportCommonQueryService the reportCommonQueryService to set
     */
    public void setReportCommonQueryService(ReportCommonQueryService reportCommonQueryService) {
        this.reportCommonQueryService = reportCommonQueryService;
    }

}

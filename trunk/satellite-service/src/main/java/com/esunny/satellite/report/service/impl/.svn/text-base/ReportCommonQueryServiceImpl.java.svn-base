package com.esunny.satellite.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.esunny.satellite.report.bean.ReportItemInfo;
import com.esunny.satellite.report.common.CommonUtils;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.common.ReportSqlException;
import com.esunny.satellite.report.constant.ReportConstant;
import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.dao.CommonDao;
import com.esunny.satellite.report.dao.interf.ReportDisplayColumnDao;
import com.esunny.satellite.report.dao.interf.ReportEnumDao;
import com.esunny.satellite.report.dao.interf.ReportSearchConditionDao;
import com.esunny.satellite.report.entity.ReportDisplayColumn;
import com.esunny.satellite.report.entity.ReportEnum;
import com.esunny.satellite.report.entity.ReportSearchCondition;
import com.esunny.satellite.report.service.intef.ReportCommonQueryService;
import com.esunny.satellite.report.service.intef.ReportService;
import com.esunny.satellite.util.SatelliteUtil;

/**
 * 类ReportCommonQueryServiceImpl.java的实现描述：共通报表查询 类实现描述
 * 
 * @author Administrator 2012-1-11 上午11:17:19
 */
public class ReportCommonQueryServiceImpl implements ReportCommonQueryService {

    private CommonDao                commonDao;

    // private static final String select_sql = "select $condition$ from $table_name$ $where$ $groupby$";

    private static final String      SELECT_SQL_BY_PAGE = " select * from (select t.*, rownum rn from ($sql$) t where rownum <= :ENDROW) where rn > :STARTROW ";

    /** 日志类 */
    private static final Logger      log                = Logger.getLogger("report_storage");

    private ReportService            reportService;

    private ReportSearchConditionDao reportSearchConditionDao;

    private ReportDisplayColumnDao   reportDisplayColumnDao;

    private ReportEnumDao            reportEnumDao;

    /**
     * 查找报表检索条件(params 这个map的 key与表的列名一致)
     * 
     * @param tableName 对应的表名
     * @param params 参数
     * @throws ReportSqlException
     * @throws Exception
     */
    public List<ReportSearchCondition> querySearchCondition(String tableName) {
        return reportSearchConditionDao.queryConditionListByTableName(tableName);
    }

    /**
     * 取报表的值（SUM）
     */
    public List<Map<String, String>> queryReportListValue(List<ReportItemInfo> searchinfoList,
                                                          Map<String, Object> params,
                                                          List<ReportDisplayColumn> displayInfoList, Page page,
                                                          boolean isSum) {
        try {
            if (isSum) {
                return queryResultWithSum(searchinfoList, params, displayInfoList, page);
            } else {
                int rscount = 0;
                StringBuilder sqlCount = new StringBuilder();
                String sqlWhere = fetchWhere(searchinfoList, params);
                StringBuilder sqlCondition = new StringBuilder();
                sqlCount.append("select count(1) from ").append(SatelliteUtil.filterTableNameSuffix(params.get("TABLE_NAME")));
                sqlCount.append(sqlWhere);
                rscount = commonDao.queryForIntWithSql(sqlCount.toString(), params);
                page.doPage(rscount);
                List<String> displayColumnList = new ArrayList<String>();
                StringBuilder displayColumnSql = new StringBuilder();
                for (ReportDisplayColumn sInfo : displayInfoList) {
                    displayColumnList.add(sInfo.getColumnName());
                    if (StringUtils.isNotBlank(sqlCondition.toString())) {
                        sqlCondition.append(",");
                    }
                    if ("DATE".equals(sInfo.getColumnDataType())) {
                        sqlCondition.append("to_char(").append(sInfo.getColumnName()).append(",'yyyy-mm-dd') as ").append(sInfo.getColumnName());
                    } else {
                        sqlCondition.append(sInfo.getColumnName());
                    }
                }
                displayColumnSql.append("select ").append(sqlCondition).append(" from ").append(SatelliteUtil.filterTableNameSuffix(params.get("TABLE_NAME"))).append(sqlWhere);
                // 如果存在排序则加入SQL
                if (StringUtils.isNotBlank(page.getOrderByClause())) {
                    displayColumnSql.append(" order by ").append(page.getOrderByClause());
                }
                // 分页SQL
                String sql = SELECT_SQL_BY_PAGE.replace("$sql$", displayColumnSql.toString());
                // 页数的控制
                params.put("STARTROW", page.getStartRow());
                params.put("ENDROW", page.getEndRow());
                List<Map<String, String>> queryrs = commonDao.queryForListWithSql(sql, params, displayColumnList);
                resetResult(queryrs, displayInfoList);
                return queryrs;
            }
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return new ArrayList<Map<String, String>>();
        }
    }

    /**
     * 处理结果中的特殊情况。1.number型0.01显示为.01的问题
     * 
     * @param queryrs
     * @param displayInfoList
     */
    private void resetResult(List<Map<String, String>> queryrs, List<ReportDisplayColumn> displayInfoList) {
        if (null == queryrs || null == displayInfoList) {
            return;
        }
        for (ReportDisplayColumn sInfo : displayInfoList) {
            if ("NUMBER".equals(sInfo.getColumnDataType())) {
                for (Map<String, String> map : queryrs) {
                    if (null == map) {
                        continue;
                    }
                    String value = map.get(sInfo.getColumnName());
                    // 如果数值的列的值为【.】开始，则前面补0
                    if (StringUtils.isNotBlank(value) && value.startsWith(".")) {
                        map.put(sInfo.getColumnName(), "0" + value);
                    }
                }
            }
        }
    }

    /**
     * 获取结果并合并值
     * 
     * @param searchinfoList
     * @param params
     * @param displayInfoList
     * @param page
     * @return
     * @throws ReportSqlException
     */
    private List<Map<String, String>> queryResultWithSum(List<ReportItemInfo> searchinfoList,
                                                         Map<String, Object> params,
                                                         List<ReportDisplayColumn> displayInfoList, Page page)
                                                                                                              throws ReportSqlException {
        int rscount = 0;
        StringBuilder dispayColumnsSql = new StringBuilder();
        List<String> displayColumnList = new ArrayList<String>();
        StringBuilder groupByColumns = new StringBuilder();
        StringBuilder sumColumns = new StringBuilder();
        for (ReportDisplayColumn sInfo : displayInfoList) {
            // 如果不为全部，添加到select条件和group条件中
            if (params.containsKey(sInfo.getColumnName())
                && ReportConstant.ESUNNY_ALL.equals(params.get(sInfo.getColumnName()))) {
                if (StringUtils.isNotBlank(sumColumns.toString())) {
                    sumColumns.append(",");
                }
                sumColumns.append("'").append(ReportConstant.ESUNNY_ALL).append("' as ").append(sInfo.getColumnName());
            } else {
                if (!"NUMBER".equals(sInfo.getColumnDataType()) || params.containsKey(sInfo.getColumnName())) {
                    if (StringUtils.isNotBlank(groupByColumns.toString())) {
                        groupByColumns.append(",");
                    }
                    if (StringUtils.isNotBlank(sumColumns.toString())) {
                        sumColumns.append(",");
                    }
                    if ("DATE".equals(sInfo.getColumnDataType())) {
                        sumColumns.append("to_char(").append(sInfo.getColumnName()).append(",'yyyy-mm-dd') as ").append(sInfo.getColumnName());
                    } else {
                        sumColumns.append(sInfo.getColumnName());
                    }
                    groupByColumns.append(sInfo.getColumnName());
                } else {
                    if (StringUtils.isNotBlank(sumColumns.toString())) {
                        sumColumns.append(",");
                    }
                    sumColumns.append("sum(").append(sInfo.getColumnName()).append(") as ").append(sInfo.getColumnName());
                }

            }
            displayColumnList.add(sInfo.getColumnName());
        }
        dispayColumnsSql.append("select ");
        dispayColumnsSql.append(sumColumns);
        dispayColumnsSql.append(" from ").append(SatelliteUtil.filterTableNameSuffix(params.get("TABLE_NAME")));
        dispayColumnsSql.append(fetchWhere(searchinfoList, params));
        dispayColumnsSql.append(" group by ").append(groupByColumns);

        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append("select count(1) from ( ").append(dispayColumnsSql).append(")");
        rscount = commonDao.queryForIntWithSql(sqlCount.toString(), params);
        // 设置分页各参数
        page.doPage(rscount);
        // 如果存在排序则加入SQL
        if (StringUtils.isNotBlank(page.getOrderByClause())) {
            dispayColumnsSql.append(" order by ").append(page.getOrderByClause());
        }
        // 分页SQL
        String sql = SELECT_SQL_BY_PAGE.replace("$sql$", dispayColumnsSql.toString());
        // 页数的控制
        params.put("STARTROW", page.getStartRow());
        params.put("ENDROW", page.getEndRow());
        List<Map<String, String>> queryrs = commonDao.queryForListWithSql(sql, params, displayColumnList);
        resetResult(queryrs, displayInfoList);
        return queryrs;
    }

    public List<ReportDisplayColumn> getDisplayColumnInfo(String tableName) {
        List<ReportDisplayColumn> resultList = reportDisplayColumnDao.queryColumnListByTableName(tableName);
        if (CollectionUtils.isEmpty(resultList)) {
            List<Map<String, String>> colList = reportService.queryTableCloumns(tableName);
            if (CollectionUtils.isNotEmpty(colList)) {
                for (int i = 0; i < colList.size(); i++) {
                    Map<String, String> colMap = colList.get(i);
                    if (null == colMap) {
                        continue;
                    }
                    ReportDisplayColumn reportItemInfo = new ReportDisplayColumn();
                    reportItemInfo.setColumnName(colMap.get("COLUMN_NAME"));
                    reportItemInfo.setColumnDisplayName(colMap.get("COLUMN_NAME"));
                    reportItemInfo.setColumnDataType(colMap.get("DATA_TYPE"));
                    reportItemInfo.setIsEnum(0);
                    resultList.add(reportItemInfo);
                }
            }
        }

        return resultList;
    }

    /**
     * 查看枚举类信息
     * 
     * @param enumName
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> queryEnumValues(List<String> enumNameList) {
        Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
        if (CollectionUtils.isEmpty(enumNameList)) {
            return enumMap;
        }
        List<ReportEnum> enumList = reportEnumDao.queryReportByEnumNameList(enumNameList);
        for (ReportEnum reportEnum : enumList) {
            Map<String, String> subMap = enumMap.get(reportEnum.getTableName());
            if (null == subMap) {
                subMap = new LinkedHashMap<String, String>();
                enumMap.put(reportEnum.getTableName(), subMap);
            }
            subMap.put(reportEnum.getEnumCodeColumn(), reportEnum.getEnumValueColumn());
        }
        return enumMap;
    }

    /**
     * 拼接where条件，如果为部分匹配字段，则会重新设置paramMap中的值（前后添加%）
     * 
     * @param searchinfoList
     * @param paramMap
     * @param tableName
     * @return
     */
    private String fetchWhere(List<ReportItemInfo> searchinfoList, Map<String, Object> paramMap) {
        if (CommonUtils.isEmpty(searchinfoList) || CommonUtils.isEmpty(paramMap)) {
            return "";
        }
        StringBuilder sWhere = new StringBuilder();
        for (ReportItemInfo itemInfo : searchinfoList) {
            if (null == itemInfo || null == itemInfo.getInputType() || StringUtils.isBlank(itemInfo.getColumnName())) {
                continue;
            }
            Object itemValue = paramMap.get(itemInfo.getColumnName());

            if (null == itemValue || StringUtils.isBlank(String.valueOf(itemValue))
                || ReportConstant.ESUNNY_ALL.equals(itemValue)) {
                continue;
            }
            if (sWhere.length() > 0) {
                sWhere.append(" and ");
            } else {
                sWhere.append(" where ");
            }
            switch (itemInfo.getInputType()) {
                case INPUT_TYPE.TEXT:
                    // 如果为完全匹配
                    if (null != itemInfo.getIsCompleteMatch() && itemInfo.getIsCompleteMatch().booleanValue()) {
                        sWhere.append(itemInfo.getColumnName()).append(" = :").append(itemInfo.getColumnName());
                    }
                    // 如果为部分匹配
                    else {
                        sWhere.append(itemInfo.getColumnName()).append(" like :").append(itemInfo.getColumnName());
                        // 重新设置paramMap中的值（前后添加%）
                        paramMap.put(itemInfo.getColumnName(), "%" + itemValue + "%");
                    }
                    break;
                case INPUT_TYPE.SELECT:
                    sWhere.append(itemInfo.getColumnName()).append(" = :").append(itemInfo.getColumnName());
                    break;
                default:
                    sWhere.append(itemInfo.getColumnName()).append(" = :").append(itemInfo.getColumnName());
                    break;
            }
        }
        return sWhere.toString();
    }

    /**
     * @param commonDao the commonDao to set
     */
    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    /**
     * @param reportService the reportService to set
     */
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * @param reportSearchConditionDao the reportSearchConditionDao to set
     */
    public void setReportSearchConditionDao(ReportSearchConditionDao reportSearchConditionDao) {
        this.reportSearchConditionDao = reportSearchConditionDao;
    }

    /**
     * @param reportEnumDao the reportEnumDao to set
     */
    public void setReportEnumDao(ReportEnumDao reportEnumDao) {
        this.reportEnumDao = reportEnumDao;
    }

    /**
     * @param reportDisplayColumnDao the reportDisplayColumnDao to set
     */
    public void setReportDisplayColumnDao(ReportDisplayColumnDao reportDisplayColumnDao) {
        this.reportDisplayColumnDao = reportDisplayColumnDao;
    }

}

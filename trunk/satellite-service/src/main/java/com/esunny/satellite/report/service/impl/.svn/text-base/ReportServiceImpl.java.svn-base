package com.esunny.satellite.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.esunny.satellite.report.bean.DisplayColumnBean;
import com.esunny.satellite.report.bean.ReportMenuBean;
import com.esunny.satellite.report.bean.SearchConditionBean;
import com.esunny.satellite.report.common.ReportSqlException;
import com.esunny.satellite.report.constant.ReportConstant.MenuLeval;
import com.esunny.satellite.report.constant.ReportConstant.ReportDisplayColumn;
import com.esunny.satellite.report.constant.ReportConstant.ReportSearchCondition;
import com.esunny.satellite.report.dao.CommonDao;
import com.esunny.satellite.report.dao.interf.ReportMenuDao;
import com.esunny.satellite.report.entity.ReportMenu;
import com.esunny.satellite.report.service.intef.ReportService;
import com.esunny.satellite.util.SatelliteUtil;

/**
 * 类ReportCommonQueryServiceImpl.java的实现描述：共通报表查询 类实现描述
 * 
 * @author Administrator 2012-1-11 上午11:17:19
 */
public class ReportServiceImpl implements ReportService {

    private CommonDao          commonDao;

    /** 日志类 */
    private static Logger      log                     = Logger.getLogger("report_storage");
    /** 表report_search_condition 列名 */
    public static List<String> report_search_condition = new ArrayList<String>();

    /** 表report_display_column 列名 */
    public static List<String> report_display_column   = new ArrayList<String>();

    /** 表 列名 */
    public static List<String> display_column          = new ArrayList<String>();

    /** 表 report_menu列名 */
    public static List<String> report_menu             = new ArrayList<String>();

    private ReportMenuDao      reportMenuDao;

    static {
        display_column.add("COLUMN_NAME");
        display_column.add("DATA_TYPE");

        report_search_condition.add(ReportSearchCondition.ID);
        report_search_condition.add(ReportSearchCondition.TABLE_NAME);
        report_search_condition.add(ReportSearchCondition.COLUMN_NAME);
        report_search_condition.add(ReportSearchCondition.COLUMN_DISPLAY_NAME);
        report_search_condition.add(ReportSearchCondition.INPUT_TYPE);
        report_search_condition.add(ReportSearchCondition.ENUM_TABLE_NAME);
        report_search_condition.add(ReportSearchCondition.IS_COMPLETE_MATCH);

        report_display_column.add(ReportDisplayColumn.ID);
        report_display_column.add(ReportDisplayColumn.TABLE_NAME);
        report_display_column.add(ReportDisplayColumn.COLUMN_NAME);
        report_display_column.add(ReportDisplayColumn.COLUMN_DISPLAY_NAME);
        report_display_column.add(ReportDisplayColumn.IS_ENUM);
        report_display_column.add(ReportDisplayColumn.ENUM_TABLE_NAME);
        report_display_column.add(ReportDisplayColumn.SORT);
        report_display_column.add(ReportDisplayColumn.IS_SUM);
        report_display_column.add(ReportDisplayColumn.COLUMN_DATA_TYPE);
        report_display_column.add(ReportDisplayColumn.IS_GROUP_BY);
    }

    /**
     * 查看表的列名
     */
    public List<Map<String, String>> queryTableCloumns(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isBlank(tableName)) {
            return new ArrayList<Map<String, String>>();
        }
        tableName = SatelliteUtil.filterTableNameSuffix(tableName);
        params.put("TABLE_NAME", tableName.toUpperCase());
        try {
            List<Map<String, String>> rsList = commonDao.queryForListWithSql(getQuerySql(params, "all_tab_columns"),
                                                                             params, display_column);
            if (rsList == null) {
                rsList = new ArrayList<Map<String, String>>();
            }
            return rsList;
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return new ArrayList<Map<String, String>>();
        }
    }

    /**
     * 根据输入条件 查看report_menu详细信息
     */
    public List<Map<String, String>> queryMenu(Map<String, Object> params) {
        if (params == null) {
            return new ArrayList<Map<String, String>>();
        }
        try {
            List<Map<String, String>> rsList = commonDao.queryForListWithSql(getQuerySql(params, "report_menu")
                                                                             + " order by id", params, report_menu);
            if (rsList == null) {
                rsList = new ArrayList<Map<String, String>>();
            }
            return rsList;
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return new ArrayList<Map<String, String>>();
        }
    }

    /**
     * 查询所有一级类目列表
     * 
     * @return 所有一级类目列表
     */
    public List<ReportMenu> queryFirstLevelMenu() {
        ReportMenu reportMenu = new ReportMenu();
        reportMenu.setMenuLevel(MenuLeval.FIRST);
        return reportMenuDao.queryReportMenuList(reportMenu);
    }

    /**
     * 查询所有类目列表
     * 
     * @return 所有类目列表
     */
    public List<ReportMenu> queryAllMenu() {
        ReportMenu reportMenu = new ReportMenu();
        reportMenu.setOrderByClause("menu_level asc, id asc");
        return reportMenuDao.queryReportMenuList(reportMenu);
    }

    /**
     * 插入表report_search_condition数据
     */
    public int insertTableSearchCondition(SearchConditionBean bean) {
        if (bean == null) {
            return 0;
        }
        if (StringUtils.isNotBlank(bean.getTableName().toUpperCase())) {
            bean.setTableName(bean.getTableName().toUpperCase());
        }
        List<String> paramList = new ArrayList<String>();
        paramList.add("tableName");
        paramList.add("columnName");
        paramList.add("columnDisplayName");
        paramList.add("inputType");
        paramList.add("enumTableName");
        paramList.add("isCompleteMatch");

        String sql = getInsertSql("report_search_condition", paramList, "SEQ_REPORT_SEARCH_CONDITION.NEXTVAL");

        try {
            // 插入新数据
            return commonDao.editTable(sql, bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 插入菜单表数据
     */
    public int insertTableReportMenu(ReportMenuBean bean) {
        if (bean == null) {
            return 0;
        }
        if (StringUtils.isNotBlank(bean.getTableName().toUpperCase())) {
            bean.setTableName(bean.getTableName().toUpperCase());
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TABLE_NAME", "tableName");
        List<String> paramList = new ArrayList<String>();
        paramList.add("name");
        paramList.add("tableName");
        paramList.add("url");
        paramList.add("menuLevel");
        paramList.add("parentId");
        paramList.add("reportName");
        String sql = getInsertSql("report_menu", paramList, "SEQ_REPORT_MENU.NEXTVAL");

        try {
            // 删除原来的数据
            commonDao.editTable(getDeleteSql(params, "report_menu"), bean);
            // 插入新数据
            return commonDao.editTable(sql, bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 插入表report_display_column数据
     */
    public int insertTableDisplayColumn(DisplayColumnBean bean) {
        if (bean == null) {
            return 0;
        }
        if (StringUtils.isNotBlank(bean.getTableName().toUpperCase())) {
            bean.setTableName(bean.getTableName().toUpperCase());
        }

        List<String> paramList = new ArrayList<String>();
        paramList.add("tableName");
        paramList.add("columnName");
        paramList.add("columnDisplayName");
        paramList.add("isEnum");
        paramList.add("enumTableName");
        paramList.add("sort");
        paramList.add("isSum");
        paramList.add("columnDataType");
        paramList.add("isGroupBy");
        String sql = getInsertSql("report_display_column", paramList, "SEQ_REPORT_DISPLAY_COLUMN.NEXTVAL");

        try {
            // 插入新数据
            return commonDao.editTable(sql, bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 删除表report_search_condition中数据 根据表名
     */
    public int deleteTableSearchCondition(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return 0;
        }
        SearchConditionBean bean = new SearchConditionBean();
        bean.setTableName(tableName);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ReportSearchCondition.TABLE_NAME, "tableName");
        try {
            return commonDao.editTable(getDeleteSql(params, "report_search_condition"), bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 删除表report_display_column中数据 根据表名
     */
    public int deleteTableDisplayColumn(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return 0;
        }
        DisplayColumnBean bean = new DisplayColumnBean();
        bean.setTableName(tableName);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ReportDisplayColumn.TABLE_NAME, "tableName");
        try {
            return commonDao.editTable(getDeleteSql(params, "report_display_column"), bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 删除表report_menu中数据 根据表名
     */
    public int deleteTableReportMenu(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return 0;
        }
        ReportMenuBean bean = new ReportMenuBean();
        bean.setTableName(tableName);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("TABLE_NAME", "tableName");
        try {
            return commonDao.editTable(getDeleteSql(params, "report_menu"), bean);
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return 0;
        }
    }

    /**
     * 根据表名，在表report_search_condition查询检索条件
     */
    public Map<String, SearchConditionBean> querySearchCondition(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isBlank(tableName)) {
            return new HashMap<String, SearchConditionBean>();
        }
        params.put(ReportSearchCondition.TABLE_NAME, tableName.toUpperCase());
        try {
            List<Map<String, String>> rsList = commonDao.queryForListWithSql(getQuerySql(params,
                                                                                         "report_search_condition"),
                                                                             params, report_search_condition);
            if (rsList == null) {
                rsList = new ArrayList<Map<String, String>>();
            }
            Map<String, SearchConditionBean> rsMap = new HashMap<String, SearchConditionBean>();
            for (int i = 0; i < rsList.size(); i++) {
                SearchConditionBean bean = new SearchConditionBean();
                bean.setId(rsList.get(i).get(ReportSearchCondition.ID));
                bean.setTableName(rsList.get(i).get(ReportSearchCondition.TABLE_NAME));
                bean.setColumnName(rsList.get(i).get(ReportSearchCondition.COLUMN_NAME));
                bean.setColumnDisplayName(rsList.get(i).get(ReportSearchCondition.COLUMN_DISPLAY_NAME));
                bean.setInputType(rsList.get(i).get(ReportSearchCondition.INPUT_TYPE));
                bean.setEnumTableName(rsList.get(i).get(ReportSearchCondition.ENUM_TABLE_NAME));
                bean.setIsCompleteMatch(rsList.get(i).get(ReportSearchCondition.IS_COMPLETE_MATCH));
                rsMap.put(rsList.get(i).get(ReportSearchCondition.COLUMN_NAME), bean);
            }
            return rsMap;
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return new HashMap<String, SearchConditionBean>();
        }
    }

    /**
     * 根据表名，在表report_display_column查询展示列
     */
    public Map<String, DisplayColumnBean> queryDisplayColumn(String tableName) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isBlank(tableName)) {
            return new HashMap<String, DisplayColumnBean>();
        }
        params.put(ReportDisplayColumn.TABLE_NAME, tableName.toUpperCase());
        try {
            List<Map<String, String>> rsList = commonDao.queryForListWithSql(getQuerySql(params,
                                                                                         "report_display_column"),
                                                                             params, report_display_column);
            if (rsList == null) {
                rsList = new ArrayList<Map<String, String>>();
            }
            Map<String, DisplayColumnBean> rsMap = new HashMap<String, DisplayColumnBean>();
            for (int i = 0; i < rsList.size(); i++) {
                DisplayColumnBean bean = new DisplayColumnBean();

                bean.setId(rsList.get(i).get(ReportDisplayColumn.ID));
                bean.setTableName(rsList.get(i).get(ReportDisplayColumn.TABLE_NAME));
                bean.setColumnName(rsList.get(i).get(ReportDisplayColumn.COLUMN_NAME));
                bean.setColumnDisplayName(rsList.get(i).get(ReportDisplayColumn.COLUMN_DISPLAY_NAME));
                bean.setIsEnum(rsList.get(i).get(ReportDisplayColumn.IS_ENUM));
                bean.setEnumTableName(rsList.get(i).get(ReportDisplayColumn.ENUM_TABLE_NAME));
                bean.setSort(rsList.get(i).get(ReportDisplayColumn.SORT));
                bean.setIsSum(rsList.get(i).get(ReportDisplayColumn.IS_SUM));
                bean.setColumnDataType(rsList.get(i).get(ReportDisplayColumn.COLUMN_DATA_TYPE));
                bean.setIsGroupBy(rsList.get(i).get(ReportDisplayColumn.IS_GROUP_BY));
                rsMap.put(rsList.get(i).get(ReportSearchCondition.COLUMN_NAME), bean);
            }
            return rsMap;
        } catch (ReportSqlException e) {
            log.error(e.getMessage());
            return new HashMap<String, DisplayColumnBean>();
        }
    }

    /**
     * 获取插入sql
     * 
     * @param table
     * @param list
     * @param seq
     * @return
     */
    private String getInsertSql(String table, List<String> list, String seq) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(table).append(" values(").append(seq);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                sql.append(", :").append(list.get(i));
            }
        }
        sql.append(")");
        return sql.toString();
    }

    /**
     * 获取sql
     * 
     * @param params
     * @return
     */
    private String getQuerySql(Map<String, Object> params, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tableName);
        if (params == null) {
            return sql.toString();
        }
        if (params.size() > 0) {
            sql.append(" where ");
        }
        boolean isFirst = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (isFirst) {
                sql.append(entry.getKey()).append("= :").append(entry.getKey());
                isFirst = false;
            } else {
                sql.append(" and ").append(entry.getKey()).append("= :").append(entry.getKey());
            }
        }
        return sql.toString();
    }

    /**
     * 获取删除sql
     * 
     * @param params
     * @param tableName
     * @return
     */
    private String getDeleteSql(Map<String, Object> params, String tableName) {
        StringBuilder sql = new StringBuilder();
        if (params == null) {
            return sql.toString();
        }
        sql.append("delete from ").append(tableName).append(" where ");
        boolean isFirst = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (isFirst) {
                sql.append(entry.getKey()).append("= :").append(entry.getValue());
                isFirst = false;
            } else {
                sql.append(" and ").append(entry.getKey()).append("= :").append(entry.getValue());
            }
        }
        return sql.toString();
    }

    /**
     * @param commonDao the commonDao to set
     */
    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    /**
     * @param reportMenuDao the reportMenuDao to set
     */
    public void setReportMenuDao(ReportMenuDao reportMenuDao) {
        this.reportMenuDao = reportMenuDao;
    }

}

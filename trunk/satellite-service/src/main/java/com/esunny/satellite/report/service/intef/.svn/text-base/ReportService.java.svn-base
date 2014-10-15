package com.esunny.satellite.report.service.intef;

import java.util.List;
import java.util.Map;

import com.esunny.satellite.report.bean.DisplayColumnBean;
import com.esunny.satellite.report.bean.ReportMenuBean;
import com.esunny.satellite.report.bean.SearchConditionBean;
import com.esunny.satellite.report.entity.ReportMenu;

/**
 * 类ReportQueryColumnsService.java的实现描述：查看表中列名 类实现描述
 * 
 * @author Administrator 2012-2-3 上午9:42:34
 */
public interface ReportService {

    /**
     * 查找表中的列名
     * 
     * @param tableName 对应的表名
     */
    public List<Map<String, String>> queryTableCloumns(String tableName);

    /**
     * 插入表report_search_condition数据
     */
    public int insertTableSearchCondition(SearchConditionBean bean);

    /**
     * 插入表report_display_column数据
     */
    public int insertTableDisplayColumn(DisplayColumnBean bean);

    /**
     * 插 入菜单表数据数据
     */
    public int insertTableReportMenu(ReportMenuBean bean);

    /**
     * 删除表report_search_condition中数据 根据表名
     */
    public int deleteTableSearchCondition(String tableName);

    /**
     * 删除表report_display_column中数据 根据表名
     */
    public int deleteTableDisplayColumn(String tableName);

    /**
     * 删除表report_menu中数据 根据表名
     */
    public int deleteTableReportMenu(String tableName);

    /**
     * 根据表名，在表report_search_condition查询检索条件
     */
    public Map<String, SearchConditionBean> querySearchCondition(String tableName);

    /**
     * 根据表名，在表report_display_column查询展示列
     */
    public Map<String, DisplayColumnBean> queryDisplayColumn(String tableName);

    /**
     * 根据输入条件 查看report_menu详细信息
     */
    public List<Map<String, String>> queryMenu(Map<String, Object> params);

    /**
     * 查询所有一级类目列表
     * 
     * @return 所有一级类目列表
     */
    public List<ReportMenu> queryFirstLevelMenu();

    /**
     * 查询所有类目列表
     * 
     * @return 所有类目列表
     */
    public List<ReportMenu> queryAllMenu();
}

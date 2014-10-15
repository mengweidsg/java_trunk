package com.esunny.satellite.web.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.esunny.platform.web.handler.action.PermitBaseAction;
import com.esunny.satellite.report.bean.DisplayColumnBean;
import com.esunny.satellite.report.bean.ReportMenuBean;
import com.esunny.satellite.report.bean.SearchConditionBean;
import com.esunny.satellite.report.common.CommonUtils;
import com.esunny.satellite.report.constant.ReportConstant.GroupByColumn;
import com.esunny.satellite.report.constant.ReportConstant.MenuLeval;
import com.esunny.satellite.report.constant.ReportConstant.ReportMenu;
import com.esunny.satellite.report.constant.ReportConstant.SumColumn;
import com.esunny.satellite.report.dao.CommonDao;
import com.esunny.satellite.report.service.intef.ReportService;

/**
 * 类ReportEditAction.java的实现描述：新增 编辑 报表检索条件和现实列名 类实现描述
 * 
 * @author Administrator 2012-2-1 下午3:17:17
 */
public class ReportEditAction extends PermitBaseAction {

    /** 序列号 */
    private static final long                serialVersionUID = 4093625655701114627L;

    private ReportService                    reportService;

    private List<Map<String, String>>        cloumnsList;
    /** key */
    private static List<String>              keyList          = new ArrayList<String>();
    /** 日志类 */
    // private static Logger log = Logger.getLogger(ReportEditAction.class);

    private String                           reportId;

    private CommonDao                        commonDao;
    private String[]                         displayColumnArr;
    private String[]                         searchConditionArr;
    private String                           menuUrl;
    private String                           menuName;
    private String                           menuTable;

    private Map<String, SearchConditionBean> conditionBeanMap;
    private Map<String, DisplayColumnBean>   displayColumnBenMap;

    private String                           parentId;

    private List<Map<String, String>>        parentIdList;

    /**
     * 页面初始化
     * 
     * @return
     */
    public String pageInit() {
        // reportId为空则为新增 不为空则是更新
        if (StringUtils.isNotBlank(reportId)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(ReportMenu.ID, reportId);
            List<Map<String, String>> list = reportService.queryMenu(params);
            if (list == null || list.size() != 1 || list.get(0) == null) {
                return SUCCESS;
            }
            menuName = list.get(0).get(ReportMenu.NAME);
            menuTable = list.get(0).get(ReportMenu.TABLE_NAME);
            menuUrl = list.get(0).get(ReportMenu.URL);
            parentId = list.get(0).get(ReportMenu.PARENT_ID);
            if (StringUtils.isBlank(menuTable)) {
                return SUCCESS;
            }
            cloumnsList = reportService.queryTableCloumns(menuTable);
            conditionBeanMap = reportService.querySearchCondition(menuTable);
            displayColumnBenMap = reportService.queryDisplayColumn(menuTable);
        }
        // 取父菜单id集合
        parentIdListInit();

        return SUCCESS;
    }
    
    /**
     * 取父菜单id集合
     */
    private void parentIdListInit () {
        // 取父菜单id集合
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ReportMenu.MENU_LEVEL, MenuLeval.SECOND);
        parentIdList = reportService.queryMenu(params);

    }

    /**
     * 查看表的列
     * 
     * @return
     */
    public String queryTableCloumns() {
        if (StringUtils.isBlank(menuTable)) {
            addActionError("所对应的表名没有填写！");
        }
        cloumnsList = reportService.queryTableCloumns(menuTable);
        return SUCCESS;
    }

    public String submitRreprot() {
        // 插入数据校验
        // 插入检索条件
        conditionBeanMap = new HashMap<String, SearchConditionBean>();
        StringBuilder errSb = new StringBuilder();
        if (searchConditionArr != null && searchConditionArr.length > 0) {
            for (int i = 0; i < searchConditionArr.length; i++) {
                // 保存页面值
                SearchConditionBean pageBean = new SearchConditionBean();
                boolean isError = false;
                pageBean.setTableName(CommonUtils.delSpace(menuTable));
                pageBean.setColumnName(searchConditionArr[i]);
                if (StringUtils.isBlank(searchConditionArr[i]) && !isError) {
                    isError = true;
                }
                // 展示名称
                String displayName = getRequest().getParameter("search_" + searchConditionArr[i] + "_display");
                pageBean.setColumnDisplayName(CommonUtils.delSpace(displayName));
                if (StringUtils.isBlank(displayName) && !isError) {
                    isError = true;
                }
                // 是否完全匹配
                String isCompleteMatch = getRequest().getParameter("search_" + searchConditionArr[i] + "_complete");
                pageBean.setIsCompleteMatch(CommonUtils.delSpace(isCompleteMatch));
                if (StringUtils.isBlank(isCompleteMatch) && !isError) {
                    isError = true;
                }
                // 输入类型
                String inputType = getRequest().getParameter("search_" + searchConditionArr[i] + "_type");
                pageBean.setInputType(CommonUtils.delSpace(inputType));
                if (StringUtils.isBlank(inputType) && !isError) {
                    isError = true;
                }
                // 枚举类表名
                pageBean.setEnumTableName(CommonUtils.delSpace(getRequest().getParameter("search_"
                                                                                                 + searchConditionArr[i]
                                                                                                 + "_enumtable")));
                // 枚举类key列名
                conditionBeanMap.put(searchConditionArr[i], pageBean);

                // 记下错误行
                if (isError) {
                    if (StringUtils.isNotBlank(errSb.toString())) {
                        errSb.append("、").append(searchConditionArr[i]);
                    } else {
                        errSb.append(searchConditionArr[i]);
                    }
                }
            }
        }
        // 结果展示 校验
        StringBuilder errDisplaySb = new StringBuilder();
        boolean displayIsEmpty = false;
        int groupbyCount = 0;
        int sumCount = 0;
        displayColumnBenMap = new HashMap<String, DisplayColumnBean>();
        if (displayColumnArr != null && displayColumnArr.length > 0) {
            for (int i = 0; i < displayColumnArr.length; i++) {
                DisplayColumnBean pageDisplayBean = new DisplayColumnBean();
                pageDisplayBean.setTableName(CommonUtils.delSpace(menuTable));
                pageDisplayBean.setColumnName(displayColumnArr[i]);
                pageDisplayBean.setColumnDisplayName(CommonUtils.delSpace(getRequest().getParameter("display_"
                                                                                                            + displayColumnArr[i]
                                                                                                            + "_display")));
                String enumTable = getRequest().getParameter("display_" + displayColumnArr[i] + "_enumtable");
                pageDisplayBean.setEnumTableName(CommonUtils.delSpace(enumTable));
                pageDisplayBean.setSort(getRequest().getParameter("display_" + displayColumnArr[i] + "_sort"));

                String isSum = getRequest().getParameter("display_" + displayColumnArr[i] + "_issum");
                pageDisplayBean.setIsSum(isSum);
                if (String.valueOf(SumColumn.YES).equals(isSum)) {
                    sumCount++;
                }

                pageDisplayBean.setColumnDataType(getRequest().getParameter("display_" + displayColumnArr[i]
                                                                                    + "_columndatatype"));
                // groupby列
                String groupby = getRequest().getParameter("display_" + displayColumnArr[i] + "_isgroupby");
                if (String.valueOf(GroupByColumn.YES).equals(groupby)) {
                    groupbyCount++;
                }
                pageDisplayBean.setIsGroupBy(groupby);

                String isEnum = getRequest().getParameter("display_" + displayColumnArr[i] + "_isenum");
                pageDisplayBean.setIsEnum(isEnum);
                // 当为枚举类是，必须填写枚举类表 和列
                if ("1".equals(isEnum) && (StringUtils.isBlank(enumTable))) {
                    if (StringUtils.isNotBlank(errDisplaySb.toString())) {
                        errDisplaySb.append("、").append(displayColumnArr[i]);
                    } else {
                        errDisplaySb.append(displayColumnArr[i]);
                    }
                }
                displayColumnBenMap.put(displayColumnArr[i], pageDisplayBean);
            }
        } else {
            errDisplaySb.append("展示结果列不能为空！");
            displayIsEmpty = true;
        }
        if (StringUtils.isBlank(menuName) || StringUtils.isBlank(menuTable) || StringUtils.isBlank(menuUrl) || StringUtils.isBlank(parentId)) {
            addActionError("必填项没有填写完整（菜单中显示的名字、所对应的表名、url、父菜单id）！");
            if (StringUtils.isNotBlank(menuTable)) {
                cloumnsList = reportService.queryTableCloumns(menuTable);
            }
            parentIdListInit();
            return ERROR;
        }
        if (errSb != null && StringUtils.isNotBlank(errSb.toString())) {
            addActionError("插入检索信息" + errSb.toString() + "中，必填项没有填写完整！");
            cloumnsList = reportService.queryTableCloumns(menuTable);
            parentIdListInit();
            return ERROR;
        }
        if (errDisplaySb != null && StringUtils.isNotBlank(errDisplaySb.toString())) {

            if (displayIsEmpty) {
                addActionError(errDisplaySb.toString());
            } else {
                addActionError("展示结果信息 " + errDisplaySb.toString() + "中，必填项没有填写完整！");
            }
            cloumnsList = reportService.queryTableCloumns(menuTable);
            parentIdListInit();
            return ERROR;
        }
        if (displayColumnBenMap.size() != (groupbyCount + sumCount) && ((groupbyCount + sumCount) != 0)) {
            addActionError("展示的列数必须和groupby和sum的列数之和相等！");
            if (StringUtils.isNotBlank(menuTable)) {
                cloumnsList = reportService.queryTableCloumns(menuTable);
            }
            parentIdListInit();
            return ERROR;
        }
        // 校验结束

        ReportMenuBean reportMenuBean = new ReportMenuBean();
        reportMenuBean.setName(menuName);
        reportMenuBean.setTableName(menuTable);
        reportMenuBean.setUrl(menuUrl);
        reportMenuBean.setMenuLevel(MenuLeval.THIRD);
        reportMenuBean.setParentId(Long.parseLong(parentId));
        
        

        // reportId 为空则为新增操作 不为空则为更新操作
        if (StringUtils.isNotBlank(reportId)) {
            // 删除原来表中的数据，重新插入
            reportService.deleteTableSearchCondition(menuTable);
            reportService.deleteTableDisplayColumn(menuTable);
            reportService.deleteTableReportMenu(menuTable);

        }
        // 新增和更新操作 数据库处理
        // 插入检索条件
        for (Entry<String, SearchConditionBean> entry : conditionBeanMap.entrySet()) {
            reportService.insertTableSearchCondition(entry.getValue());
        }
        // 插入结果展示
        for (Entry<String, DisplayColumnBean> entry : displayColumnBenMap.entrySet()) {
            reportService.insertTableDisplayColumn(entry.getValue());
        }
        // 插 入菜单表数据数据
        reportService.insertTableReportMenu(reportMenuBean);
        return SUCCESS;
    }

    public String addMenu() {

        return SUCCESS;
    }

    public static List<String> getKeyList() {
        return keyList;
    }

    public static void setKeyList(List<String> keyList) {
        ReportEditAction.keyList = keyList;
    }

    /**
     * @return the reportId
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * @param reportId the reportId to set
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * @return the commonDao
     */
    public CommonDao getCommonDao() {
        return commonDao;
    }

    /**
     * @param commonDao the commonDao to set
     */
    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    /**
     * @return the reportService
     */
    public ReportService getReportService() {
        return reportService;
    }

    /**
     * @param reportService the reportService to set
     */
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * @return the cloumnsList
     */
    public List<Map<String, String>> getCloumnsList() {
        return cloumnsList;
    }

    /**
     * @param cloumnsList the cloumnsList to set
     */
    public void setCloumnsList(List<Map<String, String>> cloumnsList) {
        this.cloumnsList = cloumnsList;
    }

    /**
     * @return the displayColumnArr
     */
    public String[] getDisplayColumnArr() {
        return displayColumnArr;
    }

    /**
     * @param displayColumnArr the displayColumnArr to set
     */
    public void setDisplayColumnArr(String[] displayColumnArr) {
        this.displayColumnArr = displayColumnArr;
    }

    /**
     * @return the searchConditionArr
     */
    public String[] getSearchConditionArr() {
        return searchConditionArr;
    }

    /**
     * @param searchConditionArr the searchConditionArr to set
     */
    public void setSearchConditionArr(String[] searchConditionArr) {
        this.searchConditionArr = searchConditionArr;
    }

    /**
     * @return the menuUrl
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl the menuUrl to set
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the menuTable
     */
    public String getMenuTable() {
        return menuTable;
    }

    /**
     * @param menuTable the menuTable to set
     */
    public void setMenuTable(String menuTable) {
        this.menuTable = menuTable;
    }

    /**
     * @return the conditionBeanMap
     */
    public Map<String, SearchConditionBean> getConditionBeanMap() {
        return conditionBeanMap;
    }

    /**
     * @param conditionBeanMap the conditionBeanMap to set
     */
    public void setConditionBeanMap(Map<String, SearchConditionBean> conditionBeanMap) {
        this.conditionBeanMap = conditionBeanMap;
    }

    /**
     * @return the displayColumnBenMap
     */
    public Map<String, DisplayColumnBean> getDisplayColumnBenMap() {
        return displayColumnBenMap;
    }

    /**
     * @param displayColumnBenMap the displayColumnBenMap to set
     */
    public void setDisplayColumnBenMap(Map<String, DisplayColumnBean> displayColumnBenMap) {
        this.displayColumnBenMap = displayColumnBenMap;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the parentIdList
     */
    public List<Map<String, String>> getParentIdList() {
        return parentIdList;
    }

    /**
     * @param parentIdList the parentIdList to set
     */
    public void setParentIdList(List<Map<String, String>> parentIdList) {
        this.parentIdList = parentIdList;
    }

}

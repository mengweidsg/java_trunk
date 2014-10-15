package com.esunny.satellite.web.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.esunny.platform.web.handler.action.PermitBaseAction;
import com.esunny.satellite.report.constant.ReportConstant.ReportMenu;
import com.esunny.satellite.report.service.intef.ReportService;

/**
 * 类ReportManagerAction.java的实现描述：报表菜单管理 类实现描述
 * 
 * @author Administrator 2012-1-10 下午2:40:32
 */
public class ReportManagerAction extends PermitBaseAction {

    /** 序列号 */
    private static final long         serialVersionUID = 4093625655701114627L;

    private List<Map<String, String>> menuList;
    /** key */
    private static List<String>       keyList          = new ArrayList<String>();

    private ReportService             reportService;

    private String                    searchMenuName;
    private String                    searchMenuTable;
    private String                    menuUrl;

    static {
        keyList.add(ReportMenu.ID);
        keyList.add(ReportMenu.TABLE_NAME);
        keyList.add(ReportMenu.NAME);
        keyList.add(ReportMenu.URL);
    }

    /**
     * 查看菜单
     * 
     * @return
     */
    public String queryList() {
        pageInit();
        return SUCCESS;
    }

    /**
     * 删除菜单
     * 
     * @return
     */
    public String deleteReportMenu() {
        // 删除原来表中的数据，重新插入
        String delTable = getRequest().getParameter("deleteTab");
        reportService.deleteTableSearchCondition(delTable);
        reportService.deleteTableDisplayColumn(delTable);
        reportService.deleteTableReportMenu(delTable);
        pageInit();
        return SUCCESS;
    }

    /**
     * 画面展示
     */
    private void pageInit() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(searchMenuTable)) {
            params.put(ReportMenu.TABLE_NAME, searchMenuTable);
        }
        if (StringUtils.isNotBlank(searchMenuName)) {
            params.put(ReportMenu.NAME, searchMenuName);
        }
        params.put(ReportMenu.MENU_LEVEL, "3");
        menuList = reportService.queryMenu(params);
        getRequest().setAttribute("keyList", keyList);
    }
    
    /**
     * 导出菜单xml
     * 
     * @return
     */
    public String outXml() {
        Map<String, Object> params = new HashMap<String, Object>();
        menuList = reportService.queryMenu(params);
        StringBuilder xmlSb= new StringBuilder();
        if (menuList != null) {
            long code = 100900010002l;
            for (int i = 0; i < menuList.size(); i++) {
                xmlSb.append("<menuItem level=\"3\" code=\"").append(code).append("\" parentCode=\"10090001\" isLeaf=\"true\">");
                xmlSb.append("\r\n");
                xmlSb.append("    <name>").append(menuList.get(i).get(ReportMenu.NAME)).append("</name>");
                xmlSb.append("\r\n");
                xmlSb.append("    <url>").append(menuList.get(i).get(ReportMenu.URL)).append("</url>");
                xmlSb.append("\r\n");
                xmlSb.append("</menuItem>");
                code++;
            }
        }
        menuList.clear();
        getRequest().setAttribute("enuXml", xmlSb);
        return SUCCESS;
    }
    public String addMenu() {

        return SUCCESS;
    }

    public List<Map<String, String>> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Map<String, String>> menuList) {
        this.menuList = menuList;
    }

    public static List<String> getKeyList() {
        return keyList;
    }

    public static void setKeyList(List<String> keyList) {
        ReportManagerAction.keyList = keyList;
    }

    /**
     * @return the searchMenuName
     */
    public String getSearchMenuName() {
        return searchMenuName;
    }

    /**
     * @param searchMenuName the searchMenuName to set
     */
    public void setSearchMenuName(String searchMenuName) {
        this.searchMenuName = searchMenuName;
    }

    /**
     * @return the searchMenuTable
     */
    public String getSearchMenuTable() {
        return searchMenuTable;
    }

    /**
     * @param searchMenuTable the searchMenuTable to set
     */
    public void setSearchMenuTable(String searchMenuTable) {
        this.searchMenuTable = searchMenuTable;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
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

}

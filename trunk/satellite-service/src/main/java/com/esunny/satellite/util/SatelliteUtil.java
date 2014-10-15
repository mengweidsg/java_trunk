package com.esunny.satellite.util;

import java.util.HashSet;
import java.util.Set;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.constant.ReportConstant;
import com.esunny.satellite.report.constant.ReportConstant.UserAuthority;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.entity.ReportMenu;

public class SatelliteUtil {

    /** uri后缀 */
    private static final String URI_SUFFIX = ".htm";

    /** 需要权限的页面（针对报表） */
    private static Set<String>  reportSet;

    /** 需要校验权限的页面 （只有管理员和超级管理员能访问） */
    private static Set<String>  authoritySet;

    static {
        reportSet = new HashSet<String>();
        reportSet.add("/report/query_list_init.htm");
        reportSet.add("/report/query_real_time_init.htm");
        reportSet.add("/tool/query_input_sql.htm");

        authoritySet = new HashSet<String>();
        authoritySet.add("/account/show_user_manager.htm");
        authoritySet.add("/account/query_user_list.htm");
        authoritySet.add("/account/show_edit_dw_user.htm");
        authoritySet.add("/account/edit_dw_user.htm");
        authoritySet.add("/account/delete_dw_user.htm");
        authoritySet.add("/account/show_report_authority_manager.htm");
        authoritySet.add("/account/edit_user_report_authority.htm");
    }

    /**
     * 获取需要查询的表名
     * 
     * @return
     */
    public static String fetchTableNameFromUri(String uri, String prefix) {
        if (StringUtil.isBlank(uri)) {
            return null;
        }
        int idx = uri.lastIndexOf("/");
        String lastUri = uri;
        if (idx >= 0) {
            lastUri = uri.substring(idx + 1);
        }
        idx = lastUri.indexOf("?");
        if (idx >= 0) {
            lastUri = lastUri.substring(0, idx);
        }
        if (lastUri.startsWith(prefix) && lastUri.endsWith(URI_SUFFIX)) {
            int staIdx = prefix.length();
            int endIdx = lastUri.length() - URI_SUFFIX.length();
            return lastUri.substring(staIdx, endIdx);
        }

        return null;
    }

    /**
     * 获取真正表名（为了一张表可以展现多个报表，如果表名中包含【+】，则真正的表名为【+】前的字符串）
     * 
     * @param tableName
     * @return
     */
    public static String filterTableNameSuffix(Object oTableName) {
        String tableName = (oTableName == null ? null : String.valueOf(oTableName));
        if (StringUtil.isBlank(tableName)) {
            return tableName;
        }
        if (tableName.contains("+")) {
            return tableName.substring(0, tableName.indexOf("+"));
        } else {
            return tableName;
        }
    }

    /**
     * 是否有访问权限
     * 
     * @param userInfo 用户信息
     * @param reportMenu 菜单信息
     * @return
     */
    public static boolean canVisit(DwUserInfo userInfo, ReportMenu reportMenu) {
        if (null == userInfo || null == userInfo.getUserIdentity() || null == reportMenu) {
            return false;
        }
        // 需要管理员权限的页面
        if (authoritySet.contains(reportMenu.getUrl())) {
            if (UserAuthority.SUPER_ADMIN == userInfo.getUserIdentity().intValue()
                || UserAuthority.ADMIN == userInfo.getUserIdentity().intValue()) {
                return true;
            } else {
                return false;
            }
        }
        // 校验报表权限
        if (reportSet.contains(reportMenu.getUrl())) {
            String reportAuthorityStr = userInfo.getReportAuthorityStr();
            if (null != reportAuthorityStr) {
                reportAuthorityStr = ReportConstant.DEFULT_SPLIT_STR + reportAuthorityStr
                                     + ReportConstant.DEFULT_SPLIT_STR;
                String menuStr = ReportConstant.DEFULT_SPLIT_STR + reportMenu.getId() + ReportConstant.DEFULT_SPLIT_STR;
                // 确认是否存在权限
                if (reportAuthorityStr.indexOf(menuStr) < 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}

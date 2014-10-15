package com.esunny.satellite.report.service.intef;

import java.util.List;
import java.util.Map;

import com.esunny.satellite.report.bean.ReportItemInfo;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.entity.ReportDisplayColumn;
import com.esunny.satellite.report.entity.ReportSearchCondition;

/**
 * 类ReportCommonQueryService.java的实现描述：共通报表查询 类实现描述
 * 
 * @author Administrator 2012-1-11 上午11:16:26
 */
public interface ReportCommonQueryService {

    /**
     * 查找报表检索条件
     * 
     * @param tableName 对应的表名
     */
    List<ReportSearchCondition> querySearchCondition(String tableName);

    /**
     * 查找报表检索条件 List<Map<String, String>>
     * 
     * @param tableName 对应的表名
     */
    public List<Map<String, String>> queryReportListValue(List<ReportItemInfo> searchInfoList,
                                                          Map<String, Object> params,
                                                          List<ReportDisplayColumn> displayInfoList, Page page, boolean isSum);

    /**
     * 查看显示的表内容信息
     * 
     * @param tableName
     * @return
     * @throws Exception
     */
    public List<ReportDisplayColumn> getDisplayColumnInfo(String tableName);

    /**
     * 查看枚举类信息
     * 
     * @param enumNameList
     * @return
     * @throws Exception
     */
    public Map<String, Map<String, String>> queryEnumValues(List<String> enumNameList);
}

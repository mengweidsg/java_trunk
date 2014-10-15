package com.esunny.satellite.report.dao.interf;

import java.util.List;

import com.esunny.satellite.report.entity.ReportDisplayColumn;

public interface ReportDisplayColumnDao {

    /**
     * 根据表名获取列信息（根据sort字段排序）
     * 
     * @param tableName 表名
     * @return 列信息
     */
    List<ReportDisplayColumn> queryColumnListByTableName(String tableName);
}

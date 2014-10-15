package com.esunny.satellite.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.satellite.report.dao.interf.ReportDisplayColumnDao;
import com.esunny.satellite.report.entity.ReportDisplayColumn;

public class ReportDisplayColumnDaoImpl extends SqlMapClientDaoSupport implements ReportDisplayColumnDao {

    @SuppressWarnings("unchecked")
    public List<ReportDisplayColumn> queryColumnListByTableName(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            return new ArrayList<ReportDisplayColumn>();
        }
        ReportDisplayColumn params = new ReportDisplayColumn();
        params.setTableName(tableName);
        params.setOrderByClause("sort");
        List<ReportDisplayColumn> list = getSqlMapClientTemplate().queryForList("REPORT_DISPLAY_COLUMN.queryColumnList",
                                                                                params);
        return list;
    }

}

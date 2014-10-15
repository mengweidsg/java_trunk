package com.esunny.satellite.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.dao.interf.ReportSearchConditionDao;
import com.esunny.satellite.report.entity.ReportSearchCondition;

public class ReportSearchConditionDaoImpl extends SqlMapClientDaoSupport implements ReportSearchConditionDao {

    @SuppressWarnings("unchecked")
    public List<ReportSearchCondition> queryConditionListByTableName(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            return new ArrayList<ReportSearchCondition>();
        }
        List<ReportSearchCondition> list = getSqlMapClientTemplate().queryForList("REPORT_SEARCH_CONDITION.queryConditionListByTableName",
                                                                                  tableName);
        return list;
    }

}

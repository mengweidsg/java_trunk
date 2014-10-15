package com.esunny.satellite.report.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.dao.interf.ReportInfoPlusDao;
import com.esunny.satellite.report.entity.ReportInfoPlus;

public class ReportInfoPlusDaoImpl extends SqlMapClientDaoSupport implements ReportInfoPlusDao {

    @SuppressWarnings("unchecked")
    public ReportInfoPlus selectInfoPlusByTableName(String tableName) {
        if (StringUtil.isBlank(tableName)){
            return null;
        }
        List<ReportInfoPlus> list = getSqlMapClientTemplate().queryForList("REPORT_INFO_PLUS.selectInfoPlusByTableName", tableName);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}

package com.esunny.satellite.report.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.dao.interf.AllTabColumnsDao;
import com.esunny.satellite.report.entity.AllTabColumns;

public class AllTabColumnsDaoImpl extends SqlMapClientDaoSupport implements AllTabColumnsDao {

    @SuppressWarnings("unchecked")
    @Override
    public AllTabColumns selectColInfoByTableColumn(String tableName, String columnName) {
        if (StringUtil.isBlank(tableName) || StringUtil.isBlank(columnName)) {
            return null;
        }
        AllTabColumns params = new AllTabColumns();
        params.setTableName(tableName.toUpperCase());
        params.setColumnName(columnName.toUpperCase());
        List<AllTabColumns> list = getSqlMapClientTemplate().queryForList("ALL_TAB_COLUMNS.queryColInfoList",
                                                                          params);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}

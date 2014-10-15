package com.esunny.satellite.report.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.satellite.report.dao.interf.DwOperationalTopicsUrlDao;
import com.esunny.satellite.report.entity.DwOperationalTopicsUrl;

public class DwOperationalTopicsUrlDaoImpl extends SqlMapClientDaoSupport implements DwOperationalTopicsUrlDao {

    public void insertDwOperationalTopicsUrl(DwOperationalTopicsUrl record) {
        getSqlMapClientTemplate().insert("DW_OPERATIONAL_TOPICS_URL.insertDwOperationalTopicsUrl", record);
    }
}
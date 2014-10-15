package com.esunny.satellite.report.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.satellite.report.dao.interf.ReportEnumDao;
import com.esunny.satellite.report.entity.ReportEnum;

public class ReportEnumDaoImpl extends SqlMapClientDaoSupport implements ReportEnumDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ReportEnum> queryAllReportEnum() {
        List<ReportEnum> list = getSqlMapClientTemplate().queryForList("REPORT_ENUM.queryAllReportEnum");
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ReportEnum> queryReportByEnumNameList(List<String> enumNameList) {
        List<ReportEnum> list = getSqlMapClientTemplate().queryForList("REPORT_ENUM.queryReportByEnumNameList", enumNameList);
        return list;
    }

}

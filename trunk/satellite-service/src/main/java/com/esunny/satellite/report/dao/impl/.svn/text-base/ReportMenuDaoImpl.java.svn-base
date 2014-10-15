package com.esunny.satellite.report.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.satellite.report.dao.interf.ReportMenuDao;
import com.esunny.satellite.report.entity.ReportMenu;

public class ReportMenuDaoImpl extends SqlMapClientDaoSupport implements ReportMenuDao {

    @SuppressWarnings("unchecked")
    public List<ReportMenu> queryReportMenuList(ReportMenu reportMenu) {
        List<ReportMenu> list = getSqlMapClientTemplate().queryForList("REPORT_MENU.queryReportMenuList", reportMenu);
        return list;
    }

    public int deleteReportMenuById(Long id) {
        int rows = getSqlMapClientTemplate().delete("REPORT_MENU.deleteReportMenuById", id);
        return rows;
    }

    public void insertReportMenu(ReportMenu reportMenu) {
        getSqlMapClientTemplate().insert("REPORT_MENU.insertReportMenu", reportMenu);
    }
}

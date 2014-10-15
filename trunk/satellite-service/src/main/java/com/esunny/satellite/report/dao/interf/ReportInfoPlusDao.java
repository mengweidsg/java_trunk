package com.esunny.satellite.report.dao.interf;

import com.esunny.satellite.report.entity.ReportInfoPlus;

public interface ReportInfoPlusDao {
 
    ReportInfoPlus selectInfoPlusByTableName(String tableName);

}
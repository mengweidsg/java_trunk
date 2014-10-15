/**
 * 
 */
package com.esunny.satellite.report.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.esunny.satellite.report.common.CommonUtils;

/**
 * @author Jet Xu
 */
public class NameListRowMapper implements RowMapper {

    List<String> colList;

    public NameListRowMapper(){

    }

    public NameListRowMapper(List<String> colList){
        this.colList = colList;
    }

    public Map<String, String> mapRow(ResultSet rs, int idx) throws SQLException {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        if (CommonUtils.isNotEmpty(colList)) {
            for (String col : colList) {
                try {
                    resultMap.put(col, rs.getString(col));
                } catch (Exception ex) {
                    resultMap.put(col, "");
                }
            }
        } else {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                try {
                    if("ROWNUMFORPAGING".equals(rsmd.getColumnName(i))){
                        continue;
                    }
                    resultMap.put(rsmd.getColumnName(i), rs.getString(rsmd.getColumnName(i)));
                } catch (Exception ex) {
                    resultMap.put(rsmd.getColumnName(i), "");
                }
            }
        }
        return resultMap;
    }

}

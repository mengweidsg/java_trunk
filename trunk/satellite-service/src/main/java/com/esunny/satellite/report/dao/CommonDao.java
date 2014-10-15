/**
 * 
 */
package com.esunny.satellite.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.esunny.satellite.report.bean.BaseBean;
import com.esunny.satellite.report.common.ReportSqlException;

/**
 * @author Jet Xu
 * <p>
 * namedParameterJdbcTemplate.query 在有参数的时候使用
 * </p>
 * <p>
 * namedParameterJdbcTemplate.getJdbcOperations().query 在没有参数的时候使用，应该等同于JdbcTemplate.query
 * </p>
 */
public class CommonDao {

    /** jdbc操作模板 */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 根据sql取得结果列表
     * 
     * @param sql
     * @param params
     * @param colList
     * @return
     * @throws XjtSqlException
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, String>> queryForListWithSql(String sql, Map<String, Object> params, List<String> colList)
                                                                                                                      throws ReportSqlException {
        RowMapper rowMapper = new NameListRowMapper(colList);
        try {
            return (List<Map<String, String>>) this.namedParameterJdbcTemplate.query(sql,
                                                                                     new MapSqlParameterSource(params),
                                                                                     rowMapper);
        } catch (Exception e) {
            throw new ReportSqlException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseBean> List<T> queryForList(String sql, T condition, Class<T> clz) throws ReportSqlException {
        if (StringUtils.isEmpty(sql)) {
            return null;
        }

        if (null == condition) {
            return queryForList(sql, clz);
        }

        try {
            return this.namedParameterJdbcTemplate.query(sql, new BeanPropertySqlParameterSource(condition),
                                                         new BeanPropertyRowMapper(clz));
        } catch (DataAccessException e) {
            throw new ReportSqlException(e);
        } catch (Exception e) {
            throw new ReportSqlException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BaseBean> List<T> queryForList(String sql, Class<T> clz) throws ReportSqlException {
        if (StringUtils.isEmpty(sql)) {
            return null;
        }
        try {
            return this.namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper(clz));
        } catch (DataAccessException e) {
            throw new ReportSqlException(e);
        } catch (Exception e) {
            throw new ReportSqlException(e);
        }
    }

    public <T extends BaseBean> T queryForBean(String sqlid, Class<T> clz) throws ReportSqlException {
        List<T> list = queryForList(sqlid, clz);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    public <T extends BaseBean> T queryForBean(String sql, T condition, Class<T> clz) throws ReportSqlException {
        List<T> list = queryForList(sql, condition, clz);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    /**
     * 根据sql取得结果数
     * 
     * @param sql
     * @param params
     * @param colList
     * @return
     * @throws XjtSqlException
     */
    public int queryForCountWithSql(String sql, Map<String, Object> params, List<String> colList) {
        namedParameterJdbcTemplate.getJdbcOperations().queryForInt(sql);

        return 0;
    }

    /**
     * 根据sql取得结果(int)
     * 
     * @param sql
     * @return
     * @throws XjtSqlException
     */
    public int queryForIntWithSql(String sql, Map<String, Object> params) throws ReportSqlException {
        try {
            return this.namedParameterJdbcTemplate.queryForInt(sql, new MapSqlParameterSource(params));
        } catch (DataAccessException e) {
            throw new ReportSqlException(e);
        } catch (Exception e) {
            throw new ReportSqlException(e);
        }
    }

    /**
     * 添加
     * 
     * @param sql
     * @param bean
     * @return
     * @throws ReportSqlException
     */
    public <T> int editTable(String sql, T bean) throws ReportSqlException {

        return this.namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(bean));
    }

    /**
     * @param namedParameterJdbcTemplate the namedParameterJdbcTemplate to set
     */
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

}

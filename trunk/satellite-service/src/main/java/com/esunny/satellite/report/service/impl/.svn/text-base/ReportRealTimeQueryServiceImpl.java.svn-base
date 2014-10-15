package com.esunny.satellite.report.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.common.ReportSqlException;
import com.esunny.satellite.report.constant.ReportConstant;
import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.dao.CommonDao;
import com.esunny.satellite.report.service.intef.ReportRealTimeQueryService;
import com.esunny.satellite.vo.ReportSearchConditionVo;
import com.esunny.satellite.vo.SearchConditonVo;

/**
 * 类ReportCommonQueryServiceImpl.java的实现描述：共通报表查询 类实现描述
 * 
 * @author Administrator 2012-1-11 上午11:17:19
 */
public class ReportRealTimeQueryServiceImpl implements ReportRealTimeQueryService {

    private CommonDao           commonDao;

    private static final String SELECT_RESULT_COUNT = " select count(1) from ($sql$)  ";
    private static final String SELECT_SQL_BY_PAGE  = " select * from (select t.*, rownum ROWNUMFORPAGING from ($sql$) t where rownum <= :ENDROW) where ROWNUMFORPAGING > :STARTROW ";

    public List<Map<String, String>> queryRealTimeList(String sql, SearchConditonVo searchConditonVo, Page query)
                                                                                                                 throws ReportSqlException {
        if (StringUtil.isEmpty(sql)) {
            return new ArrayList<Map<String, String>>();
        }
        if (null == query) {
            query = new Page();
        }
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder where = new StringBuilder();
        if (searchConditonVo != null) {
            // 双时间控件
            if (searchConditonVo.getDoubleDateInfo() != null) {
                ReportSearchConditionVo vo = searchConditonVo.getDoubleDateInfo();
                List<String> valueList = vo.getValueList();
                if (CollectionUtils.isNotEmpty(valueList) && valueList.size() == 2) {
                    String from = valueList.get(0);
                    String to = valueList.get(1);
                    Date dateFrom = DateUtils.toDate(from, "yyyy-MM-dd");
                    Date dateTo = DateUtils.toDate(to, "yyyy-MM-dd");
                    String columnName = vo.getColumnName();
                    if (null != dateFrom) {
                        where.append(columnName).append(" >= :").append(columnName + "From");
                        params.put(columnName + "From", dateFrom);
                    }
                    if (null != dateTo) {
                        if (where.length() > 0) {
                            where.append(" and ");
                        }
                        where.append(columnName).append(" < :").append(columnName + "To").append(" + 1 ");
                        params.put(columnName + "To", dateTo);
                    }
                }
            }
            // 链接控件
            if (searchConditonVo.getLiLinkInfo() != null) {
                String columnName = searchConditonVo.getLiLinkInfo().getColumnName();
                if (StringUtil.isNotBlank(searchConditonVo.getLiLinkInfo().getValue())) {
                    if (where.length() > 0) {
                        where.append(" and ");
                    }
                    where.append(columnName).append(" = :").append(columnName);
                    params.put(columnName, searchConditonVo.getLiLinkInfo().getValue());
                }
            }
            // 其他控件
            if (CollectionUtils.isNotEmpty(searchConditonVo.getDetailList())) {
                for (ReportSearchConditionVo vo : searchConditonVo.getDetailList()) {
                    // 双日期控件
                    switch (vo.getInputType()) {
                        case INPUT_TYPE.DOUBLE_DATE:
                            List<String> valueList = vo.getValueList();
                            if (CollectionUtils.isNotEmpty(valueList) && valueList.size() == 2) {
                                String from = valueList.get(0);
                                String to = valueList.get(1);
                                Date dateFrom = DateUtils.toDate(from, "yyyy-MM-dd");
                                Date dateTo = DateUtils.toDate(to, "yyyy-MM-dd");
                                String columnName = vo.getColumnName();
                                if (null != dateFrom) {
                                    where.append(columnName).append(" >= :").append(columnName + "From");
                                    params.put(columnName + "From", dateFrom);
                                }
                                if (null != dateTo) {
                                    if (where.length() > 0) {
                                        where.append(" and ");
                                    }
                                    where.append(columnName).append(" < :").append(columnName + "To").append(" + 1 ");
                                    params.put(columnName + "To", dateTo);
                                }
                            }
                            break;
                        case INPUT_TYPE.TEXT:
                            if (StringUtil.isNotBlank(vo.getValue())) {
                                // 如果为完全匹配
                                if (null != vo.getIsCompleteMatch() && vo.getIsCompleteMatch() == 1) {
                                    if (where.length() > 0) {
                                        where.append(" and ");
                                    }
                                    where.append(vo.getColumnName()).append(" = :").append(vo.getColumnName());
                                    params.put(vo.getColumnName(), vo.getValue());
                                }
                                // 如果为部分匹配
                                else {
                                    if (where.length() > 0) {
                                        where.append(" and ");
                                    }
                                    where.append(vo.getColumnName()).append(" like :").append(vo.getColumnName());
                                    // 重新设置paramMap中的值（前后添加%）
                                    params.put(vo.getColumnName(), "%" + vo.getValue() + "%");
                                }
                            }
                            break;
                        case INPUT_TYPE.DATE:
                            Date date = DateUtils.toDate(vo.getValue(), "yyyy-MM-dd");
                            if (null != date) {
                                if (where.length() > 0) {
                                    where.append(" and ");
                                }
                                where.append(vo.getColumnName()).append(" = :").append(vo.getColumnName());
                                params.put(vo.getColumnName(), date);
                            }
                            break;
                        case INPUT_TYPE.SELECT:
                            if (StringUtil.isNotBlank(vo.getValue())
                                && !ReportConstant.ESUNNY_ALL.equals(vo.getValue())) {
                                if (where.length() > 0) {
                                    where.append(" and ");
                                }
                                where.append(vo.getColumnName()).append(" = :").append(vo.getColumnName());
                                params.put(vo.getColumnName(), vo.getValue());
                            }
                            break;
                        default:
                            if (StringUtil.isNotBlank(vo.getValue())) {
                                if (where.length() > 0) {
                                    where.append(" and ");
                                }
                                where.append(vo.getColumnName()).append(" = :").append(vo.getColumnName());
                                params.put(vo.getColumnName(), vo.getValue());
                            }
                            break;
                    }
                }
            }
        }
        String strWhere = "";
        if (where.length() != 0) {
            strWhere = " where " + where.toString();
        }
        query.doPage(commonDao.queryForIntWithSql(SELECT_RESULT_COUNT.replace("$sql$", sql.replace("$where$", strWhere)),
                                                  params));
        // 页数的控制
        params.put("STARTROW", query.getStartRow());
        params.put("ENDROW", query.getEndRow());
        return commonDao.queryForListWithSql(SELECT_SQL_BY_PAGE.replace("$sql$", sql.replace("$where$", strWhere)),
                                             params, null);
    }

    /**
     * @param commonDao the commonDao to set
     */
    public void setCommonDao(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

}

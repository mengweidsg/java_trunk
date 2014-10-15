package com.esunny.satellite.web.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.DateUtils;
import com.esunny.satellite.report.common.Page;
import com.esunny.satellite.report.constant.ReportConstant.INPUT_TYPE;
import com.esunny.satellite.report.entity.ReportInfoPlus;
import com.esunny.satellite.vo.ReportSearchConditionVo;
import com.esunny.satellite.vo.SearchConditonVo;

/**
 * SEO/PPC关键字来源注册会员详情
 * 
 * @author Jet Xu 2012-6-12 上午10:34:59
 */
public class SourceKeywordQueryAction extends ReportRealTimeQueryAction {

    private static final long serialVersionUID = 2055921666047215686L;

    /** 关键词 */
    private String            keyWord;

    /** 来源类型:PPC/SEO */
    private String            sourceType;

    /** 开始时间 */
    private String            dateStart;

    /** 结束时间 */
    private String            dateEnd;

    @Override
    public String queryRealTimeList() {
        // 如果关键词为空，直接返回
        if (StringUtil.isBlank(keyWord)) {
            return SUCCESS;
        }
        // 如果来源类型不为PPC或SEO，直接返回
        if (StringUtil.isBlank(keyWord)) {
            return SUCCESS;
        }
        // 如果开始时间不为空，但时间格式不正确，直接返回
        if (StringUtil.isNotBlank(dateStart)) {
            Date start = DateUtils.toDate(dateStart, "yyyy-MM-dd");
            if (null == start) {
                return SUCCESS;
            }
        }
        // 如果结束时间不为空，但时间格式不正确，直接返回
        if (StringUtil.isNotBlank(dateEnd)) {
            Date end = DateUtils.toDate(dateEnd, "yyyy-MM-dd");
            if (null == end) {
                return SUCCESS;
            }
        }
        if (this.getQuery() == null) {
            this.setQuery(new Page());
        }
        return super.queryRealTimeList();
    }

    @Override
    protected String getSelectSql(ReportInfoPlus reportInfoPlus, String reportTableName) {
        String sql = "select a.user_id as 企业编码, a.email as 邮箱, to_char(a.gmt_create, 'yyyy-mm-dd') as 注册时间, a.mobile_no as 手机号码, rp.region_name as 省份, rc.region_name as 城市 from (select m.user_id, m.email, m.gmt_create, m.mobile_no, m.province, m.city from (select * from browse_log $where$) t left join member m on (t.last_login_email = m.email or t.last_login_email = m.mobile_no) where ((t.ref = 'http://member.esunny.com/firm_join.htm' and t.url = 'http://member.esunny.com/add_firm_join.htm') or (t.ref = 'http://member.esunny.com/join.htm' and t.url = 'http://member.esunny.com/add_normal_join.htm') or (t.ref = 'http://member.esunny.com/join/finance_join.htm' and t.url = 'http://member.esunny.com/join/finance_join_submit.htm')) and is_logon = 1 and last_login_email is not null and t.time < trunc(sysdate, 'dd')) a left join region rp on a.province = rp.region_id left join region rc on a.city = rc.region_id";
        SearchConditonVo searchConditonVo = new SearchConditonVo();
        searchConditonVo.setDetailList(new ArrayList<ReportSearchConditionVo>());
        // 构造检索条件
        // 关键词
        if (StringUtil.isNotBlank(keyWord)) {
            ReportSearchConditionVo vo = new ReportSearchConditionVo();
            vo.setColumnDisplayName("关键字");
            vo.setColumnName("key_word");
            vo.setInputType(INPUT_TYPE.OTHER);
            vo.setValue(keyWord);
            searchConditonVo.getDetailList().add(vo);
        }
        // 来源类型
        if (StringUtil.isNotBlank(sourceType)) {
            ReportSearchConditionVo vo = new ReportSearchConditionVo();
            vo.setColumnDisplayName("来源");
            vo.setColumnName("source");
            vo.setInputType(INPUT_TYPE.OTHER);
            vo.setValue(sourceType);
            searchConditonVo.getDetailList().add(vo);
        }
        // 时间范围
        if (StringUtil.isNotBlank(dateStart) || StringUtil.isNotBlank(dateEnd)) {
            ReportSearchConditionVo doubleDateInfo = new ReportSearchConditionVo();
            doubleDateInfo.setColumnDisplayName("日期");
            doubleDateInfo.setColumnName("time");
            doubleDateInfo.setInputType(INPUT_TYPE.DOUBLE_DATE);
            List<String> valueList = new ArrayList<String>();
            valueList.add(dateStart);
            valueList.add(dateEnd);
            doubleDateInfo.setValueList(valueList);
            searchConditonVo.setDoubleDateInfo(doubleDateInfo);
        }
        setSearchConditonVo(searchConditonVo);
        return sql;
    }

    /**
     * @return the keyWord
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * @param keyWord the keyWord to set
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @return the dateStart
     */
    public String getDateStart() {
        return dateStart;
    }

    /**
     * @param dateStart the dateStart to set
     */
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * @return the dateEnd
     */
    public String getDateEnd() {
        return dateEnd;
    }

    /**
     * @param dateEnd the dateEnd to set
     */
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

}

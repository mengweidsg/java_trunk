package com.esunny.satellite.remote.entity;

import java.util.Date;

import com.esunny.platform.util.page.Query;

public class DwEsitePagePvInfo extends Query {

    private static final long serialVersionUID = -2269472649672082101L;

    /**
     * DW_ESITE_PAGE_PV_INFO.url
     * 
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    private String            url;

    /**
     * DW_ESITE_PAGE_PV_INFO.user_id
     * 
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    private Long              userId;

    /**
     * DW_ESITE_PAGE_PV_INFO.pv
     * 
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    private Long              pv;

    /**
     * DW_ESITE_PAGE_PV_INFO.last_stat_date
     * 
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    private Long              lastLogId;

    /**
     * DW_ESITE_PAGE_PV_INFO.gmt_create
     * 
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    private Date              gmtCreate;

    /**
     * column DW_ESITE_PAGE_PV_INFO.url
     * 
     * @return the value of DW_ESITE_PAGE_PV_INFO.url
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public String getUrl() {
        return url;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.url
     * 
     * @param url the value for DW_ESITE_PAGE_PV_INFO.url
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.user_id
     * 
     * @return the value of DW_ESITE_PAGE_PV_INFO.user_id
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.user_id
     * 
     * @param userId the value for DW_ESITE_PAGE_PV_INFO.user_id
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.pv
     * 
     * @return the value of DW_ESITE_PAGE_PV_INFO.pv
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public Long getPv() {
        return pv;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.pv
     * 
     * @param pv the value for DW_ESITE_PAGE_PV_INFO.pv
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public void setPv(Long pv) {
        this.pv = pv;
    }

    /**
     * @return the lastLogId
     */
    public Long getLastLogId() {
        return lastLogId;
    }

    /**
     * @param lastLogId the lastLogId to set
     */
    public void setLastLogId(Long lastLogId) {
        this.lastLogId = lastLogId;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.gmt_create
     * 
     * @return the value of DW_ESITE_PAGE_PV_INFO.gmt_create
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * column DW_ESITE_PAGE_PV_INFO.gmt_create
     * 
     * @param gmtCreate the value for DW_ESITE_PAGE_PV_INFO.gmt_create
     * @hujf Thu Mar 29 19:13:11 CST 2012
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}

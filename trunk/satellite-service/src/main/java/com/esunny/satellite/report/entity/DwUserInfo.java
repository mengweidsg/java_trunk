package com.esunny.satellite.report.entity;

import java.util.Date;

import com.esunny.platform.util.page.Query;

public class DwUserInfo extends Query {

    private static final long serialVersionUID = 2497730672769925553L;

    private Long              id;

    private String            userId;

    private String            userName;

    private String            userEmail;

    private String            userPassword;

    private Integer           userIdentity;

    private String            departmentName;

    private String            reportAuthorityStr;

    private Date              gmtCreate;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * @return the userIdentity
     */
    public Integer getUserIdentity() {
        return userIdentity;
    }

    /**
     * @param userIdentity the userIdentity to set
     */
    public void setUserIdentity(Integer userIdentity) {
        this.userIdentity = userIdentity;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * @return the reportAuthorityStr
     */
    public String getReportAuthorityStr() {
        return reportAuthorityStr;
    }

    /**
     * @param reportAuthorityStr the reportAuthorityStr to set
     */
    public void setReportAuthorityStr(String reportAuthorityStr) {
        this.reportAuthorityStr = reportAuthorityStr;
    }

    /**
     * @return the gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate the gmtCreate to set
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}

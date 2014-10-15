package com.esunny.satellite.web.action.login;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.config.ResourceUtil;
import com.esunny.platform.util.security.MD5;
import com.esunny.platform.util.web.CookieUtils;
import com.esunny.platform.web.handler.action.PermitBaseAction;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.service.intef.LoginService;
import com.esunny.satellite.util.SatelliteConstants;

public class LoginAction extends PermitBaseAction {

    private static final long serialVersionUID = -2690095510421003286L;
    private String            errorMsg;

    private DwUserInfo        userInfo;

    protected Cache           loginCacheTemplate;

    private LoginService      loginService;

    public DwUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(DwUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String showLogin() {
        return SUCCESS;
    }

    public String doLogin() {
        DwUserInfo dbUserInfo = loginService.getUserInfo(userInfo.getUserId());
        if (null == dbUserInfo) {
            setErrorMsg("WRONG_EMAIL");
            return ERROR;
        }
        if (StringUtils.isNotBlank(dbUserInfo.getUserPassword())
            && dbUserInfo.getUserPassword().equals(MD5.str2md5(userInfo.getUserPassword()))) {
            writeCookie(dbUserInfo);
            return SUCCESS;
        } else {
            setErrorMsg("WRONG_PASSWORD");
            return ERROR;
        }
    }

    public String doLogout() {
        String logonKey = CookieUtils.getCookieValue(ServletActionContext.getRequest(),
                                                     SatelliteConstants.SATELLITE_COOKIE_LOGON);
        loginCacheTemplate.remove(logonKey);
        return SUCCESS;
    }

    protected void writeCookie(DwUserInfo userInfo) {
        String token = RandomStringUtils.randomAlphanumeric(12);
        long timestamp = System.currentTimeMillis();
        String encryedKey = MD5.str2md5(userInfo.getId() + token + timestamp);
        loginCacheTemplate.put(encryedKey, userInfo.getId());
        int expiry = -1;
        CookieUtils.setCookie(this.getResponse(), SatelliteConstants.SATELLITE_COOKIE_LOGON, encryedKey, expiry,
                              ResourceUtil.getUrl("satellite.cookie.domain").getDomain(),
                              ResourceUtil.getUrl("default").getDomain());
        CookieUtils.setCookie(this.getResponse(), SatelliteConstants.SATELLITE_USER_ID, userInfo.getUserId(), 31536000,
                              ResourceUtil.getUrl("satellite.cookie.domain").getDomain(),
                              ResourceUtil.getUrl("default").getDomain());
    }

    /**
     * @param loginCacheTemplate the loginCacheTemplate to set
     */
    public void setLoginCacheTemplate(Cache loginCacheTemplate) {
        this.loginCacheTemplate = loginCacheTemplate;
    }

    /**
     * @param loginService the loginService to set
     */
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

}

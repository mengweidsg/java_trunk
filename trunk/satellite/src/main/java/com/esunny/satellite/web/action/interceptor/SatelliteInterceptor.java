package com.esunny.satellite.web.action.interceptor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.esunny.platform.cache.Cache;
import com.esunny.platform.util.config.ResourceUtil;
import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.web.CookieUtils;
import com.esunny.platform.web.handler.interceptor.UrlPaternInterceptor;
import com.esunny.satellite.report.constant.ReportConstant.AuthorityType;
import com.esunny.satellite.report.constant.ReportConstant.MenuLeval;
import com.esunny.satellite.report.constant.ReportConstant.UserAuthority;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.entity.ReportMenu;
import com.esunny.satellite.report.service.intef.LoginService;
import com.esunny.satellite.report.service.intef.ReportService;
import com.esunny.satellite.util.SatelliteConstants;
import com.esunny.satellite.vo.MenuVo;
import com.esunny.satellite.web.action.MenuBaseAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 登录和菜单栏拦截器
 */
public class SatelliteInterceptor extends UrlPaternInterceptor {

    private static final long   serialVersionUID         = 2159480612460599296L;
    private static final String DEFAULT_LOGIN_URL_PREFIX = "http://satellite.esunny.com/";
    private static final Logger log                      = LoggerFactory.getLogger(SatelliteInterceptor.class);
    private static final String MENU_ROOT_LIST           = "menuRootList";
    private static final String MENU_ITEM_LIST           = "menuItemList";
    private static final String MENU_ROOT_ID             = "menuRootId";
    private Cache               menuCacheTemplate;
    protected Cache             loginCacheTemplate;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

        ApplicationContext applicationContext = (ApplicationContext) ActionContext.getContext().getApplication().get(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (applicationContext == null) {
            log.error("ApplicationContext could not be found,Login validator failed!");
            return null;
        }
        String logonKey = CookieUtils.getCookieValue(ServletActionContext.getRequest(),
                                                     SatelliteConstants.SATELLITE_COOKIE_LOGON);
        ReportService reportService = (ReportService) applicationContext.getBean("reportService");
        LoginService loginService = (LoginService) applicationContext.getBean("loginService");
        Long id = null;
        // // 该用户是否已经登录
        if (StringUtil.isNotBlank(logonKey)) {
            // 获取用户ID
            id = (Long) loginCacheTemplate.get(logonKey);
            if (id != null) {
                // 菜单加载
                if (menuCacheTemplate == null) {
                    menuCacheTemplate = (Cache) applicationContext.getBean("menuCacheTemplate");
                }
                List<ReportMenu> menuList = reportService.queryAllMenu();
                Map<Long, ReportMenu> menuMap = new LinkedHashMap<Long, ReportMenu>();
                // 一级菜单列表（导航栏）
                List<MenuVo> firstMenuList = new ArrayList<MenuVo>();
                ReportMenu currentMenu = null;
                for (ReportMenu reportMenu : menuList) {
                    menuMap.put(reportMenu.getId(), reportMenu);
                    // 如果为一级菜单
                    if (MenuLeval.FIRST.equals(reportMenu.getMenuLevel())) {
                        MenuVo menuVo = convertToMenuVo(reportMenu);
                        if (null != menuVo) {
                            if (null == menuVo.getHideFlag() || 1 != menuVo.getHideFlag().intValue()) {
                                firstMenuList.add(menuVo);
                            }
                        }
                    }
                    // 如果为第3级，且关键字一致，则为当前点击的页面
                    if (StringUtil.isNotBlank(reportMenu.getUrl())) {
                        if (MenuLeval.THIRD.equals(reportMenu.getMenuLevel())
                            && ServletActionContext.getRequest().getRequestURI().endsWith(reportMenu.getUrl())) {
                            currentMenu = reportMenu;
                        }
                    }
                }
                // 获取一级菜单ID
                Long menuRootId = getRootMenuId(menuMap, currentMenu);
                if (null == menuRootId) {
                    // 暂时这样设定
                    menuRootId = 9l;
                }
                ServletActionContext.getRequest().setAttribute(MENU_ROOT_ID, menuRootId);
                ServletActionContext.getRequest().setAttribute(MENU_ROOT_LIST, firstMenuList);

                Map<Long, MenuVo> leftMenuMap = new LinkedHashMap<Long, MenuVo>();
                // 左侧菜单列表--二级菜单
                for (ReportMenu second : menuList) {
                    // 获取页面所在一级菜单下的所有二级菜单
                    if (MenuLeval.SECOND.equals(second.getMenuLevel()) && menuRootId.equals(second.getParentId())) {
                        MenuVo menuVo = convertToMenuVo(second);
                        if (null != menuVo) {
                            // 如果不是隐藏，则添加
                            if (null == menuVo.getHideFlag() || 1 != menuVo.getHideFlag().intValue()) {
                                leftMenuMap.put(second.getId(), menuVo);
                            }
                        }
                    }
                }
                // 左侧菜单列表--三级菜单
                for (ReportMenu third : menuList) {
                    MenuVo second = leftMenuMap.get(third.getParentId());
                    // 获取页面所在一级菜单下的所有二级菜单下的所有三级菜单
                    if (MenuLeval.THIRD.equals(third.getMenuLevel()) && null != second) {
                        List<MenuVo> children = second.getChildren();
                        if (null == children) {
                            children = new ArrayList<MenuVo>();
                            second.setChildren(children);
                        }
                        MenuVo menuVo = convertToMenuVo(third);
                        if (null != menuVo) {
                            // 如果不是隐藏，则添加
                            if (null == menuVo.getHideFlag() || 1 != menuVo.getHideFlag().intValue()) {
                                children.add(menuVo);
                            }
                        }
                    }
                }
                // 左侧菜单栏
                List<MenuVo> leftMenuList = new ArrayList<MenuVo>();
                for (Map.Entry<Long, MenuVo> entry : leftMenuMap.entrySet()) {
                    leftMenuList.add(entry.getValue());
                }
                // 设置
                ServletActionContext.getRequest().setAttribute(MENU_ITEM_LIST, leftMenuList);
                // 获取用户信息
                DwUserInfo dwUserInfo = loginService.getUserInfoById(id);
                Object action = invocation.getAction();
                if (action instanceof MenuBaseAction) {
                    MenuBaseAction menuAction = (MenuBaseAction) action;
                    menuAction.setCurrentMenu(currentMenu);
                    menuAction.setDwUserInfo(dwUserInfo);
                }
                // 确认是否有权限
                if (hasAuthority(currentMenu, dwUserInfo)) {
                    return invocation.invoke();
                } else {
                    return "authorityDeny";
                }
            }
        }
        // 判定是否登陆了 未登录 打回登陆页面 用户登陆成功必须将UserID放入session
        String baseUrl = baseSatelliteUrl();
        String loginUrl = baseUrl + "login/login_info.htm";
        if ("GET".equalsIgnoreCase(ServletActionContext.getRequest().getMethod())) {
            String requestURL = ServletActionContext.getRequest().getRequestURL().toString();
            String queryString = ServletActionContext.getRequest().getQueryString();
            StringBuilder sb = new StringBuilder(requestURL);
            if (StringUtil.isNotBlank(queryString)) {
                sb.append("?").append(queryString);
            }
        }
        ActionContext.getContext().getValueStack().set("loginUrl", loginUrl);
        return Action.LOGIN;
    }

    /**
     * 确认用户是否包含权限
     * 
     * @param currentMenu 当前菜单
     * @param dwUserInfo 用户信息
     * @return true：包含 false：不包含
     */
    private boolean hasAuthority(ReportMenu currentMenu, DwUserInfo dwUserInfo) {
        if (null == dwUserInfo || null == dwUserInfo.getUserIdentity()) {
            return false;
        }
        // 如果为超级管理员，则可以使用任何功能
        if (UserAuthority.SUPER_ADMIN == dwUserInfo.getUserIdentity().intValue()) {
            return true;
        }
        if (null != currentMenu) {
            int authorityType = (null == currentMenu.getAuthorityType()) ? AuthorityType.REPORT : currentMenu.getAuthorityType();
            switch (authorityType) {
                case AuthorityType.REPORT:
                    String reportAuthorityStr = dwUserInfo.getReportAuthorityStr();
                    if (StringUtil.isNotBlank(reportAuthorityStr)) {
                        reportAuthorityStr = "," + reportAuthorityStr + ",";
                        String subStr = "," + currentMenu.getId() + ",";
                        // 如果权限存在，则可以继续访问
                        if (reportAuthorityStr.contains(subStr)) {
                            return true;
                        }
                    }
                    break;
                case AuthorityType.ADMIN:
                    // 如果为管理员，则可以继续访问
                    if (UserAuthority.ADMIN == dwUserInfo.getUserIdentity().intValue()) {
                        return true;
                    }
                case AuthorityType.ALL:
                    return true;
                default:
                    break;
            }
        }
        // 如果为没在菜单中出现的地址,管理员
        else {
            // 如果为管理员，则可以继续访问
            if (UserAuthority.ADMIN == dwUserInfo.getUserIdentity().intValue()) {
                return true;
            }
        }
        return false;
    }

    private MenuVo convertToMenuVo(ReportMenu reportMenu) {
        if (null == reportMenu) {
            return null;
        }
        MenuVo menu = new MenuVo();
        menu.setName(reportMenu.getName());
        menu.setUrl(reportMenu.getUrl());
        menu.setId(reportMenu.getId());
        menu.setTableName(reportMenu.getTableName());
        menu.setHideFlag(reportMenu.getHideFlag());
        return menu;
    }

    private Long getRootMenuId(Map<Long, ReportMenu> menuMap, ReportMenu current) {
        if (null == menuMap || null == current) {
            return null;
        }
        if (MenuLeval.SECOND.equals(current.getMenuLevel())) {
            return current.getParentId();
        }
        return getRootMenuId(menuMap, menuMap.get(current.getParentId()));
    }

    private String baseSatelliteUrl() {
        String baseUrl = ResourceUtil.getUrl("url.satellite").getDomain();
        if (StringUtil.isBlank(baseUrl)) {
            baseUrl = DEFAULT_LOGIN_URL_PREFIX;
        }
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }

    public void setMenuCacheTemplate(Cache menuCacheTemplate) {
        this.menuCacheTemplate = menuCacheTemplate;
    }

    public void setLoginCacheTemplate(Cache loginCacheTemplate) {
        this.loginCacheTemplate = loginCacheTemplate;
    }

}

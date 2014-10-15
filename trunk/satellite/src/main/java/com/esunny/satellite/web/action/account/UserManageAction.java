package com.esunny.satellite.web.action.account;

import java.util.List;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.platform.util.security.MD5;
import com.esunny.satellite.report.constant.ReportConstant.UserAuthority;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.service.intef.DwUserInfoService;
import com.esunny.satellite.vo.EditPasswordVo;
import com.esunny.satellite.web.action.MenuBaseAction;

public class UserManageAction extends MenuBaseAction {

    private static final long serialVersionUID = -826312608551878068L;

    private DwUserInfoService dwUserInfoService;

    private List<DwUserInfo>  userInfoList;

    private DwUserInfo        query;

    private EditPasswordVo    editPasswordVo;

    private String            alertMsg;

    public String showInit() {
        return SUCCESS;
    }

    /**
     * 查询用户信息列表
     * 
     * @return
     */
    public String queryUserList() {
        if (null == query) {
            query = new DwUserInfo();
        }
        userInfoList = dwUserInfoService.queryUserListByPaging(query);
        return SUCCESS;
    }

    /**
     * 显示添加/修改用户页面
     * 
     * @return
     */
    public String showEditDwUser() {
        if (null != query.getId()) {
            if (!dwUserInfoService.canOperateUser(this.getDwUserInfo(), query.getId())) {
                return AUTHORITY_DENY;
            }
            query = dwUserInfoService.selectDwUserInfoById(query.getId());
        }
        return SUCCESS;
    }

    /**
     * 添加/修改用户
     * 
     * @return
     */
    public String editDwUser() {
        if (null != query.getId()) {
            if (!dwUserInfoService.canOperateUser(this.getDwUserInfo(), query.getId())) {
                return AUTHORITY_DENY;
            }
            if (!canAddUser()) {
                return AUTHORITY_DENY;
            }
            dwUserInfoService.updateDwUserInfoById(query);
        } else {
            if (!canAddUser()) {
                return AUTHORITY_DENY;
            }
            // 设置默认密码（Abc123）
            query.setUserPassword(MD5.str2md5("Abc123"));
            dwUserInfoService.insertDwUserInfo(query);
        }
        return SUCCESS;
    }

    /**
     * 是否有添加用户的权限
     * 
     * @return
     */
    private boolean canAddUser() {
        if (null == this.getDwUserInfo()) {
            return false;
        }
        Integer loginAuth = this.getDwUserInfo().getUserIdentity();
        // 如果没有权限信息，不能操作
        if (null == loginAuth) {
            return false;
        }
        // 如果为超级管理员，则可以操作
        if (UserAuthority.SUPER_ADMIN == loginAuth.intValue()) {
            return true;
        }

        int editAuth = query.getUserIdentity();
        if (UserAuthority.ADMIN == loginAuth.intValue() && UserAuthority.USER == editAuth) {
            return true;
        }
        return false;
    }

    /**
     * 删除用户
     * 
     * @return
     */
    public String deleteDwUser() {
        if (null != query.getId()) {
            if (!dwUserInfoService.canOperateUser(this.getDwUserInfo(), query.getId())) {
                return AUTHORITY_DENY;
            }
            dwUserInfoService.deleteDwUserInfoById(query.getId());
        }
        return SUCCESS;
    }

    /**
     * 显示修改用户密码页面
     * 
     * @return
     */
    public String showEditUserPassword() {
        if (null == editPasswordVo) {
            editPasswordVo = new EditPasswordVo();
        }
        editPasswordVo.setId(this.getLogonUserId());
        return SUCCESS;

    }

    /**
     * 修改用户密码
     * 
     * @return
     */
    public String editUserPassword() {
        if (null == editPasswordVo || null == editPasswordVo.getId()) {
            alertMsg = "用户不存在！";
            return INPUT;
        }
        if (StringUtil.isBlank(editPasswordVo.getOldPwd())) {
            alertMsg = "原密码不能为空！";
            return INPUT;
        }
        if (StringUtil.isBlank(editPasswordVo.getNewPwd())) {
            alertMsg = "新密码不能为空！";
            return INPUT;
        }
        if (!StringUtil.equals(editPasswordVo.getNewPwd(), editPasswordVo.getNewPwdAgain())) {
            alertMsg = "两次密码不一致，请重新输入！";
            return INPUT;
        }
        DwUserInfo dwUserInfo = dwUserInfoService.selectDwUserInfoById(editPasswordVo.getId());
        if (null == dwUserInfo) {
            alertMsg = "用户不存在！";
            return INPUT;
        }
        if (!StringUtil.equals(dwUserInfo.getUserPassword(), MD5.str2md5(editPasswordVo.getOldPwd()))) {
            alertMsg = "原密码不正确，请重新输入！";
            return INPUT;
        }
        DwUserInfo updateInfo = new DwUserInfo();
        updateInfo.setId(editPasswordVo.getId());
        updateInfo.setUserPassword(MD5.str2md5(editPasswordVo.getNewPwd()));
        int upd = dwUserInfoService.updateDwUserInfoById(updateInfo);
        if (0 == upd) {
            alertMsg = "修改失败！";
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * @param dwUserInfoService the dwUserInfoService to set
     */
    public void setDwUserInfoService(DwUserInfoService dwUserInfoService) {
        this.dwUserInfoService = dwUserInfoService;
    }

    /**
     * @return the userInfoList
     */
    public List<DwUserInfo> getUserInfoList() {
        return userInfoList;
    }

    /**
     * @param userInfoList the userInfoList to set
     */
    public void setUserInfoList(List<DwUserInfo> userInfoList) {
        this.userInfoList = userInfoList;
    }

    /**
     * @return the query
     */
    public DwUserInfo getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(DwUserInfo query) {
        this.query = query;
    }

    /**
     * @return the editPasswordVo
     */
    public EditPasswordVo getEditPasswordVo() {
        return editPasswordVo;
    }

    /**
     * @param editPasswordVo the editPasswordVo to set
     */
    public void setEditPasswordVo(EditPasswordVo editPasswordVo) {
        this.editPasswordVo = editPasswordVo;
    }

    /**
     * @return the alertMsg
     */
    public String getAlertMsg() {
        return alertMsg;
    }

    /**
     * @param alertMsg the alertMsg to set
     */
    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

}

/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.impl;

import java.util.List;

import com.esunny.satellite.report.constant.ReportConstant.UserAuthority;
import com.esunny.satellite.report.dao.interf.DwUserInfoDao;
import com.esunny.satellite.report.entity.DwUserInfo;
import com.esunny.satellite.report.service.intef.DwUserInfoService;

/**
 * 数据仓库用户业务实现
 * 
 * @author Jet Xu 2012-5-25 上午11:00:21
 */
public class DwUserInfoServiceImpl implements DwUserInfoService {

    private DwUserInfoDao dwUserInfoDao;

    /**
     * 根据ID更新用户信息
     * 
     * @param dwUserInfo 用户信息
     * @return 更新条数
     */
    public int updateDwUserInfoById(DwUserInfo dwUserInfo) {
        if (null == dwUserInfo) {
            return 0;
        }
        return dwUserInfoDao.updateDwUserInfoById(dwUserInfo);
    }

    /**
     * 根据Id查询用户信息
     * 
     * @param id ID
     * @return 用户信息
     */
    public DwUserInfo selectDwUserInfoById(Long id) {
        return dwUserInfoDao.selectDwUserInfoById(id);
    }

    /**
     * 新增用户信息
     * 
     * @param dwUserInfo 用户信息
     */
    public void insertDwUserInfo(DwUserInfo dwUserInfo) {
        dwUserInfoDao.insertDwUserInfo(dwUserInfo);
    }

    /**
     * 查询用户信息列表（分页）
     * 
     * @param dwUserInfo 查询条件
     * @return 用户信息列表
     */
    public List<DwUserInfo> queryUserListByPaging(DwUserInfo dwUserInfo) {
        return dwUserInfoDao.queryUserListByPaging(dwUserInfo);
    }

    /**
     * 根据ID删除用户信息
     * 
     * @param id ID
     * @return 删除条数
     */
    public int deleteDwUserInfoById(Long id) {
        return dwUserInfoDao.deleteDwUserInfoById(id);
    }

    /**
     * 校验是否有操作该用户的权限
     * 
     * @param dwUserInfo 操作人
     * @param editor 被操作人ID
     * @return true：有权限 false：没有权限
     */
    public boolean canOperateUser(DwUserInfo dwUserInfo, Long editor) {
        if (null == dwUserInfo) {
            return false;
        }
        Integer loginAuth = dwUserInfo.getUserIdentity();
        // 如果没有权限信息，不能操作
        if (null == loginAuth) {
            return false;
        }
        // 如果为超级管理员，则可以操作
        if (UserAuthority.SUPER_ADMIN == loginAuth.intValue()) {
            return true;
        }
        DwUserInfo editorInfo = dwUserInfoDao.selectDwUserInfoById(editor);
        if (null != editorInfo && null != editorInfo.getUserIdentity()) {
            int editAuth = editorInfo.getUserIdentity();
            if (UserAuthority.ADMIN == loginAuth.intValue() && UserAuthority.USER == editAuth) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param dwUserInfoDao the dwUserInfoDao to set
     */
    public void setDwUserInfoDao(DwUserInfoDao dwUserInfoDao) {
        this.dwUserInfoDao = dwUserInfoDao;
    }

}

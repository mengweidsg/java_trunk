/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.report.service.intef;

import java.util.List;

import com.esunny.satellite.report.entity.DwUserInfo;

/**
 * 数据仓库用户业务接口
 * 
 * @author Jet Xu 2012-5-25 上午10:59:47
 */
public interface DwUserInfoService {

    /**
     * 校验是否有操作该用户的权限
     * 
     * @param dwUserInfo 操作人
     * @param editor 被操作人ID
     * @return true：有权限 false：没有权限
     */
    boolean canOperateUser(DwUserInfo dwUserInfo, Long editor);

    /**
     * 根据ID更新用户信息
     * 
     * @param dwUserInfo 用户信息
     * @return 更新条数
     */
    int updateDwUserInfoById(DwUserInfo dwUserInfo);

    /**
     * 根据Id查询用户信息
     * 
     * @param id ID
     * @return 用户信息
     */
    DwUserInfo selectDwUserInfoById(Long id);

    /**
     * 新增用户信息
     * 
     * @param dwUserInfo 用户信息
     */
    void insertDwUserInfo(DwUserInfo dwUserInfo);

    /**
     * 查询用户信息列表（分页）
     * 
     * @param dwUserInfo 查询条件
     * @return 用户信息列表
     */
    List<DwUserInfo> queryUserListByPaging(DwUserInfo dwUserInfo);

    /**
     * 根据ID删除用户信息
     * 
     * @param id ID
     * @return 删除条数
     */
    int deleteDwUserInfoById(Long id);
}

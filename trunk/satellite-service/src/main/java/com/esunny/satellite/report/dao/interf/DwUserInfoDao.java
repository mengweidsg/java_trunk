package com.esunny.satellite.report.dao.interf;

import java.util.List;

import com.esunny.satellite.report.entity.DwUserInfo;

public interface DwUserInfoDao {

    /**
     * 根据ID删除用户信息
     * 
     * @param id ID
     * @return 删除条数
     */
    int deleteDwUserInfoById(Long id);

    /**
     * 新增用户信息
     * 
     * @param dwUserInfo 用户信息
     */
    void insertDwUserInfo(DwUserInfo dwUserInfo);

    /**
     * 根据ID更新用户信息
     * 
     * @param dwUserInfo 用户信息
     * @return 更新条数
     */
    int updateDwUserInfoById(DwUserInfo dwUserInfo);

    /**
     * 查询用户信息列表（分页）
     * 
     * @param dwUserInfo 查询条件
     * @return 用户信息列表
     */
    List<DwUserInfo> queryUserListByPaging(DwUserInfo dwUserInfo);

    /**
     * 统计用户条数
     * 
     * @param dwUserInfo 查询条件
     * @return 用户条数
     */
    int countUserListByPaging(DwUserInfo dwUserInfo);

    /**
     * 根据Id查询用户信息
     * 
     * @param id ID
     * @return 用户信息
     */
    DwUserInfo selectDwUserInfoById(Long id);

    /**
     * 根据用户Id查询用户信息
     * 
     * @param userId 用户Id
     * @return 用户信息
     */
    DwUserInfo selectDwUserInfoByUserId(String userId);
}

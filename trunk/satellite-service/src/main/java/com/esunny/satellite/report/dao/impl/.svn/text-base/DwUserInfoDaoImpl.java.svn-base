package com.esunny.satellite.report.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.esunny.platform.util.lang.StringUtil;
import com.esunny.satellite.report.dao.interf.DwUserInfoDao;
import com.esunny.satellite.report.entity.DwUserInfo;

public class DwUserInfoDaoImpl extends SqlMapClientDaoSupport implements DwUserInfoDao {

    public int deleteDwUserInfoById(Long id) {
        if (null == id) {
            return 0;
        }
        int rows = getSqlMapClientTemplate().delete("DW_USER_INFO.deleteDwUserInfoById", id);
        return rows;
    }

    public void insertDwUserInfo(DwUserInfo dwUserInfo) {
        getSqlMapClientTemplate().insert("DW_USER_INFO.insertDwUserInfo", dwUserInfo);
    }

    public int updateDwUserInfoById(DwUserInfo dwUserInfo) {
        if (null == dwUserInfo || null == dwUserInfo.getId()) {
            return 0;
        }
        int rows = getSqlMapClientTemplate().update("DW_USER_INFO.updateDwUserInfoById", dwUserInfo);
        return rows;
    }

    @SuppressWarnings("unchecked")
    public List<DwUserInfo> queryUserListByPaging(DwUserInfo dwUserInfo) {
        dwUserInfo.doPage(this.countUserListByPaging(dwUserInfo));
        List<DwUserInfo> list = getSqlMapClientTemplate().queryForList("DW_USER_INFO.queryUserListByPaging", dwUserInfo);
        return list;
    }

    public int countUserListByPaging(DwUserInfo dwUserInfo) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("DW_USER_INFO.countUserListByPaging",
                                                                           dwUserInfo);
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DwUserInfo selectDwUserInfoById(Long id) {
        if (null == id) {
            return null;
        }
        DwUserInfo query = new DwUserInfo();
        query.setId(id);
        List<DwUserInfo> list = getSqlMapClientTemplate().queryForList("DW_USER_INFO.queryUserListByPaging", query);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DwUserInfo selectDwUserInfoByUserId(String userId) {
        if (StringUtil.isBlank(userId)) {
            return null;
        }
        DwUserInfo query = new DwUserInfo();
        query.setUserId(userId);
        List<DwUserInfo> list = getSqlMapClientTemplate().queryForList("DW_USER_INFO.queryUserListByPaging", query);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}

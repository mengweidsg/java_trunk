package com.esunny.satellite.remote.dao;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.esunny.platform.util.test.BaseTestCase;
import com.esunny.satellite.remote.dao.interf.DwEsitePagePvInfoDao;

@ContextConfiguration(locations = { "classpath:applicationContext-satellite-test.xml" })
public class DwEsitePagePvInfoDaoTest extends BaseTestCase {

    @Autowired
    private DwEsitePagePvInfoDao dwEsitePagePvInfoDao;

    @Test
    public void testInsertDword() throws UnsupportedEncodingException {
        System.out.println(dwEsitePagePvInfoDao.queryTop10PagePvByUserIdFromCache(1));
    }
}

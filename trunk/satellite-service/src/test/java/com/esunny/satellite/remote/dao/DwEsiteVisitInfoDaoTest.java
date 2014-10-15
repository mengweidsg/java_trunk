package com.esunny.satellite.remote.dao;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.esunny.platform.util.test.BaseTestCase;
import com.esunny.satellite.remote.dao.interf.DwEsiteVisitInfoDao;

@ContextConfiguration(locations = { "classpath:applicationContext-satellite-test.xml" })
public class DwEsiteVisitInfoDaoTest extends BaseTestCase {

    @Autowired
    private DwEsiteVisitInfoDao dwEsiteVisitInfoDao;

    @Test
    public void testInsertDword() throws UnsupportedEncodingException {
        System.out.println(dwEsiteVisitInfoDao.queryLatestNVisitListFromCache(1, 30));
    }
}

package com.esunny.satellite.remote.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.esunny.platform.util.test.BaseTestCase;
import com.esunny.satellite.remote.dao.interf.DwEsitePvDayDao;

@ContextConfiguration(locations = { "classpath:applicationContext-satellite-test.xml" })
public class DwEsitePvDayDaoTest extends BaseTestCase {

    @Autowired
    private DwEsitePvDayDao dwEsitePvDayDao;

    @Test
    public void testInsertDword() throws UnsupportedEncodingException {
        System.out.println(dwEsitePvDayDao.selectPvDayInfoByUserIdDateFromCache(20181535, new Date()).getPv());
    }
}

package com.esunny.satellite.remote.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.esunny.platform.satellite.common.model.DailyEsiteVisitModel;
import com.esunny.platform.satellite.common.model.EsiteHistoryPVInfoModel;
import com.esunny.platform.satellite.common.model.EsitePagePVInfoModel;
import com.esunny.platform.satellite.common.model.EsiteVisitInfoModel;
import com.esunny.platform.satellite.common.model.SatelliteQuery;
import com.esunny.platform.satellite.service.interf.EsiteVisitInfoService;
import com.esunny.platform.util.test.BaseTestCase;

@ContextConfiguration(locations = { "classpath:applicationContext-satellite-test.xml" })
public class EsiteVisitInfoServiceTest extends BaseTestCase {

    @Autowired
    private EsiteVisitInfoService esiteVisitInfoService;

    public void testQueryTodayVisitInfo() throws UnsupportedEncodingException {
        DailyEsiteVisitModel model = esiteVisitInfoService.queryTodayVisitInfo(19732261);
        if (null != model) {
            System.out.println(model.getAll().getPv());
            System.out.println(model.getAll().getVisit());
            System.out.println(model.getAll().getPvPerVisit());
            System.out.println(model.getAll().getDate());

            System.out.println(model.getMax().getPv());
            System.out.println(model.getMax().getVisit());
            System.out.println(model.getMax().getPvPerVisit());
            System.out.println(model.getMax().getDate());

            System.out.println(model.getToday().getPv());
            System.out.println(model.getToday().getVisit());
            System.out.println(model.getToday().getPvPerVisit());
            System.out.println(model.getToday().getDate());
        } else {
            System.out.println("result is null!");
        }
    }

    @Test
    public void testQueryHistoryVisitInfo() throws UnsupportedEncodingException {
        long userId = 111111111114l;
        SatelliteQuery query = new SatelliteQuery();
        EsiteHistoryPVInfoModel model = esiteVisitInfoService.queryHistoryVisitInfo(userId, query);
        if (null != model) {
            System.out.println(model.getHistoryInfoList());
            for (EsiteVisitInfoModel model2 : model.getHistoryInfoList()) {
                System.out.println(model2.getPv());
            }
        } else {
            System.out.println("result is null!");
        }
    }

    public void testQueryTop10EsitePagePvInfo() throws UnsupportedEncodingException {
        long userId = 19732261;
        List<EsitePagePVInfoModel> modelList = esiteVisitInfoService.queryTop10EsitePagePvInfo(userId);
        if (null != modelList) {
            System.out.println(modelList.size());
        } else {
            System.out.println("result is null!");
        }
    }
}

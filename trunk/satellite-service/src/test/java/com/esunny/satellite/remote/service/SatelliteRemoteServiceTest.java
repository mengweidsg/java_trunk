/*
 * Copyright 2010-2011 ESunny.com All right reserved. This software is the confidential and proprietary information of
 * ESunny.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ESunny.com.
 */
package com.esunny.satellite.remote.service;

import java.util.Date;

import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.esunny.platform.satellite.common.model.HomePageShowInfoModel;
import com.esunny.platform.satellite.service.interf.SatelliteRemoteService;
import com.esunny.platform.util.remote.hessian.SecureHessianProxyFactory;

/**
 * 远程接口测试类
 * 
 * @author Jet Xu 2012-6-19 上午9:40:03
 */
public class SatelliteRemoteServiceTest {

    public static void main(String[] args) {
        SecureHessianProxyFactory proxyFac = new SecureHessianProxyFactory();
        proxyFac.setReadTimeout(1000);
        proxyFac.setSecureKey("FD562FDSA2423KL23HKL67224JF");
        HessianProxyFactoryBean proxyFacBean = new HessianProxyFactoryBean();
        proxyFacBean.setServiceUrl("http://192.168.1.62:7011/remoting/satelliteRemoteService");
        proxyFacBean.setServiceInterface(SatelliteRemoteService.class);
        proxyFacBean.setProxyFactory(proxyFac);
        proxyFacBean.afterPropertiesSet();
        SatelliteRemoteService service = (SatelliteRemoteService) proxyFacBean.getObject();
        HomePageShowInfoModel model = service.selectHomePageShowInfo(new Date());
        if (null != model) {
            System.out.println(model.getTime());
            System.out.println(model.getMemberRegDailyNum());
            System.out.println(model.getPublishBuyOfferDailyNum());
            System.out.println(model.getPublishSellOfferDailyNum());
        } else {
            System.out.println("info empty!");
        }
    }
}

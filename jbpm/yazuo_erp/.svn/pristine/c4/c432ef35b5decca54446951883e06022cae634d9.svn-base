/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package mkt.service;


import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Test;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.mkt.service.MktShopSurveyService;

/**
 * @Description 
 */
public class MktShopSurveyServiceTest extends  JUnitDaoBaseWithTrans {
	@Resource MktShopSurveyService mktShopSurveyService;
	
	@Test
	public void getMerchantsForSurvey(){
		this.printJson(mktShopSurveyService.getMerchantsForSurvey(10219));
	}
	
	@Test
	public void getMerchantVOFromCRM(){
		this.printJson(mktShopSurveyService.getMerchantVOFromCRM(2333));
	}
	@Test
	public void testArrays(){
		logger.info(Arrays.asList(0));
	}
}
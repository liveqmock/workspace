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

package fes.service;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import static org.junit.Assert.fail;

import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import com.yazuo.erp.fes.service.FesPlanService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesPlanServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesPlanServiceTest.class);
	@Resource
	private FesPlanService fesPlanService;
	
	@Test
	public void testSaveFesPlan (){
		//不允许为空的字段： user_id,merchant_id,title,start_time,is_remind,is_send_sms,plans_source,status,is_enable,insert_by,insert_time,update_by,update_time,
		FesPlanVO fesPlan = new FesPlanVO();
		//int id = fesPlanService.saveFesPlan(fesPlan);
		//Assert.assertTrue(id>0);
	}
	
}
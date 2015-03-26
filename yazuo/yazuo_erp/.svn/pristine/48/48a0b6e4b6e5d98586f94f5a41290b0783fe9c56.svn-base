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
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesOpenCardApplicationServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesOpenCardApplicationServiceTest.class);
	@Resource
	private FesOpenCardApplicationService fesOpenCardApplicationService;
	
	@Test
	public void testSaveFesOpenCardApplication (){
		//不允许为空的字段： process_id,merchant_id,open_card_application_status,is_enable,insert_by,insert_time,update_by,update_time,
		FesOpenCardApplicationVO fesOpenCardApplication = new FesOpenCardApplicationVO();
		int id = fesOpenCardApplicationService.saveFesOpenCardApplication(fesOpenCardApplication);
		Assert.assertTrue(id>0);
	}
	
}
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

import com.yazuo.erp.base.JsonResult;
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
import com.yazuo.erp.fes.service.FesMarketingActivityService;
import com.yazuo.erp.system.vo.SysUserVO;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesMarketingActivityServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesMarketingActivityServiceTest.class);
	@Resource
	private FesMarketingActivityService fesMarketingActivityService;
	
	@Test
	public void testSaveFesMarketingActivity (){
		//不允许为空的字段： merchant_id,activity_title,attachment_id,applicant_by,application_time,marketing_activity_status,is_enable,insert_by,insert_time,update_by,update_time,
		FesMarketingActivityVO fesMarketingActivity = new FesMarketingActivityVO();
		JsonResult result = fesMarketingActivityService.saveFesMarketingActivity(fesMarketingActivity, new SysUserVO());
		Assert.assertTrue(result.getFlag()>0);
	}
	
}
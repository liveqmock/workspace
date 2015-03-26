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
import com.yazuo.erp.fes.service.FesOpenCardApplicationDtlService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesOpenCardApplicationDtlServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesOpenCardApplicationDtlServiceTest.class);
	@Resource
	private FesOpenCardApplicationDtlService fesOpenCardApplicationDtlService;
	
	@Test
	public void testSaveFesOpenCardApplicationDtl (){
		//不允许为空的字段： application_id,card_name,card_type,card_amount,insert_by,insert_time,
		FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl = new FesOpenCardApplicationDtlVO();
		int id = fesOpenCardApplicationDtlService.saveFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
		Assert.assertTrue(id>0);
	}
	
}
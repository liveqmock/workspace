/**
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
import com.yazuo.erp.fes.service.FesStowageDtlService;
import com.yazuo.erp.fes.service.FesStowageService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesStowageDtlServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesStowageDtlServiceTest.class);
	@Resource private FesStowageDtlService fesStowageDtlService;
	@Resource private FesStowageService fesStowageService;
	
	@Test
	public void testSaveFesStowageDtl (){
		//不允许为空的字段： stowage_id,insert_by,insert_time,
		FesStowageDtlVO fesStowageDtl = new FesStowageDtlVO();
		int id = fesStowageDtlService.saveFesStowageDtl(fesStowageDtl);
		Assert.assertTrue(id>0);
	}
	@Test
	public void getSysUserMerchantTel (){
		System.out.println(fesStowageService.getSysUserMerchantTel(new FesStowageVO(){{setId(67);}}));
	}
	
}
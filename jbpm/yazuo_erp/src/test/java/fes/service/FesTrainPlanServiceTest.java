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
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.system.vo.SysUserVO;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesTrainPlanServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesTrainPlanServiceTest.class);
	@Resource
	private FesTrainPlanService fesTrainPlanService;
	
	@Test
	public void testSaveFesTrainPlan (){
		//不允许为空的字段： process_id,begin_time,end_time,address,trainer,is_enable,insert_by,insert_time,update_by,update_time,
		FesTrainPlanVO fesTrainPlan = new FesTrainPlanVO();
		SysUserVO sysUserVO = new SysUserVO();
		sysUserVO.setId(1);
		FesTrainPlanVO fesTrainPlanVO = fesTrainPlanService.saveFesTrainPlan(fesTrainPlan, sysUserVO);
		this.printJson(fesTrainPlanVO);
		Assert.assertTrue(fesTrainPlanVO!=null);
	}
	
}
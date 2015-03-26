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

package fes.dao;

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

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesTrainPlanDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesTrainPlanDaoTest.class);
	@Resource
	private FesTrainPlanDao fesTrainPlanDao;
	
	@Test
	public void testSaveFesTrainPlan (){
		FesTrainPlanVO fesTrainPlan = new FesTrainPlanVO();
		int id = fesTrainPlanDao.saveFesTrainPlan(fesTrainPlan);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesTrainPlans (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesTrainPlan (FesTrainPlanVO fesTrainPlan){
		
	}
	@Test
	public void testBatchUpdateFesTrainPlansToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesTrainPlansToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesTrainPlanById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesTrainPlanByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesTrainPlanById(Integer id){
		
	}
	@Test
	public void testGetFesTrainPlans (FesTrainPlanVO fesTrainPlan){
		
	}
	@Test
	public void testGetFesTrainPlansMap (FesTrainPlanVO fesTrainPlan){
		
	}
	
}
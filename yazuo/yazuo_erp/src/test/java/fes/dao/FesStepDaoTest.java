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
public class FesStepDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesStepDaoTest.class);
	@Resource
	private FesStepDao fesStepDao;
	
	@Test
	public void testSaveFesStep (){
		FesStepVO fesStep = new FesStepVO();
		int id = fesStepDao.saveFesStep(fesStep);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesSteps (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesStep (FesStepVO fesStep){
		
	}
	@Test
	public void testBatchUpdateFesStepsToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesStepsToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesStepById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesStepByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesStepById(Integer id){
		
	}
	@Test
	public void testGetFesSteps (FesStepVO fesStep){
		
	}
	@Test
	public void testGetFesStepsMap (FesStepVO fesStep){
		
	}
	
}
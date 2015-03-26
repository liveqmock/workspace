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
public class FesMarketingActivityDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesMarketingActivityDaoTest.class);
	@Resource
	private FesMarketingActivityDao fesMarketingActivityDao;
	
	@Test
	public void testSaveFesMarketingActivity (){
		FesMarketingActivityVO fesMarketingActivity = new FesMarketingActivityVO();
		int id = fesMarketingActivityDao.saveFesMarketingActivity(fesMarketingActivity);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesMarketingActivitys (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesMarketingActivity (FesMarketingActivityVO fesMarketingActivity){
		
	}
	@Test
	public void testBatchUpdateFesMarketingActivitysToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesMarketingActivitysToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesMarketingActivityById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesMarketingActivityByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesMarketingActivityById(Integer id){
		
	}
	@Test
	public void testGetFesMarketingActivitys (FesMarketingActivityVO fesMarketingActivity){
		
	}
	@Test
	public void testGetFesMarketingActivitysMap (FesMarketingActivityVO fesMarketingActivity){
		
	}
	
}
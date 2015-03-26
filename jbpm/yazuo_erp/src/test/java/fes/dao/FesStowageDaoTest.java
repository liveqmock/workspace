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
public class FesStowageDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesStowageDaoTest.class);
	@Resource
	private FesStowageDao fesStowageDao;
	
	@Test
	public void testSaveFesStowage (){
		FesStowageVO fesStowage = new FesStowageVO();
		int id = fesStowageDao.saveFesStowage(fesStowage);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesStowages (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesStowage (FesStowageVO fesStowage){
		
	}
	@Test
	public void testBatchUpdateFesStowagesToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesStowagesToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesStowageById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesStowageByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesStowageById(Integer id){
		
	}
	@Test
	public void testGetFesStowages (FesStowageVO fesStowage){
		
	}
	@Test
	public void testGetFesStowagesMap (FesStowageVO fesStowage){
		
	}
	
}
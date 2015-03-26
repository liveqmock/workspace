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
public class FesOnlineProgramDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesOnlineProgramDaoTest.class);
	@Resource
	private FesOnlineProgramDao fesOnlineProgramDao;
	
	@Test
	public void testSaveFesOnlineProgram (){
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		int id = fesOnlineProgramDao.saveFesOnlineProgram(fesOnlineProgram);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesOnlinePrograms (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesOnlineProgram (FesOnlineProgramVO fesOnlineProgram){
		
	}
	@Test
	public void testBatchUpdateFesOnlineProgramsToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesOnlineProgramsToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesOnlineProgramById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesOnlineProgramByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesOnlineProgramById(Integer id){
		
	}
	@Test
	public void testGetFesOnlinePrograms (FesOnlineProgramVO fesOnlineProgram){
		
	}
	@Test
	public void testGetFesOnlineProgramsMap (FesOnlineProgramVO fesOnlineProgram){
		
	}
	
}
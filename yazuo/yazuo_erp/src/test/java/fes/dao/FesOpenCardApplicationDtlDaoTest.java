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
public class FesOpenCardApplicationDtlDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesOpenCardApplicationDtlDaoTest.class);
	@Resource
	private FesOpenCardApplicationDtlDao fesOpenCardApplicationDtlDao;
	
	@Test
	public void testSaveFesOpenCardApplicationDtl (){
		FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl = new FesOpenCardApplicationDtlVO();
		int id = fesOpenCardApplicationDtlDao.saveFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertFesOpenCardApplicationDtls (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateFesOpenCardApplicationDtl (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		
	}
	@Test
	public void testBatchUpdateFesOpenCardApplicationDtlsToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateFesOpenCardApplicationDtlsToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteFesOpenCardApplicationDtlById (Integer id){
		
	}
	@Test
	public void testBatchDeleteFesOpenCardApplicationDtlByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetFesOpenCardApplicationDtlById(Integer id){
		
	}
	@Test
	public void testGetFesOpenCardApplicationDtls (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		
	}
	@Test
	public void testGetFesOpenCardApplicationDtlsMap (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		
	}
	
}
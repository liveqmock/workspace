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

package system.dao;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

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
public class SysCustomerComplaintDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(SysCustomerComplaintDaoTest.class);
	@Resource
	private SysCustomerComplaintDao sysCustomerComplaintDao;
	
	@Test
	public void testSaveSysCustomerComplaint (){
		SysCustomerComplaintVO sysCustomerComplaint = new SysCustomerComplaintVO();
		int id = sysCustomerComplaintDao.saveSysCustomerComplaint(sysCustomerComplaint);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertSysCustomerComplaints (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateSysCustomerComplaint (SysCustomerComplaintVO sysCustomerComplaint){
		
	}
	@Test
	public void testBatchUpdateSysCustomerComplaintsToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateSysCustomerComplaintsToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteSysCustomerComplaintById (Integer id){
		
	}
	@Test
	public void testBatchDeleteSysCustomerComplaintByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetSysCustomerComplaintById(Integer id){
		
	}
	@Test
	public void testGetSysCustomerComplaints (SysCustomerComplaintVO sysCustomerComplaint){
		
	}
	@Test
	public void testGetSysCustomerComplaintsMap (SysCustomerComplaintVO sysCustomerComplaint){
		
	}
	
}
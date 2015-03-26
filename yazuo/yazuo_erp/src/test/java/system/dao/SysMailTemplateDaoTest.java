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
public class SysMailTemplateDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(SysMailTemplateDaoTest.class);
	@Resource
	private SysMailTemplateDao sysMailTemplateDao;
	
	@Test
	public void testSaveSysMailTemplate (){
		SysMailTemplateVO sysMailTemplate = new SysMailTemplateVO();
		int id = sysMailTemplateDao.saveSysMailTemplate(sysMailTemplate);
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsertSysMailTemplates (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdateSysMailTemplate (SysMailTemplateVO sysMailTemplate){
		
	}
	@Test
	public void testBatchUpdateSysMailTemplatesToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdateSysMailTemplatesToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDeleteSysMailTemplateById (Integer id){
		
	}
	@Test
	public void testBatchDeleteSysMailTemplateByIds (List<Integer> ids){
		
	}
	@Test
	public void testGetSysMailTemplateById(Integer id){
		
	}
	@Test
	public void testGetSysMailTemplates (SysMailTemplateVO sysMailTemplate){
		
	}
	@Test
	public void testGetSysMailTemplatesMap (SysMailTemplateVO sysMailTemplate){
		
	}
	
}
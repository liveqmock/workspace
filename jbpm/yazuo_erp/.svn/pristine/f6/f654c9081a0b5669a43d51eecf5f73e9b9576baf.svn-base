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

package syn.service;

import java.util.*;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

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
import com.yazuo.erp.syn.service.SynHealthDegreeService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class SynHealthDegreeServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(SynHealthDegreeServiceTest.class);
	@Resource
	private SynHealthDegreeService synHealthDegreeService;
	
	@Test
	public void testSaveSynHealthDegree (){
		//不允许为空的字段： merchant_id,target_type,target_value,actual_value,health_degree,report_time,update_by,update_time,
		SynHealthDegreeVO synHealthDegree = new SynHealthDegreeVO();
		int id = synHealthDegreeService.saveSynHealthDegree(synHealthDegree);
		Assert.assertTrue(id>0);
	}
	
}
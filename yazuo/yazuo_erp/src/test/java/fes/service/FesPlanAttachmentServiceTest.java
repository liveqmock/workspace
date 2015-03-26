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
import com.yazuo.erp.fes.service.FesPlanAttachmentService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesPlanAttachmentServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesPlanAttachmentServiceTest.class);
	@Resource
	private FesPlanAttachmentService fesPlanAttachmentService;
	
	@Test
	public void testSaveFesPlanAttachment (){
		//不允许为空的字段： plan_id,attachment_id,insert_by,insert_time,
		FesPlanAttachmentVO fesPlanAttachment = new FesPlanAttachmentVO();
		int id = fesPlanAttachmentService.saveFesPlanAttachment(fesPlanAttachment);
		Assert.assertTrue(id>0);
	}
	
}
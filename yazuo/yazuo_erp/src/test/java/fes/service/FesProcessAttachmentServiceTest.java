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
import com.yazuo.erp.fes.service.FesProcessAttachmentService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class FesProcessAttachmentServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(FesProcessAttachmentServiceTest.class);
	@Resource
	private FesProcessAttachmentService fesProcessAttachmentService;
	
	@Test
	public void testSaveFesProcessAttachment (){
		//不允许为空的字段： process_id,attachment_id,process_attachment_type,insert_by,insert_time,
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		int id = fesProcessAttachmentService.saveFesProcessAttachment(fesProcessAttachment);
		Assert.assertTrue(id>0);
	}
	
}
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

package system.service;

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
import com.yazuo.erp.system.service.SysAttachmentService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class SysAttachmentServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(SysAttachmentServiceTest.class);
	@Resource
	private SysAttachmentService sysAttachmentService;
	
	@Test
	public void testSaveSysAttachment (){
		//不允许为空的字段： attachment_name,attachment_type,attachment_suffix,module_type,is_enable,is_downloadable,insert_by,insert_time,update_by,update_time,
		SysAttachmentVO sysAttachment = new SysAttachmentVO();
		int id = sysAttachmentService.saveSysAttachment(sysAttachment);
		Assert.assertTrue(id>0);
	}
	
}
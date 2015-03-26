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
import com.yazuo.erp.system.service.SysMailTemplateService;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class SysMailTemplateServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(SysMailTemplateServiceTest.class);
	@Resource
	private SysMailTemplateService sysMailTemplateService;
	
	@Test
	public void testSaveSysMailTemplate (){
		//不允许为空的字段： mail_template_type,name,title,content,is_enable,insert_by,insert_time,update_by,update_time,
		SysMailTemplateVO sysMailTemplate = new SysMailTemplateVO();
		int id = sysMailTemplateService.saveSysMailTemplate(sysMailTemplate);
		Assert.assertTrue(id>0);
	}
	
}
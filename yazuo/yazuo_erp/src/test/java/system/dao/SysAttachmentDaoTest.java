/**
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
public class SysAttachmentDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(SysAttachmentDaoTest.class);
	@Resource
	private SysAttachmentDao sysAttachmentDao;
	@Test
	public void testGetSysAttachmentById(){
		SysAttachmentVO sysAttachement = this.sysAttachmentDao.getSysAttachmentById(1286);
		this.printJson(sysAttachement);
	}
	
}
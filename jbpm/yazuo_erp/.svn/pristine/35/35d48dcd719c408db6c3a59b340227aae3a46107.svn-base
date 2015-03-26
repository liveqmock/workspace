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

package req.dao;

import java.util.*;

import com.yazuo.erp.req.vo.*;
import com.yazuo.erp.req.dao.*;

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
public class ReqRequirementDaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(ReqRequirementDaoTest.class);
	@Resource
	private ReqRequirementDao reqRequirementDao;
	
	@Test
	public void testSaveReqRequirement (){
		//不允许为空的字段： name,requirement_plan_type,plan_time,is_requirements_review,
		//is_product_review,leader,requirement_status,is_enable,insert_by,insert_time,update_by,update_time,
		ReqRequirementVO reqRequirement = new ReqRequirementVO();
		reqRequirement.setProjectIds(null);
		reqRequirement.setName("name");
		reqRequirement.setRequirementPlanType("plantype1");
		reqRequirement.setPlanTime(new Date());
		reqRequirement.setIsRequirementsReview("yes");
		reqRequirement.setIsProductReview("yes");
		reqRequirement.setLeader("leader");
		reqRequirement.setRequirementStatus("1");
		reqRequirement.setIsEnable("1");
		reqRequirement.setInsertBy(99999);
		reqRequirement.setInsertTime(new Date());
		reqRequirement.setUpdateBy(9999);
		reqRequirement.setUpdateTime(new Date());
		int id = reqRequirementDao.saveReqRequirement(reqRequirement);
		Assert.assertTrue(id>0);
	}
	
}
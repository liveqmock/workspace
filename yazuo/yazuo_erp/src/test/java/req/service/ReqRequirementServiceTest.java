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

package req.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.req.service.ReqRequirementService;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.system.service.SysDictionaryService;
/**
 * @Description 
 */
public class ReqRequirementServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(ReqRequirementServiceTest.class);
	@Resource
	private ReqRequirementService reqRequirementService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	
	@Test
	@Rollback(false)
	public void testSaveReqRequirement (){
		//不允许为空的字段： name,requirement_plan_type,plan_time,is_requirements_review,
		//is_product_review,leader,requirement_status,is_enable,insert_by,insert_time,update_by,update_time,
		ReqRequirementVO reqRequirement = new ReqRequirementVO();
		reqRequirement.setProjectIds(null);
		reqRequirement.setName("name1");
		reqRequirement.setRequirementPlanType("1");
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
//		reqRequirement.setId(8);
		int id = reqRequirementService.saveReqRequirement(reqRequirement);
		Assert.assertTrue(id>0);
	}
	@Test
	@Rollback(true)
	public void testGetComplexRequirementByJoin (){
		ReqRequirementVO reqRequirement = new ReqRequirementVO();
//    	reqRequirement.setName("name1");
//		reqRequirement.setIsProductReview("1");
		reqRequirement.setPageNumber(1);
		reqRequirement.setPageSize(10);
		reqRequirement.setSortColumns("id desc");
		Page<ReqRequirementVO> page = reqRequirementService.getComplexRequirementByJoin(reqRequirement);
		for (ReqRequirementVO reqRequirementVO : page) {
			LOG.info(reqRequirementVO.getId());
		}
		LOG.info("-------------");
		reqRequirement.setPageNumber(2);
		page = reqRequirementService.getComplexRequirementByJoin(reqRequirement);
		for (ReqRequirementVO reqRequirementVO : page) {
			LOG.info(reqRequirementVO.getId());
		}
		Assert.assertTrue(page!=null);
		Assert.assertTrue(page.size()>0);
	}
	@Test
	@Rollback(true)
	public void testDeleteReqRequirementById (){
		int a = reqRequirementService.deleteReqRequirementById(8);
		Assert.assertTrue(a>0);
	}
	
	@Test
	@Rollback(true)
	public void testQuerySysDictionaryByTypeStd (){
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByTypeStd(Constant.DIC_TYPE_PLAN_TYPE);
		List<Map<String, Object>> list2 = sysDictionaryService.querySysDictionaryByTypeStd(Constant.DIC_TYPE_PORJECT_STATE);
		for (Map<String, Object> map : list) {
			LOG.info(map);
		}
		for (Map<String, Object> map : list2) {
			LOG.info(map);
		}
		Assert.assertTrue(list!=null);
	}
	@Test
	@Rollback(true)
	public void testUpdateRequirementStatusIfOverTime(){
		int row = this.reqRequirementService.updateRequirementStatusIfOverTime();
		Assert.assertTrue(row!=0);
	}
	
	
}
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.method.HandlerMethod;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.vo.FesTrainPlanVO;
import com.yazuo.erp.system.service.SysCustomerComplaintService;
import com.yazuo.erp.system.vo.SysCustomerComplaintVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 
 */
public class SysCustomerComplaintServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(SysCustomerComplaintServiceTest.class);
	@Resource
	private SysCustomerComplaintService sysCustomerComplaintService;
	@Resource
	private FesOnlineProcessController fesOnlineProcessController;
	
	@Test
	@Rollback(false)
	public void getSysCustomerComplaintsMap (){
		SysCustomerComplaintVO sysCustomerComplaint = new SysCustomerComplaintVO();
		sysCustomerComplaint.setId(1);
//		List<Map<String,Object>> list = sysCustomerComplaintService.getSysCustomerComplaintsMap(sysCustomerComplaint);
//		LOG.info("begin");
//		for (Map<String, Object> map : list) {
//			LOG.info(map);
//			Object[] strs = (Object[])map.get("customer_complaint_type");
//			LOG.info(strs);  //报错 can not cast
//			Object o = map.get("customer_complaint_type");
//			JSONObject jsonObject = new JSONObject();
//			JSONObject json = jsonObject.fromObject(o);
//			LOG.info(json); //报错
//			LOG.info(o);
//			LOG.info(map.get("customer_complaint_type"));
//			LOG.info(map.get("customer_complaint_type").getClass());
//		}
		LOG.info("end");
//		this.printJson(list);
	}
	
	/**
	 * 保存修改培训计划
	 */
	@Test
	public void saveFesTrainPlan() throws Exception {
	
//		String fesTrainPlan = "{\"processId\":\"31\", \"address\":\"111啊时代发生地方\",\"beginTime\":1405937603000,\"content\":\"111阿桑地方\",\"endTime\":1405937603000,\"trainer\":\"1阿桑地方\"}";
//		FesTrainPlanVO fesTrainPlanVO = this.mapper.readValue(fesTrainPlan.getBytes(), FesTrainPlanVO.class);
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		SysUserVO sessionUser = new SysUserVO();
//		sessionUser.setId(109);
//		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
//		fesOnlineProcessController.saveFesTrainPlan(fesTrainPlanVO, request);
//		this.printJson(fesTrainPlanVO);
	}
	
	@Test
	@Rollback(false)
	public void saveSysCustomerComplaint (){
		//不允许为空的字段： merchant_id,customer_complaint_type,complaint_title,complaint_time,customer_complaint_status,is_enable,insert_by,insert_time,update_by,update_time,
		JsonResult.getJsonString(new SysCustomerComplaintVO());
//		SysCustomerComplaintVO sysCustomerComplaint = new SysCustomerComplaintVO();
//		sysCustomerComplaint.setId(1);
//		sysCustomerComplaint.setCustomerComplaintType(new String[]{"aa","bb"});
//		int id = sysCustomerComplaintService.updateSysCustomerComplaint(sysCustomerComplaint);
//		this.printJson(sysCustomerComplaint);
//		Assert.assertTrue(id>0);
	}
	@Test
	@Rollback(false)
	public void getSysCustomerComplaintById () throws JsonParseException, JsonMappingException, IOException{
//		Integer id = 2;
//		SysCustomerComplaintVO sysCustomerComplaintVO = sysCustomerComplaintService.getSysCustomerComplaintById(id);
//		String[] customerComplaintType = sysCustomerComplaintVO.getCustomerComplaintType();
//		LOG.info(customerComplaintType);
//		this.printJson(sysCustomerComplaintVO);
//		Assert.assertTrue(sysCustomerComplaintVO!=null);
	}
	
}
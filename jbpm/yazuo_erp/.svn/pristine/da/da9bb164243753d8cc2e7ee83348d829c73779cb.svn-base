/**
 * @Description 主流程测试
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 */
package fes.testcase;

import static base.JUnitDaoBaseWithTrans.LOG;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import static junit.framework.Assert.*;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import syn.service.SynMerchantServiceTest;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.dao.FesOnlineProgramDao;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.FesRemarkService;
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysToDoListDao;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.CalendarService;

/**
 * @Description 上线主流程测试
 * @author song
 * @date 2014-8-25 上午10:24:50
 */
public class TestMainProcess  extends JUnitDaoBaseWithTrans {
	
    @Resource  	private FesOnlineProcessService fesOnlineProcessService;
	@Resource  	private CalendarService calendarService;
	@Resource  	private FesTrainPlanService fesTrainPlanService;
	@Resource  	private FesRemarkService fesRemarkService;
	@Resource  	private SysAttachmentService sysAttachmentService;
	@Resource  	private FesOnlineProgramDao fesOnlineProgramDao;
	@Resource  	private SysUserMerchantService sysUserMerchantService;
	@Resource  	private FesOnlineProgramService fesOnlineProgramService;
	@Resource  	private SysToDoListService sysToDoListService;
	@Resource  	private FesOnlineProcessController fesOnlineProcessController;
	@Resource  	private SysOperationLogService sysOperationLogService;
	@Resource  	private SysResourceDao sysResourceDao;
	@Resource  	private SysToDoListDao sysToDoListDao;

	/*********************************************************************
	 				* 初始化数据部分 *
	 **********************************************************************/
	/**
	 * 测试sql
	 * 
	 * 1.输出没有上线计划的商户
	    select * from syn.syn_merchant merchant where not exists
		 (select 1 from (select * from fes.fes_online_process as process, fes.fes_online_program as program 
		where process.program_id = program.id) as temp_t where merchant.merchant_id =temp_t.merchant_id )
		and not exists(select 1 from sys.sys_user_merchant where sys.sys_user_merchant.merchant_id = merchant.merchant_id);
		
		2.查询管理的（step， process，program， merchant）
				 select program.merchant_id, process.id as process_id, program.id as program_id,  
				 step.id as step_id, step.step_num, step.step_name 
			, program.plan_online_time, process.plan_end_time, process.online_process_status
			 from fes.fes_online_process as process, fes.fes_online_program as program , fes.fes_step as step 
					where process.program_id = program.id and  process.step_id = step.id
			  and merchant_id = 1592 order by program_id, step_id;

		3.在210数据库中 用erp用户登录 查询  public.brands crm_id dev用户 trade.merchant 表中的merchant_id中存在
  			并且不在 syn.syn_merchant_brand 中
		select * from public.brands
		where crm_id in (select m.merchant_id from syn.merchant m)
		and crm_id not in (select m.merchant_id from syn.syn_merchant_brand m)
		
		4. 待办事项查询
		select  ss.update_time, ss.related_type, ss.related_id, ss.business_type, ss.* from sys.sys_to_do_list as ss where ss.merchant_id = 1592 
	   		and ss.is_enable='1' and ss.item_status='0' and ss.related_type='1'
	   
	   
	 */
	private List<Integer> merchantIds;
	private Integer merchantId;
	private SysUserVO sessionUser = null;
	private Integer oldUserId = null;
	private Integer userId = null;
	Integer sessionUserId = null;
	@Before
	public void setUp() {
		merchantIds = Arrays.asList(); //输入已知的商户ID，可以有上线计划也可以没有， 可以一次测试多个
		merchantId = 1387;
		sessionUser = new SysUserVO();
		sessionUser.setId(1); //会话中的用户id
		oldUserId = null; //如果商户中已经分配了前端，赋值前端负责人的id
		userId = 138; //分配的负责人id
	    sessionUserId = sessionUser.getId();
	}
  
	/**
	 * 测试整个上线流程，涵盖流程中尽可能多的方法
	 */
	@Test 
	public void testAllOnlinProcess() throws JsonParseException, JsonMappingException, IOException{
		/*
		 * 1.分配负责人
			 * 0. 保存负责人商户关系
			 * 1. 创建上线计划
			 * 3. 创建上线流程
			 * 4. 创建待办事项
		 */
		SysUserMerchantVO sysUserMerchant = new SysUserMerchantVO();
		sysUserMerchant.setUserId(this.oldUserId);
		sysUserMerchant.setUserId(this.userId);
		sysUserMerchant.setMerchantId(merchantId);
		List<FesOnlineProcessVO> complexFesOnlineProcesss = this.saveSysUserMerchant(sysUserMerchant, sessionUser);
		/*
		 * 2.点击按钮更改状态 
		 *  1.状态改变
		 *  2.生成待办事项
		 */
		for (FesOnlineProcessVO fesOnlineProcess : complexFesOnlineProcesss) {
			Integer processId = fesOnlineProcess.getId();
			FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
			fesOnlineProcessVO.setId(processId);
			FesOnlineProcessVO fesOnlineProcesss2 = this.fesOnlineProcessService.getFesOnlineProcessById(processId);
			//计划上线时间与数据库原始数据有关，需要录入
			this.printJson("fesOnlineProcessVO.getPlanEndTime() "+fesOnlineProcesss2.getPlanEndTime());
			fesOnlineProcessVO.setOnlineProcessStatus("12");
//			JsonResult jsonResult = fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcessVO, this.sessionUser);
		}
	}

	/**
	 * @return 
	 * @Description 1.分配负责人
	 */
	private List<FesOnlineProcessVO> saveSysUserMerchant(SysUserMerchantVO sysUserMerchant, SysUserVO sessionUser) {

		SysToDoListVO sysToDoList = new SysToDoListVO();
		String[] inputItemTypes = new String[]{"01"};
		sysToDoList.setUserId(1);
		sysToDoList.setItemStatus("0");
		sysToDoList.setInputItemTypes(inputItemTypes);
		
		List<SysUserMerchantVO> sysUserMerchants = this.sysUserMerchantService.getSysUserMerchants(sysUserMerchant);
		if(sysUserMerchant.getOldUserId()==null&& sysUserMerchants.size()>0){
			fail("商户存在上线计划, 负责人ID不能为空");
		}
		if(sysUserMerchant.getOldUserId()!=null&& sysUserMerchants.size()==0){
			fail("商户存在上线计划, 请输入正确的 负责人ID");
		}
		int sysToDoListCountBefore = this.sysToDoListDao.getSysToDoListCount();
		int pk = sysUserMerchantService.saveSysUserMerchant(sysUserMerchant, sessionUser);
		assertTrue(pk>0); //商户与用户关系表成功插入一条记录
		
		//查询上线流程
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		fesOnlineProgram.setMerchantId(sysUserMerchant.getMerchantId());
		List<FesOnlineProgramVO> fesOnlinePrograms = fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
		assertTrue(fesOnlinePrograms.size()>0);
		Integer programId = fesOnlinePrograms.get(0).getId();//只有一个上线计划
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setProgramId(programId);
		List<FesOnlineProcessVO> complexFesOnlineProcesss = fesOnlineProcessService.getComplexFesOnlineProcesss(fesOnlineProcessVO);
		assertEquals(complexFesOnlineProcesss.size(), 10);//创建10个上线流程

		//查找所有需要创建待办事项的用户
		int count1 = this.checkResourceHaveUsers(resource_fes_card_and_materials);
		int count2 = this.checkResourceHaveUsers(resource_end_custom_service);
		int count3 = this.checkResourceHaveUsers(resource_end_delivery_service);
		int sysToDoListCountAfter = this.sysToDoListDao.getSysToDoListCount();
		assertEquals(sysToDoListCountAfter-sysToDoListCountBefore, count1*2+count2+count3);//测试待办事项个数
		// 查询新添加的待办事项
		SysToDoListVO sysToDoList1 = new SysToDoListVO();
		sysToDoList1.setMerchantId(merchantId);
		sysToDoList1.setUserId(1);
		List<SysToDoListVO> sysToDoLists = sysToDoListService.getSysToDoLists(sysToDoList1);
		for (SysToDoListVO sysToDoListVO : sysToDoLists) {
			logger.info(sysToDoListVO.getItemContent());
		}
		logger.info("生成待办事项的个数： "+sysToDoLists.size());
		Assume.assumeTrue(sysToDoLists.size() > 0);
		return complexFesOnlineProcesss;
	}

	/**
	 * @Description 判断资源关联用户
	 */
	private int checkResourceHaveUsers(String resourceRemark) {
		SysResourceVO sysResourceVO = new SysResourceVO();
		sysResourceVO.setRemark(resourceRemark); 
		List<SysUserVO> listToDoUsers = this.sysResourceDao.getAllUsersByResourceCode(sysResourceVO);
		if(listToDoUsers.size()==0) fail("当前资源 "+resourceRemark +" 没有关联用户");
		return listToDoUsers.size();
	}
	
}




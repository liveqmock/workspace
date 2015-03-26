/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.TreeVO;
import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.mkt.controller.MktShopSurveyController;
import com.yazuo.erp.mkt.dao.MktBrandInterviewDao;
import com.yazuo.erp.mkt.dao.MktContactDao;
import com.yazuo.erp.mkt.dao.MktShopSurveyDao;
import com.yazuo.erp.mkt.service.MktBusinessTypeService;
import com.yazuo.erp.mkt.service.MktShopSurveyService;
import com.yazuo.erp.mkt.vo.MktBrandInterviewVO;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.dao.SysToDoListDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.account.vo.MerchantVO;
/**
 * @author erp team
 * @date 
 */

@Service
public class MktShopSurveyServiceImpl implements MktShopSurveyService {
	private static final Log LOG = LogFactory.getLog(MktShopSurveyServiceImpl.class);
	@Resource
	private MktShopSurveyDao mktShopSurveyDao;
	@Resource
	private SysOperationLogDao sysOperationLogDao;
	@Resource
	private FesOnlineProcessDao fesOnlineProcessDao;
	@Resource
	private SysToDoListDao sysToDoListDao;
	@Resource
	private MktBrandInterviewDao mktBrandInterviewDao;
	@Resource
	private MktContactDao mktContactDao;
	@Resource private SynMerchantDao synMerchantDao;
	@Resource private SysUserDao sysUserDao;
	@Resource private MerchantDao merchantDao;
	@Resource private SysDictionaryService sysDictionaryService;
	
	/**
	 * 下拉菜单选择门店信息
	 */
	@Override
	public List<Map<String, Object>> getMerchantsForSurvey(Integer merchantId){
		return merchantDao.getMerchantsForSurvey(merchantId);
	}
	@Override
	public MerchantVO getMerchantVOFromCRM(Integer merchantId){
		return merchantDao.getMerchantVO(merchantId);
	}
	@Resource MktBusinessTypeService mktBusinessTypeService;
	/**
	 * 调研单： 页面载入的时候查询或新增调研单信息
	 * 一个商户对应多个
	 */
	@Override
	public MktShopSurveyVO loadMktShopSurvey (MktShopSurveyVO mktShopSurvey){
		//系统默认一个商户只能有一个调研单
		Integer merchantId = mktShopSurvey.getMerchantId();
		MktShopSurveyVO mktShopSurveyVO = new MktShopSurveyVO();
		
		Integer shopSurveyId = mktShopSurvey.getId();
		if(shopSurveyId!=null){
			mktShopSurveyVO = this.mktShopSurveyDao.getMktShopSurveyById(shopSurveyId);
		}
		
		//查找联系人下拉框
		MktContactVO mktContact = new MktContactVO();
		mktContact.setMerchantId(merchantId);
		//封装 选择门店下拉列表
		List<Map<String, Object>> mktContactList = mktContactDao.getMktContactsMap(mktContact);
		List<Map<String, Object>> merchantsForSurvey = this.getMerchantsForSurvey(merchantId);
		Map<String, Object> selectedShopSurvey = new HashMap<String, Object>();
		Map<String, Object> selectedContact = new HashMap<String, Object>();
		selectedShopSurvey.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getStoreId()==null? emptyList:mktShopSurveyVO.getStoreId());
		
		//不能选同一个门店
		merchantsForSurvey = removeDuplicate(merchantId, mktShopSurveyVO, merchantsForSurvey);
		selectedShopSurvey.put("dropdownlist", merchantsForSurvey);
		
		selectedContact.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getContactId()==null? emptyList:mktShopSurveyVO.getContactId());
		selectedContact.put("dropdownlist", mktContactList);
		
		mktShopSurveyVO.setMktContactList(selectedContact);
		mktShopSurveyVO.setMerchantsForSurvey(selectedShopSurvey);
		//封装数据字典
		this.setDicFormat(mktShopSurveyVO);
		this.setDicNear(mktShopSurveyVO);
		this.setDicNetworkCondition(mktShopSurveyVO);
		this.setDicNetworkSpeed(mktShopSurveyVO);
		this.setDicPublicityMaterial(mktShopSurveyVO);
		return mktShopSurveyVO;
	}
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> removeDuplicate(Integer merchantId, MktShopSurveyVO mktShopSurveyVO,
			List<Map<String, Object>> merchantsForSurvey) {
		MktShopSurveyVO mktShopSurveyVO1 = new MktShopSurveyVO();
		mktShopSurveyVO1.setMerchantId(merchantId);
		mktShopSurveyVO1.setIsEnable("1");
		List<MktShopSurveyVO> mktShopSurveys = this.mktShopSurveyDao.getMktShopSurveys(mktShopSurveyVO1);
		
		List<Map<String, Object>> tempList = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : merchantsForSurvey) {
			for (MktShopSurveyVO mktShopSurveyVO2 : mktShopSurveys) {
				Integer storeId = mktShopSurveyVO2.getStoreId();
				if(storeId.equals(map.get("merchant_id"))){
					if(mktShopSurveyVO.getId()!=null){ //不过滤自己
						if(!mktShopSurveyVO.getStoreId().equals(map.get("merchant_id"))){
							tempList.add(map); break;
						}
					}else{
						tempList.add(map); break;
					}
				}
			}
		}
		merchantsForSurvey = (List<Map<String, Object>>)CollectionUtils.subtract(merchantsForSurvey, tempList);
		return merchantsForSurvey;
	}

	private static List emptyList = Collections.EMPTY_LIST;
	
	private void setDicFormat(MktShopSurveyVO mktShopSurveyVO) {

		String[] formats = mktShopSurveyVO.getFormat();
		//如果访谈单中有业态， 新增调研单的时候需要考虑把访谈单带中的业态带过来
		if(mktShopSurveyVO.getId()==null){ //是新增操作
			Integer merchantId = mktShopSurveyVO.getMerchantId();
			MktBrandInterviewVO mktBrandInterview = new MktBrandInterviewVO();
			mktBrandInterview.setMerchantId(merchantId);
			List<MktBrandInterviewVO> mktBrandInterviews = this.mktBrandInterviewDao.getMktBrandInterviews(mktBrandInterview);
			if(mktBrandInterviews.size()>0){
				MktBrandInterviewVO mktBrandInterviewVO = mktBrandInterviews.get(0);
				formats = mktBrandInterviewVO.getFormat();
			}
		}
		final List<TreeVO> dicFormat = this.mktBusinessTypeService.getAllNode(formats);
		mktShopSurveyVO.setDicFormat(dicFormat);
	}
	private void setDicNear(MktShopSurveyVO mktShopSurveyVO) {
		List<Map<String, Object>> querySysDictionaryByTypeStd = sysDictionaryService.querySysDictionaryByTypeStd("00000067");
		Map<String, Object> dic = new HashMap<String, Object>();
		dic.put(Constant.DIC_DIC_OBJECT, querySysDictionaryByTypeStd);
		dic.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getNear()==null? emptyList:Arrays.asList(mktShopSurveyVO.getNear()));
		mktShopSurveyVO.setDicNear(dic);
	}
	
	private void setDicNetworkCondition(MktShopSurveyVO mktShopSurveyVO) {
		List<Map<String, Object>> querySysDictionaryByTypeStd = sysDictionaryService.querySysDictionaryByTypeStd("00000073");
		Collections.sort(querySysDictionaryByTypeStd, new BeanComparator("value"));
		Map<String, Object> dic = new HashMap<String, Object>();
		dic.put(Constant.DIC_DIC_OBJECT, querySysDictionaryByTypeStd);
		dic.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getNetworkCondition()==null? emptyList:Arrays.asList(mktShopSurveyVO.getNetworkCondition()));
		mktShopSurveyVO.setDicNetworkCondition(dic);
	}
	private void setDicNetworkSpeed(MktShopSurveyVO mktShopSurveyVO) {
		List<Map<String, Object>> querySysDictionaryByTypeStd = sysDictionaryService.querySysDictionaryByTypeStd("00000099");
		Map<String, Object> dic = new HashMap<String, Object>();
		dic.put(Constant.DIC_DIC_OBJECT, querySysDictionaryByTypeStd);
		dic.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getNetworkSpeed()==null? emptyList:Arrays.asList(mktShopSurveyVO.getNetworkSpeed()));
		mktShopSurveyVO.setDicNetworkSpeed(dic);
	}
	private void setDicPublicityMaterial(MktShopSurveyVO mktShopSurveyVO) {
		List<Map<String, Object>> querySysDictionaryByTypeStd = sysDictionaryService.querySysDictionaryByTypeStd("00000076");
		Map<String, Object> dic = new HashMap<String, Object>();
		dic.put(Constant.DIC_DIC_OBJECT, querySysDictionaryByTypeStd);
		dic.put(Constant.DIC_CHECKED_VALS, mktShopSurveyVO.getPublicityMaterial()==null? emptyList:mktShopSurveyVO.getPublicityMaterial() );
		mktShopSurveyVO.setDicPublicityMaterial(dic);
	}
	
	public JsonResult saveMktShopSurvey (MktShopSurveyVO mktShopSurvey, SysUserVO user) {
		// 添加
		mktShopSurvey.setIsEnable("1");
		mktShopSurvey.setInsertBy(user.getId());
		mktShopSurvey.setInsertTime(new Date());
		mktShopSurvey.setUpdateBy(user.getId());
		mktShopSurvey.setUpdateTime(new Date());
		// 添加之前查询是否已经有调研单了
		List<MktShopSurveyVO> shopList = null;
		if (mktShopSurvey.getProcessId() !=null) { // 前端服务调用的
			MktShopSurveyVO shop = new MktShopSurveyVO();
			shop.setMerchantId(mktShopSurvey.getMerchantId());
			shop.setIsEnable("1");
			shopList = mktShopSurveyDao.getMktShopSurveys(shop);
		}
		// 添加
		int count = mktShopSurveyDao.saveMktShopSurvey(mktShopSurvey);
		String onlineStatus = "";
		
		String fesLogType = ""; // 日志类型
		if (mktShopSurvey.getModuleType().equals("mkt")) {
			fesLogType = "1";
		} else if (mktShopSurvey.getModuleType().equals("fes")) {
			fesLogType = "3";
		}
		//查找商户信息
		MerchantVO merchantVOFromCRM = this.getMerchantVOFromCRM(mktShopSurvey.getStoreId());
		String brand = merchantVOFromCRM.getBrand();
		if(count > 0) {
			// 添加操作日志
			String description = "增加了" + brand + "调研单";
			addOperateLog(user, mktShopSurvey.getMerchantId(), description,fesLogType, mktShopSurvey.getModuleType());
			
			if (mktShopSurvey.getProcessId() !=null) { // 前端服务调用的
				FesOnlineProcessVO online = fesOnlineProcessDao.getFesOnlineProcessById(mktShopSurvey.getProcessId());
				// 判断是否为第一次添加门店调研单
				if (shopList ==null || shopList.size() ==0) { // 更新流程状态
					online = new FesOnlineProcessVO();
					online.setId(mktShopSurvey.getProcessId());
					online.setOnlineProcessStatus("03"); // 待确认
					fesOnlineProcessDao.updateFesOnlineProcess(online);
				}
				onlineStatus = online.getOnlineProcessStatus();
			}
		}
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!").putData("onlineProcessStatus", onlineStatus);
	}
	
	// 添加日志信息
	private void addOperateLog (SysUserVO user, Integer merchantId, String description, String fesLogType, String modleType) {
		SysOperationLogVO operateLog = new SysOperationLogVO();
		operateLog.setInsertBy(user.getId());
		operateLog.setInsertTime(new Date());
		operateLog.setFesLogType(fesLogType);
		operateLog.setMerchantId(merchantId);
		operateLog.setModuleType(modleType);
		operateLog.setOperatingTime(new Date());
		
		//添加操作人信息
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(user.getId());
		String userName = sysUserVO.getUserName();
		operateLog.setDescription(description+ " [操作人: "+userName +"]");
		sysOperationLogDao.saveSysOperationLog(operateLog);
	}
	
	public int batchInsertMktShopSurveys (Map<String, Object> map){
		return mktShopSurveyDao.batchInsertMktShopSurveys(map);
	}
	public JsonResult updateMktShopSurvey (MktShopSurveyVO mktShopSurvey, SysUserVO user){
		mktShopSurvey.setIsEnable("1");
		mktShopSurvey.setUpdateBy(user.getId());
		mktShopSurvey.setUpdateTime(new Date());
		int count =  mktShopSurveyDao.updateMktShopSurvey(mktShopSurvey);
		
		String fesLogType = ""; // 日志类型
		if (mktShopSurvey.getModuleType().equals("mkt")) {
			fesLogType = "1";
		} else if (mktShopSurvey.getModuleType().equals("fes")) {
			fesLogType = "3";
		}

		//查找商户信息
		MerchantVO merchantVOFromCRM = this.getMerchantVOFromCRM(mktShopSurvey.getStoreId());
		String brand = merchantVOFromCRM.getBrand();
		// 添加操作日志
		if (count > 0) {
			String description = "更新了" +brand+ "调研单";
			addOperateLog(user, mktShopSurvey.getMerchantId(), description, fesLogType, mktShopSurvey.getModuleType());
		}
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	public int batchUpdateMktShopSurveysToDiffVals (Map<String, Object> map){
		return mktShopSurveyDao.batchUpdateMktShopSurveysToDiffVals(map);
	}
	public int batchUpdateMktShopSurveysToSameVals (Map<String, Object> map){
		return mktShopSurveyDao.batchUpdateMktShopSurveysToSameVals(map);
	}
	public JsonResult deleteMktShopSurveyById (MktShopSurveyVO shopVo, SysUserVO user){

		//查找商户信息
		MerchantVO merchantVOFromCRM = this.getMerchantVOFromCRM(shopVo.getStoreId());
		String brand = merchantVOFromCRM.getBrand();
		
		MktShopSurveyVO mktShopSurvey = mktShopSurveyDao.getMktShopSurveyById(shopVo.getId());

		MktShopSurveyVO mktShop = new MktShopSurveyVO();
		mktShop.setId(shopVo.getId());
		mktShop.setIsEnable("0");
		mktShop.setUpdateBy(user.getId());
		mktShop.setUpdateTime(new Date());
		int count = mktShopSurveyDao.updateMktShopSurvey(mktShop);
		LOG.info("删除调研单返回结果：" + (count > 0 ? "成功":"失败"));
		String onlineStatus = "";
		
		String fesLogType = ""; // 日志类型
		if (shopVo.getModuleType().equals("mkt")) {
			fesLogType = "1";
		} else if (shopVo.getModuleType().equals("fes")) {
			fesLogType = "3";
		}
		// 添加操作日志
		if (count > 0) {
			String description ="删除了"+brand+ "调研单";
			LOG.info("删除记的流水：" + description);
			addOperateLog(user, mktShopSurvey.getMerchantId(), description, fesLogType, shopVo.getModuleType());
			
			if (shopVo.getProcessId() !=null) { // 前端服务调用的
				// 删除之后判断是否还有调研表存在
				MktShopSurveyVO shop = new MktShopSurveyVO();
				shop.setMerchantId(mktShopSurvey.getMerchantId());
				shop.setIsEnable("1");
				List<MktShopSurveyVO> shopList = mktShopSurveyDao.getMktShopSurveys(shop);
				FesOnlineProcessVO online = fesOnlineProcessDao.getFesOnlineProcessById(shopVo.getProcessId());
				if (shopList ==null || shopList.size() ==0) { // 更新流程状态
					online = new FesOnlineProcessVO();
					online.setId(shopVo.getProcessId());
					online.setOnlineProcessStatus("01"); // 待同步
					fesOnlineProcessDao.updateFesOnlineProcess(online);
				}
				onlineStatus = online!=null ? online.getOnlineProcessStatus() : "";
			}
		}
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!").putData("onlineProcessStatus", onlineStatus);
	}
	public int batchDeleteMktShopSurveyByIds (List<Integer> ids){
		return mktShopSurveyDao.batchDeleteMktShopSurveyByIds(ids);
	}
	public MktShopSurveyVO getMktShopSurveyById(Integer id){
		return mktShopSurveyDao.getMktShopSurveyById(id);
	}

	public List<MktShopSurveyVO> getMktShopSurveys(MktShopSurveyVO mktShopSurvey) {
		MerchantVO merchantVOFromCRM = this.getMerchantVOFromCRM(mktShopSurvey.getMerchantId());
		List<MktShopSurveyVO> mktShopSurveys = mktShopSurveyDao.getMktShopSurveys(mktShopSurvey);
		for (MktShopSurveyVO mktShopSurveyVO : mktShopSurveys) {
			mktShopSurveyVO.setStoreName(merchantVOFromCRM.getBrand());
		}
		return mktShopSurveys;
	}
	public List<Map<String, Object>>  getMktShopSurveysMap (MktShopSurveyVO mktShopSurvey){
		return mktShopSurveyDao.getMktShopSurveysMap(mktShopSurvey);
	}

	@Override
	public JsonResult salesConfirm(Integer merchantId, SysUserVO user) {
		// 结束待办事项
		SysToDoListVO todo = new SysToDoListVO();
		todo.setMerchantId(merchantId);
		todo.setItemType("03");
		todo.setItemStatus("0");
		List<SysToDoListVO> todoList = sysToDoListDao.getSysToDoLists(todo);
		if (todoList !=null && todoList.size() > 0) {
			for (SysToDoListVO sysToDoListVO : todoList) {
//				todo = todoList.get(0);
				//SysToDoListVO todoVo = new SysToDoListVO();
				sysToDoListVO.setItemStatus("1");
				sysToDoListVO.setUpdateBy(user.getId());
				sysToDoListVO.setUpdateTime(new Date());
				int num1 = sysToDoListDao.updateSysToDoList(sysToDoListVO);
			}
		}
		
		//商户调研 流水
		addOperateLog(user, merchantId, "确认完成商户调研", "1", "mkt");
		//更改商户状态为 '待分配前端顾问'
		SynMerchantVO synMerchant = new SynMerchantVO();
		synMerchant.setMerchantId(merchantId);
		synMerchant.setMerchantStatus("3");
		synMerchant.setUpdateTime(new Date());
		int num2 = this.synMerchantDao.updateSynMerchant(synMerchant);
		return new JsonResult(true).setMessage("创建成功");
	}

	@Override
	public String judgeSalesConfirmBtnEnble(Integer merchantId) {
		MktShopSurveyVO mktShopSurvey = new MktShopSurveyVO();
		mktShopSurvey.setMerchantId(merchantId);
		mktShopSurvey.setIsEnable("1");
		// 门店调研单数量
		long shopCount = mktShopSurveyDao.getMktShopSurveyCount(mktShopSurvey);
		//
		boolean isInterview = brandInterviewExists(merchantId);
		// 通讯录数量
		MktContactVO contact = new MktContactVO();
		contact.setMerchantId(merchantId);
		contact.setIsEnable("1");
		long contactCount = mktContactDao.getMktContactCount(contact);
		
		if (shopCount > 0 && isInterview && contactCount > 0) {
			boolean isCreateSuccess = false;
			// 该待办事项是否结束
			SysToDoListVO todo = new SysToDoListVO();
			todo.setMerchantId(merchantId);
			todo.setItemType("03");
			todo.setItemStatus("1");
			List<SysToDoListVO> todoList = sysToDoListDao.getSysToDoListsByOrder(todo);
			
			SynMerchantVO synMerchant = new SynMerchantVO();
			synMerchant.setMerchantId(merchantId);
			synMerchant.setMerchantStatus("3");
			synMerchant.setStatus(1);
			List<SynMerchantVO> merchantList = synMerchantDao.getSynMerchants(synMerchant);
			if ((todoList !=null && todoList.size() > 0) && (merchantList !=null && merchantList.size()>0)) {
				isCreateSuccess = true;	
			}
			if (isCreateSuccess) { // 待办事项结束，说明点过创建完成
				return "3";
			}else {
				return "2"; // 可以点创建完成按钮
			}
		} else {
			return "1"; // 创建按钮不可用
		}
	}

	@Override
	public boolean brandInterviewExists(Integer merchantId) {
		// 门店访谈单数量
		MktBrandInterviewVO interview = new MktBrandInterviewVO();
		interview.setMerchantId(merchantId);
		interview.setIsEnable("1");
		long interviewCount = mktBrandInterviewDao.getMktBrandInterviewCount(interview);
		return interviewCount>0 ? true : false;
	}
	
}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service.impl;

import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesStowageService;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.service.impl.MktShopSurveyServiceImpl;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.system.dao.SysDictionaryDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class FesStowageServiceImpl implements FesStowageService {

	private static final Log LOG = LogFactory.getLog(MktShopSurveyServiceImpl.class);
	@Resource private FesStowageDao fesStowageDao;
	@Resource private FesStowageDtlDao fesStowageDtlDao;
	@Resource private FesOnlineProcessService fesOnlineProcessService;
	@Resource private SendMessageVoid sendMessageVoid;
	@Resource private MktContactService mktContactService;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysDictionaryDao sysDictionaryDao;	
	@Resource private SysUserMerchantService sysUserMerchantService;	
	
	/**
	 * 1.保存事情开卡
	 * 2.生成代办事项
	 * 3.生成操作流水
	 * 4.发送配送短信通知
	 */
	public JsonResult saveFesStowage (FesStowageVO fesStowage, SysUserVO sessionUser){
		this.saveStowageAndSendSMS(fesStowage, sessionUser);
		return updateStatusAndGenOperLog(fesStowage, sessionUser);
	}
	/**
	 * 1.保存事情开卡
	 * 2.生成代办事项
	 * 3.生成操作流水
	 * 4.发送配送短信通知
	 */
	@Override
	public JsonResult saveFesStowages(FesStowageVO[] fesStowages, SysUserVO sessionUser) {
		for (FesStowageVO fesStowage : fesStowages) {
			this.saveStowageAndSendSMS(fesStowage, sessionUser);
		}
		return updateStatusAndGenOperLogs(fesStowages, sessionUser);
	}
	
	private JsonResult updateStatusAndGenOperLogs(FesStowageVO[] fesStowages, SysUserVO sessionUser) {
		//调用更改状态接口生成待办事项和操作流水
		Integer processId = fesStowages[0].getProcessId();
		FesOnlineProcessVO fesOnlineProcess = new FesOnlineProcessVO();
		fesOnlineProcess.setId(processId);
		fesOnlineProcess.setOnlineProcessStatus("04");
		fesOnlineProcess.setFesStowages(Arrays.asList(fesStowages));
		FesOnlineProcessVO fesOnlineProcessById = this.fesOnlineProcessService.getFesOnlineProcessById(processId);
		JsonResult result = null;
		if(!"04".equals(fesOnlineProcessById)){
			result = this.fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcess, sessionUser);
		}
		return result;
	}
	private JsonResult updateStatusAndGenOperLog(FesStowageVO fesStowage, SysUserVO sessionUser) {
		//调用更改状态接口生成待办事项和操作流水
		FesOnlineProcessVO fesOnlineProcess = new FesOnlineProcessVO();
		fesOnlineProcess.setId(fesStowage.getProcessId());
		fesOnlineProcess.setOnlineProcessStatus("04");
		fesOnlineProcess.setFesStowageVO(fesStowage);
		FesOnlineProcessVO fesOnlineProcessById = this.fesOnlineProcessService.getFesOnlineProcessById(fesStowage.getProcessId());
		JsonResult result = null;
		if(!"04".equals(fesOnlineProcessById)){
			result = this.fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcess, sessionUser);
		}
		return result;
	}
	
	/**
	 * 实体卡或设备配送
	 */
	private void saveStowageAndSendSMS(FesStowageVO fesStowage, SysUserVO sessionUser) {
		Integer userId = sessionUser.getId();
		fesStowage.setInsertBy(userId);
		fesStowage.setInsertTime(new Date());
		fesStowage.setUpdateBy(userId);
		fesStowage.setIsEnable("1");
		fesStowage.setUpdateTime(new Date());
		fesStowageDao.saveFesStowage(fesStowage);
		List<FesStowageDtlVO> fesStowageDtls = fesStowage.getFesStowageDtls();
		String storageInfo = "";
		for (FesStowageDtlVO fesStowageDtlVO : fesStowageDtls) {
			fesStowageDtlVO.setStowageId(fesStowage.getId());
			fesStowageDtlVO.setInsertBy(userId);
			SysDictionaryVO dic = this.sysDictionaryService.querySysDictionaryByTypeAndKey("00000045", fesStowageDtlVO.getCategory());
			storageInfo+= dic.getDictionaryValue()+"*"+fesStowageDtlVO.getAmount()+",";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FesStowageDtlVO.COLUMN_STOWAGE_ID, Constant.NOT_NULL);
		map.put(FesStowageDtlVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
		map.put(FesStowageDtlVO.COLUMN_CATEGORY, Constant.NOT_NULL);
		map.put(FesStowageDtlVO.COLUMN_AMOUNT, Constant.NOT_NULL);
		map.put(FesStowageDtlVO.COLUMN_UNIT_TYPE, Constant.NOT_NULL);
		map.put(FesStowageDtlVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
		map.put("list", fesStowageDtls);
		fesStowageDtlDao.batchInsertFesStowageDtls(map);
        //保存流程数据字典行  快递公司
        sysDictionaryService.setStdDicRow(fesStowage, "00000115", FesStowageVO.COLUMN_LOGISTICS_COMPANY,"dicRowLogisticComp");
		//发送短信通知
		this.sendSMSInform(fesStowage, storageInfo);
	}
	public void sendSMSInform(FesStowageVO fesStowage, String storageInfo) {
		Integer contactId = fesStowage.getContactId();
		MktContactVO mktContactVO = this.mktContactService.getMktContactById(contactId);
//		短信内容：某先生/女士您好，您商户的实体卡已配送（快递单：************），请注意查收。【雅座在线】
		String honorific = mktContactVO.getGenderType().equals("0")?"女士":"先生";
//		String message = mktContactVO.getName()+ honorific +"你好,您商户的实体卡已配送（快递单："+fesStowage.getLogisticsNum()+"），请注意查收。";
		storageInfo = StringUtils.removeEnd(storageInfo, ",");
		 Map<String, Object> dicRowLogisticComp = fesStowage.getDicRowLogisticComp();
		String logisticCompDesc = dicRowLogisticComp==null || dicRowLogisticComp.get("text")==null? "": dicRowLogisticComp.get("text").toString();
		String message = mktContactVO.getName()+ honorific +"你好,您商户的设备("+storageInfo+") 已配送(快递单："+fesStowage.getLogisticsNum()+
		  ",快递公司:"+ logisticCompDesc+"),请注意查收。";
		if(fesStowage.getDistributeFlag().equals(0)){//实体卡配送
//			短信内容：某先生/女士您好，您商户的实体卡已配送（快递单：************），请注意查收。【雅座在线】
			message = mktContactVO.getName()+ honorific +"你好,您商户的实体卡已配送（快递单："+fesStowage.getLogisticsNum()+
		   ",快递公司:"+ logisticCompDesc+"),请注意查收。";
		}
		LOG.info(message);
		LOG.info("商户联系人电话: "+mktContactVO.getMobilePhone());
		sendMessageVoid.sendMessage(message, mktContactVO.getMobilePhone(),  LogFactory.getLog("sms"));
		//是否发送短信给前端负责人
		Integer isSendSMSToUser = fesStowage.getIsSendSMSToUser(); 
		if(isSendSMSToUser!=null && isSendSMSToUser.equals(1)){
			Object sysUserMerchantTel = this.getSysUserMerchantTel(fesStowage);
			if(sysUserMerchantTel!=null){
				sendMessageVoid.sendMessage(message, sysUserMerchantTel.toString(),  LogFactory.getLog("sms"));
			}
		}
	}
	/**查找前端负责人手机号*/
	@Override
	public Object getSysUserMerchantTel(FesStowageVO fesStowage){
		List<Map<String, Object>> sysUserMerchantTel = fesStowageDao.getSysUserMerchantTel(fesStowage); 
		if(sysUserMerchantTel.size()>0)
			return ((Map<String, Object>)sysUserMerchantTel.get(0)).get("tel");
		return null;
	}
	
	public int batchInsertFesStowages (Map<String, Object> map){
		return fesStowageDao.batchInsertFesStowages(map);
	}
	public int updateFesStowage (FesStowageVO fesStowage){
		return fesStowageDao.updateFesStowage(fesStowage);
	}
	public int batchUpdateFesStowagesToDiffVals (Map<String, Object> map){
		return fesStowageDao.batchUpdateFesStowagesToDiffVals(map);
	}
	public int batchUpdateFesStowagesToSameVals (Map<String, Object> map){
		return fesStowageDao.batchUpdateFesStowagesToSameVals(map);
	}
	public int deleteFesStowageById (Integer id){
		return fesStowageDao.deleteFesStowageById(id);
	}
	public int batchDeleteFesStowageByIds (List<Integer> ids){
		return fesStowageDao.batchDeleteFesStowageByIds(ids);
	}
	public FesStowageVO getFesStowageById(Integer id){
		return fesStowageDao.getFesStowageById(id);
	}
	public List<FesStowageVO> getFesStowages (FesStowageVO fesStowage){
		return fesStowageDao.getFesStowages(fesStowage);
	}
	public List<Map<String, Object>>  getFesStowagesMap (FesStowageVO fesStowage){
		return fesStowageDao.getFesStowagesMap(fesStowage);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> querySysDicForStowage() {
		List<Map<String, Object>> units = this.sysDictionaryDao.querySysDictionaryByTypeStd("00000083");

		final Map<String, Object> suit = (Map<String, Object>)CollectionUtils.find(units,  new Predicate() {
			public boolean evaluate(Object object) {
				return ((Map<String, Object>)object).get("value").equals("0");
			}
		});
		final Map<String, Object> unit = (Map<String, Object>)CollectionUtils.find(units,  new Predicate() {
			public boolean evaluate(Object object) {
				return ((Map<String, Object>)object).get("value").equals("1");
			}
		});
		List<Map<String, Object>> sysDictionaryList = this.sysDictionaryDao.querySysDictionaryByTypeStd("00000045");
		CollectionUtils.forAllDo(sysDictionaryList, new Closure() {
			public void execute(Object input) {
				Map<String, Object> inputObj = (Map<String, Object>)input;
				if(ArrayUtils.contains(new String[]{"2","6","7"}, inputObj.get("value").toString())){
					inputObj.put("unit", suit.get("text").toString()); //套
				}else{
					inputObj.put("unit", unit.get("text").toString());//个
				}
			}
		});
		return sysDictionaryList;
	}
}

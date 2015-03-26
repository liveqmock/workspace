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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.exception.BesBizException;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.mkt.dao.MktContactDao;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.StringUtil;
/**
 * @author erp team
 * @date 
 */

@Service
public class MktContactServiceImpl implements MktContactService {

	@Resource
	private MktContactDao mktContactDao;
	@Resource
	private SysOperationLogDao sysOperationLogDao;
	@Resource private SysUserDao sysUserDao;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SynMerchantDao synMerchantDao;

	/**
	 * 添加联系人弹窗， 包含数据字典字段
	 * 目前只有新增
	 */
	@Override
	public MktContactVO loadMktContact(MktContactVO mktContactVO){
		Integer merchantId = mktContactVO.getMerchantId();
		MktContactVO mktContact = new MktContactVO();
		Integer contactId = mktContactVO.getId();
		if(contactId!=null){//编辑
			mktContact = this.mktContactDao.getMktContactById(contactId);
		}
		SynMerchantVO synMerchantById = synMerchantDao.getSynMerchantById(merchantId);
		mktContact.setMerchantId(merchantId);
		mktContact.setMerchantName(synMerchantById.getMerchantName());
		//添加角色
		this.setDicContact(mktContact);
		return mktContact;
	}
	private static List emptyList = Collections.EMPTY_LIST;
	private void setDicContact(MktContactVO mktContactVO){
		List<Map<String, Object>> querySysDictionaryByTypeStd = sysDictionaryService.querySysDictionaryByTypeStd("00000065");
		Map<String, Object> dic = new HashMap<String, Object>();
		dic.put(Constant.DIC_DIC_OBJECT, querySysDictionaryByTypeStd);
		dic.put(Constant.DIC_CHECKED_VALS, mktContactVO.getRoleType()==null? emptyList:Arrays.asList(mktContactVO.getRoleType()));
		mktContactVO.setDicRole(dic);
	}
	
	/**
	 * search contact drop-down-list, contains 'isSelected' attribute.
	 * 商户可能有多个联系人
	 */
	@Override
	public List<Map<String, Object>> getContactsDropDownList(Integer merchantId){
		List<Map<String, Object>> allContacts = this.getMktContactsMap(null);
		MktContactVO mktContact = new MktContactVO();
		mktContact.setMerchantId(merchantId);
		final List<Map<String, Object>> mktContactsMap = this.getMktContactsMap(mktContact);
		final List<Map<String, Object>> outputCollection = new ArrayList<Map<String,Object>>();
		CollectionUtils.collect(allContacts.iterator(), new Transformer() {
			//mark the selected.
			@Override
			public Object transform(Object input) {
				@SuppressWarnings("unchecked")
				Map<String, Object> inputMap = (Map<String, Object>)input;
				Object contact_id = inputMap.get("id");
				Map<String, Object> newMap = new HashMap<String, Object>();
				for (Map<String, Object> contact : mktContactsMap) {
//					System.out.println(contact_id.toString() + "  "+contact.get("id")+ "  "+contact_id.equals(contact.get("id")));
					if(contact_id.equals(contact.get("id"))){
						newMap.put("isSelected", "1");
					}else{
						newMap.put("isSelected", "0");
					}
					newMap.put("value", inputMap.get("id"));
					newMap.put("text", inputMap.get("name"));
					newMap.put("mobilePhone", inputMap.get("mobile_phone"));
				}
				outputCollection.add(newMap);
				return newMap;
			}
		}, outputCollection);
		return outputCollection;
	}

	/**
	 * 标准方法,查找下拉列表联系人
	 */
	@Override
	public void setStdContacts(SynMerchantVO synMerchantVO, Object vo, String... attributeName) {
		
		Integer contactId;
		try {
			contactId = (Integer)MethodUtils.invokeMethod(vo, "get"+StringUtils.capitalize(attributeName[0]), null);
		} catch (Exception e) {
			throw new BesBizException(e);
		} 
		final Integer merchantId = synMerchantVO.getMerchantId();
		List<Map<String, Object>> list = mktContactDao.getMktContactsStd(new MktContactVO(){{setMerchantId(merchantId);}});
		for (Map<String, Object> map : list) {
			if(((Integer)map.get("value")).equals(contactId)){
				map.put(Constant.DropDownList.isSelected, "1");
			}else{
				map.put(Constant.DropDownList.isSelected, "0");
			}
		}
		try {
			MethodUtils.invokeMethod(vo, "set"+StringUtils.capitalize(attributeName[1]), list);
		} catch (Exception e) {
			throw new BesBizException(e);
		} 
	}
	
	@Override
	public MktContactVO saveMktContact(MktContactVO mktContact, SysUserVO user) {
		Integer contactId = mktContact.getId();
		if(contactId!=null){
			mktContact.setUpdateBy(user.getId());
			mktContact.setUpdateTime(new Date());
			mktContactDao.updateMktContact(mktContact);
		}else{
			mktContact.setInsertBy(user.getId());
			mktContact.setUpdateBy(user.getId());
			mktContact.setInsertTime(new Date());
			mktContact.setUpdateTime(new Date());
			mktContact.setIsEnable("1");
			mktContactDao.saveMktContact(mktContact);
			if (!StringUtil.isNullOrEmpty(mktContact.getModuleType()) && mktContact.getModuleType().equals("mkt")) { // 销售模块进入的需要加流水日志
				SysOperationLogVO operateLog = new SysOperationLogVO();
				operateLog.setInsertBy(user.getId());
				operateLog.setInsertTime(new Date());
				operateLog.setFesLogType("2");
				operateLog.setMerchantId(mktContact.getMerchantId());
				operateLog.setModuleType(mktContact.getModuleType());
				operateLog.setOperatingTime(new Date());
				operateLog.setRemark(mktContact.getId() + "");
				//添加操作人信息
				SysUserVO sysUserVO = this.sysUserDao.getSysUserById(user.getId());
				String userName = sysUserVO.getUserName();
				operateLog.setDescription("增加了联系人"+ " [操作人: "+userName +"]");
				sysOperationLogDao.saveSysOperationLog(operateLog);
			}
		}

		return mktContact;
	}
	
	public int batchInsertMktContacts(Map<String, Object> map) {
		return mktContactDao.batchInsertMktContacts(map);
	}

	public int updateMktContact(MktContactVO mktContact) {
		return mktContactDao.updateMktContact(mktContact);
	}

	public int batchUpdateMktContactsToDiffVals(Map<String, Object> map) {
		return mktContactDao.batchUpdateMktContactsToDiffVals(map);
	}

	public int batchUpdateMktContactsToSameVals(Map<String, Object> map) {
		return mktContactDao.batchUpdateMktContactsToSameVals(map);
	}

	public int deleteMktContactById(Integer id) {
		return mktContactDao.deleteMktContactById(id);
	}

	public int batchDeleteMktContactByIds(List<Integer> ids) {
		return mktContactDao.batchDeleteMktContactByIds(ids);
	}

	public MktContactVO getMktContactById(Integer id) {
		return mktContactDao.getMktContactById(id);
	}

	public Page<MktContactVO> getMktContacts(Map<String, Object> paramMap) {
		return mktContactDao.getMktContacts(paramMap);
	}

	public List<Map<String, Object>> getMktContactsMap(MktContactVO mktContact) {
		return mktContactDao.getMktContactsMap(mktContact);
	}

    @Override
    public MktContactVO getLastContactForMerchant(int merchantId) {
        return this.mktContactDao.getLastContactForMerchant(merchantId);
    }

    /**
	 * @Title queryContact
	 * @Description 根据商户ID查询通讯录信息
	 * @param mktContactVO
	 * @return
	 * @see com.yazuo.erp.mkt.service.MktContactService#queryContact(com.yazuo.erp.mkt.vo.MktContactVO)
	 */
	@Override
	public List<Map<String, Object>> queryContact(MktContactVO mktContactVO) {
		List<Map<String, Object>> list = mktContactDao.getMktContactsMap(mktContactVO);
		return list;
	}
	/**
	 * @Title text value形式得到联系人
	 */
	@Override
	public List<Map<String, Object>> queryContactsStd(MktContactVO mktContactVO) {
		List<Map<String, Object>> list = mktContactDao.getMktContactsStd(mktContactVO);
		return list;
	}
}

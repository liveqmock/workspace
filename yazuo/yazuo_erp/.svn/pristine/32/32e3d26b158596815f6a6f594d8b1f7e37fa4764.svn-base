/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author erp team
 * @date
 */
public interface MktContactService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	MktContactVO saveMktContact(MktContactVO mktContact, SysUserVO user);

	/**
	 * 新增多个对象 @return :
	 */
	int batchInsertMktContacts(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktContact(MktContactVO mktContact);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktContactsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktContactsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteMktContactById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktContactByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	MktContactVO getMktContactById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	Page<MktContactVO> getMktContacts(Map<String, Object> paramMap);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getMktContactsMap(MktContactVO mktContact);

    MktContactVO getLastContactForMerchant(int merchantId);

    /**
	 * @Description 根据商户ID查询通讯录信息
	 * @param mktContactVO 
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> queryContact(MktContactVO mktContactVO);

	MktContactVO loadMktContact(MktContactVO mktContactVO);

	List<Map<String, Object>> getContactsDropDownList(MktContactVO mktContact);

	List<Map<String, Object>> queryContactsStd(MktContactVO mktContactVO);

	void setStdContacts(SynMerchantVO synMerchantVO, Object vo, String... attributeName);

	MktContactVO updateMktContactForDelete(int id);

	List<MktContactVO> getContactForMerchantRoles(List<String> roleTypes, Integer merchantId);
}

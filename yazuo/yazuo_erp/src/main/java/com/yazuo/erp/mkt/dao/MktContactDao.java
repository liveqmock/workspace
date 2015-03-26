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

package com.yazuo.erp.mkt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.mkt.vo.MktContactVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */


@Repository
public interface MktContactDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktContact (MktContactVO mktContact);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertMktContacts (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktContact (MktContactVO mktContact);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktContactsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktContactsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteMktContactById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktContactByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktContactVO getMktContactById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	Page<MktContactVO> getMktContacts (Map<String, Object> paramMap);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktContactsMap (MktContactVO mktContact);
	/**
	 * 返回text value for dropdownlist
	 */
	List<Map<String, Object>>  getMktContactsStd (MktContactVO mktContact);
	
	long getMktContactCount(MktContactVO mktContactVO);

    MktContactVO getLastContactForMerchant(Integer merchantId);

	/**
	 *
	 * @param roleTypes
	 * @param merchantId
	 * @return
	 */
    List<MktContactVO> getContactForMerchantRoles(@Param("roleTypes") List<String> roleTypes, @Param("merchantId") Integer merchantId);
}

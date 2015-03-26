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

package com.yazuo.erp.syn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.syn.vo.SynMembershipCardVO;

/**
 * @Description TODO
 * @author erp team
 * @date
 */

@Repository
public interface SynMembershipCardDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSynMembershipCard(SynMembershipCardVO synMembershipCard);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSynMembershipCards(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSynMembershipCard(SynMembershipCardVO synMembershipCard);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSynMembershipCardsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSynMembershipCardsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSynMembershipCardById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSynMembershipCardByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SynMembershipCardVO getSynMembershipCardById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SynMembershipCardVO> getSynMembershipCards(SynMembershipCardVO synMembershipCard);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSynMembershipCardsMap(SynMembershipCardVO synMembershipCard);

	/**
	 * 返回商户对应的会员卡信息
	 */
	List<Map<String, Object>> getSynMembershipCardInfo(@Param(value = "brandId") Integer brandId);

	/**
	 * 返回商户对应的会员卡信息记录数
	 */
	long getSynMembershipCardCount(@Param(value = "brandId") Integer brandId);
}

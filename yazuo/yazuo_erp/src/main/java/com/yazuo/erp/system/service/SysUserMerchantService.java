/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface SysUserMerchantService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysUserMerchant (SysUserMerchantVO sysUserMerchant, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysUserMerchants (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysUserMerchant (SysUserMerchantVO sysUserMerchant);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysUserMerchantsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysUserMerchantsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysUserMerchantById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysUserMerchantByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysUserMerchantVO getSysUserMerchantById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysUserMerchantVO> getSysUserMerchants (SysUserMerchantVO sysUserMerchant);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysUserMerchantsMap (SysUserMerchantVO sysUserMerchant);
	/**
	 * @Description 根据用户ID和商户ID查询前端顾问和商户的关系
	 * @param userMerchantVO
	 * @return
	 * @throws 
	 */
	boolean isExistSysUserMerchant(SysUserMerchantVO userMerchantVO);
	SysUserVO getFesUserByMerchantId(Integer merchantId);

    Map<Integer, String> getFesUserByMerchantIds(List<Integer> merchantIds);
	Integer getUserMerchantId(SysUserMerchantVO sysUserMerchant);
	void saveSalesAndFrontEndPersion(SynMerchantVO synMerchantVO, SysUserVO sessionUser);

}

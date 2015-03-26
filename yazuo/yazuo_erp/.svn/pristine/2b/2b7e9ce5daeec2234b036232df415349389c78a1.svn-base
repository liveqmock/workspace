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

import java.util.Map;

import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

import java.util.*;

import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
public interface MktFormerSalesMerchantService{

	/**
	 * 保存或修改
	 */
	int saveOrUpdateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant);
	/**
	 * 新增多个对象 @return 
	 */
	int batchInsertMktFormerSalesMerchants (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktFormerSalesMerchantsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktFormerSalesMerchantsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteMktFormerSalesMerchantById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktFormerSalesMerchantByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktFormerSalesMerchantVO getMktFormerSalesMerchantById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<MktFormerSalesMerchantVO> getMktFormerSalesMerchants (MktFormerSalesMerchantVO mktFormerSalesMerchant);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktFormerSalesMerchantsMap (MktFormerSalesMerchantVO mktFormerSalesMerchant);
	void saveSalesMan(SysUserVO user, Integer merchantId, Integer salesId, SysSalesmanMerchantVO salesMan);
	

}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.dao;

import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface MktFormerSalesMerchantDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
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
	

}

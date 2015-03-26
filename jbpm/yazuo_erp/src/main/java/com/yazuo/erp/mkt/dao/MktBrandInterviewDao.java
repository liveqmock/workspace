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

import org.springframework.stereotype.Repository;

import com.yazuo.erp.mkt.vo.MktBrandInterviewVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */


@Repository
public interface MktBrandInterviewDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktBrandInterview (MktBrandInterviewVO mktBrandInterview);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertMktBrandInterviews (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktBrandInterview (MktBrandInterviewVO mktBrandInterview);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateMktBrandInterviewsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateMktBrandInterviewsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteMktBrandInterviewById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteMktBrandInterviewByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	MktBrandInterviewVO getMktBrandInterviewById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<MktBrandInterviewVO> getMktBrandInterviews (MktBrandInterviewVO mktBrandInterview);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getMktBrandInterviewsMap (MktBrandInterviewVO mktBrandInterview);
	
	/**根据条件取访谈单数量*/
	long getMktBrandInterviewCount(MktBrandInterviewVO mktBrandInterviewVO);

}

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

package com.yazuo.erp.minierp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.minierp.vo.BrandsVO;

/**
 * @Description TODO
 * @author erp team
 * @date
 */

@Repository
public interface BrandsDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBrands(BrandsVO brands);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertBrandss(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBrands(BrandsVO brands);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBrandssToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBrandssToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteBrandsById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBrandsByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	BrandsVO getBrandsById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BrandsVO> getBrandss(BrandsVO brands);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getBrandssMap(BrandsVO brands);

	List<Map<String, Object>> getBrandsInfo(@Param(value = "name") String name);

	List<Integer> getCrmIds();
}

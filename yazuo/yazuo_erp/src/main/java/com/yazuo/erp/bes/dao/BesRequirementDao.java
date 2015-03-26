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

package com.yazuo.erp.bes.dao;

import java.util.*;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import org.apache.ibatis.annotations.Param;
import com.yazuo.erp.system.vo.SysUserVO;

import org.springframework.stereotype.Repository;


@Repository
public interface BesRequirementDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
     * @param sessionUser 
	 */
	int saveBesRequirement (BesRequirementVO besRequirement);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertBesRequirements (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBesRequirement (BesRequirementVO besRequirement);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBesRequirementsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBesRequirementsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteBesRequirementById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBesRequirementByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	BesRequirementVO getBesRequirementById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BesRequirementVO> getBesRequirements (BesRequirementVO besRequirement);

    List<BesRequirementVO> getBesRequirementsForSearch (@Param("vo")BesRequirementVO besRequirement,@Param("userId")Integer userId);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getBesRequirementsMap (BesRequirementVO besRequirement);
	
	/**
	 * 查找处理时间为今天, 插入时间不等于今天的需求,定时器今天23:59:59调用
	 */
	List<BesRequirementVO>  getRequirementForTimer(BesRequirementVO besRequirement);

}

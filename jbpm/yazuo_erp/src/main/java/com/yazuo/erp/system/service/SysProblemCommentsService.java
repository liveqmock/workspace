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

import java.util.Map;

import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.vo.SysProblemCommentsVO;
import com.yazuo.erp.system.vo.SysUserVO;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author erp team
 * @date 
 */
public interface SysProblemCommentsService{

	/**
	 * 保存或修改
	 */
	int saveOrUpdateSysProblemComments (SysProblemCommentsVO sysProblemComments, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysProblemComments (SysProblemCommentsVO sysProblemComments);
	/**
	 * 新增多个对象 @return 
	 */
	int batchInsertSysProblemCommentss (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysProblemComments (SysProblemCommentsVO sysProblemComments);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysProblemCommentssToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysProblemCommentssToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysProblemCommentsById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysProblemCommentsByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysProblemCommentsVO getSysProblemCommentsById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysProblemCommentsVO> getSysProblemCommentss (SysProblemCommentsVO sysProblemComments);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysProblemCommentssMap (SysProblemCommentsVO sysProblemComments);
	SysProblemCommentsVO saveSysProblemComments(SysProblemCommentsVO sysProblemComments, SysUserVO sessionUser);
	

}

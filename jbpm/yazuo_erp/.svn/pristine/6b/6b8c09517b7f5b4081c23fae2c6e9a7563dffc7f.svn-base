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

package com.yazuo.erp.fes.service;

import java.util.Map;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface FesProcessAttachmentService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveFesProcessAttachment (FesProcessAttachmentVO fesProcessAttachment);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertFesProcessAttachments (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesProcessAttachment (FesProcessAttachmentVO fesProcessAttachment);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesProcessAttachmentsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesProcessAttachmentsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesProcessAttachmentById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesProcessAttachmentByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesProcessAttachmentVO getFesProcessAttachmentById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesProcessAttachmentVO> getFesProcessAttachments (FesProcessAttachmentVO fesProcessAttachment);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesProcessAttachmentsMap (FesProcessAttachmentVO fesProcessAttachment);
	

}

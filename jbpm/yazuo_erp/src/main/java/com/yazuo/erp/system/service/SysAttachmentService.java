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

package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysAttachmentVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public interface SysAttachmentService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysAttachment (SysAttachmentVO sysAttachment);
	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysAttachments (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysAttachment (SysAttachmentVO sysAttachment);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysAttachmentsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysAttachmentsToSameVals (Map<String, Object> map);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysAttachmentByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysAttachmentVO getSysAttachmentById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysAttachmentVO> getSysAttachments (SysAttachmentVO sysAttachment);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysAttachmentsMap (SysAttachmentVO sysAttachment);
	
	/**工作计划附件*/
	Page<SysAttachmentVO> queryPlanAttachment(Map<String, Object> paramMap);
	/**上线计划附件*/
	Page<SysAttachmentVO> queryOnlineAttachment(Map<String, Object> paramMap);
	/**营销活动申请附件*/
	Page<SysAttachmentVO> queryActivityAttachment(Map<String, Object> paramMap);
	/**webkit相关的附件彻底删除*/
	int deleteSysAttachmentById(Integer id);
	String getOriginalFileName(String attachmentName);

}

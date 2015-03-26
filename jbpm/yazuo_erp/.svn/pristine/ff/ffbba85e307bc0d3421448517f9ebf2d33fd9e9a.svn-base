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

package com.yazuo.erp.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysAttachmentVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Repository
public interface SysAttachmentDao {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysAttachment(SysAttachmentVO sysAttachment);

	/**
	 * 新增多个对象 @return : 影响的行数
	 * 
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSysAttachments(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysAttachment(SysAttachmentVO sysAttachment);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysAttachmentsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysAttachmentsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSysAttachmentById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysAttachmentByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SysAttachmentVO getSysAttachmentById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysAttachmentVO> getSysAttachments(SysAttachmentVO sysAttachment);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSysAttachmentsMap(SysAttachmentVO sysAttachment);

	/**
	 * @Description 根据工作计划ID查询附件信息
	 * @param planId
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> getSysAttachmentList(Integer planId);
	
	/**工作计划附件*/
	Page<SysAttachmentVO> queryPlanAttachment(Map<String, Object> paramMap);
	/**上线计划附件*/
	Page<SysAttachmentVO> queryOnlineAttachment(Map<String, Object> paramMap);
	/**营销活动申请附件*/
	Page<SysAttachmentVO> queryActivityAttachment(Map<String, Object> paramMap);

}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesProcessAttachmentVO;
import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date
 */
public interface FesOnlineProcessService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess);

	/**
	 * 按ID删除对象
	 * @param attachmentId 附件id 
	 * @param processId 
	 * @param processId 
	 * @param prefixPath 
	 * @param sessionUser 
	 */
	JsonResult deleteSysAttachmentById (Integer processId, Integer attachmentId, String prefixPath, SysUserVO sessionUser);
	/**
	 * 新增多个对象 @return :
	 */
	int batchInsertFesOnlineProcesss(Integer programId, SysUserVO sessionUser);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesOnlineProcess(FesOnlineProcessVO fesOnlineProcess);

	/**
	 * 修改对象的状态 @return : 影响的行数
	 */
	JsonResult updateFesOnlineProcessStatus(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesOnlineProcesssToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesOnlineProcesssToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteFesOnlineProcessById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesOnlineProcessByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	FesOnlineProcessVO getFesOnlineProcessById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesOnlineProcessVO> getFesOnlineProcesss(FesOnlineProcessVO fesOnlineProcess);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getFesOnlineProcesssMap(FesOnlineProcessVO fesOnlineProcess);

	/**
	 * @param prefixPath 
			Map<String, Object> fileMap, SysUserVO sessionUser);

	/**
	 * 如果上传成功判断附件是否等于1，等于一要变更当前流程的状态， 大于1说明状态已经做了变更(不包括步骤6和7)
	 * @return 
	 */
	FesOnlineProcessVO updateProccessStatusForUpload(Integer processId, Integer userId);

	/**
	 * 通过流程id得到stepNum
	 */
	String getStepNumByProcessId(Integer processId);

	/**
	 * @Description 
	 * @param processId
	 * @param sessionUser
	 * @return
	 * @return FesOnlineProcessVO
	 * @throws 
	 */
	FesOnlineProcessVO updateProccessStatusForDeleteFile(Integer processId, SysUserVO sessionUser);

	/**
	 * 查询返回复杂的对象
	 */
	List<FesOnlineProcessVO> getComplexFesOnlineProcesss(FesOnlineProcessVO fesOnlineProcessVO);

	/**
	 * @Description 判断附件个数对于每一个步骤是否等于1
	 * @param processAttachmentType 对于步骤6和步骤7要判断 附件类型
	 */
	boolean ifAttachmentsForStepEqualsOne(Integer processId, String processAttachmentType);

	/**
	 *根据流程id和附件类型返回附件信息列表
	 */
	List<FesProcessAttachmentVO> getAttachmentListByProcess(Integer processId, String processAttachmentType);

	/**
	 * 根据具体的步骤更新流程状态 : 大部分是上传完成的时候更改状态，没有待办事项的创建
	 */
	FesOnlineProcessVO updateStepProcessStatus(FesOnlineProcessVO fesOnlineProcessVO, String newStatus, Integer userId);

	/**
	 * 为某一个商户的某一个流程批关闭待办事项
	 */
	void closeToDoListsForMrechant(Integer processId, String businessType, Integer merchantId);

	/**
	 * 计算上线流程的总状态
	 */
	String calculateOrUpdateProcessFinalStatus(Integer merchantId, SysUserVO sessionUser);

	/**
	 * 工具方法，通过商户id和步骤代码查询上线流程 
	 */
	FesOnlineProcessVO getFesOnlineProcessByMerchantAndStep(FesOnlineProcessVO fesOnlineProcessVO);

	/**
	 * 如果 卡类型和短信规则都添加了， 则更新状态
	 * @return 
	 */
	String updateStep5Status(Integer processId, String newStatus, Integer userId);

	/**
	 * @Description 计算上线流程的总状态
	 */
	String calculateProcessFinalStatus(List<FesOnlineProcessVO> complexFesOnlineProcesss, SysUserVO sessionUser);

	/**
	 * @Description  项目上线,更改实际上线时间
	 */
	void updateProgramOnlineTime(Integer merchantId);

	/**
	 * @Description 上传预览文件
	 */
	JsonResult saveUploadFilesForReview(MultipartFile myfile, String basePath);

	/**
	 * 上线后确认已回访后关闭待办事项
	 */
	void closeToDoListsAfterViste(Integer merchantId);

	JsonResult saveUploadFiles(MultipartFile myfile, Integer processId, Integer typeId, String basePath,
			Map<String, Object> fileMap, SysUserVO sessionUser);

	/**
	 * 上线后 查找流程中的步骤
	 */
	List<FesOnlineProcessVO> getComplexFesOnlineProcesssAfterOnline(FesOnlineProcessVO fesOnlineProcessVO);
	/**
	 * 保存后台设置
	 */
	int saveBackGroundContent(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser);

	int deleteBackGroundContent(FesOnlineProcessVO fesOnlineProcessVO, SysUserVO sessionUser);

	JsonResult updateFesOnlineProcessStatusForReject(FesOnlineProcessVO fesOnlineProcess, SysUserVO sessionUser);
}

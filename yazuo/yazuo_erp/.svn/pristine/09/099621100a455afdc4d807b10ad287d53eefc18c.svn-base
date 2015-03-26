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

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.TreeVO;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface MktBrandInterviewService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveMktBrandInterview (MktBrandInterviewVO mktBrandInterview, HttpServletRequest request);
	/**
	 * 新增多个对象 @return : 
	 */
	int batchInsertMktBrandInterviews (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateMktBrandInterview (MktBrandInterviewVO mktBrandInterview, HttpServletRequest request);
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
	MktBrandInterviewVO loadMktBrandInterview(MktBrandInterviewVO mktBrandInterview);
	SysAttachmentVO uploadAttachment(MultipartFile myfile, String basePath, SysUserVO user);
	JsonResult loadMerchantInfo(SynMerchantVO synMerchant);
	Object updateSynMerchantAndBrandInfo(MktBrandInterviewVO mktBrandInterview, SysUserVO user);

}

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

package com.yazuo.erp.fes.service.impl;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.fes.service.FesPlanAttachmentService;

@Service
public class FesPlanAttachmentServiceImpl implements FesPlanAttachmentService {
	
	@Resource
	private FesPlanAttachmentDao fesPlanAttachmentDao;
	
	public int saveFesPlanAttachment (FesPlanAttachmentVO fesPlanAttachment) {
		return fesPlanAttachmentDao.saveFesPlanAttachment(fesPlanAttachment);
	}
	public int batchInsertFesPlanAttachments (Map<String, Object> map){
		return fesPlanAttachmentDao.batchInsertFesPlanAttachments(map);
	}
	public int updateFesPlanAttachment (FesPlanAttachmentVO fesPlanAttachment){
		return fesPlanAttachmentDao.updateFesPlanAttachment(fesPlanAttachment);
	}
	public int batchUpdateFesPlanAttachmentsToDiffVals (Map<String, Object> map){
		return fesPlanAttachmentDao.batchUpdateFesPlanAttachmentsToDiffVals(map);
	}
	public int batchUpdateFesPlanAttachmentsToSameVals (Map<String, Object> map){
		return fesPlanAttachmentDao.batchUpdateFesPlanAttachmentsToSameVals(map);
	}
	public int deleteFesPlanAttachmentById (Integer id){
		return fesPlanAttachmentDao.deleteFesPlanAttachmentById(id);
	}
	public int batchDeleteFesPlanAttachmentByIds (List<Integer> ids){
		return fesPlanAttachmentDao.batchDeleteFesPlanAttachmentByIds(ids);
	}
	public FesPlanAttachmentVO getFesPlanAttachmentById(Integer id){
		return fesPlanAttachmentDao.getFesPlanAttachmentById(id);
	}
	public List<FesPlanAttachmentVO> getFesPlanAttachments (FesPlanAttachmentVO fesPlanAttachment){
		return fesPlanAttachmentDao.getFesPlanAttachments(fesPlanAttachment);
	}
	public List<Map<String, Object>>  getFesPlanAttachmentsMap (FesPlanAttachmentVO fesPlanAttachment){
		return fesPlanAttachmentDao.getFesPlanAttachmentsMap(fesPlanAttachment);
	}
}

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
import com.yazuo.erp.fes.service.FesProcessAttachmentService;

@Service
public class FesProcessAttachmentServiceImpl implements FesProcessAttachmentService {
	
	@Resource
	private FesProcessAttachmentDao fesProcessAttachmentDao;
	
	public int saveFesProcessAttachment (FesProcessAttachmentVO fesProcessAttachment) {
		return fesProcessAttachmentDao.saveFesProcessAttachment(fesProcessAttachment);
	}
	public int batchInsertFesProcessAttachments (Map<String, Object> map){
		return fesProcessAttachmentDao.batchInsertFesProcessAttachments(map);
	}
	public int updateFesProcessAttachment (FesProcessAttachmentVO fesProcessAttachment){
		return fesProcessAttachmentDao.updateFesProcessAttachment(fesProcessAttachment);
	}
	public int batchUpdateFesProcessAttachmentsToDiffVals (Map<String, Object> map){
		return fesProcessAttachmentDao.batchUpdateFesProcessAttachmentsToDiffVals(map);
	}
	public int batchUpdateFesProcessAttachmentsToSameVals (Map<String, Object> map){
		return fesProcessAttachmentDao.batchUpdateFesProcessAttachmentsToSameVals(map);
	}
	public int deleteFesProcessAttachmentById (Integer id){
		return fesProcessAttachmentDao.deleteFesProcessAttachmentById(id);
	}
	public int batchDeleteFesProcessAttachmentByIds (List<Integer> ids){
		return fesProcessAttachmentDao.batchDeleteFesProcessAttachmentByIds(ids);
	}
	public FesProcessAttachmentVO getFesProcessAttachmentById(Integer id){
		return fesProcessAttachmentDao.getFesProcessAttachmentById(id);
	}
	public List<FesProcessAttachmentVO> getFesProcessAttachments (FesProcessAttachmentVO fesProcessAttachment){
		return fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
	}
	public List<Map<String, Object>>  getFesProcessAttachmentsMap (FesProcessAttachmentVO fesProcessAttachment){
		return fesProcessAttachmentDao.getFesProcessAttachmentsMap(fesProcessAttachment);
	}
}

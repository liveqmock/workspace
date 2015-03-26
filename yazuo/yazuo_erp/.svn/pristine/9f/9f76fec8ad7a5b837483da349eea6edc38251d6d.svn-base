/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service.impl;

import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Service;
import com.yazuo.erp.bes.service.BesCallRecordService;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class BesCallRecordServiceImpl implements BesCallRecordService {
	
	@Resource
	private BesCallRecordDao besCallRecordDao;
	@Resource private MktContactService mktContactService;
	
	@Override
	public int saveBesCallRecord (BesCallRecordVO besCallRecord, SysUserVO sessionUser) {
		Integer userId = sessionUser.getId();
		besCallRecord.setInsertBy(userId);
		besCallRecord.setInsertTime(new Date());
		besCallRecord.setUpdateBy(userId);
		besCallRecord.setUpdateTime(new Date());
		besCallRecord.setIsEnable(Constant.Enable);
		return besCallRecordDao.saveBesCallRecord(besCallRecord);
	}
	public int batchInsertBesCallRecords (Map<String, Object> map){
		return besCallRecordDao.batchInsertBesCallRecords(map);
	}
	public int updateBesCallRecord (BesCallRecordVO besCallRecord){
		return besCallRecordDao.updateBesCallRecord(besCallRecord);
	}
	public int batchUpdateBesCallRecordsToDiffVals (Map<String, Object> map){
		return besCallRecordDao.batchUpdateBesCallRecordsToDiffVals(map);
	}
	public int batchUpdateBesCallRecordsToSameVals (Map<String, Object> map){
		return besCallRecordDao.batchUpdateBesCallRecordsToSameVals(map);
	}
	public int deleteBesCallRecordById (Integer id){
		return besCallRecordDao.deleteBesCallRecordById(id);
	}
	public int batchDeleteBesCallRecordByIds (List<Integer> ids){
		return besCallRecordDao.batchDeleteBesCallRecordByIds(ids);
	}
	public BesCallRecordVO getBesCallRecordById(Integer id){
		return besCallRecordDao.getBesCallRecordById(id);
	}
	@Override
	public List<BesCallRecordVO> getBesCallRecords (BesCallRecordVO besCallRecord){
		List<BesCallRecordVO> list = besCallRecordDao.getBesCallRecords(besCallRecord);
		CollectionUtils.collect(list.iterator(), new Transformer() {
			@Override
			public Object transform(Object input) {
				BesCallRecordVO besCallRecord = (BesCallRecordVO)input;
				//联系人
				Integer contactId = besCallRecord.getContactId();
				final MktContactVO mktContactById = mktContactService.getMktContactById(contactId);
				besCallRecord.setContactMap(new HashMap<String, Object>() {
					{
						put("tel", mktContactById.getTelephone());
						put("userName", mktContactById.getName());
						put("id", mktContactById.getId().toString());
					}
				});
				return besCallRecord;
			}
		});
	    return list;
	}
	public List<Map<String, Object>>  getBesCallRecordsMap (BesCallRecordVO besCallRecord){
		return besCallRecordDao.getBesCallRecordsMap(besCallRecord);
	}
}

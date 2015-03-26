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

package com.yazuo.erp.system.service.impl;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.system.service.SysDocumentDtlOptionService;

@Service
public class SysDocumentDtlOptionServiceImpl implements SysDocumentDtlOptionService {
	
	@Resource
	private SysDocumentDtlOptionDao sysDocumentDtlOptionDao;
	
	public int saveSysDocumentDtlOption (SysDocumentDtlOptionVO sysDocumentDtlOption) {
		return sysDocumentDtlOptionDao.saveSysDocumentDtlOption(sysDocumentDtlOption);
	}
	public int batchInsertSysDocumentDtlOptions (Map<String, Object> map){
		return sysDocumentDtlOptionDao.batchInsertSysDocumentDtlOptions(map);
	}
	public int updateSysDocumentDtlOption (SysDocumentDtlOptionVO sysDocumentDtlOption){
		return sysDocumentDtlOptionDao.updateSysDocumentDtlOption(sysDocumentDtlOption);
	}
	public int batchUpdateSysDocumentDtlOptionsToDiffVals (Map<String, Object> map){
		return sysDocumentDtlOptionDao.batchUpdateSysDocumentDtlOptionsToDiffVals(map);
	}
	public int batchUpdateSysDocumentDtlOptionsToSameVals (Map<String, Object> map){
		return sysDocumentDtlOptionDao.batchUpdateSysDocumentDtlOptionsToSameVals(map);
	}
	public int deleteSysDocumentDtlOptionById (Integer id){
		return sysDocumentDtlOptionDao.deleteSysDocumentDtlOptionById(id);
	}
	public int batchDeleteSysDocumentDtlOptionByIds (List<Integer> ids){
		return sysDocumentDtlOptionDao.batchDeleteSysDocumentDtlOptionByIds(ids);
	}
	public SysDocumentDtlOptionVO getSysDocumentDtlOptionById(Integer id){
		return sysDocumentDtlOptionDao.getSysDocumentDtlOptionById(id);
	}
	public List<SysDocumentDtlOptionVO> getSysDocumentDtlOptions (SysDocumentDtlOptionVO sysDocumentDtlOption){
		return sysDocumentDtlOptionDao.getSysDocumentDtlOptions(sysDocumentDtlOption);
	}
	public List<Map<String, Object>>  getSysDocumentDtlOptionsMap (SysDocumentDtlOptionVO sysDocumentDtlOption){
		return sysDocumentDtlOptionDao.getSysDocumentDtlOptionsMap(sysDocumentDtlOption);
	}
}

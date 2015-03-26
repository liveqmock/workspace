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
import com.yazuo.erp.system.service.SysDocumentDtlService;

@Service
public class SysDocumentDtlServiceImpl implements SysDocumentDtlService {
	
	@Resource
	private SysDocumentDtlDao sysDocumentDtlDao;
	
	public int saveSysDocumentDtl (SysDocumentDtlVO sysDocumentDtl) {
		return sysDocumentDtlDao.saveSysDocumentDtl(sysDocumentDtl);
	}
	public int batchInsertSysDocumentDtls (Map<String, Object> map){
		return sysDocumentDtlDao.batchInsertSysDocumentDtls(map);
	}
	public int updateSysDocumentDtl (SysDocumentDtlVO sysDocumentDtl){
		return sysDocumentDtlDao.updateSysDocumentDtl(sysDocumentDtl);
	}
	public int batchUpdateSysDocumentDtlsToDiffVals (Map<String, Object> map){
		return sysDocumentDtlDao.batchUpdateSysDocumentDtlsToDiffVals(map);
	}
	public int batchUpdateSysDocumentDtlsToSameVals (Map<String, Object> map){
		return sysDocumentDtlDao.batchUpdateSysDocumentDtlsToSameVals(map);
	}
	public int deleteSysDocumentDtlById (Integer id){
		return sysDocumentDtlDao.deleteSysDocumentDtlById(id);
	}
	public int batchDeleteSysDocumentDtlByIds (List<Integer> ids){
		return sysDocumentDtlDao.batchDeleteSysDocumentDtlByIds(ids);
	}
	public SysDocumentDtlVO getSysDocumentDtlById(Integer id){
		return sysDocumentDtlDao.getSysDocumentDtlById(id);
	}
	public List<SysDocumentDtlVO> getSysDocumentDtls (SysDocumentDtlVO sysDocumentDtl){
		return sysDocumentDtlDao.getSysDocumentDtls(sysDocumentDtl);
	}
	public List<Map<String, Object>>  getSysDocumentDtlsMap (SysDocumentDtlVO sysDocumentDtl){
		return sysDocumentDtlDao.getSysDocumentDtlsMap(sysDocumentDtl);
	}
}

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

import com.yazuo.erp.interceptors.Page;
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
import com.yazuo.erp.system.service.SysRemindService;

@Service
public class SysRemindServiceImpl implements SysRemindService {
	
	@Resource
	private SysRemindDao sysRemindDao;
	
	public int saveSysRemind (SysRemindVO sysRemind) {
		return sysRemindDao.saveSysRemind(sysRemind);
	}
	public int batchInsertSysReminds (Map<String, Object> map){
		return sysRemindDao.batchInsertSysReminds(map);
	}
	public int updateSysRemind (SysRemindVO sysRemind){
		return sysRemindDao.updateSysRemind(sysRemind);
	}
	public int batchUpdateSysRemindsToDiffVals (Map<String, Object> map){
		return sysRemindDao.batchUpdateSysRemindsToDiffVals(map);
	}
	public int batchUpdateSysRemindsToSameVals (Map<String, Object> map){
		return sysRemindDao.batchUpdateSysRemindsToSameVals(map);
	}
	public int deleteSysRemindById (Integer id){
		return sysRemindDao.deleteSysRemindById(id);
	}
	public int batchDeleteSysRemindByIds (List<Integer> ids){
		return sysRemindDao.batchDeleteSysRemindByIds(ids);
	}
	public SysRemindVO getSysRemindById(Integer id){
		return sysRemindDao.getSysRemindById(id);
	}
	public Page<SysRemindVO> getSysReminds (SysRemindVO sysRemind){
		return sysRemindDao.getSysReminds(sysRemind);
	}
	public List<Map<String, Object>>  getSysRemindsMap (SysRemindVO sysRemind){
		return sysRemindDao.getSysRemindsMap(sysRemind);
	}
	
	@Override
	public List<SysRemindVO> getRemindsByMerchantIdAndUserId(SysRemindVO sysRemind, SysUserVO user) {
		sysRemind.setUserId(user.getId());
		sysRemind.setItemStatus("1");
		return sysRemindDao.getRemindsByMerchantIdAndUserId(sysRemind);
	}
	
	
}

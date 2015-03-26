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
import com.yazuo.erp.system.service.SysAttachmentUserService;

@Service
public class SysAttachmentUserServiceImpl implements SysAttachmentUserService {
	
	@Resource
	private SysAttachmentUserDao sysAttachmentUserDao;
	
	public int saveSysAttachmentUser (SysAttachmentUserVO sysAttachmentUser) {
		return sysAttachmentUserDao.saveSysAttachmentUser(sysAttachmentUser);
	}
	public int batchInsertSysAttachmentUsers (Map<String, Object> map){
		return sysAttachmentUserDao.batchInsertSysAttachmentUsers(map);
	}
	public int updateSysAttachmentUser (SysAttachmentUserVO sysAttachmentUser){
		return sysAttachmentUserDao.updateSysAttachmentUser(sysAttachmentUser);
	}
	public int batchUpdateSysAttachmentUsersToDiffVals (Map<String, Object> map){
		return sysAttachmentUserDao.batchUpdateSysAttachmentUsersToDiffVals(map);
	}
	public int batchUpdateSysAttachmentUsersToSameVals (Map<String, Object> map){
		return sysAttachmentUserDao.batchUpdateSysAttachmentUsersToSameVals(map);
	}
	public int deleteSysAttachmentUserById (Integer id){
		return sysAttachmentUserDao.deleteSysAttachmentUserById(id);
	}
	public int batchDeleteSysAttachmentUserByIds (List<Integer> ids){
		return sysAttachmentUserDao.batchDeleteSysAttachmentUserByIds(ids);
	}
	public SysAttachmentUserVO getSysAttachmentUserById(Integer id){
		return sysAttachmentUserDao.getSysAttachmentUserById(id);
	}
	public List<SysAttachmentUserVO> getSysAttachmentUsers (SysAttachmentUserVO sysAttachmentUser){
		return sysAttachmentUserDao.getSysAttachmentUsers(sysAttachmentUser);
	}
	public List<Map<String, Object>>  getSysAttachmentUsersMap (SysAttachmentUserVO sysAttachmentUser){
		return sysAttachmentUserDao.getSysAttachmentUsersMap(sysAttachmentUser);
	}
}

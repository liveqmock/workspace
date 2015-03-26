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
import com.yazuo.erp.system.service.SysQuestionOptionService;

@Service
public class SysQuestionOptionServiceImpl implements SysQuestionOptionService {
	
	@Resource
	private SysQuestionOptionDao sysQuestionOptionDao;
	
	public int saveSysQuestionOption (SysQuestionOptionVO sysQuestionOption) {
		return sysQuestionOptionDao.saveSysQuestionOption(sysQuestionOption);
	}
	public int batchInsertSysQuestionOptions (Map<String, Object> map){
		return sysQuestionOptionDao.batchInsertSysQuestionOptions(map);
	}
	public int updateSysQuestionOption (SysQuestionOptionVO sysQuestionOption){
		return sysQuestionOptionDao.updateSysQuestionOption(sysQuestionOption);
	}
	public int batchUpdateSysQuestionOptionsToDiffVals (Map<String, Object> map){
		return sysQuestionOptionDao.batchUpdateSysQuestionOptionsToDiffVals(map);
	}
	public int batchUpdateSysQuestionOptionsToSameVals (Map<String, Object> map){
		return sysQuestionOptionDao.batchUpdateSysQuestionOptionsToSameVals(map);
	}
	public int deleteSysQuestionOptionById (Integer id){
		return sysQuestionOptionDao.deleteSysQuestionOptionById(id);
	}
	public int batchDeleteSysQuestionOptionByIds (List<Integer> ids){
		return sysQuestionOptionDao.batchDeleteSysQuestionOptionByIds(ids);
	}
	public SysQuestionOptionVO getSysQuestionOptionById(Integer id){
		return sysQuestionOptionDao.getSysQuestionOptionById(id);
	}
	public List<SysQuestionOptionVO> getSysQuestionOptions (SysQuestionOptionVO sysQuestionOption){
		return sysQuestionOptionDao.getSysQuestionOptions(sysQuestionOption);
	}
	public List<Map<String, Object>>  getSysQuestionOptionsMap (SysQuestionOptionVO sysQuestionOption){
		return sysQuestionOptionDao.getSysQuestionOptionsMap(sysQuestionOption);
	}
}

/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysMailTemplateDao;
import com.yazuo.erp.system.service.SysMailTemplateService;
import com.yazuo.erp.system.vo.SysMailTemplateVO;
/**
 * @author erp team
 * @date 
 */

@Service
public class SysMailTemplateServiceImpl implements SysMailTemplateService {
	
	@Resource
	private SysMailTemplateDao sysMailTemplateDao;
	
	public int saveSysMailTemplate (SysMailTemplateVO sysMailTemplate) {
		return sysMailTemplateDao.saveSysMailTemplate(sysMailTemplate);
	}
	public int batchInsertSysMailTemplates (Map<String, Object> map){
		return sysMailTemplateDao.batchInsertSysMailTemplates(map);
	}
	public int updateSysMailTemplate (SysMailTemplateVO sysMailTemplate){
		return sysMailTemplateDao.updateSysMailTemplate(sysMailTemplate);
	}
	public int batchUpdateSysMailTemplatesToDiffVals (Map<String, Object> map){
		return sysMailTemplateDao.batchUpdateSysMailTemplatesToDiffVals(map);
	}
	public int batchUpdateSysMailTemplatesToSameVals (Map<String, Object> map){
		return sysMailTemplateDao.batchUpdateSysMailTemplatesToSameVals(map);
	}
	public int deleteSysMailTemplateById (Integer id){
		return sysMailTemplateDao.deleteSysMailTemplateById(id);
	}
	public int batchDeleteSysMailTemplateByIds (List<Integer> ids){
		return sysMailTemplateDao.batchDeleteSysMailTemplateByIds(ids);
	}
	public SysMailTemplateVO getSysMailTemplateById(Integer id){
		return sysMailTemplateDao.getSysMailTemplateById(id);
	}
	public Page<SysMailTemplateVO> getSysMailTemplates (SysMailTemplateVO sysMailTemplate){
		return sysMailTemplateDao.getSysMailTemplates(sysMailTemplate);
	}
	public List<Map<String, Object>>  getSysMailTemplatesMap (SysMailTemplateVO sysMailTemplate){
		return sysMailTemplateDao.getSysMailTemplatesMap(sysMailTemplate);
	}
}

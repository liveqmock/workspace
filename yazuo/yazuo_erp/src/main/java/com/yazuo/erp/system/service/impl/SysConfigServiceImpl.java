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

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.system.dao.SysConfigDao;
import com.yazuo.erp.system.service.SysConfigService;
import com.yazuo.erp.system.vo.SysConfigVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class SysConfigServiceImpl implements SysConfigService {
	
	@Resource
	private SysConfigDao sysConfigDao;
	
	@Override
	public int saveOrUpdateSysConfig (SysConfigVO sysConfig, SysUserVO sessionUser) {
		Integer id = sysConfig.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			sysConfig.setUpdateBy(userId);
			sysConfig.setUpdateTime(new Date());
			row = sysConfigDao.updateSysConfig(sysConfig);
		}else{
			sysConfig.setUpdateBy(userId);
			sysConfig.setUpdateTime(new Date());
			row = sysConfigDao.saveSysConfig(sysConfig);
		}
		return row;
	}
	
	public int saveSysConfig (SysConfigVO sysConfig) {
		return sysConfigDao.saveSysConfig(sysConfig);
	}
	public int batchInsertSysConfigs (Map<String, Object> map){
		return sysConfigDao.batchInsertSysConfigs(map);
	}
	public int updateSysConfig (SysConfigVO sysConfig){
		return sysConfigDao.updateSysConfig(sysConfig);
	}
	public int batchUpdateSysConfigsToDiffVals (Map<String, Object> map){
		return sysConfigDao.batchUpdateSysConfigsToDiffVals(map);
	}
	public int batchUpdateSysConfigsToSameVals (Map<String, Object> map){
		return sysConfigDao.batchUpdateSysConfigsToSameVals(map);
	}
	public int deleteSysConfigById (Integer id){
		return sysConfigDao.deleteSysConfigById(id);
	}
	public int batchDeleteSysConfigByIds (List<Integer> ids){
		return sysConfigDao.batchDeleteSysConfigByIds(ids);
	}
	public SysConfigVO getSysConfigById(Integer id){
		return sysConfigDao.getSysConfigById(id);
	}
	public List<SysConfigVO> getSysConfigs (SysConfigVO sysConfig){
		return sysConfigDao.getSysConfigs(sysConfig);
	}
	public List<Map<String, Object>>  getSysConfigsMap (SysConfigVO sysConfig){
		return sysConfigDao.getSysConfigsMap(sysConfig);
	}

    protected SysConfigVO getSysConfigByKey(String key) {
        SysConfigVO configVO = new SysConfigVO();
        configVO.setConfigKey(key);
        List<SysConfigVO> configVOs = this.sysConfigDao.getSysConfigs(configVO);
        return configVOs.size() > 0 ? configVOs.get(0) : null;
    }

    @Override
    public String getSysConfigValueByKey(String key) {
        SysConfigVO configVO = this.getSysConfigByKey(key);
        return configVO == null ? null : configVO.getConfigValue();
    }

    @Override
    public String setSysConfigValueByKey(String key, String val,Integer userId) {
        SysConfigVO configVO = this.getSysConfigByKey(key);
        if (configVO.getConfigValue().equals(val)) {
            return val;
        }else{
            configVO.setUpdateBy(userId);
            configVO.setUpdateTime(new Date());
            configVO.setConfigValue(val);
            this.sysConfigDao.updateSysConfig(configVO);
            return val;
        }
    }
}

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import static com.yazuo.erp.base.Constant.DropDownList.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.dao.BesTalkingSkillsDao;
import com.yazuo.erp.bes.service.BesTalkingSkillsService;
import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.StringUtil;

@Service
public class BesTalkingSkillsServiceImpl implements BesTalkingSkillsService {
	
	@Resource private BesTalkingSkillsDao besTalkingSkillsDao;
	@Resource private SysResourceDao sysResourceDao;
	
	@Override
	public int saveOrUpdateBesTalkingSkills (BesTalkingSkillsVO besTalkingSkills, SysUserVO sessionUser) {
		Integer id = besTalkingSkills.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			besTalkingSkills.setUpdateBy(userId);
			besTalkingSkills.setUpdateTime(new Date());
			besTalkingSkills.setIsEnable(Constant.Enable);
			row = besTalkingSkillsDao.updateBesTalkingSkills(besTalkingSkills);
		}else{
			besTalkingSkills.setInsertBy(userId);
			besTalkingSkills.setInsertTime(new Date());
			besTalkingSkills.setUpdateBy(userId);
			besTalkingSkills.setUpdateTime(new Date());
			besTalkingSkills.setIsEnable(Constant.Enable);
			row = besTalkingSkillsDao.saveBesTalkingSkills(besTalkingSkills);
		}
		return row;
	}
	@Override
	public BesTalkingSkillsVO loadBesTalkingSkills (BesTalkingSkillsVO besTalkingSkills){
		BesTalkingSkillsVO besTalkingSkillsVO = new BesTalkingSkillsVO();
		Integer id = besTalkingSkills.getId();
		if(id != null){
			besTalkingSkillsVO = this.besTalkingSkillsDao.getBesTalkingSkillsById(id);
		}

		String resourceRemark = besTalkingSkillsVO.getResourceRemark();
		String resourceExtraRemark = besTalkingSkillsVO.getResourceExtraRemark();
		
		//封装下拉列表框  权限
		List<Object> allList = this.getResourceCascateList(id, resourceRemark, resourceExtraRemark);
		
		besTalkingSkillsVO.setDropDownList(allList);
		return besTalkingSkillsVO;
	}
	
	@Override
	public List<Object> getResourceCascateList(Integer id, String resourceRemark, String resourceExtraRemark) {
		List<Object> allList = new ArrayList<Object>();
		Map<String, Object> firstMap = new HashMap<String, Object>(){
			{put("parentTreeCode", "006007");}  //业务处理
		};
		List<SysResourceVO> firstSysResources = this.sysResourceDao.getSysResources(firstMap);
		for (SysResourceVO sysResourceVO : firstSysResources) {
			Map<String, Object> dropDownListMap = new HashMap<String, Object>();
			allList.add(dropDownListMap);
			dropDownListMap.put(value, sysResourceVO.getRemark());
			dropDownListMap.put(text, sysResourceVO.getResourceName());
			dropDownListMap.put(isSelected, 0);
			if(id!=null){
				if(resourceRemark.equals(sysResourceVO.getRemark())){
					dropDownListMap.put(isSelected, 1);
				}
			}
			//封装子集
			Map<String, Object> parameMap = new HashMap<String, Object>();
			parameMap.put("parentTreeCode", sysResourceVO.getTreeCode());
			List<SysResourceVO> secondSysResources = this.sysResourceDao.getSysResources(parameMap);
			List<Object> subList = new ArrayList<Object>();
			for (SysResourceVO sysResourceVO2 : secondSysResources) {
				Map<String, Object> secondMap = new HashMap<String, Object>();
				secondMap.put(value, sysResourceVO2.getRemark());
				secondMap.put(text, sysResourceVO2.getResourceName());
				secondMap.put(isSelected, 0);
				if(id!=null){
					if(resourceExtraRemark!=null && resourceExtraRemark.equals(sysResourceVO2.getRemark())){
						secondMap.put(isSelected, 1);
					}
				}
				subList.add(secondMap);
			}
			dropDownListMap.put(children, subList);
		}
		return allList;
	}
	/**
	 * 通过父节点查找资源
	 */
	@Override
	public List<SysResourceVO> getResourceByParent(BesTalkingSkillsVO besTalkingSkills) {
		Map<String, Object> firstMap = new HashMap<String, Object>(){
			{put("parentTreeCode", "006007");}
		};
		List<SysResourceVO> firstSysResources = this.sysResourceDao.getSysResources(firstMap);
		if(besTalkingSkills!=null){
			//添加选中项
			for (SysResourceVO sysResourceVO : firstSysResources) {
				sysResourceVO.setIsSelected("0");
				String resourceRemark = besTalkingSkills.getResourceRemark();
				if(!StringUtil.isNullOrEmpty(resourceRemark)){
					if(resourceRemark.equals(sysResourceVO.getRemark())){
						sysResourceVO.setIsSelected("1");
					}
				}
			}
		}
		return firstSysResources;
	}
	
	public int saveBesTalkingSkills (BesTalkingSkillsVO besTalkingSkills) {
		return besTalkingSkillsDao.saveBesTalkingSkills(besTalkingSkills);
	}
	public int batchInsertBesTalkingSkillss (Map<String, Object> map){
		return besTalkingSkillsDao.batchInsertBesTalkingSkillss(map);
	}
	public int updateBesTalkingSkills (BesTalkingSkillsVO besTalkingSkills){
		return besTalkingSkillsDao.updateBesTalkingSkills(besTalkingSkills);
	}
	public int batchUpdateBesTalkingSkillssToDiffVals (Map<String, Object> map){
		return besTalkingSkillsDao.batchUpdateBesTalkingSkillssToDiffVals(map);
	}
	public int batchUpdateBesTalkingSkillssToSameVals (Map<String, Object> map){
		return besTalkingSkillsDao.batchUpdateBesTalkingSkillssToSameVals(map);
	}
	public int deleteBesTalkingSkillsById (Integer id){
		return besTalkingSkillsDao.deleteBesTalkingSkillsById(id);
	}
	public int batchDeleteBesTalkingSkillsByIds (List<Integer> ids){
		return besTalkingSkillsDao.batchDeleteBesTalkingSkillsByIds(ids);
	}
	public BesTalkingSkillsVO getBesTalkingSkillsById(Integer id){
		return besTalkingSkillsDao.getBesTalkingSkillsById(id);
	}
	public List<BesTalkingSkillsVO> getBesTalkingSkillss (BesTalkingSkillsVO besTalkingSkills){
		return besTalkingSkillsDao.getBesTalkingSkillss(besTalkingSkills);
	}
	public List<Map<String, Object>>  getBesTalkingSkillssMap (BesTalkingSkillsVO besTalkingSkills){
		return besTalkingSkillsDao.getBesTalkingSkillssMap(besTalkingSkills);
	}
}

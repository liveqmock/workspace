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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.service.ResourceService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.util.BeanUtils;
import com.yazuo.util.StringUtil;

/**
 * @Description TODO
 * @author song
 * @date 2014-6-5 上午10:28:06
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Resource
	private SysResourceDao sysResourceDao;
	
	@Override
	public List<SysResourceVO> getSysResourcesByLevel(Map<String, Object> map) {
		return this.sysResourceDao.getSysResourcesByLevel(map);
	}

	@Override
	public int saveSysResource(SysResourceVO entity) {
		return this.sysResourceDao.saveSysResource(entity);
	}

	@Override
	public int updateSysResourceVisableStatus(int id) {
		SysResourceVO entity = sysResourceDao.getSysResourceById(id);
		entity.setIsVisible(entity.getIsVisible().equals(Constant.IS_ENABLE)? Constant.IS_NOT_ENABLE: Constant.IS_ENABLE);
		return this.sysResourceDao.updateSysResource(entity);
	}

	@Override
	public int updateSysResource(SysResourceVO entity) {
		SysResourceVO dbEntity = sysResourceDao.getSysResourceById(entity.getId());
		BeanUtils.copyProperties(entity, dbEntity);
		return this.sysResourceDao.updateSysResource(dbEntity);
	}
	@Override
	public int updateSysResources(Map<String, Object> paramerMap) {
		
		List<String> ids =  (List<String>)paramerMap.get("idString");
		int flag =  (Integer)paramerMap.get("flag"); 
		paramerMap.put("flag", flag);
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		for (String id : ids) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("id", Integer.parseInt(id));
			list.add(map);
		}
		paramerMap.put("listKey", list);
		return this.sysResourceDao.updateSysResources(paramerMap);
	}

	@Override
	public int deleteSysResource(Integer id) {
		
		return this.sysResourceDao.deleteSysResource(id);
	}

	@Override
	public SysResourceVO getSysResourceById(Integer id) {
		
		return this.sysResourceDao.getSysResourceById(id);
	}

	@Override
	public List<Map<String, Object>> getAllSysResources() {
		
		return this.sysResourceDao.getAllSysResources();
	}
	
	@Override
	public List<SysResourceVO> getSysResources(Map<String, Object> map) {
		
		return this.sysResourceDao.getSysResources(map);
	}
	@Override
	public Page<Map<String, Object>> getSysResourcesMap(Map<String, Object> paramMap) {
		
		int pageNumber =(Integer) (paramMap.get("pageNumber"));
		int pageSize = (Integer) paramMap.get("pageSize");
		PageHelper.startPage(pageNumber, pageSize, true);		
		paramMap.put("sortColumns", "sort");
		return (Page<Map<String, Object>>)this.sysResourceDao.getSysResourcesMap(paramMap);
	}
	@Override
	public Integer getSysResourceCount(Map<String, Object> map){
		return (int)this.sysResourceDao.getSysResourceCount(map);
	}
	@Override
	public List<Map<String, Object>> getPageNavigate(int currentId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		this.getAllParentNodesText(currentId, list);
		return list;
	}

	/**
	 * 通过父节点查找资源
	 */
	@Override
	public List<Map<String, Object>> getResourceByParent(String resourceRemark) {
		Map<String, Object> firstMap = new HashMap<String, Object>(){
			{put("parentTreeCode", "006007");}
		};
		List<SysResourceVO> firstSysResources = this.sysResourceDao.getSysResources(firstMap);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		//添加选中项
		for (SysResourceVO sysResourceVO : firstSysResources) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("isSelected", "0");
			if(!StringUtil.isNullOrEmpty(resourceRemark)){
				if(resourceRemark.equals(sysResourceVO.getRemark())){
					resultMap.put("isSelected", "1");
				}
			}
			resultMap.put("remark", sysResourceVO.getRemark());
			resultMap.put("resourceName", sysResourceVO.getResourceName());
			resultList.add(resultMap);
		}
		return resultList;
	}
	
	private void getAllParentNodesText (int currentId, List<Map<String, Object>> list) {
		SysResourceVO sysResourceVO = sysResourceDao.getSysResourceById(currentId);
		if(sysResourceVO!=null){
			getAllParentNodesText(sysResourceVO.getParentId(), list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", sysResourceVO.getId());
			map.put("resourceName", sysResourceVO.getResourceName());
			list.add(map);
		}
	}

	
}

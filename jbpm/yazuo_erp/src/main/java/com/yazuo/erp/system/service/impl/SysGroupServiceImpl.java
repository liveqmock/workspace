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
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.dao.SysGroupDao;
import com.yazuo.erp.system.service.SysGroupService;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.BeanUtils;
/**
 * @author erp team
 * @date 
 */

@Service
public class SysGroupServiceImpl implements SysGroupService {
	
	@Resource
	private SysGroupDao sysGroupDao;

	/**
	 * 查询所有的下属
	 * 参数 map(key='baseUserId', <userId>):  e.g. ("baseUserId", 109) 
	 */
	@Override
	public List<SysUserVO> getSubordinateEmployees(Map<String, Object> map) {
		return sysGroupDao.getSubordinateEmployees(map);
	}
	
	public int saveSysGroup (SysGroupVO entity) {
		return sysGroupDao.saveSysGroup(entity);
	}
	
	public int updateSysGroup (SysGroupVO entity){
		SysGroupVO dbEntity = sysGroupDao.getSysGroupById(entity.getId());
		BeanUtils.copyProperties(entity, dbEntity);
		return sysGroupDao.updateSysGroup(dbEntity);
	}
	
	public int deleteSysGroup(Integer id){
		if(this.checkIfExistsChildren(id)==1){
			return -1;
		}else{
			SysGroupVO entity = sysGroupDao.getSysGroupById(id);
			entity.setIsEnable(Constant.IS_NOT_ENABLE);//soft delete
			return sysGroupDao.updateSysGroup(entity);
		}
	}
	
	public SysGroupVO getSysGroupById(Integer id){
		return sysGroupDao.getSysGroupById(id);
	}

	public List<SysGroupVO> getSysGroups(Map<String, Object> inputMap){
		return sysGroupDao.getSysGroups(inputMap);
	}

	public List<Map<String, Object>>  getSysGroupsMap(){
		return sysGroupDao.getSysGroupsMap();
	}
	
	public List<Map<String, Object>> getAllSysGroups(){
		return sysGroupDao.getAllSysGroups();
	}

	/**
	 * 返回XTree需要的数据
	 */
	@Override
	public List<Map<String, Object>> getSysGroupsForXTree(){
		SysGroupVO sysGroupVO = new SysGroupVO();
		sysGroupVO.setIsEnable(Constant.IS_ENABLE);
		return sysGroupDao.getSysGroupsForXTree(sysGroupVO);
	}
	
	@Override
	/***
	 *获取组织架构的树形结构 
	 */
	public TreeNode getAllGroupNode() {
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setText("根目录");
		node.setUrl("");
		getChildrenNode(node);
		return node;
	}

	private void getChildrenNode (TreeNode parentNode) {
		List<Map<String, String>> pList = sysGroupDao.getSysGroupByParentId(Integer.parseInt(parentNode.getId()));
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Map<String, String> map : pList) {
			TreeNode node = new TreeNode();
			node.setId(String.valueOf(map.get("id")));
			node.setText(map.get("group_name"));
			getChildrenNode(node);
			treeList.add(node);
		}
		if(treeList.size()>0) parentNode.setChildrenList(treeList);
	}

	@Override
	public int addSameLevel(SysGroupVO sysGroupVO) {
		return addSaveOrNextLevel(sysGroupVO, true);
	}
	@Override
	public int addNextSameLevel(SysGroupVO sysGroupVO) {
		return addSaveOrNextLevel(sysGroupVO, false);
	}
	//需求为，无论是添加自己还是添加父级传过来的都是父级id
	private int addSaveOrNextLevel(SysGroupVO sysGroupVO, boolean isSameLevel) {
		String groupName = sysGroupVO.getGroupName();
		SysGroupVO dbSysGroupVO = sysGroupDao.getSysGroupById(sysGroupVO.getId());//父级
		Integer parentId = dbSysGroupVO.getId();
		String parentTreeCode = dbSysGroupVO.getParentTreeCode();
		SysGroupVO newVO = new SysGroupVO();
		newVO.setParentId(parentId);
		newVO.setParentTreeCode(parentTreeCode);
		newVO.setGroupName(groupName);
		//查找同级最大的tree code.
		SysGroupVO dbParentSysGroupVO = sysGroupDao.getMaxTreeCodeOfSameLevel(parentId);
		if(dbParentSysGroupVO == null){
			newVO.setTreeCode(parentTreeCode+"001");
		}else{
			String maxTreeCode = dbParentSysGroupVO.getTreeCode();
			/*  用一前缀9解决丢失0的问题
			001003  001004
			1003   1004
			001003  9001003  9001004  
			 */
			maxTreeCode = "9" + maxTreeCode;
			String newTreeCode = String.valueOf(Long.parseLong(maxTreeCode)+1);
			newTreeCode = newTreeCode.substring(1);
			newVO.setTreeCode(newTreeCode);
		}
		newVO.setIsEnable(Constant.IS_ENABLE);
		newVO.setInsertBy(Constant.DEFAULT_ADD_USER);
		newVO.setUpdateBy(Constant.DEFAULT_ADD_USER);
		newVO.setInsertTime(new Date());
		newVO.setUpdateTime(new Date());
		sysGroupDao.saveSysGroup(newVO);
		return newVO.getId();
	}
	
	public int checkIfExistsChildren(Integer groupId) {
		int count = sysGroupDao.getCountOfGroupUser(groupId);
		if (count > 0) {
			return 1;
		} else {
			// 查找子对象
			List<Map<String, String>> list = sysGroupDao.getSysGroupByParentId(groupId);
			if (list.size() > 0) {
				for (Map<String, String> map : list) {
					Integer id = (Integer)((Object)map.get("id"));
					this.checkIfExistsChildren(id);
				}
			}
		}
		return 0;
	}
}

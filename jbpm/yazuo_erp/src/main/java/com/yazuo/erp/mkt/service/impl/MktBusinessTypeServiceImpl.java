/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service.impl;

import java.sql.SQLException;
import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.RowSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;
import com.yazuo.erp.mkt.service.MktBusinessTypeService;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.TreeVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class MktBusinessTypeServiceImpl implements MktBusinessTypeService {
	
	@Resource
	private MktBusinessTypeDao mktBusinessTypeDao;
	
	@Override
	public int saveOrUpdateMktBusinessType (MktBusinessTypeVO mktBusinessType, SysUserVO sessionUser) {
		Integer id = mktBusinessType.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			mktBusinessType.setIsVisible(Constant.Enable);
			row = mktBusinessTypeDao.updateMktBusinessType(mktBusinessType);
		}else{
			mktBusinessType.setInsertBy(userId);
			mktBusinessType.setInsertTime(new Date());
			mktBusinessType.setIsVisible(Constant.Enable);
			row = mktBusinessTypeDao.saveMktBusinessType(mktBusinessType);
		}
		return row;
	}
	@Override
	public List<TreeVO> getAllNode(String[] formats) {
		TreeVO node = new TreeVO();
		node.setValue(0);
		node.setText("根目录");
		this.getChildrenNode(node, formats);
		return node.getChildren();
	}

	@SuppressWarnings("serial")
	private void getChildrenNode (final TreeVO parentNode, String[] formats) {
		if(formats==null) formats = new String[]{};
		List<MktBusinessTypeVO> pList = mktBusinessTypeDao.getMktBusinessTypes(new MktBusinessTypeVO(){{setParentId(parentNode.getValue());}});
		List<TreeVO> treeList = new ArrayList<TreeVO>();
		for (final MktBusinessTypeVO mktBusinessType : pList) {
			TreeVO node = new TreeVO();
			node.setValue(mktBusinessType.getId());
			node.setText(mktBusinessType.getTypeText());
			node.setRemark(mktBusinessType.getRemark());
			node.setIsSelected(CollectionUtils.find(Arrays.asList(formats), new Predicate() {
				public boolean evaluate(Object object) {
					String element = object.toString();
					if(element.equals(mktBusinessType.getId().toString())) return true;
					return false;
				}
			})!=null? "1": "0");
			this.getChildrenNode(node, formats);
			treeList.add(node);
		}
		if(treeList.size()>0) parentNode.setChildren(treeList);
	}
	public int saveMktBusinessType (MktBusinessTypeVO mktBusinessType) {
		return mktBusinessTypeDao.saveMktBusinessType(mktBusinessType);
	}
	public int batchInsertMktBusinessTypes (Map<String, Object> map){
		return mktBusinessTypeDao.batchInsertMktBusinessTypes(map);
	}
	public int updateMktBusinessType (MktBusinessTypeVO mktBusinessType){
		return mktBusinessTypeDao.updateMktBusinessType(mktBusinessType);
	}
	public int batchUpdateMktBusinessTypesToDiffVals (Map<String, Object> map){
		return mktBusinessTypeDao.batchUpdateMktBusinessTypesToDiffVals(map);
	}
	public int batchUpdateMktBusinessTypesToSameVals (Map<String, Object> map){
		return mktBusinessTypeDao.batchUpdateMktBusinessTypesToSameVals(map);
	}
	public int deleteMktBusinessTypeById (Integer id){
		return mktBusinessTypeDao.deleteMktBusinessTypeById(id);
	}
	public int batchDeleteMktBusinessTypeByIds (List<Integer> ids){
		return mktBusinessTypeDao.batchDeleteMktBusinessTypeByIds(ids);
	}
	public MktBusinessTypeVO getMktBusinessTypeById(Integer id){
		return mktBusinessTypeDao.getMktBusinessTypeById(id);
	}
	public List<MktBusinessTypeVO> getMktBusinessTypes (MktBusinessTypeVO mktBusinessType){
		return mktBusinessTypeDao.getMktBusinessTypes(mktBusinessType);
	}
	public List<Map<String, Object>>  getMktBusinessTypesMap (MktBusinessTypeVO mktBusinessType){
		return mktBusinessTypeDao.getMktBusinessTypesMap(mktBusinessType);
	}
}

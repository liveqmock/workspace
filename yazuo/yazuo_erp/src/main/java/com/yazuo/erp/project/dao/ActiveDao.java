package com.yazuo.erp.project.dao;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.project.vo.Active;

public interface ActiveDao {
	 public Map<String, Object> getActiveMap(Active active, int page, int pageSize);	 
	 public List<Map<String, Object>> getAllActives(Active active);
	 public List<Active> selectActiveListByObject(Active active);	 
	 public List<Map<String, Object>> getAllActiveTypes();
}

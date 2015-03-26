/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.dao;

import java.util.*;

import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


import org.springframework.stereotype.Repository;


@Repository
public interface SysDictionaryDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveSysDictionary (SysDictionaryVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateSysDictionary (SysDictionaryVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteSysDictionary(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public SysDictionaryVO getSysDictionaryById(Integer id);
	/**

	 * @parameter id
	 */
	public List<SysDictionaryVO> getAllSysDictionarys();

	/**
	 * @Description 根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> querySysDictionaryByType(String dictionaryType);

	/**
	 * @Description 标准的方式，根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws 
	 */
	public List<Map<String, Object>> querySysDictionaryByTypeStd(String dictionaryType);

	public SysDictionaryVO getSysDictionarys(Map<String, Object> map);

    /**
     * 权限类型查询字典
     * @param dictionaryVO
     * @return
     */
    public List<SysDictionaryVO> getDictionariesByType(SysDictionaryVO dictionaryVO);

    /**
     * 根据字段列表查询数据字典项
     * @param dictionaryTypes
     * @return
     */
    public List<SysDictionaryVO> getDictionariesByTypes(List<String> dictionaryTypes);
}

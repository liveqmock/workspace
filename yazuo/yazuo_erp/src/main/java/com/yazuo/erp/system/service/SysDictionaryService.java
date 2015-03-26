/**
 * @Description 数据字典相关服务
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-06-16	创建文档
 * 
 */
package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.system.vo.SysDictionaryVO;

/**
 * @Description 数据字典相关服务接口类
 * @author gaoshan
 * @date 2014-6-3 下午6:44:07
 */
public interface SysDictionaryService {
	
	/**
	 * @Description 根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return
	 * @throws
	 */
	List<Map<String,Object>> querySysDictionaryByType(String dictionaryType); 
	
	/**
	 * @Title queryDictionaryByType
	 * @Description 根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return
     *[{
          "text": "显示值"，
           "value": "1"
     }],
	 * @see com.yazuo.erp.system.service.SysDictionaryService#querySysDictionaryByType(java.lang.String)
	 */
	List<Map<String, Object>> querySysDictionaryByTypeStd(String dictionaryType); 

	/**
	 * @Description 根据类型和key查询数据字典
	 * @param dictionaryType
	 * @return
	 * @throws
	 */
	SysDictionaryVO querySysDictionaryByTypeAndKey(String dictionaryType, String key);

    /**
     * 根据类型查询数据字典列表
     * @param dictionaryType
     * @return
     */
    List<SysDictionaryVO> queryDictionariesByType(String dictionaryType);

    /**
     * 查询列表，并根据dictionaryType分组
     * @param dictionaryTypes
     * @return Map,key: dictionaryType; value:List&lt;SysDictionaryVO&gt;
     */
    Map<String, List<SysDictionaryVO>> queryDictionariesByTypes(List<String> dictionaryTypes);

	Map<String, String>  getSysDictionaryByType(String dictionaryType);

	/**
	 * @Description 根据类型列表查询有效的数据字典
	 * @param dictionaryTypeList
	 * @return
	 * @throws 
	 */
	Map<String, Object> querySysDictionaryByTypeList(List<String> dictionaryTypeList);

	Map<String, Object> getSysDictionaryMapByTypeAndKey(String dictionaryType, String key);

	List<Map<String, Object>> getDicListWithSelected(String dictionaryType, String[] key);

	List<Map<String, Object>> getDropDownList(String dictionaryType, String key);

	SysDictionaryVO querySysDictionaryByTypeAndValue(String dictionaryType, String value);

	void setStdDicRow(Object vo, String dictionaryType, String...attributeName);
}

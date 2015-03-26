/**
 * @Description 数据字典相关服务接口实现
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.service.impl;

import static com.yazuo.erp.base.Constant.DropDownList.isSelected;
import static com.yazuo.erp.base.Constant.DropDownList.value;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.dao.SysDictionaryDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysDictionaryVO;

/**
 * @Description 数据字典相关服务接口实现
 * @author gaoshan
 * @date 2014-6-16 下午2:42:49
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {

	/**
	 * 数据字典表DAO
	 */
	@Resource
	private SysDictionaryDao sysDictionaryDao;

	/**
	 * @Title queryDictionaryByType
	 * @Description 根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return [{ "text": "显示值"， "value": "1" }],
	 * @see com.yazuo.erp.system.service.SysDictionaryService#querySysDictionaryByType(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> querySysDictionaryByTypeStd(String dictionaryType) {
		return this.sysDictionaryDao.querySysDictionaryByTypeStd(dictionaryType);
	}

	/**
	 * @Title queryDictionaryByType
	 * @Description 根据类型（例如，00000001）查询数据字典
	 * @param dictionaryType
	 * @return
	 * @see com.yazuo.erp.system.service.SysDictionaryService#querySysDictionaryByType(java.lang.String)
	 */
	@Override
//    @Cacheable(value="userCache") 
//	@CacheEvict(value="merchantCache", allEntries=true)
	public List<Map<String, Object>> querySysDictionaryByType(String dictionaryType) {

//		System.out.println("method invoke!");
		return this.sysDictionaryDao.querySysDictionaryByType(dictionaryType);
	}

	@Override
	public Map<String, String> getSysDictionaryByType(String dictionaryType) {
		List<Map<String, Object>> list = this.sysDictionaryDao.querySysDictionaryByType(dictionaryType);
		Map<String, String> map = new HashMap<String, String>();
		for (Map<String, Object> m : list) {
			String key = String.valueOf(m.get("dictionary_key"));
			String value = String.valueOf(m.get("dictionary_value"));
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @Title querySysDictionaryByTypeAndKey
	 * @Description 通过类型和key查找唯一的数据字典
	 * @param dictionaryType
	 * @return
	 * @see com.yazuo.erp.system.service.SysDictionaryService#querySysDictionaryByTypeAndKey(java.lang.String)
	 */
	@Override
	public SysDictionaryVO querySysDictionaryByTypeAndKey(String dictionaryType, String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictionaryType", dictionaryType);
		map.put("dictionaryKey", key);
		return this.sysDictionaryDao.getSysDictionarys(map);
	}
	
	/**通过类型和value查找唯一的数据字典
	 */
	@Override
	public SysDictionaryVO querySysDictionaryByTypeAndValue(String dictionaryType, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictionaryType", dictionaryType);
		map.put("dictionaryValue", value);
		return this.sysDictionaryDao.getSysDictionarys(map);
	}

    @Override
    public List<SysDictionaryVO> queryDictionariesByType(String dictionaryType) {
        SysDictionaryVO dictionaryVO = new SysDictionaryVO();
        dictionaryVO.setDictionaryType(dictionaryType);
        return this.sysDictionaryDao.getDictionariesByType(dictionaryVO);
    }

    @Override
    public Map<String, List<SysDictionaryVO>> queryDictionariesByTypes(List<String> dictionaryTypes) {
        List<SysDictionaryVO> dictionaryVOs = this.sysDictionaryDao.getDictionariesByTypes(dictionaryTypes);
        Map<String, List<SysDictionaryVO>> resultMap = new HashMap<String, List<SysDictionaryVO>>();
        for (SysDictionaryVO vo : dictionaryVOs) {
            String typeStr = vo.getDictionaryType();
            List<SysDictionaryVO> tmpDictionaryVOs = resultMap.get(typeStr);
            if (tmpDictionaryVOs == null) {
                tmpDictionaryVOs = new LinkedList<SysDictionaryVO>();
                resultMap.put(typeStr, tmpDictionaryVOs);
            }
            tmpDictionaryVOs.add(vo);
        }
        return resultMap;
    }

    @Override
	public Map<String, Object> getSysDictionaryMapByTypeAndKey(String dictionaryType, String key) {
		final SysDictionaryVO querySysDictionaryByTypeAndKey = this.querySysDictionaryByTypeAndKey(dictionaryType, key);
		Map<String, Object> map = new HashMap<String, Object>();
		if(querySysDictionaryByTypeAndKey!=null){
			map.put("value", querySysDictionaryByTypeAndKey.getDictionaryKey());
			map.put("text", querySysDictionaryByTypeAndKey.getDictionaryValue());
		}
		return map;
	}
	/**
	 * 查找 数据字典下拉框,带选中状态, 可以支持多个选中
	 */
	@Override
	public List<Map<String, Object>> getDicListWithSelected(String dictionaryType, String[] keys) {

		List<Map<String, Object>> querySysDictionaryByTypeStd = this.querySysDictionaryByTypeStd(dictionaryType);
		for (Map<String, Object> map : querySysDictionaryByTypeStd) {
			map.put(isSelected, 0);
				for (String key : keys) {
				if(map.get(value).equals(key)){
					map.put(isSelected, 1);
				}
			}
		}
		return querySysDictionaryByTypeStd;
	}

	/**
	 * @Title querySysDictionaryByTypeList
	 * @Description 根据类型列表查询有效的数据字典
	 * @param dictionaryTypeList
	 * @return
	 * @see com.yazuo.erp.system.service.SysDictionaryService#querySysDictionaryByTypeList(java.util.List)
	 */
	@Override
	public Map<String, Object> querySysDictionaryByTypeList(List<String> dictionaryTypeList) {
		List<Map<String, Object>> sysDictionaryList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		for (String dictionaryType : dictionaryTypeList) {
			sysDictionaryList = this.sysDictionaryDao.querySysDictionaryByType(dictionaryType);
			map.put(dictionaryType, sysDictionaryList);
		}
		return map;
	}
	
	/**
	 * 查询下拉列表框数据字典
	 * @param dictionaryType
	 * @param key
	 */
	@Override
	public List<Map<String, Object>> getDropDownList(String dictionaryType, final String key) {
		final List<Map<String, Object>> problemType = this.querySysDictionaryByTypeStd(dictionaryType);
		CollectionUtils.forAllDo(problemType, new Closure() {
			public void execute(Object input) {
				Map<String, Object> map = (Map<String, Object>)input;
				if((map.get("value").toString().equals(key))){
					map.put(Constant.DropDownList.isSelected, "1");
				}else{
					map.put(Constant.DropDownList.isSelected, "0");
				}
			}
		});
		return problemType;
	}
}

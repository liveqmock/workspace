<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classEntity = table.className+"VO">
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.service.impl;

<#include "/java_imports.include">

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.${modelname}.service.${className}Service;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class ${className}ServiceImpl implements ${className}Service {
	
	@Resource
	private ${className}Dao ${classNameLower}Dao;
	
	@Override
	public int saveOrUpdate${className} (${classEntity} ${classNameLower}, SysUserVO sessionUser) {
		Integer id = ${classNameLower}.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			${classNameLower}.setUpdateBy(userId);
			${classNameLower}.setUpdateTime(new Date());
			${classNameLower}.setIsEnable(Constant.Enable);
			row = ${classNameLower}Dao.update${className}(${classNameLower});
		}else{
			${classNameLower}.setInsertBy(userId);
			${classNameLower}.setInsertTime(new Date());
			${classNameLower}.setUpdateBy(userId);
			${classNameLower}.setUpdateTime(new Date());
			${classNameLower}.setIsEnable(Constant.Enable);
			row = ${classNameLower}Dao.save${className}(${classNameLower});
		}
		return row;
	}
	
	public int save${className} (${classEntity} ${classNameLower}) {
		return ${classNameLower}Dao.save${className}(${classNameLower});
	}
	public int batchInsert${className}s (Map<String, Object> map){
		return ${classNameLower}Dao.batchInsert${className}s(map);
	}
	public int update${className} (${classEntity} ${classNameLower}){
		return ${classNameLower}Dao.update${className}(${classNameLower});
	}
	public int batchUpdate${className}sToDiffVals (Map<String, Object> map){
		return ${classNameLower}Dao.batchUpdate${className}sToDiffVals(map);
	}
	public int batchUpdate${className}sToSameVals (Map<String, Object> map){
		return ${classNameLower}Dao.batchUpdate${className}sToSameVals(map);
	}
	public int delete${className}ById (Integer id){
		return ${classNameLower}Dao.delete${className}ById(id);
	}
	public int batchDelete${className}ByIds (List<Integer> ids){
		return ${classNameLower}Dao.batchDelete${className}ByIds(ids);
	}
	public ${classEntity} get${className}ById(Integer id){
		return ${classNameLower}Dao.get${className}ById(id);
	}
	public List<${classEntity}> get${className}s (${classEntity} ${classNameLower}){
		return ${classNameLower}Dao.get${className}s(${classNameLower});
	}
	public List<Map<String, Object>>  get${className}sMap (${classEntity} ${classNameLower}){
		return ${classNameLower}Dao.get${className}sMap(${classNameLower});
	}
}

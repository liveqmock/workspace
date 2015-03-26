<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classEntity = table.className+"VO">
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.service;

import java.util.Map;

import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.system.vo.SysUserVO;

<#include "/java_imports.include">

public interface ${className}Service{

	/**
	 * 保存或修改
	 */
	int saveOrUpdate${className} (${classEntity} ${classNameLower}, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int save${className} (${classEntity} ${classNameLower});
	/**
	 * 新增多个对象 @return 
	 */
	int batchInsert${className}s (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int update${className} (${classEntity} ${classNameLower});
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdate${className}sToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdate${className}sToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int delete${className}ById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDelete${className}ByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	${classEntity} get${className}ById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<${classEntity}> get${className}s (${classEntity} ${classNameLower});
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  get${className}sMap (${classEntity} ${classNameLower});
	
	<#list table.columns as column>
	<#if column.unique && !column.pk>
	${classEntity} getBy${column.columnName}(${column.javaType} v);
	</#if>
	</#list>

}

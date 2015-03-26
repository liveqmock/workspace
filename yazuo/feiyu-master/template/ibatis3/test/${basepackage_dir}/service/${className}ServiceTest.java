<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classEntity = table.className+"VO">
<#assign classNameLower = className?uncap_first>  
package ${modelname}.service;

<#include "/java_imports.include">

import static org.junit.Assert.fail;

import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import com.yazuo.erp.${modelname}.service.${className}Service;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class ${className}ServiceTest extends JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(${className}ServiceTest.class);
	@Resource
	private ${className}Service ${classNameLower}Service;
	
	@Test
	public void testSave${className} (){
		//不允许为空的字段： <#list table.columns as column><#if !column.unique && !column.pk && !column.nullable>${column.sqlName},</#if></#list>
		${classEntity} ${classNameLower} = new ${classEntity}();
		int id = ${classNameLower}Service.save${className}(${classNameLower});
		Assert.assertTrue(id>0);
	}
	
}
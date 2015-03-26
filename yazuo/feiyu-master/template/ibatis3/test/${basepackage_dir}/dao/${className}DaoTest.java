<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classEntity = table.className+"VO">
<#assign classNameLower = className?uncap_first>  
package ${modelname}.dao;

<#include "/java_imports.include">

import static org.junit.Assert.fail;

import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import base.JUnitDaoBaseWithTrans;

/**
 * @Description 
 */
public class ${className}DaoTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(${className}DaoTest.class);
	@Resource
	private ${className}Dao ${classNameLower}Dao;
	
	@Test
	public void testSave${className} (){
		${classEntity} ${classNameLower} = new ${classEntity}();
		int id = ${classNameLower}Dao.save${className}(${classNameLower});
		Assert.assertTrue(id>0);
	}
	@Test
	public void testBatchInsert${className}s (Map<String, Object> maps){
		
	}
	@Test
	public void testUpdate${className} (${classEntity} ${classNameLower}){
		
	}
	@Test
	public void testBatchUpdate${className}sToDiffVals (Map<String, Object> maps){
		
	}
	@Test
	public void testBatchUpdate${className}sToSameVals (Map<String, Object> maps){
		
	}
	@Test
	public void testDelete${className}ById (Integer id){
		
	}
	@Test
	public void testBatchDelete${className}ByIds (List<Integer> ids){
		
	}
	@Test
	public void testGet${className}ById(Integer id){
		
	}
	@Test
	public void testGet${className}s (${classEntity} ${classNameLower}){
		
	}
	@Test
	public void testGet${className}sMap (${classEntity} ${classNameLower}){
		
	}
	
}
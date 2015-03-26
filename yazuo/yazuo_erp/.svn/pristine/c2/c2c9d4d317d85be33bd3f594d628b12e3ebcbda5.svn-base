/**
* Created with MyElipse.<br/>
* User: huijun.zheng<br/>
* Date: 2014-3-20<br/>
* Time: 下午2:59:59<br/>
*
* To change this template use File | Settings | File Templates.<br/>
*/
package com.yazuo.erp.base;

import java.util.List;

/**
 * 生成SQL的声明
 * Created with MyElipse.<br/>
 * User: huijun.zheng<br/>
 * Date: 2014-3-20<br/>
 * Time: 下午2:59:59<br/>
 *<br/>
 */
public interface SQLAction
{
	/**
	 * 
	 * 生成单表，插入的SQL.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午4:21:43<br/>
	 *
	 * @param tableName 数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param model 数据model对象
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateInsertSql(String tableName,List<Object> sqlValues,Object model) throws Exception;
	
	/**
	 * 
	 * 生成单表,批量插入的SQL.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午4:21:43<br/>
	 *
	 * @param tableName 数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param model 数据model对象
	 * @return
	 * @throws Exception<br/>
	 */
	public <T> String generateInsertBatchSql(String tableName,List<List<Object>> sqlValues,List<T> model) throws Exception;
	
	/**
	 * 
	 * 生成按Id去更新的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午5:05:34<br/>
	 *
	 * @param tableName 数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值更新，true:更新空值，false,不更新空值
	 * @param model 数据model对象
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateUpdateByIdSql(String tableName,List<Object> sqlValues,Boolean isNullAsUpdatedConditions,Object model) throws Exception;
	
	/**
	 * 
	 * 生成按Id去删除的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午5:12:22<br/>
	 *
	 * @param tableName 数据库表的名字
	 * @param model 数据model对象
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateDeleteByIdSql(String tableName,Object model) throws Exception;
	
	/**
	 * 
	 * 生成查询的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午5:48:11<br/>
	 *
	 * @param tableName   数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值作为查询条件，true:将空值作为查询条件，false,不将空值作为查询条件
	 * @param model 数据model对象
	 * @param dateFormat 日期格式如，yyyy-MM-dd (该值为默认值，如果未指定格式的话）
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateSelectSql(String tableName, List<Object> sqlValues,Boolean isNullAsSelectedConditions, Object model,String dateFormat)  throws Exception;
	
	/**
	 * 
	 * 生成获取count的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午4:28:26<br/>
	 *
	 * @param tableName   数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值作为查询条件，true:将空值作为查询条件，false,不将空值作为查询条件
	 * @param model 数据model对象
	 * @param dateFormat 日期格式如，yyyy-MM-dd (该值为默认值，如果未指定格式的话）
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateSelectCountSql(String tableName, List<Object> sqlValues,Boolean isNullAsSelectedConditions, Object model,String dateFormat)  throws Exception;
	
	/**
	 * 
	 *获取分页的SQL语句.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午8:46:35<br/>
	 *
	 * @param tableName   数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值作为查询条件，true:将空值作为查询条件，false,不将空值作为查询条件
	 * @param model 数据model对象
	 * @param dateFormat 日期格式如，yyyy-MM-dd (该值为默认值，如果未指定格式的话）
	 * @param pageModel 分页所需的参数
	 * @return
	 * @throws Exception<br/>
	 */
	public String generateSelectPageSql(String tableName, List<Object> sqlValues,Boolean isNullAsSelectedConditions, Object model,String dateFormat,PageModel pageModel)  throws Exception;
}

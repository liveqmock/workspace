/**
* Created with MyElipse.<br/>
* User: huijun.zheng<br/>
* Date: 2014-3-19<br/>
* Time: 下午7:24:52<br/>
*
* To change this template use File | Settings | File Templates.<br/>
*/
package com.yazuo.erp.base;

import java.util.List;


/**
 * 针对 Spring JDBC 进行的简单的封装一层
 * Created with MyElipse.<br/>
 * User: huijun.zheng<br/>
 * Date: 2014-3-19<br/>
 * Time: 下午7:24:52<br/>
 *<br/>
 */
public interface BaseDAO
{
	/**
	 * 
	 *请参看 {@link BaseDAO#insert(Object model,String tableName) insert}.<br/>
	 *
	 */
	public <T> Object insert(T model) throws Exception;
	/**
	 * 
	 *插入对象，并返回主键id值<br/>
	 *		<b>注意：</b><br/>
	 *			属性为null值或者未空的不会插入数据库<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:41:31<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @return 主键id值
	 * @throws Exception<br/>
	 */
	public <T> Object insert(T model,String tableName) throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#insertBatch(final List<Object> model,String tableName) insertBatch}.<br/>
	 *
	 */
	public <T> int[] insertBatch(final List<T> model) throws Exception;
	
	/**
	 * 
	 *批量掺入对象,返回插入数据库记录的条数.<br/>
	 *	<b>注意：</b><br/>
	 *		属性为null值或者未空的不会插入数据库<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:42:43<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @return 插入数据库记录的条数
	 * @throws Exception<br/>
	 */
	public <T> int[] insertBatch(final List<T> model,String tableName) throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#delete(Object model,String tableName) delete}.<br/>
	 *
	 */
	public <T> int delete(T model) throws Exception;
	/**
	 * 
	 *删除对象.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:40:38<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @return the number of rows affected
	 * @throws Exception<br/>
	 */
	public <T> int delete(T model,String tableName) throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#update(Object model,String tableName,Boolean isNullAsUpdatedConditions) update}.<br/>
	 *
	 */
	public <T> int update(T model) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#update(Object model,String tableName,Boolean isNullAsUpdatedConditions) update}.<br/>
	 *
	 */
	public <T> int update(T model,String tableName)throws Exception;
	/**
	 * 
	 *更新对象.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:39:19<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @param isNullAsUpdatedConditions 是否将null的属性,做为更新的属性
	 * @return	the number of rows affected
	 * @throws Exception<br/>
	 */
	public <T> int update(T model,String tableName,Boolean isNullAsUpdatedConditions)throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#getModel(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModel}.<br/>
	 *
	 */
	public <T> T getModel(T model) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModel(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModel}.<br/>
	 *
	 */
	public <T> T getModel(T model,String tableName) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModel(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModel}.<br/>
	 *
	 */
	public <T> T getModel(T model,String tableName,String dateFormat) throws Exception;
	/**
	 * 
	 *获取单个的查询.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:37:14<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @param dateFormat 查询日期的格式默认 yyyy-MM-dd
	 * @param isNullAsSelectedConditions 是否将null的属性做为查询条件
	 * @return
	 * @throws Exception<br/>
	 */
	public <T> T getModel(T model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelList(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelList}.<br/>
	 *
	 */
	public <T> List<T> getModelList(T model) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelList(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelList}.<br/>
	 *
	 */
	public <T> List<T> getModelList(T model,String tableName) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelList(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelList}.<br/>
	 *
	 */
	public <T> List<T> getModelList(T model,String tableName,String dateFormat) throws Exception;
	/**
	 * 
	 *返回查询列表.<br/>
	 *
	 *	<b>注意：</b><br/>
	 *		1、在准换为具体的对象时，应转换为List<具体对象>类型<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-21<br/>
	 * Time: 下午8:01:39<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @param dateFormat 查询日期的格式默认 yyyy-MM-dd
	 * @param isNullAsSelectedConditions 是否将null的属性做为查询条件
	 * @return
	 * @throws Exception<br/>
	 */
	public <T> List<T> getModelList(T model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) throws Exception;
	
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelCount(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelCount}.<br/>
	 *
	 */
	public <T> Object getModelCount(T model) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelCount(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelCount}.<br/>
	 *
	 */
	public <T> Object getModelCount(T model,String tableName) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelCount(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) getModelCount}.<br/>
	 *
	 */
	public <T> Object getModelCount(T model,String tableName,String dateFormat) throws Exception;
	/**
	 * 
	 *获得查询的记录个数.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午1:36:16<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @param dateFormat 查询日期的格式默认 yyyy-MM-dd
	 * @param isNullAsSelectedConditions 是否将null的属性做为查询条件
	 * @return
	 * @throws Exception<br/>
	 */
	public <T> Object getModelCount(T model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions) throws Exception;
	
	
	
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelPage(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions,PageModel pageModel) getModelPage}.<br/>
	 *
	 */
	public <T>  Pagination<T>  getModelPage(T model,PageModel pageModel) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelPage(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions,PageModel pageModel) getModelPage}.<br/>
	 *
	 */
	public <T>  Pagination<T>  getModelPage(T model,String tableName,PageModel pageModel) throws Exception;
	/**
	 * 
	 *请参看 {@link BaseDAO#getModelPage(Object model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions,PageModel pageModel) getModelPage}.<br/>
	 *
	 */
	public <T>  Pagination<T>  getModelPage(T model,String tableName,String dateFormat,PageModel pageModel) throws Exception;
	
	/**
	 * 
	 *获得分页列表.<br/>
	 **	<b>注意：</b><br/>
	 *		1、在准换为具体的对象时，应转换为List<具体对象>类型<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-25<br/>
	 * Time: 下午8:40:04<br/>
	 *
	 * @param model 数据model对象
	 * @param tableName 数据库表的名字
	 * @param dateFormat 查询日期的格式默认 yyyy-MM-dd
	 * @param isNullAsSelectedConditions 是否将null的属性做为查询条件
	 * @param pageModel 分页所需的参数 
	 * @return
	 * @throws Exception<br/>
	 */
	public <T> Pagination<T> getModelPage(T model,String tableName,String dateFormat,Boolean isNullAsSelectedConditions,PageModel pageModel) throws Exception;
	
}

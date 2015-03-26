/**
* Created with MyElipse.<br/>
* User: huijun.zheng<br/>
* Date: 2014-3-20<br/>
* Time: 下午3:19:55<br/>
*
* To change this template use File | Settings | File Templates.<br/>
*/
package com.yazuo.erp.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.yazuo.util.RefectUtils;
import com.yazuo.util.StringUtils;

/**
 * 默认生成SQL语句的实现类
 * Created with MyElipse.<br/>
 * User: huijun.zheng<br/>
 * Date: 2014-3-20<br/>
 * Time: 下午3:19:55<br/>
 *<br/>
 */
@Component("defaultSQLAction")
public class DefaultSQLAction implements SQLAction
{
	private static final Log LOG = LogFactory.getLog(DefaultSQLAction.class);
	
	/**
	 * 数据库表的主键名称，为id
	 */
	protected static final String DB_PM_NAME = "id";
	
	protected static final String SCHEMA_NAME = "superreport.";

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
	@Override
	public String generateInsertSql(String tableName, List<Object> sqlValues, Object model) throws Exception
	{
		StringBuilder sb = new StringBuilder("INSERT INTO  "+SCHEMA_NAME+tableName+" (");
		
		fillingInsertSQLValue(sb,sqlValues,model);
		
		int length = sqlValues.size();
		
		sb.append(" VALUES(");
		for(int i=0;i<length;++i)
		{
			if(i == length-1)
			{
				sb.append("?)");
			}
			else
			{
				sb.append("?,");
			}
		}
		
		String sql = sb.toString();
		
		//判断VALUES前面是否有“）”
		int len = sql.indexOf("(");
		int len2 = sql.indexOf("VALUES");
		String comma = sql.substring(len+1,len2);
		if(-1 == comma.indexOf(")"))
		{
			String temp = sql.substring(0, len2);
			int len3 = temp.lastIndexOf(",");
			temp = temp.substring(0,len3);
			
			String temp2 = sql.substring(len2);
			sql = temp +"  )  "+ temp2;
		}
		
		LOG.info(sql);
		
		return  sql;
	}
	
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
	@Override
	public <T> String generateInsertBatchSql(String tableName, List<List<Object>> sqlValues, List<T> model) throws Exception
	{
		StringBuilder sb = new StringBuilder("INSERT INTO  "+SCHEMA_NAME+tableName+" (");
		
		fillingInsertSQLValues(sb,sqlValues,model);
		
		int length = sqlValues.get(0).size();
		
		sb.append(" VALUES(");
		for(int i=0;i<length;++i)
		{
			if(i == length-1)
			{
				sb.append("?)");
			}
			else
			{
				sb.append("?,");
			}
		}
		
		String sql = sb.toString();
		
		//判断VALUES前面是否有“）”
		int len = sql.indexOf("(");
		int len2 = sql.indexOf("VALUES");
		String comma = sql.substring(len+1,len2);
		if(-1 == comma.indexOf(")"))
		{
			String temp = sql.substring(0, len2);
			int len3 = temp.lastIndexOf(",");
			temp = temp.substring(0,len3);
			
			String temp2 = sql.substring(len2);
			sql = temp +"  )  "+ temp2;
		}
		
		LOG.info(sql);
		
		return  sql;
	}

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
	@Override
	public String generateUpdateByIdSql(String tableName, List<Object> sqlValues, Boolean isNullAsUpdatedConditions,Object model) throws Exception
	{
		StringBuilder sb = new StringBuilder("UPDATE  " + SCHEMA_NAME + tableName + "  SET " + DB_PM_NAME + "="+ DB_PM_NAME + ",");
		
		fillingUpdateSQLValue(sb, sqlValues, isNullAsUpdatedConditions, model);

		String sql = sb.toString();
		
		//对最后一个?与where之间存在“,”的话，将其去掉
		int len = sql.lastIndexOf("?");
		int len2 = sql.indexOf("where");
		String comma = sql.substring(len+1,len2);
		if(-1 != comma.indexOf(","))
		{
			String temp = sql.substring(0, len+1);
			String temp2 = sql.substring(len2);
			sql = temp + " " +temp2;
		}
		
		LOG.info(sql);
		return sql;
	}

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
	@Override
	public String generateDeleteByIdSql(String tableName,Object model) throws Exception
	{
		StringBuilder sb = new StringBuilder("delete from " + SCHEMA_NAME + tableName + " where " + DB_PM_NAME + " = ");
		
		sb.append(RefectUtils.getFieldValue(DB_PM_NAME, model.getClass(),model));
		
		String sql = sb.toString();
		
		LOG.info(sql);
		
		return sql;
	}
	
	/**
	 * 
	 * 生成查询的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午5:48:11<br/>
	 *
	 * @param tableName   数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值作为查询条件，true:更新空值，false,不更新空值
	 * @param model 数据model对象
	 * @param dateFormat 日期格式如，yyyy-MM-dd (该值为默认值，如果未指定格式的话）
	 * @return
	 * @throws Exception<br/>
	 */
	@Override
	public String generateSelectSql(String tableName, List<Object> sqlValues,Boolean isNullAsUpdatedConditions, Object model,String dateFormat) throws Exception
	{
		StringBuilder sb = new StringBuilder("select * from "+SCHEMA_NAME+ tableName+" t where 1=1 ");
		
		fillingSelectSQLValue(sb, tableName, sqlValues, isNullAsUpdatedConditions, model, dateFormat);
		
		//默认按ID的倒序进行排序
		sb.append(" ORDER BY t ."+DB_PM_NAME+" DESC ");
		
		String sql = sb.toString();
		
		LOG.info(sql);
		
		return sql;
	}
	
	/**
	 * 
	 * 生成获取count的SQL语句.<br/>
	 * User: huijun.zheng<br/>
	 * Date: 2014-3-20<br/>
	 * Time: 下午4:28:26<br/>
	 *
	 * @param tableName   数据库表的名字
	 * @param sqlValues 要填充的SQL值
	 * @param isNullAsUpdatedConditions 是否将空值作为查询条件，true:更新空值，false,不更新空值
	 * @param model 数据model对象
	 * @param dateFormat 日期格式如，yyyy-MM-dd (该值为默认值，如果未指定格式的话）
	 * @return
	 * @throws Exception<br/>
	 */
	@Override
	public String generateSelectCountSql(String tableName, List<Object> sqlValues,Boolean isNullAsUpdatedConditions, Object model,String dateFormat) throws Exception
	{
		StringBuilder sb = new StringBuilder("select count(*) from "+SCHEMA_NAME+ tableName+" t where 1=1 ");
		fillingSelectSQLValue(sb, tableName, sqlValues, isNullAsUpdatedConditions, model, dateFormat);
		String sql = sb.toString();
		LOG.info(sql);
		
		return sql;
	}
	
	private void fillingInsertSQLValue(StringBuilder sb ,List<Object> sqlValues, Object model) throws Exception
	{
		if(null == sqlValues) sqlValues = new ArrayList<Object>();
		
		Class<?> clazz = model.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(int i=0,len=fields.length;i<len;++i)
		{
			//获得属性的名称
			String fieldName = fields[i].getName();
			
			//如果没有get方法的，不做处理
			if(null == RefectUtils.getGetterMethod(fieldName, clazz)) continue;
			
			fields[i].setAccessible(true);
			//获得属性值
			Object fieldValue = fields[i].get(model);
			
			//如果属性值为null,不做处理
			if(null == fieldValue) continue;
			
			//如果是日期类型格式化为sql的Timestamp日期类型
			if(fieldValue instanceof Date)
			{
				Date fvDate = (Date)fieldValue;
				sqlValues.add(new java.sql.Timestamp(fvDate.getTime()));
			}
			else
			{
				sqlValues.add(fieldValue);
			}
			
			//SQL字段
			//对javabean中驼峰式写法转换为下划线的写法
			fieldName = StringUtils.toUnderlineString(fieldName);
			if(i==len-1)
			{
				sb.append(fieldName+")");
			}
			else
			{
				sb.append(fieldName+",");
			}
		}
	}
	
	
	private <T> void fillingInsertSQLValues(StringBuilder sb ,List<List<Object>> sqlValues, List<T> models) throws Exception
	{
		if(null == sqlValues) sqlValues = new ArrayList<List<Object>>();
		
		if(null == models || 0 == models.size())
		{
			throw new IllegalArgumentException("您想要执行批量插入操作，但是你出入的数据为空，请检查");
		}
		
		/**
		 * 下面的做法效率不是很高，等有时间后去重构
		 */
		for(int j=0,leng=models.size();j<leng;++j)
		{
			List<Object> vals = new ArrayList<Object>();
			
			sqlValues.add(vals);
			
			Object model = models.get(j);
			
			Class<?> clazz = model.getClass();
			
			Field[] fields = clazz.getDeclaredFields();
			
			for(int i=0,len=fields.length;i<len;++i)
			{
				//获得属性的名称
				String fieldName = fields[i].getName();
				
				//如果没有get方法的，不做处理
				if(null == RefectUtils.getGetterMethod(fieldName, clazz)) continue;
				
				fields[i].setAccessible(true);
				//获得属性值
				Object fieldValue = fields[i].get(model);
				
				//如果属性值为null,不做处理
				if(null == fieldValue) continue;
				
				//如果是日期类型格式化为sql的Timestamp日期类型
				if(fieldValue instanceof Date)
				{
					Date fvDate = (Date)fieldValue;
					vals.add(new java.sql.Timestamp(fvDate.getTime()));
				}
				else
				{
					vals.add(fieldValue);
				}
				
				if(0 ==j)
				{
					//SQL字段
					//对javabean中驼峰式写法转换为下划线的写法
					fieldName = StringUtils.toUnderlineString(fieldName);
					if(i==len-1)
					{
						sb.append(fieldName+")");
					}
					else
					{
						sb.append(fieldName+",");
					}
				}
			}
		}
	}
	
	private void fillingUpdateSQLValue(StringBuilder sb ,List<Object> sqlValues,Boolean isNullAsUpdatedConditions, Object model) throws Exception
	{
		if(null == sqlValues) sqlValues = new ArrayList<Object>();
		
		Class<?> clazz = model.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(int i=0,len=fields.length;i<len;++i)
		{
			//获得属性的名称
			String fieldName = fields[i].getName();
			
			// 对主键为id对应的值，不进行数据库的更新操作
			if (DB_PM_NAME.equalsIgnoreCase(fieldName)) continue;
			
			//如果没有get方法的，不做处理
			if(null == RefectUtils.getGetterMethod(fieldName, clazz)) continue;
			
			fields[i].setAccessible(true);
			//获得属性值
			Object fieldValue = fields[i].get(model);
			
			//如果更新时，排除空值，也就是说空值不进行更新
			if(true != isNullAsUpdatedConditions)
			{
				//如果属性值为null,不做处理
				if(null == fieldValue) continue;
			}
			
			//如果是日期类型格式化为sql的Timestamp日期类型
			if(fieldValue instanceof Date)
			{
				Date fvDate = (Date)fieldValue;
				sqlValues.add(new java.sql.Timestamp(fvDate.getTime()));
			}
			else
			{
				sqlValues.add(fieldValue);
			}
			
			//SQL字段
			//对javabean中驼峰式写法转换为下划线的写法
			fieldName = StringUtils.toUnderlineString(fieldName);
			if (i == len - 1)
			{
				sb.append(fieldName + " = ?");
			}
			else
			{
				sb.append(fieldName + " = ?,");
			}
		}
		
		sb.append(" where "+DB_PM_NAME+"="+RefectUtils.getFieldValue(DB_PM_NAME, clazz,model));
	}
	
	private void fillingSelectSQLValue(StringBuilder sb,String tableName, List<Object> sqlValues,Boolean isNullAsUpdatedConditions, Object model,String dateFormat) throws Exception
	{
		Class<?> clazz = model.getClass();
		
		Field[] fields = clazz.getDeclaredFields();
		if(null == fields || 0 == fields.length) 
				throw new IllegalArgumentException(clazz.getName() +" has no property");
		
		for(int i=0;i<fields.length;++i)
		{
			String fieldName = fields[i].getName();
			
			//如果没有get方法的，不做处理
			if(null == RefectUtils.getGetterMethod(fieldName, clazz)) continue;
			
			fields[i].setAccessible(true);
			//获取属性值
			Object fieldValue = fields[i].get(model);
			
			if(true != isNullAsUpdatedConditions)
			{
				//如果属性值为null,不做处理
				if(null == fieldValue) continue;
			}
			
			//如果是String类型，并且为空的字符串，不做处理
//			if(null != fieldValue && fieldValue instanceof String && "".equals(fieldValue)) continue;
			
			//如果是日期类型格式化为短日期类型也就是yyy-MM-dd
			if(null != fieldValue && fieldValue instanceof Date)
			{
				if(null == dateFormat)
					dateFormat = "yyyy-MM-dd";
				
				sb.append(" and ");
				sb.append(" to_char( ");
				sb.append(" t. ").append(StringUtils.toUnderlineString(fieldName));
				sb.append(" , '"+dateFormat+"') = ? ");
				continue;
			}
			
			sb.append(" and ");
			sb.append(" t.").append(StringUtils.toUnderlineString(fieldName)).append(" =? ");
			
			sqlValues.add(fieldValue);
		}
	}
	
	@Override
	public String generateSelectPageSql(String tableName, List<Object> sqlValues, Boolean isNullAsSelectedConditions,
			Object model, String dateFormat, PageModel pageModel) throws Exception
	{
		StringBuilder sb = new StringBuilder("select * from "+SCHEMA_NAME+ tableName+" t where 1=1 ");
		fillingSelectSQLValue(sb, tableName, sqlValues, isNullAsSelectedConditions, model, dateFormat);
		
		//排序的字段
		String sort = pageModel.getSort();
		if(null != sort && !"".equals(sort))
		{
			sb.append(" ORDER BY t." +sort + " " + pageModel.getOrder());
		}
		else //默认按ID的倒序进行排序
		{
			sb.append(" ORDER BY t ."+DB_PM_NAME+" DESC ");
		}
		
		//分页的规则
		sb.append(" LIMIT "+pageModel.getRows());//每页显示多少条记录
		sb.append(" OFFSET "+(pageModel.getPage()-1)*pageModel.getRows());//从第多少条记录开始
		
		return sb.toString();
	}
}

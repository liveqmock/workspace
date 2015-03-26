/**
* Created with MyElipse.<br/>
* User: huijun.zheng<br/>
* Date: 2014-3-19<br/>
* Time: 下午7:25:26<br/>
*
* To change this template use File | Settings | File Templates.<br/>
*/
package com.yazuo.erp.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.yazuo.util.RefectUtils;

/**
 * DAO的类，直接继承该类就可以使用，
 * 该类是对Spring JDBC CRUD 操作，进行了简单的封装<br/>
 * 
 * Created with MyElipse.<br/>
 * User: huijun.zheng<br/>
 * Date: 2014-3-19<br/>
 * Time: 下午7:25:26<br/>
 *<br/>
 */
public abstract class AbstractCrmBaseDAO implements BaseDAO
{
	@Resource(name = "jdbcTemplateCrm")
	protected JdbcTemplate jdbcTemplate;
	
	@Resource(name = "defaultSQLAction")
	protected SQLAction sqlAction;
	
	protected static final String SCHEMA_NAME = "public.";
	/**
	 * 数据库表的主键，为id
	 */
	protected static final String DB_PK_NAME = "id";
	
	/**
	 * 短日期类的格式
	 */
	protected static final String DATA_FORMAT_SHORT = "yyyy-MM-dd";
	
	/**
	 * 长日期类的格式
	 */
	protected static final String DATA_FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 带毫秒的日期类的格式
	 */
	protected static final String DATA_FORMAT_LONG_MILLIS = "yyyy-MM-dd HH:mm:ss S";

	@Override
	public <T> Object insert(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		
		return insert(model,tableName);
	}

	@Override
	public <T> Object insert(T model, String tableName) throws Exception
	{
		final List<Object> sqlValues = new ArrayList<Object>();
		
		final String sql = sqlAction.generateInsertSql(tableName, sqlValues, model);
		
		KeyHolder key = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				for (int m = 0, len = sqlValues.size(); m < len; ++m)
				{
					Object o = sqlValues.get(m);
					ps.setObject(m + 1, o);
				}
				return ps;
			}
		}, key);
		
		return (Integer)key.getKeys().get(DB_PK_NAME);
	}

	@Override
	public <T> int[] insertBatch(List<T> model) throws Exception
	{
		if(null != model && 0 != model.size())
		{
			T t = model.get(0);
			String tableName = RefectUtils.getClassName(t.getClass(),true);
			return insertBatch(model,tableName);
		}
		return null;
	}

	@Override
	public <T> int[] insertBatch(List<T> model, String tableName) throws Exception
	{
		final List<List<Object>> sqlValues = new ArrayList<List<Object>>();
		
		final String sql = sqlAction.generateInsertBatchSql(tableName, sqlValues, model);
		
		return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
		{
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException
			{
				List<Object> list = sqlValues.get(i);
				
				for(int j=0,len = list.size();j<len;++j)
				{
					 ps.setObject(j+1, list.get(j));
				}
			}
			
			@Override
			public int getBatchSize()
			{
				return sqlValues.size();
			}
		});
	}

	@Override
	public <T> int delete(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return delete(model, tableName);
	}

	@Override
	public <T> int delete(T model, String tableName) throws Exception
	{
		String sql = sqlAction.generateDeleteByIdSql(tableName, model);
		return jdbcTemplate.update(sql);
	}
	
	

	@Override
	public <T> int update(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return update(model, tableName, false);
	}

	@Override
	public <T> int update(T model, String tableName) throws Exception
	{
		return update(model, tableName, false);
	}

	@Override
	public <T> int update(T model, String tableName, Boolean isNullAsUpdatedConditions) throws Exception
	{
		List<Object> sqlValues = new ArrayList<Object>();
		String sql = sqlAction.generateUpdateByIdSql(tableName, sqlValues, isNullAsUpdatedConditions, model);
		return jdbcTemplate.update(sql, sqlValues.toArray());
	}

	
	@Override
	public <T> T getModel(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return getModel(model, tableName);
	}

	@Override
	public <T> T getModel(T model, String tableName) throws Exception
	{
		return getModel(model, tableName, DATA_FORMAT_SHORT, false);
	}

	@Override
	public <T> T getModel(T model, String tableName, String dateFormat) throws Exception
	{
		return getModel(model, tableName, dateFormat, false);
	}
	
	@Override
	public <T> T getModel(T model, String tableName, String dateFormat, Boolean isNullAsSelectedConditions)
			throws Exception
	{
//		List<Object> sqlValues = new ArrayList<Object>();
//		String sql = sqlAction.generateSelectSql(tableName, sqlValues, isNullAsSelectedConditions, model,dateFormat);
//		return jdbcTemplate.queryForObject(sql, sqlValues.toArray(), model.getClass());
		
		List<T> list = getModelList(model, tableName, dateFormat, isNullAsSelectedConditions);
		if(null != list && 0 != list.size())
		{
			return list.get(0);
		}
		return null;
	}
	
	
	
	@Override
	public <T> List<T> getModelList(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return getModelList(model, tableName, DATA_FORMAT_SHORT, false);
	}

	@Override
	public <T> List<T> getModelList(T model, String tableName) throws Exception
	{
		return getModelList(model, tableName, DATA_FORMAT_SHORT, false);
	}
	
	@Override
	public <T> List<T> getModelList(T model, String tableName, String dateFormat) throws Exception
	{
		return getModelList(model, tableName, dateFormat, false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getModelList(T model, String tableName, String dateFormat, Boolean isNullAsSelectedConditions)
			throws Exception
	{
		List<Object> sqlValues = new ArrayList<Object>();
		String sql = sqlAction.generateSelectSql(tableName, sqlValues, isNullAsSelectedConditions, model,dateFormat);
		
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<T>((Class<T>) model.getClass()),sqlValues.toArray());
	}
	
	

	@Override
	public <T> Object getModelCount(T model) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return getModelCount(model, tableName, DATA_FORMAT_SHORT, false);
	}

	@Override
	public <T> Object getModelCount(T model, String tableName) throws Exception
	{
		return getModelCount(model, tableName, DATA_FORMAT_SHORT, false);
	}
	
	@Override
	public <T> Object getModelCount(T model, String tableName, String dateFormat) throws Exception
	{
		return getModelCount(model, tableName, dateFormat, false);
	}
	
	@Override
	public <T> Object getModelCount(T model, String tableName, String dateFormat, Boolean isNullAsSelectedConditions)
			throws Exception
	{
		List<Object> sqlValues = new ArrayList<Object>();
		String sql = sqlAction.generateSelectCountSql(tableName, sqlValues, isNullAsSelectedConditions, model,dateFormat);
		
//		return jdbcTemplate.queryForInt(sql, sqlValues.toArray());
		return jdbcTemplate.queryForLong(sql, sqlValues.toArray());
	}

	@Override
	public <T> Pagination<T> getModelPage(T model, PageModel pageModel) throws Exception
	{
		String tableName = RefectUtils.getClassName(model.getClass(),true);
		return getModelPage(model, tableName, DATA_FORMAT_SHORT, false,pageModel);
	}

	@Override
	public <T> Pagination<T> getModelPage(T model, String tableName, PageModel pageModel) throws Exception
	{
		return getModelPage(model, tableName, DATA_FORMAT_SHORT, false,pageModel);
	}

	@Override
	public <T> Pagination<T> getModelPage(T model, String tableName, String dateFormat, PageModel pageModel) throws Exception
	{
		return getModelPage(model, tableName, dateFormat, false,pageModel);
	}

	@Override
	public <T> Pagination<T> getModelPage(T model, String tableName, String dateFormat, Boolean isNullAsSelectedConditions, PageModel pageModel) throws Exception
	{
		Pagination<T> p = new Pagination<T>();
		
		//获取总的记录数
//		Integer counts = (Integer)getModelCount(model);
		Long counts = (Long)getModelCount(model);
		
		
		List<Object> sqlValues = new ArrayList<Object>();
		String sql = sqlAction.generateSelectPageSql(tableName, sqlValues, isNullAsSelectedConditions, model,dateFormat,pageModel);
		
		//每页的详细信息
		@SuppressWarnings("unchecked")
		List<T> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<T>((Class<T>) model.getClass()),sqlValues.toArray());
		
		p.setTotal(counts);
		p.setRows(list);
		
		return p;
	};
	
}

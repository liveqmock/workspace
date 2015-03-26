package com.yazuo.erp.project.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.AbstractCrmBaseDAO;
import com.yazuo.erp.base.Pagination;
import com.yazuo.erp.project.dao.ActiveDao;
import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Label;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;

@Repository
public class ActivelDaoImpl extends AbstractCrmBaseDAO implements ActiveDao{
	 Log logger = LogFactory.getLog(this.getClass());
	 @Resource(name = "jdbcTemplateCrm")
	 private JdbcTemplate jdbcTemplate;
	 /**
	  * 用分页的方式返回一组包含案例库的活动
	  */
	 public Map<String, Object> getActiveMap(Active active, int page, int pageSize){
	  StringBuffer sql = new StringBuffer( "SELECT a.active_id, a.active_name, a.active_type,a.merchant_id," +
	  		"a.active_begin,a.active_end, t.type_name,m.merchant_name from public.active as a " +
	  		"LEFT OUTER JOIN public.active_type as t on a.active_type = t.type_id " +
	  		"LEFT OUTER JOIN trade.merchant AS m ON A.merchant_id = m.merchant_id where 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(active != null){
			if(active.getActiveId() != null){
				sql.append(" and a.active_id = ? ");
				list.add(active.getActiveId());
			}
			if(active.getActiveName() != null){
				sql.append(" and a.active_name  like '%"+active.getActiveName()+"%'");
//				list.add(active.getActiveName());
			}
		}
		sql.append(" order by a.create_time desc ");
		try {
			Pagination<Active> pagination = new Pagination<Active>(sql.toString()
			 , page, pageSize,jdbcTemplate, new DAORowMapper<Active>(Active.class),list.toArray());
			return pagination.getResultMap();
			} catch (Exception e) {
				LogUtils.error("!", logger, e,sql.toString(),active);
			}
			return null;
	 }
	 /**
	  * 根据活动对象查询所有的活动
	  */
	 public List<Map<String, Object>> getAllActives(Active active){
		 StringBuffer sql = new StringBuffer( "SELECT a.active_id, a.active_name, a.active_type,a.merchant_id,a.active_begin,a.active_end, t.type_name " +
				 "from public.active as a LEFT OUTER JOIN public.active_type as t on a.active_type = t.type_id where 1=1 ");
		 try {
			return jdbcTemplate.queryForList(sql.toString());
		 } catch (Exception e) {
			 LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(active));
			 return null;
		 }
	 }
	 /**
	  * 查询所有活动类型
	  */
	 public List<Map<String, Object>> getAllActiveTypes(){
		 StringBuffer sql = new StringBuffer( "SELECT type_id, type_name from active_type where 1=1 ");
		 try {
			 return jdbcTemplate.queryForList(sql.toString());
		 } catch (Exception e) {
			 LogUtils.error("!", logger, e,sql.toString());
			 return null;
		 }
	 }
	 @Override
		public List<Active> selectActiveListByObject(Active active){
			StringBuffer sql = new StringBuffer("SELECT  a.active_id,a.active_begin, a.active_end, a.active_name, " +
					"a.merchant_id, m.merchant_name, a.active_type from active as a " +
					"left outer join trade.merchant as m on a.merchant_id = m.merchant_id");
			sql.append("  WHERE 1=1 ");
			List<Object> list = new ArrayList<Object>();
			if(active != null){
				if(active.getActiveId() != null){
					sql.append(" AND a.active_id = ? ");
					list.add(active.getActiveId());
				}
				java.util.Date activeBegin = active.getActiveBegin();
				java.util.Date activeEnd = active.getActiveEnd();
				if(activeBegin!=null){
					sql.append(" AND a.active_begin>= ? ");
					list.add(activeBegin);
				}
				if(activeEnd!=null){
					sql.append(" AND a.active_end<= ? ");
					list.add(activeEnd);
				}
				if(active.getMerchantId() != null){
					sql.append(" AND a.merchant_id = ? ");
					list.add(active.getMerchantId());
				}
				if(active.getActiveType() != null){
					sql.append(" AND a.active_type = ? ");
					list.add(active.getActiveType());
				}
				//页面输入 活动名 商户名是或的关系,active.getActiveName()为 页面输入的值
				if(active.getActiveName() != null && !"".equals(active.getActiveName())){
					sql.append(" AND (a.active_name like '%"+active.getActiveName()+"%'");
					sql.append(" or m.merchant_name like '%"+active.getMerchantName()+"%')");
				}
			}
			try {
			return jdbcTemplate.query(sql.toString(), new DAORowMapper<Active>(Active.class),list.toArray());
			} catch (Exception e) {
				LogUtils.error("!", logger, e,sql.toString(),active);
			}
			return null;
		}
}

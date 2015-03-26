package com.yazuo.erp.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.Pagination;
import com.yazuo.erp.project.dao.ProjectDao;
import com.yazuo.erp.project.vo.Active;
import com.yazuo.erp.project.vo.Project;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;
import com.yazuo.util.StringUtils;

@Repository
public class ProjectDaoImpl implements ProjectDao{

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	 Log logger = LogFactory.getLog(this.getClass());
	 
	 /**
	  * 查询所有满足条件的数据字典
	  * @keys 传的是一个由Constant.label_sperater分割的字符串
	  */
	 @Override
	 public List<Map<String, Object>> selectDictionarysByKeys(String dictionaryType, String keys){
		String[] strKeys = new String[] { keys };
		String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keys);
        if (m.find()) {
        	keys = keys.replaceAll("[^0-9]", Constant.logic_sperater);
        	strKeys = keys.split(Constant.logic_sperater);
        }
		 StringBuffer sql = new StringBuffer("SELECT * FROM erp_dictionary WHERE erp_dictionary.dictionary_type='"+dictionaryType+"'");
		 if(keys!=null&&keys.length()>0){
			 StringBuffer stringBuffer = new StringBuffer("(");
			 for (String key : strKeys) {
				 if(StringUtils.isEmpty(key))continue;
				 stringBuffer.append("'"+key+"'");
				 stringBuffer.append(",");
			 }
			 stringBuffer.deleteCharAt(stringBuffer.length()-1);
			 stringBuffer.append(")");
			 sql.append(" AND  erp_dictionary.dictionary_key in "+stringBuffer.toString());
		 }
		 return jdbcTemplate.queryForList(sql.toString());
	 }
	 /**
	  * 用jdbc的方式查询所有满足条件的数据字典
	  */
	 @Override
	 public List<Map<String, Object>> selectDictionarys(String dictionaryType, String key){
		StringBuffer sql = new StringBuffer("SELECT * FROM erp_dictionary WHERE erp_dictionary.dictionary_type='"+dictionaryType+"'");
		if(key != null){
			sql.append(" AND  erp_dictionary.dictionary_key= '"+key+"'");
		}
		return jdbcTemplate.queryForList(sql.toString());
	 }
	@Override
	public Project selectProjectByID(Project project){
		StringBuffer sql = new StringBuffer("SELECT project_id,project_name,project_comments,active_id,active_type_id,active_img_path,insert_time");
		sql.append(" FROM public.project WHERE 1=1 ");
		sql.append(" AND project_id  =? ");
		try {
		List<Project> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Project>(Project.class),project.getProjectId());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return null;
	}

	@Override
	public Project selectProjectByObject(Project project){
		StringBuffer sql = new StringBuffer("SELECT project_id,project_name,project_comments,active_id,active_type_id,active_img_path,insert_time,city_type,cate_type,cat_type,promote_type,avgprice_type");
		sql.append(" FROM public.project WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(project != null){
			if(project.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(project.getProjectId());
			}
			if(project.getProjectName() != null && !"".equals(project.getProjectName())){
				sql.append(" AND project_name = ? ");
				list.add(project.getProjectName());
			}
			if(project.getProjectComments() != null && !"".equals(project.getProjectComments())){
				sql.append(" AND project_comments = ? ");
				list.add(project.getProjectComments());
			}
			if(project.getActiveId() != null){
				sql.append(" AND active_id = ? ");
				list.add(project.getActiveId());
			}
			if(project.getActiveTypeId() != null){
				sql.append(" AND active_type_id = ? ");
				list.add(project.getActiveTypeId());
			}
			if(project.getActiveImgPath() != null && !"".equals(project.getActiveImgPath())){
				sql.append(" AND active_img_path = ? ");
				list.add(project.getActiveImgPath());
			}
			if(project.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(project.getInsertTime());
			}
			createSQLForCustomFields(project, sql, list);
		}
		if(list.size() == 0){
			return null;
		}
		try {
		List<Project> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Project>(Project.class),list.toArray());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return null;
	}


	@Override
	public List<Project> selectProjectListByObject(Project project){
		StringBuffer sql = new StringBuffer("SELECT project_id,project_name,project_comments,active_id,active_type_id,active_img_path,insert_time");
		sql.append(" FROM public.project WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(project != null){
			if(project.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(project.getProjectId());
			}
			if(project.getProjectName() != null && !"".equals(project.getProjectName())){
				sql.append(" AND project_name = ? ");
				list.add(project.getProjectName());
			}
			if(project.getProjectComments() != null && !"".equals(project.getProjectComments())){
				sql.append(" AND project_comments = ? ");
				list.add(project.getProjectComments());
			}
			if(project.getActiveId() != null){
				sql.append(" AND active_id = ? ");
				list.add(project.getActiveId());
			}
			if(project.getActiveTypeId() != null){
				sql.append(" AND active_type_id = ? ");
				list.add(project.getActiveTypeId());
			}
			if(project.getActiveImgPath() != null && !"".equals(project.getActiveImgPath())){
				sql.append(" AND active_img_path = ? ");
				list.add(project.getActiveImgPath());
			}
			if(project.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(project.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.query(sql.toString(), new DAORowMapper<Project>(Project.class),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return null;
	}

	/**
	 * 参数activeName为页面输入 商户名或活动名
	 */
	public Map<String, Object> selectListByPage(Project project,int page, int pageSize,List<Active> dbActives, String activeName){
		StringBuffer sql = new StringBuffer("SELECT project_id,project_name,project_comments,active_id,active_type_id,active_img_path,insert_time,city_type,cate_type,cat_type,promote_type,avgprice_type");
		sql.append(" FROM public.project WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(project != null){
			if(project.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(project.getProjectId());
			}
			if(project.getProjectName() != null && !"".equals(project.getProjectName())){
				sql.append(" AND project_name = ? ");
				list.add(project.getProjectName());
			}
			if(project.getProjectComments() != null && !"".equals(project.getProjectComments())){
				sql.append(" AND project_comments = ? ");
				list.add(project.getProjectComments());
			}
			if(project.getActiveId() != null){
				sql.append(" AND active_id = ? ");
				list.add(project.getActiveId());
			}
			if(project.getActiveTypeId() != null){
				sql.append(" AND active_type_id = ? ");
				list.add(project.getActiveTypeId());
			}
			if(project.getActiveImgPath() != null && !"".equals(project.getActiveImgPath())){
				sql.append(" AND active_img_path = ? ");
				list.add(project.getActiveImgPath());
			}
			if(project.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(project.getInsertTime());
			}
			//以下是自定义逻辑
			createSQLForCustomFields(project, sql, list);
			
			String[] stringLabels = project.getLabelIds();
			if(stringLabels != null && stringLabels.length>0){
				sql.append(" AND EXISTS (select 1 from public.project_label as pl " +
						"WHERE public.project.project_id = pl.project_id and pl.label_id = "+stringLabels[0]+" ) ");
			}
			
		}
		if(dbActives!=null&&dbActives.size()>0){
			StringBuffer activeIdsString = new StringBuffer("(");
			int i=0;
			for (Active active : dbActives) {
				i++;
				if(i==dbActives.size()){
					//是最后一个
					activeIdsString.append("'"+active.getActiveId()+"'"+")");
				}else{
					activeIdsString.append("'"+active.getActiveId()+"'"+ ",");
				}
				
			}
			//list.add(activeIdsString.toString());
			sql.append(" AND active_id in "+activeIdsString.toString());
		}else {
			//如果activeName 有输入且没有查到数据 ， 则案例库返回空
			sql.append(" AND 1=2 ");
		}
		sql.append("  order by project_id desc ");
		try {
		Pagination<Project> pagination = new Pagination<Project>(sql.toString()
		 , page, pageSize,jdbcTemplate, new DAORowMapper<Project>(Project.class),list.toArray());
		return pagination.getResultMap();
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),project);
		}
		return null;
	}
	/**
	 * 为特殊字段创建sql
	 */
	private void createSQLForCustomFields(Project project, StringBuffer sql,
			List<Object> list) {
		
		String cityType = project.getCityType();
		String cateType = project.getCateType();
		String avgPriceType = project.getAvgPriceType();
		String catType = project.getCatType();
		String promoteType = project.getPromoteType();
		
		if(cityType != null && !"".equals(cityType)){
			sql.append(" AND splitids('"+cityType+"',public.project.city_type)=1 ");
		}
		if(cateType != null && !"".equals(cateType)){
			sql.append(" AND splitids('"+cateType+"',public.project.cate_type)=1 ");
		}
		if(avgPriceType != null && !"".equals(avgPriceType)){
			sql.append(" AND splitids('"+avgPriceType+"',public.project.avgprice_type)=1 ");
		}
		if(catType != null && !"".equals(catType)){
			sql.append(" AND splitids('"+catType+"',public.project.cat_type)=1 ");
		}
		if(promoteType != null && !"".equals(promoteType)){
			sql.append(" AND splitids('"+promoteType+"',public.project.promote_type)=1 ");
		}
	}

	@Override
	public int selectProjectCount(Project project){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append(" FROM public.project WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(project != null){
			if(project.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(project.getProjectId());
			}
			if(project.getProjectName() != null && !"".equals(project.getProjectName())){
				sql.append(" AND project_name = ? ");
				list.add(project.getProjectName());
			}
			if(project.getProjectComments() != null && !"".equals(project.getProjectComments())){
				sql.append(" AND project_comments = ? ");
				list.add(project.getProjectComments());
			}
			if(project.getActiveId() != null){
				sql.append(" AND active_id = ? ");
				list.add(project.getActiveId());
			}
			if(project.getActiveTypeId() != null){
				sql.append(" AND active_type_id = ? ");
				list.add(project.getActiveTypeId());
			}
			if(project.getActiveImgPath() != null && !"".equals(project.getActiveImgPath())){
				sql.append(" AND active_img_path = ? ");
				list.add(project.getActiveImgPath());
			}
			if(project.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(project.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.queryForInt(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return 0;
	}

	@Override
	public int addProject(final Project project){
		final StringBuffer sql = new StringBuffer("insert into  " +
				"public.project(project_name,project_comments,active_id,active_type_id," +
				"active_img_path,insert_time,city_type,cate_type,cat_type,promote_type,avgprice_type ) VALUES( ?,?,?,?,?,?,?,?,?,?,?)");
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, project.getProjectName());
					ps.setString(2, project.getProjectComments());
					ps.setInt(3, project.getActiveId());
					ps.setInt(4, project.getActiveTypeId());
					ps.setString(5, project.getActiveImgPath());
					ps.setTimestamp(6, project.getInsertTime());
					ps.setString(7, project.getCityType());
					ps.setString(8, project.getCateType());
					ps.setString(9, project.getCatType());
					ps.setString(10, project.getPromoteType());
					ps.setString(11, project.getAvgPriceType());
					return ps;
				}
			}, keyHolder);
			return (Integer)keyHolder.getKeys().get("project_id");
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),project);
		}
		return 0;
	}

	@Override
	public int addProjectList(List<Project> projectlist){
		int i = 0;
		for(Project project : projectlist){
			 i += addProject(project);
		}
		return i;
	}

	@Override
	public int removeProjectByObject(Project project){
		StringBuffer sql = new StringBuffer("DELETE FROM public.project WHERE 1=1 "); 
		List<Object> list = new ArrayList<Object>();
		if(project != null){
			if(project.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(project.getProjectId());
			}
			if(project.getProjectName() != null && !"".equals(project.getProjectName())){
				sql.append(" AND project_name = ? ");
				list.add(project.getProjectName());
			}
			if(project.getProjectComments() != null && !"".equals(project.getProjectComments())){
				sql.append(" AND project_comments = ? ");
				list.add(project.getProjectComments());
			}
			if(project.getActiveId() != null){
				sql.append(" AND active_id = ? ");
				list.add(project.getActiveId());
			}
			if(project.getActiveTypeId() != null){
				sql.append(" AND active_type_id = ? ");
				list.add(project.getActiveTypeId());
			}
			if(project.getActiveImgPath() != null && !"".equals(project.getActiveImgPath())){
				sql.append(" AND active_img_path = ? ");
				list.add(project.getActiveImgPath());
			}
			if(project.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(project.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return 0;
	}

	@Override
	public int removeProjectList(List<Project> projectlist){
		int i = 0;
		for(Project project : projectlist){
			 i += removeProjectByObject(project);
		}
		return i;
	}

	@Override
	public int updateProjectByID(Project project){
		StringBuffer sql = new StringBuffer("UPDATE public.project SET "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("project_id = ?, ");
		list.add(project.getProjectId());

		sql.append("project_name = ?, ");
		list.add(project.getProjectName());

		sql.append("project_comments = ?, ");
		list.add(project.getProjectComments());

		sql.append("active_id = ?, ");
		list.add(project.getActiveId());

		sql.append("active_type_id = ?, ");
		list.add(project.getActiveTypeId());

		sql.append("active_img_path = ?, ");
		list.add(project.getActiveImgPath());

		sql.append("insert_time = ?, ");
		list.add(project.getInsertTime());

		sql.append("city_type = ?, ");
		list.add(project.getCityType());

		sql.append("cate_type = ?, ");
		list.add(project.getCateType());

		sql.append("cat_type = ?, ");
		list.add(project.getCatType());

		sql.append("promote_type = ?, ");
		list.add(project.getPromoteType());
		
		sql.append("avgprice_type = ?, ");
		list.add(project.getAvgPriceType());

		String where = " WHERE project_id = ? "; 
		list.add(project.getProjectId());
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where,list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(project));
		}
		return 0;
	}

	@Override
	public int updateProjectByObject(Project updateProject , Project whereProject){
		StringBuffer sql = new StringBuffer("UPDATE public.project SET  "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("project_id = ?, ");
		list.add( updateProject.getProjectId());

		sql.append("project_name = ?, ");
		list.add( updateProject.getProjectName());

		sql.append("project_comments = ?, ");
		list.add( updateProject.getProjectComments());

		sql.append("active_id = ?, ");
		list.add( updateProject.getActiveId());

		sql.append("active_type_id = ?, ");
		list.add( updateProject.getActiveTypeId());

		sql.append("active_img_path = ?, ");
		list.add( updateProject.getActiveImgPath());

		sql.append("insert_time = ?, ");
		list.add( updateProject.getInsertTime());
		StringBuffer where = new StringBuffer(" WHERE 1=1  "); 
		if(whereProject != null){
			if(whereProject.getProjectId() != null){
				where.append(" AND project_id = ? ");
				list.add(whereProject.getProjectId());
			}
			if(whereProject.getProjectName() != null && !"".equals(whereProject.getProjectName())){
				where.append(" AND project_name = ? ");
				list.add(whereProject.getProjectName());
			}
			if(whereProject.getProjectComments() != null && !"".equals(whereProject.getProjectComments())){
				where.append(" AND project_comments = ? ");
				list.add(whereProject.getProjectComments());
			}
			if(whereProject.getActiveId() != null){
				where.append(" AND active_id = ? ");
				list.add(whereProject.getActiveId());
			}
			if(whereProject.getActiveTypeId() != null){
				where.append(" AND active_type_id = ? ");
				list.add(whereProject.getActiveTypeId());
			}
			if(whereProject.getActiveImgPath() != null && !"".equals(whereProject.getActiveImgPath())){
				where.append(" AND active_img_path = ? ");
				list.add(whereProject.getActiveImgPath());
			}
			if(whereProject.getInsertTime() != null){
				where.append(" AND insert_time = ? ");
				list.add(whereProject.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,StringUtils.removeLast(sql.toString())+where.toString(),ObjectToArryList.getListByObject(updateProject),ObjectToArryList.getListByObject(whereProject));
		}
		return 0;
	}

	@Override
	public int updateProjectList(List<Project> projectlist){
		int i = 0;
		for(Project project : projectlist){
			 i += updateProjectByID(project);
		}
		return i;
	}

	@Override
	public Integer selectProjectSeqNextVal(){
		String sql = "SELECT nextval('public.project_seq')";
		try {
		return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString());
		}
		return 0;
	}
}

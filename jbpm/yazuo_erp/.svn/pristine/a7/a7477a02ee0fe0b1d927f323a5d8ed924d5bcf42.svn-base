package com.yazuo.erp.project.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.Pagination;
import com.yazuo.erp.project.dao.ProjectLabelDao;
import com.yazuo.erp.project.vo.ProjectLabel;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;
import com.yazuo.util.StringUtils;

@Repository
public class ProjectLabelDaoImpl implements ProjectLabelDao{

	@Resource
	private JdbcTemplate jdbcTemplate;
	 Log logger = LogFactory.getLog(this.getClass());

	@Override
	public ProjectLabel selectProjectLabelByID(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("SELECT project_label_id,project_id,label_id,insert_time");
		sql.append(" FROM public.project_label WHERE 1=1 ");
		sql.append(" AND project_label_id  =? ");
		try {
		List<ProjectLabel> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<ProjectLabel>(ProjectLabel.class),projectLabel.getProjectLabelId());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return null;
	}

	@Override
	public ProjectLabel selectProjectLabelByObject(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("SELECT project_label_id,project_id,label_id,insert_time");
		sql.append(" FROM public.project_label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(projectLabel != null){
			if(projectLabel.getProjectLabelId() != null){
				sql.append(" AND project_label_id = ? ");
				list.add(projectLabel.getProjectLabelId());
			}
			if(projectLabel.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(projectLabel.getProjectId());
			}
			if(projectLabel.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(projectLabel.getLabelId());
			}
			if(projectLabel.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(projectLabel.getInsertTime());
			}
		}
		if(list.size() == 0){
			return null;
		}
		try {
		List<ProjectLabel> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<ProjectLabel>(ProjectLabel.class),list.toArray());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return null;
	}


	@Override
	public List<ProjectLabel> selectProjectLabelListByObject(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("SELECT project_label_id,project_id,label_id,insert_time");
		sql.append(" FROM public.project_label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(projectLabel != null){
			if(projectLabel.getProjectLabelId() != null){
				sql.append(" AND project_label_id = ? ");
				list.add(projectLabel.getProjectLabelId());
			}
			if(projectLabel.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(projectLabel.getProjectId());
			}
			if(projectLabel.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(projectLabel.getLabelId());
			}
			if(projectLabel.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(projectLabel.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.query(sql.toString(), new DAORowMapper<ProjectLabel>(ProjectLabel.class),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return null;
	}

	@Override
	public Map<String, Object> selectListByPage(ProjectLabel projectLabel,int page, int pageSize){
		StringBuffer sql = new StringBuffer("SELECT project_label_id,project_id,label_id,insert_time");
		sql.append(" FROM public.project_label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(projectLabel != null){
			if(projectLabel.getProjectLabelId() != null){
				sql.append(" AND project_label_id = ? ");
				list.add(projectLabel.getProjectLabelId());
			}
			if(projectLabel.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(projectLabel.getProjectId());
			}
			if(projectLabel.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(projectLabel.getLabelId());
			}
			if(projectLabel.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(projectLabel.getInsertTime());
			}
		}
		try {
		Pagination<ProjectLabel> pagination = new Pagination<ProjectLabel>(sql.toString()
		 , page, pageSize,jdbcTemplate, new DAORowMapper<ProjectLabel>(ProjectLabel.class),list.toArray());
		return pagination.getResultMap();
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return null;
	}

	@Override
	public int selectProjectLabelCount(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append(" FROM public.project_label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(projectLabel != null){
			if(projectLabel.getProjectLabelId() != null){
				sql.append(" AND project_label_id = ? ");
				list.add(projectLabel.getProjectLabelId());
			}
			if(projectLabel.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(projectLabel.getProjectId());
			}
			if(projectLabel.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(projectLabel.getLabelId());
			}
			if(projectLabel.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(projectLabel.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.queryForInt(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return 0;
	}

	@Override
	public int addProjectLabel(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("insert into  public.project_label(project_id,label_id,insert_time		 ) VALUES( ?,?,?)");
		try {
		return jdbcTemplate.update(sql.toString(),projectLabel.getProjectId(),projectLabel.getLabelId(),projectLabel.getInsertTime());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return 0;
	}

	@Override
	public int addProjectLabelList(List<ProjectLabel> projectLabellist){
		int i = 0;
		for(ProjectLabel projectLabel : projectLabellist){
			 i += addProjectLabel(projectLabel);
		}
		return i;
	}

	@Override
	public int removeProjectLabelByObject(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("DELETE FROM public.project_label WHERE 1=1 "); 
		List<Object> list = new ArrayList<Object>();
		if(projectLabel != null){
			if(projectLabel.getProjectLabelId() != null){
				sql.append(" AND project_label_id = ? ");
				list.add(projectLabel.getProjectLabelId());
			}
			if(projectLabel.getProjectId() != null){
				sql.append(" AND project_id = ? ");
				list.add(projectLabel.getProjectId());
			}
			if(projectLabel.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(projectLabel.getLabelId());
			}
			if(projectLabel.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(projectLabel.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return 0;
	}

	@Override
	public int removeProjectLabelList(List<ProjectLabel> projectLabellist){
		int i = 0;
		for(ProjectLabel projectLabel : projectLabellist){
			 i += removeProjectLabelByObject(projectLabel);
		}
		return i;
	}

	@Override
	public int updateProjectLabelByID(ProjectLabel projectLabel){
		StringBuffer sql = new StringBuffer("UPDATE public.project_label SET "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("project_label_id = ?, ");
		list.add(projectLabel.getProjectLabelId());

		sql.append("project_id = ?, ");
		list.add(projectLabel.getProjectId());

		sql.append("label_id = ?, ");
		list.add(projectLabel.getLabelId());

		sql.append("insert_time = ?, ");
		list.add(projectLabel.getInsertTime());

		String where = " WHERE project_label_id = ? "; 
		list.add(projectLabel.getProjectLabelId());
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where,list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(projectLabel));
		}
		return 0;
	}

	@Override
	public int updateProjectLabelByObject(ProjectLabel updateProjectLabel , ProjectLabel whereProjectLabel){
		StringBuffer sql = new StringBuffer("UPDATE public.project_label SET  "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("project_label_id = ?, ");
		list.add( updateProjectLabel.getProjectLabelId());

		sql.append("project_id = ?, ");
		list.add( updateProjectLabel.getProjectId());

		sql.append("label_id = ?, ");
		list.add( updateProjectLabel.getLabelId());

		sql.append("insert_time = ?, ");
		list.add( updateProjectLabel.getInsertTime());
		StringBuffer where = new StringBuffer(" WHERE 1=1  "); 
		if(whereProjectLabel != null){
			if(whereProjectLabel.getProjectLabelId() != null){
				where.append(" AND project_label_id = ? ");
				list.add(whereProjectLabel.getProjectLabelId());
			}
			if(whereProjectLabel.getProjectId() != null){
				where.append(" AND project_id = ? ");
				list.add(whereProjectLabel.getProjectId());
			}
			if(whereProjectLabel.getLabelId() != null){
				where.append(" AND label_id = ? ");
				list.add(whereProjectLabel.getLabelId());
			}
			if(whereProjectLabel.getInsertTime() != null){
				where.append(" AND insert_time = ? ");
				list.add(whereProjectLabel.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,StringUtils.removeLast(sql.toString())+where.toString(),ObjectToArryList.getListByObject(updateProjectLabel),ObjectToArryList.getListByObject(whereProjectLabel));
		}
		return 0;
	}

	@Override
	public int updateProjectLabelList(List<ProjectLabel> projectLabellist){
		int i = 0;
		for(ProjectLabel projectLabel : projectLabellist){
			 i += updateProjectLabelByID(projectLabel);
		}
		return i;
	}

	@Override
	public Integer selectProjectLabelSeqNextVal(){
		String sql = "SELECT nextval('public.project_label_seq')";
		try {
		return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString());
		}
		return 0;
	}
}

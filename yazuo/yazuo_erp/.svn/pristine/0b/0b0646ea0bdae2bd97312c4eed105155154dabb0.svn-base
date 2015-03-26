package com.yazuo.erp.project.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.Pagination;
import com.yazuo.erp.project.dao.LabelDao;
import com.yazuo.erp.project.vo.Label;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;
import com.yazuo.util.StringUtils;

@Repository
public class LabelDaoImpl implements LabelDao{

	@Resource
	private JdbcTemplate jdbcTemplate;
	 Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Label selectLabelByID(Label label){
		StringBuffer sql = new StringBuffer("SELECT label_id,lable_name,lable_desc,created_person,insert_time");
		sql.append(" FROM public.label WHERE 1=1 ");
		sql.append(" AND label_id  =? ");
		try {
		List<Label> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Label>(Label.class),label.getLabelId());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return null;
	}

	@Override
	public Label selectLabelByObject(Label label){
		StringBuffer sql = new StringBuffer("SELECT label_id,lable_name,lable_desc,created_person,insert_time");
		sql.append(" FROM public.label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(label != null){
			if(label.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(label.getLabelId());
			}
			if(label.getLableName() != null && !"".equals(label.getLableName())){
				sql.append(" AND lable_name = ? ");
				list.add(label.getLableName());
			}
			if(label.getLableDesc() != null && !"".equals(label.getLableDesc())){
				sql.append(" AND lable_desc = ? ");
				list.add(label.getLableDesc());
			}
			if(label.getCreatedPerson() != null && !"".equals(label.getCreatedPerson())){
				sql.append(" AND created_person = ? ");
				list.add(label.getCreatedPerson());
			}
			if(label.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(label.getInsertTime());
			}
		}
		if(list.size() == 0){
			return null;
		}
		try {
		List<Label> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Label>(Label.class),list.toArray());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return null;
	}


	@Override
	public List<Label> selectLabelListByObject(Label label){
		StringBuffer sql = new StringBuffer("SELECT label_id,lable_name,lable_desc,created_person,insert_time");
		sql.append(" FROM public.label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(label != null){
			if(label.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(label.getLabelId());
			}
			if(label.getLableName() != null && !"".equals(label.getLableName())){
				sql.append(" AND lable_name = ? ");
				list.add(label.getLableName());
			}
			if(label.getLableDesc() != null && !"".equals(label.getLableDesc())){
				sql.append(" AND lable_desc = ? ");
				list.add(label.getLableDesc());
			}
			if(label.getCreatedPerson() != null && !"".equals(label.getCreatedPerson())){
				sql.append(" AND created_person = ? ");
				list.add(label.getCreatedPerson());
			}
			if(label.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(label.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.query(sql.toString(), new DAORowMapper<Label>(Label.class),list.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> selectListByPage(Label label,int page, int pageSize){
		StringBuffer sql = new StringBuffer("SELECT label_id,lable_name,lable_desc,created_person,insert_time");
		sql.append(" FROM public.label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(label != null){
			if(label.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(label.getLabelId());
			}
			if(label.getLableName() != null && !"".equals(label.getLableName())){
				sql.append(" AND lable_name = ? ");
				list.add(label.getLableName());
			}
			if(label.getLableDesc() != null && !"".equals(label.getLableDesc())){
				sql.append(" AND lable_desc = ? ");
				list.add(label.getLableDesc());
			}
			if(label.getCreatedPerson() != null && !"".equals(label.getCreatedPerson())){
				sql.append(" AND created_person = ? ");
				list.add(label.getCreatedPerson());
			}
			if(label.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(label.getInsertTime());
			}
		}
		try {
		Pagination<Label> pagination = new Pagination<Label>(sql.toString()
		 , page, pageSize,jdbcTemplate, new DAORowMapper<Label>(Label.class),list.toArray());
		return pagination.getResultMap();
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return null;
	}

	@Override
	public int selectLabelCount(Label label){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append(" FROM public.label WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(label != null){
			if(label.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(label.getLabelId());
			}
			if(label.getLableName() != null && !"".equals(label.getLableName())){
				sql.append(" AND lable_name = ? ");
				list.add(label.getLableName());
			}
			if(label.getLableDesc() != null && !"".equals(label.getLableDesc())){
				sql.append(" AND lable_desc = ? ");
				list.add(label.getLableDesc());
			}
			if(label.getCreatedPerson() != null && !"".equals(label.getCreatedPerson())){
				sql.append(" AND created_person = ? ");
				list.add(label.getCreatedPerson());
			}
			if(label.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(label.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.queryForInt(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return 0;
	}

	@Override
	public int addLabel(final Label label) {
		final StringBuffer sql = new StringBuffer(
				"insert into  public.label(lable_name,lable_desc,created_person,insert_time	 ) VALUES(?,?,?,?)");
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, label.getLableName());
					ps.setString(2, label.getLableDesc());
					ps.setString(3, label.getCreatedPerson());
					ps.setTimestamp(4, label.getInsertTime());
					return ps;
				}
			}, keyHolder);

			return (Integer)keyHolder.getKeys().get("label_id");
		} catch (Exception e) {
			LogUtils.error("!", logger, e, sql.toString(),
					ObjectToArryList.getListByObject(label));
		}
		return 0;
	}

	@Override
	public int addLabelList(List<Label> labellist){
		int i = 0;
		for(Label label : labellist){
			 i += addLabel(label);
		}
		return i;
	}

	@Override
	public int removeLabelByObject(Label label){
		StringBuffer sql = new StringBuffer("DELETE FROM public.label WHERE 1=1 "); 
		List<Object> list = new ArrayList<Object>();
		if(label != null){
			if(label.getLabelId() != null){
				sql.append(" AND label_id = ? ");
				list.add(label.getLabelId());
			}
			if(label.getLableName() != null && !"".equals(label.getLableName())){
				sql.append(" AND lable_name = ? ");
				list.add(label.getLableName());
			}
			if(label.getLableDesc() != null && !"".equals(label.getLableDesc())){
				sql.append(" AND lable_desc = ? ");
				list.add(label.getLableDesc());
			}
			if(label.getCreatedPerson() != null && !"".equals(label.getCreatedPerson())){
				sql.append(" AND created_person = ? ");
				list.add(label.getCreatedPerson());
			}
			if(label.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(label.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return 0;
	}

	@Override
	public int removeLabelList(List<Label> labellist){
		int i = 0;
		for(Label label : labellist){
			 i += removeLabelByObject(label);
		}
		return i;
	}

	@Override
	public int updateLabelByID(Label label){
		StringBuffer sql = new StringBuffer("UPDATE public.label SET "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("label_id = ?, ");
		list.add(label.getLabelId());

		sql.append("lable_name = ?, ");
		list.add(label.getLableName());

		sql.append("lable_desc = ?, ");
		list.add(label.getLableDesc());

		sql.append("created_person = ?, ");
		list.add(label.getCreatedPerson());

		sql.append("insert_time = ?, ");
		list.add(label.getInsertTime());

		String where = " WHERE label_id = ? "; 
		list.add(label.getLabelId());
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where,list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(label));
		}
		return 0;
	}

	@Override
	public int updateLabelByObject(Label updateLabel , Label whereLabel){
		StringBuffer sql = new StringBuffer("UPDATE public.label SET  "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("label_id = ?, ");
		list.add( updateLabel.getLabelId());

		sql.append("lable_name = ?, ");
		list.add( updateLabel.getLableName());

		sql.append("lable_desc = ?, ");
		list.add( updateLabel.getLableDesc());

		sql.append("created_person = ?, ");
		list.add( updateLabel.getCreatedPerson());

		sql.append("insert_time = ?, ");
		list.add( updateLabel.getInsertTime());
		StringBuffer where = new StringBuffer(" WHERE 1=1  "); 
		if(whereLabel != null){
			if(whereLabel.getLabelId() != null){
				where.append(" AND label_id = ? ");
				list.add(whereLabel.getLabelId());
			}
			if(whereLabel.getLableName() != null && !"".equals(whereLabel.getLableName())){
				where.append(" AND lable_name = ? ");
				list.add(whereLabel.getLableName());
			}
			if(whereLabel.getLableDesc() != null && !"".equals(whereLabel.getLableDesc())){
				where.append(" AND lable_desc = ? ");
				list.add(whereLabel.getLableDesc());
			}
			if(whereLabel.getCreatedPerson() != null && !"".equals(whereLabel.getCreatedPerson())){
				where.append(" AND created_person = ? ");
				list.add(whereLabel.getCreatedPerson());
			}
			if(whereLabel.getInsertTime() != null){
				where.append(" AND insert_time = ? ");
				list.add(whereLabel.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,StringUtils.removeLast(sql.toString())+where.toString(),ObjectToArryList.getListByObject(updateLabel),ObjectToArryList.getListByObject(whereLabel));
		}
		return 0;
	}

	@Override
	public int updateLabelList(List<Label> labellist){
		int i = 0;
		for(Label label : labellist){
			 i += updateLabelByID(label);
		}
		return i;
	}

	@Override
	public Integer selectLabelSeqNextVal(){
		String sql = "SELECT nextval('public.label_seq')";
		try {
		return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString());
		}
		return 0;
	}
}

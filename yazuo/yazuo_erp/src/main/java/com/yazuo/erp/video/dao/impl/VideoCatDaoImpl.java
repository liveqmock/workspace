package com.yazuo.erp.video.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.base.Pagination;
import com.yazuo.erp.video.dao.VideoCatDao;
import com.yazuo.erp.video.vo.VideoCat;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;
import com.yazuo.util.StringUtils;

@Repository
public class VideoCatDaoImpl implements VideoCatDao{

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	 Log logger = LogFactory.getLog(this.getClass());

	@Override
	public VideoCat selectVideoCatByID(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("SELECT video_cat_id,cat_val,cat_desc,insert_time");
		sql.append(" FROM public.video_cat WHERE 1=1 ");
		sql.append(" AND video_cat_id  =? ");
		try {
		List<VideoCat> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<VideoCat>(VideoCat.class),videoCat.getVideoCatId());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return null;
	}

	@Override
	public VideoCat selectVideoCatByObject(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("SELECT video_cat_id,cat_val,cat_desc,insert_time");
		sql.append(" FROM public.video_cat WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(videoCat != null){
			if(videoCat.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(videoCat.getVideoCatId());
			}
			if(videoCat.getCatVal() != null){
				sql.append(" AND cat_val = ? ");
				list.add(videoCat.getCatVal());
			}
			if(videoCat.getCatDesc() != null && !"".equals(videoCat.getCatDesc())){
				sql.append(" AND cat_desc = ? ");
				list.add(videoCat.getCatDesc());
			}
			if(videoCat.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(videoCat.getInsertTime());
			}
		}
		if(list.size() == 0){
			return null;
		}
		try {
		List<VideoCat> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<VideoCat>(VideoCat.class),list.toArray());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return null;
	}


	@Override
	public List<VideoCat> selectVideoCatListByObject(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("SELECT video_cat_id,cat_val,cat_desc,insert_time");
		sql.append(" FROM public.video_cat WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(videoCat != null){
			if(videoCat.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(videoCat.getVideoCatId());
			}
			if(videoCat.getCatVal() != null){
				sql.append(" AND cat_val = ? ");
				list.add(videoCat.getCatVal());
			}
			if(videoCat.getCatDesc() != null && !"".equals(videoCat.getCatDesc())){
				sql.append(" AND cat_desc = ? ");
				list.add(videoCat.getCatDesc());
			}
			if(videoCat.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(videoCat.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.query(sql.toString(), new DAORowMapper<VideoCat>(VideoCat.class),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),videoCat);
		}
		return null;
	}

	@Override
	public Map<String, Object> selectListByPage(VideoCat videoCat,int page, int pageSize){
		StringBuffer sql = new StringBuffer("SELECT video_cat_id,cat_val,cat_desc,insert_time");
		sql.append(" FROM public.video_cat WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(videoCat != null){
			if(videoCat.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(videoCat.getVideoCatId());
			}
			if(videoCat.getCatVal() != null){
				sql.append(" AND cat_val = ? ");
				list.add(videoCat.getCatVal());
			}
			if(videoCat.getCatDesc() != null && !"".equals(videoCat.getCatDesc())){
				sql.append(" AND cat_desc = ? ");
				list.add(videoCat.getCatDesc());
			}
			if(videoCat.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(videoCat.getInsertTime());
			}
		}
		try {
		Pagination<VideoCat> pagination = new Pagination<VideoCat>(sql.toString()
		 , page, pageSize,jdbcTemplate, new DAORowMapper<VideoCat>(VideoCat.class),list.toArray());
		return pagination.getResultMap();
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return null;
	}

	@Override
	public int selectVideoCatCount(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append(" FROM public.video_cat WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(videoCat != null){
			if(videoCat.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(videoCat.getVideoCatId());
			}
			if(videoCat.getCatVal() != null){
				sql.append(" AND cat_val = ? ");
				list.add(videoCat.getCatVal());
			}
			if(videoCat.getCatDesc() != null && !"".equals(videoCat.getCatDesc())){
				sql.append(" AND cat_desc = ? ");
				list.add(videoCat.getCatDesc());
			}
			if(videoCat.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(videoCat.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.queryForInt(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return 0;
	}

	@Override
	public int addVideoCat(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("insert into  public.video_cat(video_cat_id,cat_val,cat_desc,insert_time		 ) VALUES( ?,?,?,?)");
		try {
		return jdbcTemplate.update(sql.toString(),videoCat.getVideoCatId(),videoCat.getCatVal(),videoCat.getCatDesc(),videoCat.getInsertTime());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return 0;
	}

	@Override
	public int addVideoCatList(List<VideoCat> videoCatlist){
		int i = 0;
		for(VideoCat videoCat : videoCatlist){
			 i += addVideoCat(videoCat);
		}
		return i;
	}

	@Override
	public int removeVideoCatByObject(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("DELETE FROM public.video_cat WHERE 1=1 "); 
		List<Object> list = new ArrayList<Object>();
		if(videoCat != null){
			if(videoCat.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(videoCat.getVideoCatId());
			}
			if(videoCat.getCatVal() != null){
				sql.append(" AND cat_val = ? ");
				list.add(videoCat.getCatVal());
			}
			if(videoCat.getCatDesc() != null && !"".equals(videoCat.getCatDesc())){
				sql.append(" AND cat_desc = ? ");
				list.add(videoCat.getCatDesc());
			}
			if(videoCat.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(videoCat.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return 0;
	}

	@Override
	public int removeVideoCatList(List<VideoCat> videoCatlist){
		int i = 0;
		for(VideoCat videoCat : videoCatlist){
			 i += removeVideoCatByObject(videoCat);
		}
		return i;
	}

	@Override
	public int updateVideoCatByID(VideoCat videoCat){
		StringBuffer sql = new StringBuffer("UPDATE public.video_cat SET "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("video_cat_id = ?, ");
		list.add(videoCat.getVideoCatId());

		sql.append("cat_val = ?, ");
		list.add(videoCat.getCatVal());

		sql.append("cat_desc = ?, ");
		list.add(videoCat.getCatDesc());

		sql.append("insert_time = ?, ");
		list.add(videoCat.getInsertTime());

		String where = " WHERE video_cat_id = ? "; 
		list.add(videoCat.getVideoCatId());
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where,list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(videoCat));
		}
		return 0;
	}

	@Override
	public int updateVideoCatByObject(VideoCat updateVideoCat , VideoCat whereVideoCat){
		StringBuffer sql = new StringBuffer("UPDATE public.video_cat SET  "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("video_cat_id = ?, ");
		list.add( updateVideoCat.getVideoCatId());

		sql.append("cat_val = ?, ");
		list.add( updateVideoCat.getCatVal());

		sql.append("cat_desc = ?, ");
		list.add( updateVideoCat.getCatDesc());

		sql.append("insert_time = ?, ");
		list.add( updateVideoCat.getInsertTime());
		StringBuffer where = new StringBuffer(" WHERE 1=1  "); 
		if(whereVideoCat != null){
			if(whereVideoCat.getVideoCatId() != null){
				where.append(" AND video_cat_id = ? ");
				list.add(whereVideoCat.getVideoCatId());
			}
			if(whereVideoCat.getCatVal() != null){
				where.append(" AND cat_val = ? ");
				list.add(whereVideoCat.getCatVal());
			}
			if(whereVideoCat.getCatDesc() != null && !"".equals(whereVideoCat.getCatDesc())){
				where.append(" AND cat_desc = ? ");
				list.add(whereVideoCat.getCatDesc());
			}
			if(whereVideoCat.getInsertTime() != null){
				where.append(" AND insert_time = ? ");
				list.add(whereVideoCat.getInsertTime());
			}
		}
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,StringUtils.removeLast(sql.toString())+where.toString(),ObjectToArryList.getListByObject(updateVideoCat),ObjectToArryList.getListByObject(whereVideoCat));
		}
		return 0;
	}

	@Override
	public int updateVideoCatList(List<VideoCat> videoCatlist){
		int i = 0;
		for(VideoCat videoCat : videoCatlist){
			 i += updateVideoCatByID(videoCat);
		}
		return i;
	}

	@Override
	public Integer selectVideoCatSeqNextVal(){
		String sql = "SELECT nextval('public.video_cat_seq')";
		try {
		return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString());
		}
		return 0;
	}
}

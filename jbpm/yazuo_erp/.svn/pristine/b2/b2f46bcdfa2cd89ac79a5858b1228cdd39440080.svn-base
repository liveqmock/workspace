package com.yazuo.erp.video.dao.impl;

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
import com.yazuo.erp.video.dao.VideoDao;
import com.yazuo.erp.video.vo.Video;
import com.yazuo.util.DAORowMapper;
import com.yazuo.util.LogUtils;
import com.yazuo.util.ObjectToArryList;
import com.yazuo.util.StringUtils;

@Repository
public class VideoDaoImpl implements VideoDao{


	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	 Log logger = LogFactory.getLog(this.getClass());

	@Override
	public Video selectVideoByID(Video video){
		StringBuffer sql = new StringBuffer("SELECT video_id,video_name,video_desc,upload_time,presenter,video_path,insert_time,video_cat_id");
		sql.append(" FROM public.video WHERE 1=1 ");
		sql.append(" AND video_id  =? ");
		try {
		List<Video> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Video>(Video.class),video.getVideoId());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return null;
	}

	@Override
	public Video selectVideoByObject(Video video){
		StringBuffer sql = new StringBuffer("SELECT video_id,video_name,video_desc,upload_time,presenter,video_path,insert_time,video_cat_id");
		sql.append(" FROM public.video WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" AND video_name = ? ");
				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		if(list.size() == 0){
			return null;
		}
		try {
		List<Video> resultList = jdbcTemplate.query(sql.toString(), new DAORowMapper<Video>(Video.class),list.toArray());
		if (resultList != null && resultList.size() > 0)
			return resultList.get(0);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return null;
	}
	
	@Override
	public List<Video> selectVideoAndCatByObject(Video video){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT public.video.*, public.video_cat.cat_val, public.video_cat.cat_desc ");
		sql.append(" FROM public.video left join public.video_cat on public.video.video_cat_id = public.video_cat.video_cat_id WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" AND video_name = ? ");
				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null && video.getVideoCatId()!=0){
				sql.append(" AND video.video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		try {
		return jdbcTemplate.query(		sql.toString() , new DAORowMapper<Video>(Video.class),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return null;
	}

	@Override
	public List<Video> selectVideoListByObject(Video video){
		StringBuffer sql = new StringBuffer("SELECT video_id,video_name,video_desc,upload_time,presenter,video_path,insert_time,video_cat_id");
		sql.append(" FROM public.video WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" AND video_name = ? ");
				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		try {
		return jdbcTemplate.query(sql.toString(), new DAORowMapper<Video>(Video.class),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return null;
	}

	@Override
	public Map<String, Object> selectListByPage(Video video,int page, int pageSize){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT public.video.*, public.video_cat.cat_val, public.video_cat.cat_desc ");
		sql.append(" FROM public.video left join public.video_cat on public.video.video_cat_id = public.video_cat.video_cat_id WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" and video_name like '%"+video.getVideoName()+"%'");
//				sql.append(" AND video_name = ? ");
//				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null && video.getVideoCatId()!=0){
				sql.append(" AND video.video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		try {
		Pagination<Video> pagination = new Pagination<Video>(sql.toString()
		 , page, pageSize,jdbcTemplate, new DAORowMapper<Video>(Video.class),list.toArray());
		return pagination.getResultMap();
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return null;
	}

	@Override
	public int selectVideoCount(Video video){
		StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
		sql.append(" FROM public.video WHERE 1=1 ");
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" and video_name like '%"+video.getVideoName()+"%'");
//				sql.append(" AND video_name = ? ");
//				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null && video.getVideoCatId()!=0){
				sql.append(" AND video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		try {
		return jdbcTemplate.queryForInt(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return 0;
	}

	@Override
	public int addVideo(final Video video){
		final StringBuffer sql = new StringBuffer("insert into  public.video( video_name,video_desc,upload_time," +
				"presenter,video_path,insert_time,video_cat_id		 ) VALUES( ?,?,?,?,?,?,?)");
		try {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, video.getVideoName());
				ps.setString(2, video.getVideoDesc());
				ps.setTimestamp(3, video.getUploadTime());
				ps.setString(4, video.getPresenter());
				ps.setString(5, video.getVideoPath());
				ps.setTimestamp(6, video.getInsertTime());
				ps.setInt(7, video.getVideoCatId());
				return ps;
			}
		}, keyHolder);

		return (Integer)keyHolder.getKeys().get("video_id");
	
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return 0;
	}

	@Override
	public int addVideoList(List<Video> videolist){
		int i = 0;
		for(Video video : videolist){
			 i += addVideo(video);
		}
		return i;
	}

	@Override
	public int removeVideoByObject(Video video){
		StringBuffer sql = new StringBuffer("DELETE FROM public.video WHERE 1=1 "); 
		List<Object> list = new ArrayList<Object>();
		if(video != null){
			if(video.getVideoId() != null){
				sql.append(" AND video_id = ? ");
				list.add(video.getVideoId());
			}
			if(video.getVideoName() != null && !"".equals(video.getVideoName())){
				sql.append(" AND video_name = ? ");
				list.add(video.getVideoName());
			}
			if(video.getVideoDesc() != null && !"".equals(video.getVideoDesc())){
				sql.append(" AND video_desc = ? ");
				list.add(video.getVideoDesc());
			}
			if(video.getUploadTime() != null){
				sql.append(" AND upload_time = ? ");
				list.add(video.getUploadTime());
			}
			if(video.getPresenter() != null && !"".equals(video.getPresenter())){
				sql.append(" AND presenter = ? ");
				list.add(video.getPresenter());
			}
			if(video.getVideoPath() != null && !"".equals(video.getVideoPath())){
				sql.append(" AND video_path = ? ");
				list.add(video.getVideoPath());
			}
			if(video.getInsertTime() != null){
				sql.append(" AND insert_time = ? ");
				list.add(video.getInsertTime());
			}
			if(video.getVideoCatId() != null){
				sql.append(" AND video_cat_id = ? ");
				list.add(video.getVideoCatId());
			}
		}
		try {
		return jdbcTemplate.update(sql.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return 0;
	}

	@Override
	public int removeVideoList(List<Video> videolist){
		int i = 0;
		for(Video video : videolist){
			 i += removeVideoByObject(video);
		}
		return i;
	}

	@Override
	public int updateVideoByID(Video video){
		StringBuffer sql = new StringBuffer("UPDATE public.video SET "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("video_id = ?, ");
		list.add(video.getVideoId());

		sql.append("video_name = ?, ");
		list.add(video.getVideoName());

		sql.append("video_desc = ?, ");
		list.add(video.getVideoDesc());

		sql.append("upload_time = ?, ");
		list.add(video.getUploadTime());

		sql.append("presenter = ?, ");
		list.add(video.getPresenter());

		sql.append("video_path = ?, ");
		list.add(video.getVideoPath());

		sql.append("insert_time = ?, ");
		list.add(video.getInsertTime());

		sql.append("video_cat_id = ?, ");
		list.add(video.getVideoCatId());

		String where = " WHERE video_id = ? "; 
		list.add(video.getVideoId());
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where,list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString(),ObjectToArryList.getListByObject(video));
		}
		return 0;
	}

	@Override
	public int updateVideoByObject(Video updateVideo , Video whereVideo){
		StringBuffer sql = new StringBuffer("UPDATE public.video SET  "); 
		List<Object> list = new ArrayList<Object>();

		sql.append("video_id = ?, ");
		list.add( updateVideo.getVideoId());

		sql.append("video_name = ?, ");
		list.add( updateVideo.getVideoName());

		sql.append("video_desc = ?, ");
		list.add( updateVideo.getVideoDesc());

		sql.append("upload_time = ?, ");
		list.add( updateVideo.getUploadTime());

		sql.append("presenter = ?, ");
		list.add( updateVideo.getPresenter());

		sql.append("video_path = ?, ");
		list.add( updateVideo.getVideoPath());

		sql.append("insert_time = ?, ");
		list.add( updateVideo.getInsertTime());

		sql.append("video_cat_id = ?, ");
		list.add( updateVideo.getVideoCatId());
		StringBuffer where = new StringBuffer(" WHERE 1=1  "); 
		if(whereVideo != null){
			if(whereVideo.getVideoId() != null){
				where.append(" AND video_id = ? ");
				list.add(whereVideo.getVideoId());
			}
			if(whereVideo.getVideoName() != null && !"".equals(whereVideo.getVideoName())){
				where.append(" AND video_name = ? ");
				list.add(whereVideo.getVideoName());
			}
			if(whereVideo.getVideoDesc() != null && !"".equals(whereVideo.getVideoDesc())){
				where.append(" AND video_desc = ? ");
				list.add(whereVideo.getVideoDesc());
			}
			if(whereVideo.getUploadTime() != null){
				where.append(" AND upload_time = ? ");
				list.add(whereVideo.getUploadTime());
			}
			if(whereVideo.getPresenter() != null && !"".equals(whereVideo.getPresenter())){
				where.append(" AND presenter = ? ");
				list.add(whereVideo.getPresenter());
			}
			if(whereVideo.getVideoPath() != null && !"".equals(whereVideo.getVideoPath())){
				where.append(" AND video_path = ? ");
				list.add(whereVideo.getVideoPath());
			}
			if(whereVideo.getInsertTime() != null){
				where.append(" AND insert_time = ? ");
				list.add(whereVideo.getInsertTime());
			}
			if(whereVideo.getVideoCatId() != null){
				where.append(" AND video_cat_id = ? ");
				list.add(whereVideo.getVideoCatId());
			}
		}
		try {
		return jdbcTemplate.update(StringUtils.removeLast(sql.toString()) + where.toString(),list.toArray());
		} catch (Exception e) {
			LogUtils.error("!", logger, e,StringUtils.removeLast(sql.toString())+where.toString(),ObjectToArryList.getListByObject(updateVideo),ObjectToArryList.getListByObject(whereVideo));
		}
		return 0;
	}

	@Override
	public int updateVideoList(List<Video> videolist){
		int i = 0;
		for(Video video : videolist){
			 i += updateVideoByID(video);
		}
		return i;
	}

	@Override
	public Integer selectVideoSeqNextVal(){
		String sql = "SELECT nextval('public.video_seq')";
		try {
		return jdbcTemplate.queryForInt(sql);
		} catch (Exception e) {
			LogUtils.error("!", logger, e,sql.toString());
		}
		return 0;
	}
}

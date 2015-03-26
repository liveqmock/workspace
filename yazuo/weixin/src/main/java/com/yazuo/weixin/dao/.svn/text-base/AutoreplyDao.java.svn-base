package com.yazuo.weixin.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.AutoreplyVO;

@Repository
public class AutoreplyDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public List<AutoreplyVO> getAutoreplyListByBrandId(Integer brandId) {
		String sql = "select * from  weixin.autoreply_config where brand_id=? and  is_delete=false order by id desc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AutoreplyVO>(
				AutoreplyVO.class), brandId);
	}
	public int countAutoreplyNotKeyWord(Integer brandId) {
		String sql = "select count(*) from  weixin.autoreply_config where brand_id=? and  is_delete=false and(keyword ='' or keyword is null)";
		return jdbcTemplate.queryForInt(sql, brandId);
	}
	public Map<String, Object> getAutoreplyListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.autoreply_config where brand_id=? and  is_delete=false order by id desc";
		Pagination<AutoreplyVO> pagination = new Pagination<AutoreplyVO>(sql,
				page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<AutoreplyVO>(AutoreplyVO.class),
				brandId);
		return pagination.getResultMap();
	}

	public boolean insertAutoreply(AutoreplyVO autoreply) {
		String sql = "INSERT INTO weixin.autoreply_config(brand_id,keyword_type,keyword,reply_type,reply_content,update_time,is_delete, event_type,specific_type) values (?,?,?,?,?,?,?,?,?);";
		try {
			return jdbcTemplate.update(sql, autoreply.getBrandId(),
					autoreply.getKeywordType(), autoreply.getKeyword(),
					autoreply.getReplyType(), autoreply.getReplyContent(),
					autoreply.getUpdateTime(), false, autoreply.getEventType(),
					autoreply.getSpecificType()
					) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	
	public int insertManyAutoreply(final List<AutoreplyVO> list) {
			String sql = "INSERT INTO weixin.autoreply_config(brand_id,keyword_type,keyword,reply_type,reply_content,update_time,is_delete, event_type,specific_type) values (?,?,?,?,?,?,?,?,?);";
			int [] backSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					AutoreplyVO bean =  list.get(i);
					ps.setInt(1,bean.getBrandId());
					ps.setString(2,bean.getKeywordType());
					ps.setString(3, bean.getKeyword());
					ps.setString(4, bean.getReplyType());
					ps.setString(5, bean.getReplyContent());
					ps.setTimestamp(6, bean.getUpdateTime());
					ps.setBoolean(7, false);
					ps.setInt(8, bean.getEventType());
					ps.setInt(9, bean.getSpecificType());
				}
				public int getBatchSize() {
					return list.size();
				}
			});
			if (backSize!=null && backSize.length>0){
				return backSize.length;
			}
			return 0;
	}
	
	public boolean updateAutoreply(AutoreplyVO autoreply) {
		String sql = "update weixin.autoreply_config set reply_content=?";
		if (!StringUtil.isNullOrEmpty(autoreply.getReplyType())) {
			sql +=",reply_type='"+autoreply.getReplyType()+"'";
		}
		sql +=", update_time=? where id=?;";
		try {
			return jdbcTemplate.update(sql, autoreply.getReplyContent(),
					 autoreply.getUpdateTime(), autoreply.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteAutoreply(AutoreplyVO autoreply) {
		String sql = "update weixin.autoreply_config set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, autoreply.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteAutoreplyByBrandId(Integer brandId) {
		String sql = "update weixin.autoreply_config set is_delete=? where brand_id=?;";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	
	public AutoreplyVO getAutoreplyById(Integer id) {
		String sql = "select * from  weixin.autoreply_config where id=? and is_delete=false";
		List<AutoreplyVO> list = (List<AutoreplyVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<AutoreplyVO>(
				AutoreplyVO.class), id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<AutoreplyVO> getSettingAutoreply(Integer brandId) {
		String sql = "select * from  weixin.autoreply_config where brand_id=? and  is_delete=false and specific_type=1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AutoreplyVO>(AutoreplyVO.class), brandId);
	}
	
	public Map<String, Object> getSettingAutoreplyByMenuKey(Integer brandId, String keyWord) {
		String sql = "select img.image_name,img.reply_title,img.descripation from weixin.image_config img LEFT JOIN weixin.autoreply_config reply on (reply.id = img.autoreply_id)"
					+ "where reply.brand_id=? and  reply.is_delete=false and reply.specific_type=1 and reply.keyword=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,  brandId, keyWord);
		if (list !=null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}

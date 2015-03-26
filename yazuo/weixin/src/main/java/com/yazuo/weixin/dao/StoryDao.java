package com.yazuo.weixin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.Story;

@Repository
public class StoryDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	
	public boolean saveStory(Story story) {
		String sql = "INSERT INTO weixin.story(title, content, image_name, brand_id,is_delete) values (?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, story.getTitle(), story.getContent()
					,story.getImageName(),story.getBrandId(),story.isDelete()) == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}

	}
	
	public boolean updateStory(Story story) {
		String sql = "update weixin.story set title=?,content=?,image_name=?,  brand_id=?, is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, story.getTitle(),story.getContent(),
					story.getImageName(),story.getBrandId(),story.isDelete(),story.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	
	public Story getStoryByBrandId(Integer brandId) {
		String sql = "SELECT id, title, content, image_name, brand_id, is_Delete from weixin.story where brand_id=?";
		List<Story> storyList = (List<Story>) jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<Story>(Story.class),
				brandId);
		if (storyList != null && storyList.size() > 0) {
			return storyList.get(0);
		}
		return null;
	}
	
	/**根据brand_id获取故事*/
	public Story getByBrandId(Integer brandId) {
		String sql = "SELECT  title, content,id,brand_id,is_delete from weixin.story where brand_id=? and is_Delete=false order by id desc";
		List<Story> storyList = (List<Story>) jdbcTemplate.query(
				sql, new BeanPropertyRowMapper<Story>(Story.class),
				brandId);
		if (storyList != null && storyList.size() > 0) {
			return storyList.get(0);
		}
		return null;
	}
	
}

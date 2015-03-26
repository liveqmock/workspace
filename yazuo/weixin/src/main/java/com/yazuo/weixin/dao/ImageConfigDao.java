package com.yazuo.weixin.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.ImageConfigVO;

@Repository
public class ImageConfigDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	//添加图片
	public boolean insertImageConifg(ImageConfigVO imageConfig){
		String sql = "INSERT INTO weixin.image_config (brand_id,descripation,update_time,is_delete,image_name,reply_title,reply_url,autoreply_id) values (?,?,?,?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, imageConfig.getBrandId(),
					imageConfig.getDescripation(),imageConfig.getUpdateTime(),false,imageConfig.getImageName(),imageConfig.getReplyTitle(),imageConfig.getReplyUrl(),imageConfig.getAutoreplyId())!=-1 ? true :false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
		
	}
	//删除图片
	public boolean deleteImageConfig(ImageConfigVO imageConfig){
		String sql = "update weixin.image_config set is_delete = ? ,update_time = ? where  autoreply_id =? ";
		try {
			return jdbcTemplate.update(sql, true,imageConfig.getUpdateTime(),imageConfig.getAutoreplyId())!=-1 ? true :false;
					
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	//查询图片
	public List<ImageConfigVO> getImageConfigVOListByAutoply(AutoreplyVO autoreplyVO){
		String sql = "select * from weixin.image_config where  autoreply_id = ? and  is_delete = ? order by id asc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ImageConfigVO>(
				ImageConfigVO.class), autoreplyVO.getId(),false);
		
	}

}

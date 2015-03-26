package com.yazuo.weixin.dao;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.DishesVO;

@Repository
public class DishesDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public List<DishesVO> getDishesListByBrandId(Integer brandId) {
		String sql = "select * from  weixin.dishes where brand_id=? and  is_delete=false order by recommend_level desc";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<DishesVO>(
				DishesVO.class), brandId);
	}
	
	/**根据商家id、用户id取是否点过赞*/
	public List<Map<String, Object>> getUserPariseRecord (Integer brandId, String weixinId, Integer dishesId) {
		String sql = "select dishes_id from weixin.weixin_parise_record where merchant_id=? and weixin_id=?";
		if (dishesId !=null && dishesId !=0) {
			sql +=" and dishes_id=" + dishesId;
		}
		return jdbcTemplate.queryForList(sql, brandId, weixinId);
	}
	
	/**点完赞之后增加流水记录*/
	public int addPariseRecord (Integer merchant_id, Integer dishesId, String weixinId) {
		String sql = "INSERT INTO  weixin.weixin_parise_record(merchant_id,dishes_id,weixin_id,insert_time) VALUES (?,?,?,?)";
		return jdbcTemplate.update(sql, merchant_id, dishesId, weixinId, new Date());
	}

	
	public Map<String, Object> getDishesListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.dishes where brand_id=? and  is_delete=false order by recommend_level desc";
		Pagination<DishesVO> pagination = new Pagination<DishesVO>(sql, page,
				pagesize, jdbcTemplate, new BeanPropertyRowMapper<DishesVO>(
						DishesVO.class), brandId);
		return pagination.getResultMap();
	}

	public boolean insertDishes(DishesVO dishes) {
		String sql = "INSERT INTO weixin.dishes(brand_id,name,description,is_new,is_recommend,recommend_Level,is_delete) values (?,?,?,?,?,?,?);";
		try {
			return jdbcTemplate.update(sql, dishes.getBrandId(),
					dishes.getName(), dishes.getDescription(),
					dishes.getIsNew(), dishes.getIsRecommend(),
					dishes.getRecommendLevel(), false) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateDishes(DishesVO dishes) {
		String sql = "update weixin.dishes set name=?,description=?,is_new=?,is_recommend=? ,recommend_Level=? where id=?;";
		try {
			return jdbcTemplate.update(sql, dishes.getName(),
					dishes.getDescription(), dishes.getIsNew(),
					dishes.getIsRecommend(), dishes.getRecommendLevel(),
					dishes.getId()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteDishes(DishesVO dishes) {
		String sql = "update  weixin.dishes set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, dishes.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteDishesByBrandId(Integer brandId) {
		String sql = "update  weixin.dishes set is_delete=? where brand_id=?;";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
}

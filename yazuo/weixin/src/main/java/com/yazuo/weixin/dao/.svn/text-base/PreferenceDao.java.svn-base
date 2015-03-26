package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.PreferenceVO;

@Repository
public class PreferenceDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	Logger log = Logger.getLogger(this.getClass());

	public List<PreferenceVO> getPreferenceListByBrandId(Integer brandId) {
		String sql = "select * from  weixin.preference where brand_id=? and  is_delete=false order by  sort_number";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<PreferenceVO>(
				PreferenceVO.class), brandId);
	}

	public Map<String, Object> getPreferenceListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.preference where brand_id=? and  is_delete=false order by  sort_number";
		Pagination<PreferenceVO> pagination = new Pagination<PreferenceVO>(sql,
				page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<PreferenceVO>(PreferenceVO.class),
				brandId);
		return pagination.getResultMap();
	}

	public PreferenceVO getPreferenceById(Integer id) {
		String sql = "select * from  weixin.preference where id=?";
		return jdbcTemplate
				.query(sql,
						new BeanPropertyRowMapper<PreferenceVO>(
								PreferenceVO.class), id).get(0);
	}

	public boolean insertPreference(PreferenceVO preference) {
		String sql = "INSERT INTO weixin.preference(brand_id,title,content,is_new,is_recommend,is_delete, sort_number) values (?,?,?,?,?,?,?);";
		try {
			return jdbcTemplate.update(sql, preference.getBrandId(),
					preference.getTitle(), preference.getContent(),
					preference.getIsNew(), preference.getIsRecommend(), false, preference.getSortNumber()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updatePreference(PreferenceVO preference) {
		String sql = "update weixin.preference set title=?,content=?,is_new=?,is_recommend=?,sort_number=? where id=?;";
		try {
			return jdbcTemplate.update(sql, preference.getTitle(),
					preference.getContent(), preference.getIsNew(),
					preference.getIsRecommend(),preference.getSortNumber(), preference.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deletePreference(PreferenceVO preference) {
		String sql = "update  weixin.preference set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, preference.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePreferenceByBrandId(Integer brandId) {
		String sql = "update  weixin.preference set is_delete=? where brand_id=?;";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

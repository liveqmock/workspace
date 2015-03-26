package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.BusinessManagerVO;

@Repository
public class BusinessManagerDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public List<BusinessManagerVO> getBusinessManagerListByBrandId(
			Integer brandId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from  weixin.business_manager where brand_id=? and  is_delete=false order by id desc");
		return jdbcTemplate.query(sb.toString(),
				new BeanPropertyRowMapper<BusinessManagerVO>(
						BusinessManagerVO.class), brandId);
	}

	public Map<String, Object> getBusinessManagerListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.business_manager where brand_id=? and  is_delete=false order by id desc";
		Pagination<BusinessManagerVO> pagination = new Pagination<BusinessManagerVO>(
				sql, page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<BusinessManagerVO>(
						BusinessManagerVO.class), brandId);
		return pagination.getResultMap();
	}

	/**
	 * 返回所有包括删除的
	 * 
	 * @param businessManager
	 * @return
	 */
	public List<BusinessManagerVO> getBusinessManagerListByPhoneNo(
			BusinessManagerVO businessManager) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from  weixin.business_manager where phone_no=? and  is_delete=false");
		return jdbcTemplate.query(sb.toString(),
				new BeanPropertyRowMapper<BusinessManagerVO>(
						BusinessManagerVO.class), businessManager.getPhoneNo());
	}

	/**
	 * 返回未删除的，只有一条
	 * 
	 * @param businessManager
	 * @return
	 */
	public BusinessManagerVO getBusinessManagerByPhoneNo(
			BusinessManagerVO businessManager) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from  weixin.business_manager where phone_no=? and is_delete=false");

		List<BusinessManagerVO> businessManagerList = jdbcTemplate.query(sb
				.toString(), new BeanPropertyRowMapper<BusinessManagerVO>(
				BusinessManagerVO.class), businessManager.getPhoneNo());
		if (businessManagerList != null && businessManagerList.size() != 0) {
			return businessManagerList.get(0);
		}
		return null;
	}

	public BusinessManagerVO getBusinessManagerByWeixinId(
			BusinessManagerVO businessManager) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from  weixin.business_manager where weixin_id=? and is_delete=false");
		List<BusinessManagerVO> businessManagerList = jdbcTemplate.query(sb
				.toString(), new BeanPropertyRowMapper<BusinessManagerVO>(
				BusinessManagerVO.class), businessManager.getWeixinId());
		if (businessManagerList != null && businessManagerList.size() != 0) {
			return businessManagerList.get(0);
		}
		return null;
	}

	public boolean saveBusinessManager(BusinessManagerVO businessManager) {
		String sql = "INSERT INTO weixin.business_manager(phone_no,brand_id,create_time,is_delete,status) values (?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, businessManager.getPhoneNo(),
					businessManager.getBrandId(),
					businessManager.getCreateTime(), false,
					businessManager.getStatus()) == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}

	}

	public boolean deleteBusinessManager(BusinessManagerVO businessManager) {
		String sql = "update weixin.business_manager set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, businessManager.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateBusinessManager(BusinessManagerVO businessManager) {
		String sql = "update weixin.business_manager set weixin_id=?,last_update_time=? , status=?where id=?;";
		try {
			return jdbcTemplate.update(sql, businessManager.getWeixinId(),
					businessManager.getLastUpdateTime(),
					businessManager.getStatus(), businessManager.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteBusinessManagerByBrandId(Integer brandId) {
		String sql = "update  weixin.business_manager set is_delete=? where brand_id=?;";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.SubbranchVO;

@Repository
public class SubbranchDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public int importSubbranchInfo(BusinessVO business,
			List<SubbranchVO> subbranchList) {
		if (subbranchList == null) {
			return 0;
		}
		String sql = "update weixin.subbranch set is_delete = true where company_weibo_id=?;";
		int i = 0;
		try {
			jdbcTemplate.update(sql, business.getCompanyWeiboId());
			sql = "INSERT INTO weixin.subbranch(brand_id,name,address,phone_no,point_x,point_y,is_new,is_recommend,company_weibo_id,is_delete,order_id) values (?,?,?,?,?,?,?,?,?,?,?)";
			for (SubbranchVO subbranch : subbranchList) {
				jdbcTemplate.update(sql, business.getBrandId(), subbranch
						.getName(), subbranch.getAddress(), subbranch
						.getPhoneNo(), subbranch.getPointX(), subbranch
						.getPointY(), subbranch.getIsNew(), subbranch
						.getIsRecommend(), subbranch.getCompanyWeiboId()
						.toString(), subbranch.getIsDelete(), 0);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return -1;
		}
		return i;
	}

	public List<SubbranchVO> getSubbranchListByBrandId(Integer brandId) {
		String sql = "select * from  weixin.subbranch where brand_id=? and  is_delete=false order by  is_recommend desc ,order_id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<SubbranchVO>(
				SubbranchVO.class), brandId);
	}

	public Map<String, Object> getSubbranchListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.subbranch where brand_id=? and  is_delete=false order by  is_recommend desc ,order_id";
		Pagination<SubbranchVO> pagination = new Pagination<SubbranchVO>(sql,
				page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<SubbranchVO>(SubbranchVO.class),
				brandId);
		return pagination.getResultMap();
	}

	public SubbranchVO getNearestSubbranch(Integer brandId, Double x, Double y) {
		String sql = "SELECT * FROM weixin.subbranch  where brand_id=? and  is_delete=false ORDER BY (((point_x-?)^2)+((point_y-?)^2))";
		List<SubbranchVO> subbranchList = (List<SubbranchVO>) jdbcTemplate
				.query(sql, new BeanPropertyRowMapper<SubbranchVO>(
						SubbranchVO.class), brandId, y, x);
		if (subbranchList != null && subbranchList.size() != 0) {
			return subbranchList.get(0);
		} else {
			return null;
		}
	}

	public boolean insertSubbranch(SubbranchVO subbranch) {
		String sql = "INSERT INTO weixin.subbranch(brand_id,name,address,phone_no,point_x,point_y,is_new,is_recommend,company_weibo_id,is_delete,order_id) values (?,?,?,?,?,?,?,?,?,?,?);";
		try {
			return jdbcTemplate.update(sql, subbranch.getBrandId(),
					subbranch.getName(), subbranch.getAddress(),
					subbranch.getPhoneNo(), subbranch.getPointX(),
					subbranch.getPointY(), subbranch.getIsNew(),
					subbranch.getIsRecommend(), subbranch.getCompanyWeiboId(),
					false, 0) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateSubbranch(SubbranchVO subbranch) {
		String sql = "update weixin.subbranch set name=?,address=?,phone_no=?,point_x=?,point_y=?,is_new=?,is_recommend=?,order_id=? where id=?;";
		try {
			return jdbcTemplate.update(sql, subbranch.getName(),
					subbranch.getAddress(), subbranch.getPhoneNo(),
					subbranch.getPointX(), subbranch.getPointY(),
					subbranch.getIsNew(), subbranch.getIsRecommend(),
					subbranch.getOrderId(), subbranch.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteSubbranch(SubbranchVO subbranch) {
		String sql = "update weixin.subbranch set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, subbranch.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteSubbranchByBrandId(Integer brandId) {
		String sql = "update weixin.subbranch set is_delete=? where brand_id=?;";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
}

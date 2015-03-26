package com.yazuo.weixin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.ActivityRecordVO;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BrandAndKeyRelativeVO;

@Repository
public class BrandAndKeyRelativeDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	/**判断此商户和key在数据库是否存在*/
	public long judgeRelativeExist(Integer brandId, String key) {
		String sql = "select count(*) from  weixin.weixin_merchant_token_key where brand_id=? and token_key=? and status=true";
		return jdbcTemplate.queryForLong(sql,brandId, key);
	}

	/**根据id获取信息*/
	public BrandAndKeyRelativeVO getRelativeById(Integer relativeId) {
		String sql = "select * from  weixin.weixin_merchant_token_key where id=? ";
		List<BrandAndKeyRelativeVO> activityRecordList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BrandAndKeyRelativeVO>(
						BrandAndKeyRelativeVO.class), relativeId);
		if (activityRecordList == null || activityRecordList.size() == 0) {
			return null;
		} else
			return activityRecordList.get(0);
	}
	/**保存商户和key关联关系*/
	public boolean saveRelative(BrandAndKeyRelativeVO relativeVO) {
		String sql = "INSERT INTO weixin.weixin_merchant_token_key(brand_id,token_key,status,create_time,update_time) values (?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, relativeVO.getBrandId(), relativeVO.getTokenKey(), relativeVO.isStatus(), new Date(), new Date()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	/**修改商户与key关联关系*/
	public boolean updateActivityRecord(BrandAndKeyRelativeVO relativeVO) {
		String sql = "update weixin.weixin_merchant_token_key set token_key=?,status=?, update_time=? where id=?";
		try {
			return jdbcTemplate.update(sql, relativeVO.getTokenKey(),
					relativeVO.isStatus(), new Date(),relativeVO.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	
	/**根据brandId取关联关系*/
	public BrandAndKeyRelativeVO getRelativeByBrandId(Integer brandId) {
		String sql = "select * from  weixin.weixin_merchant_token_key where brand_id=?";
		List<BrandAndKeyRelativeVO> list = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<BrandAndKeyRelativeVO>(
						BrandAndKeyRelativeVO.class), brandId);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}

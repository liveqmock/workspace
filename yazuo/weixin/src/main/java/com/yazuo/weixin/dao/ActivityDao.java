package com.yazuo.weixin.dao;

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

@Repository
public class ActivityDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public List<ActivityConfigVO> getActivityConfigListByBrandId(Integer brandId) {
		String sql = "select * from  weixin.activity_Config where brand_id=? and  is_delete=false";
		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityConfigVO>(
						ActivityConfigVO.class), brandId);
	}

	public List<ActivityConfigVO> getEffectiveActivityConfigListByBrandId(
			Integer brandId) {
		String sql = "select * from  weixin.activity_Config where brand_id=? and start_time<now() and end_time>now() and is_delete=false";
		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityConfigVO>(
						ActivityConfigVO.class), brandId);
	}

	public List<ActivityRecordVO> getActivityRecordListByBrandIdAndWeixinId(
			ActivityRecordVO activityRecord) {
		String sql = "select * from  weixin.activity_record where brand_id=? and weixin_id=? order by id desc";
		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityRecordVO>(
						ActivityRecordVO.class), activityRecord.getBrandId(),
				activityRecord.getWeixinId());
	}

	public List<ActivityRecordVO> getActivityRecordListByConfigIdAndWeixinId(
			Integer brandId, String weixinId) {
		String sql = "select * from  weixin.activity_record where activity_config_id=? and weixin_id=? order by id desc";
		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityRecordVO>(
						ActivityRecordVO.class), brandId, weixinId);
	}

	public ActivityRecordVO getLastActivityRecordByBrandIdAndWeixinId(
			ActivityRecordVO activityRecord) {
		String sql = "select * from  weixin.activity_record where brand_id=? and weixin_id=? order by id desc";
		List<ActivityRecordVO> activityRecordList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityRecordVO>(
						ActivityRecordVO.class), activityRecord.getBrandId(),
				activityRecord.getWeixinId());
		if (activityRecordList == null || activityRecordList.size() == 0) {
			return null;
		} else
			return activityRecordList.get(0);
	}

	public ActivityRecordVO getActivityRecordById(
			ActivityRecordVO activityRecord) {
		String sql = "select * from  weixin.activity_record where id=? ";
		List<ActivityRecordVO> activityRecordList = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityRecordVO>(
						ActivityRecordVO.class), activityRecord.getId());
		if (activityRecordList == null || activityRecordList.size() == 0) {
			return null;
		} else
			return activityRecordList.get(0);
	}

	public Map<String, Object> getActivityConfigListByBrandId(Integer brandId,
			int page, int pagesize) {
		String sql = "select * from  weixin.activity_Config where brand_id=? and  is_delete=false";
		Pagination<AutoreplyVO> pagination = new Pagination<AutoreplyVO>(sql,
				page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<AutoreplyVO>(AutoreplyVO.class),
				brandId);
		return pagination.getResultMap();
	}

	public boolean insertActivityConfig(ActivityConfigVO activityConfig) {
		String sql = "INSERT INTO weixin.activity_Config(brand_id,name,probability,start_time,end_time,is_delete,insert_time,activity_id,time_unit,frequency,count_limit,award_count,reply_content) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, activityConfig.getBrandId(),
					activityConfig.getName(), activityConfig.getProbability(),
					activityConfig.getStartTime(), activityConfig.getEndTime(),
					false, activityConfig.getInsertTime(),
					activityConfig.getActivityId(),
					activityConfig.getTimeUnit(),
					activityConfig.getFrequency(),
					activityConfig.getCountLimit(),
					activityConfig.getAwardCount(),
					activityConfig.getReplyContent()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean insertActivityRecord(ActivityRecordVO activityRecord) {
		String sql = "INSERT INTO weixin.activity_record(activity_id,is_done,operate_time,coupon_id,brand_id,name,weixin_id,activity_config_id,start_time,end_time,is_award) values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, activityRecord.getActivityId(),
					activityRecord.getIsDone(),
					activityRecord.getOperateTime(),
					activityRecord.getCouponId(), activityRecord.getBrandId(),
					activityRecord.getName(), activityRecord.getWeixinId(),
					activityRecord.getActivityConfigId(),
					activityRecord.getStartTime(), activityRecord.getEndTime(),
					activityRecord.getIsAward()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean addAwardCount(ActivityRecordVO activityRecord) {
		String sql = "update weixin.activity_config set award_count=award_count+1 where id=?";
		try {
			jdbcTemplate.update(sql, activityRecord.getActivityConfigId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateActivityRecord(ActivityRecordVO activityRecord) {
		String sql = "update weixin.activity_record set is_done=?,operate_time=? where id=?";
		try {
			return jdbcTemplate.update(sql, activityRecord.getIsDone(),
					activityRecord.getOperateTime(), activityRecord.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteActivityConfig(ActivityConfigVO activityConfig) {
		String sql = "update weixin.activity_Config set is_delete=? where id=?";
		try {
			return jdbcTemplate.update(sql, true, activityConfig.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteActivityConfigByBrandId(Integer brandId) {
		String sql = "update weixin.activity_Config set is_delete=? where brand_id=?";
		try {
			return jdbcTemplate.update(sql, true, brandId) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateActivityConfig(ActivityConfigVO activityConfig) {
		String sql = "update weixin.activity_Config set name=?,probability=?,start_time=?,end_time=? ,activity_id=? ,time_unit=?,frequency=?,count_limit=?,reply_content=? where id=?";
		try {
			return jdbcTemplate.update(sql, activityConfig.getName(),
					activityConfig.getProbability(),
					activityConfig.getStartTime(), activityConfig.getEndTime(),
					activityConfig.getActivityId(),
					activityConfig.getTimeUnit(),
					activityConfig.getFrequency(),
					activityConfig.getCountLimit(),
					activityConfig.getReplyContent(), activityConfig.getId()) != -1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	 public int getActivityRecordCountByBrandIdAndWeixinId(
	 ActivityRecordVO activityRecord) {
	 if (activityRecord.getStartTime() == null) {
	 String sql =
	 "select count(*) from  weixin.activity_record where brand_id=? and weixin_id=? and activity_config_id=? and and is_done=?";
	 return jdbcTemplate.queryForInt(sql, activityRecord.getBrandId(),
	 activityRecord.getWeixinId(),
	 activityRecord.getActivityConfigId(),true);
	 } else {
	 String sql =
	 "select count(*) from  weixin.activity_record where brand_id=? and weixin_id=? and activity_config_id=? and is_done=? and operate_time > ? and operate_time < ?";
	 return jdbcTemplate.queryForInt(sql, activityRecord.getBrandId(),
	 activityRecord.getWeixinId(),
	 activityRecord.getActivityConfigId(),true,
	 activityRecord.getStartTime(), activityRecord.getEndTime());
	 }
	
	 }
//	public int getActivityRecordCountByBrandIdAndWeixinId(
//			ActivityRecordVO activityRecord) {
//		if (activityRecord.getStartTime() == null) {
//			String sql = "select count(*) from  weixin.activity_record where brand_id=? and weixin_id=? and activity_config_id=? and is_done=? ";
//			return jdbcTemplate.queryForInt(sql, activityRecord.getBrandId(),
//					activityRecord.getWeixinId(),
//					activityRecord.getActivityConfigId(), true);
//		} else {
//			String sql = "select count(*) from  weixin.activity_record where brand_id=? and weixin_id=? and activity_config_id=? and is_done=?  and operate_time > ? and operate_time < ?";
//			return jdbcTemplate.queryForInt(sql, activityRecord.getBrandId(),
//					activityRecord.getWeixinId(),
//					activityRecord.getActivityConfigId(), true,
//					activityRecord.getStartTime(), activityRecord.getEndTime());
//		}
//
//	}

	// 根据传过来的商家ID和 具体的ActivityConfigId很有可能商家填写的活动Id是别的商户的
	public List<ActivityConfigVO> getEffectiveActivityConfigListByActivityConfigID(
			Integer[] activityCofingIDS, Integer brandId) {
		String str = "";
		for (int i = 0; i < activityCofingIDS.length; i++) {
			if (i == activityCofingIDS.length - 1) {
				str += activityCofingIDS[i].toString();
			} else {
				str += activityCofingIDS[i].toString() + ",";
			}
		}
		String sql = "select * from weixin.activity_Config where id in ("
				+ str
				+ ") and brand_id=? and start_time<now() and end_time>now() and is_delete=false ";
		return jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ActivityConfigVO>(
						ActivityConfigVO.class), brandId);
	}
	

	public int getMinFrequencyByActivityConfigID(Integer[] activityCofingIDS,
			Integer brandId) {
		String str = "";
		for (int i = 0; i < activityCofingIDS.length; i++) {
			if (i == activityCofingIDS.length - 1) {
				str += activityCofingIDS[i].toString();
			} else {
				str += activityCofingIDS[i].toString() + ",";
			}
		}
		String sql = "select min(frequency)  from weixin.activity_Config where id in ("
				+ str
				+ ") and brand_id=? and start_time<now() and end_time>now() and is_delete=false ";
		return jdbcTemplate.queryForInt(sql, brandId);
	}
	public ActivityConfigVO getActivityConfigVOByActivityConfigId(Integer ActivityConfigID,Integer brandId){
	String sql = "select * from  weixin.activity_Config where id = "+ActivityConfigID.intValue() + "and brand_id = ? and start_time<now() and end_time>now() and is_delete=false";
	 List<ActivityConfigVO>  activitylist=  jdbcTemplate.query(sql,
			new BeanPropertyRowMapper<ActivityConfigVO>(
					ActivityConfigVO.class), brandId);
	 if(activitylist==null||activitylist.size()==0){
		 return null;
	 }
	 return  activitylist.get(0);
	}

}

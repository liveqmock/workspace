package com.yazuo.weixin.dao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *门店wifi数据库相关操作
 * @author kyy
 * @date 2014-10-22 上午11:03:41
 */
@Repository
public class MerchantWifiDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	
	/**添加短信发送记录*/
	public boolean addSmsRecord(Integer brandId, String phoneNo) {
		String sql = "insert into weixin.wifi_sms_record(brand_id,phone_no,insert_time) values (?,?,now())";
		int count = jdbcTemplate.update(sql, brandId, phoneNo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**判断今天是否已发过短信*/
	public boolean isSendSmsOfToday(Integer brandId, String phoneNo, String todayDate) {
		String sql = "select count(*) from weixin.wifi_sms_record where brand_id=? and phone_no=? and insert_time " +
				"between '"+todayDate+" 00:00:00' and '"+todayDate+" 23:59:59'";
		long count = jdbcTemplate.queryForLong(sql, brandId, phoneNo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**删除短信记录*/
	public boolean delSmsRecord(String todayDate) {
		String sql = "delete from weixin.wifi_sms_record where insert_time < '"+todayDate+" 00:00:00'";
		int count = jdbcTemplate.update(sql);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**添加重置密码记录*/
	public boolean addResetPwdRecord(Integer brandId, String cardNo) {
		String sql = "insert into weixin.reset_pwd_record(brand_id,card_no, insert_time) values (?,?,now())";
		int count = jdbcTemplate.update(sql, brandId, cardNo);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**判断今天重置密码的次数是否超过3次*/
	public long resetPwdCountOfToday(Integer brandId, String cardNo, String todayDate) {
		String sql = "select count(*) from weixin.reset_pwd_record where brand_id=? and card_no=? and insert_time " +
				"between '"+todayDate+" 00:00:00' and '"+todayDate+" 23:59:59'";
		return jdbcTemplate.queryForLong(sql, brandId, cardNo);
	}
}

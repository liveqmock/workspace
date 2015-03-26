package com.yazuo.weixin.dao;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* @ClassName WeixinExternalDao
* @Description 对外接口dao类
* @author sundongfeng@yazuo.com
* @date 2014-6-30 上午11:16:23
* @version 1.0
*/
@Repository
public class WeixinExternalDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger("external");
	
	/**
	 * 根据手机号和brandid 查询用户的openid
	 * @param mobile
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	public String queryOpenId(String mobile,String brandId)throws Exception{
		String openId="";
		try{
			String sql ="select weixin_id from weixin.membership  where brand_id=? and phone_no=? limit 1";
			log.info("WeixinExternalDao.queryOpenId.sql:"+sql+"\t mobile:"+mobile+"\t brandid:"+brandId);
			openId = jdbcTemplate.queryForObject(sql, new Object[]{Integer.parseInt(brandId),mobile},String.class	);
		}catch(Exception ex){
			log.error("查询异常",ex);
			openId="";
		}
		return openId;
	}
}

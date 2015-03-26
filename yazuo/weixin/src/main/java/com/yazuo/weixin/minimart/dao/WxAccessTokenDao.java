package com.yazuo.weixin.minimart.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxAccessToken;

 
/**
* @ClassName WxAccessTokenDao
* @Description 微信accessToken
* @author sundongfeng@yazuo.com
* @date 2014-9-8 上午11:35:13
* @version 1.0
*/
@Repository
public class WxAccessTokenDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	/**
	 * 查询access_token
	 * @param brandid
	 * @return
	 */
	public WxAccessToken queryAccessToken(Integer brandid){
		String sql = "select id,access_token,expires_in,create_time,brand_id" +
				" from public.weixin_access_token where brand_id="+brandid+" ORDER BY create_time desc limit 1";
		log.info("查询access_token_sql:"+sql	);
		try {
			WxAccessToken obj = jdbc.queryForObject(sql,new BeanPropertyRowMapper<WxAccessToken>(WxAccessToken.class));
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 插入access_token
	 * @param dict 
	 * @return
	 */
	public boolean insertAccessToken(WxAccessToken dict){
		String sql="insert into public.weixin_access_token (access_token,expires_in,"+ 
				"create_time, brand_id,create_date ) values( ?,?,?,?,CURRENT_TIMESTAMP)";
		int num  =jdbc.update(sql, dict.getAccessToken(),dict.getExpiresIn(),dict.getCreateTime(),dict.getBrandId());
		log.info("插入access_token_sql:"+sql	+" num:"+num+" brandid:"+dict.getBrandId());
		return num>0?true:false;
	}
	/**
	 * 更新access_token
	 * @param dict 
	 * @return
	 */
	public boolean updateAccessToken(WxAccessToken dict){
		String sql="update public.weixin_access_token set access_token=?,create_time=?,create_date=CURRENT_TIMESTAMP where brand_id=?";
		int num  =jdbc.update(sql, dict.getAccessToken(),dict.getCreateTime(),dict.getBrandId());
		log.info("更新access_token_sql:"+sql	+" num:"+num+" brandId:"+dict.getBrandId());
		return num>0?true:false;
	}
	
	public boolean deleteAccessToken(Integer brandid){
		String sql ="delete from public.weixin_access_token where brand_id="+brandid;
		int num  =jdbc.update(sql);
		log.info("删除access_token_sql:"+sql+" num:"+num+" brandid:"+brandid);
		return num>0?true:false;
	}
	
	public boolean updateTokenExpireTime(Integer brandid,Integer expiresin){
		String sql="update public.weixin_access_token set expires_in="+expiresin+",create_time="+System.currentTimeMillis()+",create_date=CURRENT_TIMESTAMP where brand_id="+brandid;
		int num  =jdbc.update(sql);
		log.info("updateTokenExpireTime.sql:"+sql+" num:"+num+" brandid:"+brandid);
		return num>0?true:false;
	}
	
}

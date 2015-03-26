package com.yazuo.weixin.minimart.dao;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;

/**
* @ClassName WxMallMerchantDictDao
* @Description 微信商城商户参数
* @author sundongfeng@yazuo.com
* @date 2014-8-6 上午9:01:10
* @version 1.0
*/
@Repository
public class WxMallMerchantDictDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	/**
	 * 查询商家参数
	 * @param brandid
	 * @return
	 */
	public WxMallMerchantDict queryMerchantParam(Integer brandid){
		String sql = "select id,app_id,app_secret,app_key,partner_id,partner_key,brand_id," +
				"mails,subject,contents,partner_pass,certificate_url,page_picurl,page_color," +
				"login_name,login_pwd,is_certification,is_open_payment,v2_v3 " +
				" from public.weixin_mall_merchant_dict where brand_id="+brandid;
		log.info("sql:"+sql	);
		try{
			WxMallMerchantDict obj = jdbc.queryForObject(sql,new BeanPropertyRowMapper<WxMallMerchantDict>(WxMallMerchantDict.class));
			return obj;
		}catch(Exception e){
			log.info("报错",e);
			log.info("brandId["+brandid+"]不存在!");
			return null;
		}
	}
	/**
	 * 插入商家参数
	 * @param dict 
	 * @return
	 */
	public boolean insertMerchantParam(WxMallMerchantDict dict){
		String sql="insert into public.weixin_mall_merchant_dict " +
				"(app_id,app_secret,app_key, partner_id, partner_key,brand_id," +
				"create_time,mails,subject,contents,login_name,login_pwd,is_certification,is_open_payment,v2_v3)" +
				" values( ?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?, ?,?,?,?,? )";
		int num  =jdbc.update(sql, dict.getAppId(),dict.getAppSecret(),dict.getAppKey(),dict.getPartnerId(),
				dict.getPartnerKey(),dict.getBrandId(),dict.getMails(),dict.getSubject(),dict.getContents(),
				dict.getLoginName(),dict.getLoginPwd(),dict.getIsCertification(),dict.getIsOpenPayment(),dict.getV2_v3());
		return num>0?true:false;
	}
	/**
	 * 更新参数
	 * @param dict
	 * @return
	 */
	public int updateMerchantParam(WxMallMerchantDict dict){
		String sql = "update public.weixin_mall_merchant_dict set app_id=?,app_secret=?," +
				"login_name=?,login_pwd=?,is_certification=?,is_open_payment=?,v2_v3=?" +
				" where brand_id=?";
		log.info("sql:"+sql+" params:"+dict.getAppId()+" ,"+dict.getAppSecret()+","+dict.getBrandId());
		int num = jdbc.update(sql, dict.getAppId(),dict.getAppSecret(),
				dict.getLoginName(),dict.getLoginPwd(),dict.getIsCertification(),
				dict.getIsOpenPayment(),dict.getV2_v3(),dict.getBrandId());
		return num;
	}
	
	public int queryCount(Integer brandid){
		String sql = "select count(*) from public.weixin_mall_merchant_dict  where brand_id="+brandid;
		int num = jdbc.queryForInt(sql);
		return num;
	}
}

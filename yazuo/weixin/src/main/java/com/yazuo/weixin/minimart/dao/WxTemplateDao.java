package com.yazuo.weixin.minimart.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.minimart.vo.WxTemplate;


/**
* @ClassName WxTemplateDao
* @Description 微信消息模版dao
* @author sundongfeng@yazuo.com
* @date 2014-9-15 下午10:02:00
* @version 1.0
*/
@Repository
public class WxTemplateDao {
	private static final Log log = LogFactory.getLog("mall");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbc;
	/**
	 * 查询商家参数
	 * @param brandid
	 * @return
	 */
	public List<Map<String,Object>> queryTemplateParam(Integer brandid){
		String sql = "select  name,template_id,type from public.weixin_template_message where brand_id="+brandid;
		log.info("sql:"+sql	);
		List<Map<String,Object>> obj = jdbc.queryForList(sql);
		return obj;
	}
	/**
	 * 插入模版参数
	 * @param dict 
	 * @return
	 */
	public boolean insertTemplateMessage(WxTemplate dict){
		String sql="insert into public.weixin_template_message (name,template_id,type,brand_id,create_time) values( ?,?,?,? ,CURRENT_TIMESTAMP )";
		int num  =jdbc.update(sql,  dict.getName(),dict.getTemplateId(),dict.getType(),dict.getBrandId());
		return num>0?true:false;
	}
	/**
	 * 删除模版
	 * @param vo
	 * @return
	 */
	public int deleteTemplate(Integer brandid){
		String sql ="delete from public.weixin_template_message where brand_id="+brandid;
		int del = jdbc.update(sql);
		return del;
	}
}

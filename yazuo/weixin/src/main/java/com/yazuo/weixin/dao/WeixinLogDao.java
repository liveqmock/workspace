package com.yazuo.weixin.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
* @ClassName WeixinLogDao
* @Description 日志分析
* @author sundongfeng@yazuo.com
* @date 2014-7-2 下午2:27:48
* @version 1.0
*/
@Repository
public class WeixinLogDao {
	private static final Log log = LogFactory.getLog("wxpay");
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 插入或更新日志
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertOrUpdateLog(Map<String,?> map) throws Exception{
		String querysql = "select count(1) cn from weixin.weixin_log_analysis where logdate=to_date(?, 'YYYY-MM-dd')";
		
		int cn = jdbcTemplate.queryForInt(querysql,(String)map.get("g"));
		if(cn>0){
			String upSql = "update weixin.weixin_log_analysis set gorecharge=?,goeachpage=?,goeachpaypage=?,checkandget=?," +
					" payresult=?,gopaysuccesspage=? where logdate=to_date(?, 'YYYY-MM-dd')";
			return jdbcTemplate.update(upSql, map.get("a"),map.get("b"),map.get("c"),map.get("d"),map.get("e"),map.get("f"),(String)map.get("g"));
		}else{
			String sql =   "insert into weixin.weixin_log_analysis ( gorecharge, goeachpage,  "+
					   "  goeachpaypage, checkandget, payresult, gopaysuccesspage, logdate) "+
					   "values (?,?,?,?,?,?,?) ";
			return jdbcTemplate.update(sql, map.get("a"),map.get("b"),map.get("c"),map.get("d"),map.get("e"),map.get("f"),sdf.parse((String)map.get("g")));
		}
	}
	/**
	 * 查询日志
	 * @param beginDate
	 * @param endDate
	 * @return 日志列表
	 */
	public List<Map<String,Object>> queryLog(String beginDate,String endDate)throws Exception{
		String sql = "select gorecharge, goeachpage, goeachpaypage, checkandget, payresult, gopaysuccesspage, "+
					" to_char(logdate, 'YYYY-MM-dd') logdate FROM " +
					" (select  wl.*,row_number() over(PARTITION by logdate order by logdate DESC) n from weixin.weixin_log_analysis wl " +
					" where  logdate BETWEEN to_date(?, 'YYYY-MM-dd') and to_date(?, 'YYYY-MM-dd')" +
					" ) tt where tt.n=1";
		log.info("sql:"+sql+" "+beginDate+" "+endDate);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, beginDate,endDate);
		return list;
	}
	/**
	 * 查询商城日志
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public synchronized int insertOrUpdateMallLog(Map<String,?> map) throws Exception{
		log.info("date:"+map.get("m"));
		String querysql = "select count(1) cn from weixin.weixin_mall_log_analysis where brandId=? and logdate=to_date(?, 'YYYY-MM-dd')";
		
		int cn = jdbcTemplate.queryForInt(querysql,map.get("j"),(String)map.get("m"));
		if(cn>0){
			String upSql = "update weixin.weixin_mall_log_analysis set mallmartindex=?,goodinfopage=?,buygood=?,integralpay=?," +
					" jifenxiaofei=?,obtaingoodinfo=?,mallpayresult=?,gomallsuccesspage=? where brandid=? and logdate=to_date(?, 'YYYY-MM-dd')";
			return jdbcTemplate.update(upSql, map.get("a"),map.get("b"),map.get("c"),map.get("d"),map.get("e"),map.get("f"),map.get("g"),map.get("h"),map.get("j"),(String)map.get("m"));
		}else{
			String sql =  "insert into weixin.weixin_mall_log_analysis (mallmartindex, goodinfopage,"+
					"  buygood, integralpay, jifenxiaofei, obtaingoodinfo, mallpayresult ,gomallsuccesspage ,brandid ,logdate) "+
					" values (?,?,?,?,?,?,?,?,?,?) ";
			return jdbcTemplate.update(sql, map.get("a"),map.get("b"),map.get("c"),map.get("d"),map.get("e"),map.get("f"),map.get("g"),map.get("h"),map.get("j"),sdf.parse((String)map.get("m")));
		}
	}
	/*查询商城日志*/
	public List<Map<String,Object>> queryMallLog(String beginDate,String endDate,String brandId)throws Exception{
		String sql = "select mallmartindex , goodinfopage, buygood, integralpay, jifenxiaofei, obtaingoodinfo,mallpayresult,gomallsuccesspage,brandid, "+
					" to_char(logdate, 'YYYY-MM-dd') logdate FROM " +
					" (select  wl.*,row_number() over(PARTITION by logdate order by logdate DESC) n from weixin.weixin_mall_log_analysis wl " +
					" where 1=1 " ;
					if(StringUtils.isNotBlank(brandId)){
						sql+=" and brandId="+brandId;
					}
					sql+=" and logdate BETWEEN to_date(?, 'YYYY-MM-dd') and to_date(?, 'YYYY-MM-dd')" +
					" ) tt where tt.n=1";
		log.info("sql:"+sql+" "+beginDate+" "+endDate);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, beginDate,endDate);
		return list;
	}
	public List<Map<String,Object>> queryMallBrand(){
		String sql = "SELECT DISTINCT(aa.brandid) brandId,bb.title" +
				" FROM weixin.weixin_mall_log_analysis aa LEFT JOIN weixin.business bb on aa.brandid=bb.brand_id" +
				" WHERE aa.brandid IS NOT NULL";
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql);
		return list;
	}
}

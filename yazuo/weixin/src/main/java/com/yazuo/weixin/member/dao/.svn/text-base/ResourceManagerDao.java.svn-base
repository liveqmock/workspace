package com.yazuo.weixin.member.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.member.vo.MerchantSettingResourceVo;
import com.yazuo.weixin.member.vo.WeixinSettingResourceVo;
import com.yazuo.weixin.util.StringUtil;

@Repository
public class ResourceManagerDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	private static final Log LOG = LogFactory.getLog("weixin");

	/**保存设置资源数据*/
	public int saveResource(WeixinSettingResourceVo resource) {
		String sql = "INSERT INTO weixin.weixin_setting_resource(name,type,is_enable,description,insert_time,update_time, resource_url,resource_source) values(?,?,?,?,now(),now(),?,?)";
		return jdbcTemplate.update(sql, resource.getName(), resource.getType(), resource.getIsEnable(), resource.getDescription(), resource.getResourceUrl(), resource.getResourceSource());
	}
	
	/**保存设置资源数据与商户关系*/
	public int saveResourceMerchant(final List<MerchantSettingResourceVo> list) {
		String sql = "insert into weixin.merchant_setting_resource (brand_id,resource_id,insert_time, data_source)" +
				" values (?,?,CURRENT_TIMESTAMP,?)";
		int [] backSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MerchantSettingResourceVo bean =  list.get(i);
				ps.setInt(1,bean.getBrandId());
				ps.setInt(2,bean.getResourceId());
				ps.setInt(3, bean.getDataSource());
			}
			public int getBatchSize() {
				return list.size();
			}
		});
		if (backSize!=null && backSize.length>0){
			return backSize.length;
		}
		return 0;
	}
	
	/**取设置资源数据*/
	public List<WeixinSettingResourceVo> getAllResource(boolean isResourceList) {
		String sql = "select * from weixin.weixin_setting_resource where 1=1";
		if (!isResourceList) { // 取商户能选的资源数据
			sql+=" and is_enable=true";
		}
		return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<WeixinSettingResourceVo>(WeixinSettingResourceVo.class));
	}
	
	/**取某个商户已选的资源*/
	public List<Map<String, Object>> getResourceByBrandId(Integer brandId) {
		String sql = "select * from weixin.merchant_setting_resource where brand_id=?";
		return jdbcTemplate.queryForList(sql, brandId);
	}
	
	/**删除某个商户选的资源*/
	public int deleteResourceByBrandId(Integer brandId) {
		String sql = "delete from weixin.merchant_setting_resource where brand_id=?";
		return jdbcTemplate.update(sql, brandId);
	}
	
	/**查询某个商户是否有wifi设置的资源*/
	public boolean judgeWifiResourceByBrandId(MerchantSettingResourceVo msr) {
		String sql = "select count(*) from weixin.merchant_setting_resource where brand_id=? and data_source=?";
		long count = jdbcTemplate.queryForLong(sql, msr.getBrandId(), msr.getDataSource());
		if (count > 0) { // 存在
			return true;
		}
		return false;
	}
	
	/**删除wifi设置的资源*/
	public int deleteWifiResourceByBrandId(MerchantSettingResourceVo msr) {
		String sql = "delete from weixin.merchant_setting_resource where brand_id=? and data_source=?";
		return jdbcTemplate.update(sql, msr.getBrandId(), msr.getDataSource());
	}
	
	/**修改设置资源信息*/
	public int updateSettingResource(WeixinSettingResourceVo resource){
		String sql = "update weixin.weixin_setting_resource set name=?,type=?,is_enable=?,description=?, update_time=now(),resource_url=?,resource_source=? where id=?";
		return jdbcTemplate.update(sql, resource.getName(), resource.getType(), resource.getIsEnable(), resource.getDescription(),resource.getResourceUrl(),resource.getResourceSource(), resource.getId());
	}
	
	/**根据id取设置资源*/
	public WeixinSettingResourceVo getResourceById(Integer id) {
		String sql = "select * from weixin.weixin_setting_resource where id=?";
		List<WeixinSettingResourceVo> list = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<WeixinSettingResourceVo>(WeixinSettingResourceVo.class));
		if (list !=null && list.size() >0) {
			return list.get(0);
		}
		return null;
	}
	
	/**根据类型值判断是否存在*/
	public Map<String, Object> getExistsCount(Integer typeValue) {
		String sql = "select id from weixin.weixin_setting_resource where type=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, typeValue);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	/**判断某个商户是否存在资源中的某一种*/
	public long judgeHasResourceOfBrand(Integer brandId, Integer typeValue) {
		String sql = "select count(*) from weixin.weixin_setting_resource wsr INNER JOIN weixin.merchant_setting_resource msr on (wsr.id = msr.resource_id) " +
				"where wsr.type=? and msr.brand_id=? and wsr.is_enable=true;";
		return jdbcTemplate.queryForLong(sql, typeValue, brandId);
	}
	
	/**取某个商户配置的功能*/
	public List<Map<String, Object>> getResourceOfBrandId(Integer brandId, String resourceSource) {
		String sql = "select wsr.* from weixin.weixin_setting_resource wsr INNER JOIN weixin.merchant_setting_resource msr on (wsr.id = msr.resource_id) " +
				"where msr.brand_id=? and wsr.is_enable=true";
		if (!StringUtil.isNullOrEmpty(resourceSource)) {
			sql += " and resource_source='"+resourceSource+"'";
		}
		sql += " order by id";
		return jdbcTemplate.queryForList(sql, brandId);
	}
	
	
	/**判断某个商户是否存在会员权益的权限*/
	public boolean judgeHasMemberRightOfBrand(Integer brandId, Integer resourceId) {
		String sql = "select count(*) from weixin.weixin_setting_resource wsr INNER JOIN weixin.merchant_setting_resource msr on (wsr.id = msr.resource_id) " +
				"where msr.brand_id=? and wsr.is_enable=true and msr.resource_id=?";
		long count = jdbcTemplate.queryForLong(sql, brandId, resourceId);
		if (count > 0) {
			return true;
		} 
		return false;
	}
}

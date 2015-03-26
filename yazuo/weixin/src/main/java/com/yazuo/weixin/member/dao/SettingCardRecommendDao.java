package com.yazuo.weixin.member.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.member.vo.SettingCardRecommendVo;

@Repository
public class SettingCardRecommendDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**保存设置的卡类型*/
	public int saveSettingCard(final List<SettingCardRecommendVo> list) {
		String sql = "insert into weixin.setting_card_recommend (brand_id,card_type_id,insert_time)" +
				" values (?,?,CURRENT_TIMESTAMP)";
		int [] backSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SettingCardRecommendVo bean =  list.get(i);
				ps.setInt(1,bean.getBrandId());
				ps.setInt(2,bean.getCardTypeId());
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
	
	
	
	/**删除某个商户设置的卡类型*/
	public int deleteSettingByBrandId(Integer brandId) {
		String sql = "delete from weixin.setting_card_recommend where brand_id=?";
		return jdbcTemplate.update(sql, brandId);
	}
	
	/**根据某个商户取已选择的卡类型*/
	public List<Map<String, Object>> getSettingCardByBrandId(Integer brandId) {
		String sql = "select * from weixin.setting_card_recommend where brand_id=?";
		return jdbcTemplate.queryForList(sql, brandId);
	}
}

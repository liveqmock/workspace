/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yazuo.weixin.vo.FunctionMenu;
import com.yazuo.weixin.vo.MemberVO;

/**
 * 创建菜单
 * @author kyy
 * @date 2014-7-21 下午4:48:44
 */
@Repository
public class CreateMenuDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**批量保存菜单*/
	public int saveMenu(final List<FunctionMenu> list) {
		String sql = "insert into weixin.weixin_function_menu (merchant_id,menu_type,menu_name,menu_value,app_id,app_secret,parent_code,insert_time)" +
				" values (?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
		 jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FunctionMenu bean =  list.get(i);
				ps.setInt(1,bean.getMerchantId());
				ps.setInt(2,bean.getMenuType());
				ps.setString(3,bean.getMenuName());
				ps.setString(4,bean.getMenuValue());
				ps.setString(5,bean.getAppId());
				ps.setString(6,bean.getAppSecret());
				ps.setString(7,bean.getParentCode());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
		return 0;
	}

	/**根据门店id和parentCode查询菜单*/
	public List<FunctionMenu> queryMenuListByBrandId (Integer brandId, String parentCode) {
		String sql = "select * from weixin.weixin_function_menu where merchant_id="+brandId+" and parent_code like '"+parentCode+"%' and length(parent_code)>3";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FunctionMenu>(FunctionMenu.class));
	}
	
	public List<FunctionMenu> queryAllParentMenu (Integer brandId) {
		String sql = "select * from weixin.weixin_function_menu where merchant_id="+brandId+" and length(parent_code)=3";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<FunctionMenu>(FunctionMenu.class));
	}
	
	/**根据门店id批量删除*/
	public int deleteMenu (Integer brandId) {
		String sql = "delete from weixin.weixin_function_menu where merchant_id="+brandId;
		return jdbcTemplate.update(sql);
	}
}

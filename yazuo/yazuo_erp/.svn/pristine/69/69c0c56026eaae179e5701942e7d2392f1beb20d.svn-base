/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-11-29
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.demo.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.demo.dao.DemoDao;
import com.yazuo.erp.demo.vo.DemoVO;

/**
 * @ClassName: DemoDaoImpl
 * @Description: 处理demoDAO的实现
 */
@Repository("demoDao")
public class DemoDaoImpl implements DemoDao {

	@Resource(name = "jdbcTemplateCrm")
	private JdbcTemplate jdbcTemplate;

	private static final Log LOG = LogFactory.getLog("erp");

	public List<DemoVO> getDemoByBrandId(Integer brandId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.name,a.merchant_id, "
				+ brandId
				+ " as brand_id, a.id as coupon_type_id,a.expire_end,a.batch_no,"
				+ " COALESCE((SELECT description FROM weixin.coupon_describe b where b.coupon_type_id=a.id),'') as description"
				+ " FROM trade.coupon_type a where  a.merchant_id in (select merchant_id from trade.merchant where brand_id=?) and (a.expire_end is null or a.expire_end>=now()) order by a.name");
		LOG.info("demo查询" + sb.toString());
		return jdbcTemplate.query(sb.toString(),
				new BeanPropertyRowMapper<DemoVO>(DemoVO.class), brandId);

	}

	public boolean saveDemo(DemoVO cd) throws Exception {

		return true;
	}
}

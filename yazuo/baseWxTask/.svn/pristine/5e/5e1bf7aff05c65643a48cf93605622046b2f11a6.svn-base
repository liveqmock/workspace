package com.yazuo.erp.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with MyElipse.<br/>
 * Date: 2013-11-18<br/>
 * Time: 下午8:29:12<br/>
 * <br/>
 */
public class BaseDAO {
	protected static final String SCHEMANAME_SYN = "syn";
	protected static final String SCHEMANAME_FES = "fes";
	protected static final String SCHEMANAME_SYS = "sys";
	protected static final String SCHEMANAME_TRADE = "trade";

	@Resource(name = "jdbcTemplateErp")
	protected JdbcTemplate jdbcTemplate;

	@Resource(name = "jdbcTemplateCRM")
	protected JdbcTemplate jdbcTemplateCrm;
	
	@Resource(name = "jdbcTemplateCRMTrade")
	protected JdbcTemplate jdbcTemplateCRMTrade;

	@Resource(name = "jdbcTemplateCrm46")
	protected JdbcTemplate jdbcTemplateCrm46;
	
	/**
	 * 查找 所有有效的商户的 sql condition
	 * @return
	 */
	protected String getExistsTradeMerchantList() {
		String sql = "SELECT merchant_id FROM trade.merchant where  status = 1 ";
		 List<Integer> list1 = jdbcTemplateCRMTrade.queryForList(sql, Integer.class);
		
		if(list1!=null&&list1.size()>0){
			StringBuffer str = new StringBuffer("(");
			int i=0;
			for (Integer merchantId : list1) {
				i++;
				if(i==list1.size()){
					//是最后一个
					str.append(merchantId+")");
				}else{
					str.append(merchantId+ ",");
				}
				
			}
			return str.toString();
		}
		return "";
	}
}

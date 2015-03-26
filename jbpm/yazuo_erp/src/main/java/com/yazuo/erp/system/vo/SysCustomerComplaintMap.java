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

package com.yazuo.erp.system.vo;

import java.util.HashMap;


/**
 * @date 
 */
public class SysCustomerComplaintMap extends HashMap<String, Object>{

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customer_complaint_type = null;
	
	public String getCustomerComplaintType() {
		return customer_complaint_type;
	}
	public void setCustomerComplaintType(String customerComplaintType) {
		this.customer_complaint_type = customerComplaintType;
	}     
}


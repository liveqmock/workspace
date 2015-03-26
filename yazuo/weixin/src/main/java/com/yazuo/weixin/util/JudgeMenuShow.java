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
package com.yazuo.weixin.util;

import java.util.ResourceBundle;

/**
 * 根据配置文件配置的商家id让菜单隐藏
 * @author kyy
 * @date 2014-6-27 下午6:09:48
 */
public class JudgeMenuShow {

	public static boolean judgeMenu (Integer brandId) {
		ResourceBundle bundle = ResourceBundle.getBundle("image-config");
		String brandStr = bundle.getString("hide_brand_id"); // 取需要隐藏商家的id
		if (!StringUtil.isNullOrEmpty(brandStr) && brandStr.contains(brandId+"")) {
			return true;
		}
		return false;
	}
}

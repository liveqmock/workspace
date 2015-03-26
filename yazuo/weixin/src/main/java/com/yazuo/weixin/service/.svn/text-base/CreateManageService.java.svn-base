/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-01-08
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service;

import java.util.List;
import com.yazuo.weixin.vo.FunctionMenu;

/**
 * @ClassName: CreateManageService
 * @Description: 处理菜单逻辑接口
 */
public interface CreateManageService {

	/**Description:菜单保存*/
	public String createButton(String json, String appId, String appSecret)
			throws Exception;

	/**根据brandId取相应创建的菜单*/
	public FunctionMenu getMenuMsgByBrandId (Integer brandId);
	
	/**将微信创建的菜单存入某个表方便取创建过的菜单*/
	public int insertMenu(final List<FunctionMenu> list);
}

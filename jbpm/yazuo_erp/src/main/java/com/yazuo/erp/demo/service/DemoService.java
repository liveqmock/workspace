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
package com.yazuo.erp.demo.service;

import java.util.List;

import com.yazuo.erp.demo.vo.DemoVO;

/**
 * @ClassName: DemoService
 * @Description: 处理demo逻辑接口
 */
public interface DemoService {

	/**
	 * <p>
	 * Description:查询优惠券描述列表
	 * </p>
	 * 
	 * @param id
	 *            集团id
	 * 
	 * @return List
	 */
	public List<DemoVO> getDemoByBrandId(Integer id) throws Exception;

	/**
	 * <p>
	 * Description:保存优惠券描述
	 * </p>
	 * 
	 * @param DemoVO
	 * 
	 * 
	 * @return boolean
	 */
	public boolean saveDemo(DemoVO cd) throws Exception;

}

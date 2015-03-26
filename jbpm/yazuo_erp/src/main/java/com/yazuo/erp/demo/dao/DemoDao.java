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
package com.yazuo.erp.demo.dao;

import java.util.List;

import com.yazuo.erp.demo.vo.DemoVO;

/**
 * @ClassName: DemoDao
 * @Description: 处理demo的DAO接口
 */
public interface DemoDao {

	/**
	 * <p>
	 * Description:查询列表
	 * </p>
	 * 
	 * @param id
	 *            集团id
	 * 
	 * @return List
	 */
	public List<DemoVO> getDemoByBrandId(Integer brandId)
			throws Exception;

	/**
	 * <p>
	 * Description:保存demo
	 * </p>
	 * 
	 * @param DemoVO
	 * 
	 * 
	 * @return boolean
	 */
	public boolean saveDemo(DemoVO cd) throws Exception;
}

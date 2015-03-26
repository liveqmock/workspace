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
package com.yazuo.erp.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.demo.dao.DemoDao;
import com.yazuo.erp.demo.service.DemoService;
import com.yazuo.erp.demo.vo.DemoVO;

/**
 * @InterfaceName: DemoServiceImpl
 * @Description: 处理demo逻辑的实现
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

	@Resource
	private DemoDao demoDao;

	public List<DemoVO> getDemoByBrandId(Integer id) throws Exception {
		return demoDao.getDemoByBrandId(id);
	}

	public boolean saveDemo(DemoVO cd) throws Exception {
		return demoDao.saveDemo(cd);
	}

}

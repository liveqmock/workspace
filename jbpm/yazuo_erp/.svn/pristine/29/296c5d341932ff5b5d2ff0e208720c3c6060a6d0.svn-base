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
package com.yazuo.erp.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yazuo.erp.system.dao.SysPositionDao;
import com.yazuo.erp.system.service.SysPositionService;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.util.StringUtils;

/**
 * @Description 职位相关操作的实现类
 * @author kyy
 * @date 2014-6-3 下午6:24:26
 */
@Service
public class SysPositionServiceImpl implements SysPositionService {

	@Resource
	private SysPositionDao sysPostionDao;
	/**
	 * @Title 保存职位
	 * @Description 
	 * @param sysPosition
	 * @return
	 */
	@Override
	public int savePosition(SysPositionVO sysPosition) {
		return sysPostionDao.savePosition(sysPosition);
	}

	/**
	 * @Title 修改职位
	 * @Description 
	 * @param sysPosition
	 * @return
	 */
	@Override
	public int updatePosition(SysPositionVO sysPosition) {
		return sysPostionDao.update(sysPosition);
	}

	/**
	 * @Title 删除职位
	 * @Description 
	 * @param id
	 * @return
	 */
	@Override
	public int deletePosition(Integer id) {
		SysPositionVO sysPosition = new SysPositionVO();
		sysPosition.setId(id);
		sysPosition.setIsEnable("0");
		return sysPostionDao.update(sysPosition);
//		return sysPostionDao.delete(id);
	}

	/**
	 * @Title 根据id获取职位信息
	 * @Description 
	 * @param id
	 * @return
	 */
	@Override
	public SysPositionVO getById(Integer id) {
		return sysPostionDao.getById(id);
	}

	/**
	 * @Title 根据查询条件分页
	 * @Description 
	 * @param sysPosition
	 * @return
	 */
	@Override
	public List<Map<String, String>> getAllPageByOder() {
		return sysPostionDao.getSysPositionsPage();
	}

	/**
	 * @Title 判断该职位下是否有用户
	 * @Description 
	 * @param positionId
	 * @return
	 */
	@Override
	public boolean isExistsUserOfPosition(Integer positionId) {
		List<Map<String, String>> list = sysPostionDao.isExistsUser(positionId);
		if (list !=null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title 批量删除岗位
	 * @Description 
	 * @param idStr
	 * @return
	 */
	@Override
	public Map<String, Object> deleteManyPosition(String [] orders) {
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
		StringBuilder noSb = new StringBuilder();
		for (int i = 0; i < orders.length; i ++) {
			Integer id = Integer.parseInt(orders[i]);
			// 判断该岗位下是否存在用户
			boolean isExistsUser = isExistsUserOfPosition(id);
			if (isExistsUser) { // 存在
				noSb.append(id).append(",");
			} else {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("id", id);
				list.add(map);
			}
		}
		// 批量删除
		int count = sysPostionDao.deleteMany(list);
		String msg = "";
		if (!StringUtils.isEmpty(noSb.toString())) {
			msg = "选中的职位中部分被用户使用，故部分删除成功!";
		} else {
			msg = "删除成功!";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", count > 0 ? "1" : "0");
		map.put("message", count > 0 ? msg : "删除失败!");
		map.put("data", "");
		return map;
	}

	@Override
	public long getTotalCount() {
		return sysPostionDao.findCount();
	}

}

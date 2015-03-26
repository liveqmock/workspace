/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 */
package com.yazuo.erp.system.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.system.vo.SysPositionVO;

/**
 * @Description 职位相关操作
 * @author kyy
 * @date 2014-6-3 下午6:22:01
 */
public interface SysPositionService {

	/**保存职位*/
	public int savePosition(SysPositionVO sysPosition);
	
	/**修改职位*/
	public int updatePosition(SysPositionVO sysPosition);
	
	/**删除职位*/
	public int deletePosition(Integer id);
	
	/**根据id获取相关对象*/
	public SysPositionVO getById(Integer id);
	
	/**根据条件查询*/
	public List<Map<String, String>> getAllPageByOder();
	
	/**获取总数量*/
	public long getTotalCount();
	
	/**判断该岗位下是否存在用户*/
	public boolean isExistsUserOfPosition(Integer positionId);
	
	/**批量删除*/
	public Map<String, Object> deleteManyPosition (String [] orders);

    /**
     * 查询有效的职位
     * @return
     */
    List<SysPositionVO> queryPositionList();
}

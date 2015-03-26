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
package com.yazuo.erp.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.system.vo.SysPositionVO;

/**
 * @跟mapper xml文件中的方法对应
 * @author yazuo
 * @date 2014-6-3 下午6:25:50
 */
@Repository
public interface SysPositionDao {

	int savePosition(SysPositionVO sysPosition);
	
	int update(SysPositionVO sysPosition);
	
	int delete(Integer id);
	
	SysPositionVO getById(Integer id);
	
	List<Map<String, String>> isExistsUser(Integer positionId);
	
	List<Map<String, String>> getSysPositionsPage();
	
	long findCount ();
	
	int deleteMany (List<Map<String, Integer>> list);

	/**
	 * @Description 根据课程ID查询有效的职位信息
	 * @param courseId 
	 * @return
	 * @throws 
	 */
	List<Map<String, Object>> queryPosition(@Param("courseId") Integer courseId);

	/**
	 * @Description 查询有效的职位
	 * @return
	 * @throws 
	 */
	List<SysPositionVO> queryPositionList();
}

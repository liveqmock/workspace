/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysWebkitVO;


@Repository
public interface SysWebkitDao{
	
	/**保存数据*/
	public int saveSysWebkit (SysWebkitVO entity);
	/**删除数据通过语句删除*/
	public int deleteSysWebkit(Integer id);
	
	/**根据条件查询*/
	public Page<SysWebkitVO> getWebKitManager(SysWebkitVO sysWebkitVO);
	/**根据id取对象信息*/
	SysWebkitVO getSysWebkitById (Integer id);
	/**判断某个版本号是否已存在了*/
	public long getWebKitManagerByVersion(SysWebkitVO sysWebkitVO);
	/**取最大版本*/
	public List<SysWebkitVO> getWebKitMaxVersion();
}

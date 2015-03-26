package com.yazuo.weixin.service;

import com.yazuo.weixin.vo.BrandAndKeyRelativeVO;

/**
* 门店和key关联关系
* @author kyy
* @date 2014-6-30 上午11:28:27
* @version 1.0
*/
public interface BrandManageService {
	/**判断此商户和key在数据库是否存在*/
	public long judgeRelativeExist(Integer brandId, String key);

	/**根据id获取信息*/
	public BrandAndKeyRelativeVO getRelativeById(Integer relativeId);
	/**保存商户和key关联关系*/
	public boolean saveRelative(BrandAndKeyRelativeVO relativeVO);

	/**修改商户与key关联关系*/
	public boolean updateActivityRecord(BrandAndKeyRelativeVO relativeVO);
	
	/**根据brand_id查询*/
	public BrandAndKeyRelativeVO getRelativeByBrandId(Integer brandId);
}

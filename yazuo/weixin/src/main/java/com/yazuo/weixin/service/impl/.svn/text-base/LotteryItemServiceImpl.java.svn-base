/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-24
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.LotteryItemDao;
import com.yazuo.weixin.service.LotteryItemService;

/**
 * @InterfaceName: LotteryItemServiceImpl
 * @Description: 处理奖项逻辑的实现
 */
@Service("lotteryItemService")
public class LotteryItemServiceImpl implements LotteryItemService {

	@Resource
	private LotteryItemDao lotteryItemDao;

	public List<Map<String, Object>> getLotteryItem(String lotteryRuleId)
			throws Exception {
		return lotteryItemDao.getLotteryItem(lotteryRuleId);
	}

}

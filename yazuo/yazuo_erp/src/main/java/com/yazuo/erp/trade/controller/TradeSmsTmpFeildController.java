package com.yazuo.erp.trade.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.trade.service.TradeSmsTmpFeildService;
import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;

/**
 * 
 * TradeCardtype 控制器
 * 
 * @author
 */
@Controller
@RequestMapping("/trade/smstmpfeild")
public class TradeSmsTmpFeildController {

	private static final Log LOG = LogFactory.getLog(TradeSmsTmpFeildController.class);
	@Resource
	private TradeSmsTmpFeildService tradeSmsTmpFeildService;

	/**
	 * 列表显示 TradeSmsTmpFeild
	 */
	@RequestMapping(value = "listTradeSmsTmpFeilds", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listTradeSmsTmpFeilds() {
		// 查询列表
		List<TradeSmsTmpFieldVO> cardtypeVOList = tradeSmsTmpFeildService.listTradeSmsTmpFeilds();
		return new JsonResult(true, "查询完成").setData(cardtypeVOList);
	}

}

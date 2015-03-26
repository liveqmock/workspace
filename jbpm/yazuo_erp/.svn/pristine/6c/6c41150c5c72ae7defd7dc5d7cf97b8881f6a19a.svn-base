package com.yazuo.erp.trade.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.service.TradeCardtypeService;
import com.yazuo.erp.trade.vo.TradeAwardRuleVO;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

/**
 *
 * TradeCardtype 控制器
 * @author 
 */
@Controller
@RequestMapping("/trade/cardtype")
public class TradeCardtypeController {
	
	private static final Log LOG = LogFactory.getLog(TradeCardtypeController.class);
	@Resource
	private TradeCardtypeService tradeCardtypeService;

	/**
	 * 列表显示 TradeCardtype 
	 */
	@RequestMapping(value = "listCardtypes", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listTradeCardtypes(Integer merchantId) {
        // 查询列表 附带奖励规则
        List<TradeCardtypeVO> cardtypeVOList = this.tradeCardtypeService.getTradeCardtypesAndAwardRules(merchantId);
        for (TradeCardtypeVO cardtypeVO : cardtypeVOList) {
            List<TradeAwardRuleVO> awardRuleVOs = cardtypeVO.getAwardRuleVos();
            if (awardRuleVOs.size() > 0) {
                int rebateType = awardRuleVOs.get(0).getRebateType();
                cardtypeVO.setRebateType(rebateType);
            }
        }
            return new JsonResult(true, "查询完成").setData(cardtypeVOList);
    }

    /**
     * 保存会员卡
     * @param cardtypeVOs
     * @return
     */
    @RequestMapping(value = "saveCardtypes", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult saveTradeCardtypes(@RequestBody TradeCardtypeVO[] cardtypeVOs, HttpSession session) {
    	SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
        SysOperationLogVO operationLogs = this.tradeCardtypeService.saveCardAndOperatoinLogs(Arrays.asList(cardtypeVOs), sessionUser);
        return new JsonResult(true, "保存完成").setData(operationLogs);
    }


}

package com.yazuo.erp.trade.service;

import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;

import java.util.List;

/**
 * @author erp team
 * @Description 短信模板服务类
 * @date
 */
public interface TradeMessageTemplateService {
    /**
     * 得到短信信息模板
     *
     * @param merchantNo
     * @return
     */
    List<TradeMessageTemplateVO> listTradeMessageTemplates(String merchantNo);

    /**
     * 同步短信模板以及会员卡信息
     *
     * @param merchantId
     */
    boolean syncMsgTplsAndCardTypes(Integer merchantId);

    /**
     * 保存短信模板
     *
     * @param messageTemplateVOs
     * @return
     */
    List<TradeMessageTemplateVO> saveTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs);

    /**
     * 查询字段列表
     *
     *
     * @param transCode
     * @return
     */
    List<TradeSmsTmpFieldVO> listSmsTmpFeilds(List<String> transCode);

    /**
     * 1.保存交易短息
     * 2.生成流水
     * 3.查询上流流程步骤状态等信息，封装结果并返回
     */
	SysOperationLogVO saveMessageAndOperatoinLogs(List<TradeMessageTemplateVO> messageTemplateVOsList, SysUserVO sessionUser);
}

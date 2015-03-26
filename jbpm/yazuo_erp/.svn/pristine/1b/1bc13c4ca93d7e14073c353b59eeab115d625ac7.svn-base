package com.yazuo.erp.trade.controller;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.service.TradeMessageTemplateService;
import com.yazuo.erp.trade.service.TradeSmsTmpFeildService;
import com.yazuo.erp.trade.service.TradeTransCodeService;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;
import com.yazuo.erp.trade.vo.TradeTransCodeVO;
import com.yazuo.external.account.service.MerchantService;
import com.yazuo.external.account.vo.MerchantVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * 短信模板控制器
 *
 * @author erp team
 */
@Controller
@RequestMapping("/trade/messageTemplate")
public class TradeMessageTemplateController {
    private static final Log LOG = LogFactory.getLog(TradeMessageTemplateController.class);

    @Resource
    private TradeSmsTmpFeildService tradeSmsTmpFeildService;

    @Resource
    private TradeMessageTemplateService tradeMessageTemplateService;

    @Resource
    private SynMerchantService merchantService;

    @Resource
    private TradeTransCodeService tradeTransCodeService;

    @RequestMapping(value = "saveMessageTemplates", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult saveTradeMessageTemplates(@RequestBody TradeMessageTemplateVO[] messageTemplateVOs, HttpSession session) {
    	SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
        Integer merchantId = messageTemplateVOs[0].getMerchantId();
        SynMerchantVO merchantVO = this.merchantService.getSynMerchantById(merchantId);
        // 设置parent
        this.assingTemplateParentInfo(messageTemplateVOs);

        // 设置merchantNo
        String merchantNo = merchantVO.getMerchantNo();
        for (TradeMessageTemplateVO messageTemplateVO : messageTemplateVOs) {
            if (!merchantNo.equals(messageTemplateVO.getMerchantNo())) {
                messageTemplateVO.setId(null);
                messageTemplateVO.setMerchantNo(merchantNo);
            }
        }
        // 保存
        SysOperationLogVO messageAndOperatoin = 
        		this.tradeMessageTemplateService.saveMessageAndOperatoinLogs(Arrays.asList(messageTemplateVOs), sessionUser);
        return new JsonResult(true).setMessage("保存成功!").setData(messageAndOperatoin);
    }

    /**
     * @param merchantId
     * @return
     */
    @RequestMapping(value = "listMessageTemplates", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult listTradeMessageTemplates(Integer merchantId) {
        SynMerchantVO merchantVO = this.merchantService.getSynMerchantById(merchantId);
        List<TradeMessageTemplateVO> messageTemplateVOs = this.tradeMessageTemplateService.listTradeMessageTemplates(merchantVO.getMerchantNo());
        this.assignToMsgTplVOs(messageTemplateVOs);
        return new JsonResult(true).setData(messageTemplateVOs);
    }

    /**
     * 查询交易码对应的字段
     *
     * @param transCodes
     * @return
     */
    @RequestMapping(value = "listSmsTmpFields", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult listSmsTmpFields(@RequestBody String[] transCodes) {
        List<TradeSmsTmpFieldVO> smsTmpFeildVOs = this.tradeMessageTemplateService.listSmsTmpFeilds(Arrays.asList(transCodes));
        Map<String, List<TradeSmsTmpFieldVO>> smsTmpFeildVOMap = this.groupByTransCode(smsTmpFeildVOs);
        return new JsonResult(true).setData(smsTmpFeildVOMap);
    }
/*
    @RequestMapping(value = "testAsynchronize", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult testAsynchronize(Integer merchantId) {
        this.tradeMessageTemplateService.syncMsgTplsAndCardTypes(merchantId);
        return new JsonResult(true);
    }*/


    /**
     * 对TradeSmsTmpField按TransCode进行分组
     * @param smsTmpFieldVOs
     * @return
     */
    private Map<String, List<TradeSmsTmpFieldVO>> groupByTransCode(List<TradeSmsTmpFieldVO> smsTmpFieldVOs) {
        Map<String, List<TradeSmsTmpFieldVO>> result = new HashMap<String, List<TradeSmsTmpFieldVO>>();
        for (TradeSmsTmpFieldVO vo : smsTmpFieldVOs) {
            List<TradeSmsTmpFieldVO> tmpSmsTmpFieldVOs = result.get(vo.getTransCode());
            if (tmpSmsTmpFieldVOs == null) {
                tmpSmsTmpFieldVOs = new ArrayList<TradeSmsTmpFieldVO>();
                result.put(vo.getTransCode(), tmpSmsTmpFieldVOs);
            }
            tmpSmsTmpFieldVOs.add(vo);
        }
        return result;
    }

    /**
     * 对templateText字段赋值
     * @param messageTemplateVOs
     */
    private void assignToMsgTplVOs(List<TradeMessageTemplateVO> messageTemplateVOs) {
        List<TradeSmsTmpFieldVO> smsTmpFieldVOs = tradeSmsTmpFeildService.listTradeSmsTmpFeilds();

        // 构建Map
        Map<String, TradeSmsTmpFieldVO> smsTmpFieldsMap = new HashMap<String, TradeSmsTmpFieldVO>();
        for (TradeSmsTmpFieldVO smsTmpFieldVO : smsTmpFieldVOs) {
            smsTmpFieldsMap.put(smsTmpFieldVO.getFieldName(), smsTmpFieldVO);
        }
        // 对messageTemplate赋值
        for (TradeMessageTemplateVO messageTemplateVO : messageTemplateVOs) {
            this.assignTemplateTxt(messageTemplateVO, smsTmpFieldsMap);
        }

        List<TradeTransCodeVO> transCodeVOs = this.tradeTransCodeService.getAllTransCodes();
        Map<String, TradeTransCodeVO> tradeTransCodeVOMap = new HashMap<String, TradeTransCodeVO>();
        for (TradeTransCodeVO transCodeVO : transCodeVOs) {
            tradeTransCodeVOMap.put(transCodeVO.getTransCode(), transCodeVO);
        }
        for (TradeMessageTemplateVO messageTemplateVO : messageTemplateVOs) {
            TradeTransCodeVO transCodeVO = tradeTransCodeVOMap.get(messageTemplateVO.getTransCode());
            messageTemplateVO.setTransName(transCodeVO.getTransName());
        }

    }

    /**
     *
     * @param messageTemplateVO
     * @param smsTmpFieldVOs
     */
    private void assignTemplateTxt(TradeMessageTemplateVO messageTemplateVO, Map<String, TradeSmsTmpFieldVO> smsTmpFieldVOs) {
        String templateTxt = messageTemplateVO.getMessageTemplate();
        String templateField = messageTemplateVO.getTemplateFeilds();
        String[] fields = templateField.split(",");
        for (String field : fields) {
            TradeSmsTmpFieldVO smsTmpFieldVO = smsTmpFieldVOs.get(field);
            templateTxt = templateTxt.replaceFirst(smsTmpFieldVO.getFieldType(), "[" + smsTmpFieldVO.getDescription() + "]");
        }
        messageTemplateVO.setTemplateTxt(templateTxt);
    }

    private void assingTemplateParentInfo(TradeMessageTemplateVO[] messageTemplateVOs) {
        Map<Long, TradeMessageTemplateVO> idIndex = new HashMap<Long, TradeMessageTemplateVO>();
        for (TradeMessageTemplateVO templateVO : messageTemplateVOs) {
            idIndex.put(templateVO.getId(), templateVO);
        }
        for (TradeMessageTemplateVO templateVO : messageTemplateVOs) {
            Long parentId = templateVO.getParent();
            if (parentId != null) {
                TradeMessageTemplateVO parentMessageTemplateVO = idIndex.get(parentId);
                templateVO.setParentMessageTemplateVO(parentMessageTemplateVO);
            }
        }
    }

}

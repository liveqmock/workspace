package com.yazuo.erp.trade.service.impl;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.Encrypt;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.dao.TradeMessageTemplateDao;
import com.yazuo.erp.trade.dao.TradeSmsTmpFieldDao;
import com.yazuo.erp.trade.service.TradeCardtypeService;
import com.yazuo.erp.trade.service.TradeMessageTemplateService;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import com.yazuo.erp.trade.vo.TradeSmsTmpFieldVO;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @Description 短信模板Service
 * @author erp team
 * @date
 */

@Service
public class TradeMessageTemplateServiceImpl implements TradeMessageTemplateService {
    private Log log = LogFactory.getLog(this.getClass());
    // 模板的商户编号，与运营平台一致
    protected static final String DEFAULT_MERCHANT_NO = "111111111111111";

    @Value("${sop.url.prefix}")
    protected String SOP_APP_CONTEXT;

    @Resource
    private SynMerchantService synMerchantService;

    @Resource
    private TradeCardtypeService tradeCardtypeService;

    @Resource
	private TradeMessageTemplateDao tradeMessageTemplateDao;

    @Resource
    private TradeSmsTmpFieldDao tradeSmsTmpFieldDao;
    
	@Resource private SysOperationLogService sysOperationLogService;
	@Resource private FesOnlineProcessService fesOnlineProcessService;

    /**
     * {
     *     messageTemplates:[],
     *     cardtypes:[]
     * }
     * @param merchantId
     */
    @Override
    public boolean syncMsgTplsAndCardTypes(Integer merchantId) {
        SynMerchantVO merchantVO = this.synMerchantService.getSynMerchantById(merchantId);
        String merchantNo = merchantVO.getMerchantNo();
        List<TradeMessageTemplateVO> messageTemplates = this.listTradeMessageTemplates(merchantNo);
        List<TradeCardtypeVO> cardtypes = this.tradeCardtypeService.getTradeCardtypesAndAwardRules(merchantId);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("messageTemplates", messageTemplates);
        params.put("cardtypes", cardtypes);

        String cipherText = this.serializeObjectAndEncrypted(params);
        // 同步短信模板以及会员卡信息
        boolean isSuccess = this.synchronizeMsgTplAndCardtypes(cipherText);
        if (isSuccess) { //同步成功更新数据
            this.updateIsSynchronize(messageTemplates, cardtypes);
        }
        return isSuccess;
    }

    @Override
    public List<TradeMessageTemplateVO> listTradeMessageTemplates(String merchantNo) {
        List<TradeMessageTemplateVO> merchantMstTplVOs = this.getMessageTemplateVOs(merchantNo);
        List<TradeMessageTemplateVO> result = merchantMstTplVOs;
        // 默认的模板
        List<TradeMessageTemplateVO> defaultMstTplVOs = this.getDefaultMessageTemplateVOs();
        List<String> transCodes = new ArrayList<String>();
        for (TradeMessageTemplateVO messageTemplateVO : merchantMstTplVOs) {
            if (messageTemplateVO.getParent() == null) {
                transCodes.add(messageTemplateVO.getTransCode());
            }
        }

        // 查列表
        Map<Long, TradeMessageTemplateVO> messageTemplateVOMap = new HashMap<Long, TradeMessageTemplateVO>();
        for (TradeMessageTemplateVO defaultMsgTpl : defaultMstTplVOs) {
            messageTemplateVOMap.put(defaultMsgTpl.getId(), defaultMsgTpl);
        }

        for (TradeMessageTemplateVO msgTplVO : defaultMstTplVOs) {
            if (msgTplVO.getParent() != null) {
                TradeMessageTemplateVO parentMsgTpl = messageTemplateVOMap.get(msgTplVO.getParent());
                if (parentMsgTpl != null && !transCodes.contains(parentMsgTpl.getTransCode())) {
                    result.add(msgTplVO);
                }
            }else if (!transCodes.contains(msgTplVO.getTransCode())) {
                result.add(msgTplVO);
            }
        }
        return result;
    }

    @Override
    public List<TradeMessageTemplateVO> saveTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs) {

        List<TradeMessageTemplateVO> messageTemplateVOsToInsert = new ArrayList<TradeMessageTemplateVO>();
        List<TradeMessageTemplateVO> messageTemplateVOsToUpdate = new ArrayList<TradeMessageTemplateVO>();
        for (TradeMessageTemplateVO messageTemplateVO : messageTemplateVOs) {
            if (messageTemplateVO.getId() != null) {
                messageTemplateVOsToUpdate.add(messageTemplateVO);
            }else {
                messageTemplateVOsToInsert.add(messageTemplateVO);
            }
        }
        this.insertTradeMessageTemplateVOs(messageTemplateVOsToInsert);
        this.updateTradeMessageTemplateVOs(messageTemplateVOsToUpdate);
        this.updateParentForMessageTemplateVOs(messageTemplateVOs);
        return messageTemplateVOs;
    }

    /**
     * 1.保存交易短息
     * 2.生成流水
     * 3.查询上流流程步骤状态等信息，封装结果并返回
     */
    //@Deprecated//暂时未使用， 交易短信接口联调完在处理
    @Override
    public SysOperationLogVO saveMessageAndOperatoinLogs(List<TradeMessageTemplateVO> messageTemplateVOsList, SysUserVO sessionUser) {
    	//保存交易短信规则
    	List<TradeMessageTemplateVO> tradeMessageTemplates = this.saveTradeMessageTemplateVOs(messageTemplateVOsList);
    	SysOperationLogVO sysOperationLog = new SysOperationLogVO();
    	Integer merchantId = tradeMessageTemplates.get(0).getMerchantId();
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setMerchantId(merchantId);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		sysOperationLog.setDescription("添加了交易短信规则。");
		sysOperationLog.setFesLogType("7");
		sysOperationLogService.saveSysOperationLog(sysOperationLog);
		//为返回值添加状态和操作流程等信息
		String finalStatus = this.fesOnlineProcessService.calculateOrUpdateProcessFinalStatus(merchantId, sessionUser);//计算总状态
		sysOperationLog.setFinalStatus(finalStatus);
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setMerchantId(merchantId);
		fesOnlineProcessVO.setStepNum(StepNum.background_set.toString());
		//重新做一次查询， 为了获得真实的状态
		FesOnlineProcessVO fesOnlineProcessDB = this.fesOnlineProcessService.getFesOnlineProcessByMerchantAndStep(fesOnlineProcessVO);
		//如果 卡类型和短信规则都添加了， 则更新状态
		String step5Status = this.fesOnlineProcessService.updateStep5Status(fesOnlineProcessDB.getId(), "03", sessionUser.getId());
		sysOperationLog.setOnlineProcessStatus(step5Status); 
		sysOperationLog.setStepId(fesOnlineProcessDB.getStepId());
		sysOperationLog.setStepNum(fesOnlineProcessDB.getStepNum());
		sysOperationLog.setProcessId(fesOnlineProcessDB.getId());
		sysOperationLog.setTradeMessageTemplates(tradeMessageTemplates);
    	return sysOperationLog;
    }
    @Override
    public List<TradeSmsTmpFieldVO> listSmsTmpFeilds(List<String> transCode) {
        return this.tradeSmsTmpFieldDao.getSmsTmpFields(transCode);
    }

    @Transactional("erp")
    protected void updateIsSynchronize(List<TradeMessageTemplateVO> messageTemplates, List<TradeCardtypeVO> cardtypes) {
        if (messageTemplates.size() > 0) {
            this.tradeMessageTemplateDao.updateIsSynchronize(messageTemplates);
        }
        if (cardtypes.size() > 0) {
            this.tradeCardtypeService.synchronize(cardtypes);
        }
    }

    protected void updateTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs) {
        if (messageTemplateVOs.size() > 0) {
            this.tradeMessageTemplateDao.updateTradeMessageTemplateVOs(messageTemplateVOs);
        }
    }

    protected void insertTradeMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs) {
        if (messageTemplateVOs.size() > 0) {
//            this.tradeMessageTemplateDao.insertTradeMessageTemplateVOs(messageTemplateVOs);
            for (TradeMessageTemplateVO vo : messageTemplateVOs ) {
                this.tradeMessageTemplateDao.insertTradeMessageTemplateVO(vo);
            }
        }
    }

    protected void updateParentForMessageTemplateVOs(List<TradeMessageTemplateVO> messageTemplateVOs) {
        List<TradeMessageTemplateVO> messageTemplateVOsToUpdate = new LinkedList<TradeMessageTemplateVO>();
        for (TradeMessageTemplateVO messageTemplateVO : messageTemplateVOs) {
            if (messageTemplateVO.getParentMessageTemplateVO() != null) {
                Long parentId = messageTemplateVO.getParentMessageTemplateVO().getId();
                messageTemplateVO.setParent(parentId);
                messageTemplateVOsToUpdate.add(messageTemplateVO);
            }
        }
        this.updateTradeMessageTemplateVOs(messageTemplateVOsToUpdate);
    }

    /**
     * 得到默认的模板信息
     * @return
     */
    protected List<TradeMessageTemplateVO> getDefaultMessageTemplateVOs() {
        return this.getMessageTemplateVOs(DEFAULT_MERCHANT_NO);
    }

    /**
     * 得到商户的模板信息
     * @param merchantNo
     * @return
     */
    protected List<TradeMessageTemplateVO> getMessageTemplateVOs(String merchantNo) {
        return this.tradeMessageTemplateDao.listTradeMessageTemplates(merchantNo);
    }

    /**
     * 加密并对其进行序列化
     * @param object
     * @return
     */
    protected String serializeObjectAndEncrypted(Object object) {
        String plainText = this.serializeObject(object);
        String cipherText = this.encryptString(plainText);
        return cipherText;
    }

    /**
     * 对象序列化
     * @param object
     * @return
     */
    protected String serializeObject(Object object) {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param plainText
     * @return
     */
    protected String encryptString(String plainText) {
        String cipherText = Encrypt.getInstance().encrypt(plainText);
        return cipherText;
    }


    protected boolean synchronizeMsgTplAndCardtypes(String ciphertext) {
        String url = SOP_APP_CONTEXT + "/api/erp/synchronizeMstTplAndCardtypes.htm";
        String result = this.getResponseBody(url,ciphertext);
        Map<String, Object> json = this.unserialize(result);
        return json != null && ((Boolean)json.get("success") == true);
    }


    protected Map<String, Object> unserialize(String text) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> objectMap = null;
        try {
            objectMap = objectMapper.readValue(text, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objectMap;
    }

    private String getResponseBody(String url,String cipherText) {
        HttpPost postReq = new HttpPost(url);
        postReq.setEntity(new StringEntity(cipherText, ContentType.APPLICATION_JSON));
        HttpClient httpclient = new DefaultHttpClient();
        String resultStr = null;
        try {
            long beginning = System.currentTimeMillis();
            HttpResponse httpResponse = httpclient.execute(postReq);
            long end = System.currentTimeMillis();
            log.debug("url:" + url + ",用时:" +(end-beginning)+"ms");
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
            } else {
                log.error("返回状态码不是200: 状态码:" + httpResponse.getStatusLine().getStatusCode() + ",URL:" + url);
            }
        } catch (IOException e) {
            log.error("请求错误，请求URL：" + url);
            e.printStackTrace();
            postReq.abort();
        }finally {
            postReq.releaseConnection();
        }
        return resultStr;
    }
}
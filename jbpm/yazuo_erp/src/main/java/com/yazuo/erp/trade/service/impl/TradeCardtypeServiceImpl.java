package com.yazuo.erp.trade.service.impl;

import java.util.*;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.vo.*;
import com.yazuo.erp.trade.dao.*;

/**
 * @Description 会员卡
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.trade.service.TradeCardtypeService;

@Service
public class TradeCardtypeServiceImpl implements TradeCardtypeService {

	@Resource private TradeCardtypeDao tradeCardtypeDao;
	@Resource private TradeAwardRuleDao tradeAwardRuleDao;
	@Resource private SysOperationLogService sysOperationLogService;
	@Resource private FesOnlineProcessService fesOnlineProcessService;

    @Override
    public List<TradeCardtypeVO> getTradeCardtypesAndAwardRules(Integer merchantId) {
        return this.tradeCardtypeDao.getTradeCardtypesAndAwardRules(merchantId);
    }
    
    /**
     * 1.保存卡类型和奖励规则
     * 2.生成流水
     * 3.查询上流流程步骤状态等信息，封装结果并返回
     */
    //@Deprecated//暂时未使用， 等会员规则接口联调完在处理
    @Override
    public SysOperationLogVO saveCardAndOperatoinLogs(List<TradeCardtypeVO> tradeCardtypeVOs, SysUserVO sessionUser) {
    	//保存卡类型和规则
    	List<TradeCardtypeVO> cardtypesAndAwardRules = this.saveCardtypesAndAwardRules(tradeCardtypeVOs);
    	SysOperationLogVO sysOperationLog = new SysOperationLogVO();
    	Integer merchantId = tradeCardtypeVOs.get(0).getMerchantId();
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		sysOperationLog.setMerchantId(merchantId);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setModuleType(Constant._FES);
		sysOperationLog.setDescription("添加了会员卡规则。");
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
		sysOperationLog.setCardtypesAndAwardRules(cardtypesAndAwardRules);
    	return sysOperationLog;
    }
    @Override
    public List<TradeCardtypeVO> saveCardtypesAndAwardRules(List<TradeCardtypeVO> tradeCardtypeVOs) {
        if (null == tradeCardtypeVOs && tradeCardtypeVOs.size() == 0) {
            return Collections.emptyList();
        }
        // 区分已经保存与未保存，以及待删除
        List<TradeCardtypeVO> cardtypeVOsToUpdate = new ArrayList<TradeCardtypeVO>();
        List<TradeCardtypeVO> cardtypeVOsToSave = new ArrayList<TradeCardtypeVO>();

        //查询数据库
        Integer merchantId = tradeCardtypeVOs.get(0).getMerchantId();
        List<TradeCardtypeVO> persistCardtypeVOs = this.tradeCardtypeDao.getTradeCardtypes(merchantId);
        Map<Integer, TradeCardtypeVO> cardtypeIdToVoMap = new HashMap<Integer, TradeCardtypeVO>();
        for (TradeCardtypeVO cardtypeVO : persistCardtypeVOs) {
            cardtypeIdToVoMap.put(cardtypeVO.getId(), cardtypeVO);
        }
        Set<Integer> cardtypeIds = cardtypeIdToVoMap.keySet();

        // 分类
        for (TradeCardtypeVO vo : tradeCardtypeVOs) {
            if (null == vo.getId()) {
                cardtypeVOsToSave.add(vo);
            } else if(cardtypeIds.contains(vo.getId())) {
                cardtypeVOsToUpdate.add(vo);
                cardtypeIdToVoMap.remove(vo.getId());
            }
        }


        this.updateCardtypesAndAwardRules(cardtypeVOsToUpdate);
        this.insertCardtypesAndAwardRules(cardtypeVOsToSave);
        this.deleteCardtypesAndAwardRules(new ArrayList<TradeCardtypeVO>(cardtypeIdToVoMap.values()));
        return tradeCardtypeVOs;
    }

    @Override
    public int synchronize(List<TradeCardtypeVO> cardtypeVOs) {
        return this.tradeCardtypeDao.updateIsSynchronizeForCardtypes(cardtypeVOs);
    }

    /**
     * 更新会员卡信息
     * @param cardtypeVOs
     */
    protected void updateCardtypesAndAwardRules(List<TradeCardtypeVO> cardtypeVOs) {
        if (cardtypeVOs.size() > 0) {
            this.tradeCardtypeDao.batchUpdateTradeCardtypes(cardtypeVOs);

            // 同步属性
            this.sychronizeCardtypeId(cardtypeVOs);
            List<TradeAwardRuleVO> allAwardRuleVOList = new ArrayList<TradeAwardRuleVO>();
            List<Integer> cardtypeIds = new ArrayList<Integer>();
            for (TradeCardtypeVO cardtypeVO : cardtypeVOs) {
                allAwardRuleVOList.addAll(cardtypeVO.getAwardRuleVos());
                cardtypeIds.add(cardtypeVO.getId());
            }

            List<TradeAwardRuleVO> awardRuleVOsToSave = new ArrayList<TradeAwardRuleVO>();
            List<TradeAwardRuleVO> awardRuleVOsToUpdate = new ArrayList<TradeAwardRuleVO>();
            List<TradeAwardRuleVO> persistAwardRuleVOs = this.tradeAwardRuleDao.getAwardRulesByCardtypeIds(cardtypeIds);
            List<Integer> persistAwardRuleIds = new ArrayList<Integer>();
            for (TradeAwardRuleVO awardRuleVO : persistAwardRuleVOs) {
                persistAwardRuleIds.add(awardRuleVO.getId());
            }

            for (TradeAwardRuleVO awardRuleVO : allAwardRuleVOList) {
                if (awardRuleVO.getId() == null) {
                    awardRuleVOsToSave.add(awardRuleVO);
                } else if (persistAwardRuleIds.contains(awardRuleVO.getId())) {
                    awardRuleVOsToUpdate.add(awardRuleVO);
                    persistAwardRuleIds.remove(awardRuleVO.getId());
                }
            }

            if (persistAwardRuleIds.size() > 0) {
                this.tradeAwardRuleDao.batchDeleteTradeAwardRuleByIds(persistAwardRuleIds);
            }

            if (awardRuleVOsToSave.size() > 0) {
                this.tradeAwardRuleDao.batchInsertTradeAwardRules(awardRuleVOsToSave);
            }
            if (awardRuleVOsToUpdate.size() > 0) {
                this.tradeAwardRuleDao.batchUpdateTradeAwardRules(awardRuleVOsToUpdate);
            }
        }
    }

    /**
     * 保存会员卡信息
     *
     * @param cardtypeVOs
     */
    protected void insertCardtypesAndAwardRules(List<TradeCardtypeVO> cardtypeVOs) {
        if (cardtypeVOs.size() > 0) {
            //  保存会员卡
            for (TradeCardtypeVO cardtypeVO : cardtypeVOs) {
                cardtypeVO.setIsSynchronize(false);
                this.tradeCardtypeDao.insertTradeCardtypeVO(cardtypeVO);
            }

            //  保存奖励规则
            List<TradeAwardRuleVO> awardRuleVOs = new ArrayList<TradeAwardRuleVO>();
            for (TradeCardtypeVO cardtypeVO : cardtypeVOs) {
                for (TradeAwardRuleVO awardRuleVO : cardtypeVO.getAwardRuleVos()) {
                    awardRuleVO.setCardtypeId(cardtypeVO.getId());
                    awardRuleVOs.add(awardRuleVO);
                }
            }
            if (awardRuleVOs.size() > 0) {
                this.tradeAwardRuleDao.batchInsertTradeAwardRules(awardRuleVOs);
            }
        }
    }

    /**
     * 删除会员卡信息
     *
     * @param cardtypeVOs
     */
    protected void deleteCardtypesAndAwardRules(List<TradeCardtypeVO> cardtypeVOs) {
        if (cardtypeVOs.size() > 0) {
            List<Integer> cardtypeIds = new ArrayList<Integer>();
            for (TradeCardtypeVO cardtypeVO : cardtypeVOs) {
                cardtypeIds.add(cardtypeVO.getId());
            }
            //  删除奖励规则
            this.tradeAwardRuleDao.deleteAwardRuleByCardtypeIds(cardtypeIds);
            //  删除会员卡信息
            this.tradeCardtypeDao.batchDeleteTradeCardtypeByIds(cardtypeIds);
        }
    }

    /**
     *
     * @param cardtypeVOs
     */
    protected void sychronizeCardtypeId(List<TradeCardtypeVO> cardtypeVOs) {
        for (TradeCardtypeVO cardtypeVO : cardtypeVOs) {
            if (cardtypeVO.getAwardRuleVos() != null) {
                for (TradeAwardRuleVO awardRuleVO : cardtypeVO.getAwardRuleVos()) {
                    awardRuleVO.setCardtypeId(cardtypeVO.getId());
                }
            }
        }
    }

}

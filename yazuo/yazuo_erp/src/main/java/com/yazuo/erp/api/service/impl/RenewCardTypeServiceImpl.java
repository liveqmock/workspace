package com.yazuo.erp.api.service.impl;

import com.beust.jcommander.internal.Lists;
import com.yazuo.erp.api.dao.RenewCardTypeDao;
import com.yazuo.erp.api.service.RenewCardtypeService;
import com.yazuo.erp.api.vo.CardTypeVO;
import com.yazuo.erp.bes.service.BesRenewCardStatusService;
import com.yazuo.erp.bes.vo.BesRenewCardStatusVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 */
@Service
public class RenewCardTypeServiceImpl implements RenewCardtypeService {
    @Resource
    private BesRenewCardStatusService renewCardStatusService;

    @Resource
    private SynMerchantService synMerchantService;

    @Resource
    private RenewCardTypeDao cardTypeDao;

    @Override
    public void statics() {
        //ERP中可用的brandIds列表
        List<Integer> merchantIds = this.synMerchantService.getAllAvailMerchantIds();
        List<Integer> cardTypeIdsFromCrm = this.cardTypeDao.getAllCardtypeIds(merchantIds);
        List<Integer> cardTypeIdsFromErp = this.renewCardStatusService.getAllCardTypeIds();
        // 对比ID
        Collection<Integer> cardTypeIdsToCreate = CollectionUtils.subtract(cardTypeIdsFromCrm, cardTypeIdsFromErp);
        Collection<Integer> cardTypeIdsToDelete = CollectionUtils.subtract(cardTypeIdsFromErp, cardTypeIdsFromCrm);
        Collection<Integer> cardTypeIdsToUpdate = CollectionUtils.intersection(cardTypeIdsFromCrm, cardTypeIdsFromErp);


        this.renewCardStatusService.deleteRenewCardStatusByIds(Lists.newArrayList(cardTypeIdsToDelete));

        for (Integer cardTypeId : cardTypeIdsToCreate) {
            BesRenewCardStatusVO cardStatusVO = this.getRenewCardStatusVO(cardTypeId);
            if (cardStatusVO != null) {
                cardStatusVO.setStatus(BesRenewCardStatusVO.NORMAL_STATE);
                this.renewCardStatusService.saveRenewCardStatus(cardStatusVO);
            }
        }

        for (Integer cardTypeId : cardTypeIdsToUpdate) {
            BesRenewCardStatusVO cardStatusVO = this.getRenewCardStatusVO(cardTypeId);
            this.renewCardStatusService.updateRenewCardStatInfoByCardTypeId(cardStatusVO);
        }
    }

    protected BesRenewCardStatusVO getRenewCardStatusVO(Integer cardTypeId) {
        BesRenewCardStatusVO resultVo = new BesRenewCardStatusVO();
        CardTypeVO cardTypeVO = this.cardTypeDao.getCardType(cardTypeId);
        if (cardTypeVO == null || cardTypeVO.getTotalNum() == null) {
            return null;
        }
        Date now = new Date();
        resultVo.setCardType(cardTypeVO.getCardType());
        resultVo.setCardTypeId(cardTypeVO.getCardTypeId());
        resultVo.setCardTypeStatus(cardTypeVO.getStatus());
        resultVo.setBrandId(cardTypeVO.getBrandId());
        resultVo.setCardTotalNum(cardTypeVO.getTotalNum());
        resultVo.setIsEntityCard(cardTypeVO.isEntityCard() ? BesRenewCardStatusVO.ENTITY_CARD_YES : BesRenewCardStatusVO.ENTITY_CARD_NO);
        resultVo.setLastActivateDate(cardTypeVO.getCreatedDate());
        resultVo.setStatus(BesRenewCardStatusVO.NORMAL_STATE);
        resultVo.setUpdatedTime(now);
        resultVo.setOperationIds(new Integer[]{});

        Integer noActivatedNum = this.cardTypeDao.getNoActivatedNum(cardTypeVO.getBatchId());
        resultVo.setNoActivatedNum(noActivatedNum);

        Integer newCardNum = this.cardTypeDao.getNewCardNum(resultVo.getBrandId(), DateUtils.addDays(now, 90), now);
        resultVo.setCardTotalNumFor3Months(newCardNum);
        resultVo.setAvgCardNumFor3Months(newCardNum * 1.0 / 90);
        Integer availDateNum = null;
        if (!resultVo.getAvgCardNumFor3Months().equals(0.0)) {
            new Double(resultVo.getNoActivatedNum() / resultVo.getAvgCardNumFor3Months()).intValue();
        }
        resultVo.setAvailDateNum(availDateNum);
        return resultVo;
    }
}

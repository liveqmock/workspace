package com.yazuo.erp.bes.service.impl;

import com.google.common.base.Equivalence;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yazuo.erp.bes.controller.vo.RenewCardDTO;
import com.yazuo.erp.bes.dao.BesRenewCardStatusDao;
import com.yazuo.erp.bes.service.BesRenewCardStatusService;
import com.yazuo.erp.bes.vo.BesRenewCardStatusVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class BesRenewCardStatusServiceImpl implements BesRenewCardStatusService {

    @Resource
    private SysDictionaryService sysDictionaryService;


    @Resource
    private BesRenewCardStatusDao besRenewCardStatusDao;

    @Resource
    private SysOperationLogDao sysOperationLogDao;

    @Override
    public List<Integer> getAllCardTypeIds() {
        return this.besRenewCardStatusDao.getAllCardTypeIds();
    }

    @Override
    public BesRenewCardStatusVO saveRenewCardStatus(BesRenewCardStatusVO renewCardStatusVO) {
        int num = this.besRenewCardStatusDao.insert(renewCardStatusVO);
        return num > 0 ? renewCardStatusVO : null;
    }

    @Override
    public BesRenewCardStatusVO getRenewCardStatusById(Integer statusId) {
        return this.besRenewCardStatusDao.selectById(statusId);
    }

    @Override
    public BesRenewCardStatusVO updateRenewCardStatInfoByCardTypeId(BesRenewCardStatusVO renewCardStatusVO) {
        BesRenewCardStatusVO renewCardStatus = this.besRenewCardStatusDao.selectByCardTypeId(renewCardStatusVO.getCardTypeId());
        BeanUtils.copyProperties(renewCardStatusVO, renewCardStatus, new String[]{"status", "operationIds", "remark", "updatedTime","id"});
        int num = this.besRenewCardStatusDao.updateById(renewCardStatus);
        return num > 0 ? renewCardStatus : null;
    }

    @Override
    public BesRenewCardStatusVO updateRenewCardStatus(BesRenewCardStatusVO renewCardStatusVO, Integer userId) {
        BesRenewCardStatusVO renewCardStatus = this.besRenewCardStatusDao.selectById(renewCardStatusVO.getId());
        Map<String, String> dictionaries = this.sysDictionaryService.getSysDictionaryByType(BesRenewCardStatusVO.DICTIONARY_TYPE_FOR_RENEW);

        boolean addOperationLog = renewCardStatus != null
                && (!renewCardStatus.getStatus().equals(renewCardStatusVO.getStatus()) || !Strings.nullToEmpty(renewCardStatus.getRemark()).equals(renewCardStatusVO.getRemark()));

        // 更新日志列表
        if (addOperationLog) {
            String description = "由【" + dictionaries.get(renewCardStatus.getStatus()) + "】修改为【" + dictionaries.get(renewCardStatusVO.getStatus()) + "】";
            if (!Strings.isNullOrEmpty(renewCardStatusVO.getRemark())) {
                description += "，" + renewCardStatusVO.getRemark();
            }
            SysOperationLogVO operationLogVO = new SysOperationLogVO();
            operationLogVO.setMerchantId(renewCardStatus.getBrandId());
            operationLogVO.setModuleType("bes");
            operationLogVO.setFesLogType("33");
            operationLogVO.setOperatingTime(new Date());
            operationLogVO.setDescription(description);
            operationLogVO.setInsertBy(userId);
            operationLogVO.setInsertTime(new Date());
            sysOperationLogDao.saveSysOperationLog(operationLogVO);
            List<Integer> operationIds = Lists.asList(operationLogVO.getId(), renewCardStatus.getOperationIds());
            renewCardStatus.setOperationIds(operationIds.toArray(new Integer[operationIds.size()]));
        }

        // 更新状态
        renewCardStatus.setStatus(renewCardStatusVO.getStatus());
        renewCardStatus.setRemark(renewCardStatusVO.getRemark());
        renewCardStatus.setUpdatedTime(new Date());
        int num = this.besRenewCardStatusDao.updateById(renewCardStatus);
        return num > 0 ? renewCardStatus : null;
    }

    @Override
    public void deleteRenewCardStatusByIds(List<Integer> renewCardStatusIds) {
        if (CollectionUtils.isNotEmpty(renewCardStatusIds)) {
            this.besRenewCardStatusDao.delete(renewCardStatusIds);
        }
    }

    @Override
    public Page<BesRenewCardStatusVO> getBesRenewCardStatuses(RenewCardDTO dto) {
        return this.besRenewCardStatusDao.getBesRenewCardStatuses(dto);
    }
}

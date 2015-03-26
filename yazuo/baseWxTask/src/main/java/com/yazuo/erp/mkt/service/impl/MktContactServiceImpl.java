package com.yazuo.erp.mkt.service.impl;

import com.yazuo.erp.mkt.dao.MktContactDao;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVo;
import com.yazuo.erp.mkt.vo.UserInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class MktContactServiceImpl implements MktContactService {
    private Log log = LogFactory.getLog(MktContactServiceImpl.class);
    @Resource
    private MktContactDao contactDao;

    @Override
    public void synchronizeContactFromSopToErp() {
        List<String> mobilesForContact = this.contactDao.getAllMobileForContact();
        List<String> mobilesForCrm = this.contactDao.getMobilesForCrm();
        List<Integer> merchantIds = this.contactDao.getMerchantIdsForErp();
        List<String> toSearch = ListUtils.removeAll(mobilesForCrm, mobilesForContact);
        if (CollectionUtils.isNotEmpty(toSearch)) {
            List<UserInfoVo> userInfoVos = this.contactDao.getUserInfoVos(toSearch);
            List<MktContactVo> toSavedContact = new ArrayList<MktContactVo>();
            for (UserInfoVo userInfoVo : userInfoVos) {
                if (merchantIds.contains(userInfoVo.getMerchantId())) {
                    MktContactVo contactVo = new MktContactVo();
                    contactVo.setName(userInfoVo.getUserInfoName());
                    contactVo.setBirthday(null);
                    contactVo.setGenderType("1");
                    contactVo.setIsEnable("1");
                    contactVo.setMail(userInfoVo.getEmail());
                    contactVo.setMerchantId(userInfoVo.getMerchantId());
                    contactVo.setPosition(null);
                    contactVo.setMicroMessage(null);
                    contactVo.setMobilePhone(userInfoVo.getMobile());
                    contactVo.setRoleType("6");
                    contactVo.setInsertBy(999999);
                    contactVo.setInsertTime(new Date());
                    contactVo.setUpdateBy(999999);
                    contactVo.setUpdateTime(new Date());
                    toSavedContact.add(contactVo);
                    log.info("CRM同步联系人至ERP：" + contactVo);
                }
            }
            this.contactDao.saveContact(toSavedContact);
        } else {
            log.info("无新增联系人");
        }
    }
}

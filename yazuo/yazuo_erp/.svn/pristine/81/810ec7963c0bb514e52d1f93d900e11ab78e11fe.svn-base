package com.yazuo.erp.system.service.impl;

import com.google.common.base.Joiner;
import com.yazuo.erp.api.dao.ReportDataDao;
import com.yazuo.erp.api.service.SysReportParamsService;
import com.yazuo.erp.api.vo.SysReportParams;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.base.SinaShortLinkApi;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysSmsTemplateDao;
import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import com.yazuo.erp.system.vo.SysUserVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 */
@Service
public class SysSmsTemplateServiceImpl implements SysSmsTemplateService {

    @Resource
    private SysSmsTemplateDao sysSmsTemplateDao;

    @Resource
    private SysSalesmanMerchantService sysSalesmanMerchantService;

    @Resource
    private SysUserMerchantService sysUserMerchantService;

    @Resource
    private MktContactService mktContactService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysGroupService sysGroupService;

    @Resource
    private SysReportParamsService sysReportParamsService;

    @Resource
    private SendMessageVoid sendMessageVoid;

    @Resource
    private ReportDataDao reportDataDao;

    @Resource
    private SynMerchantService synMerchantService;

    private static final String H5_PREFIX = "/portal/origin/page/mobile/monthreport/monthreport.html";

    @Override
    public SysSmsTemplateVO saveSmsTemplate(SysSmsTemplateVO smsTemplateVO) {
        smsTemplateVO.setCreatedTime(new Date());
        this.sysSmsTemplateDao.saveSmsTemplate(smsTemplateVO);
        return smsTemplateVO;
    }

    @Override
    public SysSmsTemplateVO updateSmsTemplate(SysSmsTemplateVO smsTemplateVO) {
        this.sysSmsTemplateDao.updateSmsTemplate(smsTemplateVO);
        return smsTemplateVO;
    }

    @Override
    public SysSmsTemplateVO deleteSmsTemplate(Integer id) {
        SysSmsTemplateVO persistVO = this.sysSmsTemplateDao.getSmsTemplate(id);
        this.sysSmsTemplateDao.deleteSmsTemplate(id);
        return persistVO;
    }

    @Override
    public SysSmsTemplateVO getSmsTemplate(Integer id) {
        return this.sysSmsTemplateDao.getSmsTemplate(id);
    }

    @Override
    public List<SysUserVO> getSelectedUsers(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType) {
        SysSmsTemplateVO smsTemplateVO = this.sysSmsTemplateDao.getLastSmsTemplateByType(tplType.getVal());
        Set<Integer> userIds = new HashSet<Integer>();
        Set<Integer> userIdsForGroups = this.getGroupUserIds(smsTemplateVO);
        userIds.addAll(userIdsForGroups);

        Set<Integer> userIdsForPositionIds = this.getPositionUserIds(smsTemplateVO);
        userIds.addAll(userIdsForPositionIds);

        Set<Integer> userIdsForUserTypes = this.getUserTypeUserIds(smsTemplateVO, merchantId);
        userIds.addAll(userIdsForUserTypes);

        userIds.addAll(Arrays.asList(smsTemplateVO.getUserIds()));
        List<Integer> userIdList = Arrays.asList(userIds.toArray(new Integer[userIds.size()]));
        // 查询人的信息
        return userIdList.size() > 0 ? this.sysUserService.getUsersByIds(userIdList) : Collections.<SysUserVO>emptyList();
    }

    @Override
    public List<MktContactVO> getSelectedContactVO(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType) {
        SysSmsTemplateVO smsTemplateVO = this.sysSmsTemplateDao.getLastSmsTemplateByType(tplType.getVal());
        String[] roleTypes = smsTemplateVO.getRoleTypes();
        List<MktContactVO> result = null;
        // 查询联系人的信息
        if (roleTypes.length > 0) {
            result = this.mktContactService.getContactForMerchantRoles(Arrays.asList(roleTypes), merchantId);
        }
        return result == null ? Collections.<MktContactVO>emptyList() : result;
    }

    @Override
    public List<String> getAllMobiles(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType) {
        Set<String> mobiles = new HashSet<String>();
        List<SysUserVO> userVOs = this.getSelectedUsers(merchantId, tplType);
        List<MktContactVO> contactVOs = this.getSelectedContactVO(merchantId,tplType);
        for (SysUserVO userVO : userVOs) {
            mobiles.add(userVO.getTel());
        }
        for (MktContactVO contactVO : contactVOs) {
            mobiles.add(contactVO.getMobilePhone());
        }
        return Arrays.asList(mobiles.toArray(new String[mobiles.size()]));
    }


    @Override
    public Page<SysSmsTemplateVO> getAllSmsTemplate(SysSmsTemplateVO sysSmsTemplateVO) {
        return this.sysSmsTemplateDao.getAllTemplateVOs(sysSmsTemplateVO);
    }

    @Override
    public void sendMonthlySMS(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType, String appRoot, Date reportMonth) {
        SysSmsTemplateVO sysSmsTemplateVO = this.sysSmsTemplateDao.getLastSmsTemplateByType(tplType.getVal());

        // 短信模板不能为空
        boolean availSendSMS = sysSmsTemplateVO != null;
        // 商户已经上线
        if (availSendSMS) {
            SynMerchantVO merchantVO = this.synMerchantService.getSynMerchantById(merchantId);
            availSendSMS = availSendSMS && (merchantVO.getStatus().equals("1"));
        }
        // 开台数不能为空
        if (availSendSMS) {
            int total = this.reportDataDao.getAllOpenNum(merchantId, reportMonth);
            availSendSMS = availSendSMS && (total > 0);
        }

        if (availSendSMS) {
            List<String> mobiles = this.getAllMobiles(merchantId, tplType);
            String key = UUID.randomUUID().toString().replace("-", "");
            String url = appRoot + H5_PREFIX + "?key=" + key;
            String shortLink = SinaShortLinkApi.getShortLink(url);

            SysReportParams smsReportParams = new SysReportParams();
            smsReportParams.setMerchantId(merchantId);
            smsReportParams.setKey(key);
            smsReportParams.setShortLink(shortLink);
            smsReportParams.setCreatedTime(new Date());
            smsReportParams.setReportTime(DateUtils.truncate(reportMonth, Calendar.MONTH));
            smsReportParams.setIsExpired(false);
            this.sysReportParamsService.insert(smsReportParams);

            String smsContent = sysSmsTemplateVO.getContent();
            String content = smsContent + shortLink;
            if (CollectionUtils.isNotEmpty(mobiles)) {
                this.sendMessageVoid.sendMessage(content, Joiner.on(",").join(mobiles), LogFactory.getLog("sms"));
            }
        }
    }

    @Override
    public boolean isExist(SysSmsTemplateVO.TPL_TYPE tplType) {
        SysSmsTemplateVO vo = this.sysSmsTemplateDao.getLastSmsTemplateByType(tplType.getVal());
        return vo != null;
    }

    /**
     * 组对应的用户信息
     * @param templateVO
     * @return
     */
    protected Set<Integer> getGroupUserIds(SysSmsTemplateVO templateVO) {
        Integer[] groupIds = templateVO.getGroupIds();
        List<Integer> userIds = null;
        if (groupIds.length > 0) {
            userIds = this.sysGroupService.getUserIdsByGroupIds(Arrays.asList(groupIds));
            return new HashSet<Integer>(userIds);
        } else {
            return Collections.emptySet();
        }
    }

    /**
     * 职位对应的用户信息
     * @param templateVO
     * @return
     */
    protected Set<Integer> getPositionUserIds(SysSmsTemplateVO templateVO) {
        Integer[] positionIds = templateVO.getPositionIds();
        List<SysUserVO> userVOs = null;
        Set<Integer> result = new HashSet<Integer>();
        if (positionIds.length > 0) {
            userVOs = this.sysUserService.getAllUsersByPositionIds(Arrays.asList(positionIds));
            for (SysUserVO userVO : userVOs) {
                result.add(userVO.getId());
            }
        }
        return result;
    }

    /**
     * 商户负责人
     *
     * @param templateVO
     * @param merchantId
     * @return
     */
    protected Set<Integer> getUserTypeUserIds(SysSmsTemplateVO templateVO, Integer merchantId) {
        String[] userTypes = templateVO.getUserTypes();
        Set<Integer> userIds = new HashSet<Integer>();

        if (ArrayUtils.contains(userTypes, "1")) {// 销售负责人
            SysSalesmanMerchantVO merchantVO = this.sysSalesmanMerchantService.getSysSalesmanMerchantByMerchantId(merchantId);
            userIds.add(merchantVO.getUserId());
        }
        if (ArrayUtils.contains(userTypes, "2")) {//前端负责人
            SysUserVO userVO = this.sysUserMerchantService.getFesUserByMerchantId(merchantId);
            userIds.add(userVO.getId());
        }
        return userIds;
    }
}

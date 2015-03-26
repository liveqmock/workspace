package com.yazuo.erp.system.service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import com.yazuo.erp.system.vo.SysUserVO;

import java.util.Date;import java.util.List;

/**
 * 短信模板功能
 */
public interface SysSmsTemplateService {
    /**
     * 更新模板信息
     *
     * @param smsTemplateVO
     * @return
     */
    SysSmsTemplateVO saveSmsTemplate(SysSmsTemplateVO smsTemplateVO);

    /**
     * 更新模板信息
     *
     * @param smsTemplateVO
     * @return
     */
    SysSmsTemplateVO updateSmsTemplate(SysSmsTemplateVO smsTemplateVO);

    /**
     * 删除并返回被删除的短信模板信息
     *
     * @param id
     * @return
     */
    SysSmsTemplateVO deleteSmsTemplate(Integer id);

    /**
     * 查询短信模板
     *
     * @param id
     * @return
     */
    SysSmsTemplateVO getSmsTemplate(Integer id);

    /**
     * 得到公司的用户信息
     *
     * @param merchantId
     * @param tplType
     * @return
     */
    List<SysUserVO> getSelectedUsers(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType);

    /**
     * 得到联系人信息
     *
     * @param merchantId
     * @param tplType
     * @return
     */
    List<MktContactVO> getSelectedContactVO(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType);

    /**
     * 得到所有的手机信息
     *
     * @param merchantId
     * @param tplType
     * @return
     */
    List<String> getAllMobiles(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType);

    /**
     * 查询所有模板信息
     * @return
     * @param sysSmsTemplateVO
     */
    Page<SysSmsTemplateVO> getAllSmsTemplate(SysSmsTemplateVO sysSmsTemplateVO);


    void sendMonthlySMS(Integer merchantId, SysSmsTemplateVO.TPL_TYPE tplType, String appRoot, Date reportMonth);

    boolean isExist(SysSmsTemplateVO.TPL_TYPE tplType);

}

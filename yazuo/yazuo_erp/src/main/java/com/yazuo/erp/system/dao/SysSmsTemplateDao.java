package com.yazuo.erp.system.dao;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 筹集模板功能
 */
@Repository
public interface SysSmsTemplateDao {
    /**
     * 更新模板信息
     *
     * @param smsTemplateVO
     * @return
     */
    int saveSmsTemplate(SysSmsTemplateVO smsTemplateVO);

    /**
     * 更新模板信息
     *
     * @param smsTemplateVO
     * @return
     */
    int updateSmsTemplate(SysSmsTemplateVO smsTemplateVO);

    /**
     * 删除并返回被删除的短信模板信息
     *
     * @param id
     * @return
     */
    int deleteSmsTemplate(Integer id);

    /**
     * 查询短信模板
     *
     * @param id
     * @return
     */
    SysSmsTemplateVO getSmsTemplate(Integer id);

    /**
     * 查询短信模板功能
     *
     * @param sysSmsTemplateVO
     * @return
     */
    Page<SysSmsTemplateVO> getAllTemplateVOs(@Param("templateVO")SysSmsTemplateVO sysSmsTemplateVO);

    /**
     * 按类型查询短信模板
     *
     * @param tplType
     * @return
     */
    SysSmsTemplateVO getLastSmsTemplateByType(String tplType);

}

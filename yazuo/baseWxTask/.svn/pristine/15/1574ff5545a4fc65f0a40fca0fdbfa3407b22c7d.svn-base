package com.yazuo.erp.schedule;

import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.task.BaseTask;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 同步联系人信息
 */
public class ContactSynchronize extends BaseTask{
    @Resource
    private MktContactService contactService;
    @Override
    public void execute1(Map params) {
        this.contactService.synchronizeContactFromSopToErp();
    }
}

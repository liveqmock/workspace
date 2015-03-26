package com.yazuo.erp.schedule;

import com.yazuo.erp.util.HttpClientUtil;
import com.yazuo.task.BaseTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

/**
 * 扫描需求保存提醒
 */
public class ErpGenRemindForReq extends BaseTask {
    static Log log = LogFactory.getLog(HttpClientUtil.class);
    @Value("${erp.app.contextPath}")
    private String erpPrefix;
    @Override
    public void execute1(Map params) {
        String url = erpPrefix + String.valueOf(params.get("erpUri"));
        log.info("开始执行扫描需求保存提醒：" + new Date());
        long beginTime = System.currentTimeMillis();
        String result = HttpClientUtil.execRequestAndGetResponse(url);
        long endTime = System.currentTimeMillis();
        log.info("耗时:" + (endTime - beginTime));
        log.info("调用返回值:" + result);
    }
}

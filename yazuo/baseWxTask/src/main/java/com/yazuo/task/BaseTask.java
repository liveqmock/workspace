package com.yazuo.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luo on 2014/7/4.
 */
public abstract class BaseTask {
    Log log = LogFactory.getLog(this.getClass());

    Map params = new HashMap();

    String taskName;

    public abstract void execute1(Map params);

    public void execute(){
        log.info("---- 开始执行 " + taskName);
        try {
            execute1(params);
        } catch (Exception e) {
            log.error("---- 发生异常 " + taskName, e);
            return;
        }
        log.info("---- 执行完毕 " + taskName);
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}

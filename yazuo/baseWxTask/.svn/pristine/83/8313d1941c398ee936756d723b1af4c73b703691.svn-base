package com.yazuo.vo;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: lixueliang
 * Date: 11-10-26
 * Time: 上午9:40
 * To change this template use File | Settings | File Templates.
 */
public class TaskConfig {
    //任务的类
    private String taskImplClass;
    //多表达式支持
    private List<String> taskExpressList = new ArrayList<String>();
    //任务初始化参数，任务运行的时候，可以从JobDataMap对象中获取该字段的值。
    private Map params = new LinkedHashMap();
    //taskName
    private String taskName;

    public String getTaskImplClass() {
        return taskImplClass;
    }

    public void setTaskImplClass(String taskImplClass) {
        this.taskImplClass = taskImplClass;
    }

    public List<String> getTaskExpressList() {
        return taskExpressList;
    }

    public void setTaskExpressList(List<String> taskExpressList) {
        this.taskExpressList = taskExpressList;
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

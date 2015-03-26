package com.yazuo;

import com.yazuo.task.BaseTask;
import com.yazuo.utils.SpringUtil;
import com.yazuo.vo.TaskConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于spring的quartz封装重新实现
 * 单个task任务类，保证不会并分发执行
 * task类需要集成baseTask，并支持spring属性注入
 * Created by Luo on 2014/7/5.
 */

@Service
public class CustomTaskGenerator {
    Log log = LogFactory.getLog(this.getClass());
    @Autowired
    ApplicationContext applicationContext;
    static final String taskFile = Class.class.getClass().getResource("/").getPath() + "task.xml";
    static long modifiedTime = 0;
    StdScheduler inScheduler = null;

    public synchronized void initTask() {
        log.debug("开始检查定时任务配置文件");
        //首次检查时，初始化scheduler
        if (modifiedTime == 0){
            initSchedule();
            modifiedTime = System.currentTimeMillis();
            return;
        }

        //检查文件日期
        if (!configChanged()) {
            log.debug("配置文件文件无变化");
            return;
        } else {
            log.info("配置文件有变化 " + taskFile);
        }

        log.info("开始重新加载定时任务 ...");
        inScheduler.shutdown();
        initSchedule();
        log.info("清空定时任务完成");


    }

    private void initSchedule(){
        log.info("开始初始化 scheduler ...");

        List<TaskConfig> TaskConfigList = null;
        try {
            TaskConfigList = readConfigFile(taskFile);
        } catch (Exception e) {
            log.error("初始化定时任务失败，config.xml配置文件有误，请检查！", e);
            return;
        }
        log.info("配置文件读取完成，任务数=" + TaskConfigList.size());
        SpringUtil springUtil = new SpringUtil(applicationContext);
        final List<Trigger> triggers = new ArrayList<Trigger>();
        for (TaskConfig TaskConfig : TaskConfigList) {
            List<String> taskExpressList = TaskConfig.getTaskExpressList();
            log.info(String.format("开始添加任务 taskName=%s", TaskConfig.getTaskName()));
            String taskClass = TaskConfig.getTaskImplClass();
            String taskName = TaskConfig.getTaskName();

            //初始化quartz任务
            try {
                BaseTask task = null;
                try {
                    Object bean = springUtil.createBean(Class.forName(taskClass), taskName + "_task", null);
                    //验证task类型是否合法
                    if (bean instanceof BaseTask){
                        task = (BaseTask) bean;
                        task.setParams(TaskConfig.getParams());
                        task.setTaskName(taskName);
                    }
                } catch (Exception e) {
                    log.error("task实例创建失败 " + taskName, e);
                    break;
                }

                final BaseTask finalTask = task;
                final JobDetail job = (JobDetail) springUtil.createBean(MethodInvokingJobDetailFactoryBean.class, taskName + "_job", new SpringUtil.Callback() {
                    @Override
                    public void setProperties(MutablePropertyValues propertyValues) {
                        propertyValues.add("targetObject", finalTask);
                        propertyValues.add("targetMethod", "execute");
                        //不允许单个任务并发执行
                        propertyValues.add("concurrent", "false");
                    }
                });


                int index = 0;
                for (final String express : taskExpressList) {
                    CronTriggerBean trigger = (CronTriggerBean) springUtil.createBean(CronTriggerBean.class, taskName + "_trigger_" + index, new SpringUtil.Callback() {
                        @Override
                        public void setProperties(MutablePropertyValues propertyValues) {
                            propertyValues.add("jobDetail", job);
                            propertyValues.add("cronExpression", express);
                        }
                    });
                    triggers.add(trigger);
                    index ++;
                }

            } catch (Exception e) {
                log.error("创建定时任务失败，请检查配置文件！ taskName = " + taskName, e);
            }
            StdScheduler scheduler = (StdScheduler) springUtil.createBean(SchedulerFactoryBean.class, "taskScheduler", new SpringUtil.Callback() {
                @Override
                public void setProperties(MutablePropertyValues propertyValues) {
                    propertyValues.add("triggers", triggers);
                }
            });
            try {
                scheduler.start();
            } catch (SchedulerException e) {
                log.error("scheduler 起动失败，请检查配置文件！", e);
            }
            inScheduler = scheduler;

        }

        log.info("初始化 scheduler 完成");
    }


    private boolean configChanged() {
        boolean flag = false;
        File file = new File(taskFile);
        if (!file.exists()) {
            log.error(taskFile + "文件不存在");
        }
        if (file.isDirectory()) {
            log.error(taskFile + "文件夹");
        }
        long lastModifyTimes = file.lastModified();
        if (lastModifyTimes > modifiedTime) {
            flag = true;
            modifiedTime = lastModifyTimes;
        }
        return flag;
    }

    private List<TaskConfig> readConfigFile(String xmlPath) throws Exception {
        log.info("开始读取配置文件");
        List<TaskConfig> TaskConfigList = new ArrayList<TaskConfig>();
        Map<String, Integer> classMap = new HashMap<String, Integer>();
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(xmlPath));
        Element foo = doc.getRootElement();
        List<Element> allChildren = foo.getChildren();
        for (int i = 0; i < allChildren.size(); i++) {
            Element element = allChildren.get(i);
            //拼装taskName，taskName为className_${class_index}
            String taskClass = element.getChild("class").getTextTrim();
            if (!classMap.containsKey(taskClass)){
                classMap.put(taskClass, 0);
            } else {
                classMap.put(taskClass, classMap.get(taskClass) + 1);
            }
            String taskName = taskClass + "_" + classMap.get(taskClass);
            try {
                TaskConfig taskConfig = new TaskConfig();
                taskConfig.setTaskImplClass(taskClass);
                taskConfig.setTaskName(taskName);
                String open = element.getChild("open").getTextTrim();
                //开关判断
                if (!"t".equalsIgnoreCase(open)){
                    continue;
                }

                //多表达式支持
                if (element.getChild("cronExpress") != null && element.getChild("cronExpress").getTextTrim() != null) {
                    taskConfig.getTaskExpressList().add(element.getChild("cronExpress").getTextTrim());
                } else if (element.getChild("cronExpresses") != null) {
                    for (Object obj : element.getChild("cronExpresses").getChildren()) {
                        Element param = (Element) obj;
                        String conExpress = param.getTextTrim();
                        if (conExpress != null && conExpress.trim().length() > 0) {
                            taskConfig.getTaskExpressList().add(conExpress);
                        }
                    }
                } else {
                    throw new Exception("cronExpress或cronExpresses至少有一项不能为空，请检查配置文件");
                }

                //设置params
                if (element.getChild("params") != null) {
                    for (Object obj : element.getChild("params").getChildren()) {
                        Element param = (Element) obj;
                        String paramName = param.getName();

                        if ("list".equalsIgnoreCase(param.getAttributeValue("type"))) {
                            //解析list类型
                            List<String> values = new ArrayList();
                            for (Object obj1 : param.getChildren()) {
                                String value = ((Element) obj1).getText();
                                if (value != null && value.trim().length() > 0) {
                                    values.add(((Element) obj1).getText());
                                }
                            }
                            taskConfig.getParams().put(paramName, values);
                        } else {
                            //一般类型
                            taskConfig.getParams().put(paramName, param.getText());

                        }
                    }
                }
                TaskConfigList.add(taskConfig);
            } catch (Exception e) {
                log.error(String.format("读取任务配置异常 taskClass=%s，跳过该任务，请检查配置文件", taskClass), e);
            }
        }
        return TaskConfigList;
    }

}

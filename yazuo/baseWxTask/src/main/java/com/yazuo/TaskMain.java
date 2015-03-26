package com.yazuo;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Luo on 2014/4/10.
 */
public class TaskMain {
    static Logger log = Logger.getLogger(TaskMain.class);

    public static void main(String[] args) {
        log.info("开始加载spring ...");
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring.xml"});
        log.info("加载spring 完成");

    }
}

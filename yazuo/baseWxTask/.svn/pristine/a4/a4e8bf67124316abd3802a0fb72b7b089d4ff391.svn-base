package com.yazuo.utils;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * Created by Luo on 2014/7/5.
 */
public class SpringUtil {

    ApplicationContext applicationContext = null;

    public SpringUtil(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 利用spring容器手工创建指定类的实例，beanName不能重名
     * @param clazz
     * @param beanName
     * @param callback  回调接口，用于设置新实例的属性
     * @return
     */
    public Object createBean(Class clazz, String beanName, Callback callback){
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(clazz);
        definition.setAutowireCandidate(true);

        //通过回调接口实现类设置初始化property
        if (callback != null){
            MutablePropertyValues propertyValues = new MutablePropertyValues();
            callback.setProperties(propertyValues);
            definition.setPropertyValues(propertyValues);
        }

        registry.registerBeanDefinition(beanName, definition);
        Object bean = applicationContext.getBean(beanName);
        return bean;
    }

    public interface Callback {
        public void setProperties(MutablePropertyValues propertyValues);
    }
}

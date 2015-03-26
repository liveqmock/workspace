/**
 * Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/> Time: 上午11:40:10<br/>
 * To change this template use File | Settings | File Templates.<br/>
 */
package com.yazuo.superwifi.util;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;


/**
 * Java 反射的工具类 Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/> Time:
 * 上午11:40:10<br/> <br/>
 */
public class RefectUtils
{
    /**
     * 根据属性的名字，以及class对象获得Method对象的getMethod.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/>
     * Time: 上午11:44:22<br/>
     * 
     * @param fieldName
     * @param clazz
     * @return
     * @throws IntrospectionException
     *             <br/>
     */
    public static <T> Method getGetterMethod(String fieldName, Class<T> clazz)
    {
        try
        {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
            Method method = pd.getReadMethod();
            return method;
        }
        catch (IntrospectionException e)
        {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据属性的名字，以及class对象获得Method对象的setMethod.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/>
     * Time: 上午11:46:50<br/>
     * 
     * @param fieldName
     * @param clazz
     * @return
     * @throws IntrospectionException
     *             <br/>
     */
    public static <T> Method getSetterMethod(String fieldName, Class<T> clazz)
    {
        try
        {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
            Method method = pd.getWriteMethod();
            return method;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据属性的名字，以及class对象获得属性的值.<br/> User: huijun.zheng<br/> Date: 2014-3-20<br/> Time:
     * 下午4:51:47<br/>
     * 
     * @param fieldName
     * @param clazz
     * @return<br/>
     */
    public static <T> Object getFieldValue(String fieldName, Class<T> clazz, Object obj)
    {
        try
        {
            Method getMethod = RefectUtils.getGetterMethod(fieldName, clazz);
            return getMethod.invoke(obj, new Object[] {});
        }
        catch (Exception e)
        {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的名字.<br/> User: huijun.zheng<br/> Date: 2014-3-20<br/> Time: 下午5:20:43<br/>
     * 
     * @param clazz
     * @param isCamelFormat
     *            是否转换为驼峰式写法，并首字母转换为小写，true是，false,否
     * @return<br/>
     */
    public static <T> String getClassName(Class<T> clazz, Boolean isCamelFormat)
    {
        int len = clazz.getName().lastIndexOf(".");
        String className = clazz.getName().substring(len + 1);
        if (true == isCamelFormat)
        {
            return StringUtils.toUnderlineString(className);
        }
        else
        {
            return className;
        }
    }
}

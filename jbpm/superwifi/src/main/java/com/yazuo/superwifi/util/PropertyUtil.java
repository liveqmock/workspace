package com.yazuo.superwifi.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 加载属性配置文件工具类 Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2013-11-12<br/> Time:
 * 下午4:34:48<br/><br/>
 */
public class PropertyUtil
{
    private static final PropertyUtil instance = new PropertyUtil();

    private static final String defaultPropertiesFileName = "/config.properties";

    /**
     * 存放所有的属性配置文件对象
     */
    private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    private PropertyUtil()
    {}

    /**
     * 获得属性配置文件工具类的实例对象
     */
    public static PropertyUtil getInstance()
    {
        return instance;
    }

    /**
     * 获取Properties文件对象<br/> User: huijun.zheng<br/> Date: 2013-11-12<br/> Time: 下午4:46:26<br/>
     * 
     * @param propertiesFileName
     * @return
     * @throws IOException
     *             <br/>
     */
    public synchronized Properties getProperties(String propertiesFileName)
        throws IOException
    {
        return getProperties(propertiesFileName, true);
    }

    /**
     * 是否每次都重新加载一次配置文件，isSingleton为true表示否，反之则是.<br/> User: huijun.zheng<br/> Date: 2013-12-25<br/>
     * Time: 下午4:25:24<br/>
     * 
     * @param propertiesFileName
     * @param isSingleton
     * @return
     * @throws IOException
     *             <br/>
     */
    public synchronized Properties getProperties(String propertiesFileName, boolean isSingleton)
        throws IOException
    {
        if (propertiesFileName == null || "".equals(propertiesFileName))
            throw new IllegalArgumentException("The propertiesFileName is illegal argument!");

        if (!"/".equals(propertiesFileName.substring(0, 1)))
        {
            propertiesFileName = "/" + propertiesFileName;
        }

        Properties prop = null;

        if (isSingleton)// 表示只从配置文件读取一次
        {
            prop = propertiesMap.get(propertiesFileName);

            if (prop == null)
            {
                prop = new Properties();
                InputStream is = this.getClass().getResourceAsStream(propertiesFileName);
                if (is == null)
                    throw new FileNotFoundException("The file \"" + propertiesFileName
                                                    + "\" is  not found!");
                try
                {
                    prop.load(is);
                }
                catch (IOException e)
                {
                    throw e;
                }
                finally
                {
                    try
                    {
                        is.close();
                    }
                    catch (IOException e)
                    {
                        throw new IOException("file can not close", e);
                    }
                }
                propertiesMap.put(propertiesFileName, prop);
            }
        }
        else
        // 每次读取属性时都从配置文件进行，从新读取
        {
            prop = new Properties();
            InputStream is = this.getClass().getResourceAsStream(propertiesFileName);
            if (is == null)
                throw new FileNotFoundException("The file \"" + propertiesFileName
                                                + "\" is  not found!");
            try
            {
                prop.load(is);
            }
            catch (IOException e)
            {
                throw e;
            }
            finally
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    throw new IOException("file can not close", e);
                }
            }
            propertiesMap.put(propertiesFileName, prop);
        }

        return prop;
    }

    /**
     * 根据属性配置文件名称和属性名称获取属性配置值<br/> User: huijun.zheng<br/> Date: 2013-11-12<br/> Time:
     * 下午4:47:18<br/>
     * 
     * @param propertiesFileName
     *            属性配置文件名称
     * @param propertyName
     *            属性名称
     * @return 属性值
     * @throws IOException
     *             <br/>
     */
    public String getProperty(String propertiesFileName, String propertyName)
        throws IOException
    {
        Properties prop = getProperties(propertiesFileName);
        return prop.getProperty(propertyName);
    }

    /**
     * 根据属性配置文件名称和属性名称获取属性配置值.<br/> User: huijun.zheng<br/> Date: 2013-12-25<br/> Time:
     * 下午4:32:14<br/>
     * 
     * @param propertiesFileName
     * @param propertyName
     * @param isSingleton
     *            false表示每次读取属性值，从重新加载一次配置文件，true则仅仅加载一次
     * @return
     * @throws IOException
     *             <br/>
     */
    public String getProperty(String propertiesFileName, String propertyName, boolean isSingleton)
        throws IOException
    {
        Properties prop = getProperties(propertiesFileName, isSingleton);
        return prop.getProperty(propertyName);
    }

    /**
     * 根据属性名称获取属性配置值<br/> User: huijun.zheng<br/> Date: 2013-11-12<br/> Time: 下午4:50:15<br/>
     * 
     * @param propertyName
     *            属性名称
     * @return
     * @throws IOException
     *             <br/>
     */
    public String getProperty(String propertyName)
    {
        try
        {
            return getProperty(defaultPropertiesFileName, propertyName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据属性名称获取属性配置值.<br/> User: huijun.zheng<br/> Date: 2013-12-25<br/> Time: 下午4:36:14<br/>
     * 
     * @param propertyName
     * @param isSingleton
     *            false表示每次读取属性值，从重新加载一次配置文件，true则仅仅加载一次
     * @return<br/>
     */
    public String getProperty(String propertyName, boolean isSingleton)
    {
        try
        {
            return getProperty(defaultPropertiesFileName, propertyName, isSingleton);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

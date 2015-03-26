/*
 * 文件名：JsonResult.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-10-20 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhaohuaqin
 * @version 2014-10-20
 * @see JsonResult
 * @since
 */

public class JsonResult
{
    private boolean flag;

    private String message;

    private Object data;

    public JsonResult()
    {}

    /**
     * @param flag
     */
    public JsonResult(boolean flag)
    {
        super();
        this.flag = flag;
    }

    /**
     * @param flag
     * @param message
     */
    public JsonResult(boolean flag, String message)
    {
        super();
        this.flag = flag;
        this.message = message;
    }

    public int getFlag()
    {
        return flag ? 1 : 0;
    }

    public JsonResult setFlag(boolean flag)
    {
        this.flag = flag;
        return this;
    }

    public String getMessage()
    {
        return message;
    }

    public JsonResult setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public Object getData()
    {
        if (null == data)
        {
            data = new HashMap<String, Object>();
        }
        return data;
    }

    public JsonResult setData(Object data)
    {
        this.data = data;
        return this;
    }

    public JsonResult putData(String name, Object value)
    {
        Map<String, Object> map = this.initData(HashMap.class);
        map.put(name, value);
        return this;
    }

    /**
     * 向List类型data中添加数据
     * 
     * @param object
     * @return
     */
    public JsonResult addData(Object object)
    {
        List<Object> list = this.initData(ArrayList.class);
        list.add(object);
        return this;
    }

    private <E> E initData(Class<?> classes)
    {
        if (this.data == null)
        {
            try
            {
                this.data = classes.newInstance();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return (E)this.data;
    }

}

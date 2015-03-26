/**
 * @Description TODO Copyright Copyright (c) 2014 Company 雅座在线（北京）科技发展有限公司 author date description
 *              —————————————————————————————————————————————
 */
package com.yazuo.superwifi.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * @Description Timestamp 处理器
 * @author song
 * @date 2014-6-6 下午1:09:10
 */
public class DateJsonValueProcessor implements JsonValueProcessor
{

    private String datePattern = "yyyy-MM-dd";

    public DateJsonValueProcessor()
    {
        super();
    }

    public DateJsonValueProcessor(String format)
    {
        super();
        this.datePattern = format;
    }

    public Object processArrayValue(Object value, JsonConfig jsonConfig)
    {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig)
    {
        return process(value);
    }

    private Object process(Object value)
    {
        try
        {
            if (value instanceof Date)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.UK);
                return sdf.format((Date)value);
            }
            return value == null ? "" : value.toString();
        }
        catch (Exception e)
        {
            return "";
        }

    }

    public String getDatePattern()
    {
        return datePattern;
    }

    public void setDatePattern(String pDatePattern)
    {
        datePattern = pDatePattern;
    }

}
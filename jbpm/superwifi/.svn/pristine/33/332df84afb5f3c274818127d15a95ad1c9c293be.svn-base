/**
 * Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/> Time: 上午11:08:48<br/>
 * To change this template use File | Settings | File Templates.<br/>
 */
package com.yazuo.superwifi.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * BaseDAO的工具类 Created with MyElipse.<br/> User: huijun.zheng<br/> Date: 2013-12-16<br/> Time:
 * 上午11:08:48<br/> <br/>
 */
public class StringUtils
{
    /**
     * 去掉字符的后缀.<br/> User: huijun.zheng<br/> Date: 2013-12-15<br/> Time: 下午9:32:01<br/>
     * 
     * @param str
     * @param suffix
     * @return<br/>
     */
    public static String removeSuffix(String str, String suffix)
    {
        if (isEmpty(str)) return str;

        suffix = suffix.trim();

        int len = str.lastIndexOf(suffix);

        if (-1 != len)
        {
            str = str.substring(0, len);
        }
        return str;
    }

    /**
     * 获取属性名称的get方法.<br/> User: huijun.zheng<br/> Date: 2013-12-15<br/> Time: 下午9:12:02<br/>
     * 
     * @param fieldName
     *            <br/>
     */
    public static String getGetMethodName(String fieldName)
    {
        if (isEmpty(fieldName))
        {
            return fieldName;
        }

        String firstLetter = fieldName.substring(0, 1).toUpperCase();

        return "get" + firstLetter + fieldName.substring(1);
    }

    /**
     * 判断字符是否为空.<br/> User: huijun.zheng<br/> Date: 2013-12-15<br/> Time: 下午9:11:07<br/>
     * 
     * @param str
     * @return<br/>
     */
    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    /**
     * 将带有下划线的字符串转换为驼峰式命名规则的字符串
     * 
     * @author huijun.zheng
     * @since 2012-11-18
     * @param s
     * @return
     */
    public static String toCamelCaseString(String s)
    {
        if (isEmpty(s))
        {
            return s;
        }
        Pattern p = Pattern.compile("_[a-z]");
        StringBuffer sb = new StringBuffer(s);
        Matcher c = p.matcher(s);
        int i = 0;
        while (c.find())
        {
            sb.replace(c.start() - i, c.end() - i, c.group().substring(1).toUpperCase());
            i++ ;
        }
        return sb.toString();
    }

    /**
     * 将所有大写字母转换为“_小写字母”，首字母不加下划线，如：<br> userId --> user_id <br/> toURL --> to_u_r_l <br/> ABCD -->
     * a_b_c_d <br/>
     * 
     * @param s
     * @return
     */
    public static String toUnderlineString(String s)
    {
        if (isEmpty(s))
        {
            return s;
        }
        Pattern p = Pattern.compile("[A-Z]");
        StringBuffer sb = new StringBuffer(s);
        Matcher c = p.matcher(s);
        int i = 0;
        while (c.find())
        {
            sb.replace(c.start() + i, c.end() + i, "_" + c.group().toLowerCase());
            i++ ;
        }
        if ('_' == sb.charAt(0))
        {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}

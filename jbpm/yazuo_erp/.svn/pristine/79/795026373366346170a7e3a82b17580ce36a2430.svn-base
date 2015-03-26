package com.yazuo.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String removeLast(String str) {
		str = str.trim();
		return str.substring(0, str.length() - 1);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static int getRandom6() {
		Random ran = new Random();
		int r = 0;
		m1: while (true) {
			int n = ran.nextInt(1000000);
			r = n;
			int[] bs = new int[6];
			for (int i = 0; i < bs.length; i++) {
				bs[i] = n % 10;
				n /= 10;
			}
			Arrays.sort(bs);
			for (int i = 1; i < bs.length; i++) {
				if (bs[i - 1] == bs[i]) {
					continue m1;
				}
			}
			break;
		}
		return r;
	}

	public static String md5(String str) {
		try {
			byte[] btInput = str.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16)
					sb.append("0");
				sb.append(Integer.toHexString(val));
			}
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			return str;
		}
	}

	public static int getCurrentTimeHashCode() {
		return  String.valueOf(System.currentTimeMillis()).hashCode();
	}
	
	public static String randomNumString(int len) {
		final int maxNum = 10;
		char[] str = { '0', '1', '2', '3', '5', '6', '7', '8', '9' };
		StringBuffer randStr = new StringBuffer("");
		Random r = new Random();
		for(int i=0,count = 0;count<len;i++){
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				randStr.append(str[i]);
				count++;
			}
		}
		return randStr.toString();
	}
	
	public static String format(String format,Object... objects ){
		return String.format(format,objects);
	}

	/**
	 *去掉字符的后缀.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2013-12-15<br/>
	 * Time: 下午9:32:01<br/>
	 *
	 * @param str
	 * @param suffix
	 * @return<br/>
	 */
	public static String removeSuffix(String str,String suffix)
	{
		if(isEmpty(str)) return str;
		
		suffix = suffix.trim();
		
		int len = str.lastIndexOf(suffix);
		
		if(-1 != len)
		{
			str = str.substring(0,len);
		}
		return str;
	}
	
	/**
	 * 
	 *获取属性名称的get方法.<br/>
	 *
	 * User: huijun.zheng<br/>
	 * Date: 2013-12-15<br/>
	 * Time: 下午9:12:02<br/>
	 *
	 * @param fieldName<br/>
	 */
	public static String getGetMethodName(String fieldName)
	{
		if (isEmpty(fieldName))
		{
			return fieldName;
		}

		String firstLetter = fieldName.substring(0, 1).toUpperCase();

		return "get"+firstLetter + fieldName.substring(1);
	}
	/**
     * 将带有下划线的字符串转换为驼峰式命名规则的字符串
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
			i++;
		}
		return sb.toString();
	}
    
	/**
	 * 将所有大写字母转换为“_小写字母”，首字母不加下划线，如：<br>
     * userId --> user_id <br/>
     * toURL --> to_u_r_l <br/>
     * ABCD --> a_b_c_d <br/>
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
			sb.replace(c.start() + i, c.end() + i, "_"
					+ c.group().toLowerCase());
			i++;
		}
		if ('_' == sb.charAt(0))
		{
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

}

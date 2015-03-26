package com.yazuo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *加密工具类
 *
 *@auther 		Gao ZhongJie<gaozhongjie@yazuo.com>
 *@version 		v1.0
 *@copyright 	Copyright(c)2012,yazuo.com
 *@modifier 	Gao ZhongJie<gaozhongjie@yazuo.com>
 *@lastmodifide 2012-3-22 上午10:22:06
 */
public class EncryptUtil
{
	
	static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	private static IA300ServerScript iaServer = new IA300ServerScript();
	
	/**
	 * 用3des加密字符串
	 * 
	 * @param secretKey		密钥
	 * @param objectJson	要加密的字符串，通常为Object转json的字符串
	 * @return				密文
	 * @throws UnsupportedEncodingException
	 * @author Gao ZhongJie<gaozhongjie@yazuo.com>
	 */
	public static String encrypt3DesObject(String secretKey, String objectJson) throws UnsupportedEncodingException
	{
		byte[] pBuffer = Base64.encode(objectJson.getBytes("UTF-8"));
		String json = new String(pBuffer, "UTF-8");
		if("".equals(secretKey)){
			return json;
		}
		return iaServer.EncryptTripleDesBase64(secretKey, json);
	}
	/**
	 * 用3des解密字符串
	 * 
	 * 
	 * @param secretKey		密钥
	 * @param json			要解密的字符串
	 * @return				明文，通常为一个json字符串
	 * @throws UnsupportedEncodingException
	 * @author Gao ZhongJie<gaozhongjie@yazuo.com>
	 */
	public static String dncrypt3DesString(String secretKey, String json) throws UnsupportedEncodingException
	{
		
		json = URLDecoder.decode(json,"UTF-8");
		if("".equals(secretKey)){
			return json;
		}
		String decryptStr = iaServer.DecryptBase64TripleDes(secretKey, json);
		String result = Base64.decode(decryptStr,"UTF-8");
		return result;
	}
	
}

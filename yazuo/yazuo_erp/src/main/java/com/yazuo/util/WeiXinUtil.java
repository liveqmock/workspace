package com.yazuo.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yazuo.erp.demo.vo.Signature;

/**
 * @ClassName: WeiXinUtil
 * @Description: 微信工具类
 * 
 */
public class WeiXinUtil {

	/**
	 * token 是否合法
	 * 
	 * @param st
	 * @param token
	 * @return
	 */
	public static boolean checkUser(Signature st, String token) {
		String signature = st.getSignature();
		String timestamp = st.getTimestamp();
		String nonce = st.getNonce();
		String[] sList = new String[] { token, timestamp, nonce };
		Arrays.sort(sList);
		String s = sList[0] + sList[1] + sList[2];
		SHA1 sha1 = new SHA1();
		String code = sha1.getDigestOfString(s.getBytes());
		if (signature.equals(code)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否是手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 是否是数字
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNum(String num) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(num);
		return m.matches();
	}

}

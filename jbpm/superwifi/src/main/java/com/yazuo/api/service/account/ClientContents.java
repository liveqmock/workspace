package com.yazuo.api.service.account;

public class ClientContents {
	/**
	 * 3种登陆方式
	 * 
	 * @author libin
	 * 
	 */
	public enum LoginType {
		MOBILE(1), USERNAME(2), EMAIL(3), ID(4);
		private int code;

		private LoginType(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}

	/**
	 * 登陆返回码
	 * 
	 * @author libin 0：登陆成功， 1： 临时密码登陆成功 2：临时密码登陆超时 3：密码错误 , 4:不支持登录方式
	 */
	public enum LoginCode {
		SUCCESS(0), TEMPPASSWD(1), TIMEOUT(2), ERROR(3), NOSUPPORT(4);
		private int code;

		private LoginCode(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}
}

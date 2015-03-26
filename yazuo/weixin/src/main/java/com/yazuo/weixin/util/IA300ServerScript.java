package com.yazuo.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class IA300ServerScript {
	// IV为3des加密解密所需要的明钥,在此默认为8个0
	private static byte[] iv = new byte[] { (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00 };
	// 加密解密的方式
	private static String information = "DESEDE/CBC/PKCS5Padding";//

	/**
	 * newPassWordTreeDes：增加新密码 _TreeDesKey:3des密钥 _TreeDesValue： 传递过来的申请解密信息
	 * newPassWord：修改后的新密码
	 * */
	public String GenerateResetPasswordResponse(String triDesKey,
			String resetRequest, String newPassword) {
		String TreeDesDeValue = this.DecryptBase64TripleDes(triDesKey,
				resetRequest);
		String newPassWordDes = "ERROR！";
		if (TreeDesDeValue != "") {
			String[] sArray = TreeDesDeValue.split(";");
			String _IA300Guid = sArray[0];
			String _IA300Random = sArray[1];
			String _IA300RandomTwo = sArray[2];
			String _newPwd = newPassword;
			String TreeDesEnValue = _IA300Guid + ";" + _IA300Random + ";"
					+ _IA300RandomTwo + ";" + _newPwd + ";";
			newPassWordDes = this.EncryptTripleDesBase64(triDesKey,
					TreeDesEnValue);
		}
		return newPassWordDes;
	}

	/**
	 * 进行3des加密后再base64编码操作 fKey为3des密钥,从数据库取出 fValue为需要加密的值
	 * 返回经过3des加密后再base64编码后的值 若是要存储带中文的字符进安全存储区，需要将byte[] des_byte_cbc =
	 * cipher3.doFinal(fValue.getBytes())修改为byte[] des_byte_cbc =
	 * cipher3.doFinal(fValue.getBytes("UnicodeLittleUnmarked"));
	 * **/
	public String EncryptTripleDesBase64(String fKey, String fValue) {
		Base64 _base64 = new Base64();// 实例BASE64类
		try {
			SecretKey key = getDESKey(fKey);
			IvParameterSpec iv_param_spec = new IvParameterSpec(iv);
			// CBC加密，带初始化向量的加密方法
			Cipher cipher3 = Cipher.getInstance(information);
			cipher3.init(Cipher.ENCRYPT_MODE, key, iv_param_spec);
			byte[] des_byte_cbc = cipher3.doFinal(fValue.getBytes("UTF-8"));
			String des_string_cbc = new String(_base64.encode(des_byte_cbc));
			return des_string_cbc;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 进行base64解码后进行3des解密 操作 fKey为3des密钥,从数据库取出 fValue为需要解密的值
	 * 返回经过base64解密后再3des解密的值
	 * **/
	public String DecryptBase64TripleDes(String fKey, String fValue) {

		try {

			Base64 _base64 = new Base64();// 实例BASE64类
			byte[] En_byte = Base64.decode(fValue.getBytes("UTF-8"));

			SecretKey key = getDESKey(fKey);
			IvParameterSpec iv_param_spec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(information);
			cipher.init(Cipher.DECRYPT_MODE, key, iv_param_spec);
			byte[] des_byte_cbc = cipher.doFinal(En_byte);
			return new String(des_byte_cbc);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String GenerateRandomString() {
		// 产生随机数
		String _RndData;
		int b = 0;
		int a = 0;
		_RndData = "";
		java.util.Random r = new java.util.Random();
		for (int i = 0; i < 32; i++) {
			a = r.nextInt(26);
			b = (char) (a + 65);
			_RndData += new Character((char) b).toString();
		}
		return _RndData;
	}

	public int CheckHashValues(String clientHashValue, String userSeed,
			String randomStr) {

		String serverSHA1 = ServerSHA1(randomStr + userSeed);
		if (serverSHA1.equals(clientHashValue)) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 服务端SHA1算法 Str为需要进行SHA1算法的值 返回经过SHA1算法后的值
	 * */
	private String ServerSHA1(String StrRandomAddUserSeed) {
		MessageDigest sha;
		try {
			sha = MessageDigest.getInstance("SHA-1");
			sha.update(StrRandomAddUserSeed.getBytes());
			byte[] digesta = sha.digest();
			return byte2hex(digesta);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * byte数组转换字符串 传入参数为需要转换的数组 返回 转换后的字符串
	 * */
	private String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toLowerCase();
	}

	/**
	 * 获取密钥并判断密钥长度，如果不是24位密钥，则以“0”补齐 key_string为需要传入的3des字符串密钥 返回最后经过处理的3des密钥
	 * **/
	private static SecretKey getDESKey(String key_string)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		SecretKey key = null;
		String zeros = "000000000000000000000000";
		if (key_string != null) {
			int keylength = key_string.getBytes().length;
			if (keylength < 24) {
				key_string += zeros.substring(keylength);
			}
		} else {
			return null;
		}
		DESedeKeySpec dks = new DESedeKeySpec(key_string.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		key = keyFactory.generateSecret(dks);
		return key;
	}
}
package com.yazuo.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {
	public static String inputStream2String(InputStream is) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is,
					"utf-8"));
			
			String line = "";

			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
}

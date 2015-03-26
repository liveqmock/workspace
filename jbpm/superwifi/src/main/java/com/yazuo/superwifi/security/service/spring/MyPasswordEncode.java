/*
 * 文件名：MyPasswordEncode.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2015年1月28日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.security.service.spring;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class MyPasswordEncode extends MessageDigestPasswordEncoder
{
    
    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    public MyPasswordEncode(String algorithm)
    {
        super(algorithm);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    // 如果返回true，则验证通过。
    public boolean isPasswordValid(String savePass, String submitPass,
            Object salt) {
        return true;
    }
    
}

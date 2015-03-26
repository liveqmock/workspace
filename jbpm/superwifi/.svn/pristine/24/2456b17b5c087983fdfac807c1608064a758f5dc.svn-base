/*
 * 文件名：UserService.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年12月1日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.security.service;


import java.util.Map;

import com.yazuo.api.service.account.vo.User;
import com.yazuo.superwifi.pagehelper.Page;


public interface UserService
{
    Page<Map<String, Object>> listUsers(Map<String, Object> map)
        throws Exception;

    User addOrUpdateUser(Map<String, Object> map)
        throws Exception;

    void deleteUser(Map<String, Object> map)
        throws Exception;

    void changePwd(Map<String, Object> map)
        throws Exception;

    void resetUserPasswd(String mobileNumber)
        throws Exception;
}

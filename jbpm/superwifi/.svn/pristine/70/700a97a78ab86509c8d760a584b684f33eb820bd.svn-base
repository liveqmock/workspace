/*
 * 文件名：UserController.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-12-5 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.security.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.api.service.account.exception.AccountCheckedException;
import com.yazuo.superwifi.pagehelper.Page;
import com.yazuo.superwifi.security.service.UserService;
import com.yazuo.superwifi.util.JsonResult;


@Controller
@RequestMapping("/controller/user")
public class UserController
{
    Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;

    /**
     * 添加或修改用户
     */
    @RequestMapping(value = "addOrUpdateUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object addOrUpdateUser(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        try
        {
            userService.addOrUpdateUser(map);
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        catch (AccountCheckedException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        return new JsonResult(true).setMessage("处理成功");
    }

    /**
     * 用户列表
     */
    @RequestMapping(value = "listUsers", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult listUsers(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        Page<Map<String, Object>> usersList = userService.listUsers(map);
        return new JsonResult(true).setMessage("查询成功").putData("rows", usersList).putData("totalSize",
            usersList.getTotal());
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "deleteUser", method = {RequestMethod.POST})
    @ResponseBody
    public Object deleteUser(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        try
        {
            userService.deleteUser(map);
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        catch (AccountCheckedException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        return new JsonResult(true).setMessage("删除成功");
    }

    /**
     * 用户密码修改
     * 
     * @throws Exception
     */
    @RequestMapping(value = "changePwd", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult changePwd(@RequestBody
    Map<String, Object> map, Authentication authentication)
        throws Exception
    {
        try
        {
            com.yazuo.superwifi.security.vo.PhoneUserDetails user = ((com.yazuo.superwifi.security.vo.PhoneUserDetails)authentication.getPrincipal());
            Map<String, Object> userMap = user.getUser();
            Boolean isSetLoginPwd = (Boolean)userMap.get("isSetLoginPwd");
            map.put("pwdFlag", !isSetLoginPwd);
            userService.changePwd(map);
            user.getUser().put("isSetLoginPwd", false);
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        catch (AccountCheckedException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        return new JsonResult(true).setMessage("密码修改成功");
    }

    /**
     * 找回密码
     * 
     * @throws Exception
     */
    @RequestMapping(value = "resetUserPasswd", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult resetUserPasswd(@RequestBody
                                      Map<String, Object> map)
        throws Exception
    {
        String mobileNumber = (String)map.get("mobileNumber");
        try
        {
            userService.resetUserPasswd(mobileNumber);
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        catch (AccountCheckedException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        return new JsonResult(true).setMessage("找回密码成功");
    }
}

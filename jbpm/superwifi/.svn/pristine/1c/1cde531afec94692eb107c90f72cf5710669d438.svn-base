/**
 * <p>Project: weixin</p> <p>Copyright:</p> <p>Company: yazuo</p>
 * 
 * @author zc
 * @date 2014-03-26 HISTORY:...
 */
package com.yazuo.superwifi.security.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yazuo.api.service.account.ClientContents;
import com.yazuo.api.service.account.user.UserAuthWebService;
import com.yazuo.api.service.account.user.UserWebService;
import com.yazuo.api.service.account.vo.User;
import com.yazuo.api.service.account.vo.userInfo.UserInfoVo;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.pagehelper.Page;
import com.yazuo.superwifi.pagehelper.PageHelper;
import com.yazuo.superwifi.security.dao.UserDao;
import com.yazuo.superwifi.security.service.UserService;


/**
 * @InterfaceName: UserServiceImpl
 * @Description: 处理用户逻辑的实现
 */
@Service("userService")
public class UserServiceImpl implements UserService
{

    Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantServiceImpl;

    @Value("#{propertiesReader['superwiki_productId']}")
    int superwikiProductId;

    @Resource
    private UserWebService userWebService;

    @Resource
    private UserAuthWebService userAuthWebService;

    @Override
    public Page<Map<String, Object>> listUsers(Map<String, Object> map)
        throws Exception
    {
        Integer merchantId = (Integer)map.get("merchantId");
        String userInfoName = (null != map.get("userInfoName")) ? map.get("userInfoName").toString() : null;
        Integer pageNumber = (Integer)map.get("pageNumber");
        Integer pageSize = (Integer)map.get("pageSize");

        // 1、根据商户id查询商户信息及下级商户信息
        JSONArray merchantList = merchantServiceImpl.getMerchantList(merchantId);

        // 2、得到商户下级商户id列表
        List<Integer> merIds = new ArrayList<Integer>();
        Map<Integer, Object> merchantMap = new HashMap<Integer, Object>();
        for (int i = 0; i < merchantList.size(); i++ )
        {
            JSONObject merchant = (JSONObject)merchantList.get(i);
            if (merchant.getInt("status") == 1)
            {
                merIds.add(merchant.getInt("merchantId"));
                merchantMap.put(merchant.getInt("merchantId"), merchant);
            }
        }

        // 3、用户查询
        PageHelper.startPage(pageNumber, pageSize, true);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("list", merIds);
        paramMap.put("userInfoName", userInfoName);
        paramMap.put("productId", superwikiProductId);
        List<Map<String, Object>> userList = userDao.getUsersByTerm(paramMap);
        for (Map<String, Object> userMap : userList)
        {
            Integer merchant_id = (Integer)userMap.get("merchantId");
            JSONObject mer = (JSONObject)merchantMap.get(merchant_id);
            userMap.put("merchantName", mer.getString("merchantName"));
        }
        return (Page<Map<String, Object>>)userList;
    }

    @Override
    public User addOrUpdateUser(Map<String, Object> map)
        throws Exception
    {
        // 1、参数校验
        Assert.notNull(map.get("userInfoName"), "请输入用户姓名");
        Assert.notNull(map.get("mobile"), "请输入手机号");
        Assert.notNull(map.get("merchantId"), "请选择门店");

        User user = new User();
        user.setUserInfoName(map.get("userInfoName").toString());
        user.setMobile(map.get("mobile").toString());
        user.setProductId(superwikiProductId);
        user.setMerchantId((Integer)map.get("merchantId"));
        Object userInfoId = map.get("userInfoId");
        if (null != userInfoId && (((Integer)userInfoId).intValue() != 0))
        {
            user.setUserInfoId((Integer)userInfoId);
        }
        Object auths = map.get("authorityIds");
        if (null != auths)
        {
            List<Integer> authorityIds = (List<Integer>)auths;
            user.setAuthorityIdList(authorityIds);
        }
        log.info(auths);
        return userWebService.addOrUpdateUserForWifi(user);
    }

    @Override
    public void deleteUser(Map<String, Object> map)
        throws Exception
    {
        Object userIdList = map.get("userIdList");
        Assert.notNull(map.get("userIdList"), "请选择需删除的用户");
        List<Integer> userIds = (List<Integer>)userIdList;
        Assert.notEmpty(userIds, "请选择需删除的用户");
        userWebService.deleteUser(userIds, superwikiProductId);
    }

    @Override
    public void changePwd(Map<String, Object> map)
        throws Exception
    {
        // 1、参数校验
        Assert.notNull(map.get("userInfoId"), "用戶id不能为空");
        Assert.notNull(map.get("oldPassWord"), "原密码不能为空");
        Assert.notNull(map.get("newPassWord"), "新密码不能为空");
        Assert.notNull(map.get("pwdFlag"), "密码标识不能为空");

        Integer userId = (java.lang.Integer)map.get("userInfoId");
        String oldPasswd = (String)map.get("oldPassWord");
        String passwd = (String)map.get("newPassWord");
        boolean flag = (Boolean)map.get("pwdFlag");
        userWebService.updateUserPasswd(userId, oldPasswd, passwd, flag);
    }

    @Override
    public void resetUserPasswd(String mobileNumber)
        throws Exception
    {
        Assert.notNull(mobileNumber, "请输入手机号");
      //  com.yazuo.superwifi.security.dto.User user = userDao.loadUserByMobileNumber(mobileNumber);
        UserInfoVo user = userAuthWebService.getUser(mobileNumber, ClientContents.LoginType.MOBILE.getCode());
        if (null == user)
        {
            throw new IllegalArgumentException("该用户不存在");
        }
        else
        {
            Integer userId = user.getUserInfoId();
            userWebService.resetUserPasswd(userId);
        }
    }

}

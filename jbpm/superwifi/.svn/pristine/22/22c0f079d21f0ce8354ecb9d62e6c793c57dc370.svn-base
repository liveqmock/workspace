package com.yazuo.superwifi.security.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.yazuo.superwifi.merchant.dao.MerchantDao;
import com.yazuo.superwifi.security.dao.ResourceDao;
import com.yazuo.superwifi.security.dao.RoleDao;
import com.yazuo.superwifi.security.dao.RoleResourceDao;
import com.yazuo.superwifi.security.dao.UserDao;
import com.yazuo.superwifi.security.dao.UserRoleDao;
import com.yazuo.superwifi.security.dto.Role;
import com.yazuo.superwifi.security.service.SecurityService;
import com.yazuo.superwifi.security.service.spring.MySecurityMetadataSource;
import com.yazuo.superwifi.sms.dao.SmsLogDao;


@Service("securityServiceImpl")
public class SecurityServiceImpl implements SecurityService
{
    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "roleDao")
    private RoleDao roleDao;

    @Resource(name = "resourceDao")
    private ResourceDao resourceDao;

    @Resource(name = "roleResourceDao")
    private RoleResourceDao roleResourceDao;

    @Resource(name = "userRoleDao")
    private UserRoleDao userRoleDao;

    @Resource(name = "merchantDao")
    private MerchantDao merchantDao;

    @Resource(name = "smsLogDao")
    private SmsLogDao smsLogDao;

    @Resource(name = "sessionRegistry")
    private SessionRegistry sessionRegistry;

    @Resource(name = "securityMetadataSource")
    private MySecurityMetadataSource mySecurityMetadataSource;

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    /**
     * 保存资源和权限的对应关系 key-资源url value-权限
     */
    private Map<String, List<Role>> resourceMap = new HashMap<String, List<Role>>();

    private static final Logger LOG = Logger.getLogger(SecurityServiceImpl.class);

}

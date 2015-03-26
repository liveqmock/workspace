package com.yazuo.superwifi.security.service.spring;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yazuo.api.service.account.ClientContents;
import com.yazuo.api.service.account.user.UserAuthWebService;
import com.yazuo.api.service.account.vo.LoginUser;
import com.yazuo.api.service.account.vo.authority.AuthorityVo;
import com.yazuo.api.service.account.vo.userInfo.UserInfoVo;
import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.security.dao.UserDao;
import com.yazuo.superwifi.security.vo.PhoneUserDetails;
import com.yazuo.superwifi.util.StringList;


@Service
public class MyUserDetailServiceImpl implements UserDetailsService
{

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;
    @Resource
    private UserAuthWebService userAuthWebService;

    // 超级wifi产品号
    @Value("#{propertiesReader['superwiki_productId']}")
    Integer superwikiProductId;

    private static final Logger LOG = Logger.getLogger(MyUserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String mobileNumber)
        throws UsernameNotFoundException
    {

        LOG.info("---------MyUserDetailServiceImpl:loadUserByUsername------正在加载用户名和密码，登陆手机号为：" + mobileNumber);

        //User user = userDao.loadUserByMobileNumber(mobileNumber);
        UserInfoVo userInfo =null;
        try
        {
            //userAuthWebService.resetCache();
            userInfo =userAuthWebService.getUser(mobileNumber, ClientContents.LoginType.MOBILE.getCode());
        }
        catch (Exception e)
        {
            throw new BussinessException("调用dubbo出错", e);
        }
        if (userInfo == null)
        {
            LOG.info("用户名没有找到!");
            throw new BadCredentialsException("账号未找到!");
        }

        boolean enabled = userInfo.getAvailableFlag(); // 是否可用
        boolean accountNonExpired = false; // 是否过期
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        // String password = (StringUtils.isNotBlank(user.getPassword())) ? user.getPassword() : user.getTempPwd();
        Integer merchantId = userInfo.getMerchantId();

        // 查询商户信息，判断商户状态是否正常
        JSONObject mer = merchantService.getMerchantByMerchantId(merchantId);
        Integer brandId = mer.getInt("brandId");
        Integer faceShopType = null;
        if (mer.getBoolean("isFaceShop"))
        {
            faceShopType = 3;
        }
        else
        {
            if (mer.getInt("merchantId") == mer.getInt("brandId"))
            {
                faceShopType = 1;
            }
            else
            {
                faceShopType = 2;
            }
        }
        // 查询用户对应的商户是否开通超级wiki
        // List<Map<String, Object>> list = userDao.getMerchantProductByMerchantIdAndProId(merchantId,
        // superwikiProductId);

        Boolean isOpenedWifi = false;
        try
        {
            isOpenedWifi = merchantService.selectAuthForMerchant(merchantId);
        }
        catch (Exception e)
        {
            throw new BadCredentialsException("验证商户信息时出错");
        }
        if (!isOpenedWifi)
        {
            LOG.info("用户所在商户没有开通超级WIKI!");
            throw new BadCredentialsException("用户所在门店没有开通雅座回头宝产品!");
        }

        // 判断用户密码是否为临时密码且已过期
        /* String password = null;
        Boolean isSetLoginPwd = false;// 用户登陆后是否需要设置登陆密码
        String cipherPasswd = user.getCipherPasswd();
        String tempPwd = user.getTempPwd();
        if (StringUtils.isEmpty(cipherPasswd))
        {
            if (StringUtils.isEmpty(tempPwd))
            {
                throw new BadCredentialsException("用户密码为空，请点击找回密码重新获取");
            }
            else
            {
                long tempPwdExpireTime = user.getTempPwdExpireTime();
                if (System.currentTimeMillis() > tempPwdExpireTime)
                {
                    throw new BadCredentialsException("您的临时密码已过期，请点击找回密码重新获取");
                }
                else
                {
                    password = DigestUtils.md5Hex(tempPwd);
                    isSetLoginPwd = true;
                }
            }

        }
        else
        {
            password = cipherPasswd;
        }*/

        Set<AuthorityVo> authorityVoSet;
        try
        {
            authorityVoSet = userAuthWebService.getUserAuth(userInfo.getUserInfoId(),superwikiProductId);
        }
        catch (Exception e)
        {
            throw new BussinessException("调用dubbo出错", e);
        }
        if(CollectionUtils.isEmpty(authorityVoSet)){
            throw new BadCredentialsException("抱歉，您还没有登录权限。请联系老板或店长为您开通权限。");
        }
        
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        // 查询用户所拥有的资源列表
        StringList auths = new StringList();
        for (AuthorityVo av:authorityVoSet){
            auths.add(av.getAuthorityCode());
            GrantedAuthority ga = new SimpleGrantedAuthority(av.getAuthorityCode());
            authorities.add(ga);
        }
        
        Boolean havePassWord = merchantService.isHavePassWord(merchantId);
        Boolean needPassword = mer.getBoolean("isFaceShop") && !havePassWord;

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("faceShopType", faceShopType);
        userMap.put("id", userInfo.getUserInfoId());
        userMap.put("userName", userInfo.getUserInfoName());
        userMap.put("merchantId", merchantId);
        userMap.put("brandId", brandId);
        userMap.put("needSetSupervisor", needPassword);
        userMap.put("isSetLoginPwd", false);
        PhoneUserDetails userDetails = new PhoneUserDetails(userInfo.getUserInfoName(), "", enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities, userInfo.getUserInfoId(), brandId, userInfo.getMobile(), "",
            auths, merchantId, userMap);
        return userDetails;
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String mobileNumber,String password)
        throws UsernameNotFoundException
    {
        LOG.info("---------MyUserDetailServiceImpl:loadUserByUsername------正在加载用户名和密码，登陆手机号为：" + mobileNumber);
        LoginUser loginUser=null;
        try
        {
            loginUser = userAuthWebService.login(mobileNumber, password, ClientContents.LoginType.MOBILE.getCode());
        }
        catch (Exception e)
        {
            throw new BussinessException("dubbo 调用失败",e);
        }
        
        // 0：登陆成功， 1： 临时密码登陆成功 2：临时密码登陆超时 3：密码错误 , 4:不支持登录方式
        if (loginUser.getCode() == 2)
        {
            throw new BussinessException("您的临时密码已过期，请点击找回密码重新获取");
        }
        else if (loginUser.getCode() == 3)
        {
            throw new BussinessException("用户名或密码错误，请重新输入!");
        }
        else if (loginUser.getCode() == 4)
        {throw new BussinessException("不支持登录方式");}
        else
        {
            UserInfoVo userInfo = loginUser.getUserInfoVo();
            boolean enabled = userInfo.getAvailableFlag(); // 是否可用
            boolean accountNonExpired = false; // 是否过期
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            // String password = (StringUtils.isNotBlank(user.getPassword())) ? user.getPassword() : user.getTempPwd();
            Integer merchantId = userInfo.getMerchantId();

            // 查询商户信息，判断商户状态是否正常
            JSONObject mer = merchantService.getMerchantByMerchantId(merchantId);
            Integer brandId = mer.getInt("brandId");
            Integer faceShopType = null;
            if (mer.getBoolean("isFaceShop"))
            {
                faceShopType = 3;
            }
            else
            {
                if (mer.getInt("merchantId") == mer.getInt("brandId"))
                {
                    faceShopType = 1;
                }
                else
                {
                    faceShopType = 2;
                }
            }

            Boolean isOpenedWifi = false;
            try
            {
                isOpenedWifi = merchantService.selectAuthForMerchant(merchantId);
            }
            catch (Exception e)
            {
               // throw new BadCredentialsException("验证商户信息时出错");
                throw new BussinessException("dubbo 调用失败",e);
            }
            if (!isOpenedWifi)
            {
                LOG.info("用户所在商户没有开通超级WIKI!");
                throw new BadCredentialsException("用户所在门店没有开通雅座回头宝产品!");
            }

            Set<AuthorityVo> authorityVoSet;
            try
            {
                authorityVoSet = userAuthWebService.getUserAuth(userInfo.getUserInfoId(),superwikiProductId);
            }
            catch (Exception e)
            {
                throw new BussinessException("调用dubbo出错", e);
            }
            if(CollectionUtils.isEmpty(authorityVoSet)){
                throw new BadCredentialsException("抱歉，您还没有登录权限。请联系老板或店长为您开通权限。");
            }
            
            Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            // 查询用户所拥有的资源列表
            StringList auths = new StringList();
            for (AuthorityVo av:authorityVoSet){
                auths.add(av.getAuthorityCode());
                GrantedAuthority ga = new SimpleGrantedAuthority(av.getAuthorityCode());
                authorities.add(ga);
            }
            
            Boolean havePassWord = merchantService.isHavePassWord(merchantId);
            Boolean needPassword = mer.getBoolean("isFaceShop") && !havePassWord;

            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("faceShopType", faceShopType);
            userMap.put("id", userInfo.getUserInfoId());
            userMap.put("userName", userInfo.getUserInfoName());
            userMap.put("merchantId", merchantId);
            userMap.put("brandId", brandId);
            userMap.put("needSetSupervisor", needPassword);
            if (loginUser.getCode() == 0){
                userMap.put("isSetLoginPwd", false);
            }else if (loginUser.getCode() == 1){
                userMap.put("isSetLoginPwd", true);
            }else{
                throw new BussinessException("错误的状态码");
            }
            
            PhoneUserDetails userDetails = new PhoneUserDetails(userInfo.getUserInfoName(), "", enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities, userInfo.getUserInfoId(), brandId, userInfo.getMobile(), "",
                auths, merchantId, userMap);
            return userDetails;
        }
    }
}

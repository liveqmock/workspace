package com.yazuo.superwifi.security.vo;


import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

import com.yazuo.superwifi.util.StringList;


public class PhoneUserDetails extends BaseUserDetails
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer brandId;

    private String mobileNumber;

    private String userPosition;

    private StringList authorityList;

    private Integer merchantId;

    private Map<String, Object> user;

    /**
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param enabled
     *            是否可用
     * @param accountNonExpired
     *            是否为过期
     * @param credentialsNonExpired
     *            凭证是否未过期
     * @param accountNonLocked
     *            未锁定
     * @param authorities
     *            权限
     * @param userId
     *            用户ID
     * @param brandId
     *            品牌ID
     * @param mobileNumber
     *            手机号
     * @param userPosition
     *            用户职别
     * @param needPassword
     *            用户所在门店是否需要设置管理密码
     * @param isSetLoginPwd
     *            登陆后用户是否需设置登陆密码
     * @throws IllegalArgumentException
     */
    public PhoneUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                            boolean credentialsNonExpired, boolean accountNonLocked,
                            Collection<? extends GrantedAuthority> authorities, Integer userId, Integer brandId,
                            String mobileNumber, String userPosition, StringList authorityList, Integer merchantId,
                            Map<String, Object> userMap)
        throws IllegalArgumentException
    {
        super(username, password, enabled, enabled, enabled, enabled, authorities);
        this.id = userId;
        this.brandId = brandId;
        this.mobileNumber = mobileNumber;
        this.userPosition = userPosition;
        this.authorityList = authorityList;
        this.merchantId = merchantId;
        this.user = userMap;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getBrandId()
    {
        return brandId;
    }

    public void setBrandId(Integer brandId)
    {
        this.brandId = brandId;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getUserPosition()
    {
        return userPosition;
    }

    public void setUserPosition(String userPosition)
    {
        this.userPosition = userPosition;
    }

    /**
     * @return Returns the authorityList.
     */
    public StringList getAuthorityList()
    {
        return authorityList;
    }

    /**
     * @param authorityList
     *            The authorityList to set.
     */
    public void setAuthorityList(StringList authorityList)
    {
        this.authorityList = authorityList;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof PhoneUserDetails)
        {
            if (this.id.equals(((PhoneUserDetails)object).getId())) return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    /**
     * @return Returns the merchantId.
     */
    public Integer getMerchantId()
    {
        return merchantId;
    }

    /**
     * @param merchantId
     *            The merchantId to set.
     */
    public void setMerchantId(Integer merchantId)
    {
        this.merchantId = merchantId;
    }

    /**
     * @return Returns the user.
     */
    public Map<String, Object> getUser()
    {
        return user;
    }

    /**
     * @param user
     *            The user to set.
     */
    public void setUser(Map<String, Object> user)
    {
        this.user = user;
    }

}

package com.yazuo.superwifi.security.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.superwifi.security.dto.User;
import com.yazuo.superwifi.util.StringList;


@Repository("userDao")
public interface UserDao
{
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User loadUserByMobileNumber(String mobileNumber);
    
    User loadUserByUserId(Integer userId);

    List<Map<String, Object>> getUsersByTerm (Map<String, Object> map);

    List<Map<String, Object>> getMerchantProductByMerchantIdAndProId(@Param("merchantId")
    Integer merchantId, @Param("productId")
    Integer productId);

    StringList getAuthByUserId(@Param("userId")
    Integer userId, @Param("productId")
    Integer productId);
}
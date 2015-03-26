package com.yazuo.superwifi.security.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.superwifi.security.dto.Resource;
import com.yazuo.superwifi.util.StringList;


@Repository("resourceDao")
public interface ResourceDao
{
    int deleteByPrimaryKey(Integer id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    List<Map<String, Object>> listAllResources(@Param("productId")
    Integer productId);

    List<Integer> getAuthoritiesByUserId(@Param("userId")
    Integer userId, @Param("productId")
    Integer productId);

    StringList listAllAuthorityOfPrivilege(@Param("productId")
    Integer productId);

    List<Map<String, Object>> listAllProductAndAuthority(@Param("productId")
    Integer productId);
}
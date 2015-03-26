package com.yazuo.superwifi.security.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.superwifi.security.dto.Role;


@Repository("roleDao")
public interface RoleDao
{
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> findUserRolesByMobileNumber(String mobileNumber);

    List<Role> findUserRolesByUserId(@Param("userId")
    Integer userId, @Param("productId")
    Integer productId);

    List<Map<String, Object>> findRolesByBrandIdAndRoleName(@Param("brandId")
    Integer brandId, @Param("roleName")
    String roleName);
}
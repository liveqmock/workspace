package com.yazuo.erp.bes.dao;

import com.yazuo.erp.bes.controller.vo.RenewCardDTO;
import com.yazuo.erp.bes.vo.BesRenewCardStatusVO;
import com.yazuo.erp.interceptors.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BesRenewCardStatusDao {
    public List<Integer> getAllCardTypeIds();

    int insert(BesRenewCardStatusVO record);

    BesRenewCardStatusVO selectById(Integer id);

    BesRenewCardStatusVO selectByCardTypeId(Integer id);

    int delete(@Param("renewCardStatusIds") List<Integer> renewCardStatusIds);

    int updateById(BesRenewCardStatusVO record);

    Page<BesRenewCardStatusVO> getBesRenewCardStatuses(@Param("dto") RenewCardDTO dto);
}
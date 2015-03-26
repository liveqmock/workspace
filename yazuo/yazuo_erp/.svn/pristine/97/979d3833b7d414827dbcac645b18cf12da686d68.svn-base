package com.yazuo.erp.bes.service;

import com.yazuo.erp.bes.controller.vo.RenewCardDTO;
import com.yazuo.erp.bes.vo.BesRenewCardStatusVO;
import com.yazuo.erp.interceptors.Page;

import java.util.List;

/**
 *
 */
public interface BesRenewCardStatusService {
    List<Integer> getAllCardTypeIds();

    BesRenewCardStatusVO saveRenewCardStatus(BesRenewCardStatusVO renewCardStatusVO);

    BesRenewCardStatusVO getRenewCardStatusById(Integer statusId);

    BesRenewCardStatusVO updateRenewCardStatInfoByCardTypeId(BesRenewCardStatusVO renewCardStatusVO);

    BesRenewCardStatusVO updateRenewCardStatus(BesRenewCardStatusVO renewCardStatusVO,Integer userId);

    void deleteRenewCardStatusByIds(List<Integer> renewCardStatusIds);

    Page<BesRenewCardStatusVO> getBesRenewCardStatuses(RenewCardDTO dto);

}

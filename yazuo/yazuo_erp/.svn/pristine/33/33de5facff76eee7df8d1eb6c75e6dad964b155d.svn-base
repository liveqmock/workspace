package com.yazuo.erp.bes.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.controller.vo.RenewCardDTO;
import com.yazuo.erp.bes.controller.vo.RenewCardHistoryDTO;
import com.yazuo.erp.bes.controller.vo.RenewCardReturnDTO;
import com.yazuo.erp.bes.service.BesRenewCardStatusService;
import com.yazuo.erp.bes.vo.BesRenewCardStatusVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 续卡管理
 */
@Controller
@RequestMapping("/besRenewCard")
public class BesRenewCardController extends BesBasicController {
    @Resource
    private BesRenewCardStatusService renewCardStatusService;

    @Resource
    private SysUserMerchantService sysUserMerchantService;

    @Resource
    private SysDictionaryService sysDictionaryService;

    @Resource
    private SynMerchantService synMerchantService;

    @Resource
    private SysOperationLogService sysOperationLogService;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("search")
    @ResponseBody
    public JsonResult search(@RequestBody RenewCardDTO dto) {
        return this.searchRenewCard(dto);
    }

    @RequestMapping("searchAll")
    @ResponseBody
    public JsonResult searchAll(@RequestBody RenewCardDTO dto) {
        Map<String, String> statuses = this.sysDictionaryService.getSysDictionaryByType(BesRenewCardStatusVO.DICTIONARY_TYPE_FOR_RENEW);
        statuses.put("0", "全部状态");

        Map<String, String> cardTypes = this.sysDictionaryService.getSysDictionaryByType(BesRenewCardStatusVO.DICTIONARY_TYPE_FOR_CARD_TYPE);
        cardTypes.put("0", "全部分类");

        return this.searchRenewCard(dto).putData("statuses", statuses).putData("cardtypes", cardTypes);
    }

    protected JsonResult searchRenewCard(RenewCardDTO dto) {
        this.preRenewCardDTO(dto);
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize(), true, true);
        Page<BesRenewCardStatusVO> paginate = this.renewCardStatusService.getBesRenewCardStatuses(dto);

        Set<Integer> merchantIds = new HashSet<Integer>();
        for (BesRenewCardStatusVO statusVO : paginate.getResult()) {
            merchantIds.add(statusVO.getBrandId());
        }
        List<Integer> merchantIdList = Lists.newArrayList(merchantIds);
        Map<Integer, String> users = this.sysUserMerchantService.getFesUserByMerchantIds(merchantIdList); //前端人员
        Map<Integer, String> merchants = this.synMerchantService.getMerchantNamesFromErp(merchantIdList);//商户名称
        Map<String, String> dictionaries = this.sysDictionaryService.getSysDictionaryByType(BesRenewCardStatusVO.DICTIONARY_TYPE_FOR_RENEW);//状态中文

        List<RenewCardReturnDTO> rows = new ArrayList<RenewCardReturnDTO>();
        for (BesRenewCardStatusVO statusVO : paginate.getResult()) {
            RenewCardReturnDTO returnDTO = new RenewCardReturnDTO();
            BeanUtils.copyProperties(statusVO, returnDTO);
            returnDTO.setStatusCn(dictionaries.get(returnDTO.getStatus()));
            returnDTO.setMerchantName(merchants.get(returnDTO.getBrandId()));
            returnDTO.setUsername(users.get(returnDTO.getBrandId()));
            rows.add(returnDTO);
        }
        return new JsonResult(true).putData(Constant.ROWS, rows).putData(Constant.TOTAL_SIZE, paginate.getTotal());
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult updateRenewCardStatus(@RequestBody BesRenewCardStatusVO renewCardStatus, HttpSession session) {
        Integer userId = this.getUserId(session);
        this.renewCardStatusService.updateRenewCardStatus(renewCardStatus, userId);
        return new JsonResult(true);
    }

    @RequestMapping("getHistory")
    @ResponseBody
    public JsonResult getHistory(@RequestBody RenewCardHistoryDTO historyDTO) {
        if (historyDTO.getEndDate() != null) {
            historyDTO.setEndDate(DateUtils.addDays(historyDTO.getEndDate(), 1));
        }
        BesRenewCardStatusVO statusVO = this.renewCardStatusService.getRenewCardStatusById(historyDTO.getId());
        if (statusVO.getOperationIds().length > 0) {
            PageHelper.startPage(historyDTO.getPageNumber(), historyDTO.getPageSize(), true, true);
            Page<SysOperationLogVO> pagination = this.sysOperationLogService.getSysOperationByTypeAndIds(
                    Arrays.asList(statusVO.getOperationIds()),
                    BesRenewCardStatusVO.FES_LOG_TYPE,
                    historyDTO.getBeginDate(),
                    historyDTO.getEndDate());

            return new JsonResult(true).putData(Constant.ROWS, this.getResult(pagination.getResult())).putData(Constant.TOTAL_SIZE, pagination.getTotal());
        } else {
            return new JsonResult(true).putData(Constant.ROWS, Collections.emptyList()).putData(Constant.TOTAL_SIZE, 0);
        }
    }

    protected void preRenewCardDTO(RenewCardDTO dto) {
        if ("0".equals(dto.getCardType())) {
            dto.setCardType(null);
        }
        if ("0".equals(dto.getMerchantStatus())) {
            dto.setMerchantStatus(null);
        }
    }

    /**
     * 返回操作日志的信息
     * @param result
     * @return
     */
    protected Object getResult(List<SysOperationLogVO> result) {
        List<Integer> userIds = Lists.transform(result, new Function<SysOperationLogVO, Integer>() {
            @Override
            public Integer apply(SysOperationLogVO input) {
                return input.getInsertBy();
            }
        });
        List<SysUserVO> users = this.sysUserService.getUsersByIds(userIds);
        final  Map<Integer, SysUserVO> usersIndex = Maps.uniqueIndex(users, new Function<SysUserVO, Integer>() {
            @Override
            public Integer apply(SysUserVO input) {
                return input.getId();
            }
        });
        return Lists.transform(result, new Function<SysOperationLogVO, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(SysOperationLogVO input) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("description", input.getDescription());
                map.put("operatingTime", input.getOperatingTime());
                SysUserVO userVO = usersIndex.get(input.getInsertBy());
                map.put("username", ObjectUtils.defaultIfNull(userVO.getUserName(), ""));
                return map;
            }
        });
    }
}
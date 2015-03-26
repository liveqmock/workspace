package com.yazuo.erp.system.controller;

import com.google.common.collect.ImmutableMap;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.ViewUtils;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.controller.dto.SysSmsTemplateDTO;
import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.erp.system.vo.SysSmsTemplateVO;
import com.yazuo.erp.system.vo.SysUserVO;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * 短信模板功能
 */
@Controller
@RequestMapping("smsTemplate")
public class SysSmsTemplateController extends SysBasicController {
    private static final String CONTACT_ROLE_CODE = "00000065";
    private static final String USER_TYPE_CODE = "00000123";
    private static final String TEMPLATE_TYPE_CODE = "00000122";
    @Resource
    private SysSmsTemplateService smsTemplateService;

    @Resource
    private SysDictionaryService dictionaryService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysPositionService sysPositionService;

    @Resource
    private SysGroupService sysGroupService;

    @RequestMapping("get")
    @ResponseBody
    public JsonResult getSmsTemplates(HttpSession session, @RequestBody SysSmsTemplateVO templateVO) {
        SysSmsTemplateVO smsTemplateVO = this.smsTemplateService.getSmsTemplate(templateVO.getId());
        smsTemplateVO.setRoleTypes(ArrayUtils.nullToEmpty(smsTemplateVO.getRoleTypes()));
        smsTemplateVO.setUserTypes(ArrayUtils.nullToEmpty(smsTemplateVO.getUserTypes()));
        smsTemplateVO.setGroupIds(ArrayUtils.nullToEmpty(smsTemplateVO.getGroupIds()));
        smsTemplateVO.setPositionIds(ArrayUtils.nullToEmpty(smsTemplateVO.getPositionIds()));
        smsTemplateVO.setUserIds(ArrayUtils.nullToEmpty(smsTemplateVO.getUserIds()));
        return new JsonResult(true, "查询完成").setData(smsTemplateVO);
    }

    @RequestMapping("getObjects")
    @ResponseBody
    public JsonResult getObjects() {
        //字典列表查询
        Map<String, String> contentRoles = this.dictionaryService.getSysDictionaryByType(CONTACT_ROLE_CODE);
        Map<String, String> userTypes = this.dictionaryService.getSysDictionaryByType(USER_TYPE_CODE);

        //部门、职位、用户列表,并去除不用的信息
        List<SysGroupVO> groupVOs = this.sysGroupService.getAllSysGroupVOs();
        List<SysPositionVO> positionVOs = this.sysPositionService.queryPositionList();
        List<SysUserVO> userVOs = this.sysUserService.getSysUserList();
        return new JsonResult(true, null)
                .putData("roleTypes", ViewUtils.mapToList(contentRoles))
                .putData("userTypes", ViewUtils.mapToList(userTypes))
                .putData("groups", ViewUtils.getAttributes(groupVOs, "id", "groupName", "parentId"))
                .putData("positions", ViewUtils.getAttributes(positionVOs, "id", "positionName"))
                .putData("users", ViewUtils.getAttributes(userVOs, "id", "userName", "tel"));
    }

    @RequestMapping("add")
    @ResponseBody
    public JsonResult
    addSmsTemplate(HttpSession session, @RequestBody SysSmsTemplateVO templateVO) {
        this.smsTemplateService.saveSmsTemplate(templateVO);
        return new JsonResult(true, "保存成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonResult updateSmsTemplate(HttpSession session, @RequestBody SysSmsTemplateVO templateVO) {
        this.smsTemplateService.updateSmsTemplate(templateVO);
        return new JsonResult(true, "保存成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonResult deleteSmsTemplate(HttpSession session, @RequestBody SysSmsTemplateVO templateVO) {
        this.smsTemplateService.deleteSmsTemplate(templateVO.getId());
        return new JsonResult(true, "删除完成");
    }

    @RequestMapping("search")
    @ResponseBody
    public JsonResult searchSmsTemplate(@RequestBody SysSmsTemplateDTO templateDTO) {
        PageHelper.startPage(templateDTO.getPageNumber(), templateDTO.getPageSize(), true, true);
        Page<SysSmsTemplateVO> pagination = this.smsTemplateService.getAllSmsTemplate(templateDTO);
        List<Map<String, Object>> objects = ViewUtils.getAttributes(pagination.getResult(), "id", "title", "createdTime", "tplType");
        return new JsonResult(true, "查询完成").putData("resultSet", ImmutableMap.of(
                Constant.TOTAL_SIZE, pagination.getTotal(),
                Constant.ROWS, this.getResult(objects)));
    }

    @RequestMapping("getTplTypes")
    @ResponseBody
    public JsonResult getTplTypes() {
        Map<String, String> tplTypes = this.dictionaryService.getSysDictionaryByType(TEMPLATE_TYPE_CODE);
        return new JsonResult(true, "模板类型").putData("tplTypes", ViewUtils.mapToList(tplTypes));
    }

    private List<Map<String, Object>> getResult(List<Map<String, Object>> records) {
        Map<String, String> tplTypes = this.dictionaryService.getSysDictionaryByType(TEMPLATE_TYPE_CODE);
        for (Map<String, Object> record : records) {
            String id = record.get("tplType").toString();
            record.put("tplTypeCn", tplTypes.get(id));
        }
        return records;
    }
}

/*
 * 文件名：MemberController.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年8月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.member.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.member.service.MemberService;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.util.JsonResult;


@Controller
@RequestMapping("/controller/member")
public class MemberController
{
    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    @Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;
    
    
    /**
     * 会员增量统计
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "memberAddStatic", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult memberAddStatic(@RequestBody Map<String,Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        jsoninfo = memberService.memberAddStatic(map);
        return jsoninfo;
    }
    
    /**
     * 导出会员信息
     */
    @RequestMapping(value = "exportMemberInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult exportMemberInfo(@RequestBody Map<String,Object> map, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        JsonResult js = memberService.exportMemberInfo(map, request,response);
    	return js;
    } 
    /**
     * 获取老板手机号
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBossMobile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getBossMobile(@RequestBody Map<String,Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Map<String,Object> mapp = null;
        mapp = memberService.getBossMobile(map);
        jsoninfo.setFlag(true).setMessage("查询老板手机号成功").setData(mapp);
        return jsoninfo;
    }
    /**
     * 设置会员增量统计报表发送的手机号
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "setMemberSendMobile", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult setMemberSendMobile(@RequestBody Map<String,Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        memberService.setMemberSendMobile(map);
        return jsoninfo.setFlag(true).setMessage("设置统计发送的手机号成功");
    }
}

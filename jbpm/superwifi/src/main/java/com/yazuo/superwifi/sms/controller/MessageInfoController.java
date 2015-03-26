/*
 * 文件名：MerchantController.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月31日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.sms.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.sms.service.MessageInfoService;
import com.yazuo.superwifi.sms.vo.MessageInfo;
import com.yazuo.superwifi.util.JsonResult;


@Controller
@RequestMapping("/controller/messageInfo")
public class MessageInfoController
{
    @Resource(name = "messageInfoServiceImpl")
    private MessageInfoService messageInfoService;

    @RequestMapping(value = "addMessageInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object addMessageInfo(@RequestBody
    MessageInfo messageInfo)
        throws Exception
    {
        JsonResult json = new JsonResult();
        try
        {
            messageInfoService.addMessageInfo(messageInfo);
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        catch (BussinessException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
        json.setFlag(true).setMessage("短信设置成功");
        return json;
    }

    @RequestMapping(value = "getMessageInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object getMessageInfo(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        JsonResult json = new JsonResult();
        MessageInfo messageInfo = messageInfoService.getMessageInfo(map);
        json.setFlag(true).setMessage("获取商家设置的短信成功");
        json.putData("messageInfo", messageInfo);
        return json;
    }
}

/*
 * 文件名：ResourceController.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-12-17 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.security.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.superwifi.security.service.ResourceService;
import com.yazuo.superwifi.util.JsonResult;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhaohuaqin
 * @version 2014-12-17
 * @see ResourceController
 * @since
 */
@Controller
@RequestMapping("/controller/resource")
public class ResourceController
{
    Logger log = Logger.getLogger(this.getClass());

    @Resource(name = "resourceService")
    private ResourceService resourceService;

    /**
     * 权限列表相关
     */
    @RequestMapping(value = "listResources", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult listResources()
    {
        List<Map<String, Object>> listResources = resourceService.listResources();
        return new JsonResult(true).setMessage("查询成功").setData(listResources);
    }

    @RequestMapping(value = "getAuthoritiesByUserId", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult getAuthoritiesByUserId(@RequestBody
    Map<String, Object> map)
    {
        Integer userId = (Integer)map.get("userId");
        List<Integer> authoritiesList = resourceService.getAuthoritiesByUserId(userId);
        return new JsonResult(true).setMessage("查询成功").setData(authoritiesList);
    }

}

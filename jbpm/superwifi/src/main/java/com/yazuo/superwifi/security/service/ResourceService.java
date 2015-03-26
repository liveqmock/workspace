/*
 * 文件名：ResourceService.java 版权：Copyright by www.yazuo.com 描述： 修改人：zhaohuaqin 修改时间：2014-12-17 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.security.service;


import java.util.List;
import java.util.Map;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhaohuaqin
 * @version 2014-12-17
 * @see ResourceService
 * @since
 */

public interface ResourceService
{
    List<Map<String, Object>> listResources();

    List<Integer> getAuthoritiesByUserId(Integer userId);
}

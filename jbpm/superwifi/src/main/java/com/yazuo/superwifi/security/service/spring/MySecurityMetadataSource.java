package com.yazuo.superwifi.security.service.spring;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.yazuo.superwifi.security.dao.ResourceDao;
import com.yazuo.superwifi.util.PropertyUtil;
import com.yazuo.superwifi.util.StringList;


public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource
{

    private ResourceDao resourceDao;

    // 超级wifi产品号
    Integer superwikiProductId = Integer.valueOf(PropertyUtil.getInstance().getProperty("superwiki_productId"));

    private final String URL = "/**/*.jsp";

    private static final Logger log = Logger.getLogger(MySecurityMetadataSource.class);

    /* 保存资源和权限的对应关系 key-资源url value-权限 */
    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    private AntPathMatcher urlMatcher = new AntPathMatcher();

    public MySecurityMetadataSource(ResourceDao resourceDao)
    {
        this.resourceDao = resourceDao;
        loadResourcesDefine();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes()
    {
        return null;
    }

    @Deprecated
    public void loadResourcesDefine()
    {
        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

        initLoadResourcesOfPrivilege();
    }

    public void initLoadResourcesOfPrivilege()
    {
        log.info("初始化权限资源列表数据...");
        // 查询wifi产品权限列表
        // List<Resource> resources = resourceDao.listAllResourcesOfPrivilege(superwikiProductId);
        List<Map<String, Object>> resourceList = resourceDao.listAllProductAndAuthority(superwikiProductId);
        if (null == resourceList || 0 == resourceList.size())
        {
            log.warn("初始化权限资源列表数据为空");
            return;
        }
        else
        {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            for (Map<String, Object> map : resourceList)
            {
                Collection<ConfigAttribute> listConfigAttributes = null;

                ConfigAttribute configAttribute = new SecurityConfig(map.get("authority_code").toString());

                if (resourceMap.containsKey(map.get("product_url").toString()))
                {
                    listConfigAttributes = resourceMap.get(map.get("product_url").toString());
                    listConfigAttributes.add(configAttribute);
                }
                else
                {
                    listConfigAttributes = new ArrayList<ConfigAttribute>();
                    listConfigAttributes.add(configAttribute);
                    resourceMap.put(map.get("product_url").toString(), listConfigAttributes);
                }
            }
            log.info("完成数据库初始化权限资源列表数据");
        }
        /** 添加jsp对应资源 */
        StringList authorityList = resourceDao.listAllAuthorityOfPrivilege(superwikiProductId);
        if (null != authorityList && authorityList.size() > 0)
        {
            for (String str : authorityList)
            {
                Collection<ConfigAttribute> listConfigAttributes = null;

                ConfigAttribute configAttribute = new SecurityConfig(str);

                if (resourceMap.containsKey(URL))
                {
                    listConfigAttributes = resourceMap.get(URL);
                    listConfigAttributes.add(configAttribute);
                }
                else
                {
                    listConfigAttributes = new ArrayList<ConfigAttribute>();
                    listConfigAttributes.add(configAttribute);
                    resourceMap.put(URL, listConfigAttributes);
                }
            }
        }
        log.info("resourceMap资源：" + resourceMap);
        log.info("初始化权限资源列表数据完成");
    }

    /*
     * 根据请求的资源地址，获取它所拥有的权限
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj)
        throws IllegalArgumentException
    {
        // 获取请求的url地址
        String url = ((FilterInvocation)obj).getRequestUrl();
        log.debug("MySecurityMetadataSource:getAttributes()---------------请求地址为：" + url);

        if (null == resourceMap) return null;

        Iterator<String> it = resourceMap.keySet().iterator();
        while (it.hasNext())
        {
            String _url = it.next();
            if (url.indexOf("?") != -1)
            {
                url = url.substring(0, url.indexOf("?"));
            }
            if (urlMatcher.match(_url, url)) return resourceMap.get(_url);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> arg0)
    {
        log.info("MySecurityMetadataSource.supports()---------------------");
        return true;
    }
}

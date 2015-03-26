/*
 * 文件名：MerchantController.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月31日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.merchant.controller;


import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.HomePage;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.JsonResult;
import com.yazuo.superwifi.util.StringList;


@Controller
@RequestMapping("/controller/merchant")
public class MerchantController
{
    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    @Value("#{propertiesReader['config']}")
    private String config;

    @Value("#{propertiesReader['appKey']}")
    private String appKey;

    @Value("#{propertiesReader['secretKey']}")
    private String secretKey;
    @Resource(name="sessionRegistry")
    SessionRegistry sessionRegistry;
    private static final Logger log = Logger.getLogger(MerchantController.class);
    @RequestMapping(value = "getMerchantList", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object addMerchant(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        Integer merchantId = (Integer)map.get("merchantId");
        JSONArray merchantList = merchantService.getMerchantList(merchantId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("flag", true);
        result.put("message", "查询成功");
        result.put("data", merchantList);
        return result;
    }

    @RequestMapping(value = "updateConnetType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateConnetType(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.updateConnetType(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }

        return jsoninfo;
    }

    @RequestMapping(value = "getConnectType", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getConnectType(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.getConnectType(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }

        return jsoninfo;
    }

    /**
     * 修改主管密码
     * 
     * @param merchantId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateAdminPassWord", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object updateAdminPassWord(@RequestBody
    Map<String, Object> map, Authentication authentication)
        throws Exception 
    {
        JsonResult jsRe = new JsonResult();
        Integer merchantId = (Integer)map.get("merchantId");
        String oldPassWord = map.get("oldPassWord").toString();
        String newPassWord = map.get("newPassWord").toString();

        jsRe = merchantService.updateAdminPassWord(merchantId, oldPassWord, newPassWord);
        com.yazuo.superwifi.security.vo.PhoneUserDetails user = ((com.yazuo.superwifi.security.vo.PhoneUserDetails)authentication.getPrincipal());
        user.getUser().put("needSetSupervisor", false);
        return jsRe;
    }

    /**
     * 找回主管密码
     * 
     * @param merchantId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findAdminPassword", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object findAdminPassword(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        JsonResult jsRe = new JsonResult();
        Integer merchantId = (Integer)map.get("merchantId");
        Integer brandId = (Integer)map.get("brandId");
        String mobileNumber = map.get("mobileNumber").toString();

        String content = merchantService.findAdminPassword(merchantId, brandId, mobileNumber);
        jsRe.setFlag(true).setMessage("临时密码已通过短信形式发送至您的手机，请注意查收。");
        return jsRe;
    }

    /**
     * 更改连接到会员中心的方式，1连接至会员中心，2自定义主页方案，3会员卡推荐页面
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "updateHomePageInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult updateHomePageInfo(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.updateHomePageInfo(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }
        return jsoninfo;
    }

    @RequestMapping(value = "getHomePageInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getHomePageInfo(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.getHomePageInfo(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }
        return jsoninfo;
    }

    /**
     * 获取设置的广告卡推荐类型的卡id的列表给微信端来展示
     * 
     * @param ciphertext
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCardTypeIds", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCardTypeIds(String ciphertext)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        JSONObject json = JSONObject.fromObject(ciphertext);
        Integer brandId =json.getInt("brandId");
        jsoninfo = merchantService.getCardTypeIds(brandId);
        return jsoninfo;
    }

    /**
     * 拿到crm的卡类型 用于在广告页展示选择
     * 
     * @param ciphertext
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCardTypeIdsFromCrm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCardTypeIdsFromCrm(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer brandId = (Integer)map.get("brandId");
        jsoninfo = merchantService.getCardTypeIdsFromCrm(brandId);
        return jsoninfo;
    }

    /**
     * 驻店时长设置
     */
    @RequestMapping(value = "setShopTime", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult setShopTime(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.setShopTime(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }
        return jsoninfo;
    }

    /**
     * 获取商户 驻店时长设置
     */
    @RequestMapping(value = "getShopTime", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getShopTime(@RequestBody
    Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if (null != paramMap)
        {
            jsoninfo = merchantService.getShopTime(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("请正确传入参数");
        }
        return jsoninfo;
    }

    /**
     * 上传商户portal页图片
     */
    @RequestMapping(value = "upLoadPortalPic", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult upLoadPortalPic(MultipartFile myfile,String sessionid)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        SessionInformation si=sessionRegistry.getSessionInformation(sessionid);
        if(null==si){
            return jsoninfo.setFlag(false).setMessage("您没有权限，请尝试重新登录。");
        }
        
        jsoninfo = merchantService.uploadPic(myfile);
        return jsoninfo;
    }

    /**
     * 保存商户portal页图片
     */
    @RequestMapping(value = "savePortalPic", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult savePortalPic(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        // Integer merchantId,Integer brandId,String myfile
        Integer merchantId = (Integer)map.get("merchantId");
        Integer brandId = (Integer)map.get("brandId");
        String myfile = map.get("myfile").toString();
        jsoninfo = merchantService.savePortalPic(merchantId, brandId, myfile);
        return jsoninfo;
    }

    /**
     * 保存商户portal
     */
    @RequestMapping(value = "updatePortal", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object savePortalPic(String mac, String name)
        throws Exception
    {
        String portal = "wifi.yazuo.com/controller/login/portal.do";
        return post(portal, mac, name);
    }

    String  post(String portal, String mac, String ssid)
        throws Exception
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(appKey + day + time + secretKey, null);

        List<Ssid> ssidList = new ArrayList<Ssid>();
        Ssid s1 = new Ssid();
        s1.setId(1);
        s1.setName(ssid);
        // "124.42.38.77/controller/login/portal.do"
        s1.setPortal_addr(portal);
        s1.setPortal_status(1);
        ssidList.add(s1);
        Ssid s2 = new Ssid();
        s2.setId(2);
        s2.setName("ssid7");
        s2.setPortal_addr("wifi.yazuo.com/controller/login/portal.do");
        s2.setPortal_status(0);
        ssidList.add(s2);
        Ssid s3 = new Ssid();
        s3.setId(3);
        s3.setName("ssid8");
        s3.setPortal_addr("wifi.yazuo.com/controller/login/portal2.do");
        s3.setPortal_status(0);
        ssidList.add(s3);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ssid", ssidList);
        JSONObject jo = new JSONObject().fromObject(map);
        String data = jo.toString();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", appKey));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", mac));
        nvps.add(new BasicNameValuePair("data", data));
        JSONObject result=HttpUtil.httpPostForJson(nvps, config);
        return result.toString();
    }

    /**
     * 获取商户portal页路径
     */
    @RequestMapping(value = "getPortalPicByMerchantId", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getPortalPicByMerchantId(@RequestBody
    Map<String, Object> map)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        jsoninfo = merchantService.getPortalPicByMerchantId(map);
        return jsoninfo;
    }

    /**
     * 为老商户开通雅座回头宝
     */
    @RequestMapping(value = "addOrUpdateWifiProductForOldBrand", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult addOrUpdateWifiProductForOldBrand(Integer brandId, Integer merchantId, String macs, String ssid1,
                                                String ssid2, String ssid3, String password1, String password2,
                                                String password3, Integer userId, String operatorId,Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        if ((password1 != null && (!"".equals(password1) && (!Pattern.matches("^\\w+$", password1) || password1.length() != 8)))
            || (password2 != null && (!"".equals(password2) && (!Pattern.matches("^\\w+$", password2) || password3.length() != 8)))
            || (password3 != null && (!"".equals(password3) && (!Pattern.matches("^\\w+$", password3) || password3.length() != 8))))
        {
            return jsoninfo.setFlag(false).setMessage("密码应该为空或者8位英文字符");
        }

        if ((ssid1 != null && !"".equals(ssid1) && !Pattern.matches("^\\w+$", ssid1))
            || (ssid2 != null && !"".equals(ssid2) && !Pattern.matches("^\\w+$", ssid2))
            || (ssid3 != null && !"".equals(ssid3) && !Pattern.matches("^\\w+$", ssid3)))
        {
            return jsoninfo.setFlag(false).setMessage("ssid只可以为英文的大小写");
        }
        if(null==userId){
            return jsoninfo.setFlag(false).setMessage("请选择用户");
        }

        StringList macss = new StringList();
        for (String s : macs.split(","))
        {
            macss.add(s.replace(":", "").replace("-", "").toLowerCase());
        }
        
        jsoninfo = merchantService.addOrUpdateWifiProduct(brandId, merchantId, macss, ssid1, ssid2, ssid3, password1,
            password2, password3, userId, operatorId, MerchantInfo.SOURCE_CRM,isPassWordCheck,merchantType);
        return jsoninfo;
    }

    /**
     * 为新商户开通雅座回头宝
     */
    @RequestMapping(value = "addOrUpdateWifiProductForNewBrand", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult addOrUpdateWifiProductForNewBrand(Integer merchantId,String merchantName, String macs, String ssid1, String ssid2,
                                                String ssid3, String password1, String password2, String password3,
                                                String userName, String mobileNumber, String operatorId,String operatorMobileNumber,
                                                Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {
        
        JsonResult jsoninfo = new JsonResult();
        if ((password1 != null && (!"".equals(password1) && (!Pattern.matches("^\\w+$", password1) || password1.length() != 8)))
            || (password2 != null && (!"".equals(password2) && (!Pattern.matches("^\\w+$", password2) || password3.length() != 8)))
            || (password3 != null && (!"".equals(password2) && (!Pattern.matches("^\\w+$", password2) || password3.length() != 8))))
        {
            return jsoninfo.setFlag(false).setMessage("密码应该为空或者8位英文字符");
        }

        if ((ssid1 != null && !"".equals(ssid1) && !Pattern.matches("^\\w+$", ssid1))
            || (ssid2 != null && !"".equals(ssid2) && !Pattern.matches("^\\w+$", ssid2))
            || (ssid3 != null && !"".equals(ssid3) && !Pattern.matches("^\\w+$", ssid3)))
        {
            return jsoninfo.setFlag(false).setMessage("ssid只可以为英文的大小写");
        }
        StringList macss = new StringList();
        for (String s : macs.split(","))
        {
            macss.add(s.replace(":", "").replace("-", "").toLowerCase());
        }
        try
        {
            jsoninfo = merchantService.addOrUpdateWifiProductForNewBrand(merchantId,merchantName, macss, ssid1, ssid2, ssid3, password1,
                password2, password3, userName, mobileNumber, operatorId,operatorMobileNumber,isPassWordCheck,merchantType);
        }
        catch (Exception e)
        {
            jsoninfo.setFlag(false).setMessage(e.getMessage());
            log.error("异常", e);
            e.printStackTrace();
        }
        return jsoninfo;
    }

    /**
     * 获取基本信息，供运营平台，erp展示用
     */
    @RequestMapping(value = "getWifiProductInfomation", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getWifiProductInfomation(Integer merchantId, Integer brandId)
        throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        MerchantInfo mi=null;
        try
        {
            mi = merchantService.getWifiProductInfomation(merchantId);
        }
        catch (Exception e)
        {
            if(e.getMessage().equals("商户还未开通wifi产品")){
                map.put("MerchantInfo", mi);
                JsonResult json = new JsonResult();
                return json.setFlag(false).setMessage("查询成功!").setData(map);
            }else if(e.getMessage().equals("该商户为历史遗留商户，不允许操作")){
            	map.put("MerchantInfo", mi);
                JsonResult json = new JsonResult();
                return json.setFlag(false).setMessage("该商户为历史遗留商户，不允许操作").setData(map);
            }else{
                throw e;
            }
        }
        map.put("MerchantInfo", mi);
        JsonResult json = new JsonResult();
        return json.setFlag(true).setMessage("查询成功!").setData(map);
    }

    /**
     * 获取商户自定义页面内容
     */
    @RequestMapping(value = "getCustomizeHomepage", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult getCustomizeHomepage(String ciphertext)
        throws Exception
    {
        JSONObject json = JSONObject.fromObject(ciphertext);
        Integer brandId = json.getInt("merchantId");
        try
        {
            HomePage homePage = merchantService.getCustomizeHomepage(brandId);
            if (null == homePage || null == homePage.getMemberCenterType()
                || homePage.getMemberCenterType().intValue() != HomePage.MYSETHOMEPAGE_VALUE)
            {
                return new JsonResult(false).setMessage("该商户主页设置非自定义主页");
            }
            else
            {
                String html = homePage.getHtml();
                html = URLDecoder.decode(html, "UTF-8");
                return new JsonResult(true).setMessage("查询成功").setData(html);
            }
        }
        catch (IllegalArgumentException e)
        {
            return new JsonResult(false).setMessage(e.getMessage());
        }
    }

    @RequestMapping(value = "addWifiInfoForERP", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView addWifiInfoForERP(String operatorMobileNumber,Integer operatorId)
        throws Exception
    {
        ModelAndView mav=new ModelAndView("/mobile/online-form");
        mav.addObject("operatorMobileNumber", operatorMobileNumber);
        mav.addObject("operatorId", operatorId);
        mav.addObject("isUpdate", false);
        return mav;
    }

    @RequestMapping(value = "formManager", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView formManager(String operatorMobileNumber, String operatorId, String searchKey)
        throws Exception
    {
            return new ModelAndView("/mobile/form-manager").addObject("operatorMobileNumber", operatorMobileNumber).addObject(
                "operatorId", operatorId);
    }
    
    @RequestMapping(value = "formManagerAjax", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult formManagerAjax(String operatorMobileNumber, String operatorId, String searchKey,Integer timeSortBy)
        throws Exception
    {
        JsonResult js = new JsonResult();
        if (null == searchKey || "".equals(searchKey))
        {
            //按时间排序
            if(timeSortBy == null){
                timeSortBy = 1;
            }
            List<MerchantInfo> merchantList = merchantService.getMerchantListByOperatorId(operatorId,timeSortBy);
            return js.setFlag(true).putData("merchantList", merchantList);
        }
        else
        {
            List<MerchantInfo> merchantList = merchantService.getMerchantListBySearchKey(operatorId,searchKey);
            return js.setFlag(true).putData("merchantList", merchantList);
        }
    }

    @RequestMapping(value = "updateWifiInfoForERP", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ModelAndView updateWifiInfoForERP(String operatorMobileNumber,Integer operatorId,Integer merchantId)
        throws Exception
    {
        ModelAndView mav = new ModelAndView("/mobile/online-form");
        MerchantInfo merchantInfo =merchantService.getWifiProductInfomation(merchantId);
        mav.addObject("operatorMobileNumber", operatorMobileNumber);
        mav.addObject("operatorId", operatorId);
        mav.addObject("merchantInfo", merchantInfo);
        mav.addObject("isUpdate", true);
        return mav;
    }
    
}

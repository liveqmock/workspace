/*
 * 文件名：LoginServiceImpl.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年8月5日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.login.service.impl;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.device.vo.DeviceSSID;
import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.login.service.IdentifyinginfoService;
import com.yazuo.superwifi.login.service.LoginService;
import com.yazuo.superwifi.login.vo.Identifyinginfo;
import com.yazuo.superwifi.login.vo.LoginLog;
import com.yazuo.superwifi.member.service.MemberService;
import com.yazuo.superwifi.member.service.impl.MemberServiceImpl;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.member.vo.MemberLoginInfo;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.HomePage;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.sms.service.SMSSender;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.JsonResult;
import com.yazuo.superwifi.util.SendMessage;
import com.yazuo.superwifi.util.UrlUtils;


@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService
{

    @Value("#{propertiesReader['macRelease']}")
    String macRelease;

    // 微信会员中心
    @Value("#{propertiesReader['connectWifiAddMember']}")
    String connectWifiAddMember;

    @Resource(name = "deviceServiceImpl")
    DeviceService deviceServiceImpl;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    @Resource(name = "identifyinginfoServiceImpl")
    private IdentifyinginfoService identifyinginfoService;

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "SMSSenderImpl")
    private SMSSender sMSSender;

    @Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;

    @Resource(name = "loginServiceImpl")
    private LoginService loginService;

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Value("#{propertiesReader['appKey']}")
    private String appKey;

    @Value("#{propertiesReader['secretKey']}")
    private String secretKey;


    private static final Logger log = Logger.getLogger(MemberServiceImpl.class);

    public Map<String, Object> requestPassword(String mobileNumber, String deviceMac, String userMac,String deviceSSID)
        throws Exception
    {
        DeviceInfo deviceInfo = deviceServiceImpl.getDeviceInfoByMac(deviceMac);
        Integer brandId = deviceInfo.getBrandId();
        Integer merchantId = deviceInfo.getMerchantId();
        
        MerchantInfo mi=merchantService.getMerchantInfoByMid(merchantId);
        
        
        if(mi==null){
            throw new BussinessException("门店不存在");
        }
        
        Map<String, Object> map=new HashMap<String, Object>();
        if(!mi.getIsPassWordCheck()){
            macRelease(userMac, deviceMac, deviceSSID, mobileNumber);
            String redirectUrl =getUrl(deviceInfo, userMac, deviceMac, deviceSSID, mobileNumber,mi);
            map.put("redirectUrl", redirectUrl);
        }else{
            String smsContent = sendIdentifyinginfo(mobileNumber, brandId, merchantId);
            map.put("smsContent", smsContent);
        }
       
        return map;
    }

    private String sendIdentifyinginfo(String mobileNumber, Integer brandId, Integer merchantId)
        throws Exception
    {
        Identifyinginfo identifyinginfo = new Identifyinginfo();
        identifyinginfo.setBrandId(brandId);
        identifyinginfo.setMerchantId(merchantId);
        identifyinginfo.setMobileNumber(mobileNumber);
        identifyinginfo.setInsertTime(new Date());
        identifyinginfo.setStatus(Identifyinginfo.STATUS_NOUSE);
        // 生成code
        Random rand = new Random();
        String identifyingCode = String.valueOf(rand.nextInt(99999));
        while (identifyingCode.length() != 5)
        {
            identifyingCode = String.valueOf(rand.nextInt(99999));
        }
        identifyinginfo.setIdentifyingCode(identifyingCode);
        identifyinginfo.setFlag(Identifyinginfo.FLAG_MEMBERLOGIN);
        identifyinginfoService.saveIdentifyinginfo(identifyinginfo);

        // 拼写短信内容，根据前端传过来的sms和刚生成的验证码组成
        String smsContent = "您的上网密码为：" + identifyingCode;

        // 发送短信
        sMSSender.sendMessage(smsContent, mobileNumber, brandId,SendMessage.SMS_TYPE);
        return smsContent;
    }

    public void macRelease(String pcmac, String rmac, String ssid, String userMobile)
        throws Exception
    {
        MacRelease mr=new MacRelease(pcmac, rmac, ssid, userMobile, appKey, secretKey, macRelease);
        Thread t1=new Thread(mr);
        t1.start();
        //http请求发出后不能保证设备立即生效，为保证生效，等待两分钟后再跳转下一个url
        //Thread.sleep(2000);
    }

    @Override
    public String sendIdentifyinginfo(String mobileNumber, Integer brandId)
        throws Exception
    {

        return null;
    }

    @Override
    public Integer getBrandIdByDeviceMac(String deviceMac)
        throws Exception
    {
        return null;
    }

    @Override
    public Map<String, Object> login(String mobileNumber, String password, String deviceMac, String userMac,
                                     String deviceSSID)
        throws Exception
    {
        // 1、查询设备信息
        DeviceInfo deviceInfo = deviceServiceImpl.getDeviceInfoByMac(deviceMac);

        // 2、根据手机号、验证码与门店id查询验证码信息
        List<Identifyinginfo> list = identifyinginfoService.getIdentifyinginfoByMobileAndIdentifyingCode(mobileNumber,
            password, deviceInfo.getMerchantId());

        if (null == list || 0 == list.size())
        {
            log.error("mobileNumber:" + mobileNumber + "password:" + password + "deviceMac:" + deviceMac);
            throw new BussinessException(mobileNumber + "手机号或密码错误，请确认是否已申请验证");
        }
        else
        {
            String id = list.get(0).getId();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            if ((System.currentTimeMillis() - list.get(0).getInsertTime().getTime()) > (30 * 60 * 1000))
            {
                map.put("status", Identifyinginfo.STATUS_OVERDUE);
                identifyinginfoService.updateIdentifyinginfo(map);
                throw new BussinessException(mobileNumber + "验证码过期");
            }
            else
            {
            	//判断商户是否开通短信验证码
            	MerchantInfo mer = merchantService.getMerchantInfoByMid(deviceInfo.getMerchantId());
            	if(mer.getIsPassWordCheck()){
            		//如果开通短信验证就验证上网密码 ，否则直接放行
	                map.put("status", Identifyinginfo.STATUS_USED);
	                identifyinginfoService.updateIdentifyinginfo(map);
            	}

                String url = getUrl(deviceInfo, userMac, deviceMac, deviceSSID, mobileNumber,mer);
                macRelease(userMac, deviceMac, deviceSSID, mobileNumber);
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("url", url);
                resultMap.put("brandId", deviceInfo.getBrandId());
                return resultMap;
            }
        }

    }

    @Override
    public HomePage getHomePageInfo(Integer brandId)
    {
        List<HomePage> homePageList = mongoTemplate.find(new Query(Criteria.where("brandId").is(brandId)),
            HomePage.class);
        HomePage homePage = null;
        if (null != homePageList && homePageList.size() > 0)
        {
            homePage = homePageList.get(0);
        }
        return homePage;
    }

    @Override
    public ModelAndView portal(String pcip, String pcmac, String rmac, String url, String ssid,
                               HttpServletRequest request)
        throws Exception
    {
        String userAgent = request.getHeader("User-Agent");
        // 1.根据设备mac码查询设备信息
        DeviceInfo deviceInfo = deviceService.getDeviceInfoByMac(rmac);
        Integer brandId = deviceInfo.getBrandId();// 品牌ID
        Integer merchantId = deviceInfo.getMerchantId();// 门店ID
        List<DeviceSSID> devSSIDList = deviceInfo.getDevSSID();

        MerchantInfo mi = merchantService.getMerchantInfoByMid(merchantId);
        if (mi == null)
        {
            throw new BussinessException("商户未找到");
        }

        // Integer ssidBelonging = null;// 连接用户使用的SSID归属1-会员 2-店员 3-其他
        // for (DeviceSSID deviceSSID : devSSIDList)
        // {
        // if (StringUtils.equals(deviceSSID.getSsid(), ssid))
        // {
        // ssidBelonging = deviceSSID.getSsidBelonging();
        // }
        // }
        // if (null == ssidBelonging)
        // {
        // log.error("ssid名称未录入数据库！");
        // }

        // 2.记录连接用户登陆日志
        MemberLoginInfo memberLoginInfo = getMemberLoginInfo(pcmac, rmac, ssid, deviceInfo, request);
        memberService.saveMemberLoginInfo(memberLoginInfo);

        // 3.判断登陆用户是否是新用户
        Boolean newUser = true;
        Member member = memberService.getMemberByMac(brandId, pcmac);
        if (null != member)
        {
            newUser = false;// 老会员
        }
        else
        {
            newUser = true;// 新会员
        }

        Map<String, Object> map = new HashMap<String, Object>();
        if (!newUser)
        {
            // 1.查询商户的品牌名称
            /** 减少访问crmapi压力，品牌名称取merchantInfo中的merchantName at 2015-1-22 */
            // JSONObject jsonObject = merchantService.getMerchantByMerchantId(brandId);
            // String brandName = jsonObject.getString("merchantName");

            // 2.查询商户主页面设置
            HomePage homePageInfo = loginService.getHomePageInfo(brandId);
            int memberCenterType = 1;// 默认会员中心
            if (null != homePageInfo)
            {
                memberCenterType = homePageInfo.getMemberCenterType().intValue();
            }

           
            boolean isShouldRelease = true;
            if (UrlUtils.isIOSAutoRequest(url))
            {
                //wifi请求模式下一直不放行，等待出现其中的一次浏览器模式
                if(userAgent==null||userAgent.equals("")||userAgent.indexOf("CaptiveNetworkSupport")!=-1){
                    isShouldRelease=false;
                }else{
                    isShouldRelease = true;
                }
                // 下边代码主要是针对ISO设备的处理，苹果设备在设置--无线局域网 点击SSID后会连着发出两个请求，请求的地址是随机的，只有当两次请求都没有达到苹果的预期值的时候才会弹出portal，
                // 如果第一次没有达到预期值，但是老客户的逻辑会对其放行，放行后的第二次会达到苹果的预期值，就无法正常的弹出会员页面了
                // 目前发现第一次的请求 User-Agent 为CaptiveNetworkSupport 第二次为正常的浏览器UA  之后的多次请求 都为 CaptiveNetworkSupport
                // 之后的多次请求是为了确定连接WIFI后弹出的页面右上角应该显示“取消”还是“完成”
                // 还未用User-Agent 做任何逻辑上的限定
               /* LoginLog loginLog = UrlUtils.portalMap.get(pcmac + rmac);
                if (loginLog == null)
                {
                    loginLog = new LoginLog();
                    loginLog.setDevice_mac(rmac);
                    loginLog.setUrl(url);
                    loginLog.setMember_mac(pcmac);
                    loginLog.setInsertTime(new Timestamp(System.currentTimeMillis()));
                    UrlUtils.portalMap.put(pcmac + rmac, loginLog);
                    isShouldRelease = false;
                }
                else
                {
                    if ((System.currentTimeMillis() - loginLog.getInsertTime().getTime()) < 30000L)
                    {
                        loginLog.setDevice_mac(rmac);
                        loginLog.setUrl(url);
                        loginLog.setMember_mac(pcmac);
                        loginLog.setInsertTime(new Timestamp(System.currentTimeMillis()));
                        UrlUtils.portalMap.put(pcmac + rmac, loginLog);
                        isShouldRelease = true;
                    }
                    else
                    {
                        // 还没想好其他过滤方式
                        loginLog.setDevice_mac(rmac);
                        loginLog.setUrl(url);
                        loginLog.setMember_mac(pcmac);
                        loginLog.setInsertTime(new Timestamp(System.currentTimeMillis()));
                        UrlUtils.portalMap.put(pcmac + rmac, loginLog);
                        isShouldRelease = false;
                        if (UrlUtils.portalMap.size() > 10000)
                        {
                            UrlUtils.cleanPortalMap();
                        }
                    }

                }*/
            }

            // 3.用户放行
            if (isShouldRelease)
            {
                macRelease(pcmac, rmac, ssid, member.getPhoneNumber());
            }

            // 微信入参准备
            String param = getParamForwinxin(member.getPhoneNumber(), brandId, merchantId, memberCenterType,
                mi.getMerchantName());
            map.put("param", param);
            log.info("调用微信接口"+connectWifiAddMember+param);
            return new ModelAndView(new RedirectView(connectWifiAddMember), map);
        }
        else
        {
            String picUrl = null;
            map.put("merchantId", merchantId);
            JsonResult jsonResult = merchantService.getPortalPicByMerchantId(map);
            int flag = jsonResult.getFlag();
            if (flag == 1)
            {
                picUrl = jsonResult.getData().toString();
            }
            ssid = ssid.replace("'", "\\'").replace("\"", "\\\"");
            return new ModelAndView("redirect:/jsp/login.jsp?pcip=" + pcip + "&pcmac=" + pcmac + "&rmac=" + rmac
                                    + "&url=" + url + "&ssid=" + ssid + "&picUrl=" + picUrl + "&isPassWordCheck="
                                    + (mi.getIsPassWordCheck() == null ? true : mi.getIsPassWordCheck()));
        }
    }

    private String getParamForwinxin(String phoneNumber, Integer brandId, Integer merchantId, int memberCenterType,
                                     String brandName)
        throws Exception
    {
        Boolean isCrmMemberUser = false;
        MerchantInfo merchantInfo = merchantService.getMerchantInfoByMid(merchantId);
        if (null != merchantInfo)
        {
            Integer source = merchantInfo.getSource();
            if (null != source && source.intValue() == MerchantInfo.SOURCE_CRM)
            {
                isCrmMemberUser = true;
            }
        }
        JSONObject jo = new JSONObject();
        jo.accumulate("brandId", brandId);
        jo.accumulate("phoneNo", phoneNumber);
        jo.accumulate("settingAdvertSource", memberCenterType);
        jo.accumulate("isCrmMemberUser", isCrmMemberUser);
        jo.accumulate("merchantId", merchantId);
        jo.accumulate("brandName", brandName);
        String param = jo.toString();
        return param;
    }

    private MemberLoginInfo getMemberLoginInfo(String pcmac, String rmac, String ssid, DeviceInfo deviceInfo,HttpServletRequest request)
    {
        MemberLoginInfo memberLoginInfo = new MemberLoginInfo();
        memberLoginInfo.setPcmac(pcmac);
        memberLoginInfo.setBrandId(deviceInfo.getBrandId());
        memberLoginInfo.setMerchantId(deviceInfo.getMerchantId());
        memberLoginInfo.setRmac(rmac);
        memberLoginInfo.setSsid(ssid);
        memberLoginInfo.setLoginTime(new Timestamp(System.currentTimeMillis()));
        memberLoginInfo.setUserAgent(request.getHeader("User-Agent"));
        return memberLoginInfo;
    }
    
    
    private String getUrl(DeviceInfo deviceInfo,String userMac,String deviceMac,String deviceSSID,String mobileNumber,MerchantInfo mer) throws Exception{
        // 1.查询商户的品牌名称
        Integer brandId = deviceInfo.getBrandId();// 品牌id
        Integer merchantId = deviceInfo.getMerchantId();
        /**减少访问crmapi压力，品牌名称取merchantInfo中的merchantName   at 2015-1-22*/
//        JSONObject jsonObject = merchantService.getMerchantByMerchantId(brandId);
//        String brandName = jsonObject.getString("merchantName");

        // 2.查询门店主页设置
        HomePage homePage = getHomePageInfo(brandId);

        // 3.用户放行
        //macRelease(userMac, deviceMac, deviceSSID, mobileNumber);

        // 4. 添加或修改会员信息
        Member mem = memberService.getMemberByMobileAndBrandId(brandId, mobileNumber);
        if (mem == null)
        {
            // 增加会员信息
            Member member = new Member();
            member.setBrandId(brandId);
            member.setMerchantId(merchantId);
            member.setMac(userMac);
            member.setPhoneNumber(mobileNumber);
            member.setStatus(Member.STATUS_NORMAL);
            member.setInsertTime(new Timestamp(System.currentTimeMillis()));
            member.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            memberService.saveMember(member);
        }
        else
        {
            // 修改会员信息
            Member updateMem = mem;
            updateMem.setMac(userMac);
            updateMem.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            memberService.updateMember(mem, updateMem);
        }

        int memberCenterType = 1;
        if (null != homePage && ((HomePage)homePage).getMemberCenterType() != null)
        {
            memberCenterType = homePage.getMemberCenterType().intValue();
        }
        // 微信入参准备
        String param = getParamForwinxin(mobileNumber, brandId, merchantId, memberCenterType, mer.getMerchantName());
        String url = connectWifiAddMember + "?param=" + param;
        return url;
    }

}


class MacRelease implements Runnable {
    private String pcmac;
    private String rmac;
    private String ssid;
    private String userMobile;
    private String appKey;
    private String secretKey;
    private String macRelease;
    private static final Logger log = Logger.getLogger(MacRelease.class);
    public MacRelease() {
 
    }
 
    public MacRelease(String pcmac, String rmac, String ssid, String userMobile,String appKey,String secretKey,String macRelease) {
        this.pcmac = pcmac;
        this.rmac=rmac;
        this.ssid=ssid;
        this.userMobile=userMobile;
        this.appKey=appKey;
        this.secretKey=secretKey;
        this.macRelease=macRelease;
    }
 
    public void run() {
        
        final Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(appKey + day + time + secretKey, null);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", appKey));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", rmac.replace(":", "")));
        nvps.add(new BasicNameValuePair("userMac", pcmac.replace(":", "")));
        nvps.add(new BasicNameValuePair("userMobile", userMobile));
        nvps.add(new BasicNameValuePair("deviceSSID", ssid));

        JSONObject result=null;
        try
        {
            result = HttpUtil.httpPostForJson(nvps, macRelease);
        }
        catch (Exception e)
        {
            log.error("放行失败", e);
            e.printStackTrace();
            return;
        }
        log.info(result);
    }
    
}
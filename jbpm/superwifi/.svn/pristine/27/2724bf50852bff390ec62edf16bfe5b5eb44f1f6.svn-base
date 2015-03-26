/*
 * 文件名：MerchantServiceImpl.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月31日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.merchant.service.impl;


import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yazuo.api.service.ClientContents;
import com.yazuo.api.service.card.CardWebService;
import com.yazuo.api.service.merchant.MerchantProductVo;
import com.yazuo.api.service.merchant.MerchantVo;
import com.yazuo.api.service.merchant.MerchantWebService;
import com.yazuo.superwifi.device.service.DeviceService;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.device.vo.DeviceSSID;
import com.yazuo.superwifi.exception.BussinessException;
import com.yazuo.superwifi.merchant.controller.Ssid;
import com.yazuo.superwifi.merchant.dao.MerchantDao;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.merchant.vo.Account;
import com.yazuo.superwifi.merchant.vo.CityJson;
import com.yazuo.superwifi.merchant.vo.HomePage;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.merchant.vo.Shop;
import com.yazuo.superwifi.security.dao.UserDao;
import com.yazuo.superwifi.security.dto.User;
import com.yazuo.superwifi.security.service.ResourceService;
import com.yazuo.superwifi.security.service.UserService;
import com.yazuo.superwifi.sms.service.SMSSender;
import com.yazuo.superwifi.util.CloneUtil;
import com.yazuo.superwifi.util.HttpUtil;
import com.yazuo.superwifi.util.JsonResult;
import com.yazuo.superwifi.util.SendMessage;
import com.yazuo.superwifi.util.StringList;
import com.yazuo.superwifi.util.UploadFileUtil;


@Service("merchantServiceImpl")
public class MerchantServiceImpl implements MerchantService
{
    @Resource(name = "merchantDao")
    MerchantDao merchantDao;

    @Resource(name = "userService")
    UserService userService;

    @Resource(name = "resourceService")
    ResourceService resourceService;

    @Resource(name = "SMSSenderImpl")
    private SMSSender sMSSender;

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Resource(name = "userDao")
    private UserDao userDao;
    
    @Resource(name = "deviceServiceImpl")
    private DeviceService deviceService;

    @Resource
    private MerchantWebService merchantWebService;
    
    @Resource
    private CardWebService cardWebService;

    @Value("#{propertiesReader['getMerchantChildListByMerchantId']}")
    String getMerchantChildListByMerchantId;

    @Value("#{propertiesReader['getMerchantByMerchantId']}")
    String getMerchantByMerchantId;

    @Value("#{propertiesReader['getMerchantInfoByMerchantId']}")
    String getMerchantInfoByMerchantId;

    @Value("#{propertiesReader['crmApiKey']}")
    private String crmApiKey;

    @Value("#{propertiesReader['saveMemberRightSetting']}")
    String saveMemberRightSetting;
    
    @Value("#{propertiesReader['saveCardType']}")
    String saveCardType;
    
    @Value("#{propertiesReader['getCardType']}")
    String getCardType;

    @Value("#{propertiesReader['synchrodataBrand']}")
    String synchrodataBrand;

    @Value("#{propertiesReader['yazuoToken']}")
    String yazuoToken;

    @Value("#{propertiesReader['dfs.server.ip']}")
    private String picDfsServer;

    @Value("#{propertiesReader['dfs.server.port']}")
    private Integer picDfsPort;

    @Value("#{propertiesReader['dfs.tracker.http.port']}")
    private String dfsTrackerHttpPort;

    @Value("#{propertiesReader['config']}")
    private String config;

    @Value("#{propertiesReader['appKey']}")
    private String appKey;

    @Value("#{propertiesReader['secretKey']}")
    private String secretKey;

    @Value("#{propertiesReader['portal']}")
    private String portal;

    @Value("#{propertiesReader['superwiki_productId']}")
    private String superwiki_productId;

    /**
     * 品牌
     */
    private static final Integer FACESHOPTYPE_BRAND = 1;

    /**
     * 管理公司
     */
    private static final Integer FACESHOPTYPE_MANAGERCOMPANY = 2;

    /**
     * 门店
     */
    private static final Integer FACESHOPTYPE_FACESHOP = 3;

    private Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();

    private static final Logger log = Logger.getLogger(MerchantServiceImpl.class);

    @Override
    public JSONArray getMerchantList(Integer merchantid)
        throws Exception
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", "{merchantId:" + merchantid + "}"));
        nvps.add(new BasicNameValuePair("key", crmApiKey));

        // 获取商户信息
        // = HttpUtil.httpPostForString(nvps, getMerchantChildListByMerchantId);
        String merchantJson = HttpUtil.httpPostForString(nvps, getMerchantChildListByMerchantId).replace("null", "''");

        JSONArray merchantJSONArray = JSONObject.fromObject(merchantJson).getJSONObject("data").getJSONObject("result").getJSONArray(
            "merchantList");

        for (int i = 0; i < merchantJSONArray.size(); i++ )
        {
            JSONObject jo = (JSONObject)merchantJSONArray.get(i);
            if (jo.getBoolean("isFaceShop"))
            {
                jo.accumulate("faceShopType", FACESHOPTYPE_FACESHOP);

            }
            else
            {
                if (jo.getInt("merchantId") == jo.getInt("brandId"))
                {
                    jo.accumulate("faceShopType", FACESHOPTYPE_BRAND);
                }
                else
                {
                    jo.accumulate("faceShopType", FACESHOPTYPE_MANAGERCOMPANY);
                }

            }
        }
        return merchantJSONArray;
    }

    @Override
    public JsonResult updateConnetType(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer connectType = (Integer)paramMap.get("connectType");
        if (merchantId == null || merchantId.intValue() == 0 || connectType == null || connectType.intValue() == 0)
        {
            jsoninfo.setFlag(false).setMessage("参数为空");
        }
        else
        {
            mongoTemplate.updateMulti(
                new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                Update.update("connectType", connectType), MerchantInfo.class);
            String connectName = "";

            switch (connectType)
            {
                case (1):
                    connectName = MerchantInfo.CONNECTTYPE_WEB;
                    break;
                case (2):
                    connectName = MerchantInfo.CONNECTTYPE_WEIXIN;
                    break;
                default:
                    connectName = MerchantInfo.CONNECTTYPE_WEIXINADDWEB;
                    break;
            }
            jsoninfo.setFlag(true).setMessage("更换连接方式成功");
            jsoninfo.putData("connectName", connectName);
        }

        return jsoninfo;
    }

    @Override
    public JsonResult getConnectType(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        Query query = new Query();
        query = new Query(Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("status").is(
            DeviceInfo.STATUS_NORMAL));
        List<MerchantInfo> mer = mongoTemplate.find(query, MerchantInfo.class);
        Integer mtype = 0;
        for (MerchantInfo m : mer)
        {
            mtype = m.getConnectType();
        }
        jsoninfo.setFlag(true).setMessage("查询成功");
        // 1浏览器 2微信 3浏览器+微信
        String connectName = "";
        switch (mtype)
        {
            case (1):
                connectName = MerchantInfo.CONNECTTYPE_WEB;
                break;
            case (2):
                connectName = MerchantInfo.CONNECTTYPE_WEIXIN;
                break;
            default:
                connectName = MerchantInfo.CONNECTTYPE_WEIXINADDWEB;
                break;
        }
        paramMap.clear();
        paramMap.put("connectName", connectName);
        jsoninfo.setData(paramMap);
        return jsoninfo;
    }

    @Override
    public JsonResult updateAdminPassWord(Integer merchantId, String oldPassWord, String newPassWord)
        throws Exception
    {
        JsonResult js =new JsonResult();
        // 如果老密码不是空的，说明是修改密码
        if (!"".equals(oldPassWord))
        {
            MerchantInfo mer = mongoTemplate.findOne(new Query(Criteria.where("merchantId").is(merchantId)), MerchantInfo.class);
            
            if(mer !=null && !mer.getAdminPassWord().equals(oldPassWord)){
                return js.setFlag(false).setMessage("您输入的旧密码有误。修改失败");
            }else{
                mongoTemplate.updateMulti(new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                    Update.update("adminPassWord", newPassWord), MerchantInfo.class);
                return  js.setFlag(true).setMessage("主管密码修改成功");
            }
        }
        else
        {//设置密码
            mongoTemplate.updateMulti(
                new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                Update.update("adminPassWord", newPassWord), MerchantInfo.class);
        }
       return  js.setFlag(true).setMessage("主管密码设置成功");
    }

    @Override
    public String findAdminPassword(Integer merchantId, Integer brandId, String mobileNumber)
        throws Exception
    {
        // 生成code
        Random rand = new Random();
        String pasword = String.valueOf(rand.nextInt(999999));
        while (pasword.length() != 6)
        {
            pasword = String.valueOf(rand.nextInt(999999));
        }
        // 修改系统中的主管密码
        MerchantInfo mer = mongoTemplate.findAndModify(new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            Update.update("adminPassWord", pasword), MerchantInfo.class);
        // 拼写短信内容，根据前端传过来的sms和刚生成的验证码组成
        String smsContent = "临时主管密码：" + pasword + "，您正在找回您的主管密码。为了您的账户安全，请及时登录并修改您的主管密码，切勿泄露给他人。";
        // 发送短信
        sMSSender.sendMessage(smsContent, mobileNumber, brandId,SendMessage.SMS_TYPE);

        return smsContent;
    }

    @Override
    public Boolean checkAdminPassWord(Integer merchantId, Integer brandId, String pasd)
        throws Exception
    {
        List<MerchantInfo> merchantList = mongoTemplate.find(
            new Query(Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("adminPassWord").is(
                pasd)), MerchantInfo.class);
        if (merchantList != null && merchantList.size() > 0)
            return true;
        else
        {
            return false;
        }
    }

    @Override
    public JsonResult updateHomePageInfo(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        String html = paramMap.get("html").toString();
        if(StringUtils.isNotEmpty(html)){
            if (StringUtils.contains(html, "\"/upload/aticlecontent")){
                jsoninfo.setFlag(false).setMessage("网络连接出现错误，请重新上传图片");
                return jsoninfo;
            }
        }
        // html = URLDecoder.decode(html, "UTF-8");
        Integer brandId = (Integer)paramMap.get("brandId");
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer memberCenterType = (Integer)paramMap.get("memberCenterType");
        List<Integer> cardTypes = (List<Integer>)paramMap.get("cardTypeList");
        if (!"".equals(brandId) && brandId != null && memberCenterType != 0)
        {
            HomePage hp =mongoTemplate.findOne(new Query(Criteria.where("brandId").is(brandId)), HomePage.class);
            String operation = "del";
            if(memberCenterType.intValue() == HomePage.MEMBERCARD_RECOMMEND_VALUE){
                //如果是卡推荐页面，要调用ERP接口 operation=add否则 operation=del
                operation = "add";
                
                //如果用户选择了推荐的卡，调用微信ERP另一个接口
                if(cardTypes != null && cardTypes.size()>0){
                	saveCardTypeERP(brandId,cardTypes,operation);
                }
            }else{
            	saveCardTypeERP(brandId,null,operation);
            }
            if(!isSuccess(brandId,merchantId,operation)){//调用接口
                jsoninfo.setFlag(false).setMessage("调用微信ERP失败");
            }else{
            	if(hp != null){
            		//修改
            		mongoTemplate.updateMulti(
                            new Query(Criteria.where("brandId").is(brandId)),
                            Update.update("memberCenterType", memberCenterType).set("html", html).set(
                                "lastUpdateTime", new Date()).set("memberCenterType", memberCenterType).set("brandId", brandId).set(
                                "cardTypeIds", cardTypes), HomePage.class);
            	}else{//添加
            		HomePage hp1 = new HomePage();
            		hp1.setBrandId(brandId);
            		hp1.setInsertTime(new Date());
            		hp1.setMemberCenterType(memberCenterType);
            		hp1.setCardTypeIds(cardTypes);
            		hp1.setHtml(html);
            		
            		mongoTemplate.insert(hp1);
            	}
                
                jsoninfo.setFlag(true).setMessage("更新成功");
            }
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("参数错误");
        }
        return jsoninfo;
    }

    //调用ERP接口
    public Boolean isSuccess(Integer brandId,Integer merchantId,String operation)throws Exception{
        String json = "";
        JSONObject jo = new JSONObject();
        jo.accumulate("merchantId", merchantId);
        jo.accumulate("brandId", brandId);
        jo.accumulate("operation", operation);
        String ciphertext = jo.toString();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("param", ciphertext));
        
        log.info("访问参数地址：" +saveMemberRightSetting );
        json = HttpUtil.httpPostForString(nvps, saveMemberRightSetting).toString();
        
        JSONObject ja = JSONObject.fromObject(json);
        Boolean flag = ja.getBoolean("flag");
        return flag;
    }
    
    //ERP保存卡样信息接口
    public void saveCardTypeERP(Integer brandId,List<Integer> cardTypeIdList,String operation)throws Exception{
        String json = "";
        JSONObject jo = new JSONObject();
        if(cardTypeIdList!=null && cardTypeIdList.size()>0)
        	jo.accumulate("cardTypeIdList", cardTypeIdList);
        jo.accumulate("brandId", brandId);
        jo.accumulate("operation", operation);
        String ciphertext = jo.toString();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("param", ciphertext));
        
        log.info("访问参数地址：" +saveCardType );
        json = HttpUtil.httpPostForString(nvps, saveCardType).toString();
    }
    
    @Override
    public JsonResult getHomePageInfo(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer brandId = (Integer)paramMap.get("brandId");
        HomePage homePage = mongoTemplate.findOne(
            new Query(Criteria.where("brandId").is(brandId)), HomePage.class);
        Boolean isCrmMerchant = false;
        MerchantInfo merchantInfo = mongoTemplate.findOne(new Query(
                Criteria.where("brandId").is(brandId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                MerchantInfo.class);
        if (null == merchantInfo)
        {
            jsoninfo.setFlag(false).setMessage("该集团不存在");
        }
        else
        {
            Integer source = merchantInfo.getSource();
            if (null != source && source.intValue() == MerchantInfo.SOURCE_CRM)
            {
                isCrmMerchant = true;
            }
            else
            {
                isCrmMerchant = false;
            }
        }
        if (null == homePage)
        {
            homePage = new HomePage();
            homePage.setMemberCenterType(HomePage.MEMBERCENTER_VALUE);
            homePage.setCardTypeIds(new ArrayList<Integer>());
        }
        Integer memberCenterType = homePage.getMemberCenterType();
        String memberCenterName = "";
        switch (memberCenterType)
        {
            case (1):
                memberCenterName = HomePage.MEMBERCENTER;
                break;
            case (2):
                memberCenterName = HomePage.MYSETHOMEPAGE;
                break;
            case (3):
                memberCenterName = HomePage.MEMBERCARD_RECOMMEND;
                break;
            default:
                memberCenterName = HomePage.MEMBERCENTER;
                break;
        }
        homePage.setIsCrmMerchant(isCrmMerchant);
        homePage.setMemberCenterName(memberCenterName);
        jsoninfo.setFlag(true).setMessage("查询成功");
        jsoninfo.setData(homePage);
        return jsoninfo;
    }

    @Override
    public JsonResult getCardTypeIds(Integer brandId)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();

        List<Integer> cardTypes = new ArrayList<Integer>();
        if (!"".equals(brandId) && brandId != null)
        {
            HomePage hp = mongoTemplate.findOne(new Query(Criteria.where("brandId").is(brandId)), HomePage.class);
            // 如果是会员卡推荐类型，则返回卡号列表
            if (hp != null && hp.getMemberCenterType().intValue() == HomePage.MEMBERCARD_RECOMMEND_VALUE)
            {
                cardTypes = hp.getCardTypeIds();
                jsoninfo.setFlag(true).setMessage("获取卡类型成功").setData(cardTypes);
            }
            else
            {
                // 如果查询结果为空或者设置的类型不是卡推荐，则不返回数据
                jsoninfo.setFlag(false).setMessage("该商户没有设置广告连接方式或者连接方式不是卡类型");
            }
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("参数错误");
        }
        return jsoninfo;
    }

    @Override
    public JsonResult getCardTypeIdsFromCrm(Integer brandId)
        throws Exception
    {
        JsonResult jsre = new JsonResult();
        // 获取crm的卡类型信息
        String json = getCardTypeFromCrmByMerchantId(brandId);
        if (json != "")
        {
            json = json.replace("\\t", "");

            List<Map<String, Object>> cardInfo = new ArrayList<Map<String, Object>>();// 卡信息集合
            // 解析json
            JSONObject ja = JSONObject.fromObject(json);
            JSONObject jo = JSONObject.fromObject(ja.get("data"));
            JSONObject jo11 = JSONObject.fromObject(jo.get("result"));
            String jo111 = jo11.get("cardtypeList").toString();
            if (jo111.isEmpty() || jo111.toString().equals(""))
            {
                log.error("CRM没有查到id为：" + brandId + "的商户信息，请检查brandId是否正确");
                return null;
            }

            JSONArray jo1 = JSONArray.fromObject(jo111);

            if (jo1 != null && jo1.size() > 0)
            {
                for (int i = 0; i < jo1.size(); i++ )
                {
                    Integer id = jo1.getJSONObject(i).getInt("id");
                    String description = jo1.getJSONObject(i).getString("description");
                    String cardtype = jo1.getJSONObject(i).getString("cardtype");

                    Map<String, Object> m = new HashMap<String, Object>();

                    m.put("id", id);
                    m.put("cardtype", cardtype);
                    m.put("description", description);
                    cardInfo.add(m);
                }
            }
            jsre.setFlag(true).setMessage("获取商户crm卡类型成功").setData(cardInfo);
        }
        else
        {
            jsre.setFlag(false).setMessage("获取商户crm卡类型失败");
        }
        return jsre;
    }

    public String getCardTypeFromCrmByMerchantId(Integer brandId)
        throws Exception
    {
        String json = "";

        JSONObject jo = new JSONObject();
        jo.accumulate("merchantId", brandId);
        jo.accumulate("isParent", false);
        String ciphertext = jo.toString();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", ciphertext));
        nvps.add(new BasicNameValuePair("key", crmApiKey));

        log.info("访问参数地址：" + getCardType);
        json = HttpUtil.httpPostForString(nvps, getCardType).toString();

        return json;
    }

    @Override
    public Boolean isHavePassWord(Integer merchantId)
    {
        MerchantInfo mer = mongoTemplate.findOne(new Query(
            Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            MerchantInfo.class);
        if (mer != null && mer.getAdminPassWord() != null && !mer.getAdminPassWord().equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public JsonResult setShopTime(Map<String, Object> map)
        throws Exception
    {
        JsonResult json = new JsonResult();
        Integer merchantId = map.get("merchantId") != null ? (Integer)map.get("merchantId") : null;
        Integer time = map.get("time") != null ? (Integer)map.get("time") : null;

        if (merchantId == null || time == null)
        {
            json.setFlag(false).setMessage("请输入正确的参数!");
        }
        else
        {
            // 获取商户原本的驻店设置
            MerchantInfo mer = mongoTemplate.findOne(new Query(
                Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                MerchantInfo.class);
            if (mer != null)
            {
                mongoTemplate.updateMulti(
                    new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                    Update.update("time", time), MerchantInfo.class);
                json.setFlag(true).setMessage("设置驻店时长成功!");
            }
            else
            {
                json.setFlag(false).setMessage("没有该商户的信息!");
            }
        }
        return json;
    }

    @Override
    public JsonResult getShopTime(Map<String, Object> map)
        throws Exception
    {
        JsonResult json = new JsonResult();
        Integer merchantId = map.get("merchantId") != null ? (Integer)map.get("merchantId") : null;

        if (merchantId == null)
        {
            json.setFlag(false).setMessage("请输入正确的参数!");
        }
        else
        {
            // 获取商户原本的驻店设置
            MerchantInfo mer = mongoTemplate.findOne(new Query(
                Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
                MerchantInfo.class);
            if (mer != null)
            {
                json.setFlag(true).setMessage("获取商户驻店时长成功!");
                json.setData(mer.getTime());
            }
            else
            {
                json.setFlag(false).setMessage("没有该商户的信息!");
            }
        }
        return json;
    }

    @Override
    public JsonResult savePortalPic(Integer merchantId, Integer brandId, String fileAddres)
        throws Exception
    {
        JsonResult resultInfo = new JsonResult(false, "上传失败");

        String iconUrl = "http://" + picDfsServer + ":" + dfsTrackerHttpPort + "/";
        // 保存路径到数据库
      mongoTemplate.updateMulti(
            new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            Update.update("portalPic", fileAddres), MerchantInfo.class);
        resultInfo.setFlag(true);
        resultInfo.setMessage("上传成功");
        resultInfo.putData("icon", fileAddres);
        resultInfo.putData("path", iconUrl + fileAddres);

        return resultInfo;
    }

    @Override
    public JsonResult uploadPic(MultipartFile file)
        throws Exception
    {
        JsonResult resultInfo = new JsonResult(false, "上传失败");

        resultInfo = UploadFileUtil.upLoadFiles(file, picDfsServer, dfsTrackerHttpPort, picDfsPort);
        return resultInfo;
    }

    @Override
    public JsonResult getPortalPicByMerchantId(Map<String, Object> map)
        throws Exception
    {

        JsonResult json = new JsonResult();
        Integer merchantId = (Integer)map.get("merchantId");

        MerchantInfo mer = mongoTemplate.findOne(new Query(
            Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            MerchantInfo.class);
        if (mer == null)
        {
            return json.setFlag(false).setMessage("查询无此商户");
        }
        else
        {
            if (mer.getPortalPic() == null || mer.getPortalPic().equals(""))
            {
                return json.setFlag(true).setMessage("商户还没有设置portal图片").setData("");
            }
            String iconUrl = "http://" + picDfsServer + ":" + dfsTrackerHttpPort + "/";
            return json.setFlag(true).setMessage("查询商户portal图片成功").setData(iconUrl + mer.getPortalPic());
        }
    }

    @Override
    public JsonResult addOrUpdateWifiProduct(Integer brandId, Integer merchantId, StringList macs, String ssid1,
                                             String ssid2, String ssid3, String password1, String password2,
                                             String password3, Integer userId, String operatorId, Integer source,Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {
        MerchantInfo merchant = getMerchantInfoByMid(merchantId);
        if (null == merchant)
        {
            return addWifiProduct(brandId, merchantId, macs, ssid1, ssid2, ssid3, password1, password2, password3,
                userId, operatorId, source,isPassWordCheck,merchantType);
        }
        else
        {
            return updateWifiProduct(merchant, macs, ssid1, ssid2, ssid3, password1, password2, password3, userId,
                operatorId,isPassWordCheck,merchantType);
        }
    }

    private JsonResult updateWifiProduct(MerchantInfo merchant, StringList macs, String ssid1, String ssid2,
                                         String ssid3, String password1, String password2, String password3,
                                         Integer userId, String operatorId,Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {
        // 同步信息至芝麻科技
        String zhimaUsername = merchant.getZhimaUsername();
        String zhimaPassword = merchant.getZhimaPassword();

        // 同步信息至芝麻科技
        Account account = new Account();
        Shop shop = getShop(merchant.getMerchantId(), macs, merchant.getShopId());
        List<Shop> shopList = new ArrayList<Shop>();
        shopList.add(shop);
        account.setShop(shopList);
        account.setUser_name(zhimaUsername);
        account.setUser_password(zhimaPassword);

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(account);

        Map<String, Object> acc = new HashMap<String, Object>();
        acc.put("account", accountList);
        JSONObject accountInfo = pushAccountToZhima(acc);

        if (accountInfo.getInt("code") != 1000)
        {
            throw new BussinessException("接口调用失败");
        }
        Update merUp = new Update();
        // 比较老板id是否发生变更，或变更，则解除原用户的wifi权限，为新用户添加wifi权限
        Integer oldUserId = merchant.getUserId();
        if(userId.intValue()!=oldUserId.intValue()){
            
            // 解除原用户wifi权限
            Map<String, Object> map= new HashMap<String, Object>();
            List<Integer> userIds =new ArrayList<Integer>();
            userIds.add(oldUserId);
            map.put("userIdList", userIds);
            userService.deleteUser(map);
            
            // 为新用户添加wifi权限
            User user = userDao.loadUserByUserId(userId);
            if (user == null)
            {
                throw new BussinessException("用户不存在userId=" + userId);
            }

            List<Integer> authorityIds = getAllAuthorityIds();

            map = new HashMap<String, Object>();
            map.put("userInfoName", user.getUserName());
            map.put("mobile", user.getMobileNumber());
            map.put("merchantId", merchant.getMerchantId());
            map.put("authorityIds", authorityIds);
            map.put("userInfoId", userId);
            userService.addOrUpdateUser(map);
            
            merUp.set("userId", userId);
            merUp.set("bossName", user.getUserName());
            merUp.set("bossMobile", user.getMobileNumber());
        }
        
        // 组织device信息
        List<DeviceSSID> ssidList = getSsidList(ssid1, ssid2, ssid3, password1, password2, password3);
        // 调用芝麻接口保存device信息
        long start=System.currentTimeMillis();
        for (String mac : macs)
        {
            setSsid(ssidList, mac);
        }
        log.info("调用config耗时："+(start-System.currentTimeMillis()));
        
        for (String mac : macs)
        {
            Criteria cr = new Criteria();
            Criteria criteriaV1 = Criteria.where("mac").is(mac);
            Criteria criteriaV2 = Criteria.where("merchantId").is(merchant.getMerchantId());
            Query query = new Query(cr.orOperator(criteriaV1, criteriaV2));
            Update up = new Update();
            up.set("lastUpdateTime", new Date());
            up.set("status", DeviceInfo.STATUS_DELETE);
            mongoTemplate.updateMulti(query, up, DeviceInfo.class);
        }
        //释放设备
        deviceService.releaseDevice(merchant.getMerchantId());
        for (String mac : macs)
        {
            DeviceInfo di = new DeviceInfo();
            di.setBrandId(merchant.getBrandId());
            di.setDevSSID(ssidList);
            di.setInsertTime(new Date());
            di.setMac(mac);
            di.setMerchantId(merchant.getMerchantId());
            di.setStatus(DeviceInfo.STATUS_NORMAL);

            mongoTemplate.insert(di);
            deviceService.activatedDevice(mac);
        }
        // 保存商户信息
       // Update merUp = new Update();
        merUp.set("lastUpdateTime", new Date());
        merUp.set("operatorId", operatorId);
        merUp.set("isPassWordCheck", isPassWordCheck);
        merUp.set("merchantType", merchantType);
        mongoTemplate.updateMulti(new Query(Criteria.where("merchantId").is(merchant.getMerchantId())), merUp,
            MerchantInfo.class);

        return new JsonResult().setFlag(true).setMessage("修改商户成功!");

    }

    private JsonResult addWifiProduct(Integer brandId, Integer merchantId, StringList macs, String ssid1, String ssid2,
                                      String ssid3, String password1, String password2, String password3,
                                      Integer userId, String operatorId, Integer source,Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {

        // 同步信息至芝麻科技
        String zhimaUsername = "zhimaAccount" + merchantId.toString() + "@yazuo.com";
        String zhimaPassword = "zhimaAccount" + merchantId.toString();

        // 同步信息至芝麻科技
        Account account = new Account();
        Shop shop = getShop(merchantId, macs, -1);
        List<Shop> shopList = new ArrayList<Shop>();
        shopList.add(shop);
        account.setShop(shopList);
        account.setUser_name(zhimaUsername);
        account.setUser_password(zhimaPassword);
        List<Account> accountList = new ArrayList<Account>();
        accountList.add(account);
        Map<String, Object> acc = new HashMap<String, Object>();
        acc.put("account", accountList);
        JSONObject accountInfo = pushAccountToZhima(acc);
        if (accountInfo.getInt("code") != 1000)
        {
            throw new BussinessException("接口调用失败");
        }

        // 组织device信息
        List<DeviceSSID> ssidList = getSsidList(ssid1, ssid2, ssid3, password1, password2, password3);
        // 调用芝麻接口保存device信息
        long start=System.currentTimeMillis();
        for (String mac : macs)
        {
            setSsid(ssidList, mac);
        }
        log.info("调用config耗时："+(start-System.currentTimeMillis()));
        // 处理用户权限
        User user = userDao.loadUserByUserId(userId);
        if (user == null)
        {
            throw new BussinessException("用户不存在userId=" + userId);
        }

        List<Integer> authorityIds = getAllAuthorityIds();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userInfoName", user.getUserName());
        map.put("mobile", user.getMobileNumber());
        map.put("merchantId", merchantId);
        map.put("authorityIds", authorityIds);
        map.put("userInfoId", userId);
        userService.addOrUpdateUser(map);
        // 处理商户权限
        merchantWebService.accreditAuthForMerchant(merchantId, Integer.valueOf(superwiki_productId),
            100L * 3600 * 24 * 365*1000);

        // 保存device信息
        for (String mac : macs)
        {
            Update up = new Update();
            up.set("devSSID", ssidList);
            up.set("lastUpdateTime", new Date());
            up.set("status", DeviceInfo.STATUS_DELETE);
            mongoTemplate.updateMulti(new Query(Criteria.where("mac").is(mac)), up, DeviceInfo.class);
            
            DeviceInfo di = new DeviceInfo();
            di.setBrandId(brandId);
            di.setDevSSID(ssidList);
            di.setInsertTime(new Date());
            di.setMac(mac);
            di.setMerchantId(merchantId);
            di.setStatus(DeviceInfo.STATUS_NORMAL);

            mongoTemplate.insert(di);
            deviceService.activatedDevice(mac);
        }

        // 保存商户信息
        JSONObject accountJson = (JSONObject)accountInfo.getJSONObject("data").getJSONArray("account").get(0);
        JSONObject shopJson = (JSONObject)accountJson.getJSONArray("shop").get(0);
        int shopId = shopJson.getInt("id");
        MerchantInfo mer = new MerchantInfo();
        mer.setAdminPassWord(null);
        mer.setBrandId(brandId);
        mer.setShopId(shopId);
        mer.setInsertTime(new Date());
        mer.setIsFaceShop(true);
        mer.setMerchantId(merchantId);
        mer.setMerchantName(shop.getName());
        mer.setStatus(MerchantInfo.STATUS_NORMAL);
        mer.setZhimaPassword(zhimaPassword);
        mer.setZhimaUsername(zhimaUsername);
        mer.setSource(source);
        mer.setOperatorId(operatorId);
        mer.setBossMobile(user.getMobileNumber());
        mer.setBossName(user.getUserName());
        mer.setUserId(userId);
        mer.setIsPassWordCheck(isPassWordCheck);
        mer.setMerchantType(merchantType);
        mongoTemplate.insert(mer);

        return new JsonResult().setFlag(true).setMessage("增加商户成功!");

    }

    private List<Integer> getAllAuthorityIds()
    {
        List<Map<String, Object>> resourcesList = resourceService.listResources();

        List<Integer> authorityIds = new ArrayList<Integer>();
        for (Map<String, Object> map : resourcesList)
        {
            authorityIds.add(Integer.valueOf(map.get("authority_id").toString()));
        }
        return authorityIds;
    }

    private List<DeviceSSID> getSsidList(String ssid1, String ssid2, String ssid3, String password1, String password2,
                                         String password3)
    {
        List<DeviceSSID> ssidList = new ArrayList<DeviceSSID>();
        DeviceSSID s1 = new DeviceSSID();
        s1.setSsid(ssid1);
        s1.setId("1");
        s1.setIsMain(true);
        s1.setSsidBelonging(1);
        s1.setPassword(password1);
        if (null == password1 || "".equals(password1))
        {
            s1.setIsHavaPassword(false);
        }
        else
        {
            s1.setIsHavaPassword(true);
        }
        ssidList.add(s1);

        DeviceSSID s2 = new DeviceSSID();
        s2.setSsid(ssid2);
        s2.setId("2");
        s2.setIsMain(true);
        s2.setSsidBelonging(2);
        s2.setPassword(password2);
        if (null == password2 || "".equals(password2))
        {
            s2.setIsHavaPassword(false);
        }
        else
        {
            s2.setIsHavaPassword(true);
        }
        ssidList.add(s2);

        DeviceSSID s3 = new DeviceSSID();
        s3.setSsid(ssid3);
        s3.setId("3");
        s3.setIsMain(true);
        s3.setSsidBelonging(3);
        s3.setPassword(password3);
        if (null == password3 || "".equals(password3))
        {
            s3.setIsHavaPassword(false);
        }
        else
        {
            s3.setIsHavaPassword(true);
        }
        ssidList.add(s3);

        return ssidList;
    }

    @Override
    public MerchantInfo getWifiProductInfomation(Integer merchantId)
        throws Exception
    {
        Query merchantQuery = new Query(Criteria.where("merchantId").is(merchantId).and("status").is(
            DeviceInfo.STATUS_NORMAL));
        MerchantInfo mer = mongoTemplate.findOne(merchantQuery, MerchantInfo.class);
        if (null == mer)
        {
            throw new BussinessException("商户还未开通wifi产品");
        }
        if(null==mer.getUserId()){
        	throw new BussinessException("该商户为历史遗留商户，不允许操作");
        }

        Query deviceQuery = new Query(Criteria.where("merchantId").is(merchantId).and("status").is(
            DeviceInfo.STATUS_NORMAL));
        List<DeviceInfo> deviceList = mongoTemplate.find(deviceQuery, DeviceInfo.class);
        for (DeviceInfo di : deviceList)
        {
            String mac = di.getMac().toLowerCase();
            mac = mac.substring(0, 2) + "-" + mac.substring(2, 4) + "-" + mac.substring(4, 6) + "-"
                  + mac.substring(6, 8) + "-" + mac.substring(8, 10) + "-" + mac.substring(10, 12);
            di.setMac(mac);
        }
        mer.setDeviceInfoList(deviceList);
        return mer;
    }

    /**
     * Description: <br> 封装满足芝麻要求的shop对象 Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param brandId
     * @param merchantId
     * @param macList
     * @param shopId
     *            -1代表新增
     * @return
     * @throws Exception
     * @see
     */
    private Shop getShop(Integer merchantId, StringList macList, Integer shopId)
        throws Exception
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", "{merchantId:" + merchantId + "}"));
        nvps.add(new BasicNameValuePair("key", crmApiKey));

        // 获取商户信息

        // JSONObject merchantJson = getMerchantByMerchantId(merchantId);

        JSONObject merchantInfoJson = getMerchantInfoByMerchantId(merchantId);
        Shop shop = new Shop();
        shop.setId(shopId == null ? -1 : shopId);
        shop.setAddress("".equals(merchantInfoJson.getString("address")) ? "商户未录入" : merchantInfoJson.getString("address"));
        shop.setBrand(merchantInfoJson.getString("merchantName"));
        shop.setCategory("餐饮");
        shop.setMac(macList);
        shop.setName(merchantInfoJson.getString("merchantName"));
        JSONArray citylist = new CityJson().getJson();
        String province = merchantInfoJson.getString("province");
        String city = merchantInfoJson.getString("city");
        if (province == null || province.equals(""))
        {
            shop.setProvince("北京");
            shop.setCity("北京");
            shop.setDistrict("东城区");
        }
        else
        {
            try
            {
                shop.setProvince(province);
                JSONObject provinceJson = null;
                for (int i = 0; i < citylist.size(); i++ )
                {
                    JSONObject provinceTemp = (JSONObject)citylist.get(i);
                    if (provinceTemp.getString("p").equals(province))
                    {
                        provinceJson = provinceTemp;
                        break;
                    }
                }
                if (city == null || city.equals(""))
                {
                    JSONObject cityJson = (JSONObject)(provinceJson.getJSONArray("c").get(0));
                    city = cityJson.getString("n");
                    shop.setCity(city);
                    JSONObject districtJson = (JSONObject)(cityJson.getJSONArray("a").get(0));
                    shop.setDistrict(districtJson.getString("s"));
                }
                else
                {
                    shop.setCity(city);
                    JSONObject cityJson = null;
                    for (int i = 0; i < provinceJson.getJSONArray("c").size(); i++ )
                    {
                        JSONObject cityTemp = (JSONObject)provinceJson.getJSONArray("c").get(i);
                        if (cityTemp.getString("p").equals(city))
                        {
                            cityJson = cityTemp;
                            break;
                        }
                    }
                    shop.setDistrict(((JSONObject)(cityJson.getJSONArray("a").get(0))).getString("s"));
                }
            }
            catch (Exception e)
            {
                log.error("解析地理位置失败", e);
                shop.setProvince("北京");
                shop.setCity("北京");
                shop.setDistrict("东城区");
            }

        }

        return shop;
    }

    /**
     * Description: <br> 封装满足芝麻要求的shop对象 Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param brandId
     * @param merchantId
     * @param macList
     * @param shopId
     *            -1代表新增
     * @return
     * @throws Exception
     * @see
     */
    private Shop getShop(String merchangName, StringList macList, Integer shopId)
        throws Exception
    {
        Shop shop = new Shop();
        shop.setId(shopId == null ? -1 : shopId);
        shop.setAddress("商户未录入");
        shop.setBrand(merchangName);
        shop.setCategory("餐饮");
        shop.setMac(macList);
        shop.setName(merchangName);
        JSONArray citylist = new CityJson().getJson();
        String province = null;
        String city = null;
        if (province == null || province.equals(""))
        {
            shop.setProvince("北京");
            shop.setCity("北京");
            shop.setDistrict("东城区");
        }

        return shop;
    }

    /**
     * Description: <br> 请求芝麻接口同步数据 Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param account
     * @return
     * @throws Exception
     * @see
     */
    private JSONObject pushAccountToZhima(Map<String, Object> account)
        throws Exception
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(appKey + day + time + secretKey, null);

        String accountJson = JSONObject.fromObject(account).toString();
        log.info(accountJson);
        accountJson = URLEncoder.encode(accountJson, "utf-8");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("data", accountJson));
        nvps.add(new BasicNameValuePair("key", appKey));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));

        JSONObject accountInfo = HttpUtil.httpPostForJson(nvps, synchrodataBrand);
        return accountInfo;

    }

    @Override
    public JsonResult addOrUpdateWifiProductForNewBrand(Integer merchantId,String merchantName, StringList macs, String ssid1, String ssid2,
                                                String ssid3, String password1, String password2, String password3,
                                                String userName, String userMobile, String operatorId,
                                                String operatorMobileNumber,Boolean isPassWordCheck,Integer merchantType)
        throws Exception
    {
        MerchantInfo mi = getMerchantInfoByMid(merchantId);
        
        // 同步信息至芝麻科技
        String zhimaUsername = "zhimaAccount" + userMobile + "@yazuo.com";
        String zhimaPassword = "zhimaAccount" + userMobile;
        Account account = new Account();
        Shop shop = getShop(merchantName, macs, -1);
        List<Shop> shopList = new ArrayList<Shop>();
        shopList.add(shop);
        account.setShop(shopList);
        account.setUser_name(zhimaUsername);
        account.setUser_password(zhimaPassword);

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(account);

        Map<String, Object> acc = new HashMap<String, Object>();
        acc.put("account", accountList);
        JSONObject accountInfo = pushAccountToZhima(acc);
        if (accountInfo.getInt("code") != 1000)
        {
            throw new BussinessException("接口调用失败");
        }

        // 组织device信息
        List<DeviceSSID> ssidList = getSsidList(ssid1, ssid2, ssid3, password1, password2, password3);
        // 调用芝麻接口保存device信息
        long start=System.currentTimeMillis();
        for (String mac : macs)
        {
            setSsid(ssidList, mac);
        }
        log.info("调用config耗时："+(start-System.currentTimeMillis()));
        com.yazuo.api.service.account.vo.User user =null;
        if(mi == null){
	        // 创建CRM商户 以及开卡
	        mi = addCrmBrand(merchantId,userMobile, userName, merchantName);
	        
	        //添加用户并给用户权限
	        List<Integer> authorityIds = getAllAuthorityIds();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("userInfoName", userName);
	        map.put("mobile", userMobile);
	        map.put("merchantId", mi.getMerchantId());
	        map.put("authorityIds", authorityIds);
	        user = userService.addOrUpdateUser(map);
	        
	        merchantWebService.accreditAuthForMerchant(mi.getMerchantId(), Integer.valueOf(superwiki_productId),
	                100L * 3600 * 24 * 365*1000);

	        // 保存商户信息
	        JSONObject accountJson = (JSONObject)accountInfo.getJSONObject("data").getJSONArray("account").get(0);
	        JSONObject shopJson = (JSONObject)accountJson.getJSONArray("shop").get(0);
	        int shopId = shopJson.getInt("id");
	        MerchantInfo mer = new MerchantInfo();
	        mer.setAdminPassWord(null);
	        mer.setBrandId(mi.getBrandId());
	        mer.setShopId(shopId);
	        mer.setInsertTime(new Date());
	        mer.setIsFaceShop(true);
	        mer.setMerchantId(mi.getMerchantId());
	        mer.setMerchantName(shop.getName());
	        mer.setStatus(MerchantInfo.STATUS_NORMAL);
	        mer.setZhimaPassword(zhimaPassword);
	        mer.setZhimaUsername(zhimaUsername);
	        mer.setSource(MerchantInfo.SOURCE_ALONE);
	        mer.setOperatorId(operatorId);
	        mer.setOperatorMobileNumber(operatorMobileNumber);
	        mer.setBossMobile(userMobile);
	        mer.setBossName( userName);
	        mer.setUserId(user.getUserInfoId());
	        mer.setIsPassWordCheck(isPassWordCheck);
	    //    mer.setMerchantType(merchantType);
	        mongoTemplate.insert(mer);
        }else{
        	//修改crm商户
        	addCrmBrand(mi.getMerchantId(),userMobile, userName, merchantName);
        	
        	//如果该商户是没有换联系人，一切不变，如果换了联系人则要把原因联系人的权限清除，给新用户添加权限
        	 Map<String, Object> map= new HashMap<String, Object>();
        	if(!userMobile.equals(mi.getBossMobile())){
        		// 解除原用户wifi权限
                List<Integer> userIds =new ArrayList<Integer>();
                userIds.add(mi.getUserId());
                map.put("userIdList", userIds);
                userService.deleteUser(map);
                
                //添加用户并给用户权限
    	        List<Integer> authorityIds = getAllAuthorityIds();
    	        map.clear();
    	        map.put("userInfoName", userName);
    	        map.put("mobile", userMobile);
    	        map.put("merchantId", mi.getMerchantId());
    	        map.put("authorityIds", authorityIds);
    	        user = userService.addOrUpdateUser(map);
        	}else{
        		if(!userName.equals(mi.getBossName())){
        			List<Integer> authorityIds = getAllAuthorityIds();
        	        map.clear();
        	        map.put("userInfoName", userName);
        	        map.put("mobile", userMobile);
        	        map.put("merchantId", mi.getMerchantId());
        	        map.put("userInfoId", mi.getUserId());
        	        map.put("authorityIds", authorityIds);
        	        user = userService.addOrUpdateUser(map);
        		}
        	}
        	 // 修改wifi商户信息
        	mongoTemplate.updateMulti(new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
        			Update.update("bossMobile", userMobile).set("bossName", userName).set("merchantName", merchantName)
        			.set("lastUpdateTime", new Date()).set("isPassWordCheck", isPassWordCheck), MerchantInfo.class);
        }
        // 保存device信息
        for (String mac : macs)
        {
            Update up = new Update();
            up.set("devSSID", ssidList);
            up.set("lastUpdateTime", new Date());
            up.set("status", DeviceInfo.STATUS_DELETE);
            mongoTemplate.updateMulti(new Query(Criteria.where("mac").is(mac)), up, DeviceInfo.class);
            
        }
        
        if(null!=merchantId){
            //释放设备
            deviceService.releaseDevice(merchantId);
        }
        for (String mac : macs)
        {
            DeviceInfo di = new DeviceInfo();
            di.setBrandId(mi.getBrandId());
            di.setDevSSID(ssidList);
            di.setInsertTime(new Date());
            di.setMac(mac);
            di.setMerchantId(mi.getMerchantId());
            di.setStatus(DeviceInfo.STATUS_NORMAL);

            mongoTemplate.insert(di);
            deviceService.activatedDevice(mac);
        }


        return new JsonResult().setFlag(true).setMessage("增加商户成功!");

    }

    private MerchantInfo addCrmBrand(Integer merchantId,String bossMobile, String bossName, String merchantName)
        throws Exception
    {
        MerchantVo mv = new MerchantVo();
        mv.setBossMobile(bossMobile);
        mv.setBossName(bossName);
        mv.setMerchantName(merchantName);
        mv.setMerchantId(merchantId);
        MerchantVo newBrand = merchantWebService.addOrUpDateMerchant(mv);
        if(null==merchantId||merchantId.intValue()==0){
            MerchantVo brandVo = (MerchantVo)CloneUtil.objectDeepCopy(newBrand);
            brandVo.setMerchantId(newBrand.getBrandId());
            long start=System.currentTimeMillis();
            cardWebService.createCardRecord(brandVo, ClientContents.CardBatchEnum.VIRTUAL.getCode(), 2000, false);
            log.info("开卡耗时："+(start-System.currentTimeMillis()));
        }
        MerchantInfo mi = new MerchantInfo();
        mi.setBossMobile(bossMobile);
        mi.setBossName(bossName);
        mi.setBrandId(newBrand.getBrandId());
        mi.setMerchantId(newBrand.getMerchantId());
        mi.setIsFaceShop(true);
        mi.setInsertTime(new Date());
        mi.setLastUpdateTime(new Date());
        mi.setMerchantName(merchantName);
        mi.setParent(newBrand.getParentId());
        return mi;
    }

    private void setSsid(List<DeviceSSID> ssids, String mac)
        throws Exception
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(new Date());
        String time = String.valueOf(System.currentTimeMillis());
        String token = md5PasswordEncoder.encodePassword(appKey + day + time + secretKey, null);

        List<Ssid> ssidList = new ArrayList<Ssid>();
        Ssid s1 = new Ssid();
        s1.setId(1);
        s1.setName(ssids.get(0).getSsid());
        s1.setPortal_addr(portal);
        if (null == ssids.get(0).getSsid() || "".equals(ssids.get(0).getSsid())){
            s1.setPortal_status(0);
        }else
        {
            s1.setPortal_status(1);
        }
        if (null == ssids.get(0).getPassword() || "".equals(ssids.get(0).getPassword()))
        {
            s1.setEnable_ssid_authen(0);
            s1.setEncrypt_mode(0);
            s1.setPassword("");;
        }
        else
        {
            s1.setEnable_ssid_authen(8);
            s1.setEncrypt_mode(4);
            s1.setPassword(ssids.get(0).getPassword());;
        }

        ssidList.add(s1);
        Ssid s2 = new Ssid();
        s2.setId(2);
        s2.setName(ssids.get(1).getSsid());
        s2.setPortal_addr(portal);
        if (null == ssids.get(1).getSsid() || "".equals(ssids.get(1).getSsid())){
            s2.setPortal_status(0);
        }else
        {
            s2.setPortal_status(1);
        }
        if (null == ssids.get(1).getPassword() || "".equals(ssids.get(1).getPassword()))
        {
            s2.setEnable_ssid_authen(0);
            s2.setEncrypt_mode(0);
            s2.setPassword("");;
        }
        else
        {
            s2.setEnable_ssid_authen(8);
            s2.setEncrypt_mode(4);
            s2.setPassword(ssids.get(1).getPassword());;
        }
        ssidList.add(s2);
        Ssid s3 = new Ssid();
        s3.setId(3);
        s3.setName(ssids.get(2).getSsid());
        s3.setPortal_addr(portal);
        if (null == ssids.get(2).getSsid() || "".equals(ssids.get(2).getSsid())){
            s3.setPortal_status(0);
        }else
        {
            s3.setPortal_status(1);
        }
        if (null == ssids.get(2).getPassword() || "".equals(ssids.get(2).getPassword()))
        {
            s3.setEnable_ssid_authen(0);
            s3.setEncrypt_mode(0);
            s3.setPassword("");;
        }
        else
        {
            s3.setEnable_ssid_authen(8);
            s3.setEncrypt_mode(4);
            s3.setPassword(ssids.get(2).getPassword());;
        }
        ssidList.add(s3);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ssid", ssidList);
        JSONObject jo = JSONObject.fromObject(map);
        String data = jo.toString();
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", appKey));
        nvps.add(new BasicNameValuePair("token", token));
        nvps.add(new BasicNameValuePair("day", day));
        nvps.add(new BasicNameValuePair("time", time));
        nvps.add(new BasicNameValuePair("deviceMac", mac.replace(":", "").trim()));
        nvps.add(new BasicNameValuePair("data", data));

        JSONObject accountInfo=null;
        int requestTimes=0;
        while(requestTimes<3){
            try
            {
                accountInfo = HttpUtil.httpPostForJson(nvps, config);
                if(accountInfo.getString("code").equals("1000")){
                    return;
                }else{
                    log.error("请求芝麻config失败"+ ++requestTimes+"次\n"+accountInfo.toString());
                    continue;
                }
                
            }
            catch (Exception e)
            {
                log.error("请求芝麻config失败"+ ++requestTimes+"次", e);
                e.printStackTrace();
                continue;
            }
        }
        
        throw new BussinessException(accountInfo.toString());
    }

    @Override
    public JSONObject getMerchantByMerchantId(Integer merchantId)
        throws BadCredentialsException
    {
        String json = "";
        JSONObject jo = new JSONObject();
        jo.accumulate("merchantId", merchantId);
        String ciphertext = jo.toString();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", ciphertext));
        nvps.add(new BasicNameValuePair("key", crmApiKey));

        log.info("访问参数地址：" + getMerchantByMerchantId);
        try
        {
            json = HttpUtil.httpPostForString(nvps, getMerchantByMerchantId);
        }
        catch (Exception e)
        {
            throw new BadCredentialsException("商户不存在或状态不正常");
        }

        json = json.replace("\\t", "");
        JSONObject ja = JSONObject.fromObject(json);
        JSONObject data = JSONObject.fromObject(ja.get("data"));
        JSONObject result = JSONObject.fromObject(data.get("result"));
        Object merchants = result.get("merchants");
        if (null == merchants)
        {
            log.error("CRM没有查到id为：" + merchantId + "的商户信息，请检查crmMerchantId是否正确");
            throw new BadCredentialsException("商户不存在或状态不正常");
        }
        JSONObject merchant = JSONObject.fromObject(merchants);
        Integer status = (Integer)merchant.get("status");
        if (status.intValue() != 1)
        {
            throw new BadCredentialsException("商户不存在或状态不正常");
        }
        return merchant;
    }

    @Override
    public JSONObject getMerchantInfoByMerchantId(Integer merchantId)
        throws BadCredentialsException
    {
        String json = "";
        JSONObject jo = new JSONObject();
        jo.accumulate("merchantId", merchantId);
        String ciphertext = jo.toString();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("ciphertext", ciphertext));
        nvps.add(new BasicNameValuePair("key", crmApiKey));

        log.info("访问参数地址：" + getMerchantInfoByMerchantId);
        try
        {
            json = HttpUtil.httpPostForString(nvps, getMerchantInfoByMerchantId);
        }
        catch (Exception e)
        {
            throw new BadCredentialsException("商户不存在或状态不正常");
        }

        json = json.replace("\\t", "");
        JSONObject ja = JSONObject.fromObject(json);
        JSONObject data = JSONObject.fromObject(ja.get("data"));
        JSONObject result = JSONObject.fromObject(data.get("result"));
        Object merchants = result.get("data");
        if (null == merchants)
        {
            log.error("CRM没有查到id为：" + merchantId + "的商户信息，请检查crmMerchantId是否正确");
            throw new BadCredentialsException("商户不存在或状态不正常");
        }
        JSONObject merchant = JSONObject.fromObject(merchants);
        return merchant;
    }

    @Override
    public MerchantInfo getMerchantInfoByMid(Integer merchantId)
        throws Exception
    {
        MerchantInfo mer = mongoTemplate.findOne(new Query(
            Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            MerchantInfo.class);
        return mer;
    }

    @Override
    public HomePage getCustomizeHomepage(Integer brandId)
        throws Exception
    {
        Assert.notNull(brandId, "品牌id不能为空");
        HomePage homePage = mongoTemplate.findOne(new Query(Criteria.where("brandId").is(brandId)),
            HomePage.class);
        return homePage;
    }

    @Override
    public List<MerchantInfo> getMerchantListByOperatorId(String operatorId,Integer timeSortBy)
        throws Exception
    {
        Query query = new Query(Criteria.where("operatorId").is(operatorId).and("status").is(MerchantInfo.STATUS_NORMAL));
        if(timeSortBy.intValue() == 1){//按时间倒叙
            query.sort().on("insertTime", Order.DESCENDING);
        }else{
            query.sort().on("insertTime", Order.ASCENDING);
        }
        List<MerchantInfo> merchantList = mongoTemplate.find(query, MerchantInfo.class);
        return merchantList;
    }

    @Override
    public List<MerchantInfo> getMerchantListBySearchKey(String operatorId,String searchKey)
        throws Exception
    {
        Criteria criteriaV1 = Criteria.where("bossMobile").regex(searchKey);
        Criteria criteriaV2 = Criteria.where("bossName").regex(searchKey);
        Criteria criteriaV3 = Criteria.where("merchantName").regex(searchKey);
        Query query = new Query(Criteria.where("operatorId").is(operatorId).orOperator(criteriaV1, criteriaV2, criteriaV3));
        List<MerchantInfo> merchantList = mongoTemplate.find(query, MerchantInfo.class);
        return merchantList;
    }

    @Override
    public Integer getMerchantTypeByMerchantId(Integer merchantId)
        throws BadCredentialsException
    {
        JSONObject merchant = getMerchantByMerchantId(merchantId);
        if (merchant.getBoolean("isFaceShop"))
        {
            return FACESHOPTYPE_FACESHOP;
        }
        else
        {
            if (merchant.getInt("merchantId") == merchant.getInt("brandId"))
            {
                return FACESHOPTYPE_BRAND;
            }
            else
            {
                return FACESHOPTYPE_MANAGERCOMPANY;
            }

        }
    }

    @Override
    public Boolean selectAuthForMerchant(Integer merchantId)
        throws Exception
    {
        Boolean flag = false;
        List<MerchantProductVo> productsList = merchantWebService.selectAuthForMerchant(merchantId);
        for (MerchantProductVo merchantProductVo : productsList)
        {
            int proId = merchantProductVo.getProductId().intValue();
            if (proId == Integer.valueOf(superwiki_productId).intValue())
            {
                flag = true;
                break;
            }
        }
        return flag;
    }
}

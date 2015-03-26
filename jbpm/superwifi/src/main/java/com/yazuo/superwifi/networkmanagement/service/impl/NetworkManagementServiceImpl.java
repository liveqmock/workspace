package com.yazuo.superwifi.networkmanagement.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.merchant.service.MerchantService;
import com.yazuo.superwifi.networkmanagement.service.NetworkManagementService;
import com.yazuo.superwifi.networkmanagement.vo.AccessRestrictions;
import com.yazuo.superwifi.networkmanagement.vo.AppInfo;
import com.yazuo.superwifi.networkmanagement.vo.BlackWhiteList;
import com.yazuo.superwifi.pagehelper.Page;
import com.yazuo.superwifi.security.service.UserService;
import com.yazuo.superwifi.util.JsonResult;


@Service("networkManagementServiceImpl")
public class NetworkManagementServiceImpl implements NetworkManagementService
{

    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Value("#{propertiesReader['deviceMemCount']}")
    String deviceMemCount;

    @Value("#{propertiesReader['addOrUpdateBlackWhiteList']}")
    String addOrUpdateBlackWhiteList;

    @Value("#{propertiesReader['yazuoToken']}")
    String yazuoToken;

    @Resource(name = "merchantServiceImpl")
    private MerchantService merchantService;

    @Resource(name = "userService")
    private UserService userService;

    private String mac = "xx-xx-xx-xx-xx";

    @Override
    public JsonResult getBlackWhiteList(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        Integer type = (Integer)paramMap.get("type");
        String phoneNum = (String)paramMap.get("phoneNum");
        Integer pageSize = (Integer)paramMap.get("pageSize");
        Integer pageNumber = (Integer)paramMap.get("pageNumber");
        Integer source = paramMap.get("source") == null ? 0 : (Integer)paramMap.get("source");// 如果是0查询全部，1查询店员，2查询自动踢出的
        Query query = new Query();
        if (!"".equals(phoneNum))
        {
            if (merchantId != 0 && merchantId != null)
            {
                query = new Query(Criteria.where("type").is(type).and("merchantId").is(merchantId).and("brandId").is(
                    brandId).and("phoneNum").regex((".*?" + phoneNum + ".*")).and("status").is(
                    BlackWhiteList.STATUS_NORMAL));

            }
            else
            {
                query = new Query(Criteria.where("type").is(type).and("phoneNum").regex((".*?" + phoneNum + ".*")).and(
                    "brandId").is(brandId).and("status").is(BlackWhiteList.STATUS_NORMAL));
            }
        }
        else
        {
            if (merchantId != 0 && merchantId != null)
            {
                if (type.intValue() == BlackWhiteList.TYPE_BLACK
                    && source.intValue() == BlackWhiteList.SOURCE_TIMEOUT.intValue())
                {
                    // 如果是2，查询最近超时的用户，否则查询全部用户
                    query = new Query(
                        Criteria.where("type").is(type).and("merchantId").is(merchantId).and("status").is(
                            BlackWhiteList.STATUS_NORMAL).and("source").is(source));
                }
                else
                {
                    query = new Query(
                        Criteria.where("type").is(type).and("merchantId").is(merchantId).and("status").is(
                            BlackWhiteList.STATUS_NORMAL));
                }
            }
            else
            {
                query = new Query(Criteria.where("type").is(type).and("brandId").is(brandId).and("status").is(
                    BlackWhiteList.STATUS_NORMAL));
            }
        }
        // query.sort().on("insertTime", Order.DESCENDING);
        query.skip((pageNumber - 1) * pageSize);
        query.limit(pageSize);
        List<BlackWhiteList> bwl = new ArrayList<BlackWhiteList>();

        try
        {
            bwl = mongoTemplate.find(query, BlackWhiteList.class);
            int totle = bwl.size();
            Integer totalSize = new Integer(totle);
            jsoninfo.setFlag(true).setMessage("查询成功");
            paramMap.clear();
            paramMap.put("totalSize", totalSize);
            paramMap.put("rows", bwl);
            jsoninfo.setData(paramMap);
        }
        catch (Exception e)
        {
            jsoninfo.setFlag(false).setMessage("查询失败数据异常");
            e.printStackTrace();

        }
        return jsoninfo;
    }

    @Override
    public JsonResult addOrUpdateBlackWhiteList(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer brandId = (Integer)paramMap.get("brandId");
        List<String> phoneList = (List<String>)paramMap.get("phoneNum");
        Integer type = (Integer)paramMap.get("type");
        Integer merchantId = (Integer)paramMap.get("merchantId");
        // 操作类型，1添加，2修改
        Integer actionType = (Integer)paramMap.get("actionType");
        String managerPsd = (String)paramMap.get("managerPsd");

        if (actionType != 0 && merchantId != 0 && brandId != 0 && type != 0 && !"".equals(managerPsd)
            && managerPsd != null && phoneList != null)
        {
            // 验证主管密码
            Boolean flag = merchantService.checkAdminPassWord(merchantId, brandId, managerPsd);
            if (flag)
            {
                switch (actionType)
                {
                    case 1:
                        for (String phoneNums : phoneList)
                        {
                            // 获取该手机对应的mac
                            Member mem = mongoTemplate.findOne(
                                new Query(Criteria.where("phoneNumber").is(phoneNums).and("brandId").is(brandId).and(
                                    "status").is(Member.STATUS_NORMAL)), Member.class);
                            if (mem == null)
                            {
                                return jsoninfo.setFlag(false).setMessage("用户" + phoneNums + "还没有连接过WiFi，请先连接WiFi");
                            }
                            else
                            {
                                mac = mem.getMac();
                            }
                            // 查看该手机是否在白名单或者店员名单中存在，如果存在不允许重复添加
                            List<BlackWhiteList> phonel = mongoTemplate.find(
                                new Query(Criteria.where("phoneNum").is(phoneNums).and("type").is(type).and(
                                    "merchantId").is(merchantId).and("status").is(BlackWhiteList.STATUS_NORMAL)),
                                BlackWhiteList.class);
                            if (phonel != null && phonel.size() > 0)
                            {
                                return jsoninfo.setFlag(false).setMessage("手机号" + phoneNums + "已经存在");
                            }
                            BlackWhiteList art = new BlackWhiteList();
                            art.setMerchantId(merchantId);
                            art.setStatus(BlackWhiteList.STATUS_NORMAL);
                            art.setType(type);
                            art.setBrandId(brandId);
                            art.setPhoneNum(phoneNums);
                            art.setInsertTime(new Date());
                            art.setLastUpdateTime(new Date());
                            art.setMac(mac);
                            mongoTemplate.insert(art);
                        }
                        return jsoninfo.setFlag(true).setMessage("插入数据成功");
                    case 2:
                        for (String phoneNums : phoneList)
                        {
                            mongoTemplate.updateFirst(
                                new Query(
                                    Criteria.where("phoneNum").is(phoneNums).and("merchantId").is(merchantId).and(
                                        "status").is(BlackWhiteList.STATUS_NORMAL)),
                                Update.update("status", BlackWhiteList.STATUS_DELETE).set("lastUpdateTime", new Date()),
                                BlackWhiteList.class);
                        }
                        return jsoninfo.setFlag(true).setMessage("从" + (type == 1 ? "白名单" : "店员名单") + "移除成功");
                    case 3:
                        for (String phoneNums : phoneList)
                        {
                            mongoTemplate.updateFirst(
                                new Query(
                                    Criteria.where("phoneNum").is(phoneNums).and("merchantId").is(merchantId).and(
                                        "status").is(BlackWhiteList.STATUS_NORMAL)),
                                Update.update("type", type).set("lastUpdateTime", new Date()), BlackWhiteList.class);
                        }
                        return jsoninfo.setFlag(true).setMessage("添加至" + (type == 1 ? "白名单" : "店员名单") + "成功");
                    default:
                        return jsoninfo.setFlag(true).setMessage("参数错误");
                }
            }
            else
            {
                return jsoninfo.setFlag(true).setMessage("主管密码错误");
            }
        }
        else
        {
            return jsoninfo.setFlag(false).setMessage("参数不正确");
        }
    }

    @Override
    public JsonResult getAccessRestrictionsList(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        Integer pageSize = (Integer)paramMap.get("pageSize");
        Integer pageNumber = (Integer)paramMap.get("pageNumber");
        Integer type = (Integer)paramMap.get("type");
        Query query = new Query();
        if (merchantId != 0 && merchantId != null)
        {
            query = new Query(Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("type").is(
                type).and("status").is(AccessRestrictions.STATUS_NORMAL));
            // query.sort().on("insertTime", Order.DESCENDING);
            query.skip((pageNumber - 1) * pageSize);
            query.limit(pageSize);
            List<AccessRestrictions> BW = mongoTemplate.find(query, AccessRestrictions.class);
            Integer totalSize = BW.size();

            jsoninfo.setFlag(true).setMessage("查询成功");
            paramMap.clear();
            paramMap.put("totalSize", totalSize);
            paramMap.put("rows", BW);
            jsoninfo.setData(paramMap);
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("查询参数不正确");
        }
        return jsoninfo;
    }

    @Override
    public JsonResult getTopAccessRestrictionsList(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer type = (Integer)paramMap.get("type");
        // 获取该类型的所有信息,分组获取URL的屏蔽量
        GroupBy groupBy = GroupBy.key("url", "name").initialDocument("{count:0}").reduceFunction(
            "function(doc,prev){prev.count++;}");
        GroupByResults<AccessRestrictions> BW = mongoTemplate.group(
            Criteria.where("type").is(type).and("status").is(AccessRestrictions.STATUS_NORMAL), "accessRestrictions",
            groupBy, AccessRestrictions.class);
        // 根据URL分组结果
        BasicDBList list = (BasicDBList)BW.getRawResults().get("retval");
        // 获取所有使用屏蔽网页功能的商户的数量,使用mongo的distinct
        CommandResult result = mongoTemplate.executeCommand("{distinct:'accessRestrictions', key:'merchantId'}");
        BasicDBList merchantIds = (BasicDBList)result.get("values"); // 使用屏蔽功能的商户数
        Integer merCount = merchantIds.size();
        // url屏蔽率的结果集
        List<Map<String, Object>> perList = new ArrayList<Map<String, Object>>();
        // 循环计算每个url 的限制率
        for (int i = 0; i < list.size(); i++ )
        {
            Map<String, Object> m = new HashMap<String, Object>();
            BasicDBObject obj = (BasicDBObject)list.get(i);
            BigDecimal urlCount = new BigDecimal(obj.getDouble("count"));// url的限制个数
            // url屏蔽率 = url的个数/商户总数*100
            BigDecimal accessPercent = urlCount.divide(new BigDecimal(merCount), 2, BigDecimal.ROUND_HALF_EVEN).multiply(
                new BigDecimal(100));

            m.put("urlPercent", accessPercent);
            m.put("url", obj.getString("url"));
            m.put("appName", obj.getString("name"));
            perList.add(m);
        }
        // 按照百分比，排序，使用冒泡
        Map<String, Object> temp = null;
        for (int i = 0; i < perList.size() - 1; i++ )
        {
            for (int j = i + 1; j < perList.size(); j++ )
            {
                Double a = Double.parseDouble(perList.get(i).get("urlPercent").toString());
                Double b = Double.parseDouble(perList.get(j).get("urlPercent").toString());
                if (a < b)
                {
                    temp = perList.get(i);
                    perList.set(i, perList.get(j));
                    perList.set(j, temp);
                }
            }
        }

        jsoninfo.setFlag(true).setMessage("商户" + (type == 1 ? "网址" : "app") + "屏蔽排行查询成功");
        jsoninfo.setData(perList);
        return jsoninfo;
    }

    @Override
    public JsonResult addAccessRestrictions(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = paramMap.get("merchantId") != null ? (Integer)paramMap.get("merchantId") : null;
        Integer brandId = (Integer)paramMap.get("brandId");
        Integer type = (Integer)paramMap.get("type");
        String url = paramMap.get("url") != null ? paramMap.get("url").toString() : null;
        String appName = (String)paramMap.get("appName");
        // 主管密码验证
        String managerPsd = (String)paramMap.get("managerPsd");
        List<AccessRestrictions> ac = mongoTemplate.find(
            new Query(Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("status").is(
                AccessRestrictions.STATUS_NORMAL)), AccessRestrictions.class);
        if (merchantId != null)
        {
            if ("".equals(managerPsd) || managerPsd == null)
            {
                jsoninfo.setFlag(false).setMessage("请输入主管密码");
            }
            else
            {
                // 验证主管密码
                Boolean flag = merchantService.checkAdminPassWord(merchantId, brandId, managerPsd);
                if (flag)
                {// 验证通过，添加APP或者网址
                    for (AccessRestrictions a : ac)
                    {
                        if (type.intValue() == AccessRestrictions.TYPE_APP)
                        {
                            if (a.getName() != null && a.getName().equals(appName))
                            {
                                return jsoninfo.setFlag(false).setMessage("app不能重复添加");
                            }
                        }
                        else
                        {
                            if (url == null)
                            {
                                return jsoninfo.setFlag(false).setMessage("请填写网页url");
                            }
                            if (url.equals(a.getUrl()))
                            {
                                return jsoninfo.setFlag(false).setMessage("url不能重复添加");
                            }
                        }
                    }
                    AccessRestrictions ar = new AccessRestrictions();
                    ar.setBrandId(brandId);
                    ar.setMerchantId(merchantId);
                    ar.setStatus(AccessRestrictions.STATUS_NORMAL);
                    ar.setType(type);
                    ar.setUrl(url);
                    ar.setInsertTime(new Date());
                    ar.setName(appName);
                    mongoTemplate.insert(ar);
                    // 通过接口给芝麻科技，最终添加到芝麻科技的数据库中

                    jsoninfo.setFlag(true).setMessage("插入网址成功");
                }
                else
                {
                    jsoninfo.setFlag(false).setMessage("主管密码错误");
                }
            }
        }
        else
        {
            jsoninfo.setFlag(false).setMessage("参数错误");
        }
        return jsoninfo;
    }

    @Override
    public JsonResult deleteAccessRestrictions(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        List<String> idList = (List<String>)paramMap.get("id");
        String managerPsd = (String)paramMap.get("managerPsd");
        if ("".equals(managerPsd) || managerPsd == null)
        {
            jsoninfo.setFlag(false).setMessage("请输入主管密码");
        }
        else
        {
            Boolean flag = merchantService.checkAdminPassWord(merchantId, brandId, managerPsd);
            if (flag)
            {
                if (merchantId == 0 && brandId == 0 && idList == null)
                {
                    jsoninfo.setFlag(false).setMessage("参数错误");
                }
                else
                {
                    try
                    {
                        for (String id : idList)
                        {
                            mongoTemplate.updateFirst(
                                new Query(Criteria.where("id").is(id).and("brandId").is(brandId).and("merchantId").is(
                                    merchantId)),
                                Update.update("status", AccessRestrictions.STATUS_DELETE).set("lastUpdateTime",
                                    new Date()), AccessRestrictions.class);
                        }
                        jsoninfo.setFlag(true).setMessage("移除成功");
                    }
                    catch (Exception e)
                    {
                        jsoninfo.setFlag(false).setMessage("失败，不存在或系统错误");
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                jsoninfo.setFlag(false).setMessage("主管密码错误");
            }
        }
        return jsoninfo;
    }

    @Override
    public JsonResult getBlackWhiteCountNum(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        Integer type = (Integer)paramMap.get("type");
        Query query;
        query = new Query(
            Criteria.where("type").is(type).and("merchantId").is(merchantId).and("brandId").is(brandId).and("status").is(
                BlackWhiteList.STATUS_NORMAL));
        Long total = mongoTemplate.count(query, BlackWhiteList.class);
        jsoninfo.setFlag(true).setMessage("查询成功");
        paramMap.clear();
        jsoninfo.setData(total.intValue());
        return jsoninfo;
    }

    @Override
    public JsonResult getAccessRestrictionsNum(Map<String, Object> paramMap)
        throws Exception
    {
        JsonResult jsoninfo = new JsonResult();
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");

        Query query1 = new Query(
            Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("status").is(
                AccessRestrictions.STATUS_NORMAL).and("type").is(AccessRestrictions.TYPE_APP));

        Query query2 = new Query(
            Criteria.where("merchantId").is(merchantId).and("brandId").is(brandId).and("status").is(
                AccessRestrictions.STATUS_NORMAL).and("type").is(AccessRestrictions.TYPE_WEB));
        Long totalApp = mongoTemplate.count(query1, AccessRestrictions.class);
        Long totalWeb = mongoTemplate.count(query2, AccessRestrictions.class);

        jsoninfo.setFlag(true).setMessage("查询成功");
        paramMap.clear();
        paramMap.put("totalApp", totalApp.intValue());
        paramMap.put("totalWeb", totalWeb.intValue());
        paramMap.put("total", totalApp.intValue() + totalWeb.intValue());
        jsoninfo.setData(paramMap);
        return jsoninfo;
    }

    @Override
    public JsonResult synchronousDate(Map<String, Object> paramMap)
        throws Exception
    {
        Integer merchantId = (Integer)paramMap.get("merchantId");
        Integer brandId = (Integer)paramMap.get("brandId");
        // 组装信息，获取用户信息
        paramMap.put("pageSize", 10000);
        paramMap.put("pageNumber", 1);
        // 获取商户的用户列表
        Page<Map<String, Object>> userList = userService.listUsers(paramMap);
        // 获取该商户的黑白名单list
        List<BlackWhiteList> blackWhList = mongoTemplate.find(new Query(
            Criteria.where("merchantId").is(merchantId).and("status").is(BlackWhiteList.STATUS_NORMAL)),
            BlackWhiteList.class);
        // 获取该商户所在的集团的会员列表
        List<Member> memList = mongoTemplate.find(
            new Query(Criteria.where("brandId").is(brandId).and("status").is(Member.STATUS_NORMAL)), Member.class);
        for (Map<String, Object> u : userList)
        {
            String mobile = u.get("mobile") == null ? "" : u.get("mobile").toString();
            // String userName = u.get("userInfoName")==null?"":u.get("userInfoName").toString();
            mac = "xx-xx-xx-xx-xx";
            // 用户与商户的会员相比较，获取用户的mac
            for (Member m : memList)
            {
                if (m.getPhoneNumber().equals(mobile))
                {
                    // 如果用户同时也是会员，拿到该会员的mac
                    mac = m.getMac();
                    break;
                }
            }
            // 同步用户，遍历该商户的黑白名单，如果该用户已经存在在名单中只需要修改，如果不存在则要添加
            boolean flag = false;// 标示是否在名单中存在
            for (BlackWhiteList b : blackWhList)
            {
                if (b.getPhoneNum().equals(mobile))
                {
                    flag = true;
                    break;
                }
            }
            if (flag)
            {// 如果存在在名单中，修改
                mongoTemplate.updateFirst(
                    new Query(Criteria.where("merchantId").is(merchantId).and("phoneNum").is(mobile).and("status").is(
                        BlackWhiteList.STATUS_NORMAL)),
                    Update.update("mac", mac).set("lastUpdateTime", new Date()).set("type", BlackWhiteList.TYPE_BLACK).set(
                        "source", BlackWhiteList.SOURCE_FACESHOP), BlackWhiteList.class);
            }
            else
            {
                // 如果不存在，添加到名单中
                BlackWhiteList bw = new BlackWhiteList();
                bw.setBrandId(brandId);
                bw.setMac(mac);
                bw.setMerchantId(merchantId);
                bw.setInsertTime(new Date());
                bw.setLastUpdateTime(new Date());
                bw.setPhoneNum(mobile);
                bw.setSource(BlackWhiteList.SOURCE_FACESHOP);
                bw.setStatus(BlackWhiteList.STATUS_NORMAL);
                bw.setType(BlackWhiteList.TYPE_BLACK);
                mongoTemplate.insert(bw);
            }
        }
        JsonResult json = new JsonResult();
        json.setFlag(true).setMessage("同步完成，本次操作共同步了" + userList.getTotal() + "名用户!");
        return json;
    }

    @Override
    public List<AppInfo> getAppInfoList()
        throws Exception
    {
        List<AppInfo> appList = new ArrayList<AppInfo>();
        appList = mongoTemplate.find(new Query(Criteria.where("status").is(AppInfo.STATUS_NORMAL)), AppInfo.class);

        return appList;
    }

    @Override
    public BlackWhiteList getBlackWhiteListByMacAndBrandId(Integer merchantId, String mac)
        throws Exception
    {
        List<BlackWhiteList> list = mongoTemplate.find(
            new Query(Criteria.where("merchantId").is(merchantId).and("mac").is(mac).and("status").is(
                BlackWhiteList.STATUS_NORMAL).and("type").is(BlackWhiteList.TYPE_BLACK)), BlackWhiteList.class);
        if (null != list && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }
}

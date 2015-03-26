/*
 * 文件名：MemberServiceImpl.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年8月7日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.member.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.QueryOperators;
import com.yazuo.superwifi.device.vo.DeviceInfo;
import com.yazuo.superwifi.member.service.MemberService;
import com.yazuo.superwifi.member.vo.Member;
import com.yazuo.superwifi.member.vo.MemberLoginInfo;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.JsonResult;
import com.yazuo.superwifi.util.TimeUtil;
import com.yazuo.superwifi.util.UploadFileUtil;


@Service("memberServiceImpl")
public class MemberServiceImpl implements MemberService
{
    @Resource(name = "mongoTemplate")
    private MongoTemplate mongoTemplate;

    @Value("#{propertiesReader['dfs.server.ip']}")
    private String picDfsServer;

    @Value("#{propertiesReader['dfs.server.port']}")
    private Integer picDfsPort;

    @Value("#{propertiesReader['dfs.tracker.http.port']}")
    private String dfsTrackerHttpPort;

    @Override
    public Member getMemberByMac(Integer brandId, String mac)
        throws Exception
    {
        List<Member> memberList = mongoTemplate.find(new Query(
            Criteria.where("mac").is(mac).and("brandId").is(brandId).and("status").is(DeviceInfo.STATUS_NORMAL)),
            Member.class);
        if (null != memberList && memberList.size() > 0)
        {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public JsonResult memberAddStatic(Map<String, Object> map)
        throws Exception
    {
        JsonResult json = new JsonResult();
        Integer merchantId =Integer.valueOf(map.get("merchantId").toString());
        Long startTime = map.get("startTime") == "" ? null : (Long)map.get("startTime");
        Long endTime = map.get("endTime") == "" ? null : (Long)map.get("endTime");

        List<String> dateList = new ArrayList<String>();// 日期集合
        List<Integer> countList = new ArrayList<Integer>();// 新增会员数量集合
        List<Member> memList = new ArrayList<Member>();// 新增会员集合
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null; // 开始日期
        Date end = null;// 结束日期
        if (startTime == null)
        {
            // 如果开始时间和结束时间为null，查询出近七天的，首选查询出七天的具体起止日期
            // 得到今天的日期
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 23);
            calendar.set(Calendar.SECOND, 59);
            end = calendar.getTime();
            // 得到七天前的日期
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DATE, -6);
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 1);
            start = calendar1.getTime();
        }
        else
        {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTimeInMillis(startTime);
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 1);
            start = calendar1.getTime();

            Calendar calendar2 = Calendar.getInstance(); 
            calendar2.setTimeInMillis(endTime);
            calendar2.set(Calendar.HOUR_OF_DAY, 23);
            calendar2.set(Calendar.MINUTE, 23);
            calendar2.set(Calendar.SECOND, 59);
            end = calendar2.getTime();
        }
        // 从数据库中获取七天的新增会员数据，以便后面获取个数
        memList = mongoTemplate.find(
            new Query(Criteria.where("merchantId").is(merchantId).and("status").is(Member.STATUS_NORMAL).andOperator(
                Criteria.where("insertTime").gte(start), Criteria.where("insertTime").lte(end))), Member.class);
        // 设置时间区间参数
        BasicDBObject query = new BasicDBObject();
        query.put(QueryOperators.GTE, start);
        query.put(QueryOperators.LTE, end);
     // 获取该时间段内连接WiFi的人数,使用mongo的distinct
        CommandResult result = mongoTemplate.executeCommand("{distinct:'memberLoginInfo', key:'pcmac'," +
        		"query:{merchantId:"+merchantId+",loginTime:"+query+"}}");
        BasicDBList memberLoginList = (BasicDBList)result.get("values"); // 结果
        
        //获取该商户的名称
        MerchantInfo mer = mongoTemplate.findOne(new Query(Criteria.where("merchantId").is(merchantId)
            .and("status").is(MerchantInfo.STATUS_NORMAL)), MerchantInfo.class);
        DateFormat format = new SimpleDateFormat("MM-dd");
        for (int i = 6; i >= 0; i-- )
        {
            Date startStr = TimeUtil.getTimestampSeveralDaysAgo(end, i);
            dateList.add(format.format(startStr));

            int j = 0;
            for (Member mem : memList)
            {
                String insertDate = TimeUtil.getDateToString(mem.getInsertTime());
                String startDate = TimeUtil.getDateToString(startStr);
                if (insertDate.equals(startDate))
                {
                    j++ ;
                }
            }
            countList.add(j);
        }

        Integer todayCount = countList.get(countList.size()-1);//标记今日新增会员
        json.setFlag(true).setMessage("查询新增会员成功");
        json.putData("dateList", dateList);
        json.putData("countList", countList);
        json.putData("totalCount", memList.size());//指定日期内新增的总会员数
        json.putData("startTime",sdf.format(start));//统计周期
        json.putData("endTime",sdf.format(end));//统计周期
        json.putData("todayCount",todayCount);//今日新增会员
        json.putData("loginCount",memberLoginList==null?0:memberLoginList.size());//会员登录人数
        json.putData("merchantName",mer==null?"":mer.getMerchantName());//商户名称
        return json;
    }

    @Override
    public JsonResult exportMemberInfo(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        JsonResult js = new JsonResult();
        Integer merchantId = (Integer)map.get("merchantId");
        Long startTime = map.get("startTime") == "" ? null : (Long)map.get("startTime");
        Long endTime = map.get("endTime") == "" ? null : (Long)map.get("endTime");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(startTime);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 1);
        Date start = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(endTime);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 23);
        calendar2.set(Calendar.SECOND, 59);
        Date end = calendar2.getTime();

        // 从数据库中获取日期区间内的数据
        List<Member> memList = mongoTemplate.find(
            new Query(Criteria.where("merchantId").is(merchantId).and("status").is(Member.STATUS_NORMAL).andOperator(
                Criteria.where("insertTime").gte(start), Criteria.where("insertTime").lte(end))), Member.class);

        HSSFWorkbook wb = new HSSFWorkbook();
        // 建立一个文件夹存放生成的Excel文件
        String filePath = new File(request.getSession().getServletContext().getRealPath("")).getParentFile().getParentFile()
                          + File.separator + "logs" + File.separator
                          // + merchantId.toString()
                          + "WiFi新增会员.xls";

        File file = new File(filePath);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }

        // 设置一个title字体
        Font font1 = wb.createFont();
        font1.setFontHeightInPoints((short)18);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗

        // 设置明细title字体
        Font font2 = wb.createFont();
        font2.setFontHeightInPoints((short)16);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗

        // 设置小title字体
        Font font3 = wb.createFont();
        font3.setFontHeightInPoints((short)14);
        font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗

        // 设置明细字体
        Font font4 = wb.createFont();
        font4.setFontHeightInPoints((short)14);

        // 设置Exceltitle的样式
        CellStyle cellStyleTitle1 = wb.createCellStyle();
        cellStyleTitle1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中显示
        cellStyleTitle1.setFont(font1);

        // 设置titile明细单元格的样式
        CellStyle cellStyleTitle2 = wb.createCellStyle();
        // HSSFPalette palette1 = wb.getCustomPalette();
        // palette1.setColorAtIndex(HSSFColor.GREY_25_PERCENT.index, (byte) 91, (byte) 85, (byte)
        // 87);
        // cellStyleTitle2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        // cellStyleTitle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
        cellStyleTitle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中显示
        cellStyleTitle2.setFont(font2);

        // 设置小title的样式
        CellStyle cellStyleTitle3 = wb.createCellStyle();
        cellStyleTitle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中显示
        cellStyleTitle3.setFont(font3);

        // 设置明细的样式
        CellStyle cellStyleTitle4 = wb.createCellStyle();
        cellStyleTitle4.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中显示
        cellStyleTitle4.setFont(font4);

        FileOutputStream out = new FileOutputStream(file);

        Sheet s1 = wb.createSheet();
        s1.setDefaultColumnWidth(1 * 25);
        s1.setDefaultRowHeight((short)(30 * 20));
        wb.setSheetName(0, "新增WiFi会员统计");
        Row r = null;
        Cell c = null;
        r = s1.createRow(0);// 创建一行，显示大标题
        r.setHeight((short)600);// 创建行的高度
        c = r.createCell(0);
        c.setCellStyle(cellStyleTitle1);// 设置单元格样式
        c.setCellValue("新增WiFi会员资料");

        s1.addMergedRegion(new CellRangeAddress(// 向右合并格
            0, // first row (0-based)
            0, // last row (0-based)
            0, // first column (0-based)
            1 // last column (0-based)
        ));

        r = s1.createRow(1);// 创建第一行，显示统计时间
        r.setHeight((short)400);// 创建行的高度
        c = r.createCell(0);
        c.setCellStyle(cellStyleTitle2);// 设置单元格样式
        c.setCellValue("统计时间：" + sdf.format(start) + "到" + sdf.format(end));

        s1.addMergedRegion(new CellRangeAddress(// 向右合并两格
            1, // first row (0-based)
            1, // last row (0-based)
            0, // first column (0-based)
            1 // last column (0-based)
        ));

        r = s1.createRow(2);// 创建一行，显示Excel的title
        c = r.createCell(0);
        c.setCellStyle(cellStyleTitle3);// 设置单元格样式
        c.setCellValue("手机号");
        c = r.createCell(1);
        c.setCellStyle(cellStyleTitle3);// 设置单元格样式
        c.setCellValue("添加时间");

        if (memList != null && memList.size() > 0)
        {
            for (int i = 0; i < memList.size(); i++ )
            {
                r = s1.createRow(i + 3);
                c = r.createCell(0);
                c.setCellStyle(cellStyleTitle4);// 设置单元格样式
                c.setCellValue(memList.get(i).getPhoneNumber() == null ? "" : memList.get(i).getPhoneNumber());
                c = r.createCell(1);
                c.setCellStyle(cellStyleTitle4);// 设置单元格样式
                c.setCellValue(memList.get(i).getInsertTime() == null ? "" : sdf.format(memList.get(i).getInsertTime()));
            }
        }
        else
        {
            js.setFlag(false).setMessage("您" + sdf.format(start) + "到" + sdf.format(end) + "区间内还没有新增会员");
        }
        wb.write(out);
        out.close();
        out = null;
        FileInputStream inStream = new FileInputStream(file);

        js = UploadFileUtil.upLoadFiles(inStream, filePath, file.length(), picDfsServer, dfsTrackerHttpPort, picDfsPort);
        return js;
    }

    @Override
    public void saveMember(Member member)
    {
        mongoTemplate.insert(member);
    }

    @Override
    public Map<String, Object> getBossMobile(Map<String, Object> map)
        throws Exception
    {
        Integer merchantId = (Integer)map.get("merchantId");
        MerchantInfo mer = mongoTemplate.findOne(new Query(
            Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            MerchantInfo.class);

        map.clear();
        if (mer != null)
        {
            // 如果设置了会员统计发送短信的手机号
            if (mer.getSendMemStatisMobile() != null && !mer.getSendMemStatisMobile().equals(""))
            {
                map.put("sendMemStatic", mer.getSendMemStatisMobile());
                map.put("isSendMemStatic", mer.getIsSendMemStatic());
            }
            else
            {
                map.put("sendMemStatic", mer.getBossMobile() == null ? "" : mer.getBossMobile());
                map.put("isSendMemStatic", false);
            }
        }
        return map;
    }

    @Override
    public void setMemberSendMobile(Map<String, Object> map)
        throws Exception
    {
        Integer merchantId = (Integer)map.get("merchantId");
        Boolean isSendMemStatic = (Boolean)map.get("isSendMemStatic");
        String sendMobile = map.get("sendMobile").toString();
        mongoTemplate.findAndModify(
            new Query(Criteria.where("merchantId").is(merchantId).and("status").is(MerchantInfo.STATUS_NORMAL)),
            Update.update("isSendMemStatic", isSendMemStatic).set("sendMemStatisMobile", sendMobile),
            MerchantInfo.class);
    }

    @Override
    public Member getMemberByMobileAndBrandId(Integer brandId, String mobile)
        throws Exception
    {
        return mongoTemplate.findOne(
            new Query(Criteria.where("brandId").is(brandId).and("status").is(MerchantInfo.STATUS_NORMAL).and(
                "phoneNumber").is(mobile)), Member.class);
    }

    @Override
    public void updateMember(Member member, Member updateMember)
    {
        Query query = new Query(Criteria.where("_id").is(member.getId()));
        Update update = Update.update("mac", updateMember.getMac()).set("lastUpdateTime",
            updateMember.getLastUpdateTime());
        mongoTemplate.findAndModify(query, update, Member.class);
    }

    @Override
    public void saveMemberLoginInfo(MemberLoginInfo memberLoginInfo)
    {
        mongoTemplate.insert(memberLoginInfo);
    }
}

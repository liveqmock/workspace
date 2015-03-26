package com.yazuo.erp.superReport.controller;

import com.yazuo.erp.superReport.service.SuperReportService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/superReport")
public class SuperReportController {
    @Resource
    private SuperReportService superReportService;
    private Map<String, String> keyToTitle;

    @RequestMapping(value = "index", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("superReport/index");
        mav.addObject("userData", this.superReportService.getUserInfo());
        mav.addObject("brandData", this.superReportService.getBrandInfo());
        mav.addObject("userCountData", this.superReportService.getUserCountInfo());
        mav.addObject("userTimeData", this.superReportService.getUserTimesInfo());
        return mav;
    }

    @RequestMapping(value = "getUserInfo", method = {RequestMethod.GET})
    @ResponseBody
    public JSONObject getUserInfo(boolean isNew, Date date) {
        JSONObject resultJson = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();

        JSONArray jsonObject = this.superReportService.getUserInfoByDate(isNew, date);
        Iterator<JSONObject> iterator = jsonObject.iterator();

        int total = 0;
        while (iterator.hasNext()) {
            total += iterator.next().getInt("userCount");
        }

        iterator = jsonObject.iterator();
        JSONArray jsonArray = new JSONArray();
        JSONArray tmpJsonObject = null;
        while (iterator.hasNext()) {
            JSONObject tmp = iterator.next();
            tmpJsonObject = new JSONArray();
            tmpJsonObject.add(tmp.get("role"));
            tmpJsonObject.add(tmp.getDouble("userCount"));
            jsonArray.add(tmpJsonObject);
        }


        JSONObject seriesJsonObject = new JSONObject();
        seriesJsonObject.put("type", "pie");
        seriesJsonObject.put("name", "用户占比");
        seriesJsonObject.put("data", jsonArray);
        JSONArray a = new JSONArray();
        a.add(seriesJsonObject);
        dataJsonObject.put("series", a);
        resultJson.put("data", dataJsonObject);
        resultJson.put("success", true);
        return resultJson;
    }

    @RequestMapping(value = "getBrandInfo", method = {RequestMethod.GET})
    @ResponseBody
    public JSONObject getBrandInfo(boolean isNew, Date date) {
        JSONObject resultJson = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();

        JSONObject jsonObject = this.superReportService.getBrandInfoByDate(isNew, date);
        String[] names = {"品牌", "分公司", "门店"};

        List<String> dateList = new ArrayList<String>();
        JSONArray array = (JSONArray) jsonObject.get(names[0]);
        Iterator<JSONObject> objectIterator = array.iterator();
        while (objectIterator.hasNext()) {
            dateList.add(objectIterator.next().getString("time"));
        }

        JSONArray jsonArray = new JSONArray();
        Iterator<String> dateIterator = dateList.iterator();
        while (dateIterator.hasNext()) {
            String dateStr = dateIterator.next();
            List<Integer> nums = new ArrayList<Integer>();
            JSONObject listItem = new JSONObject();
            for (String name : names) {
                for (Object item : jsonObject.getJSONArray(name)) {
                    JSONObject ite = (JSONObject) item;
                    if (ite.getString("time").equals(dateStr)) {
                        nums.add(ite.getInt("count"));
                    }
                }
            }
            listItem.put("name", dateStr);
            listItem.put("data", nums);
            jsonArray.add(listItem);
        }
        dataJsonObject.put("series", jsonArray);
        resultJson.put("data", dataJsonObject);
        resultJson.put("success", true);
        return resultJson;
    }

    @RequestMapping(value = "getUserCountByFunc", method = {RequestMethod.GET})
    @ResponseBody
    public JSONObject getUserCountByFunc(String funcName, Date date) {
        JSONObject resultJson = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();
        boolean isAll = funcName.equals("1");
        if (isAll) {
            funcName = null;
        }
        JSONArray jsonObject = this.superReportService.getUserCountByFunc(funcName, date);
        JSONArray jsonArray = new JSONArray();
        Iterator<JSONObject> iterator = jsonObject.iterator();
        JSONArray tmpJsonArray = null;
        while (iterator.hasNext()) {
            JSONObject tmp = iterator.next();

            tmpJsonArray = new JSONArray();
            if (isAll) {
                tmpJsonArray.add(this.toTitle(tmp.getString("name")));
            } else {
                tmpJsonArray.add(tmp.getString("name"));
            }
            tmpJsonArray.add(tmp.get("count"));

            jsonArray.add(tmpJsonArray);
        }

        JSONObject seriesJsonObject = new JSONObject();
        seriesJsonObject.put("type", "pie");
        seriesJsonObject.put("name", "用户占比");
        seriesJsonObject.put("data", jsonArray);
        JSONArray a = new JSONArray();
        a.add(seriesJsonObject);
        dataJsonObject.put("series", a);
        resultJson.put("data", dataJsonObject);
        resultJson.put("success", true);
        return resultJson;
    }

    @RequestMapping(value = "getUserTimesByFunc", method = {RequestMethod.GET})
    @ResponseBody
    public JSONObject getUserTimesByFunc(String funcName, Date date) {
        JSONObject resultJson = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();
        boolean isAll = funcName.equals("1");
        if (isAll) {
            funcName = null;
        }

        JSONArray jsonObject = this.superReportService.getUserCountByFunc(funcName, date);
        JSONArray jsonArray = new JSONArray();
        Iterator<JSONObject> iterator = jsonObject.iterator();
        JSONArray tmpJsonArray = null;
        while (iterator.hasNext()) {
            JSONObject tmp = iterator.next();

            tmpJsonArray = new JSONArray();
            if (isAll) {
                tmpJsonArray.add(this.toTitle(tmp.getString("name")));
            } else {
                tmpJsonArray.add(tmp.getString("name"));
            }
            tmpJsonArray.add(tmp.get("count"));

            jsonArray.add(tmpJsonArray);
        }

        JSONObject seriesJsonObject = new JSONObject();
        seriesJsonObject.put("type", "pie");
        seriesJsonObject.put("name", "用户占比");
        seriesJsonObject.put("data", jsonArray);
        JSONArray a = new JSONArray();
        a.add(seriesJsonObject);
        dataJsonObject.put("series", a);
        resultJson.put("data", dataJsonObject);
        resultJson.put("success", true);
        return resultJson;
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    private String toTitle(String key) {
        return this.getTitle().get(key);
    }

    private Map<String, String> getTitle() {
        if (this.keyToTitle == null) {
            Map<String, String> map = new HashMap();
            map.put("SEND_REPORT", "发送日报");
            map.put("VIEW_REPORT", "查看日报");
            map.put("VIEW_STATISTICS", "数据统计");
            map.put("SEND_COMMEND", "发送评论");
            map.put("VIEW_COMMEND", "查看评论");
            map.put("VIEW_NOTICE", "查看通知");
            map.put("VIEW_MARK", "标注");
            this.keyToTitle = map;
        }
        return this.keyToTitle;
    }
}
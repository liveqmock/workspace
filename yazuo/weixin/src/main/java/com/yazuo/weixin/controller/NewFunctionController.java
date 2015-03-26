/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.Story;

/**
 * 门店和菜品功能
 * @author kyy
 * @date 2014-7-8 上午10:14:30
 */
@Controller
@RequestMapping("/weixin/newFun")
public class NewFunctionController {
	//日志
	Logger log = Logger.getLogger(this.getClass());
	private static final Log coordinatesOrderLog = LogFactory.getLog("coordinatesOrderLog");//记录经纬度排序
	@Resource
	private WeixinManageService weixinManageService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;
	
	/**门店所在行政区划*/
	public Map<Integer, Object> AREA_STATUS = new HashMap<Integer, Object>();
	
	@RequestMapping(value = "dishes", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView dishes(@RequestParam(value = "brandId") Integer brandId, @RequestParam(value = "weixinId") String weixinId
			,@RequestParam(value = "brandName", required=false) String brandName) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if(business == null){
			business = new BusinessVO();
			business.setTitle(brandName);
			business.setBrandId(brandId);
		}
		String key = String.valueOf(Constant.KEY);
		Object obj = weixinManageService.getDishesList(brandId, weixinId, key);
		// 排序，将新菜放上面
//		Object temp = sortDishesList(obj);
		
//		ModelAndView mav = new ModelAndView("dishes/dishes");
		ModelAndView mav = new ModelAndView("/member/dishes_new");
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject("merchantId", brandId);
		mav.addObject("dishesList", obj);
//		mav.addObject("hasImage", business!=null ? weixinPhonePageService.hasImage(brandId, business.getSmallimageName()):"");
		return mav;
	}

	@RequestMapping(value = "detailDishes", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ModelAndView detailDishes(@RequestParam(value = "dishesId") Integer dishesId, @RequestParam(value = "brandId") Integer merchantId, String weixinId) {
		String input = "{id:" + dishesId + ",merchantId:" + merchantId + " }";
		String key = String.valueOf(Constant.KEY);
		// 取详细信息
		JSONObject jsObj = weixinManageService.getDishesDetail(input, key);
		// 取点赞状态
		List<Map<String, Object>> list = weixinManageService.getUserPariseRecord(merchantId, weixinId, dishesId);
		log.info("取点赞状态：list=" + list);
		jsObj.put("pariseStatus", 0);
		if (dishesId != null && dishesId != 0 && list != null && list.size() > 0) {
			jsObj.put("pariseStatus", 1);
		}
		ModelAndView mav = new ModelAndView("/member/dishes_detail_new");
		mav.addObject("dishesDetail", jsObj);
		return mav;
//		return jsObj.toString();
	}

	@RequestMapping(value = "dishesParise", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object dishesParise(Integer merchantId, Integer dishesId, String weixinId) {
		// 更新dishes表中赞的数量
		String input = "{dishesId:" + dishesId + ",merchantId:" + merchantId + " }";
		String key = String.valueOf(Constant.KEY);
		boolean updatePariseCount = weixinManageService.updateDishesParise(input, key);
		log.info("点赞crm是否更新成功：" + updatePariseCount);
		// 插入流水记录
		if (updatePariseCount) { // 点赞数更新成功后再插入流水
			boolean parise = weixinManageService.addPariseRecord(merchantId, dishesId, weixinId);
			log.info("插入流水记录" + (parise ? "成功" : "失败") + "!");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", updatePariseCount);
		return map;
	}
	
	@RequestMapping(value = "subbranch", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView subbranch(@RequestParam(value="brandId") Integer merchantId, @RequestParam(value="weixinId", required=false)String weixinId
			,@RequestParam(value = "brandName", required = false) String brandName) {
//		ModelAndView mv = new ModelAndView("merchant/w_subbranch");
		ModelAndView mv = new ModelAndView("member/w_subbranch_new");
		BusinessVO business = weixinManageService.getBusinessByBrandId(merchantId);
		if(business == null){
			business = new BusinessVO();
			business.setTitle(brandName);
			business.setBrandId(merchantId);
		}
		mv.addObject("business", business);
		mv.addObject("weixinId", weixinId);
		mv.addObject("areaName", "选择地区");
//		mv.addObject("hasImage", business !=null ? weixinPhonePageService.hasImage(merchantId, business.getSmallimageName()) : "");
		return mv;
	}
	
	// 全部和按区域排序查出门店信息
	@RequestMapping(value = "subbranchSortArea", method = { RequestMethod.POST, RequestMethod.GET })
	public void subbranchSortArea(@RequestParam(value = "brandId") Integer merchantId,
			String weixinId, Integer pageIndex,
			@RequestParam(value="areaId", required=false) Integer areaId, 
			@RequestParam(value="areaName", required=false)String areaName
			, @RequestParam(value = "merchantName", required=false) String merchantName,
			@RequestParam(value = "source", required=false) String source,
			@RequestParam(value = "brandName", required = false) String brandName,
			HttpServletResponse response) {
		double pageSize = 20;
		if (pageIndex==null) {
			pageIndex = 1;
		}
		coordinatesOrderLog.info("定位失败！"+DateUtil.getDate()+"area_sort" +"@merchantId:"+merchantId+",areaId:"+areaId+",orderName:wx_order");
		//返回的基本信息
		BusinessVO business = weixinManageService.getBusinessByBrandId(merchantId);
		if(business == null){
			business = new BusinessVO();
			business.setTitle(brandName);
			business.setBrandId(merchantId);
		}
		// 排序方式一
		if (areaId==null) {
			areaId = 0;
		}
		JSONObject jsonObject = weixinManageService.getSubbrachByNewOrder(merchantId, pageIndex, 20, areaId, source, merchantName);
		JSONArray jsonArray = jsonObject !=null ? jsonObject.getJSONArray("rows") : new JSONArray(); // 列表数据
		// 计算总页数
		double total = jsonObject!=null ? jsonObject.getDouble("total") : 0; // 总条数
		double result = total/pageSize;
		log.info("总数量：" + total);
		int count = 0;
		if (result > (int)result) {
			count = (int)result + 1;
		} else {
			count = (int)result;
		}
		
		Map<String, Object> resultObj = new HashMap<String, Object>();
		resultObj.put("merchantList", jsonArray);
		resultObj.put("totalPageCount" , count);
		resultObj.put("page", jsonObject!=null ? jsonObject.getInt("page") : 0);
		resultObj.put("areaName", "选择地区");
		resultObj.put("areaId", areaId);
		 try {
			response.getWriter().print(JSONObject.fromObject(resultObj).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 分页，点下一页
	@RequestMapping(value = "loadMoreSubbranch", method = { RequestMethod.POST, RequestMethod.GET })
	public void subbranchList(@RequestParam(value = "brandId") Integer merchantId, Integer pageIndex, @RequestParam(value="areaId", required=false) 
		Integer areaId,@RequestParam(value="latitude", required=false) String latitude,@RequestParam(value="province", required=false) String province,@RequestParam(value="longitude", required=false)String longitude
		,@RequestParam(value = "merchantName", required=false) String merchantName,
		@RequestParam(value = "source", required=false) String source,HttpServletResponse response) {
		JSONObject resultObj = new JSONObject();
		double pageSize = 20;
		// 排序方式一
		if (areaId==null) {
			areaId = 0;
		}
		JSONObject jsonObject = new JSONObject();
		if (StringUtil.isNullOrEmpty(latitude) && StringUtil.isNullOrEmpty(longitude) && StringUtil.isNullOrEmpty(province)) { // 按行政区划排序
			jsonObject = weixinManageService.getSubbrachByNewOrder(merchantId, pageIndex, (int)pageSize, areaId, source, merchantName);
		} else { //经纬度排序
			province = province.substring(0, province.length());
			jsonObject = weixinManageService.getSubbrachByCoordinatesOrder(merchantId, longitude, latitude, province, pageIndex, (int)pageSize);
		}
		JSONArray jsonArray = jsonObject.getJSONArray("rows"); // 列表数据
		resultObj.put("merchantList", jsonArray);
		// 计算总页数
		double total = jsonObject!=null ? jsonObject.getDouble("total") : 0; // 总条数
		log.info("总条数:" +total);
		double result = total/pageSize;
		int count = 0;
		if (result > (int)result) {
			count = (int)result + 1;
		} else {
			count = (int)result;
		}
		resultObj.put("totalPageCount" , count);
		resultObj.put("page", jsonObject!=null ? jsonObject.getInt("page") : 0);
		resultObj.put("areaId" , areaId);
		 try {
			response.getWriter().print(JSONObject.fromObject(resultObj).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 根据经纬度按距离搜索附近的门店并排序(未完成)
	@RequestMapping(value = "subbranchSort", method = { RequestMethod.POST, RequestMethod.GET })
	public void subbranchSort(@RequestParam(value = "brandId") Integer merchantId, String latitude, String longitude, @RequestParam(value="province") 
		String province, Integer pageIndex, HttpServletResponse response) {
		Integer pageSize = 20;
		if (pageIndex==null) {
			pageIndex = 1;
		}
		coordinatesOrderLog.info("定位成功!"+DateUtil.getDate() + "coordinate_sort@" + "merchantId:" + merchantId + ",longitude:" + longitude + ",laitude:"+latitude+",provinceName:"+province+";");
		JSONObject obj = weixinManageService.getSubbrachByCoordinatesOrder(merchantId, longitude, latitude, province, pageIndex, pageSize);
		double total = obj !=null ? obj.getDouble("total") : 0;
		log.info("总条数:" + total);
		double size = Double.parseDouble(pageSize.toString());
		double totalPage = 0.0;
		if (total/size > (int)total/size) {
			totalPage = (int)total/size + 1;
		} else {
			totalPage = (int)total/size;
		}
		
		Map<String, Object> resultObj = new HashMap<String, Object>();
		JSONArray jsonArray = obj !=null ? obj.getJSONArray("rows") : new JSONArray(); // 列表数据
		resultObj.put("merchantList", jsonArray);
		resultObj.put("totalPageCount" , totalPage);
		resultObj.put("page", obj!=null ? obj.getInt("page") : 0);
		resultObj.put("areaName", "选择地区");
		 try {
			response.getWriter().print(JSONObject.fromObject(resultObj).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "initArea", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object initArea(@RequestParam(value="brandId") Integer merchantId) {
		AREA_STATUS = judgeData(merchantId);
		Map<String, Object> map = (Map<String, Object>)AREA_STATUS.get(merchantId);
		if (map ==null || map.size()==0){
			return null;
		}
		return map.keySet();
	}
	
	@RequestMapping(value = "loadChildArea", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object loadChildArea(Integer pAreaId, @RequestParam(value="brandId", required=false) Integer merchantId) {
		AREA_STATUS = judgeData(merchantId);
		Map<String, Object> map = (Map<String, Object>)AREA_STATUS.get(merchantId);
		if (map !=null && map.size()>0) {
			for (String k : map.keySet()) {
				String [] array = k.split(",");
				if (pAreaId == Integer.parseInt(array[1])) {
					return map.get(k);
				}
			}
		}
		return null;
	}
	
	// 判断静态变量中是否有数据
	private Map<Integer, Object> judgeData(Integer merchantId) {
		Map<Integer, Object> resultMap = new HashMap<Integer, Object>();
		Map<String, Object> areaMap = (Map<String, Object>)AREA_STATUS.get(merchantId); // 某个门店相关区域化数据
		if (areaMap==null || areaMap.size()==0) {
			JSONObject finalResult = new JSONObject();
			Map<String, Object> map = weixinManageService.getAllArea(merchantId);
			for (String k : map.keySet()) {
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get(k);
				if(list !=null && list.size() > 0) { //有二级菜单
					finalResult.accumulate(k+",true", map.get(k));
				} else {
					finalResult.accumulate(k+",false", map.get(k));
				}
			}
			if(finalResult !=null && finalResult.size() > 0) {
				resultMap.put(merchantId, finalResult);
			}
		} else {
			resultMap = AREA_STATUS;
		}
		return resultMap;
	}
	
	// 第一次加载所有门店或查询全部区域的门店按直辖市先排后省的门店
	private JSONArray sortAllMerchant(Object obj, Integer brandId) {
		JSONArray resultJson = new JSONArray();
		JSONArray jsonArray = obj !=null ? (JSONArray)obj : new JSONArray();
		// 判断区域化数据是否已经存在
		AREA_STATUS = judgeData(brandId);
		Map<String, Object> map = (Map<String, Object>)AREA_STATUS.get(brandId);
		if (map!=null && map.size() >0) {
			for (String k : map.keySet()) {
				String [] array = k.split(","); //areaId= array[1]
				for (int i=0; i<jsonArray.size();i++) {
					JSONObject temp = jsonArray.getJSONObject(i);
					if(temp.getString("areaId").equals(array[1])) {
						resultJson.add(jsonArray.get(i));
					}
				}
			}
		}
		jsonArray.removeAll(resultJson);
		resultJson.addAll(jsonArray);
		return resultJson;
	}
	
	// 菜品将新菜排在前面
	private Object sortDishesList(Object obj) {
		Map<String, Object> dishesMap = new HashMap<String, Object>();
		if (obj !=null) {
			Map<String, Object> map = (Map<String, Object>)obj;
			for (String key : map.keySet()) {
				List<Map<String, Object>> list = (List<Map<String, Object>>)map.get(key); // 每个分类下的菜品
				   Collections.sort(list, new Comparator<Map<String, Object>>() {
			            @Override
			            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			            	int i1 = Integer.parseInt(String.valueOf(o1.get("id")));
			            	int i2 = Integer.parseInt(String.valueOf(o2.get("id")));
			                return i1 - i2 ;
			            }
			        });
				JSONArray array = new JSONArray();
			   for (Map<String, Object> m : list) {
				   String newDishes = String.valueOf(m.get("dishesIsNew"));
				   int isNew = StringUtils.isNotEmpty(newDishes) ? Integer.parseInt(newDishes) : 0;
				  if(isNew == 1) {
					  array.add(m);
				  }   
			   }
			   list.removeAll(array);
			   array.addAll(list);
			   dishesMap.put(key, array);
			}
		}
		return dishesMap;
	}
	
	@RequestMapping(value = "showStory", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView showStory(Integer brandId, String weixinId, @RequestParam(value="brandName",required=false)String brandName) {
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		if (business == null) {
			business = new BusinessVO();
			business.setBrandId(brandId);
			business.setTitle(StringUtil.isNullOrEmpty(brandName)?"":brandName);
		}
		Story story = weixinManageService.getByBrandId(brandId);
		ModelAndView mav = new ModelAndView("w_new_story");
		mav.addObject("business", business);
		mav.addObject("weixinId", weixinId);
		mav.addObject("merchantId", brandId);
		mav.addObject("story", story);
//		mav.addObject("hasImage", business!=null ? weixinPhonePageService.hasImage(brandId, business.getSmallimageName()):"");
		return mav;
	}
}

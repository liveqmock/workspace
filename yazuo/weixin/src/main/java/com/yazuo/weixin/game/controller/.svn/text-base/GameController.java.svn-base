/** 
* @author kyy
* @version 创建时间：2015-3-4 上午10:04:42 
* 多泡游戏相关业务
*/ 
package com.yazuo.weixin.game.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.weixin.game.service.GameService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.MerchantVO;

@Controller
@RequestMapping("/game/{brandId}")
public class GameController {
	
	@Resource
	private GameService gameService;
	@Resource
	private WeixinManageService weixinManageService;
	
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	
	@RequestMapping(value = "getCoordinatesInfo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object initAreaInfo(@PathVariable(value = "brandId") Integer brandId, String weixinId, @RequestParam(value="longitude", required=false)String longitude,
			@RequestParam(value="latitude", required=false)String latitude) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			JSONObject json = weixinManageService.getSubbrachByCoordinatesOrder(brandId, longitude, latitude, "", 0, 10);
			if (json !=null) {
				JSONArray array = json.getJSONArray("rows");
				if (array !=null && array.size()>0){
					
					JSONObject rjson = (JSONObject)array.get(0);
					resultMap.put("merchant", rjson);
					// 查询选择门店下的游戏
					Integer merchantId = rjson.getInt("merchantId");
				}
			}
		return resultMap;
	}
	
	@RequestMapping(value = "loadSign", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView loadSign(@PathVariable(value = "brandId") Integer brandId, String weixinId, HttpServletRequest request) {
		String url = serverIp + request.getContextPath() + "/game/"+brandId+"/loadSign.do.do?weixinId="+ weixinId;
		Map<String, Object> resultMap = gameService.queryWeixinSign(brandId, url);
		ModelAndView mav = new ModelAndView("/game/game_info");
		mav.addObject("resultMap", resultMap);
		mav.addObject("brandId", brandId);
		mav.addObject("weixinId", weixinId);
		return mav;
	}
}

/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-11-29
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.demo.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yazuo.erp.demo.service.DemoService;
import com.yazuo.erp.demo.vo.DemoVO;

/**
 * @ClassName: DemoController
 * @Description: demo管理
 * 
 */  
@Controller
@RequestMapping("/weixin/demo")
public class DemoController {

	@Resource
	private DemoService demoService;

	private static final Log LOG = LogFactory.getLog("erp");

	/**
	 * demo列表
	 */
	@RequestMapping(value = "listDemo", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public ModelAndView listDemo(
			@RequestParam(value = "brandId", required = false) Integer brandId,
			HttpServletResponse response, HttpServletRequest request) {
		List<DemoVO> demos = null;

		try {
			demos = demoService.getDemoByBrandId(brandId);

		} catch (Exception e) {
			LOG.error("demo列表错误", e);
		}
		ModelAndView mav = new ModelAndView("demo/demo");
		mav.addObject("demos", demos);
		return mav;
	}

	/**
	 * demo保存
	 */
	@RequestMapping(value = "saveDemo", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public void saveDemo(HttpServletResponse response,
			HttpServletRequest request, DemoVO cd) {
		
		try {
			// if (demoService.saveDemo(cd)) {
			// response.getWriter().print("1");
			// }
		} catch (Exception e) {
			LOG.error("demo添加错误", e);
		}
	}

}
package com.yazuo.weixin.timer;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yazuo.weixin.dao.WeixinPayDao;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.vo.WxPayDeliverStateVo;

/**
* @ClassName TaskDeliverService
* @Description  发货定时任务
* @author sundongfeng@yazuo.com
* @date 2014-6-23 上午9:38:52
* @version 1.0
*/
@Component("TaskDeliverService")
public class TaskDeliverService {
	private static final Log log = LogFactory.getLog("wxpay");
	@Autowired
	private WeixinPayDao weixinPayDao;
	@Value("#{payPropertiesReader['xld.appId']}")
	private String appid;
	@Value("#{payPropertiesReader['xld.appKey']}")
	private String appkey;
	@Value("#{payPropertiesReader['xld.appSecret']}")
	private String appSecret;
	/**
	 * 定时任务
	 */
//	@Scheduled(cron="0 0 6,7 * * ?")
	public void deliver()
	{
		log.info("更新发货状态定时任务开始");
		String token="";
		try {
			token = CommonUtil.getToken(appid,appSecret);//获取access_token
			log.info("token:"+token);
			List<WxPayDeliverStateVo> list = weixinPayDao.queryDeliver("1119");
			if(list!=null &&list.size()>0){
				log.info("发货数量:"+list.size());
				int successCounter = 0;
				int errorCounter = 0;
				for(WxPayDeliverStateVo vo:list){
					String rs = CommonUtil.deliverState(token,vo,appkey);//立即发货
					String errmsg = JSONObject.fromObject(rs).getString("errmsg");
					if("ok".equals(errmsg)){
						int a = weixinPayDao.updateDeliverState(vo.getOut_trade_no());
						log.info("更新："+(a>0?true:false)+"，订单号:"+vo.getOut_trade_no());
						successCounter++;
					}else{
						errorCounter++;
					}
				}
				log.info("更新发货状态成功个数："+successCounter+"，失败个数:"+errorCounter);
			}
			log.info("更新发货状态定时任务结束");
		} catch (Exception e) {
			log.error("更新发货happen error.",e);
		}
	} 
}

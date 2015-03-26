package com.yazuo.weixin.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jms.JMSException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.BusinessManagerDao;
import com.yazuo.weixin.dao.EventRecordDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.dao.SubbranchDao;
import com.yazuo.weixin.service.AdministratorInterfaceService;
import com.yazuo.weixin.util.MessageSender;
import com.yazuo.weixin.vo.BusinessManagerVO;
import com.yazuo.weixin.vo.EventRecordVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.SummaryDetail;

/**
 * 雅座CRM微信版的service本项目没用到这个类。
 * 
 * @author five
 * 
 */
@Service
public class AdministratorInterfaceServiceImpl implements
		AdministratorInterfaceService {
	@Value("#{propertiesReader['reportAddress']}")
	private String reportAddress;
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;

	@Autowired
	private SubbranchDao subbranchDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private EventRecordDao eventRecordDao;
	@Autowired
	private BusinessManagerDao businessManagerDao;
	@Autowired
	private BusinessDao businessDao;

	private final String REPLYINFO = "\n\n回复1:查询昨日概况\n回复2:查询本月概况\n回复3:查询本季度概况\n回复6位数字:查任意月\n       如：201305\n回复8位数字:查任意日\n       如：20130501";
	Logger log = Logger.getLogger(this.getClass());

	@Override
	public Message message(Message messageIn, String path) {
		// 文字消息处理
		if (messageIn.getMsgType().equals(Message.MSG_TYPE_TEXT)) {
			return executeText(messageIn, path);
		}
		// 事件消息处理 关注事件需要放到这处理
		else if (messageIn.getMsgType().equals(Message.MSG_TYPE_EVENT)) {
			return executeEvent(messageIn);
		}
		// 其他消息处理
		else
			return executeother(messageIn);
	}

	Message executeText(Message messageIn, String path) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setFuncFlag("0");

		Calendar calendar = Calendar.getInstance();

		// 短信验证
		if (messageIn.getContent().trim().length() == 1) {
			BusinessManagerVO businessManager = new BusinessManagerVO();
			businessManager.setWeixinId(messageIn.getFromUserName());
			businessManager = businessManagerDao
					.getBusinessManagerByWeixinId(businessManager);
			// BusinessVO
			// business=businessDao.getBusinessByBrandId(businessManager.getBrandId());
			if (businessManager == null
					|| businessManager.getStatus().intValue() != 2) {
				String info = "还未通过手机验证,请输入11位手机号进行验证";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			if (messageIn.getContent().trim().equals("1")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String yesterday = sdf.format(new Date(System
						.currentTimeMillis() - 24 * 60 * 60 * 1000));

				String info = "昨日概况\n("
						+ yesterday
						+ ")\n"
						+ getSummaryReport(businessManager.getBrandId(),
								yesterday, yesterday, path);
				log.info(info);
				messageOut.setContent(info);
				return messageOut;

			}
			if (messageIn.getContent().trim().equals("2")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String startDate = sdf.format(new Date(System
						.currentTimeMillis())) + "-01";
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				String endDate = sdf.format(new Date(
						System.currentTimeMillis() - 24 * 3600 * 1000));
				if (startDate.endsWith(sdf.format(new Date(System
						.currentTimeMillis())))) {
					String info = "数据暂未生成";
					messageOut.setContent(info);
					log.info(info);
					return messageOut;
				}
				String info = "本月概况\n("
						+ startDate
						+ "-"
						+ endDate
						+ ")\n"
						+ getSummaryReport(businessManager.getBrandId(),
								startDate, endDate, path);
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			if (messageIn.getContent().trim().equals("3")) {
				String startDate = null;
				String endDate = null;
				Calendar now = Calendar.getInstance();
				int month = now.get(Calendar.MONTH) + 1;
				if (month == 1 || month == 2 || month == 3) {
					startDate = now.get(Calendar.YEAR) + "-01-01";
				} else if (month == 4 || month == 5 || month == 6) {
					startDate = now.get(Calendar.YEAR) + "-04-01";
				} else if (month == 7 || month == 8 || month == 9) {
					startDate = now.get(Calendar.YEAR) + "-07-01";
				} else if (month == 10 || month == 11 || month == 12) {
					startDate = now.get(Calendar.YEAR) + "-10-01";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				endDate = sdf.format(new Date(
						System.currentTimeMillis() - 24 * 3600 * 1000));
				if (startDate.endsWith(sdf.format(new Date(System
						.currentTimeMillis())))) {
					String info = "数据暂未生成";
					messageOut.setContent(info);
					log.info(info);
					return messageOut;
				}

				String info = "本季度概况\n("
						+ startDate
						+ "-"
						+ endDate
						+ ")\n"
						+ getSummaryReport(businessManager.getBrandId(),
								startDate, endDate, path);
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}

		} else if (messageIn.getContent().trim().length() == 6
				&& isNum(messageIn.getContent().trim())) {

			BusinessManagerVO businessManager = new BusinessManagerVO();
			businessManager.setWeixinId(messageIn.getFromUserName());
			businessManager = businessManagerDao
					.getBusinessManagerByWeixinId(businessManager);
			if (businessManager == null
					|| businessManager.getStatus().intValue() != 2) {
				String info = "还未通过手机验证,请输入11位手机号进行验证";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}

			String startDate = getMonthBegin(messageIn.getContent().trim());
			String endDate = getMonthEnd(messageIn.getContent().trim());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			// 防止数据不一致
			if (sdf.format(new Date(System.currentTimeMillis())).equals(
					messageIn.getContent().trim())) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				endDate = sdf.format(new Date(
						System.currentTimeMillis() - 24 * 3600 * 1000));
			}

			calendar.add(Calendar.MONTH, 1);
			Date date1 = calendar.getTime();
			Date date2 = null;
			try {
				date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
			} catch (ParseException e) {
				log.error(e);
				e.printStackTrace();
			}
			if (date2.after(date1)) {
				String info = "数据暂未生成";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}

			String info = "指定月份概况\n("
					+ startDate
					+ "-"
					+ endDate
					+ ")\n"
					+ getSummaryReport(businessManager.getBrandId(), startDate,
							endDate, path);
			messageOut.setContent(info);
			log.info(info);
			return messageOut;
		} else if (messageIn.getContent().trim().length() == 8
				&& isNum(messageIn.getContent().trim())) {
			BusinessManagerVO businessManager = new BusinessManagerVO();
			businessManager.setWeixinId(messageIn.getFromUserName());
			businessManager = businessManagerDao
					.getBusinessManagerByWeixinId(businessManager);
			if (businessManager == null
					|| businessManager.getStatus().intValue() != 2) {
				String info = "还未通过手机验证,请输入11位手机号进行验证";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}

			String date = messageIn.getContent().trim().substring(0, 4) + "-"
					+ messageIn.getContent().trim().substring(4, 6) + "-"
					+ messageIn.getContent().trim().substring(6, 8);

			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date date1 = calendar.getTime();
			Date date2 = null;
			try {
				date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			} catch (ParseException e) {
				log.error(e);
				e.printStackTrace();
			}
			if (date2.after(date1)) {
				String info = "数据暂未生成";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			String info = "指定日概况\n("
					+ date
					+ ")\n"
					+ getSummaryReport(businessManager.getBrandId(), date,
							date, path);
			messageOut.setContent(info);
			log.info(info);
			return messageOut;
		} else if (messageIn.getContent().trim().length() == 3
				&& isNum(messageIn.getContent().trim())) {
			BusinessManagerVO businessManager = new BusinessManagerVO();
			businessManager.setWeixinId(messageIn.getFromUserName());
			businessManager = businessManagerDao
					.getBusinessManagerByWeixinId(businessManager);
			if (businessManager == null) {
				String info = "未申请验证";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			if (businessManager.getStatus().intValue() == 2) {
				String info = "已绑定，请勿重复绑定" + REPLYINFO;
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			IdentifyinginfoVO identifyinginfo = memberDao
					.getLastIdentifyinginfoByWeixinId(
							messageIn.getFromUserName(),
							businessManager.getBrandId());
			String info2 = identifying(identifyinginfo, messageIn.getContent());
			if (info2 != null) {
				messageOut.setContent(info2);
				log.info(info2);
				return messageOut;
			}
			businessManager.setLastUpdateTime(new Timestamp(System
					.currentTimeMillis()));
			businessManager.setStatus(2);
			businessManagerDao.updateBusinessManager(businessManager);
			// 哪家店铺
			String info = "尊敬的"
					+ businessDao.getBusinessByBrandId(
							businessManager.getBrandId()).getTitle()
					+ "管理员您好，您的账号已经绑定成功，可进行下述操作" + REPLYINFO;
			messageOut.setContent(info);
			log.info(info);
			return messageOut;
			// 手机号申请短信验证
		} else if (messageIn.getContent().trim().length() == 11
				&& isMobileNO(messageIn.getContent().trim())) {
			BusinessManagerVO businessManager = new BusinessManagerVO();
			businessManager.setPhoneNo(messageIn.getContent().trim());
			businessManager = businessManagerDao
					.getBusinessManagerByPhoneNo(businessManager);
			if (businessManager == null) {
				String info = "手机号未登记，请联系客服";
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			if (businessManager.getStatus().intValue() == 2) {
				String info = "已绑定，请勿重复绑定" + REPLYINFO;
				messageOut.setContent(info);
				log.info(info);
				return messageOut;
			}
			businessManager.setLastUpdateTime(new Timestamp(System
					.currentTimeMillis()));
			businessManager.setWeixinId(messageIn.getFromUserName());
			businessManager.setStatus(1);
			businessManagerDao.updateBusinessManager(businessManager);

			// 判断验证码
			String identifyingCode = sendIdentifyingCode(messageIn.getContent()
					.trim(), businessManager.getBrandId(),
					messageIn.getFromUserName());
			String info = "验证码为" + identifyingCode + "，欢迎使用雅座CRM微信版。";
			log.info(info);
			if (identifyingCode != null) {
				messageOut.setContent("验证码已发放，请输入您短信内的验证码");
			} else {
				messageOut.setContent("验证码已发放失败");
			}
			return messageOut;
		}
		String info = "无匹配项，请按照提示进行操作\n" + REPLYINFO;
		messageOut.setContent(info);
		log.info(info);
		return messageOut;
	}

	boolean isBundling(String weixinId) {
		BusinessManagerVO businessManager = new BusinessManagerVO();
		businessManager.setWeixinId(weixinId);
		businessManager = businessManagerDao
				.getBusinessManagerByWeixinId(businessManager);
		if (businessManager == null
				|| businessManager.getStatus().intValue() != 2) {
			return false;
		}
		return true;
	}

	String sendIdentifyingCode(String phoneNo, Integer brandId, String weixinId) {
		Random r = new Random();
		String identifyingCode = String.valueOf(r.nextInt(999));
		while (identifyingCode.length() != 3) {
			identifyingCode = String.valueOf(r.nextInt(999));
		}
		IdentifyinginfoVO identifyinginfo = new IdentifyinginfoVO();
		identifyinginfo.setIdentifyingCode(identifyingCode);
		identifyinginfo.setIdentifyingTime(new Timestamp(System
				.currentTimeMillis()));
		identifyinginfo.setWeixinId(weixinId);
		identifyinginfo.setPhoneNo(phoneNo);
		identifyinginfo.setBrandId(brandId);
		memberDao.insertIdentifyinginfo(identifyinginfo);

		try {
			MessageSender.sendSMS("雅座微信端验证码为" + identifyingCode, phoneNo,
					smsAddress, smsUsername, brandId);
			return identifyingCode;
		} catch (JMSException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}

	}

	String identifying(IdentifyinginfoVO identifyinginfo, String identifyingCode) {
		if (identifyinginfo == null
				|| identifyinginfo.getIdentifyingCode() == null
				|| identifyinginfo.getIdentifyingCode().equals("")) {

			return "并未申请验证" + REPLYINFO;
		}
		Timestamp timestamp = new Timestamp(
				System.currentTimeMillis() - 1800 * 1000);
		if (!identifyinginfo.getIdentifyingCode().equals(identifyingCode)) {
			return "验证码错误，请重新申请验证" + REPLYINFO;
		}
		if (identifyinginfo.getIdentifyingTime().before(timestamp)) {
			return "验证码过期，请重新申请验证" + REPLYINFO;
		}

		return null;
	}

	Message executeEvent(Message messageIn) {

		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("欢迎关注雅座CRM微信版,请回复手机号绑定商家");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	Message executeother(Message messageIn) {
		Message messageOut = new Message();
		messageOut.setToUserName(messageIn.getFromUserName());
		messageOut.setFromUserName(messageIn.getToUserName());
		messageOut.setCreateTime(messageIn.getCreateTime());
		messageOut.setMsgType(Message.MSG_TYPE_TEXT);
		messageOut.setContent("对不起，暂时这种输入方式");
		messageOut.setFuncFlag("0");
		return messageOut;
	}

	public void eventRecord(EventRecordVO eventRecord) {
		eventRecordDao.insertEventRecord(eventRecord);
	}

	static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	static boolean isNum(String input) {

		Pattern p = Pattern.compile("^[0-9_]+$");
		Matcher m = p.matcher(input);
		return m.matches();
	}

	public String getSummaryReport(Integer brandId, String startDate,
			String endDate, String path) {
		JSONObject jo = null;
		String url = reportAddress + "/summary/103/info/" + brandId.toString()
				+ "/" + startDate + "/" + endDate;
		try {
			jo = getReportByDate(url);
		} catch (Exception e) {
			log.error("调用报表接口失败" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {
			return "访问失败";
		} else {
			log.info("调用报表接口" + jo.toString());
			boolean flag = jo.getBoolean("success");
			if (flag) {
				return getReportStringByJson(jo, startDate, endDate, path,
						brandId);
			} else {
				return "访问失败";
			}
		}

	}

	@Override
	public List<SummaryDetail> summaryDetail(String brandId, String startDate,
			String endDate, Integer detailType) {
		JSONObject jo = getSummaryDetailJson(brandId, startDate, endDate,
				detailType);
		JSONArray ja = jo.getJSONObject("data").getJSONArray("info");
		List<SummaryDetail> summaryDetailList = new ArrayList<SummaryDetail>();
		SummaryDetail summaryDetail = null;
		for (int i = 0; i < ja.size(); i++) {
			summaryDetail = new SummaryDetail();
			summaryDetail.setId(ja.getJSONObject(i).getString("id"));
			summaryDetail.setName(ja.getJSONObject(i).getString("name"));
			summaryDetail.setValue(new BigDecimal(ja.getJSONObject(i)
					.getString("value")).toPlainString());
			summaryDetail.setUnit(detailType);
			summaryDetailList.add(summaryDetail);
		}

		return summaryDetailList;
	}

	public JSONObject getSummaryDetailJson(String brandId, String startDate,
			String endDate, Integer detailType) {
		JSONObject jo = null;
		String url = reportAddress + "/summaryDetail/merchantList/"
				+ detailType.toString() + "/" + brandId + "/" + startDate + "/"
				+ endDate;
		try {
			jo = getReportByDate(url);
		} catch (Exception e) {
			log.error("调用报表接口失败" + e.getMessage());
			e.printStackTrace();
		}
		if (jo == null) {
			return null;
		} else {
			log.info("调用报表接口" + jo.toString());
			boolean flag = jo.getBoolean("success");
			if (flag) {
				return jo;
			} else {
				return null;
			}
		}

	}

	public JSONObject getReportByDate(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();

		log.info("调用报表接口" + url);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			return requestObject;
		} else {
			return null;
		}
	}

	public String getReportStringByJson(JSONObject jo, String startDate,
			String endDate, String path, Integer brandId) {
		String url = serverIp + path + "/weixin/report/summaryDetail.do?";
		String parms = "brandId=" + brandId.toString() + "&startDate="
				+ startDate + "&endDate=" + endDate + "&detailType=";
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("###,###,###.###");
		System.out.println(df.format(new BigDecimal(jo.getJSONObject("data")
				.getJSONObject("info").getString("cash_consume_amount"))));
		sb.append("新增会员：");

		sb.append("<a href=\""
				+ url
				+ parms
				+ "0\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("new_nember")))
				+ "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("新激活卡：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "1\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("new_card"))) + "</a>");
		sb.append("\n");
		sb.append("------------------------\n");
		sb.append("现金消费：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "2\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("cash_consume_amount")))
				+ "</a>");

		sb.append("\n");
		sb.append("\n");
		sb.append("积分消费：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "3\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("integral_consume")))
				+ "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("储值消费：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "4\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("store_consume")))
				+ "</a>");
		sb.append("\n");
		sb.append("------------------------\n");
		sb.append("用券数：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "5\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("coupon_quantity")))
				+ "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("用券金额：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "6\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info")
						.getString("coupon_quantity_amount"))) + "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("营销收益：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "7\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("marketing_income")))
				+ "</a>");
		sb.append("\n");
		sb.append("------------------------\n");
		sb.append("储值充值：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "8\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("storage_recharge")))
				+ "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("储值奖励：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "9\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("store_reward")))
				+ "</a>");
		sb.append("\n");
		sb.append("\n");
		sb.append("新增积分：");
		sb.append("<a href=\""
				+ url
				+ parms
				+ "10\">"
				+ df.format(new BigDecimal(jo.getJSONObject("data")
						.getJSONObject("info").getString("new_integral")))
				+ "</a>");
		return sb.toString();
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthBegin(String strdate) {
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMM").parse(strdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public String getMonthEnd(String strdate) {
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd")
					.parse(getMonthBegin(strdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				log.error("date:" + date);
				ex.printStackTrace();
			}
		}
		return result;
	}

}

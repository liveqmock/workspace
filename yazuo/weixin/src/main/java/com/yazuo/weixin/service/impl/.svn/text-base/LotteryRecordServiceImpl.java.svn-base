/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-24
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.LotteryRecordDao;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.service.LotteryItemCouponService;
import com.yazuo.weixin.service.LotteryRecordService;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.SendHttpRequestUtil;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.AwardVO;
import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * @InterfaceName: LotteryRecordServiceImpl
 * @Description: 处理中奖记录逻辑的实现
 */
@Service("lotteryRecordService")
public class LotteryRecordServiceImpl implements LotteryRecordService {

	private static final Log LOG = LogFactory.getLog("lottery");

	ResourceBundle bundle = ResourceBundle.getBundle("image-config");

	private final String lottery_url = bundle.getString("lottery_url");

	@Resource
	private LotteryRecordDao lotteryRecordDao;

	@Resource
	private LotteryItemCouponService lotteryItemCouponService;
	@Resource
	private WeixinMallMartService weixinMallMartService;

	public int getLotteryRecord(String phoneNo, int brandId,
			String lottery_rule_id, String begintime, String endtime, int lotteryRecordType)
			throws Exception {
		return lotteryRecordDao.getLotteryRecord(phoneNo, brandId,
				lottery_rule_id, begintime, endtime, lotteryRecordType);
	}

	public int insertLotteryRecord(MemberAwardVO member, AwardVO award,
			boolean isAward) {

		int winflag = 5;// 5没中奖4：中奖了

		if (isAward) {// 中奖了

			int lottery_item_id = award.getLottery_item_id();// 奖品id

			List<Map<String, Object>> list = lotteryItemCouponService
					.getLotteryItemCoupon(lottery_item_id);

			if (list != null && list.size() > 0) {// 有奖品
				LOG.info("奖品对应券个数为=" + list.size() + "奖品id为=" + lottery_item_id);

				// 取第一个
				Map<String, Object> cmap = list.get(0);
				// 券批次号
				String batchNo = cmap.get("batchNo") + "";
				int membershipId = member.getMembershipId();
				int brandId = member.getBrandId();
				String source = "Crmbz2";
				String jsonpar = "{\"batchNo\":\"" + batchNo
						+ "\",\"membershipId\":\"" + membershipId
						+ "\",\"merchantId\":\"" + brandId + "\",\"source\":\""
						+ source + "\"}";

				Map<String, Object> resmap = isLotteryCoupon(jsonpar);

				// 接口逻辑
				if ((Boolean) resmap.get("b")) {// 调用接口成功了保存中奖信息

					String couponId = resmap.get("couponId") + "";
					String couponName = resmap.get("couponName") + "";

					// 保存中奖记录
					insertRecordAndAddressOfOrder(member, award, couponId, couponName, isAward);
					
					String[] sqls = getLotterySql(member, award, isAward,
							couponId, couponName, 1);
					if (lotteryRecordDao.batchUpdateLottery(sqls)) {
						winflag = 4;// 保存成功,中奖成功了。
						LOG.info("中奖了!!!membershipId=" + membershipId
								+ ";brandId=" + brandId + ";batchNo=" + batchNo
								+ "奖品id为=" + lottery_item_id);
					}

				} else {
					LOG.info("调用中奖接口错误;结果没中奖，奖品对应券个数为=" + list.size()
							+ "奖品id为=" + lottery_item_id);

					// 保存中奖记录
					insertRecordAndAddressOfOrder(member, award, "0", "", false);
					
					// 没中奖情况
					String[] sqls = getLotterySql(member, award, false, "0",
							"", 2);
					lotteryRecordDao.batchUpdateLottery(sqls);
				}

			} else {
				LOG.info("中奖了但是表weixin_lottery_item_coupon没有设置奖品;奖品对应券个数为="
						+ list.size() + "奖品id为=" + lottery_item_id);
			}

		} else {// 没中奖情况
			// 保存中奖记录
			insertRecordAndAddressOfOrder(member, award, "0", "", isAward);
			
			String[] sqls = getLotterySql(member, award, isAward, "0", "", 1);
			lotteryRecordDao.batchUpdateLottery(sqls);
		}

		return winflag;
	}

	@Override
	public int insertLotteryRecord(MemberAwardVO member, AwardVO award) {
		int winFlag = 5; // 5未中奖；4中奖了
		// 保存中奖记录
		insertRecordAndAddressOfOrder(member, award, "0", "", true);
		// 更新抽奖规则表
			String sql2 = "update public.weixin_lottery_rule set residue_number=residue_number-1 where id="
					+ award.getLottery_rule_id();
			// 更新奖项表
			String sql3 = "update public.weixin_lottery_item  set lottery_item_remain=lottery_item_remain-1 where id="
					+ award.getLottery_item_id();

			String[] sqls = new String[2];
			sqls[0] = sql2;
			sqls[1] = sql3;
			boolean isTrue = lotteryRecordDao.batchUpdateLottery(sqls);
			if (isTrue) {
				winFlag = 4;
			}
		return winFlag;
	}
	
	/**插入中奖记录及订单信息*/
	private void insertRecordAndAddressOfOrder (MemberAwardVO member, AwardVO award, String couponId, String couponName, boolean isAward) {
		int item_id = 0;
		if (isAward) {// 中奖了
			item_id = award.getLottery_item_id();
		}
		
		String sql = "SELECT nextval ('weixin.weixin_lottery_record_id_seq') as id";
		Map<String, Object> idMap = lotteryRecordDao.getinsertRecordId(sql);
		long recordId = Long.parseLong(idMap.get("id").toString()); // 取插入记录表的id
		
		// 插入中奖记录表
		String sql1 = "INSERT INTO weixin.weixin_lottery_record (id,coupon_id,lottery_rule_id,lottery_rule_type,membership_id,weixin_id,phone_no,addtime,brand_id,coupon_name,is_award,lottery_item_id,lottery_record_type) values ("
				+recordId+","
				+ couponId+ ","
				+ award.getLottery_rule_id()+ ","
				+ award.getLottery_rule_type()+ ","
				+ member.getMembershipId()+ ",'"
				+ member.getWeixinId()+ "','"
				+ member.getPhoneNo()+ "',now(),"
				+ member.getBrandId()+ ",'"
				+ couponName+ "',"
				+ isAward + "," + item_id + ","+award.getLottery_item_type()+" )";
		int count = lotteryRecordDao.insertRecord(sql1); // 保存中奖记录
		
		if (count > 0 && award.getLottery_item_type() ==2) { // 向订单表插入数据只有实物添加订单信息
			String sqlTemp = "select nextval ('public.weixin_mall_order_id_seq') as id";
			Map<String, Object> tempMap = lotteryRecordDao.getinsertRecordId(sqlTemp);
			Integer orderId = Integer.parseInt(tempMap.get("id").toString()); // 取插入记录表的id
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WxMallOrder order = new WxMallOrder();
			order.setId(orderId);
			order.setProductInfo(StringUtil.isNullOrEmpty(award.getLottery_coupon_name()) ?award.getLottery_item_name() : award.getLottery_coupon_name());
			order.setOpenId(member.getWeixinId());
			order.setAppId("0");
			order.setAppSignature("0");
			order.setSource(3l); // 抽奖信息插入到订单表的状态
			order.setTrade_state("0");
			order.setBuyNum(1l);
			order.setOrderState(1l);
			order.setDeliverState(0l);
			order.setTimeBegin(new Date());
			order.setTime_end(sdf.format(new Date()));
			order.setBrandId(Long.parseLong(member.getBrandId()+""));
			order.setGoodsId(recordId); // 中奖记录id
			try {
				 count = weixinMallMartService.insertMallOrderLuckUse(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (count > 0) award.setOrderId(orderId);
		}
		
	}

	private String[] getLotterySql(MemberAwardVO member, AwardVO award,
			boolean isAward, String couponId, String couponName, int flag) {

		int item_id = 0;
		if (isAward) {// 中奖了
			item_id = award.getLottery_item_id();
		}

//		// 插入中奖记录表
//		String sql1 = "INSERT INTO weixin.weixin_lottery_record (coupon_id,lottery_rule_id,lottery_rule_type,membership_id,weixin_id,phone_no,addtime,brand_id,coupon_name,is_award,lottery_item_id,lottery_record_type) values ("
//				+ couponId
//				+ ","
//				+ award.getLottery_rule_id()
//				+ ","
//				+ award.getLottery_rule_type()
//				+ ","
//				+ member.getMembershipId()
//				+ ",'"
//				+ member.getWeixinId()
//				+ "','"
//				+ member.getPhoneNo()
//				+ "',now(),"
//				+ member.getBrandId()
//				+ ",'"
//				+ couponName
//				+ "',"
//				+ isAward + "," + item_id + ","+award.getLottery_item_type()+" )";

		if (isAward) {// 中奖了
			// 更新抽奖规则表
			String sql2 = "update public.weixin_lottery_rule set residue_number=residue_number-1 where id="
					+ award.getLottery_rule_id();
			// 更新奖项表
			String sql3 = "update public.weixin_lottery_item  set lottery_item_remain=lottery_item_remain-1 where id="
					+ award.getLottery_item_id();

			String[] sqls = new String[2];
//			sqls[0] = sql1;
			sqls[0] = sql2;
			sqls[1] = sql3;
			return sqls;

		} else {// 没中奖
			if (flag == 2) {// 表示是中奖了但是交互出问题了不更新sql2的weixin_lottery_rule
//				String[] sqls = new String[1];
//				sqls[0] = sql1;
//				return sqls;
			} else {
				// 更新抽奖规则表
				String sql2 = "update public.weixin_lottery_rule set residue_number=residue_number-1 where id="
						+ award.getLottery_rule_id();
				String[] sqls = new String[1];
//				sqls[0] = sql1;
				sqls[0] = sql2;
				return sqls;
			}
		}
		return	null;
	}

	private Map<String, Object> isLotteryCoupon(String jsonpar) {

		String url = lottery_url + "?ciphertext=" + jsonpar + "&key="
				+ Constant.KEY;
		long startTime = System.currentTimeMillis();
		boolean success = false;
		Map<String, Object> resmap = new HashMap<String, Object>();
		LOG.info("调用crm接口地址："+url);
		String jsonre = SendHttpRequestUtil.sendHttpRequest(url);
		LOG.error("调用中奖券接口返回jsonre=" + jsonre);
		if (!StringUtils.isBlank(jsonre)) {
			
			String crmInterface = lottery_url.substring((lottery_url.lastIndexOf("/")+1),lottery_url.lastIndexOf("."));
			LOG.info("--访用CRM【"+crmInterface+"】接口...耗时::" + (System.currentTimeMillis() - startTime) + "毫秒");

			JSONObject requestObject = JSONObject.fromObject(jsonre);

			try {
				String data = URLDecoder.decode(((JSONObject) requestObject
						.get("data")).get("result").toString(), "UTF-8");
				// String reStr = EncryptUtil.dncrypt3DesString(secretKey,
				// data);
				LOG.info("中奖优惠券保存返回" + data);
				JSONObject jsondata = JSONObject.fromObject(data);

				if (jsondata == null) {
					LOG.error("接口调用失败jsondata为null");
				} else if (!jsondata.getBoolean("success")) {
					LOG.error("活动不存在success为false");
				} else {
					String couponId = Long.toString(jsondata.getJSONObject(
							"lottery").getLong("id"));
					String couponName = jsondata.getJSONObject("lottery")
							.getString("name");
					resmap.put("couponId", couponId);
					resmap.put("couponName", couponName);
					success = true;
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			LOG.error("调用中奖券接口返回jsonre为空");
		}
		resmap.put("b", success);

		return resmap;
	}

	@Override
	public List<Map<String, Object>> getLotteryRecordByBrandIdAndWeixinId(String weixinId, Integer brandId) {
		return lotteryRecordDao.getLotteryRecordByBrandIdAndWeixinId(weixinId, brandId);
	}

}

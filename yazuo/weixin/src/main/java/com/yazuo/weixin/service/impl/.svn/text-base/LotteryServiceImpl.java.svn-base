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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.weixin.service.LotteryItemService;
import com.yazuo.weixin.service.LotteryRecordService;
import com.yazuo.weixin.service.LotteryRuleService;
import com.yazuo.weixin.service.LotteryService;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.vo.AwardVO;
import com.yazuo.weixin.vo.MemberAwardVO;

/**
 * @InterfaceName: LotteryServiceImpl
 * @Description: 处理抽奖逻辑的实现
 */
@Service("lotteryService")
public class LotteryServiceImpl implements LotteryService {

	ResourceBundle bundle = ResourceBundle.getBundle("image-config");

	private final String lottery_pic = bundle.getString("caFileIp"); // 原图片地址 static.yazuo.com
	private final String newPictureUrl = bundle.getString("newPictureUrl"); // 新图片地址 image.yazuo.com

	@Resource
	private LotteryRuleService lotteryRuleService;

	@Resource
	private LotteryItemService lotteryItemService;

	@Resource
	private LotteryRecordService lotteryRecordService;

	/*
	 * 
	 * <p> Description:幸运抽奖 只有会员才能抽奖（在微信的clink事件去判断，本方法不做判断） </p>
	 */
	public Map<String, Object> luckyDraw(MemberAwardVO member) throws Exception {

//		String weixinId = member.getWeixinId();
		int brandId = member.getBrandId();
		int type = member.getType();
		String phoneNo = member.getPhoneNo();

		Map<String, Object> remap = new HashMap<String, Object>();

		// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
		int winflag = 1;

		// 中奖类
		AwardVO award = new AwardVO();

		// 是否有这个活动(判断1)
		Map<String, Object> lrule = lotteryRuleService.getLotteryRule(brandId,
				type);

		if (lrule != null) {// 有活动
			// 抽奖规则表id
			String lottery_rule_id = lrule.get("id") + "";
			// 规则时间间隔1：一共总次数2：每天
			int time_unit = Integer.parseInt(lrule.get("time_unit") + "");
			// 规则周期次数 例如：3就是每天3次
			int frequency = Integer.parseInt(lrule.get("frequency") + "");
			// 规则剩余抽奖次数
			int residue_number = Integer.parseInt(lrule.get("residue_number")
					+ "");

			award.setLottery_rule_type(type);// 抽奖规则类型
			award.setLottery_rule_id(lottery_rule_id);// 抽奖规则表id
			award.setLottery_rule_title(lrule.get("title") + "");// 抽奖规则头部描述
			award.setLottery_rule_title_pic(lrule.get("title_pic") + "");// 抽奖规则头部描述图片

			if (type == 1) {// 刮刮卡

				award.setTime_unit(time_unit);
				award.setFrequency(frequency);
				award.setBegin_time(lrule.get("begin_time") + "");// 活动开始时间
				award.setEnd_time(lrule.get("end_time") + "");// 活动结束时间

			}

			if (residue_number == 1) {
				award.setLasttime(1);// 是最后一次抽奖机会
			} else {
				award.setLasttime(2);// 不是最后一次抽奖机会
			}

			if (residue_number > 0) {// 可以抽奖
				// 当天日期
				String begintime = null;
				// 明天日期
				String endtime = null;
				if (time_unit == 2) {// 每天
					// 当天日期
					begintime = DateUtil.getDate4d();// yyyy-MM-dd
					// 明天日期
					endtime = DateUtil.formerday(begintime, 1);
				}
				// 抽奖过程
				winflag = lotteryProcess(member, brandId, phoneNo, award,
						lottery_rule_id, frequency, residue_number, begintime,
						endtime);
			} else {// 奖品抽完抽奖已经结束
				winflag = 3;
			}
		}
		// 大转盘角度
		getDegrees(award, winflag);
		remap.put("winflag", winflag);// 中奖状态
		remap.put("award", award);// 奖项信息
		remap.put("member", member);// 人员信息
		remap.put("lottery_pic", lottery_pic);// 图片地址
		remap.put("newPictureUrl", newPictureUrl); // 新图片地址
		return remap;
	}


	public Map<String, Object> whetherLuckyDraw(MemberAwardVO member)
			throws Exception {

//		String weixinId = member.getWeixinId();
		int brandId = member.getBrandId();
		int type = member.getType();
		String phoneNo = member.getPhoneNo();

		Map<String, Object> remap = new HashMap<String, Object>();

		// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：可以抽奖5:没设置奖品
		int winflag = 1;

		// 是否有这个活动(判断1)
		Map<String, Object> lrule = lotteryRuleService.getLotteryRule(brandId,
				type);

		if (lrule != null) {// 有活动
			// 抽奖规则表id
			String lottery_rule_id = lrule.get("id") + "";
			// 规则时间间隔1：一共总次数2：每天
			int time_unit = Integer.parseInt(lrule.get("time_unit") + "");
			// 规则周期次数 例如：3就是每天3次
			int frequency = Integer.parseInt(lrule.get("frequency") + "");
			// 规则剩余抽奖次数
			int residue_number = Integer.parseInt(lrule.get("residue_number")
					+ "");

			// 奖品列表
			List<Map<String, Object>> list = lotteryItemService
					.getLotteryItem(lottery_rule_id);

			// 当天日期
			String begintime = null;
			// 明天日期
			String endtime = null;

			if (time_unit == 2) {// 每天
				// 当天日期
				begintime = DateUtil.getDate4d();// yyyy-MM-dd
				// 明天日期
				endtime = DateUtil.formerday(begintime, 1);
			}

			// 是否超出抽奖次数
			int count = lotteryRecordService.getLotteryRecord(phoneNo,
					brandId, lottery_rule_id, begintime, endtime, 1);

			remap.put("list", list);
			remap.put("number", frequency - count);// 剩余抽奖次数
			remap.put("time_unit", time_unit);// 规则时间间隔1：一共总次数2：每天
			remap.put("frequency", frequency);// 规则周期次数 例如：3就是每天3次
			remap.put("begin_time", lrule.get("begin_time") + "");// 活动开始时间
			remap.put("end_time", lrule.get("end_time") + "");// 活动结束时间

			remap.put("title", lrule.get("title") + "");// 抽奖规则头部描述
			remap.put("title_pic", lrule.get("title_pic") + "");// 抽奖规则头部描述图片

			if (residue_number > 0) {// 可以抽奖

				if (count < frequency) { // 允许抽奖
					if (list != null && list.size() > 0) {// 设置奖品
						remap.put("length", list.size());
						winflag = 4;
					} else {// 没设置奖项
						winflag = 5;
					}

				} else {// 超出次数
					winflag = 2;
				}
			} else {// 抽奖次数用完抽奖结束
				winflag = 3;
			}
		}
		remap.put("winflag", winflag);
		remap.put("lottery_pic", lottery_pic);// 图片地址
		remap.put("newPictureUrl", newPictureUrl); // 新图片地址
		return remap;
	}
	
	/**抽奖过程*/
	private synchronized int lotteryProcess(MemberAwardVO member, int brandId,
			String phoneNo, AwardVO award, String lottery_rule_id,
			int frequency, int residue_number, String begintime, String endtime)
			throws Exception {
		int winflag = 0;
		// 是否超出抽奖次数
		int count = lotteryRecordService.getLotteryRecord(phoneNo,
				brandId, lottery_rule_id, begintime, endtime,1);

		if (count < frequency) { // 合格

			award.setRemainCount(frequency - count - 1);// 用户剩余抽奖次数
			// 抽奖
			winflag = isWonLottery(member, award, residue_number);

		} else {// 超出次数
			winflag = 2;
		}
		return winflag;
	}

	/*
	 * 
	 * <p> Description:抽奖逻辑根据规则id和允许抽取的次数 处理用户是否中奖了</p>
	 */
	private int isWonLottery(MemberAwardVO member, AwardVO award,
			int residue_number) throws Exception {
		// 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
		int winflag = 5;

		Random r = new Random();
		// 中奖随机数
		int ratio = r.nextInt(residue_number) + 1;

		// 根据规则id查找奖品列表（必须是按等级升序排列）
		List<Map<String, Object>> list = lotteryItemService
				.getLotteryItem(award.getLottery_rule_id());
		if (list != null && list.size() > 0) {
			// 中奖数字开始区间
			int wonBeginNum = 0;
			// 中奖数字结束区间
			int wonEndNum = 0;
			// 一共有几个等级的奖项
			award.setItem_seq_count(list.size());

			for (Map<String, Object> map : list) {
				// 奖品剩余数量
				int lottery_item_remain = Integer.parseInt(map
						.get("lottery_item_remain") + "");

				wonEndNum = wonEndNum + lottery_item_remain;
				wonBeginNum = wonEndNum - lottery_item_remain;

				if (ratio > wonBeginNum && ratio <= wonEndNum) {// 表示中奖了
					// 奖项id
					int id = Integer.parseInt(map.get("id") + "");
					// 奖项名称
					String lottery_item_name = map.get("lottery_item_name")
							+ "";
					// 奖项图片名称
					String picName = map.get("lottery_item_pic") + "";
					// 奖项等级
					int lottery_item_seq = Integer.parseInt(map
							.get("lottery_item_seq") + "");

					// 中奖vo
					award.setLottery_item_id(id);
					award.setLottery_item_seq(lottery_item_seq);
					award.setLottery_item_name(lottery_item_name);
					award.setLottery_item_pic(picName);
					winflag = 4;// 中奖了
					break;
				}
			}

		} else {
			winflag = 6;// 6：(可以抽奖)但是没添加奖项
		}
		award.setLottery_item_type(1); // 正常抽奖
		if (winflag == 4) {// 中奖保存信息
			winflag = lotteryRecordService.insertLotteryRecord(member, award,
					true);

		} else if (winflag == 5) {// 没中奖保存信息
			lotteryRecordService.insertLotteryRecord(member, award, false);
		}
		return winflag;
	}

	/*
	 * 
	 * <p> Description:根据是否中奖和奖项设置判断转动角度数</p>
	 */
	private void getDegrees(AwardVO award, int winflag) {

		int type = award.getLottery_rule_type();
		if (type == 2) {// 大转盘
			Random r = new Random();
			int count = award.getItem_seq_count();// 一共有几个奖项
			int itemseq = award.getLottery_item_seq();// 奖品等级
			if (winflag == 4) {// 中奖了

				if (count == 1) {// 1个奖项
					int[] degrees = { 68, 157, 247, 337 };
					// 中奖随机角度
					award.setDegrees(degrees[r.nextInt(4)]);

				} else if (count == 2) {// 2个奖项

					if (itemseq == 1) {// 1等奖
						int[] degrees = { 157, 337 };
						award.setDegrees(degrees[r.nextInt(2)]);

					} else if (itemseq == 2) {// 2等奖
						int[] degrees = { 68, 247 };
						award.setDegrees(degrees[r.nextInt(2)]);
					}

				} else if (count == 3) {// 3个奖项
					if (itemseq == 1) {// 1等奖
						int[] degrees = { 23, 202 };
						award.setDegrees(degrees[r.nextInt(2)]);

					} else if (itemseq == 2) {// 2等奖
						int[] degrees = { 68, 247 };
						award.setDegrees(degrees[r.nextInt(2)]);
					} else if (itemseq == 3) {// 3等奖
						int[] degrees = { 113, 292 };
						award.setDegrees(degrees[r.nextInt(2)]);
					}
				}

			} else {// 没中奖

				if (count == 1 || count == 2) {// 1个或2个奖项

					int[] degrees = { 23, 113, 202, 292 };
					// 未中奖随机角度
					award.setDegrees(degrees[r.nextInt(4)]);

				} else if (count == 3) {// 3个奖项

					int[] degrees = { 157, 337 };
					// 未中奖随机角度
					award.setDegrees(degrees[r.nextInt(2)]);
				}

			}
		}
	}

}

/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-02-26
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.vo;

/**
 * @InterfaceName: AwardVO
 * @Description: 奖品的VO
 */
public class AwardVO {

	// 奖品相关字段
	private int lottery_item_id;// 奖品id
	private int lottery_item_seq;// 奖品等级
	private String lottery_item_name;// 奖项名称
	private String lottery_item_pic;// 奖品图片名称
	private int item_seq_count;// 一共有几个等级的奖项
	private String lottery_coupon_name; // 奖品名称

	// 刮刮卡用到参数
	private int time_unit;// 规则时间间隔1：一共总次数2：每天
	private int frequency;// 规则周期次数
	private String begin_time;// 活动开始时间
	private String end_time;// 活动结束时间

	private int remainCount;// 用户剩余抽奖次数
	private int lasttime;// 是否是最好一个抽奖机会 1是，2不是

	// 抽奖规则相关字段
	private String lottery_rule_id;// 抽奖规则id
	private String lottery_rule_title;// 抽奖规则头部描述
	private String lottery_rule_title_pic;// 抽奖规则头部描述图片
	private int lottery_rule_type;// 抽奖规则类型1：刮刮卡2：大转盘3：老虎机
	private int degrees;// 大转盘角度
	
	private int lottery_item_type=1; // 奖品类型 1 券  2 实物
	private long orderId; // 插入的订单id
	

	public int getDegrees() {
		return degrees;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}

	public int getItem_seq_count() {
		return item_seq_count;
	}

	public int getLottery_rule_type() {
		return lottery_rule_type;
	}

	public int getLasttime() {
		return lasttime;
	}

	public void setLasttime(int lasttime) {
		this.lasttime = lasttime;
	}

	public void setLottery_rule_type(int lottery_rule_type) {
		this.lottery_rule_type = lottery_rule_type;
	}

	public void setItem_seq_count(int item_seq_count) {
		this.item_seq_count = item_seq_count;
	}

	public int getLottery_item_id() {
		return lottery_item_id;
	}

	public void setLottery_item_id(int lottery_item_id) {
		this.lottery_item_id = lottery_item_id;
	}

	public int getLottery_item_seq() {
		return lottery_item_seq;
	}

	public void setLottery_item_seq(int lottery_item_seq) {
		this.lottery_item_seq = lottery_item_seq;
	}

	public String getLottery_item_name() {
		return lottery_item_name;
	}

	public void setLottery_item_name(String lottery_item_name) {
		this.lottery_item_name = lottery_item_name;
	}

	public String getLottery_item_pic() {
		return lottery_item_pic;
	}

	public void setLottery_item_pic(String lottery_item_pic) {
		this.lottery_item_pic = lottery_item_pic;
	}

	public String getLottery_rule_id() {
		return lottery_rule_id;
	}

	public void setLottery_rule_id(String lottery_rule_id) {
		this.lottery_rule_id = lottery_rule_id;
	}

	public String getLottery_rule_title() {
		return lottery_rule_title;
	}

	public void setLottery_rule_title(String lottery_rule_title) {
		this.lottery_rule_title = lottery_rule_title;
	}

	public String getLottery_rule_title_pic() {
		return lottery_rule_title_pic;
	}

	public void setLottery_rule_title_pic(String lottery_rule_title_pic) {
		this.lottery_rule_title_pic = lottery_rule_title_pic;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public int getTime_unit() {
		return time_unit;
	}

	public void setTime_unit(int time_unit) {
		this.time_unit = time_unit;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getLottery_item_type() {
		return lottery_item_type;
	}

	public void setLottery_item_type(int lottery_item_type) {
		this.lottery_item_type = lottery_item_type;
	}
	
	public String getLottery_coupon_name() {
		return lottery_coupon_name;
	}

	public void setLottery_coupon_name(String lottery_coupon_name) {
		this.lottery_coupon_name = lottery_coupon_name;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "AwardVO [lottery_item_id=" + lottery_item_id
				+ ", lottery_item_seq=" + lottery_item_seq
				+ ", lottery_item_name=" + lottery_item_name
				+ ", lottery_item_pic=" + lottery_item_pic
				+ ", item_seq_count=" + item_seq_count + ", time_unit="
				+ time_unit + ", frequency=" + frequency + ", begin_time="
				+ begin_time + ", end_time=" + end_time + ", remainCount="
				+ remainCount + ", lasttime=" + lasttime + ", lottery_rule_id="
				+ lottery_rule_id + ", lottery_rule_title="
				+ lottery_rule_title + ", lottery_rule_title_pic="
				+ lottery_rule_title_pic + ", lottery_rule_type="
				+ lottery_rule_type + ", degrees=" + degrees + "]";
	}

}

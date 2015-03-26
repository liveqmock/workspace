package com.yazuo.weixin.minimart.vo;

import java.util.Date;

/**
* @ClassName WxMallOrderCouponState
* @Description 微信商城订单发券vo
* @author sundongfeng@yazuo.com
* @date 2014-8-6 下午2:36:05
* @version 1.0
*/
public class WxMallOrderCouponState {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.mobile
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.total_fee
     *
     * @mbggenerated
     */
    private Double totalFee;

    private Long integral;
    
    private Long counter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.goods_id
     *
     * @mbggenerated
     */
    private Long goodsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.open_id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.trade_state
     *
     * @mbggenerated
     */
    private String tradeState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.send_state
     *
     * @mbggenerated
     */
    private Boolean sendState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.transaction_id
     *
     * @mbggenerated
     */
    private String transactionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.out_trade_no
     *
     * @mbggenerated
     */
    private String outTradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column weixin_mall_order_coupon_state.brand_id
     *
     * @mbggenerated
     */
    private Long brandId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.id
     *
     * @return the value of weixin_mall_order_coupon_state.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.id
     *
     * @param id the value for weixin_mall_order_coupon_state.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.mobile
     *
     * @return the value of weixin_mall_order_coupon_state.mobile
     *
     * @mbggenerated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.mobile
     *
     * @param mobile the value for weixin_mall_order_coupon_state.mobile
     *
     * @mbggenerated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.total_fee
     *
     * @return the value of weixin_mall_order_coupon_state.total_fee
     *
     * @mbggenerated
     */
    public Double getTotalFee() {
        return totalFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.total_fee
     *
     * @param totalFee the value for weixin_mall_order_coupon_state.total_fee
     *
     * @mbggenerated
     */
    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.counter
     *
     * @return the value of weixin_mall_order_coupon_state.counter
     *
     * @mbggenerated
     */
    public Long getCounter() {
        return counter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.counter
     *
     * @param counter the value for weixin_mall_order_coupon_state.counter
     *
     * @mbggenerated
     */
    public void setCounter(Long counter) {
        this.counter = counter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.goods_id
     *
     * @return the value of weixin_mall_order_coupon_state.goods_id
     *
     * @mbggenerated
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.goods_id
     *
     * @param goodsId the value for weixin_mall_order_coupon_state.goods_id
     *
     * @mbggenerated
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.open_id
     *
     * @return the value of weixin_mall_order_coupon_state.open_id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.open_id
     *
     * @param openId the value for weixin_mall_order_coupon_state.open_id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.trade_state
     *
     * @return the value of weixin_mall_order_coupon_state.trade_state
     *
     * @mbggenerated
     */
    public String getTradeState() {
        return tradeState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.trade_state
     *
     * @param tradeState the value for weixin_mall_order_coupon_state.trade_state
     *
     * @mbggenerated
     */
    public void setTradeState(String tradeState) {
        this.tradeState = tradeState == null ? null : tradeState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.send_state
     *
     * @return the value of weixin_mall_order_coupon_state.send_state
     *
     * @mbggenerated
     */
    public Boolean getSendState() {
        return sendState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.send_state
     *
     * @param sendState the value for weixin_mall_order_coupon_state.send_state
     *
     * @mbggenerated
     */
    public void setSendState(Boolean sendState) {
        this.sendState = sendState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.create_time
     *
     * @return the value of weixin_mall_order_coupon_state.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.create_time
     *
     * @param createTime the value for weixin_mall_order_coupon_state.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.transaction_id
     *
     * @return the value of weixin_mall_order_coupon_state.transaction_id
     *
     * @mbggenerated
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.transaction_id
     *
     * @param transactionId the value for weixin_mall_order_coupon_state.transaction_id
     *
     * @mbggenerated
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.out_trade_no
     *
     * @return the value of weixin_mall_order_coupon_state.out_trade_no
     *
     * @mbggenerated
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.out_trade_no
     *
     * @param outTradeNo the value for weixin_mall_order_coupon_state.out_trade_no
     *
     * @mbggenerated
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column weixin_mall_order_coupon_state.brand_id
     *
     * @return the value of weixin_mall_order_coupon_state.brand_id
     *
     * @mbggenerated
     */
    public Long getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column weixin_mall_order_coupon_state.brand_id
     *
     * @param brandId the value for weixin_mall_order_coupon_state.brand_id
     *
     * @mbggenerated
     */
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-05-14
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.vo;

/**
 * @ClassName: CouponNewDescribeVO
 * @Description: 优惠券描述bean
 */
public class CouponNewDescribeVO {

	private String description;// 描述信息
	private String coupon_name;// 优惠券名称
	private Integer brand_id;// 集团id
	private Integer flag;// 标识
	private Integer batch_no;// 批次号
	private Integer merchant_id;// 门店id
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoupon_name() {
		return coupon_name;
	}

	public Integer getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(Integer batch_no) {
		this.batch_no = batch_no;
	}

	public Integer getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(Integer merchant_id) {
		this.merchant_id = merchant_id;
	}

	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}

	public Integer getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(Integer brand_id) {
		this.brand_id = brand_id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "CouponNewDescribeVO [description=" + description
				+ ", coupon_name=" + coupon_name + ", brand_id=" + brand_id
				+ ", flag=" + flag + ", batch_no=" + batch_no
				+ ", merchant_id=" + merchant_id + "]";
	}

}

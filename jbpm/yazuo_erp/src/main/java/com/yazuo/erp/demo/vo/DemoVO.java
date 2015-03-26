/**
 * <p>Project: erp</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2013-12-12
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.erp.demo.vo;


/**
 * @ClassName: StatDataVO
 * @Description: 报表常量数据
 * 
 */
public class DemoVO {

	private int brandId;// 集团id
	private String starttime;// 统计开始时间
	private String endtime;// 统计结束时间
	private String flag;// 标识flag=0昨日连接过来的，flag=1今天连接过来的(月份同理)

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Override
	public String toString() {
		return "StatDataVO [brandId=" + brandId + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", flag=" + flag + "]";
	}

}

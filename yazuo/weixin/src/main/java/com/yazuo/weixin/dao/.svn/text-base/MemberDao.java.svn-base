package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.service.QueryBrandIdService;
import com.yazuo.weixin.vo.CardVO;
import com.yazuo.weixin.vo.CouponVO;
import com.yazuo.weixin.vo.IdentifyinginfoVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.MerchantVO;

@Repository
public class MemberDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private QueryBrandIdService queryBrandIdService;

	public MemberVO getMember(String weixin_id, Integer brand_id) {
		String sql = "select * from weixin.membership where weixin_id=? and brand_id =? ";
		List<MemberVO> memberList = (List<MemberVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<MemberVO>(MemberVO.class),
				weixin_id, brand_id);
		if (memberList != null && memberList.size() != 0) {
			return memberList.get(0);
		}
		return null;
	}

	public boolean updateMemberSubscribeStatus(MemberVO member) {
		String sql = "update weixin.membership set is_subscribe=? where id=?";
		try {
			return jdbcTemplate.update(sql, member.getIsSubscribe(), member.getId()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateMemberInfo(MemberVO member) {
		String sql = null;
		if (member.getBirthday() == null) {
			sql = "update weixin.membership set phone_no=?,card_no=?,membership_id=?,is_member=? where id=?";
			try {
				return jdbcTemplate.update(sql, member.getPhoneNo(), member.getCardNo(), member.getMembershipId(),
						member.getIsMember(), member.getId()) != -1 ? true : false;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return false;
			}
		} else {
			sql = "update weixin.membership set phone_no=?,card_no=?,membership_id=?,is_member=?,birthday=?,update_times=update_times+1,birth_type=? where id=?";
			try {
				String birthType = member.getBirthType();
				if (StringUtils.isEmpty(birthType)) {
					birthType = "1";
				}
				return jdbcTemplate.update(sql, member.getPhoneNo(), member.getCardNo(), member.getMembershipId(),
						member.getIsMember(), member.getBirthday(), birthType, member.getId()) != -1 ? true : false;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				return false;
			}
		}

	}

	// 走的注册页面
	public boolean updateMemberPageInfo(MemberVO member, String phonNo) {// 新的入口里面的更改用户的基本信息
		String sql = null;// 这一步是为了判断这个用户进入新入口前是否用手机投过票，没有update_times不用加1
		if (phonNo == null || phonNo != null && phonNo.equals("")) {
			sql = "update weixin.membership set phone_no=?,card_no=?,membership_id=?,is_member=?,birthday=?,gender = ?,name = ?, update_times=0, member_type=? ,birth_type=?,data_source=? where id=?";
		} else {
			sql = "update weixin.membership set phone_no=?,card_no=?,membership_id=?,is_member=?,birthday=?,gender = ?,name = ?, update_times=update_times+1, member_type=? ,birth_type=?,data_source=?  where id=?";
		}
		try {
			String birthType=member.getBirthType();
			if(StringUtils.isEmpty(birthType)){
				birthType="1";
			}
			return jdbcTemplate.update(sql, member.getPhoneNo(), member.getCardNo(), member.getMembershipId(),
					member.getIsMember(), member.getBirthday(), member.getGender(), member.getName(), member.getMemberType(),birthType,member.getDataSource(), member.getId()) != -1 ? true	: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}


	// 用户当前卡作废时查询用户有效的实体卡||

	// 更新用户卡号，卡作废时
	public boolean updateMemebrShipInfoById(MemberVO member, CardVO card) {
		String sql = "update weixin.membership set card_no = ?,membership_id = ? where id = ?";
		try {
			return jdbcTemplate.update(sql, card.getCardNo(), card.getMembershipId(), member.getId()) == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}




	public boolean insertMember(MemberVO member) {
		String sql = "INSERT INTO weixin.membership(weixin_id,  brand_id,is_subscribe,is_member,update_times,data_source) values (?,?,?,?,0,?)";
		try {
			return jdbcTemplate.update(sql, member.getWeixinId(), member.getBrandId(), true, false,member.getDataSource()) == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public MemberVO getMemberByWeixinIdAndBrandId(String weixinId, Integer brandId) {
		String sql = "select * from weixin.membership where weixin_id=? and brand_id =? ";
		List<MemberVO> memberList = (List<MemberVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<MemberVO>(MemberVO.class),
				weixinId, brandId);
		if (memberList != null && memberList.size() != 0) {
			return memberList.get(0);
		}
		return null;
	}

	public IdentifyinginfoVO getLastIdentifyinginfoByWeixinId(String weixinId, Integer brandId) {
		String sql = "select * from weixin.identifyinginfo where weixin_id=? and brand_id=? order by identifying_time desc limit 1 ";
		List<IdentifyinginfoVO> identifyinginfoList = (List<IdentifyinginfoVO>) jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IdentifyinginfoVO>(IdentifyinginfoVO.class), weixinId, brandId);
		if (identifyinginfoList != null && identifyinginfoList.size() != 0) {
			return identifyinginfoList.get(0);
		}
		return null;
	}

	public boolean insertIdentifyinginfo(IdentifyinginfoVO identifyinginfo) {
		String sql = "INSERT INTO weixin.identifyinginfo(weixin_id,identifying_code,identifying_time ,phone_no,brand_id) values (?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, identifyinginfo.getWeixinId(), identifyinginfo.getIdentifyingCode(),
					identifyinginfo.getIdentifyingTime(), identifyinginfo.getPhoneNo(), identifyinginfo.getBrandId()) == 1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}


	public MemberVO getMemberByOpenId(String openId) {
		String sql = "select * from weixin.membership where weixin_id=? ";
		List<MemberVO> memberList = (List<MemberVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<MemberVO>(MemberVO.class),
				openId);
		if (memberList != null && memberList.size() != 0) {
			return memberList.get(0);
		}
		return null;
	}

	public Map<String, Object> getTagId(String weixinId, Integer merchantId, Integer membershipId) {
		StringBuffer sb = new StringBuffer(256);
		sb.append(" SELECT                       ");
		sb.append(" 	M .membership_id,        ");
		sb.append(" 	b.tag_id,                ");
		sb.append(" 	b.tag_name               ");
		sb.append(" FROM                         ");
		sb.append(" 	weixin.membership M,     ");
		sb.append(" 	weixin.business b        ");
		sb.append(" WHERE                        ");
		sb.append(" 	1 = 1                    ");
		sb.append(" AND b.brand_id = M .brand_id ");
		sb.append(" AND M .brand_id = " + merchantId);
		if (weixinId != null) {
			sb.append(" AND M .weixin_id = '" + weixinId + "' ");
		}
		if (membershipId != null) {
			sb.append(" AND M .membership_id = " + membershipId);
		}
		log.info(sb.toString());
		return jdbcTemplate.queryForMap(sb.toString());
	}

	/**
	 * 根据membershipId查询所有的电子会员列表
	 * 
	 * @param membershipId
	 * @return
	 */
	public List<MemberVO> getMemberListByMembershipId(Integer membershipId) {
		StringBuffer sb = new StringBuffer(64);
		sb.append("select m.* from weixin.membership m where m.membership_id=").append(membershipId);
		log.info(sb.toString());
		List<MemberVO> memberList = (List<MemberVO>) jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<MemberVO>(
				MemberVO.class));
		return memberList;
	}

	/**
	 * 根据membershipId批量修改该会员的相关电子会员信息
	 * @param member
	 */
	public boolean batchUpdateMemberPageInfo(MemberVO member) {
		String sql = null;
		sql = "update weixin.membership set phone_no=?,card_no=?,membership_id=?,is_member=?,birthday=?,gender = ?,name = ?, update_times=update_times+1 ,birth_type=?,data_source=?  where membership_id=? and id!=? ";
		log.info(sql+"["+member.toString()+"]");
		try {
			String birthType=member.getBirthType();
			if(StringUtils.isEmpty(birthType)){
				birthType="1";
			}
			return jdbcTemplate.update(sql, member.getPhoneNo(), member.getCardNo(), member.getMembershipId(),
					member.getIsMember(), member.getBirthday(), member.getGender(), member.getName(),birthType,member.getDataSource(), member.getMembershipId(),member.getId()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 根据brandID和手机号查询会员信息
	 * 
	 * @param Map
	 * @return
	 */
	public Map<String, Object> getMember(Integer brandId, String phoneNo) {
		String sql = "select weixin_id,phone_no,brand_id,is_member,member_type,data_source,membership_id from weixin.membership where brand_id="
				+ brandId + " and phone_no='" + phoneNo + "' order by id desc limit 1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * <p>
	 * Description:网页加入会员
	 * </p>
	 * 
	 * @param map
	 * 
	 * 
	 * @return boolean
	 */
	public boolean insertMember(Map<String, Object> map) {

		String sql = "INSERT INTO weixin.membership(membership_id,brand_id,card_no,phone_no,is_subscribe,is_member,member_type,data_source,update_times,weixin_id, merchant_source) values ("
				+ map.get("membership_id")
				+ ","
				+ map.get("brand_id")
				+ ",'"
				+ map.get("card_no")
				+ "','"
				+ map.get("phone_no")
				+ "','"
				+ map.get("is_subscribe")
				+ "','"
				+ map.get("is_member")
				+ "',"
				+ map.get("member_type")
				+ ","
				+ map.get("data_source") + "," + map.get("update_times") + ",'" + map.get("weixin_id") + "',"+map.get("merchant_source")+");";
		try {
			return jdbcTemplate.update(sql) != -1 ? true : false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	public String getCouponDesc(int batchNo){
		String sql = "select c.coupon_desc from PUBLIC.coupon_template C where C.batch_no =?";
		
		try {
			return jdbcTemplate.queryForObject(sql,String.class,batchNo);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "";
		}
	}
	
}

package com.yazuo.weixin.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.util.PageInfo;
import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.BusinessVO;

@Repository
public class BusinessDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());

	public List<BusinessVO> getAllBusiness(){
		StringBuffer sb = new StringBuffer();
		sb.append("select wb.*,wmm.is_certification,wmm.is_open_payment  from  weixin.business wb");
		sb.append(" LEFT JOIN public.weixin_mall_merchant_dict wmm on wb.brand_id=wmm.brand_id");
		sb.append(" where wb.is_delete=false order by wb.id desc");
		return jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<BusinessVO>(BusinessVO.class));
	}
	
	public Pagination<BusinessVO> getBusinessPage(PageInfo page, BusinessVO business) {
		StringBuffer sb = new StringBuffer();
		sb.append("select wb.*,wmm.is_certification,wmm.is_open_payment  from  weixin.business wb");
		sb.append(" LEFT JOIN public.weixin_mall_merchant_dict wmm on wb.brand_id=wmm.brand_id");
		sb.append(" where wb.is_delete=false");
		if (business !=null) {
			if (!StringUtil.isNullOrEmpty(business.getTitle())) {
				sb.append(" and wb.title like '%").append(business.getTitle()).append("%'");
			}
			if (!StringUtil.isNullOrEmpty(business.getWeixinId())) {
				sb.append(" and wb.weixin_id like '%").append(business.getWeixinId()).append("%'");		
			}
			if (business.getBrandId()!=null) {
				sb.append(" and wb.brand_id=").append(business.getBrandId());
			}
			if (business.getIsCertification()!=null) {
				if (business.getIsCertification()) { // 认证
					sb.append(" and wmm.is_certification=").append(business.getIsCertification());
				} else {
					sb.append(" and (wmm.is_certification=").append(business.getIsCertification()).append(" or wmm.is_certification is null)");
				}
			}
			if (business.getIsOpenPayment()!=null) {
				if (business.getIsOpenPayment()) { // 开通支付
					sb.append(" and wmm.is_open_payment=").append(business.getIsOpenPayment());
				} else {
					sb.append(" and (wmm.is_open_payment=").append(business.getIsOpenPayment()).append(" or wmm.is_open_payment is null)");
				}
			}
		}
		sb.append(" order by wb.id desc");
		
		Pagination<BusinessVO> pagination = new Pagination<BusinessVO>(sb.toString(), page.getCurrentPage(), page.getNumPerPage(), jdbcTemplate,
				new BeanPropertyRowMapper<BusinessVO>(BusinessVO.class));
		return pagination;
	}

	public Map<String, Object> getBusinessByPage(int page, int pagesize) {
		String sql = "select member_rights,birthday_message,weixin_id ,token,	bigimage_name,	smallimage_name,	title,	brand_id,	company_weibo_id,	is_delete,	id,	is_allow_weixin_member,	salutatory,	COALESCE((SELECT is_need_identifying FROM weixin.business_activity where brand_id=t.brand_id),false) as is_need_identifying,	COALESCE((SELECT replay_content FROM weixin.business_activity where brand_id=t.brand_id),'') as vote_msg FROM	weixin.business T where is_delete=false";
		Pagination<BusinessVO> pagination = new Pagination<BusinessVO>(sql, page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<BusinessVO>(BusinessVO.class));
		return pagination.getResultMap();
	}

	public Map<String, Object> getBusinessByTitle(String title, int page, int pagesize) {
		String sql = "select member_rights,birthday_message,weixin_id ,token,	bigimage_name,	smallimage_name,	title,	brand_id,	company_weibo_id,	is_delete,	id, is_allow_weixin_member,	salutatory,	COALESCE((SELECT is_need_identifying FROM weixin.business_activity where brand_id=t.brand_id),false) as is_need_identifying,	COALESCE((SELECT replay_content FROM weixin.business_activity where brand_id=t.brand_id),'') as vote_msg FROM	weixin.business T where title like ? and is_delete=false order by id desc";
		Pagination<BusinessVO> pagination = new Pagination<BusinessVO>(sql, page, pagesize, jdbcTemplate,
				new BeanPropertyRowMapper<BusinessVO>(BusinessVO.class), "%" + title + "%");
		return pagination.getResultMap();
	}

	public boolean saveBusiness(BusinessVO business) {
		String sql = "INSERT INTO weixin.business(weixin_id,token, bigimage_name, smallimage_name, title, brand_id,company_weibo_id,is_delete,is_allow_weixin_member,salutatory,vote_status,birthday_message,member_rights,tag_name,tag_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, business.getWeixinId(), business.getToken(), business.getBigimageName(),
					business.getSmallimageName(), business.getTitle(), business.getBrandId(), business.getCompanyWeiboId(),
					false, business.getIsAllowWeixinMember(), business.getSalutatory(), business.getVoteStatus(),
					business.getBirthdayMessage(), business.getMemberRights(), business.getTagName(), business.getTagId()) == 1 ? true
					: false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}

	}

	public BusinessVO getBusinessByWeixinId(String weixinId) {
		String sql = "SELECT member_rights,birthday_message,weixin_id ,token,	bigimage_name,	smallimage_name,	title,	brand_id,	company_weibo_id,	is_delete,	id,	"
				+ " is_allow_weixin_member,	salutatory,"
				+ "COALESCE((SELECT is_need_identifying FROM weixin.business_activity where brand_id=t.brand_id),false) as is_need_identifying,	"
				+ "COALESCE((SELECT replay_content FROM weixin.business_activity where brand_id=t.brand_id),'') as vote_msg "
				+ "FROM	weixin.business T where weixin_id=? and is_delete=false";
		List<BusinessVO> businessList = (List<BusinessVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusinessVO>(
				BusinessVO.class), weixinId);
		if (businessList != null && businessList.size() > 0) {
			return businessList.get(0);
		}
		return null;
	}

	public BusinessVO getBusinessByBrandId(Integer brandId) {
		String sql = "SELECT member_rights,birthday_message,weixin_id ,token,	bigimage_name,	smallimage_name,	title,	brand_id,	company_weibo_id,	is_delete,	id,	" +
				" salutatory,	COALESCE((SELECT is_need_identifying FROM weixin.business_activity where brand_id=t.brand_id),false) as is_need_identifying,	COALESCE((SELECT replay_content FROM weixin.business_activity where brand_id=t.brand_id),'') as vote_msg ,vote_status,tag_id,tag_name FROM	weixin.business T where brand_id=? and is_delete=false";
		List<BusinessVO> businessList = (List<BusinessVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusinessVO>(
				BusinessVO.class), brandId);
		if (businessList != null && businessList.size() > 0) {
			return businessList.get(0);
		}
		return null;
	}
	
	public BusinessVO getIsAllowWeixinMember(Integer brandId){
		StringBuffer sb=new StringBuffer(512);                                             
		sb.append(" SELECT COALESCE(b.is_allow_weixin_member, true) AS is_allow_weixin_member FROM weixin.business b ");
		sb.append("WHERE b.brand_id = ? and is_delete=false");
		List<BusinessVO> businessList = (List<BusinessVO>) jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<BusinessVO>(
				BusinessVO.class), brandId);
		if (businessList != null && businessList.size() > 0) {
			return businessList.get(0);
		}
		return null;
	}

	public BusinessVO getBusinessById(Integer id) {
		String sql = "select * from  weixin.business where id=? and is_delete=false";
		List<BusinessVO> businessList = (List<BusinessVO>) jdbcTemplate.query(sql, new BeanPropertyRowMapper<BusinessVO>(
				BusinessVO.class), id);
		if (businessList != null && businessList.size() > 0) {
			return businessList.get(0);
		}
		return null;
	}

	public boolean deleteBusiness(BusinessVO business) {
		String sql = "update weixin.business set is_delete=? where id=?;";
		try {
			return jdbcTemplate.update(sql, true, business.getId()) != -1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	public boolean updateBusiness(BusinessVO business) {
		try {
			boolean flag = false;
			boolean isEmpty = !StringUtil.isNullOrEmpty(business.getMemberRights());
			String memberRight = "";
			if (isEmpty) {
				memberRight = ",member_rights='"+business.getMemberRights()+"' ";
			}
			if (business.getTagId() == null) {
				String sql = "update weixin.business set birthday_message=?,weixin_id=?,token=?,title=?, brand_id=?,company_weibo_id=?,is_allow_weixin_member=?,salutatory=?,vote_status=?"+memberRight+ " where id=?;";
				flag = jdbcTemplate.update(sql,business.getBirthdayMessage(), business.getWeixinId(), business.getToken(),
						business.getTitle(), business.getBrandId(), business.getCompanyWeiboId(),
						business.getIsAllowWeixinMember(), business.getSalutatory(), business.getVoteStatus(),
						 business.getId()) != -1 ? true : false;
			} else {
				String sql = "update weixin.business set birthday_message=?,weixin_id=?,token=?,title=?, brand_id=?,company_weibo_id=?,is_allow_weixin_member=?,salutatory=?,vote_status=?"+memberRight+",tag_name=?,tag_id=? where id=?;";
				flag = jdbcTemplate.update(sql,business.getBirthdayMessage(), business.getWeixinId(), business.getToken(),
						business.getTitle(), business.getBrandId(), business.getCompanyWeiboId(),
						business.getIsAllowWeixinMember(), business.getSalutatory(), business.getVoteStatus(),
						business.getTagName(), business.getTagId(), business.getId()) != -1 ? true
						: false;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}
	/**
	 * 修改数据库图片
	 * @param business
	 * @return
	 */
	public boolean updateBusinessImg(BusinessVO business){
		try{
			String brandid=business.getBrandId().toString();
			String sql=" update weixin.business set ";
			if(StringUtils.isNotBlank(business.getBigimageName())&&StringUtils.isBlank(business.getSmallimageName())){
				sql+=" bigimage_name='"+business.getBigimageName()+"' where brand_id="+brandid;
			}
			if(StringUtils.isNotBlank(business.getSmallimageName())&&StringUtils.isBlank(business.getBigimageName())){
				sql+=" smallimage_name='"+business.getSmallimageName()+"' where brand_id="+brandid;
			}
			if(StringUtils.isNotBlank(business.getBigimageName())&&StringUtils.isNotBlank(business.getSmallimageName())){
				sql+=" bigimage_name='"+business.getBigimageName()+"'";
				sql+=" ,smallimage_name='"+business.getSmallimageName()+"' where brand_id="+brandid;
			}
			log.info("sql:"+sql);
			int flag = jdbcTemplate.update(sql);
			return flag>0;
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			return false;
		}
	}
	
	public boolean updateMemberRight(BusinessVO business) {
		String sql = "update weixin.business set member_rights=? where id=?;";
		return jdbcTemplate.update(sql,
				business.getMemberRights(), business.getId()) != -1 ? true : false;
	}
}

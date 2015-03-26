package com.yazuo.weixin.es.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.yazuo.weixin.card.service.CardService;
import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.es.util.EsUtil;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.es.vo.EsRequestVO;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.CouponVO2;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;
import com.yazuo.weixin.vo.PageVO;
import com.yazuo.weixin.vo.TransWaterPage;

/**
* @ClassName MemberShipCenterServiceImpl
* @Description 会员中心serviceImpl
* @author sundongfeng@yazuo.com
* @date 2014-11-26 下午1:44:59
* @version 1.0
*/
@Service("MemberShipCenterServiceImpl")
public class MemberShipCenterServiceImpl implements MemberShipCenterService{
	private static final Log log = LogFactory.getLog("weixin");
	@Value("#{propertiesReader['imageFileUrl']}")
	private String imageFileUrl;
	@Value("#{propertiesReader['esurl']}")
	private String esurl;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat SDFYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Resource
	private CardService cardService;
	
	public MemberVO queryMemberByMsId(String brandId, String memberShipId){
		MemberVO member = new MemberVO();
		BoolFilterBuilder boolFilterBuilder = 
				FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", brandId))
				.must(FilterBuilders.termFilter("trade.id", memberShipId))
				.must(FilterBuilders.termFilter("trade.status", "1"));
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder))
		.toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"membership/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONArray hits =((JSONObject) result.get("hits")).getJSONArray("hits");
				if(hits!=null&&hits.length()>0){
					JSONObject _source = hits.getJSONObject(0).getJSONObject("_source");
					member.setPhoneNo(_source.getString("mobile"));
					String birthday= _source.getString("birthday");
					if(StringUtils.isNotEmpty(birthday) && !birthday.equals("null")){
						birthday =birthday.substring(0,10);
						member.setBirthday(sdf.parse(birthday));
					}
					member.setBirthType(_source.getString("birth_type"));
				}
			} catch (JSONException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}catch (ParseException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
		return member;
	}
	
	public List<MemberCardVO> queryMemberCardList(String brandId,Object... memberShipId){
		BoolFilterBuilder boolFilterBuilder = 
				FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", brandId))
				.must(FilterBuilders.inFilter("trade.membership_id", memberShipId))
				.must(FilterBuilders.termFilter("trade.status", "1"));
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).addSort("trade._id", SortOrder.DESC)
		.toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"card/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
		if(result!=null){
			try {
				JSONObject _hits_first = (JSONObject) result.get("hits");
				if(_hits_first!=null){
					JSONArray hits =_hits_first.getJSONArray("hits");
					if(hits!=null&&hits.length()>0){
						for(int i=0;i<hits.length();i++){
							JSONObject _source =   hits.getJSONObject(i).getJSONObject("_source");
							MemberCardVO cardInfo = new MemberCardVO();
							cardInfo.setCardNo(_source.getString("card_no"));
							cardInfo.setBrandId(_source.getString("brand_id"));
							cardInfo.setCardtypeId(_source.getString("cardtype_id"));
							if(StringUtil.isNullOrEmpty(_source.getString("store_balance"))){
								cardInfo.setStoreBalance("0");
							}else{
								cardInfo.setStoreBalance(new BigDecimal(_source.getDouble("store_balance")).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
							}
							cardInfo.setMembershipId(_source.getString("membership_id"));
							cardInfo.setIntegralAvailable(new BigDecimal(_source.getDouble("integral_available")).setScale(2, BigDecimal.ROUND_HALF_UP)+"");
							cardInfo.setStatus(_source.getString("status"));
							CardTypeVo cardTypevo = null;
							if(!StringUtil.isNullOrEmpty(_source.getString("cardtype_id"))) {
								cardTypevo = cardService.getCardType(Integer.parseInt(_source.getString("cardtype_id")));
							}
							if(cardTypevo!=null){
								cardInfo.setIsTradePassword(cardTypevo.getIsTradePassword());
								cardInfo.setCardtype(cardTypevo.getCardtype());
								cardInfo.setIcon(StringUtil.isNullOrEmpty(cardTypevo.getCardtypeIcon())? "" : imageFileUrl+cardTypevo.getCardtypeIcon());
							}else{
								cardInfo.setIsTradePassword(false);
								cardInfo.setCardtype("");
								cardInfo.setIcon("");
							}
							cardList.add(cardInfo);
						} 
					}
				}
			} catch (JSONException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
		return cardList;
	}
	
	public Map<String,List<CouponVO2>> getCardCouponList(String brandId,String cardno,String mobile){
		List<CouponVO2> cardCouponList = new ArrayList<CouponVO2>();
		List<CouponVO2> phoneCouponList = new ArrayList<CouponVO2>();
		// 组合条件((card_no ='' or card_no is null)and mobile=?)
		FilterBuilder t = FilterBuilders.andFilter(  
				FilterBuilders.orFilter(FilterBuilders.termFilter("trade.card_no", ""), FilterBuilders.missingFilter("trade.card_no")),
				FilterBuilders.termFilter("trade.mobile",mobile)  
        );
		
		BoolFilterBuilder boolFilterBuilder = 
				FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", brandId))
				.must(FilterBuilders.orFilter(t,FilterBuilders.termFilter("trade.card_no",cardno)))
				.must(FilterBuilders.termFilter("trade.status", "1"))
				.must(FilterBuilders.rangeFilter("trade.expired_date").from(formatDateToEs(new Date())))
				.must(FilterBuilders.rangeFilter("trade.available_quantity").gte(1)) ;
				
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).addSort("trade.id", SortOrder.DESC)
		.toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"coupon/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONObject _hits_first = (JSONObject) result.get("hits");
				if(_hits_first!=null){
					JSONArray hits =_hits_first.getJSONArray("hits");
					if(hits!=null&&hits.length()>0){
						for(int i=0;i<hits.length();i++){
							JSONObject _source =   hits.getJSONObject(i).getJSONObject("_source");
							CouponVO2 coupon = new CouponVO2();
							coupon.setName(_source.getString("name"));
							coupon.setAvailableQuantity(Integer.parseInt(_source.getString("available_quantity")));
							coupon.setId(_source.getLong("id"));
							coupon.setBatchNo(_source.getInt("batch_no"));
							coupon.setStartDate(_source.getString("start_date").substring(0, 10));
							coupon.setExpiredDate(_source.getString("expired_date").substring(0, 10));
							if(StringUtils.isEmpty(_source.getString("card_no")) || _source.getString("card_no").equals("null")){
								phoneCouponList.add(coupon);
							}else{
								cardCouponList.add(coupon);
							}
						}
					}
				}
			} catch (JSONException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
		Map<String,List<CouponVO2>> couponList = new HashMap<String,List<CouponVO2>>();
		couponList.put("card", cardCouponList);
		couponList.put("mobile", phoneCouponList);
		return couponList;
	}
	
	
	@Override
	public List<Object> queryRemainOrderCouponByEs(Integer brandId, Object... couponIdStr) {
		List<Object> resultList = new ArrayList<Object>();
		BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter()
			.mustNot(FilterBuilders.termFilter("trade.available_quantity", 0))
			.must(FilterBuilders.termFilter("trade.brand_id", brandId))
			.must(FilterBuilders.inFilter("trade.id", couponIdStr));
		String esrequest = new SearchRequestBuilder(null).setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"coupon/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONObject _hits_first = (JSONObject) result.get("hits");
				if(_hits_first!=null){
					JSONArray hits =_hits_first.getJSONArray("hits");
					if(hits!=null&&hits.length()>0){
						for(int i=0;i<hits.length();i++){
							JSONObject _source =   hits.getJSONObject(i).getJSONObject("_source");
							resultList.add(_source.getLong("id"));
						}
					}
				}
			} catch (JSONException e) {
				log.error("code happen error.",e);
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	/**将普通的时间转换成ES搜索引擎中时间格式*/
	private String formatDateToEs(Date date) {
		return sdf.format(date)+"T00:00:00.000+08:00"; 
	}
	
	/** 查询会员ids*/
	public Set<Object> queryMemberShipsByMobile(String brandId, String mobile) {
		Set<Object> set = new HashSet();
		MemberVO member = new MemberVO();
		BoolFilterBuilder boolFilterBuilder = 
				FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", brandId))
				.must(FilterBuilders.termFilter("trade.mobile", mobile))
				.must(FilterBuilders.termFilter("trade.status", "1"));
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"membership/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONArray hits =((JSONObject) result.get("hits")).getJSONArray("hits");
				if(hits!=null&&hits.length()>0){
					for(int i=0;i<hits.length();i++){
						JSONObject _source =   hits.getJSONObject(i).getJSONObject("_source");
						set.add(_source.getLong("id"));
					}
				}
			} catch (JSONException e) {
				log.error("queryMemberShipsByMobile error.",e);
				e.printStackTrace();
			}
		}
		return set;
	}
	public static void main(String[] args) {
		String[] transcode = new String[]{"0002","1002","1006","2002","2006","3001","3002","3005"};
		BoolFilterBuilder boolFilterBuilder = 
				FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", 15))
				.must(FilterBuilders.termFilter("trade.membership_id", 689))
				.must(FilterBuilders.inFilter("trade.trans_code", transcode))
				.must(FilterBuilders.termFilter("trade.card_no", "6201300513128778"))
				.must(FilterBuilders.termFilter("trade.reversedflag", false))
				.must(FilterBuilders.termFilter("trade.revocationflag", false)) 
				;
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder))
		.addAggregation(AggregationBuilders.terms("group_by_master_id").field("master_id").size(0).order(Terms.Order.term(false))
				.subAggregation(AggregationBuilders.terms("group_by_trans_merchant").field("trans_merchant")
							.subAggregation(AggregationBuilders.filter("total_amount").filter(FilterBuilders.existsFilter("trade.amount"))
									.subAggregation(AggregationBuilders.sum("total").field("trade.amount")))
							.subAggregation(AggregationBuilders.filter("cash_trans_code").filter(FilterBuilders.termFilter("trade.trans_code","0002"))
									.subAggregation(AggregationBuilders.sum("cash").field("trade.amount")))	
							.subAggregation(AggregationBuilders.filter("integral_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","1002","1006"))
									.subAggregation(AggregationBuilders.sum("integral").field("trade.amount")))
							.subAggregation(AggregationBuilders.filter("store_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","2002","2006"))
									.subAggregation(AggregationBuilders.sum("store").field("trade.amount")))
							.subAggregation(AggregationBuilders.filter("coupon_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","3002","3001"))
									.subAggregation(AggregationBuilders.sum("coupon").field("trade.amount")))
				.subAggregation(AggregationBuilders.min("min_trans_time").field("trade.trans_time"))		
				)).setSize(0).setFrom(0)
		.toString();
		
		System.out.println(esrequest);
		Date date = new Date(1392862051799l);// 将指定毫秒值封装成Date对象。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println( sdf.format(date));
		
		
		String esrequest1 = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).addFields("trade.master_id")
		.addAggregation(AggregationBuilders.terms("group_by_master_id").field("trade.master_id").size(0)
		).toString();
		System.out.println(esrequest1);
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl("http://192.168.236.73:9200/"+"trans_water/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONArray hits = (JSONArray) result.getJSONObject("aggregations").getJSONObject("group_by_master_id").get("buckets");
				if(hits!=null&&hits.length()>0){
					 log.info(hits.length());
				}
			} catch (JSONException e) {
				log.error("queryTransWaterCount error.",e);
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 通过搜索引擎查询交易流水
	 */
	public Map<String, Object> queryTransWaterRecordByEs(String cardno,String memberShipId, String brandId, PageVO page) {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.YEAR, -1);
		String begintime = sdf.format(cal.getTime())+"T00:00:00.000+08:00";
		int allcount = queryTransWaterCount(cardno,memberShipId,brandId);
		int pagesize = page.getPagesize();// 每页条数
		int allpage = 0;// 数据总条数
		if (allcount % pagesize == 0) {
			allpage = allcount / pagesize;// 总页数
		} else {
			allpage = (allcount / pagesize) + 1;
		}
		page.setAllcount(allcount);
		page.setAllpage(allpage);
		Map<String, Object> resmap = new HashMap<String, Object>();
		List<TransWaterPage> list= new ArrayList<TransWaterPage>();
		Set<Integer> masterIds=new HashSet<Integer>();
		Set<String> transMerchantIds = new HashSet<String>();
		resmap.put("page", page);
		if(allcount>0){
			String[] transcode = new String[]{"0002","1002","1006","2002","2006","3001","3002","3005"};
			BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter()
					.must(FilterBuilders.termFilter("trade.brand_id", brandId))
//					.must(FilterBuilders.termFilter("trade.membership_id", memberShipId))
					.must(FilterBuilders.termFilter("trade.card_no", cardno))
					.must(FilterBuilders.inFilter("trade.trans_code", transcode))
					.must(FilterBuilders.rangeFilter("trade.trans_time").from(begintime))
					.must(FilterBuilders.termFilter("trade.reversedflag", false))
					.must(FilterBuilders.termFilter("trade.revocationflag", false)) ;
			String esrequest = new SearchRequestBuilder(null)
			.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder))
			.addAggregation(AggregationBuilders.terms("group_by_master_id").field("master_id").size(0).order(Terms.Order.term(false))
					.subAggregation(AggregationBuilders.terms("group_by_trans_merchant").field("trans_merchant")
								.subAggregation(AggregationBuilders.filter("total_amount").filter(FilterBuilders.existsFilter("trade.amount"))
										.subAggregation(AggregationBuilders.sum("total").field("trade.amount")))
								.subAggregation(AggregationBuilders.filter("cash_trans_code").filter(FilterBuilders.termFilter("trade.trans_code","0002"))
										.subAggregation(AggregationBuilders.sum("cash").field("trade.amount")))	
								.subAggregation(AggregationBuilders.filter("integral_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","1002","1006"))
										.subAggregation(AggregationBuilders.sum("integral").field("trade.amount")))
								.subAggregation(AggregationBuilders.filter("store_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","2002","2006"))
										.subAggregation(AggregationBuilders.sum("store").field("trade.amount")))
								.subAggregation(AggregationBuilders.filter("coupon_trans_code").filter(FilterBuilders.inFilter("trade.trans_code","3002","3001"))
										.subAggregation(AggregationBuilders.sum("coupon").field("trade.amount")))
					.subAggregation(AggregationBuilders.min("min_trans_time").field("trade.trans_time"))		
					)).setSize(0).setFrom(0)
			.toString();
			EsRequestVO esRequestVO=new EsRequestVO();
			esRequestVO.setRequestJson(esrequest);
			esRequestVO.setUrl(esurl.trim()+"trans_water/_search/");
			JSONObject result = EsUtil.doEsRequest(esRequestVO);
			if(result!=null){
				try {
					JSONArray hits = (JSONArray) result.getJSONObject("aggregations").getJSONObject("group_by_master_id").get("buckets");
					if(hits!=null&&hits.length()>0){
						for(int i=0;i<hits.length();i++){
							TransWaterPage mapeve= new TransWaterPage();
							JSONObject eachGroup = hits.getJSONObject(i);
							int masterId = eachGroup.getInt("key");//masterId
							mapeve.setId(masterId);
							masterIds.add(masterId);
							JSONArray buckets = (JSONArray) eachGroup.getJSONObject("group_by_trans_merchant").get("buckets");
							if(buckets!=null&&buckets.length()>0){
								JSONObject bucket = buckets.getJSONObject(0);
								String transMerchant = bucket.getString("key");//交易门店id
								mapeve.setTransMerchant(transMerchant);
								transMerchantIds.add(transMerchant);
								double total = bucket.getJSONObject("total_amount").getJSONObject("total").getDouble("value");
								double integral = bucket.getJSONObject("integral_trans_code").getJSONObject("integral").getDouble("value");
								double cash = bucket.getJSONObject("cash_trans_code").getJSONObject("cash").getDouble("value");
								double store = bucket.getJSONObject("store_trans_code").getJSONObject("store").getDouble("value");
								double coupon = bucket.getJSONObject("coupon_trans_code").getJSONObject("coupon").getDouble("value");
								mapeve.setTotal(total);
								mapeve.setIntegral(integral);
								mapeve.setCash(cash);
								mapeve.setCoupon(coupon);
								mapeve.setStore(store);
								long time = bucket.getJSONObject("min_trans_time").getLong("value");
								Date date = new Date(time);// 将指定毫秒值封装成Date对象。
								mapeve.setTransTime(SDFYMDHMS.format(date));//交易时间
								long nowtime = new Date().getTime();
								boolean out = (nowtime-time)/1000>((long)(30*24*60*60));
								mapeve.setOut(out);//设置是否过期
								list.add(mapeve);
							}
						}
					}
				} catch (JSONException e) {
					log.error("queryTransWaterCount error.",e);
					e.printStackTrace();
				}
			}
			if(masterIds.size()>0||transMerchantIds.size()>0){
				Map<Integer, TransWaterPage> eva = cardService.queryEvaluate(Integer.parseInt(brandId), masterIds);
				Map<String,String> names=cardService.queryMerchant(transMerchantIds);
				for(TransWaterPage tw:list){
					TransWaterPage tweva = eva.get(tw.getId());
					String transName = names.get(tw.getTransMerchant());
					if(StringUtils.isNotEmpty(transName)){
						tw.setTransMerchantName(transName);
					}
					if(tweva!=null){
						tw.setContent(tweva.getContent());
						tw.setScore(tweva.getScore());
						tw.setEvaluateId(tweva.getEvaluateId());
					}
				}
			}
		}
		resmap.put("list", list);
		return resmap;
	}
	/**
	 * 查询交易总数
	 */
	public int queryTransWaterCount(String cardno,String memberShipId, String brandId) {
		Calendar cal = Calendar.getInstance();
		cal.add(cal.YEAR, -1);
		String begintime = sdf.format(cal.getTime())+"T00:00:00.000+08:00";
		int count  =0 ;
		String[] transcode = new String[]{"0002","1002","1006","2002","2006","3001","3002","3005"};
		BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter()
				.must(FilterBuilders.termFilter("trade.brand_id", brandId))
//				.must(FilterBuilders.termFilter("trade.membership_id", memberShipId))
				.must(FilterBuilders.termFilter("trade.card_no", cardno))
				.must(FilterBuilders.inFilter("trade.trans_code", transcode))
				.must(FilterBuilders.rangeFilter("trade.trans_time").from(begintime))
				.must(FilterBuilders.termFilter("trade.reversedflag", false))
				.must(FilterBuilders.termFilter("trade.revocationflag", false)) 
				;
		String esrequest = new SearchRequestBuilder(null)
		.setQuery(QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder)).addFields("trade.master_id")
		.addAggregation(AggregationBuilders.terms("group_by_master_id").field("trade.master_id").size(0)
		).toString();
		EsRequestVO esRequestVO=new EsRequestVO();
		esRequestVO.setRequestJson(esrequest);
		esRequestVO.setUrl(esurl.trim()+"trans_water/_search/");
		JSONObject result = EsUtil.doEsRequest(esRequestVO);
		if(result!=null){
			try {
				JSONArray hits = (JSONArray) result.getJSONObject("aggregations").getJSONObject("group_by_master_id").get("buckets");
				if(hits!=null&&hits.length()>0){
					 count =hits.length();
				}
			} catch (JSONException e) {
				log.error("queryTransWaterCount error.",e);
				e.printStackTrace();
			}
		}
		return count;
	}
	
}

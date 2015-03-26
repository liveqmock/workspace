package com.yazuo.weixin.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessManagerVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.Story;
import com.yazuo.weixin.vo.SubbranchVO;

public interface WeixinManageService {
	public List<BusinessVO> getAllBusiness();

	public Map<String, Object> getBusinessByPage(int page, int pagesize);
	
	public Map<String, Object> getBusinessByTitle(String title,int page, int pagesize);

	public BusinessVO getBusinessByBrandId(Integer brandId);
	
	public BusinessVO getBusinessByBrandId(Integer brandId,boolean contain);

	public BusinessVO getBusinessByWeixinId(String weixinId);

	public Map<String, Object> getSubbranchListByBrandId(Integer brandId,
			int page, int pagesize);

	public Map<String, Object> getDishesListByBrandId(Integer brandId,
			int page, int pagesize);

	public Map<String, Object> getPreferenceListByBrandId(Integer brandId,
			int page, int pagesize);

	public Map<String, Object> getAutoreplyListByBrandId(Integer brandId,
			int page, int pagesize);

	public Map<String, Object> getBusinessManagerListByBrandId(Integer brandId,
			int page, int pagesize);

	public BusinessVO getBusinessById(Integer id);

	public boolean saveBusiness(BusinessVO business);

	public boolean modifyBusiness(BusinessVO business);
	
	public boolean updateBusiness(BusinessVO business);

	public boolean deleteBusiness(BusinessVO business);

	public DishesVO getDishesById(Integer id);

	public boolean saveDishes(DishesVO dishes);

	public boolean modifyDishes(DishesVO dishes);

	public boolean deleteDishes(DishesVO dishes);

	public PreferenceVO getPreferenceById(Integer id);

	public boolean savePreference(PreferenceVO preference);

	public boolean modifyPreference(PreferenceVO preference);

	public boolean deletePreference(PreferenceVO preference);

	public SubbranchVO getSubbranchById(Integer id);

	public boolean saveSubbranch(SubbranchVO subbranch);

	public boolean modifySubbranch(SubbranchVO subbranch);

	public boolean deleteSubbranch(SubbranchVO subbranch);

	public int importSubbranch(BusinessVO business);

	public void modifyImage(BusinessVO business);

	public boolean saveBusinessManager(BusinessManagerVO businessManager);

	public boolean deleteBusinessManager(BusinessManagerVO businessManager);

	public boolean saveAutoreply(AutoreplyVO autoreply);
	
	public Map<String, Object> saveManyAutoreply(String [] keywords, Integer brandId);
	
	public List<String> getSettingAutoreply(Integer brandId);
	
	public Map<String, Object> getSettingAutoreplyByMenuKey(Integer brandId, String keyWord);
	
	//保存图片文件
	public boolean saveImageConfig(ImageConfigVO imageConfig);
	//删除图片
	public boolean delImageConfig(ImageConfigVO imageConfig);
	//查询图片
	public List<ImageConfigVO>getImageConfigVOListByAutoply(AutoreplyVO autoreply);
	public boolean updateAutoreply(AutoreplyVO autoreply);
	/**根据id获取信息*/
	public AutoreplyVO getAutoreply(Integer id);

	public boolean deleteAutoreply(AutoreplyVO autoreply);

	public int countAutoreplyNotKeyWord(Integer brandId);

	public void loadAutoreplyData();
	
	public boolean saveActivityConfig(ActivityConfigVO activityConfig);
	
	public boolean modifyActivityConfig(ActivityConfigVO activityConfig);
	
	public boolean deleteActivityConfig(ActivityConfigVO activityConfig);
	
	/**
	 * 创建一二级菜单
	 * @param json
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	public String createButton(String json,String appId,String appSecret) throws Exception ;
	
	public boolean saveStory(Story story);
	
	public boolean updateStory(Story story);
	
	public Story getStoryByBrandId(Integer brandId);
	
	/**根据brandId获取品牌故事讲删除的剔除*/
	public Story getByBrandId(Integer brandId);

	/**获取菜品列表*/
	public Object getDishesList (Integer merchantId, String weixinId, String key);
	
	/**获取某个菜品详细*/
	public JSONObject getDishesDetail (String input, String key);
	
	/**取用户点赞的流水记录*/
	public List<Map<String, Object>> getUserPariseRecord(Integer brandId, String weixinId, Integer dishesId);
	
	/**添加用户点赞的流水记录*/
	public boolean addPariseRecord (Integer merchant_id, Integer dishesId, String weixinId);
	
	/**用户点赞更新菜品点赞数量*/
	public boolean updateDishesParise (String input, String key);
	
	/**根据门店id获取所在行政区域*/
	public  Map<String, Object> getAllArea(Integer merchantId);
	
	/**根据区域查询该区域下的所有门店*/
	public Object getSubbrachByOrder(Integer areaId, Integer merchantId);
	
	/**门店排序*/
	public JSONObject getSubbrachByNewOrder (Integer merchantId, Integer pageIndex,Integer pageSize, Integer areaId, String source, String merchantName);
	
	/**根据经纬度获取门店并按远近排序*/
	public JSONObject getSubbrachByCoordinatesOrder(Integer merchantId, String longitude, String latitude, String province, Integer pageIndex, Integer pageSize);
	
	/**根据id更新会员权益*/
	public boolean updateMemberRight(BusinessVO business);
	
	/**商户分页*/
	public Pagination<BusinessVO> getBusinessPageByOrder(int currentPage, BusinessVO business);
}

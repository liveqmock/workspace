package com.yazuo.weixin.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.weixin.dao.ActivityDao;
import com.yazuo.weixin.dao.AutoreplyDao;
import com.yazuo.weixin.dao.BusinessDao;
import com.yazuo.weixin.dao.BusinessManagerDao;
import com.yazuo.weixin.dao.DishesDao;
import com.yazuo.weixin.dao.ImageConfigDao;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.dao.PreferenceDao;
import com.yazuo.weixin.dao.StoryDao;
import com.yazuo.weixin.dao.SubbranchDao;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.SaveOrUpdateMerchantTagService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.FastDFSUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.PageInfo;
import com.yazuo.weixin.util.Pagination;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.util.WebClientDevWrapper;
import com.yazuo.weixin.vo.ActivityConfigVO;
import com.yazuo.weixin.vo.AutoreplyVO;
import com.yazuo.weixin.vo.BusinessManagerVO;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.DishesVO;
import com.yazuo.weixin.vo.ImageConfigVO;
import com.yazuo.weixin.vo.PreferenceVO;
import com.yazuo.weixin.vo.Story;
import com.yazuo.weixin.vo.SubbranchVO;

@Scope("prototype")
@Service
public class WeixinManageServiceImpl implements WeixinManageService {
	@Value("#{propertiesReader['imageLocationPath']}")
	private String imageLocationPath;
	@Value("#{propertiesReader['poiCompanyStore']}")
	private String poiCompanyStore;
	@Value("#{propertiesReader['smsAddress']}")
	private String smsAddress;
	@Value("#{propertiesReader['smsUsername']}")
	private String smsUsername;
	
	@Value("#{propertiesReader['dishesListUrl']}")
	private String dishesListUrl; // 菜品列表接口路径
	
	@Value("#{propertiesReader['dishesDetailUrl']}")
	private String dishesDetailUrl; // 菜品详情接口路径
	
	@Value("#{propertiesReader['dishesPrasieUrl']}")
	private String dishesPrasieUrl; // 菜品点赞接口路径
	
	@Value("#{propertiesReader['memchantListUrl']}")
	private String memchantListUrl; //门店列表接口路径
	
	@Value("#{propertiesReader['zoneListUrl']}")
	private String zoneListUrl; //行政区划接口路径
	
	@Value("#{propertiesReader['caFileIp']}")
	private String dishesPicPAth; // 菜品图片地址
	@Value("#{propertiesReader['newPictureUrl']}")
	private String newDishesPicUrl; // 新菜品图片地址
	
	@Value("#{propertiesReader['merchantSortUrl']}")
	private String merchantSortUrl; // 门店排序（按区域排序）
	
	@Value("#{propertiesReader['merchantSortByCoordinatesUrl']}")
	private String merchantSortByCoordinatesUrl; // 根据经纬度查询门店且按距离排序
	@Value("#{propertiesReader['querySubbrachByMerchantName']}")
	private String querySubbrachByMerchantName; // 根据输入的门店查询接口地址
	
	@Value("#{propertiesReader['dfsFilePath']}")
	private String dfsFilePath; // 图片服务器地址
	
	@Autowired
	private SaveOrUpdateMerchantTagService saveOrUpdateMerchantTagService;

	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private SubbranchDao subbranchDao;
	@Autowired
	private DishesDao dishesDao;
	@Autowired
	private PreferenceDao preferenceDao;
	@Autowired
	private BusinessManagerDao businessManagerDao;
	@Autowired
	private AutoreplyDao autoreplyDao;
	@Autowired
	private ActivityDao acvivityDao;
	@Autowired
	private StoryDao storyDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ImageConfigDao imageConfigDao;
	Logger log = Logger.getLogger(this.getClass());

	private final static String TAG_TYPE_ = "6";

	@Override
	public List<BusinessVO> getAllBusiness() {
		// TODO Auto-generated method stub
		return businessDao.getAllBusiness();
	}

	@Override
	public Map<String, Object> getBusinessByPage(int page, int pagesize) {
		return businessDao.getBusinessByPage(page, pagesize);
	}

	@Override
	public Map<String, Object> getBusinessByTitle(String title, int page, int pagesize) {
		return businessDao.getBusinessByTitle(title, page, pagesize);
	}

	@Override
	public BusinessVO getBusinessByBrandId(Integer brandId) {
		BusinessVO business = businessDao.getBusinessByBrandId(brandId);
		if (business == null) {
			return null;
		}
		business.setDishesList(dishesDao.getDishesListByBrandId(business.getBrandId()));
		business.setPreferenceList(preferenceDao.getPreferenceListByBrandId(business.getBrandId()));
		business.setSubbranchList(subbranchDao.getSubbranchListByBrandId(business.getBrandId()));
		business.setBusinessManagerList(businessManagerDao.getBusinessManagerListByBrandId(business.getBrandId()));
		// 新增加的
		List<AutoreplyVO> autoreplyList = autoreplyDao.getAutoreplyListByBrandId(business.getBrandId());// 获取该商家所有的自动回复
		if (autoreplyList != null) {
			for (AutoreplyVO autoreplyVO : autoreplyList) {
				List<ImageConfigVO> imageConfigList = imageConfigDao.getImageConfigVOListByAutoply(autoreplyVO);
				autoreplyVO.setImageConfigList(imageConfigList);
			}
		}
		business.setAutoreplyList(autoreplyList);
		business.setActivityConfigList(acvivityDao.getActivityConfigListByBrandId(business.getBrandId()));
		business.setStory(storyDao.getStoryByBrandId(business.getBrandId()));
		return business;
	}

	@Override
	public BusinessVO getBusinessByBrandId(Integer brandId, boolean contain) {
		BusinessVO business = businessDao.getBusinessByBrandId(brandId);
		return business;
	}

	// 要有变动
	@Override
	public BusinessVO getBusinessById(Integer id) {
		BusinessVO business = businessDao.getBusinessById(id);
		if (business == null) {
			return null;
		}
		business.setDishesList(dishesDao.getDishesListByBrandId(business.getBrandId()));
		business.setPreferenceList(preferenceDao.getPreferenceListByBrandId(business.getBrandId()));
		business.setSubbranchList(subbranchDao.getSubbranchListByBrandId(business.getBrandId()));
		business.setBusinessManagerList(businessManagerDao.getBusinessManagerListByBrandId(business.getBrandId()));
		// 新增加的
		List<AutoreplyVO> autoreplyList = autoreplyDao.getAutoreplyListByBrandId(business.getBrandId());// 获取该商家所有的自动回复
		if (autoreplyList != null) {
			for (AutoreplyVO autoreplyVO : autoreplyList) {
				List<ImageConfigVO> imageConfigList = imageConfigDao.getImageConfigVOListByAutoply(autoreplyVO);
				autoreplyVO.setImageConfigList(imageConfigList);
			}
		}
		business.setAutoreplyList(autoreplyList);
		business.setActivityConfigList(acvivityDao.getActivityConfigListByBrandId(business.getBrandId()));
		business.setStory(storyDao.getStoryByBrandId(business.getBrandId()));
		return business;
	}

	@Override
	public BusinessVO getBusinessByWeixinId(String weixinId) {
		BusinessVO business = businessDao.getBusinessByWeixinId(weixinId);
		business.setDishesList(dishesDao.getDishesListByBrandId(business.getBrandId()));
		business.setPreferenceList(preferenceDao.getPreferenceListByBrandId(business.getBrandId()));
		business.setSubbranchList(subbranchDao.getSubbranchListByBrandId(business.getBrandId()));
		business.setBusinessManagerList(businessManagerDao.getBusinessManagerListByBrandId(business.getBrandId()));
		// 新增加的
		List<AutoreplyVO> autoreplyList = autoreplyDao.getAutoreplyListByBrandId(business.getBrandId());// 获取该商家所有的自动回复
		if (autoreplyList != null) {
			for (AutoreplyVO autoreplyVO : autoreplyList) {
				List<ImageConfigVO> imageConfigList = imageConfigDao.getImageConfigVOListByAutoply(autoreplyVO);
				autoreplyVO.setImageConfigList(imageConfigList);
			}
		}
		business.setAutoreplyList(autoreplyList);
		business.setActivityConfigList(acvivityDao.getActivityConfigListByBrandId(business.getBrandId()));
		business.setStory(storyDao.getStoryByBrandId(business.getBrandId()));
		return business;
	}

	@Override
	public Map<String, Object> getSubbranchListByBrandId(Integer brandId, int page, int pagesize) {
		return subbranchDao.getSubbranchListByBrandId(brandId, page, pagesize);
	}

	@Override
	public Map<String, Object> getDishesListByBrandId(Integer brandId, int page, int pagesize) {
		return dishesDao.getDishesListByBrandId(brandId, page, pagesize);
	}

	@Override
	public Map<String, Object> getPreferenceListByBrandId(Integer brandId, int page, int pagesize) {
		return preferenceDao.getPreferenceListByBrandId(brandId, page, pagesize);
	}

	// 待修改
	@Override
	public Map<String, Object> getAutoreplyListByBrandId(Integer brandId, int page, int pagesize) {
		return autoreplyDao.getAutoreplyListByBrandId(brandId, page, pagesize);
	}
	public int countAutoreplyNotKeyWord(Integer brandId) {
		return autoreplyDao.countAutoreplyNotKeyWord(brandId);
	}
	@Override
	public Map<String, Object> getBusinessManagerListByBrandId(Integer brandId, int page, int pagesize) {
		return businessManagerDao.getBusinessManagerListByBrandId(brandId, page, pagesize);
	}

	@Override
	public boolean saveBusiness(BusinessVO business) {

		String tagName = business.getTagName();
		Integer brandId = business.getBrandId();

		Integer tagId = null;
		JSONObject res = null;
		if (tagName != null && tagName.trim().length() > 0) {
			// 添加或者修改商户标签信息
			try {
				res = saveOrUpdateMerchantTagService.saveOrUpdateMerchantTagService(brandId, null, tagName, TAG_TYPE_);
			} catch (Exception e) {
				log.info("[添加或修改商户标签信息]服务调用失败");
				e.printStackTrace();
			}
			if (res == null) {
				tagName = null;
				log.info("[添加或修改商户标签信息]服务调用失败");
			} else {
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				if (success) {// 成功
					String id = res.getString("id"); // 商户标签id
					tagId = Integer.valueOf(id);
				} else {// 失败
					tagName = null;
					log.info("[添加或修改商户标签信息]服务调用失败，" + message);
				}
			}
		}
		business.setTagName(tagName);
		business.setTagId(tagId);
		
		// 图片放图片服务器上
		String bigResult =new FastDFSUtil().uploadImage(business.getBigImage());
		business.setBigimageName(bigResult); // 大图
		
		String smallResult =new FastDFSUtil().uploadImage(business.getSmallImage());
		business.setSmallimageName(smallResult); // 小图
//		String path = this.getClass().getClassLoader().getResource("").getPath();
//		path = path.split("WEB-INF")[0];
//		path = path + imageLocationPath + File.separator + business.getBrandId().toString();
//		try {
//			SaveFileFromInputStream(business.getBigImage().getInputStream(), path, business.getBigimageName());
//			SaveFileFromInputStream(business.getSmallImage().getInputStream(), path, business.getSmallimageName());
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		boolean flag = businessDao.saveBusiness(business);
		loadAutoreplyData();
		return flag;
	}

	@Override
	public boolean updateBusiness(BusinessVO business) {
		// 图片放图片服务器上
		String bigResult =new FastDFSUtil().uploadImage(business.getBigImage());
		business.setBigimageName(bigResult); // 大图
		
		String smallResult =new FastDFSUtil().uploadImage(business.getSmallImage());
		business.setSmallimageName(smallResult); // 小图
//		String path = this.getClass().getClassLoader().getResource("").getPath();
//		path = path.split("WEB-INF")[0];
//		path = path + imageLocationPath + File.separator + business.getBrandId().toString();
//		try {
//			if (business.getBigImage() != null)
//				SaveFileFromInputStream(business.getBigImage().getInputStream(), path, business.getBigimageName());
//			if (business.getSmallImage() != null)
//				SaveFileFromInputStream(business.getSmallImage().getInputStream(), path, business.getSmallimageName());
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		boolean flag = businessDao.updateBusiness(business);
		loadAutoreplyData();
		return flag;
	}

	@Override
	public boolean modifyBusiness(BusinessVO business) {
		boolean flag = false;
		Integer tagId = business.getTagId();
		String tagName = business.getTagName();
		Integer merchantId = business.getBrandId();

		JSONObject res = null;
		if (tagName != null && tagName.trim().length() > 0) {
			// 添加或者修改商户标签信息
			try {
				res = saveOrUpdateMerchantTagService.saveOrUpdateMerchantTagService(merchantId, tagId, tagName, TAG_TYPE_);
			} catch (Exception e) {
				log.info("[添加或修改商户标签信息]服务调用失败");
				e.printStackTrace();
			}
			if (res == null) {
				tagId = null;//标示tagId和tagName不进行修改
				log.info("[添加或修改商户标签信息]服务调用失败");
			} else {
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				if (success) {// 成功
					String tid = res.getString("id"); // 商户标签id
					tagId = Integer.valueOf(tid);
					business.setTagName(tagName);
					business.setTagId(tagId);
				} else {// 失败
					business.setTagId(null);// 用于DAO层判断tagId和tagName不进行修改
					log.info("[添加或修改商户标签信息]服务调用失败，" + message);
				}
			}
		}
		business.setTagName(tagName);
		business.setTagId(tagId);
		
		flag = businessDao.updateBusiness(business);
		return flag;
	}

	@Override
	public boolean deleteBusiness(BusinessVO business) {
		subbranchDao.deleteSubbranchByBrandId(business.getBrandId());
		dishesDao.deleteDishesByBrandId(business.getBrandId());
		preferenceDao.deletePreferenceByBrandId(business.getBrandId());
		businessManagerDao.deleteBusinessManagerByBrandId(business.getBrandId());
		autoreplyDao.deleteAutoreplyByBrandId(business.getBrandId());
		acvivityDao.deleteActivityConfigByBrandId(business.getBrandId());
		return businessDao.deleteBusiness(business);
	}

	@Override
	public DishesVO getDishesById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveDishes(DishesVO dishes) {
		return dishesDao.insertDishes(dishes);
	}

	@Override
	public boolean modifyDishes(DishesVO dishes) {
		// TODO Auto-generated method stub
		return dishesDao.updateDishes(dishes);
	}

	@Override
	public boolean deleteDishes(DishesVO dishes) {
		// TODO Auto-generated method stub
		return dishesDao.deleteDishes(dishes);
	}

	@Override
	public PreferenceVO getPreferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean savePreference(PreferenceVO preference) {
		return preferenceDao.insertPreference(preference);
	}

	@Override
	public boolean modifyPreference(PreferenceVO preference) {
		return preferenceDao.updatePreference(preference);
	}

	@Override
	public boolean deletePreference(PreferenceVO preference) {
		return preferenceDao.deletePreference(preference);
	}

	@Override
	public SubbranchVO getSubbranchById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveSubbranch(SubbranchVO subbranch) {
		return subbranchDao.insertSubbranch(subbranch);
	}

	@Override
	public boolean modifySubbranch(SubbranchVO subbranch) {
		return subbranchDao.updateSubbranch(subbranch);
	}

	@Override
	public boolean deleteSubbranch(SubbranchVO subbranch) {
		return subbranchDao.deleteSubbranch(subbranch);
	}

	@Override
	public int importSubbranch(BusinessVO business) {
		JSONObject jo = null;
		try {
			jo = getJsonFromWeiboId(business.getCompanyWeiboId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<SubbranchVO> subbranchList = getSubbranchListFromJson(jo);
		return subbranchDao.importSubbranchInfo(business, subbranchList);
	}

	@Override
	public boolean saveBusinessManager(BusinessManagerVO businessManager) {
		List<BusinessManagerVO> businessManagerList = businessManagerDao.getBusinessManagerListByPhoneNo(businessManager);
		boolean flag = false;
		for (BusinessManagerVO businessManager1 : businessManagerList) {
			if (!businessManager1.getIsDelete())
				flag = true;
		}
		if (flag) {
			return false;
		} else {
			return businessManagerDao.saveBusinessManager(businessManager);
		}

	}

	@Override
	public boolean deleteBusinessManager(BusinessManagerVO businessManager) {
		return businessManagerDao.deleteBusinessManager(businessManager);
	}

	@Override
	public void modifyImage(BusinessVO business) {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.split("WEB-INF")[0];
		path = path + imageLocationPath + File.separator + business.getBrandId().toString();
		try {
			if (business.getBigImage() != null&&!business.getBigImage().isEmpty()) {
				// 图片放图片服务器上
				String bigResult =new FastDFSUtil().uploadImage(business.getBigImage());
				business.setBigimageName(bigResult);
				
//				SaveFileFromInputStream(business.getBigImage().getInputStream(), path, business.getBrandId().toString()
//						+"_big.jpg");
//				business.setBigimageName(business.getBrandId().toString()+"_big.jpg");
			}
			if (business.getSmallImage() != null&&!business.getSmallImage().isEmpty()) {
				//图片放图片服务器上
				String samllResult =new FastDFSUtil().uploadImage(business.getSmallImage());
				business.setSmallimageName(samllResult);
				
//				SaveFileFromInputStream(business.getSmallImage().getInputStream(), path, business.getBrandId().toString()
//						+"_small.jpg");
//				business.setSmallimageName( business.getBrandId().toString()+"_small.jpg");
			}
			boolean flag = businessDao.updateBusinessImg(business);
			log.info("更新表中大小图成功:"+flag);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

	}

	@Override
	public boolean saveAutoreply(AutoreplyVO autoreply) {
		return autoreplyDao.insertAutoreply(autoreply);
	}

	@Override
	// 保存图片到指定的服务器地址
	public boolean saveImageConfig(ImageConfigVO imageConfig) {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.split("WEB-INF")[0];
		path = path + imageLocationPath + File.separator + imageConfig.getBrandId().toString();
		try {
			MultipartFile image = imageConfig.getImage();
			
			// 上传到图片服务器
			String fileAdrress = new FastDFSUtil().uploadImage(image);
			imageConfig.setImageName(fileAdrress);
			
//			String imageName = imageConfig.getImageName();
//			if (image != null) {
//				SaveFileFromInputStream(image.getInputStream(), path, imageName);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return imageConfigDao.insertImageConifg(imageConfig);
	}

	public void SaveFileFromInputStream(InputStream stream, String path, String filename) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			FileOutputStream fs = new FileOutputStream(path + File.separator + filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 删除图片
	@Override
	public boolean delImageConfig(ImageConfigVO imageConfig) {

		return imageConfigDao.deleteImageConfig(imageConfig);
	}

	@Override
	public boolean updateAutoreply(AutoreplyVO autoreply) {

		return autoreplyDao.updateAutoreply(autoreply);
	}

	@Override
	public AutoreplyVO getAutoreply(Integer id) {
		return autoreplyDao.getAutoreplyById(id);
	}

	@Override
	public boolean deleteAutoreply(AutoreplyVO autoreply) {
		return autoreplyDao.deleteAutoreply(autoreply);
	}

	// 查询图片
	@Override
	public List<ImageConfigVO> getImageConfigVOListByAutoply(AutoreplyVO autoreply) {
		return imageConfigDao.getImageConfigVOListByAutoply(autoreply);
	}

	// 将自动回复中回复类型为"news"的注入进去
	@Override
	public void loadAutoreplyData() {
		if (AutoreplyData.getMap() == null) {
			AutoreplyData.setMap(new HashMap<String, List<AutoreplyVO>>());
		}
		AutoreplyData.getMap().clear();
		// 查询所有企业
		List<BusinessVO> businessList = businessDao.getAllBusiness();
		for (BusinessVO business : businessList) {
			if (!business.getIsDelete()) {
				List<AutoreplyVO> autoreplyList = autoreplyDao.getAutoreplyListByBrandId(business.getBrandId());// 获取该商家所有的自动回复
				if (autoreplyList != null) {
					for (AutoreplyVO autoreplyVO : autoreplyList) {
						List<ImageConfigVO> imageConfigList = imageConfigDao.getImageConfigVOListByAutoply(autoreplyVO);
						autoreplyVO.setImageConfigList(imageConfigList);
					}
				}
				AutoreplyData.getMap().put(business.getBrandId().toString(), autoreplyList);
			}
		}
	}

	@Override
	public boolean saveActivityConfig(ActivityConfigVO activityConfig) {
		return acvivityDao.insertActivityConfig(activityConfig);
	}

	@Override
	public boolean modifyActivityConfig(ActivityConfigVO activityConfig) {
		return acvivityDao.updateActivityConfig(activityConfig);
	}

	@Override
	public boolean deleteActivityConfig(ActivityConfigVO activityConfig) {
		return acvivityDao.deleteActivityConfig(activityConfig);
	}

	@Override
	public String createButton(String json, String appId, String appSecret) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret="
				+ appSecret;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.getAllowAllClient(httpclient);
		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {

			String access_token = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(access_token);
			access_token = requestObject.getString("access_token");

			String weixinurl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
			return HttpClientCommonSSL.commonPostStream(weixinurl, json);
		} else {
			return null;
		}
	}

	List<SubbranchVO> getSubbranchListFromJson(JSONObject jo) {
		if (jo == null) {
			return null;
		}
		List<SubbranchVO> subbranchList = new ArrayList<SubbranchVO>();
		SubbranchVO subbranch = null;
		Iterator<String> keys = jo.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.equals("info")) {
				return null;
			}
			subbranch = new SubbranchVO();
			subbranch.setName(jo.getJSONObject(key).getString("name"));
			subbranch.setAddress(jo.getJSONObject(key).getString("address"));
			subbranch.setPhoneNo(jo.getJSONObject(key).getString("phone"));
			subbranch.setCompanyWeiboId(jo.getJSONObject(key).getString("company_weibo_id"));
			subbranch.setIsDelete(false);
			subbranch.setIsNew(false);
			subbranch.setIsRecommend(false);
			if (jo.getJSONObject(key).get("map_x") == null || jo.getJSONObject(key).getString("map_x").equals("null")
					|| jo.getJSONObject(key).getDouble("map_x") < 0 || jo.getJSONObject(key).getDouble("map_x") > 360) {
				subbranch.setPointX(new Double("0"));
			} else {
				subbranch.setPointX(new Double(jo.getJSONObject(key).getString("map_x")) + new Double(0.0067));
			}
			if (jo.getJSONObject(key).get("map_y") == null || jo.getJSONObject(key).getString("map_y").equals("null")
					|| jo.getJSONObject(key).getDouble("map_y") < 0 || jo.getJSONObject(key).getDouble("map_y") > 360) {
				subbranch.setPointY(new Double("0"));
			} else {
				subbranch.setPointY(new Double(jo.getJSONObject(key).getString("map_y")) + new Double(0.0061));
			}
			subbranchList.add(subbranch);
		}
		return subbranchList;
	}

	public JSONObject getJsonFromWeiboId(String company_weibo_id) throws Exception {
		company_weibo_id = URLEncoder.encode(company_weibo_id, "UTF-8");
		HttpClient httpclient = new DefaultHttpClient();
		String url = poiCompanyStore + "?company_weibo_id=" + company_weibo_id;

		// 获得HttpGet对象
		HttpGet httpGet = new HttpGet(url);
		// 发送请求
		HttpResponse response;
		response = httpclient.execute(httpGet);
		if (response.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(response.getEntity());
			JSONObject requestObject = JSONObject.fromObject(result);
			return requestObject;
		} else {
			return null;
		}
	}

	@Override
	public boolean saveStory(Story story) {
//		String path = this.getClass().getClassLoader().getResource("").getPath();
//		path = path.split("WEB-INF")[0];
//		path = path + imageLocationPath + File.separator + story.getBrandId().toString();
//		try {
//			SaveFileFromInputStream(story.getImage().getInputStream(), path, story.getImageName());
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		// 将图片放置图片服务器
		String filePath = new FastDFSUtil().uploadImage(story.getImage());
		story.setImageName(filePath);
		
		boolean flag = storyDao.saveStory(story);
		loadAutoreplyData();
		return flag;
	}

	@Override
	public boolean updateStory(Story story) {
//		String path = this.getClass().getClassLoader().getResource("").getPath();
//		path = path.split("WEB-INF")[0];
//		path = path + imageLocationPath + File.separator + story.getBrandId().toString();
//		try {
//			if (story.getImage() != null)
//				SaveFileFromInputStream(story.getImage().getInputStream(), path, story.getImageName());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
		// 将图片放置图片服务器
		String filePath = new FastDFSUtil().uploadImage(story.getImage());
		story.setImageName(filePath);
		
		boolean flag = storyDao.updateStory(story);
		loadAutoreplyData();
		return flag;
	}

	@Override
	public Story getStoryByBrandId(Integer brandId) {

		return storyDao.getStoryByBrandId(brandId);
	}
	@Override
	public Object getDishesList(Integer merchantId, String weixinId, String key) {
		String input = "{merchantId:" + merchantId + "}";
		String paramStr = JSONObject.fromObject(input).toString();
		JSONObject res = convertData(dishesListUrl, paramStr, key);
//		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (res != null) { 
			Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {// 成功
				JSONArray array = res.getJSONArray("dishesList");
				Iterator it = array.iterator();
				List<Map<String, Object>> list = null;
				while(it.hasNext()) {
					JSONObject jsonObject = JSONObject.fromObject(it.next()); // 一个集合
					// 转换成map数据
					Map<String, Object> valMap = new HashMap<String, Object>();
					for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
						String keyVal = (String) iter.next();
						Object obj = jsonObject.get(keyVal);
						if (keyVal.equals("dishesPic")) { // 处理图片路径
							String vpath = String.valueOf(obj).trim();
							String pic = "";
							if (!StringUtil.isNullOrEmpty(vpath)) {
								if (vpath.startsWith("M00/")) {
									pic = newDishesPicUrl + vpath;
								} else {
									pic = dishesPicPAth.trim() + vpath;
								}
							}
							valMap.put(keyVal, pic);
						} else {
							valMap.put(keyVal,obj);
						}
					}
					// 是否还可以点赞
					valMap.put("pariseStatus", 0);
					//取点赞状态
					List<Map<String, Object>> praiseList = getUserPariseRecord(merchantId, weixinId, jsonObject.getInt("id"));
					log.info("取点赞状态：praiseList=" + praiseList);
					if (praiseList !=null && praiseList.size() > 0) {
						valMap.put("pariseStatus", 1);
					} 
					
					list = (List<Map<String, Object>>)map.get(jsonObject.getString("dishesTypeName"));
					if (list == null || list.size() == 0) {
						list = new ArrayList<Map<String,Object>>();
						list.add(valMap);
						map.put(jsonObject.getString("dishesTypeName"), list);
					} else {
						list.add(valMap);
					}
				}
			}
		}
		return map;
	}

	@Override
	public JSONObject getDishesDetail(String input, String key) {
		JSONObject res = convertData(dishesDetailUrl, input, key);
		if (res != null) { 
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {
				JSONObject jsonObject = res.getJSONObject("dishes");
				for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
					String keyVal = (String) iter.next();
					Object obj = jsonObject.get(keyVal);
					if (keyVal.equals("dishesPic")) { // 处理图片路径
						String vpath = String.valueOf(obj).trim();
						String pic = "";
						if (!StringUtil.isNullOrEmpty(vpath)) {
							if (vpath.startsWith("M00/")) {
								pic = newDishesPicUrl + vpath;
							} else {
								pic = dishesPicPAth.trim() + vpath;
							}
						}
						jsonObject.put(keyVal, pic);
						break;
					}
				}
				return jsonObject;
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getUserPariseRecord(Integer brandId, String weixinId, Integer dishesId) {
		return dishesDao.getUserPariseRecord(brandId, weixinId, dishesId);
	}

	@Override
	public boolean addPariseRecord(Integer merchant_id, Integer dishesId, String weixinId) {
		int count = dishesDao.addPariseRecord(merchant_id, dishesId, weixinId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateDishesParise(String input, String key) {
		JSONObject res = convertData(dishesPrasieUrl, input, key);
		if (res != null) { 
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			return success;
		}
		return false;
	}
	
	@Override
	public Map<String, Object> getAllArea(Integer merchantId) {
		JSONObject res = convertData(zoneListUrl, "{'merchantId':"+merchantId+"}", String.valueOf(Constant.KEY));
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject resultJson = new JSONObject();
		if (res !=null) {
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {
				JSONArray array = res.getJSONArray("areaList");
				Iterator it = array.iterator();
				List<Map<String, Object>> childList = new ArrayList<Map<String,Object>>(); // 保存不是父节点的市
				List<Map<String, Object>> parentList = new ArrayList<Map<String,Object>>(); // 父节点省
				while(it.hasNext()) {
					JSONObject jsonObject = JSONObject.fromObject(it.next()); // 一个集合
					// 转换成map数据
					Map<String, Object> valMap = new HashMap<String, Object>();
					for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
						String keyVal = (String) iter.next();
						Object obj = jsonObject.get(keyVal);
						valMap.put(keyVal,obj);
					}
					if (jsonObject.getInt("fatherId") == 0) { //父节点
						parentList.add(valMap);
					} else { // 子节点
						childList.add(valMap);
					}
				}
				
				for (Map<String, Object> p : parentList) {
					Integer areaId = Integer.parseInt(String.valueOf(p.get("areaId")));
					String name = String.valueOf(p.get("name"))+","+areaId;
					if (childList == null || childList.size() ==0) { // 没有子节点
						resultJson.accumulate(name, new JSONArray());
						map.put(name, new ArrayList<Map<String,Object>>());
					} else {
						int count = 0;
						for (Map<String, Object> c : childList) {
							Integer fatherId = Integer.parseInt(String.valueOf(c.get("fatherId")));
							List<Map<String, Object>> list = null;
							if (areaId.equals(fatherId)) { // 父节点下的子节点
								list = (List<Map<String, Object>>)map.get(name);
								if (list!=null && list.size() > 0) { // 已有key值了
									list.add(c);
								} else {
									list = new ArrayList<Map<String,Object>>();
									list.add(c);
									map.put(name, list);
								}
								Object obj = resultJson.get(name);
								JSONArray resultArray = null;
								if (obj !=null) {
									resultArray = resultJson.getJSONArray(name);
									resultArray.add(c);
								} else {
									resultArray = new JSONArray();
									resultArray.add(c);
									resultJson.accumulate(name, list);
								}
							} else {
								count++;
							}
						}
						if (count == childList.size()) { // 有子节点，但不是此父节点下的子节点
							resultJson.accumulate(name, new ArrayList<Map<String,Object>>());
							map.put(name, new JSONArray());
						}
					}
				}
			}
	}
		return resultJson;
	}

	@Override
	public Object getSubbrachByOrder(Integer areaId, Integer merchantId) {
		String input = "{'merchantId':" + merchantId;
		if (areaId !=null && areaId !=0) {
			input += ",'areaId':"+ areaId;
		}
		input +=",'isFaceShop':true}";
		
		JSONObject res = convertData(memchantListUrl, input, String.valueOf(Constant.KEY));
		if (res != null) { 
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {
				return res.getJSONArray("merchantList");
			}
		}
		return null;
	}

	@Override
	public JSONObject getSubbrachByNewOrder(Integer merchantId, Integer pageIndex,Integer pageSize, Integer areaId, String source, String merchantName) {
		//按区域查询且排序
		StringBuilder paramSb = new StringBuilder("{");
		paramSb.append("'merchantId':").append(merchantId).append(",");
		if (!StringUtil.isNullOrEmpty(source) && !source.equals("serach")) { //从搜索框查询数据
			paramSb.append("'areaId':").append(0).append(",").append("'merchantName':'").append(merchantName).append("',");
		} else { // 按区域查询
			if (areaId !=null && areaId !=0) {
				paramSb.append("'areaId':").append(areaId).append(",");
			}
		}
		paramSb.append("'orderName':").append("'wx_ord'")
		.append(",'showName':'is_wx_show'").append(",'isFaceShop':true").append(",'page':").append(pageIndex).
		append(",'size':").append(pageSize).append("}");
		JSONObject res = convertData(merchantSortUrl, paramSb.toString(), String.valueOf(Constant.KEY));
		if (res!=null) {
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {
				return res.getJSONObject("merchantPage");
			}
		}
		return null;
	}

	@Override
	public JSONObject getSubbrachByCoordinatesOrder(Integer merchantId, String longitude, String latitude, String province, Integer pageIndex, Integer pageSize) {
		// 经纬度查询门店按距离排序
		Integer maxDistance = 30;
		if (!StringUtil.isNullOrEmpty(province)) {
			province = province.substring(0, province.length()-1);
		}
		StringBuilder sb = new StringBuilder("{");
		sb.append("'merchantId':").append(merchantId).append(",").append("'longitude':'").append(longitude).append("',").append("'laitude':'").
		append(latitude).append("','provinceName':'").append(province).append("', 'page':").append(pageIndex).append(",'size':").append(pageSize).append(",'maxDistance':"+maxDistance).append("}");
		JSONObject res = convertData(merchantSortByCoordinatesUrl, sb.toString(), String.valueOf(Constant.KEY));
		if (res!=null) {
			boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			log.info("result message:"+message + "; success:" + success);
			if (success) {
				return res.getJSONObject("merchantPage");
			}
		}
		return null;
	}

	// 调用crm接口，将json格式数据转换为json对象
	private JSONObject convertData (String url, String input, String key) {
		String result = "";
		try {
			result = CommonUtil.postSendMessage(url, input, key);
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		//log.info("调用crm接口返回结果:" + result+"+++---------------------");
		if (!StringUtil.isNullOrEmpty(result)) { // 判断不为空
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = "";
			JSONObject obj = (JSONObject) requestObject.get("data");
			try {
				if (obj !=null && obj.get("result")!=null && !StringUtil.isNullOrEmpty(obj.get("result").toString())) {
					data = URLDecoder.decode(obj.get("result").toString(), "UTF-8");
				}
				//log.info("result:" + data+"---------------------");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return JSONObject.fromObject(data);
		}
		return null;
	}

	@Override
	public Story getByBrandId(Integer brandId) {
		return storyDao.getByBrandId(brandId);
	}

	@Override
	public boolean updateMemberRight(BusinessVO business) {
		return businessDao.updateMemberRight(business);
	}

	@Override
	public Pagination<BusinessVO> getBusinessPageByOrder(int currentPage, BusinessVO business) {
		PageInfo page = new PageInfo(currentPage, 15);
		return businessDao.getBusinessPage(page, business);
	}

	@Override
	public Map<String, Object>  saveManyAutoreply(String [] keywords, Integer brandId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (keywords !=null && keywords.length > 0) {
			// 判断是否已经存在
			List<String> rlist = this.getSettingAutoreply(brandId);
			// 是否为第一次添加
			boolean isFirstAdd = rlist !=null && rlist.size()>0;
			
			List<AutoreplyVO> list = new ArrayList<AutoreplyVO>();
			for (int i = 0; i < keywords.length; i++) {
				if (isFirstAdd && rlist.contains(keywords[i])) {
					continue;
				}
				AutoreplyVO autoreply = new AutoreplyVO();
				autoreply.setBrandId(brandId);
				autoreply.setKeywordType("equals");
				autoreply.setKeyword(keywords[i]);
				autoreply.setReplyContent("");
				autoreply.setReplyType("news"); // 图文
				autoreply.setEventType(2); // 事件类型
				autoreply.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				autoreply.setSpecificType(1); // 某部分菜单对应的自动回复
				list.add(autoreply);
			}
			if (list.size() > 0) {
				int count = autoreplyDao.insertManyAutoreply(list);
				map.put("flag", count>0);
				map.put("message", (count>0 ? "添加成功" : "添加失败"));
				if(count>0){
					loadAutoreplyData(); // 重新加载
				}
			} else {
				map.put("flag", 0);
				map.put("message", "您选择的项已经被添加，不能重复添加");
			}
		} else {
			map.put("flag", 0);
			map.put("message", "您未选择项");
		}
		return map;
	}

	@Override
	public List<String> getSettingAutoreply(Integer brandId) {
		List<String> resultList = new ArrayList<String>();
		List<AutoreplyVO> list = autoreplyDao.getSettingAutoreply(brandId);
		if (list !=null && list.size()>0) {
			for (AutoreplyVO a : list) {
				resultList.add(a.getKeyword());
			}
		}
		return resultList;
	}

	@Override
	public Map<String, Object> getSettingAutoreplyByMenuKey(Integer brandId, String keyWord) {
		return autoreplyDao.getSettingAutoreplyByMenuKey(brandId, keyWord);
	}
	
	
}

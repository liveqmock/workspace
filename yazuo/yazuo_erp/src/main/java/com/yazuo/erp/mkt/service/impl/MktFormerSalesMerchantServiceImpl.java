/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service.impl;

import java.util.*;

import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.mkt.service.MktFormerSalesMerchantService;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.service.SysSalesmanMerchantService;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class MktFormerSalesMerchantServiceImpl implements MktFormerSalesMerchantService {
	
	@Resource
	private MktFormerSalesMerchantDao mktFormerSalesMerchantDao;
	@Resource
	private SysSalesmanMerchantService salesmanMerchantService;
	
	@Override
	public int saveOrUpdateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant, SysUserVO sessionUser) {
		Integer id = mktFormerSalesMerchant.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			mktFormerSalesMerchant.setUpdateBy(userId);
			mktFormerSalesMerchant.setUpdateTime(new Date());
			mktFormerSalesMerchant.setIsEnable(Constant.Enable);
			row = mktFormerSalesMerchantDao.updateMktFormerSalesMerchant(mktFormerSalesMerchant);
		}else{
			mktFormerSalesMerchant.setInsertBy(userId);
			mktFormerSalesMerchant.setInsertTime(new Date());
			mktFormerSalesMerchant.setUpdateBy(userId);
			mktFormerSalesMerchant.setUpdateTime(new Date());
			mktFormerSalesMerchant.setIsEnable(Constant.Enable);
			row = mktFormerSalesMerchantDao.saveMktFormerSalesMerchant(mktFormerSalesMerchant);
		}
		return row;
	}
	/**保存历史销售负责人*/
	@Override
	public void saveSalesMan(final SysUserVO user, final Integer merchantId, final Integer salesId,
			final SysSalesmanMerchantVO salesMan) {
		
		//原销售不责任不为空且新改过的销售负责人不是同一个人
			final Integer userId = salesMan.getId();
			List<MktFormerSalesMerchantVO> mktFormerSalesMerchants = this
					.getMktFormerSalesMerchants(new MktFormerSalesMerchantVO() {
						{
							setMerchantId(merchantId);
							setIsEnable("1");
							setSortColumns("begin_time desc");
						}
					});
			if (mktFormerSalesMerchants.size() > 0) {
				//更新历史销售负责人结束时间
				MktFormerSalesMerchantVO mktFormerSalesMerchant = mktFormerSalesMerchants.get(0);
				if(!mktFormerSalesMerchant.getUserId().equals(userId)){//如果修改的销售负责人也原来历史销售负责人不是同一个人
					mktFormerSalesMerchant.setUpdateTime(new Date());
					mktFormerSalesMerchant.setEndTime(new Date());
					mktFormerSalesMerchant.setUpdateBy(user.getId());
					this.updateMktFormerSalesMerchant(mktFormerSalesMerchant);
				}
			}
			//新增一条历史销售负责人
			this.saveMktFormerSalesMerchant(new MktFormerSalesMerchantVO() {
				{
					setUserId(salesId);
					setMerchantId(merchantId);
					setInsertBy(user.getId());
					setInsertTime(new Date());
					setBeginTime(new Date());
					setUpdateTime(new Date());
					setUpdateBy(user.getId());
					setIsEnable("1");
				}
			});
			//修改当前销售负责人
			salesmanMerchantService.updateSysSalesmanMerchant(new SysSalesmanMerchantVO() {
				{
					setId(salesMan.getId());
					setUserId(salesId);
				}
			});
	}
	
	public int saveMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant) {
		return mktFormerSalesMerchantDao.saveMktFormerSalesMerchant(mktFormerSalesMerchant);
	}
	public int batchInsertMktFormerSalesMerchants (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchInsertMktFormerSalesMerchants(map);
	}
	public int updateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.updateMktFormerSalesMerchant(mktFormerSalesMerchant);
	}
	public int batchUpdateMktFormerSalesMerchantsToDiffVals (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchUpdateMktFormerSalesMerchantsToDiffVals(map);
	}
	public int batchUpdateMktFormerSalesMerchantsToSameVals (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchUpdateMktFormerSalesMerchantsToSameVals(map);
	}
	public int deleteMktFormerSalesMerchantById (Integer id){
		return mktFormerSalesMerchantDao.deleteMktFormerSalesMerchantById(id);
	}
	public int batchDeleteMktFormerSalesMerchantByIds (List<Integer> ids){
		return mktFormerSalesMerchantDao.batchDeleteMktFormerSalesMerchantByIds(ids);
	}
	public MktFormerSalesMerchantVO getMktFormerSalesMerchantById(Integer id){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchantById(id);
	}
	public List<MktFormerSalesMerchantVO> getMktFormerSalesMerchants (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchants(mktFormerSalesMerchant);
	}
	public List<Map<String, Object>>  getMktFormerSalesMerchantsMap (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchantsMap(mktFormerSalesMerchant);
	}
}

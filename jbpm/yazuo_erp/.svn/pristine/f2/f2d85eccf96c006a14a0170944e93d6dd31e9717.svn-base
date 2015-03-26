/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.exception.BesBizException;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysSalesmanMerchantDao;
import com.yazuo.erp.system.service.SysSalesmanMerchantService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysSalesmanMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class SysSalesmanMerchantServiceImpl implements SysSalesmanMerchantService {

	@Resource SysResourceDao sysResourceDao;
	/**
	 * 标准方法,设置商户下拉列表销售负责人
	 */
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	public void setStdSales(SynMerchantVO synMerchantVO, Object vo, String... attributeName) {
		
		List<SysUserVO> sales = this.sysResourceDao.getAllUsersByResourceCode(new SysResourceVO(){{setRemark("sales_service");}});
		
		CollectionUtils.transform(sales, new Transformer() {
			public Object transform(Object input) {
				SysUserVO sale = (SysUserVO)input;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("value", sale.getId());
				map.put("text", sale.getUserName());
				return map;
			}
		});
		try {
			MethodUtils.invokeMethod(vo, "set"+StringUtils.capitalize(attributeName[0]),sales);
		} catch (Exception e) {
			throw new BesBizException(e);
		} 
	}
	@Resource
	private SysSalesmanMerchantDao sysSalesmanMerchantDao;
	
	public int saveSysSalesmanMerchant (SysSalesmanMerchantVO sysSalesmanMerchant) {
		return sysSalesmanMerchantDao.saveSysSalesmanMerchant(sysSalesmanMerchant);
	}
	public int batchInsertSysSalesmanMerchants (Map<String, Object> map){
		return sysSalesmanMerchantDao.batchInsertSysSalesmanMerchants(map);
	}
	public int updateSysSalesmanMerchant (SysSalesmanMerchantVO sysSalesmanMerchant){
		return sysSalesmanMerchantDao.updateSysSalesmanMerchant(sysSalesmanMerchant);
	}
	public int batchUpdateSysSalesmanMerchantsToDiffVals (Map<String, Object> map){
		return sysSalesmanMerchantDao.batchUpdateSysSalesmanMerchantsToDiffVals(map);
	}
	public int batchUpdateSysSalesmanMerchantsToSameVals (Map<String, Object> map){
		return sysSalesmanMerchantDao.batchUpdateSysSalesmanMerchantsToSameVals(map);
	}
	public int deleteSysSalesmanMerchantById (Integer id){
		return sysSalesmanMerchantDao.deleteSysSalesmanMerchantById(id);
	}
	public int batchDeleteSysSalesmanMerchantByIds (List<Integer> ids){
		return sysSalesmanMerchantDao.batchDeleteSysSalesmanMerchantByIds(ids);
	}
	public SysSalesmanMerchantVO getSysSalesmanMerchantById(Integer id){
		return sysSalesmanMerchantDao.getSysSalesmanMerchantById(id);
	}
	public List<SysSalesmanMerchantVO> getSysSalesmanMerchants (SysSalesmanMerchantVO sysSalesmanMerchant){
		return sysSalesmanMerchantDao.getSysSalesmanMerchants(sysSalesmanMerchant);
	}
	public List<Map<String, Object>>  getSysSalesmanMerchantsMap (SysSalesmanMerchantVO sysSalesmanMerchant){
		return sysSalesmanMerchantDao.getSysSalesmanMerchantsMap(sysSalesmanMerchant);
	}

    @Override
    public SysSalesmanMerchantVO getSysSalesmanMerchantByMerchantId(Integer merchantId) {
        SysSalesmanMerchantVO sysSalesmanMerchantVO = new SysSalesmanMerchantVO();
        sysSalesmanMerchantVO.setMerchantId(merchantId);
        List<SysSalesmanMerchantVO> sysSalesmanMerchantVOs =this.sysSalesmanMerchantDao.getSysSalesmanMerchants(sysSalesmanMerchantVO);
        return sysSalesmanMerchantVOs.size() > 0 ? sysSalesmanMerchantVOs.get(0) : null;
    }
}

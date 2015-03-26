/**
 * 文件名：SynContractService.java 
 * 版权：Copyright by www.yazuo.com 
 * 描述：八百客相关合同相关接口
 * 修改人：gaoshan 
 * 修改时间：2014-8-11 
 * 跟踪单号： 
 * 修改单号： 
 * 修改内容：
 */

package com.yazuo.erp.syn.service;

import java.util.Map;

public interface SynContractService {
	
	/**
	 * @Description 八百客合同信息同步，并同时进行客户信息同步
	 * @param param
	 * @return
	 * @throws
	 */
	boolean batchUpdateSynContracts(Map<String, Object> param);
	
}

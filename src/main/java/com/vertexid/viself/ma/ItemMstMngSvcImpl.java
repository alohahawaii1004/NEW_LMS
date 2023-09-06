package com.vertexid.viself.ma;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.vertexid.viself.ma.ItemMstMngVo;

import com.vertexid.viself.ma.dao.ItemMstMngDAO; 

/**
 * @class Name : ItemMstMngSvcImpl.java
 * @Description : 품목 마스터 Service impl
 * @Modification Information
 * @  CboCmDao
 * @ Date            Author       Description
 * @ -------------  -----------  -------------
 * 2023.09.06    윤태영         최초넥사크로 F/W 생성 작업  
 */
@Service("com.vertexid.viself.ma.ItemMstMngSvc")
public class ItemMstMngSvcImpl  implements ItemMstMngSvc {  
	
	private static final Logger logger = LoggerFactory.getLogger(ItemMstMngSvcImpl.class);
	
	/* (non-Javadoc)
	*/
	@Override
	public List<ItemMstMngVo> selectList(ItemMstMngVo vo) {
		
		logger.info("VO............." + vo);  
	///	return ItemMstMngDAO.selectList(ItemMstMngDao.selectList, vo);
		
		return null;
	}
	
}

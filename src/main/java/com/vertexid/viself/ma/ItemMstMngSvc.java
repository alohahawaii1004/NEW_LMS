package com.vertexid.viself.ma;

import java.util.List;
import com.vertexid.viself.ma.ItemMstMngVo;

/**
 * @class Name : ItemMstMngSvc.java
 * @Description : 품목 마스터 관리 Service
 * @Modification Information
 * @
 * @ Date            Author       Description
 * @ -------------  -----------  -------------
 * @ 2023.09.06    윤태영         최초넥사크로 F/W 생성 작업  
 */
public interface ItemMstMngSvc {

	
	/**
	 * 품목 기준정보 리스트 조회
	 * @param vo
	 * @return
	 */
	public List<ItemMstMngVo> selectList(ItemMstMngVo vo);
	
}

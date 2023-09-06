package com.vertexid.viself.ma;
import com.vertexid.core.vo.AbstractVo;

/**
 * @class Name : ItemMstMngVo.java
 * @Description :  마스터 관리 Vo
 * @Modification Information
 * @
 * @ Date            Author       Description
 * @ -------------  -----------  -------------
 * @ 2023.09.06    윤태영         최초넥사크로 F/W 생성 작업 
 */
public class ItemMstMngVo extends AbstractVo {
	
private String itemCd;			// 품목 코드
private String itemNm;			// 품목 명

	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}

	public String getItemNm() {
		return itemNm;
	}
	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}
	
	@Override
	public String toString() {
		return "ItemMstMngVo [itemCd=" + itemCd + ", itemNm=" + itemNm + "]";
	}

	
	
}
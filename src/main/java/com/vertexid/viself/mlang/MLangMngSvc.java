package com.vertexid.viself.mlang;

import com.vertexid.viself.base.BaseSvc;
import com.vertexid.viself.base.CmmDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 다국어사전 관리 서비스
 *
 */
@Transactional
@Service
public class MLangMngSvc extends BaseSvc {

    private static final String NAMESPACE =
            "com.vertexid.viself.mlang.MLangMng";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    public String insert(MLangDTO params) {
        String result = "";

        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "saveData"), params);
        if (ERROR_RESULT.equals(params.getErrCd())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }
        return result;
    }

    public String update(MLangDTO params) {
        String result = "";

        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "updateData"), params);
        if (ERROR_RESULT.equals(params.getErrCd())) {
            result = params.getErrMsg();
            log.error("Error.Input parameters............." + params);
            throw new RuntimeException(result);
        }
        return result;
    }

	public String notYetInsert(Map<String, Object> params) {
		 cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "notYetInsert"), params);
		return null;
	}

	public void deleteNoLang(Map<String, Object> params) {
		cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteNoLang"), params);
	}

	public void notYetSelectInsert(Map<String, Object> params) {
		cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "notYetSelectInsert"), params);
	}
}

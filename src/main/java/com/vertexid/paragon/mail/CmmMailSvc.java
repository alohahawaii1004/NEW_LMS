package com.vertexid.paragon.mail;

import com.vertexid.viself.base.CmmDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>Description</b>
 * <pre>
 *     메일 데이터를 처리하는 Base Service
 *     type: (F/W) Base Service
 * </pre>
 */
@Service
@Transactional
public class CmmMailSvc {

    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String NAMESPACE = "com.vertexid.paragon.mail.Mail";

    @Resource(name = "cmmDAO")
    private CmmDAO cmmDAO;

    /**
     * 메일전송 대상정보 입력하기
     *
     * @param list Mail DTO 로 구성된 리스트
     */
    public void setMailData(List<Object> list) {

        if(null == list){
            log.info("......NO LIST");
            return;
        }

        // 2. 대상정보 입력하기
        for (Object o : list) {
            if (o instanceof MailDTO) {
                log.debug("MailDTO...."+o);
                insertData((MailDTO) o);
            }
        }// end of for

//        throw new RuntimeException("사용자 에러");
    }

    /**
     * 메일정보 입력
     *
     * @param param 메일정보
     */
    public void insertData(MailDTO param) {
        cmmDAO.insert(cmmDAO.getStmtByNS(NAMESPACE, "insert"), param);
    }

    /**
     * 동일건에 대해 기존에 발송되지 않은 메일이 있다면 삭제한다.
     * @param param parameter(solMasUid, relUid)
     */
    public void deleteUnsentMail(Map<String, Object> param) {
        cmmDAO.delete(cmmDAO.getStmtByNS(NAMESPACE, "deleteUnsentMail"), param);
    }

    public void updateResult(MailDTO param) {
        cmmDAO.update(cmmDAO.getStmtByNS(NAMESPACE, "update"), param);
    }

    @Transactional(readOnly = true)
    public <E> List<E> getMailList(Map<String, Object> param) {
        return cmmDAO.getList(cmmDAO.getStmtByNS(NAMESPACE, "getMailList"), param);
    }
}

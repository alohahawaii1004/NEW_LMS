package com.vertexid.paragon.bbs;

import com.vertexid.commons.utils.JsonUtil;
import com.vertexid.commons.utils.ParamMap;
import com.vertexid.mybatis.ListPerPageFunction;
import com.vertexid.spring.utils.SessionUtils;
import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.PagingParamFunction;
import com.vertexid.viself.hr.SysLoginVO;
import com.vertexid.viself.security.AuthorityCheckFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.ModelAttribute.DATA;

/**
 * <b>Description</b>
 * <pre>
 *     게시판 컨트롤러
 * </pre>
 *
 * @author 양기화(梁起華 Yang, Ki Hwa)
 */
@Controller
public class BbsMasCtrl extends BaseCtrl {

    private static final String REDIRECT_VIEW =
            "redirect:/paragon/bbs/bbsView.popup";

    @Resource
    ListPerPageFunction listPerPageFunction;

    @Resource
    PagingParamFunction pagingParamFunction;

    @Resource
    BbsMasSvc bbsMasSvc;

    @Resource
    AuthorityCheckFunction authorityCheckFunction;

    private boolean checkWriteAuthority(BbsMasDTO param) {

        /*
         * REVIEW
         * 아래 로직은 향후 DB 나 설정을 이용해서 개선해야 한다.
         */
        String bbsCd = param.getBbsCd();
        String msg;
        String saveAuthorities = "LMS_BCD,LMS_BJD,CMM_SYS";
        // 게시물의 종류에 따라 로직 적용
        if ("BOARD_NOTICE,BOARD_FILE,BOARD_FAQ,CT_OLD,CO_OLD,CT_PCA,CO_PCA".indexOf(
                bbsCd) >= 0) {

            return authorityCheckFunction.checkAuthority(saveAuthorities);
        }
        msg = "지원하지 않는 게시판 코드 입니다.:" + bbsCd;
        log.error(msg);
        return false;
    }

    @SuppressWarnings("null")
    @RequestMapping(value = "/paragon/bbs/BbsMas/selectAuth/json",
            method = RequestMethod.POST)
    public String bbsAuth(HttpServletRequest req, ModelMap model,
            @RequestParam
            Map<String, Object> params) {

        // 권한 정보 가져오기
        ParamMap<String, Object> bbsMasView = bbsMasSvc.getbbsMasView(params);

        String bbsAuth = bbsMasView.getString("bbsViewAuth");

        String[] bbsAuthList = null;

        Map<String, Object> whereMap = new HashMap<String, Object>();

        if (bbsAuth != null && !"".equals(bbsAuth)) {
            bbsAuthList = bbsAuth.split(",");
        }

        whereMap.put("bbsViewAuth", bbsAuthList);

        ParamMap<String, Object> authNms = bbsMasSvc.getbbsAuth(whereMap);

        bbsMasView.put("bbsViewAuth", authNms.get("bbsViewAuth"));

        //모델에 해당 데이터를 넣는다.
        model.addAttribute("data", bbsMasView);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/paragon/bbs/BbsMas/listPerPage/json",
            method = RequestMethod.POST)
    public String getListPerPage(ModelMap modelMap,
            @RequestParam
            Map<String, Object> param) {

        modelMap.clear();
        SysLoginVO loingInfo = (SysLoginVO) SessionUtils.getLoginVO();
        param.put("loginInfo", loingInfo);
        pagingParamFunction.setOrderParam(param);
        listPerPageFunction.setNamespace(BbsMasSvc.NAMESPACE);
        modelMap.addAttribute(DATA.getAttributeId(),
                listPerPageFunction.getListPerPage(param));

        return JSON_VIEW.getViewId();
    }

    /**
     * 게시판 조회수 증가
     *
     * @param modelMap model
     * @param param    parameters(게시판 정보)
     * @param extParam parameters(기타 파라메터)
     * @return redirect view
     */
    @RequestMapping(value = "/paragon/bbs/bbsMasView.popup",
            method = RequestMethod.POST)
    public String addHit(ModelMap modelMap, BbsMasDTO param,
            @RequestParam
            Map<String, Object> extParam) {

        log.debug("param....." + param);
        log.debug("extParam....." + extParam);

        // 조회수 증가
        bbsMasSvc.addHit(param);

        modelMap.addAttribute("bbsCd", param.getBbsCd());
        modelMap.addAttribute("bbsTpCd", param.getBbsTpCd());
        modelMap.addAttribute("bbsUid", param.getBbsUid());
        modelMap.addAttribute("popupTitle", extParam.get("popupTitle"));

        // callback key
        Map<String, Object> openerData = JsonUtil.getMapFromJsonObject(
                String.valueOf(extParam.get("openerData")));
        if(openerData != null){
            modelMap.addAttribute("cbKey", openerData.get("cbKey"));
        }

        return REDIRECT_VIEW;
    }

    //게시글 수정
    @RequestMapping(value = "/paragon/bbs/BbsMas/update/json",
            method = RequestMethod.POST)
    public String bbsUpdate(HttpServletRequest req, ModelMap model,
            @RequestParam
            Map<String, Object> params) {
        log.debug("bbsUpdate_params....." + params);

        String msg = bbsMasSvc.updateBbs(params);

        return getTransactionJsonView(model, msg);
    }

    @RequestMapping(value = "/paragon/bbs/BbsMas/save/json",
            method = RequestMethod.POST)
    public String insertData(ModelMap model,
            @RequestBody
            BbsMasDTO param) {

        String msg;
        if (checkWriteAuthority(param)) {
            msg = bbsMasSvc.saveBbs(param);
        } else {
            msg = "NO AUTHORITY";
        }
        return getTransactionJsonView(model, msg);
    }

    @RequestMapping(value = "/paragon/bbs/BbsMas/delete/json",
            method = RequestMethod.POST)
    public String deleteData(ModelMap model,
            @RequestBody
            BbsMasDTO param) {

        String msg;
        if (checkWriteAuthority(param)) {
            msg = bbsMasSvc.deleteData(param);
        } else {
            msg = "NO AUTHORITY";
        }
        return getTransactionJsonView(model, msg);
    }
}
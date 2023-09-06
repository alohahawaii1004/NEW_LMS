package com.vertexid.viself.mlang;

import com.vertexid.viself.base.BaseCtrl;
import com.vertexid.viself.base.BaseModelVO;
import com.vertexid.viself.base.ModelAttribute;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.vertexid.commons.view.ViewType.JSON_VIEW;
import static com.vertexid.viself.base.MessageCode.COMPLETE;
import static com.vertexid.viself.base.MessageCode.ERROR;

/**
 * 다국어사전관리 컨트롤러
 *
 */
@Controller
public class MLangMngCtrl extends BaseCtrl {

    @Resource
    private MLangMngSvc mLangMngSvc;

    @RequestMapping(value = "/viself/mlang/MLangMng/saveData/json",
            method = RequestMethod.POST)
    public String save(HttpSession session, ModelMap model,
                    MLangDTO params) {

        log.info("params........\n" + params.toString());
        model.clear();

        String result = mLangMngSvc.insert(params);

        if (StringUtils.isEmpty(result)) {
            // 정상처리시 메시지 표시
            model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                    COMPLETE.getMsgCode());
        } else {
            if (isNotProd()) {
                // 운영이 아닐 경우 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(), result);
            } else {
                // 운영일 경우 기본 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                        ERROR.getMsgCode());
            }
            model.addAttribute(ModelAttribute.ERROR_FLAG.getAttributeId(),
                    ERROR.getResultCode());
        }

        return JSON_VIEW.getViewId();
    }

    @RequestMapping(value = "/viself/mlang/MLangMng/updateData/json",
            method = RequestMethod.POST)
    public String udpate(HttpSession session, ModelMap model,
                    MLangDTO params) {

        log.info("params........\n" + params.toString());
        model.clear();

        String result = mLangMngSvc.update(params);

        if (StringUtils.isEmpty(result)) {
            // 정상처리시 메시지 표시
            model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                    COMPLETE.getMsgCode());
        } else {
            if (isNotProd()) {
                // 운영이 아닐 경우 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(), result);
            } else {
                // 운영일 경우 기본 에러 메시지 표시
                model.addAttribute(ModelAttribute.MSG.getAttributeId(),
                        ERROR.getMsgCode());
            }
            model.addAttribute(ModelAttribute.ERROR_FLAG.getAttributeId(),
                    ERROR.getResultCode());
        }

        return JSON_VIEW.getViewId();
    }


    /**
     * 삭제처리
     *
     * @param session session
     * @param model   model
     * @param params  parameters
     * @return view
     */
    @RequestMapping("/viself/mlangSvc/deleteNoLang/json")
    public String deleteNoLang(HttpSession session, ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        BaseModelVO modelInfo =
                new BaseModelVO("viself", "mlang", null, null, "MLangMng",
                        "deleteNoLang");

        mLangMngSvc.deleteNoLang(params);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping("/viself/mlangSvc/notYetInsert/json")
    public String notYetInsert(HttpSession session, ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

    	String result = mLangMngSvc.notYetInsert(params);

        return JSON_VIEW.getViewId();
    }

    @RequestMapping("/viself/mlangSvc/notYetSelectInsert/json")
    public String notYetSelectInsert(HttpSession session, ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

    	mLangMngSvc.notYetSelectInsert(params);


        return JSON_VIEW.getViewId();
    }

    @RequestMapping("/viself/mlangSvc/MLangInit/json")
    public String MLangInit(HttpSession session, ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        BaseModelVO modelInfo =
                new BaseModelVO("viself", "mlang", null, null, "MLangMng",
                        "getInitList");
        return getJsonView(modelInfo, model, params);
    }

    @RequestMapping("/viself/mlangSvc/listMaxVersion/json")
    public String listMaxVersion(HttpSession session, ModelMap model,
            @RequestParam
                    Map<String, Object> params) {

        BaseModelVO modelInfo =
                new BaseModelVO("viself", "mlang", null, null, "MLangMng",
                        "listMaxVersion");
        return getJsonView(modelInfo, model, params);
    }

}

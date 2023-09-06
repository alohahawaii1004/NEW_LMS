package com.vertexid.viself.base;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.vertexid.commons.view.ViewType;


@Controller
public class CmmInfoCtrl extends BaseCtrl {

    @RequestMapping("/viself/json")
    public String baseInfo(SessionStatus status, HttpServletRequest req,
                           ModelMap model,
                           @RequestParam
                                   Map<String, Object> params) {

        model.clear();

        String contextPath = req.getContextPath();
        model.addAttribute("CONTEXT_PATH", contextPath);

        return ViewType.JSON_VIEW.getViewId();
    }
}

/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.oauth2.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Stan Wu
 * @version 1.0
 * @date Created in 2021/8/27 9:20
 * @description
 */
@Controller
@SessionAttributes({"authorizationRequest"})
public class LoginController {

    @GetMapping("/loginpage")
    public String loginPage(){
        return "login";
    }

    /**
     * 自定义授权页面，注意：一定要在类上加@SessionAttributes({"authorizationRequest"})
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/confirmAccess")
    public String getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        @SuppressWarnings("unchecked")
        Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ? model.get("scopes") : request.getAttribute("scopes"));
        List<String> scopeList = new ArrayList<>();
        if (scopes != null) {
            scopeList.addAll(scopes.keySet());
        }
        model.put("scopeList", scopeList);
        return "/grant";
    }
}

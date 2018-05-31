package com.webdev.siteparser.controller;

import com.webdev.siteparser.service.security.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseSecurityController {
    @Autowired
    protected SecurityProcessor securityProcessor;

    public ModelAndView createModelAndView(String viewName) {
        return securityProcessor.createModelAndView(viewName);
    }
}

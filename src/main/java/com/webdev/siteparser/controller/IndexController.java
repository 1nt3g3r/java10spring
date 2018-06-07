package com.webdev.siteparser.controller;

import com.webdev.siteparser.service.jpa.ProjectService;
import com.webdev.siteparser.service.security.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class IndexController extends BaseSecurityController {
    @Autowired
    private SecurityProcessor securityProcessor;

    @GetMapping
    public ModelAndView index() {
        if (securityProcessor.isCurrentUserHasRole("ADMIN")) {
            return new ModelAndView("redirect:/admin");
        }

        return createModelAndView("index");
    }
}

package com.webdev.siteparser.controller;

import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class IndexController extends BaseSecurityController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ModelAndView index() {
        return createModelAndView("index");
    }
}

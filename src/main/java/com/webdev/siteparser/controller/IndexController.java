package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("/")
public class IndexController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ModelAndView index(@RequestParam(required = false, defaultValue = "42") String value) {
        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("projects", projectService.getAll());

        return modelAndView;
    }


    @GetMapping("project/{projectId}")
    @ResponseBody
    public Object getProjectById(@PathVariable long projectId) {
        if (projectService.exists(projectId)) {
            return projectService.getById(projectId);
        }

        return "Project not found";
    }

    @GetMapping("projects")
    @ResponseBody
    public List<Project> listProjects() {
        return projectService.getAll();
    }
}

package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController extends BaseSecurityController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public ModelAndView index(@RequestParam(required = false, defaultValue = "42") String value) {
        ModelAndView modelAndView = createModelAndView("projects");

        modelAndView.addObject("projects", projectService.getAll());

        return modelAndView;
    }

    @GetMapping("/project/changeParsingState")
    public String changeParsingState(@RequestParam(name = "projectId", required = true) long projectId,
                                     @RequestParam(name = "parsingEnabled", required = true) boolean parsingEnabled) {

        if (projectService.exists(projectId)) {
            Project project = projectService.getById(projectId);
            project.setParsingEnabled(parsingEnabled);
            projectService.save(project);
        }

        return "redirect:/";
    }

    @GetMapping("/project/delete")
    public String deleteProject(@RequestParam long projectId) {
        if (projectService.exists(projectId)) {
            Project project = projectService.getById(projectId);
            projectService.delete(project);
        }
        return "redirect:/";
    }

    @GetMapping("/project/add")
    public ModelAndView showCreateProjectPage() {
        return createModelAndView("project/create");
    }

    @PostMapping("/project/add")
    public String handleCreateProject(@ModelAttribute Project project) {
        projectService.save(project);

        return "redirect:/";
    }
}

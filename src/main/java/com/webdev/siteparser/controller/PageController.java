package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.convert.ContentFormatService;
import com.webdev.siteparser.service.jpa.PageService;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    public static final int PAGE_SIZE = 100;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ContentFormatService contentFormatService;

    @GetMapping("/project/pages")
    public ModelAndView dashboard(@RequestParam long projectId, @RequestParam(required = false, defaultValue = "0") String pageNumber) {
        int pNumber = Integer.parseInt(pageNumber);

        ModelAndView modelAndView = new ModelAndView("/project/pages/dashboard");

        int index = 0;
        if (projectService.exists(projectId)) {
            Project project = projectService.getById(projectId);
            modelAndView.addObject("project", project);

            List<Page> pages = pageService.getPage(projectId, pNumber, PAGE_SIZE);
            modelAndView.addObject("pages", pages);

            Map<Long, String> content = new HashMap<>();
            Map<Long, Integer> indices = new HashMap<>();

            for(Page page: pages) {
                content.put(page.getId(), contentFormatService.prepareToShow(page.getContent()));
                indices.put(page.getId(), pNumber*PAGE_SIZE + index++);
            }
            modelAndView.addObject("content", content);
            modelAndView.addObject("indices", indices);

            int totalPageCountInProject = pageService.getPageCount(projectId);
            int pageCount = totalPageCountInProject / PAGE_SIZE;
            if (totalPageCountInProject%PAGE_SIZE != 0) {
                pageCount++;
            }

            modelAndView.addObject("pageCount", pageCount);
            modelAndView.addObject("pageNumber", pNumber);
        }

        return modelAndView;
    }
}

package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.SearchService;
import com.webdev.siteparser.service.convert.ContentFormatService;
import com.webdev.siteparser.service.export.PagesToCsvExporter;
import com.webdev.siteparser.service.jpa.PageService;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class PageController extends BaseSecurityController {
    public static final int PAGE_SIZE = 100;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PageService pageService;

    @Autowired
    private ContentFormatService contentFormatService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private PagesToCsvExporter exporter;

    @PostMapping("/project/export")
    @ResponseBody
    public byte[] export(@RequestParam long projectId,
                         @RequestParam(name = "searchPhrase", required = true) String searchPhrase,
                         @RequestParam(name = "searchConditions", required = false) String[] searchConditions,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {

        SearchService.SearchSpecification searchSpecification = SearchService.SearchSpecification.load(searchConditions);
        Set<Page> pages = searchService.search(projectService.getById(projectId), searchSpecification, searchPhrase);

        String csv = exporter.export(pages);

        //Download file
        response.setContentType("application/text");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"csvfile.csv\"");

        //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(csv.getBytes());

        return byteArrayOutputStream.toByteArray();
    }

    @PostMapping("/project/search")
    public ModelAndView search(@RequestParam long projectId,
                               @RequestParam(name = "searchPhrase", required = true) String searchPhrase,
                               @RequestParam(name = "searchConditions", required = false) String[] searchConditions ) {
        ModelAndView modelAndView = createModelAndView("/project/pages/dashboard");

        SearchService.SearchSpecification searchSpecification = SearchService.SearchSpecification.load(searchConditions);
        Set<Page> pages = searchService.search(projectService.getById(projectId), searchSpecification, searchPhrase);

        //Add search params
        modelAndView.addObject("searchPhrase", searchPhrase);
        modelAndView.addObject("searchInTitle", searchSpecification.title);
        modelAndView.addObject("searchInUrl", searchSpecification.url);
        modelAndView.addObject("searchInContent", searchSpecification.content);
        modelAndView.addObject("searchInDescription", searchSpecification.description);

        //Add project
        modelAndView.addObject("project", projectService.getById(projectId));

        //Add pages
        modelAndView.addObject("pages", pages);

        //Add content and indices
        int index = 1;
        Map<Long, String> content = new HashMap<>();
        Map<Long, Integer> indices = new HashMap<>();

        for(Page page: pages) {
            content.put(page.getId(), contentFormatService.prepareToShow(page.getContent()));
            indices.put(page.getId(), index++);
        }
        modelAndView.addObject("content", content);
        modelAndView.addObject("indices", indices);

        return modelAndView;
    }

    @GetMapping("/project/pages")
    public ModelAndView dashboard(@RequestParam long projectId,
                                  @RequestParam(required = false, defaultValue = "0") String pageNumber) {
        int pNumber = Integer.parseInt(pageNumber);

        ModelAndView modelAndView = createModelAndView("/project/pages/dashboard");

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

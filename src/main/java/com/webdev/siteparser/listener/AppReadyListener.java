package com.webdev.siteparser.listener;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.repository.PageRepository;
import com.webdev.siteparser.repository.ProjectRepository;
import com.webdev.siteparser.service.cli.HtmlAnalyseCLI;
import com.webdev.siteparser.service.jpa.PageService;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppReadyListener {
    @Value("${launchMode}")
    private String launchMode;

    @Autowired
    private HtmlAnalyseCLI cli;

    @Autowired
    private PageService pageService;

    @Autowired
    private ProjectService projectService;

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {
//        pageService.clean();
//        projectService.clean();
//
//        Project project = new Project();
//        project.setDomain("https://habr.com");
//        project.setParsingEnabled(true);
//        projectService.save(project);

        if (launchMode != null && launchMode.equals("cli")) {
            cli.start();
        }
    }
}

package com.webdev.siteparser.service.cli;

import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsCLIHandler implements CLICommandHandler {
    @Autowired
    private ProjectService projectService;

    @Override
    public void handleCommand(String command, HtmlAnalyseCLI cli) {
        System.out.println("List of projects:");
//        for(Project project: projectService.getAll()) {
//            System.out.println(project.getDomain() + " (" + project.getPages().size() + " pages). ID: " + project.getId());
//        }
    }
}

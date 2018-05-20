package com.webdev.siteparser.service.cli;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.SearchService;
import com.webdev.siteparser.service.jpa.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SearchProjectCLIHandler implements CLICommandHandler {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private SearchService searchService;

    //search 5 url|title|content keywords
    @Override
    public void handleCommand(String command, HtmlAnalyseCLI cli) {
        String[] args = command.split(" ");

        long projectId = Long.parseLong(args[1]);
        Project project = projectService.getById(projectId);

        String[] searchSpecParams = args[2].split("\\|");
        SearchService.SearchSpecification specification = new SearchService.SearchSpecification();
        specification.reset();
        for(String searchItem: searchSpecParams) {
            if (searchItem.equals("url")) {
                specification.url = true;
            } else if (searchItem.equals("title")) {
                specification.title = true;
            } else if (searchItem.equals("content")) {
                specification.content = true;
            } else if (searchItem.equals("description")) {
                specification.description = true;
            }
        }

        String[] keywords = new String[args.length - 3];
        for(int i = 3; i < args.length; i++) {
            keywords[i - 3] = args[i];
        }

        Set<Page> results = searchService.search(project, specification, keywords);
        System.out.println("Found " + results.size() + " pages");
        for(Page page: results) {
            System.out.println(page.getTitle() + " - " + page.getUrl());
        }
    }
}

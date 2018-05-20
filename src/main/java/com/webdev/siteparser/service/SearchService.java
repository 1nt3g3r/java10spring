package com.webdev.siteparser.service;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.jpa.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SearchService {
    @Autowired
    private PageService pageService;

    public static class SearchSpecification {
        public static final SearchSpecification ALL = new SearchSpecification();

        public boolean url = true;
        public boolean content = true;
        public boolean title = true;
        public boolean description = true;

        public void reset() {
            url = false;
            content = false;
            title = false;
            description = false;
        }
    }

    public Set<Page> search(Project project, String ... keywords) {
        return search(project, SearchSpecification.ALL, keywords);
    }

    public Set<Page> search(Project project, SearchSpecification searchSpecification, String ... keywords) {
        Set<Page> result = new HashSet<>();

        if (searchSpecification.url) {
            result.addAll(pageService.findByKeywordsInUrl(project, keywords));
        }

        if (searchSpecification.title) {
            result.addAll(pageService.findByKeywordsInTitle(project, keywords));
        }

        if (searchSpecification.description) {
            result.addAll(pageService.findByKeywordsInDescription(project, keywords));
        }

        if (searchSpecification.content) {
            result.addAll(pageService.findByKeywordsInContent(project, keywords));
        }

        return result;
    }
}

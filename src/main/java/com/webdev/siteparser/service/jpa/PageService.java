package com.webdev.siteparser.service.jpa;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    public Page save(Page page) {
        long projectId = page.getProject().getId();
        String pageUrl = page.getUrl();

        Page existingPage = pageRepository.findPageByUrl(projectId, pageUrl);
        if (existingPage != null && page.getId() == 0) {
            return page;
        }

        pageRepository.save(page);

        return page;
    }

    public Page getById(long id) {
        return pageRepository.findById(id).get();
    }

    public List<Page> getAll(long projectId) {
        return pageRepository.getPagesByProjectId(projectId);
    }

    public void delete(Page page) {
        pageRepository.delete(page);
    }

    public Page findPagesByUrl(long projectId, String url) {
        return pageRepository.findPageByUrl(projectId, url);
    }

    public boolean hasProjectUnparsedPages(Project p) {
        return pageRepository.getUnparsedProjectPages(p.getId()).size() > 0;
    }

    public int getPageCount(long projectId) {
        return pageRepository.getPageCount(projectId);
    }

    public List<Page> getUnparsedProjectPages(Project p) {
        return pageRepository.getUnparsedProjectPages(p.getId());
    }

    public List<Page> getPage(long projectid, int pageNumber, int count) {
        int offset = pageNumber * count;

        return pageRepository.getPagesByProjectIdWithOffset(projectid, offset, count);
    }

    public void clean() {
        pageRepository.deleteAll();
    }

    public Set<Page> findByKeywordsInUrl(Project project, String[] keywords) {
        Set<Page> result = new HashSet<>();
        for(String keyword: keywords) {
            result.addAll(pageRepository.getByKeywordInUrl(project.getId(), keyword));
        }
        return result;
    }

    public Set<Page> findByKeywordsInTitle(Project project, String[] keywords) {
        Set<Page> result = new HashSet<>();
        for(String keyword: keywords) {
            result.addAll(pageRepository.getByKeywordInTitle(project.getId(), keyword));
        }
        return result;
    }

    public Set<Page> findByKeywordsInDescription(Project project, String[] keywords) {
        Set<Page> result = new HashSet<>();
        for(String keyword: keywords) {
            result.addAll(pageRepository.getByKeywordInDescription(project.getId(), keyword));
        }
        return result;
    }

    public Set<Page> findByKeywordsInContent(Project project, String[] keywords) {
        Set<Page> result = new HashSet<>();
        for(String keyword: keywords) {
            result.addAll(pageRepository.getByKeywordInContent(project.getId(), keyword));
        }
        return result;
    }
}

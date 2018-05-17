package com.webdev.siteparser.service.jpa;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    public Page save(Page page) {
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
}

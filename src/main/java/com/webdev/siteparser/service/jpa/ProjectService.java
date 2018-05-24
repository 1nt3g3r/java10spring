package com.webdev.siteparser.service.jpa;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PageService pageService;

    public boolean exists(long projectId) {
        return projectRepository.existsById(projectId);
    }

    public Project save(Project project) {
        projectRepository.save(project);

        if (project.getPages().size() == 0) {
            Page indexPage = new Page();
            indexPage.setUrl(project.getDomain());
            indexPage.setProject(project);
            pageService.save(indexPage);
        }

        return project;
    }

    public Project getById(long id) {
        return projectRepository.findById(id).get();
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public List<Project> getProjectsWithEnabledParsing() {
        return projectRepository.getProjectWithParsingEnabled();
    }

    public void clean() {
        projectRepository.deleteAll();
    }
}

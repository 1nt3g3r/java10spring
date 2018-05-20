package com.webdev.siteparser.repository;

import com.webdev.siteparser.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("from Project p where p.parsingEnabled = true")
    List<Project> getProjectWithParsingEnabled();
}

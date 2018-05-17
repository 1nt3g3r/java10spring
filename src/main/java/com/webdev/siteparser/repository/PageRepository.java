package com.webdev.siteparser.repository;

import com.webdev.siteparser.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    @Query("from Page p where p.url = :url and p.project.id = :projectId")
    Page findPageByUrl(@Param("projectId") long projectId, @Param("url") String url);

    @Query("from Page p where p.project.id = :projectId")
    List<Page> getPagesByProjectId(@Param("projectId") long projectId);
}
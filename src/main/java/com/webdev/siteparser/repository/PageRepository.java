package com.webdev.siteparser.repository;

import com.webdev.siteparser.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends PagingAndSortingRepository<Page, Long> {
    @Query("select count(p.id) from Page p where p.project.id= :projectId")
    int getPageCount(@Param("projectId") long projectId);

    @Query(nativeQuery = true, value = "select * from page where project_id = :projectId limit :count offset :offset")
    List<Page> getPagesByProjectIdWithOffset(@Param("projectId") long projectId, @Param("offset") int offset, @Param("count") int count);

    @Query("from Page p where p.url = :url and p.project.id = :projectId")
    Page findPageByUrl(@Param("projectId") long projectId, @Param("url") String url);

    @Query("from Page p where p.project.id = :projectId")
    List<Page> getPagesByProjectId(@Param("projectId") long projectId);

    @Query("from Page p where p.project.id = :projectId and p.content = null")
    List<Page> getUnparsedProjectPages(@Param("projectId") long projectId);

    @Query("from Page p where p.project.id = :projectId and p.url like %:keyword%")
    List<Page> getByKeywordInUrl(@Param("projectId") long projectId, @Param("keyword") String keyword);

    @Query("from Page p where p.project.id = :projectId and p.title like %:keyword%")
    List<Page> getByKeywordInTitle(@Param("projectId") long projectId, @Param("keyword") String keyword);

    @Query("from Page p where p.project.id = :projectId and p.content like %:keyword%")
    List<Page> getByKeywordInContent(@Param("projectId") long projectId, @Param("keyword") String keyword);

    @Query("from Page p where p.project.id = :projectId and p.description like %:keyword%")
    List<Page> getByKeywordInDescription(@Param("projectId") long projectId, @Param("keyword") String keyword);
}
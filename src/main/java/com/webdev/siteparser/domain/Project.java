package com.webdev.siteparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "domain")
    private String domain;

    @Column(name = "parsingEnabled")
    private boolean parsingEnabled;

    @JsonIgnore
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Page> pages = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public boolean isParsingEnabled() {
        return parsingEnabled;
    }

    public void setParsingEnabled(boolean parsingEnabled) {
        this.parsingEnabled = parsingEnabled;
    }
}

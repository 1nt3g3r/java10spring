package com.webdev.siteparser.service.parse;

import com.webdev.siteparser.domain.Project;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExtractLinksService {
    @Autowired
    private UrlService urlService;

    public List<String> parseLinks(Project project, Document document) {
        List<String> result = new ArrayList<>();

        Elements links = document.select("a");
        for(Element link: links) {
            String href = link.absUrl("href");
            if (href.startsWith(project.getDomain())) {

                href = urlService.normalize(href);

                result.add(href);
            }
        }

        return result;
    }
}

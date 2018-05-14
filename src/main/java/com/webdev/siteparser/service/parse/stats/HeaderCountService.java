package com.webdev.siteparser.service.parse.stats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class HeaderCountService {
    public static final String[] HEADER_TAGS = {
            "h1", "h2", "h3", "h4", "h5", "h6"
    };

    public int getHeaderCount(String html) {
        Document document = Jsoup.parse(html);

        int count = 0;
        for(String headerTag: HEADER_TAGS) {
            int headerCount = document.select(headerTag).size();

            count += headerCount;
        }

        return count;
    }
}

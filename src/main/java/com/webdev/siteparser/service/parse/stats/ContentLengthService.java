package com.webdev.siteparser.service.parse.stats;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

@Service
public class ContentLengthService {
    public int getContentLengthWithoutSpaces(String html) {
        String text = Jsoup.parse(html).body().text();
        return cleanText(text).length();
    }

    private String cleanText(String text) {
        return text.replace(" ", "");
    }
}

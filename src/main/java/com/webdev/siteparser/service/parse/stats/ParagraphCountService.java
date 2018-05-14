package com.webdev.siteparser.service.parse.stats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class ParagraphCountService {
    public static final int MIN_CHAR_COUNT = 100;

    public int getParagraphCount(String html) {
        Elements paragraphs = Jsoup.parse(html).body().select("p");

        int count = 0;
        for(Element paragraph: paragraphs) {
            int length = paragraph.text().length();
            if (length >= MIN_CHAR_COUNT) {
                count++;
            }
        }

        return count;
    }
}

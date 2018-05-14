package com.webdev.siteparser.service.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlLoadService {
    public Document loadDocument(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public String loadHtml(String url) throws IOException {
        return loadDocument(url).html();
    }
}

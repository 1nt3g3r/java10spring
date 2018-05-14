package com.webdev.siteparser.service.parse.stats.language;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnlineLanguageDetectService implements LanguageDetectService {
    private String apiKey = "c2e3bf1bb63c2ccb9adfb08e6c101f1f";

    public OnlineLanguageDetectService() {
        DetectLanguage.apiKey = apiKey;
    }

    @Override
    public String detectLanguage(String html) {
        String text = Jsoup.parse(html).body().text();

        try {
            List<Result> results = DetectLanguage.detect(text);

            Result result = results.get(0);

            return result.language;
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }
}

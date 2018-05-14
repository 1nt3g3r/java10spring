package com.webdev.siteparser.service.parse.stats.language;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HeuristicLanguageDetectService implements LanguageDetectService {
    private Map<String, String> languageSpecificChars;

    public HeuristicLanguageDetectService() {
        languageSpecificChars = new HashMap<>();

        languageSpecificChars.put("uk", "їєґі");
        languageSpecificChars.put("ru", "ёъыэ");
    }

    public String detectLanguage(String html) {
        String text = Jsoup.parse(html).body().text().toLowerCase();

        Map<String, Integer> charCounts = new HashMap<>();

        for(String language: languageSpecificChars.keySet()) {
            char[] languageChars = languageSpecificChars.get(language).toCharArray();

            for(char c: languageChars) {
                int charCount = getCharCount(c, text);

                int oldValue = charCounts.getOrDefault(language, 0);
                oldValue += charCount;
                charCounts.put(language, oldValue);
            }
        }

        int maxCharCount = 0;
        String maxCharCountLanguage = "unknown";
        for(String language: languageSpecificChars.keySet()) {
            int count = charCounts.get(language);
            if (count > maxCharCount) {
                maxCharCount = count;
                maxCharCountLanguage = language;
            }
        }

        int foundCount = 0;
        for(String language: languageSpecificChars.keySet()) {
            if (charCounts.get(language) == maxCharCount) {
                foundCount++;
            }
        }

        if (maxCharCount == 0) {
            return "en";
        }

        if (foundCount > 1) {
            return "unknown";
        }

        return maxCharCountLanguage;
    }

    private int getCharCount(char c, String line) {
        int count = 0;

        for(char tmp: line.toCharArray()) {
            if(c == tmp) {
                count++;
            }
        }
        return count;
    }
}

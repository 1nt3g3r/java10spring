package com.webdev.siteparser.service.convert;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LanguageCodeToReadableNameService {
    private Map<String, String> languageMap;

    public LanguageCodeToReadableNameService() {
        languageMap = new HashMap<>();

        languageMap.put("en", "Англійська");
        languageMap.put("uk", "Українська");
        languageMap.put("ru", "Російська");
    }

    public String convertLanguageCode(String code) {
        if (languageMap.containsKey(code)) {
            return languageMap.get(code);
        } else {
            return code;
        }
    }
}

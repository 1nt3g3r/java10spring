package com.webdev.siteparser.service.convert;

import org.springframework.stereotype.Service;

@Service
public class ContentFormatService {
    public String prepareToShow(String text) {
        if (text == null) {
            return "";
        }

        if (text.length() < 500) {
            return text;
        }

        return text.substring(0, 500);
    }
}

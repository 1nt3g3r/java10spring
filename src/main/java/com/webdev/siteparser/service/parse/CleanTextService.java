package com.webdev.siteparser.service.parse;

import org.springframework.stereotype.Service;

@Service
public class CleanTextService {
    public String clean(String text) {
        if (text == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for(char c: text.toCharArray()) {
            if (isCharValid(c)) {
                result.append(c);
            }
        }
        return result.toString();
    }

    private boolean isCharValid(char c) {
        if (Character.isDigit(c)) {
            return true;
        }

        if (Character.isAlphabetic(c)) {
            return true;
        }

        if (Character.isLetterOrDigit(c)) {
            return true;
        }

        if (Character.isSpaceChar(c)) {
            return true;
        }

        return false;
    }
}

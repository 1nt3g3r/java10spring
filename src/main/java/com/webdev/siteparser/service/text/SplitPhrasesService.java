package com.webdev.siteparser.service.text;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SplitPhrasesService {
    private static final char[] END_PHRASE_PUNCTUATION_MARKS = {'.', '?', '!'};

    public List<String> splitToPhrases(String text) {
        text = clean(text);

        List<String> phrases = new ArrayList<>();

        List<Integer> breakpoints = new ArrayList<>();
        breakpoints.add(0);

        //Find breakpoints
        for(int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (isEndOfPhrasePunctuationMark(currentChar) && i < text.length() - 1) {
                char nextChar = text.charAt(i + 1);
                if (nextChar == ' ') {
                    if (i < text.length() - 2) { //Move up two char more
                        char charPlus2 = text.charAt(i + 2);
                        if (Character.isLetter(charPlus2) && Character.isUpperCase(charPlus2)) {
                            breakpoints.add(i + 1);
                        }
                    } else {
                        breakpoints.add(i + 1);
                    }
                }
            }
        }

        breakpoints.add(text.length());

        //Split to phrases
        for(int i = 0; i < breakpoints.size() - 1; i++) {
            int startPosition = breakpoints.get(i);
            int endPosition = breakpoints.get(i + 1);
            String phrase = text.substring(startPosition, endPosition);
            phrases.add(phrase.trim());
        }

        return phrases;
    }

    private boolean isEndOfPhrasePunctuationMark(char c) {
        for(char punctuationMark: END_PHRASE_PUNCTUATION_MARKS) {
            if (punctuationMark == c) {
                return true;
            }
        }

        return false;
    }

    private String clean(String text) {
        boolean needClean = true;

        while(needClean) {
            needClean = false;
            int oldSize = text.length();
            text = text.replace("  ", " ");
            int newSize = text.length();

            if (oldSize != newSize) {
                needClean = true;
            }
        }

        return text.trim();
    }
}

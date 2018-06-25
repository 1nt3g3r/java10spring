package com.webdev.siteparser.service.text;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SplitWordsService {
    private static final String[] PUNCTUATION_MARKS = {".", ",", "?", "!", ":", ";", "-", "(", ")", "[", "]"};

    public List<String> splitToWords(String phrase) {
        for(String punctuationMark: PUNCTUATION_MARKS) {
            phrase = phrase.replace(punctuationMark, "");
        }

        List<String> result = new ArrayList<>();

        String[] words = phrase.split(" ");
        for(String word: words) {
            if (word.length() > 0) {
                result.add(word.toLowerCase());
            }
        }

        return result;
    }
}

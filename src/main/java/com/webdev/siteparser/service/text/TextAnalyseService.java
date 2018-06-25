package com.webdev.siteparser.service.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextAnalyseService {
    @Autowired
    private SplitPhrasesService splitPhrasesService;

    @Autowired
    private SplitWordsService splitWordsService;

    //Кількість символів загальна
    public int getTotalCharCount(String text) {
        return text.length();
    }

    //Кількість символів без пробілів
    public int getCharCountWithoutSpaces(String text) {
        int result = 0;
        for(char c: text.toCharArray()) {
            if (!Character.isSpaceChar(c)) {
                result++;
            }
        }
        return result;
    }

    //Кількість фраз
    public int getPhraseCount(String text) {
        return splitPhrasesService.splitToPhrases(text).size();
    }

    //Загальна кількість слів у тексті
    public int getTotalWordCount(String text) {
        return splitWordsService.splitToWords(text).size();
    }

    //Середня кількість слів в одній фразі
    public float getAverageWordCountInPhrase(String text) {
        float average = (float) getTotalWordCount(text) / (float) getPhraseCount(text);
        return round10(average);
    }

    private float round10(float average) {
        int intAvg = Math.round(average * 10f);

        return (float) intAvg / 10f;
    }

    //Середня довжина слова
    public float getAverageWordLength(String text) {
        List<String> words = splitWordsService.splitToWords(text);

        int totalCharCount = 0;
        for(String word: words) {
            totalCharCount += word.length();
        }

        float averageWordLength = (float) totalCharCount / (float) words.size();

        return round10(averageWordLength);
    }
}

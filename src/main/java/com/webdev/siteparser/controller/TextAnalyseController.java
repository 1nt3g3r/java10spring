package com.webdev.siteparser.controller;

import com.webdev.siteparser.service.text.TextAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/text-analyse")
public class TextAnalyseController {
    @Autowired
    private TextAnalyseService textAnalyseService;

    @GetMapping
    public String showPage() {
        return "text-analyse";
    }

    @PostMapping
    public ModelAndView analyseText(@RequestParam String text) {
        ModelAndView result = new ModelAndView("text-analyse");

        result.addObject("text", text);

        //Кількість символів загальна
        result.addObject("totalCharCount", textAnalyseService.getTotalCharCount(text));

        //Кількість символів без пробілів
        result.addObject("charCountWithoutSpaces", textAnalyseService.getCharCountWithoutSpaces(text));

        //Кількість фраз
        result.addObject("phraseCount", textAnalyseService.getPhraseCount(text));

        //Загальна кількість слів у тексті
        result.addObject("totalWordCount", textAnalyseService.getTotalWordCount(text));

        //Середня кількість слів в одній фразі
        result.addObject("averageWordCountInPhrase", textAnalyseService.getAverageWordCountInPhrase(text));

        //Середня довжина слова
        result.addObject("averageWordLength", textAnalyseService.getAverageWordLength(text));

        return result;
    }
}

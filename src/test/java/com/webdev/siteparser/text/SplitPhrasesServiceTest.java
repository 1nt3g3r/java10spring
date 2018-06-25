package com.webdev.siteparser.text;

import com.webdev.siteparser.service.text.SplitPhrasesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SplitPhrasesServiceTest {
    @Autowired
    private SplitPhrasesService phrasesService;

    @Test
    public void testOnePhraseText() {
        testSplit("It's world.", "It's world.");
    }

    @Test
    public void testTwoPhrasesText() {
        testSplit("It's pain. Really?", "It's pain.", "Really?");
    }

    @Test
    public void testTwoPhrasesWithMultipleSpaces() {
        testSplit("It's pain.           Really?", "It's pain.", "Really?");
    }

    @Test
    public void testThreePhrasesWithPunctuationMarks() {
        testSplit("Hello? Anybody! Okay.", "Hello?", "Anybody!", "Okay.");
    }

    @Test
    public void testPhrasesWithMultipleDots() {
        testSplit("Okay... it was good! But I should go.", "Okay... it was good!", "But I should go.");
    }

    private void testSplit(String text, String ... expectedPhrases) {
        List<String> expectedPhrasesList = Arrays.asList(expectedPhrases);

        List<String> actualPhrasesList = phrasesService.splitToPhrases(text);

        Assert.assertEquals(expectedPhrasesList, actualPhrasesList);
    }
}

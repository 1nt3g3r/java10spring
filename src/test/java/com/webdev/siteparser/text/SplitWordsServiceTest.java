package com.webdev.siteparser.text;

import com.webdev.siteparser.service.text.SplitWordsService;
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
public class SplitWordsServiceTest {
    @Autowired
    private SplitWordsService wordsService;

    @Test
    public void testOneWordCase() {
        testPhrase("word", "word");
    }

    @Test
    public void testTwoWordsCase() {
        testPhrase("two words", "two", "words");
    }

    @Test
    public void testTwoWordsWithAdditionalSpace() {
        testPhrase("There is additional  space", "there", "is", "additional", "space");
    }

    @Test
    public void testSplitTwoWordsWithDot() {
        testPhrase("It is pain.", "it", "is", "pain");
    }

    @Test
    public void testPhraseWithMultiplePunctuationMarks() {
        testPhrase("Hello, it's the best thing in the world?! ",
                "hello", "it's", "the", "best", "thing", "in", "the", "world");
    }

    private void testPhrase(String phrase, String ... expectedWords) {
        List<String> expectedWordsList = Arrays.asList(expectedWords);

        List<String> actualWordsList = wordsService.splitToWords(phrase);

        Assert.assertEquals(expectedWordsList, actualWordsList);
    }
}

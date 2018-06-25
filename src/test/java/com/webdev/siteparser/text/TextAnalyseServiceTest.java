package com.webdev.siteparser.text;

import com.webdev.siteparser.service.text.TextAnalyseService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TextAnalyseServiceTest {
    @Autowired
    private TextAnalyseService textAnalyseService;

    @Test
    public void testTotalCharCount() {
        String text = "This is some text. The end.";

        int expectedLength = 27;
        int actualLength = textAnalyseService.getTotalCharCount(text);

        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testCharCountWithoutSpaces() {
        String text = "This is some text.";

        int expectedLength = 15;
        int actualLength = textAnalyseService.getCharCountWithoutSpaces(text);

        Assert.assertEquals(expectedLength, actualLength);
    }

    @Test
    public void testPhraseCount() {
        String text = "Phrase A. Phrase B. And phrase C.";

        int expectedPhraseCount = 3;
        int actualPhraseCount = textAnalyseService.getPhraseCount(text);

        Assert.assertEquals(expectedPhraseCount, actualPhraseCount);
    }

    @Test
    public void testWordCount() {
        String text = "This is some text. Yes, it is text.";

        int expectedWordCount = 8;
        int actualWordCount = textAnalyseService.getTotalWordCount(text);

        Assert.assertEquals(expectedWordCount, actualWordCount);
    }

    @Test
    public void testAverageWordCountInPhrase() {
        String text = "Phrase A. And phrase B.";

        float expectedAverageWordCount = 2.5f;
        float actualAverageWordCount = textAnalyseService.getAverageWordCountInPhrase(text);

        Assert.assertEquals(expectedAverageWordCount, actualAverageWordCount, Float.MIN_VALUE);
    }

    @Test
    public void testAverageWordLengthInText() {
        String text = "This is some long text. Really long.";

        float expectedAverageWordLength = 4;
        float actualAverageWordLength = textAnalyseService.getAverageWordLength(text);

        Assert.assertEquals(expectedAverageWordLength, actualAverageWordLength, Float.MIN_VALUE);
    }
}

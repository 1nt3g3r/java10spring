package com.webdev.siteparser.service.cli;

import com.webdev.siteparser.service.convert.LanguageCodeToReadableNameService;
import com.webdev.siteparser.service.parse.HtmlLoadService;
import com.webdev.siteparser.service.parse.MetatagsService;
import com.webdev.siteparser.service.parse.stats.ContentLengthService;
import com.webdev.siteparser.service.parse.stats.HeaderCountService;
import com.webdev.siteparser.service.parse.stats.ParagraphCountService;
import com.webdev.siteparser.service.parse.stats.language.LanguageDetectService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class HtmlAnalyseCLI {
    @Autowired
    private HtmlLoadService htmlLoadService;

    @Autowired
    private MetatagsService metatagsService;

    @Autowired
    private ContentLengthService contentLengthService;

    @Autowired
    private ParagraphCountService paragraphCountService;

    @Autowired
    private HeaderCountService headerCountService;

    @Qualifier("onlineLanguageDetectService")
    @Autowired
    private LanguageDetectService languageDetectService;

    @Autowired
    private LanguageCodeToReadableNameService codeToReadableNameService;

    private Map<String, CLICommandHandler> handlers;

    public HtmlAnalyseCLI(ApplicationContext context) {
        handlers = new HashMap<>();

        handlers.put("exit", context.getBean(ExitCLICommandHandler.class));
        handlers.put("help", context.getBean(HelpCLICommandHandler.class));
        handlers.put("listProjects", context.getBean(ListProjectsCLIHandler.class));
        handlers.put("search", context.getBean(SearchProjectCLIHandler.class));
    }

    public void start() {
        System.out.println("Starting CLI...");
        System.out.println("Type URL and press Enter");
        System.out.println("Type help to see info");

        Scanner scanner = new Scanner(System.in);
        while(true) {
            String command = scanner.nextLine();

            if (command.startsWith("http://") || command.startsWith("https://")) {
                printPageStats(command);
            } else {
                handleCommand(command);
            }
        }
    }
    //searchProject <ID> key1 key2
    private void handleCommand(String commandLine) {
        String[] commandParts = commandLine.split(" ");
        String command = commandParts[0];

        if (handlers.containsKey(command)) {
            handlers.get(command).handleCommand(commandLine, this);
        } else {
            System.out.println("Unknown command: <" + commandLine + ">. Type <help> to get help");
        }
    }

    public void printPageStats(String url) {
        try {
            Document doc = htmlLoadService.loadDocument(url);

            String title = metatagsService.parseTitle(doc);
            String description = metatagsService.parseDescription(doc);

            String html = doc.html();

            int contentLength = contentLengthService.getContentLengthWithoutSpaces(html);
            int paragraphCount = paragraphCountService.getParagraphCount(html);
            int headerCount = headerCountService.getHeaderCount(html);
            String language = languageDetectService.detectLanguage(html);

            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Content length: " + contentLength);
            System.out.println("Paragraph count: " + paragraphCount);
            System.out.println("Header count: " + headerCount);
            System.out.println("Language: " + codeToReadableNameService.convertLanguageCode(language));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
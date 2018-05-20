package com.webdev.siteparser.service.parser;

import com.webdev.siteparser.domain.Page;
import com.webdev.siteparser.domain.Project;
import com.webdev.siteparser.service.jpa.PageService;
import com.webdev.siteparser.service.jpa.ProjectService;
import com.webdev.siteparser.service.parse.CleanTextService;
import com.webdev.siteparser.service.parse.ExtractLinksService;
import com.webdev.siteparser.service.parse.HtmlLoadService;
import com.webdev.siteparser.service.parse.MetatagsService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ParserService {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private PageService pageService;

    @Autowired
    private HtmlLoadService htmlLoadService;

    @Autowired
    private MetatagsService metatagsService;

    @Autowired
    private ExtractLinksService extractLinksService;

    @Autowired
    private CleanTextService cleanTextService;

    @Scheduled(fixedDelay = 1000)
    public void parse() {
        List<Project> projectsToParse = projectService.getProjectsWithEnabledParsing();

        for(Project project: projectsToParse) {
            if (pageService.hasProjectUnparsedPages(project)) {
                parseProject(project);
                break;
            }
        }
    }

    private void parseProject(Project project) {
        //1. Взяти першу сторінку, яка ще не пропарсена
        List<Page> pagesToParse = pageService.getUnparsedProjectPages(project);
        Page firstPage = pagesToParse.get(0);
        parsePage(firstPage);
    }

    //2. Пропарсити цю сторінку, зберегти інформацію з неї в БД
    private void parsePage(Page page) {
        String url = page.getUrl();

        try {
            Document htmlDocument = htmlLoadService.loadDocument(url);

            String content = htmlDocument.body().text();
            String title = metatagsService.parseTitle(htmlDocument);
            String description = metatagsService.parseDescription(htmlDocument);

            content = cleanTextService.clean(content);
            title = cleanTextService.clean(title);
            description = cleanTextService.clean(description);

            page.setContent(content);
            page.setTitle(title);
            page.setDescription(description);

            pageService.save(page);

            //3. Витягнути з цієї сторінки всі посилання на сайт з таким же доменним ім'ям
            List<String> links = extractLinksService.parseLinks(page.getProject(), htmlDocument);
            for(String link: links) {
                Page linkPage = new Page();
                linkPage.setUrl(link);
                linkPage.setProject(page.getProject());
                pageService.save(linkPage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

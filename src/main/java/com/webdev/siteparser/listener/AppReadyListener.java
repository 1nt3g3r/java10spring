package com.webdev.siteparser.listener;

import com.webdev.siteparser.service.cli.HtmlAnalyseCLI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppReadyListener {
    @Value("${launchMode}")
    private String launchMode;

    @Autowired
    private HtmlAnalyseCLI cli;

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {
        if (launchMode != null && launchMode.equals("cli")) {
            cli.start();
        }
    }
}

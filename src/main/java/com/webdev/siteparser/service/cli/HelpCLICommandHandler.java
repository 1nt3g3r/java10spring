package com.webdev.siteparser.service.cli;

import org.springframework.stereotype.Service;

@Service
public class HelpCLICommandHandler implements CLICommandHandler {
    @Override
    public void handleCommand(String command, HtmlAnalyseCLI cli) {
        System.out.println("Command line interface utility to analyse HTML pages");
        System.out.println("Enter full URL (including http:// or https://) and press ENTER");
    }
}

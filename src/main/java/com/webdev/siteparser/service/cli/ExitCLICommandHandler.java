package com.webdev.siteparser.service.cli;

import org.springframework.stereotype.Service;

@Service("exitCLI")
public class ExitCLICommandHandler implements CLICommandHandler {
    @Override
    public void handleCommand(String command, HtmlAnalyseCLI cli) {
        System.out.println("Bye!");
        System.exit(0);
    }
}

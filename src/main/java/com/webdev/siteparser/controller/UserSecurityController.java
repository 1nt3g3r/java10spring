package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.User;
import com.webdev.siteparser.service.jpa.UserService;
import com.webdev.siteparser.service.security.UserValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserSecurityController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidateService userValidateService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute User user) {
        UserValidateService.ValidateResult result = userValidateService.validate(user);

        if (result == UserValidateService.ValidateResult.ok) {
            userService.addNewUser(user);
            return new ModelAndView("redirect:/login");
        } else {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", result.getStringRepresenation());
            return modelAndView;
        }
    }
}

package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.User;
import com.webdev.siteparser.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController extends BaseSecurityController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public ModelAndView dashboard() {
        ModelAndView result = createModelAndView("admin/dashboard");
        result.addObject("users", userService.getAllUsers());
        return result;
    }

    @GetMapping("/admin/user/setActiveState")
    public String setUserActiveState(@RequestParam("email") String email, @RequestParam("active") int active) {
        User user = userService.getByEmail(email);
        if (user != null) {
            user.setActive(active);
            userService.update(user);
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/user/delete")
    public String deleteUser(@RequestParam("email") String email) {
        User user = userService.getByEmail(email);
        if (user != null) {
            userService.delete(user);
        }

        return "redirect:/admin";
    }
}

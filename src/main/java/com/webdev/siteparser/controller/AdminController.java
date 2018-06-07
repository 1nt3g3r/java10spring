package com.webdev.siteparser.controller;

import com.webdev.siteparser.domain.User;
import com.webdev.siteparser.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/admin/user/edit")
    public ModelAndView editUser(@RequestParam("email") String email) {
        ModelAndView result = createModelAndView("admin/user-edit");
        result.addObject("editUser", userService.getByEmail(email));
        return result;
    }

    @PostMapping("/admin/user/edit")
    public String handleEditUser(
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "password", required = false) String password) {

        User user = userService.getByEmail(email);

        if (user != null) {
            if (checkFilled(name)) {
                user.setName(name);
            }

            if (checkFilled(lastName)) {
                user.setLastName(lastName);
            }

            if (checkFilled(password)) {
                userService.setPassword(user, password);
            }

            userService.update(user);
        }

        return "redirect:/admin";
    }

    @GetMapping("/admin/user/add")
    public ModelAndView addUser() {
        return createModelAndView("admin/user-add");
    }

    @PostMapping("/admin/user/add")
    public ModelAndView handleAddUser(@ModelAttribute User user) {
        if (userService.getByEmail(user.getEmail()) != null) {
            ModelAndView result = createModelAndView("admin/user-add");
            result.addObject("error", "Користувач з таким email вже існує");
            return result;
        } else {
            userService.addNewUser(user);
            return new ModelAndView("redirect:/admin");
        }
    }

    private boolean checkFilled(String value) {
        if (value == null) {
            return false;
        }

        if (value.trim().length() == 0) {
            return false;
        }

        return true;
    }
}

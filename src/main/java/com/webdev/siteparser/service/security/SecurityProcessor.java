package com.webdev.siteparser.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Service
public class SecurityProcessor {
    public ModelAndView createModelAndView(String viewName) {
        ModelAndView result = new ModelAndView(viewName);
        result.addObject("user", getUser());
        result.addObject("roles", getUserRolesAsString());

        return result;
    }

    private User getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication == null) {
            return null;
        }

        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            return null;
        }

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        if (token == null) {
            return null;
        }

        User user = (User) token.getPrincipal();

        if (user == null) {
            return null;
        }

        return user;
    }

    public String getUserRolesAsString() {
        User user = getUser();
        if (user == null) {
            return null;
        }

        Collection<GrantedAuthority> authorities = getUser().getAuthorities();

        StringBuilder result = new StringBuilder();
        for(GrantedAuthority authority: authorities) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(authority.getAuthority());
        }
        return result.toString();
    }
}

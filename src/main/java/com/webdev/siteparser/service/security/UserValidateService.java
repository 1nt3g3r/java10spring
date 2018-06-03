package com.webdev.siteparser.service.security;

import com.webdev.siteparser.domain.User;
import com.webdev.siteparser.service.jpa.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidateService {
    @Autowired
    private UserService userService;

    public enum ValidateResult {
        ok,
        shortPassword,
        invalidEmail,
        userExists;

        public String getStringRepresenation() {
            switch (this) {
                case ok: return "Ok";
                case shortPassword: return "Короткий пароль. Довжина паролю має складати хоча б 5 символів";
                case invalidEmail: return "Неправильний email";
                case userExists: return "Користувач з таким email вже існує";
                default: return "";
            }
        }
    }

    public ValidateResult validate(User user) {
        if (user.getPassword().length() < 5) {
            return ValidateResult.shortPassword;
        }

        if (!isValidEmailAddress(user.getEmail())) {
            return ValidateResult.invalidEmail;
        }

        if (userService.getByEmail(user.getEmail()) != null) {
            return ValidateResult.userExists;
        }

        return ValidateResult.ok;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}

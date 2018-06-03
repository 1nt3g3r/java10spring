package com.webdev.siteparser.service.jpa;

import com.webdev.siteparser.domain.Role;
import com.webdev.siteparser.domain.User;
import com.webdev.siteparser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void addNewUser(User user) {
        Role userRole = roleService.getRoleByName("USER");

        user.getRoles().add(userRole);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}

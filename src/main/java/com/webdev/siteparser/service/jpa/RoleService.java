package com.webdev.siteparser.service.jpa;

import com.webdev.siteparser.domain.Role;
import com.webdev.siteparser.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

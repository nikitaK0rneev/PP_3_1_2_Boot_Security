package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    UserService userService;
    RoleService roleService;

    @Autowired
    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role adminRole = new Role("ADMIN");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        roleService.save(adminRole);

        Role userRole = new Role("USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        roleService.save(userRole);

        User admin = new User("admin1", 22, "admin", adminRoles);

        userService.createUser(admin);

        User user = new User("user1", 33, "user", userRoles);

        userService.createUser(user);
    }

}

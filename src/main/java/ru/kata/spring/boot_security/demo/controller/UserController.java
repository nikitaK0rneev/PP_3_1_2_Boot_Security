package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String redirectToIndex() {
        return "index";
    }

    @GetMapping(value = "/admin")
    public String listUsers(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping(value = "/add")
    public String showCreateUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "add";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/remove")
    public String deleteUserById(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String showEditUserForm(@RequestParam("id") int id, ModelMap model) {
        User user = userService.find(id);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUserPage(ModelMap model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        return "user";
    }

}

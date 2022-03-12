package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("roles", roleService.allRoles());
        return "index";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("role", roleService.allRoles());
        return "index";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, @RequestParam("nameRoles") String[] nameRoles) {
        user.setRoles(roleService.getRoleSet(nameRoles));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("role", roleService.allRoles());
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute User user, @RequestParam("nameRoles") String[] nameRoles) {
        user.setRoles(roleService.getRoleSet(nameRoles));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}

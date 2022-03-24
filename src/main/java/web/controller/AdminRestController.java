package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PatchMapping("/users/{id}")
    public User editUser(@PathVariable("id") int id, @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }

}


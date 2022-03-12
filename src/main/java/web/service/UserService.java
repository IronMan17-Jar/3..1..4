package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();

    void save(User user);

    void delete(int id);

    User update(User user);

    User getById(int id);

    User getUserByName(String name);
}
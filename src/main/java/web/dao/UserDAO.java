package web.dao;

import web.model.User;

import java.util.List;

public interface UserDAO {
    List<User> allUsers();

    void save(User user);

    void delete(int id);

    void update(User user);

    User getById(int id);

    User getUserByName(String name);
}

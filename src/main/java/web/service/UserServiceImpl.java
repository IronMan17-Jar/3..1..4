package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.model.User;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    @Override
    public List<User> allUsers() {
        return userDAO.allUsers();
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Transactional
    @Override
    public void update(User user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        userDAO.update(user);
    }

    @Transactional
    @Override
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Transactional
    @Override
    public User getUserByName(String name) {
        return userDAO.getUserByName(name);
    }
}


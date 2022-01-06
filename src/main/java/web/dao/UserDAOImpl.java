package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        List<User> resultList = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return resultList;
    }

    @Override
    public void save(User user) {
        User merge = entityManager.merge(user);
        entityManager.persist(merge);
    }

    @Override
    public void delete(int id) {
        entityManager.createQuery("delete from User o where o.id=:id").setParameter("id", id).executeUpdate();

    }

    @Override
    public void update(User user) {//int id
        //       User updatedUser = getById(id);
//        updatedUser.setName(user.getName());
//        updatedUser.setLastName(user.getLastName());
//        updatedUser.setAge(user.getAge());
        entityManager.merge(user);
    }

    @Override
    public User getById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByName(String name) {
        return entityManager
                .createQuery(" SELECT u FROM User u WHERE u.name =:name", User.class)
                .setParameter("name", name)
                .getSingleResult();

    }
}


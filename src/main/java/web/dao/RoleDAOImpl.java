package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Role> getAllRoles() {
        List<Role> resultList = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return resultList;
    }

    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager
                .createQuery(" SELECT r FROM Role r WHERE r.role =:parName")
                .setParameter("parName", name)
                .getSingleResult();
    }

    @Override
    public HashSet<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String role : roleNames) {
            roleSet.add(getRoleByName(role));
        }
        return (HashSet) roleSet;
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void update(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getById(long id) {
        return entityManager.find(Role.class, id);
    }
}


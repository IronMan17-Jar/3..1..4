package web.dao;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    List<Role> getAllRoles();

    Role getRoleByName(String name);

    Set<Role> getSetOfRoles(String[] roleNames);

    void add(Role role);

    void update(Role role);

    Role getById(long id);
}
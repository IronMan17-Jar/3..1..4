package web.service;

import web.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> allRoles();

    void save(Role role);

    void update(Role role);

    Role getById(int id);

    Role getByName(String roleName);

    public Set<Role> getRoleSet(String[] role);

}

package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDAO;
import web.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    public List<Role> allRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public void save(Role role) {
        roleDAO.add(role);
    }

    @Override
    public void update(Role role) {
        roleDAO.update(role);
    }

    @Override
    public Role getById(int id) {
        return roleDAO.getById(id);
    }

    @Override
    public Role getByName(String roleName) {
        return roleDAO.getRoleByName(roleName);
    }

    @Override
    public HashSet getRoleSet(String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String roles : role) {
            roleSet.add(roleDAO.getRoleByName(roles));
        }
        return (HashSet) roleSet;
    }
}

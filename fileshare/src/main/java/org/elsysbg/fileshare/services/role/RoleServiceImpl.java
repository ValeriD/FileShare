package org.elsysbg.fileshare.services.role;

import org.elsysbg.fileshare.dao.role.RoleDao;
import org.elsysbg.fileshare.models.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Optional<Role> findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public Role create(Role role) {
        return roleDao.create(role);
    }
}

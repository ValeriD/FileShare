package org.elsysbg.fileshare.services.role;

import org.elsysbg.fileshare.models.Role;
import org.elsysbg.fileshare.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }
}

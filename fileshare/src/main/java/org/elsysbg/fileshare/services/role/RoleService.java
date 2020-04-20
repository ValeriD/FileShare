package org.elsysbg.fileshare.services.role;

import org.elsysbg.fileshare.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
    Role create(Role role);
}

package org.elsysbg.fileshare.dao.user;

import org.elsysbg.fileshare.dao.IOperations;
import org.elsysbg.fileshare.models.User;

import java.util.Optional;

public interface UserDao extends IOperations<User> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}

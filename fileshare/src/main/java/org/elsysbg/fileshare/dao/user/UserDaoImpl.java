package org.elsysbg.fileshare.dao.user;

import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User create(User entity) {
        return userRepository.save(entity);
    }

    @Transactional
    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Transactional
    @Override
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Transactional
    @Override
    public void deleteById(long entityId) {
        userRepository.deleteById(entityId);
    }
}

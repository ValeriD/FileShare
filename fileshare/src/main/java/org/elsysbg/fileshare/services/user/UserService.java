package org.elsysbg.fileshare.services.user;

import org.elsysbg.fileshare.dto.UserCreateDto;
import org.elsysbg.fileshare.dto.VerifyCodeDto;
import org.elsysbg.fileshare.models.User;

import java.util.Optional;

public interface UserService  {
    public User createMember(UserCreateDto accountDto) throws Exception;

    public User createAdmin(UserCreateDto accountDto);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    public void verifyCode(VerifyCodeDto verifyCodeDto);
}

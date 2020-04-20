package org.elsysbg.fileshare.services.user;

import org.elsysbg.fileshare.dao.user.UserDao;
import org.elsysbg.fileshare.dao.verify_account.VerifyAccountDao;
import org.elsysbg.fileshare.dto.UserCreateDto;
import org.elsysbg.fileshare.dto.VerifyCodeDto;
import org.elsysbg.fileshare.mail.Mail;
import org.elsysbg.fileshare.mail.MailService;
import org.elsysbg.fileshare.models.Role;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.models.VerifyAccount;
import org.elsysbg.fileshare.services.role.RoleService;
import org.elsysbg.fileshare.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerifyAccountDao verifyAccountDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User createMember(UserCreateDto userDto) throws Exception {
        String email = userDto.getEmail();
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(false);

        if(roleService.findById(2l).isPresent()) {
            Role role = roleService.findById(2l).get();
            user.addRole(role);
        }
        String token = RandomUtil.generateRandomStringNumber(6).toUpperCase();

        VerifyAccount verifyAccount = new VerifyAccount();
        verifyAccount.setUser(user);
        verifyAccount.setCreatedDate(LocalDateTime.now());
        verifyAccount.setExpiredDataToken(5);
        verifyAccount.setToken(token);
        verifyAccountDao.create(verifyAccount);

        Map<String, Object> maps = new HashMap<>();
        maps.put("user", user);
        maps.put("token", token);


        Mail mail = new Mail();
        mail.setFrom("postmaster@mg.iteacode.com");
        mail.setSubject("Registration");
        mail.setTo(user.getEmail());
        mail.setModel(maps);
        mailService.sendEmail(mail);

        return userDao.create(user);
    }

    @Override
    public User createAdmin(UserCreateDto userDto) {
        String email = userDto.getEmail();
        String username = userDto.getUsername();
        String password = userDto.getPassword();

        User account = new User();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));

        if(roleService.findById(2l).isPresent()) {
            Role role = roleService.findById(2l).get();
            account.addRole(role);
        }

        return userDao.create(account);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userDao.findByUsernameOrEmail(username,email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void verifyCode(VerifyCodeDto verifyCodeDto) {
        String token = verifyCodeDto.getToken();

        VerifyAccount verifyAccount = verifyAccountDao.findByToken(token).get();
        User user = verifyAccount.getUser();
        user.setActive(true);
        userDao.update(user);
    }
}

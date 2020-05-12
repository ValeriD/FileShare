package org.elsysbg.fileshare.services.user;


import org.elsysbg.fileshare.dto.UserCreateDto;
import org.elsysbg.fileshare.dto.VerifyCodeDto;
import org.elsysbg.fileshare.mail.Mail;
import org.elsysbg.fileshare.mail.MailService;
import org.elsysbg.fileshare.models.File;
import org.elsysbg.fileshare.models.Role;
import org.elsysbg.fileshare.models.User;
import org.elsysbg.fileshare.models.VerifyAccount;
import org.elsysbg.fileshare.repositories.UserRepository;
import org.elsysbg.fileshare.repositories.VerifyAccountRepository;
import org.elsysbg.fileshare.services.files.FileService;
import org.elsysbg.fileshare.services.role.RoleService;
import org.elsysbg.fileshare.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FileService fileService;

    @Autowired
    private VerifyAccountRepository verifyAccountRepository;

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
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(false);

        if(roleService.findById((long)1).isPresent()) {
            Role role = roleService.findById((long)1).get();
            user.addRole(role);
        }
        String token = RandomUtil.generateRandomStringNumber(6).toUpperCase();

        VerifyAccount verifyAccount = new VerifyAccount();
        verifyAccount.setUser(user);
        verifyAccount.setCreatedDate(LocalDateTime.now());
        verifyAccount.setExpiredDataToken(5);
        verifyAccount.setToken(token);
        verifyAccountRepository.save(verifyAccount);

        Map<String, Object> maps = new HashMap<>();
        maps.put("user", user);
        maps.put("token", token);


        Mail mail = new Mail();
        mail.setFrom("postmaster@mg.iteacode.com");
        mail.setSubject("Registration");
        mail.setTo(user.getEmail());
        mail.setModel(maps);
        mailService.sendEmail(mail);

        User savedUser = userRepository.save(user);
       fileService.saveDir(username + "_root", savedUser, null);
        return savedUser;
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

        if(roleService.findById((long)2).isPresent()) {
            Role role = roleService.findById((long)2).get();
            account.addRole(role);
        }

        return userRepository.save(account);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username,email);
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
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void verifyCode(VerifyCodeDto verifyCodeDto) {
        String token = verifyCodeDto.getToken();

        VerifyAccount verifyAccount = verifyAccountRepository.findByToken(token).get();
        User user = verifyAccount.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }



}

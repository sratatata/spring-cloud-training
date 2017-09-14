package pl.training.cloud.users.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.training.cloud.common.model.ResultPage;
import pl.training.cloud.users.config.Role;
import pl.training.cloud.users.model.Authority;
import pl.training.cloud.users.model.User;
import pl.training.cloud.users.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

    @Value("${defaultDepartmentId}")
    private long defaultDepartmentId;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user) {
        configureDepartment(user);
        configureAuthority(user);
        encodePassword(user);
        user.setActive(true);
        usersRepository.saveAndFlush(user);
    }

    private void configureDepartment(User user) {
        if (user.getDepartmentId() == null) {
            user.setDepartmentId(defaultDepartmentId);
        }
    }

    private void configureAuthority(User user) {
        Authority authority = new Authority(Role.USER.name());
        user.addAuthority(authority);
    }

    private void encodePassword(User user) {
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
    }

    public ResultPage<User> getUsers(int pageNumber, int pageSize) {
        Page<User> usersPage = usersRepository.findAll(new PageRequest(pageNumber, pageSize));
        return new ResultPage<>(usersPage.getContent(), usersPage.getNumber(), usersPage.getTotalPages());
    }

    public void setDefaultDepartmentId(long defaultDepartmentId) {
        this.defaultDepartmentId = defaultDepartmentId;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usersRepository.getByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", login)));

    }

}

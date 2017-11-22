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
import pl.training.cloud.users.model.Message;
import pl.training.cloud.users.model.User;
import pl.training.cloud.users.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

    @Value("${defaultDepartmentId}")
    private long defaultDepartmentId;
    @Value("${defaultValidity}")
    private long defaultValidity;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private EventEmitter eventEmitter;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, EventEmitter eventEmitter) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventEmitter = eventEmitter;
    }

    public void addUser(User user) {
        configureDepartment(user);
        configureAuthority(user);
        encodePassword(user);
        user.setActive(true);
        usersRepository.saveAndFlush(user);
        notify(user);
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

    private void notify(User user) {
        String text = String.format("New user: %s in department %d", user.getLastName(), user.getDepartmentId());
        Message message = new Message(user.getId(), text);
        eventEmitter.emit(message);
    }

    private void updateValidity(User user, int time){
        user.setValidity(user.getValidity()+time);
        String text = String.format("User validity changed to: %d", user.getValidity());
        Message message = new Message(user.getId(), text);
        eventEmitter.emit(message);
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

package ru.photorex.hw16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.photorex.hw16.exception.NoDataWithThisIdException;
import ru.photorex.hw16.model.User;
import ru.photorex.hw16.repository.UserRepository;
import ru.photorex.hw16.to.RegUser;
import ru.photorex.hw16.to.mapper.UserRegMapper;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ActuatorUserServiceImpl implements ActuatorUserService {

    private final UserRepository userRepository;
    private final UserRegMapper mapper;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createAdmin(RegUser regUser) {
        User user = mapper.toEntity(regUser);
        user.setRoles(Set.of(User.Role.ROLE_ADMIN, User.Role.ROLE_USER));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User changeAccount(String id, boolean isEnabled, boolean isCredentialsExpired, boolean isAccountLocked, boolean isAccountExpired) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoDataWithThisIdException(id));
        user.setEnabled(isEnabled);
        user.setAccountNonExpired(isAccountExpired);
        user.setCredentialsNonExpired(isCredentialsExpired);
        user.setAccountNonLocked(isAccountLocked);
        return userRepository.save(user);
    }
}

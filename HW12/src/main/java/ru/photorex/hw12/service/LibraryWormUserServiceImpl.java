package ru.photorex.hw12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.photorex.hw12.model.User;
import ru.photorex.hw12.repository.UserRepository;
import ru.photorex.hw12.to.RegUser;
import ru.photorex.hw12.to.UserTo;
import ru.photorex.hw12.to.mapper.UserRegMapper;

@Service
@RequiredArgsConstructor
public class LibraryWormUserServiceImpl implements LibraryWormUserService  {

    private final UserRepository repository;
    private final UserRegMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public UserTo saveNewUser(RegUser regUser) {
        regUser.setPassword(encoder.encode(regUser.getPassword()));
        User user = repository.save(mapper.toEntity(regUser));
        return new UserTo(user.getId(), user.getFullName());
    }
}

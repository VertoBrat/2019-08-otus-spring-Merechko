package ru.photorex.hw14.batch.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.mapper.UserMapper;
import ru.photorex.hw14.model.mongo.User;
import ru.photorex.hw14.model.sql.UserTo;

@Component
@RequiredArgsConstructor
public class UserProcessor implements ItemProcessor<User, UserTo> {

    private final UserMapper userMapper;

    @Override
    public UserTo process(User user) throws Exception {
        return userMapper.toTo(user);
    }
}

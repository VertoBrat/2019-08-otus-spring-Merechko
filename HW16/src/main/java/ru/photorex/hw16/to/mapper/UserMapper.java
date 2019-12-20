package ru.photorex.hw16.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.hw16.model.User;
import ru.photorex.hw16.to.UserTo;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserTo> {

    @Mapping(source = "user.username", target = "nick")
    UserTo toTo(User user);
}

package ru.photorex.hw13.to.mapper;

import org.mapstruct.Mapper;
import ru.photorex.hw13.model.User;
import ru.photorex.hw13.to.RegUser;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserRegMapper extends BaseMapper<User, RegUser> {

    default User toEntity(RegUser regUser) {
        User user = new User();
        user.setUserName(regUser.getUsername());
        user.setPassword(regUser.getPassword());
        user.setFullName(regUser.getFullName());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setRoles(Set.of(User.Role.ROLE_USER));
        return user;
    }
}

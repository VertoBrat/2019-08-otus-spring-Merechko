package ru.photorex.hw14.model.mapper;

import org.mapstruct.Mapper;
import ru.photorex.hw14.model.mongo.User;
import ru.photorex.hw14.model.sql.RoleTo;
import ru.photorex.hw14.model.sql.UserTo;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserTo> {

    default UserTo toTo(User user) {
        UserTo to = new UserTo();
        to.setUserName(user.getUserName());
        to.setPassword(user.getPassword());
        to.setFullName(user.getFullName());
        to.setEnabled(user.isEnabled());
        to.setAccountNonExpired(user.isAccountNonExpired());
        to.setAccountNonLocked(user.isAccountNonLocked());
        to.setCredentialsNonExpired(user.isCredentialsNonExpired());

        Set<RoleTo> roles = new HashSet<>();

        for (User.Role r : user.getRoles()) {
            RoleTo roleTo = new RoleTo();
            roleTo.setRoleName(r.getRole());
            roleTo.setUser(to);
            roles.add(roleTo);
        }

        to.setRoles(roles);
        return to;
    }
}

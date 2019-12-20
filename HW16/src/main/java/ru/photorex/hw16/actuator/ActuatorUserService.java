package ru.photorex.hw16.actuator;

import ru.photorex.hw16.model.User;
import ru.photorex.hw16.to.RegUser;

import java.util.List;

public interface ActuatorUserService {

    List<User> getAllUsers();

    User createAdmin(RegUser regUser);

    void deleteUser(String id);

    User changeAccount(String id, boolean isEnabled, boolean isCredentialsExpired,
                       boolean isAccountLocked, boolean isAccountExpired);
}

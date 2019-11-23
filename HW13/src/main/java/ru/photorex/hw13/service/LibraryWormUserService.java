package ru.photorex.hw13.service;

import ru.photorex.hw13.to.RegUser;
import ru.photorex.hw13.to.UserTo;

public interface LibraryWormUserService {

    UserTo saveNewUser(RegUser user);

    UserTo findUserByUserName(String userName);
}

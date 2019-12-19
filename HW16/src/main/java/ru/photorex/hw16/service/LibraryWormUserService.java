package ru.photorex.hw16.service;

import ru.photorex.hw16.to.RegUser;
import ru.photorex.hw16.to.UserTo;

public interface LibraryWormUserService {

    UserTo saveNewUser(RegUser user);

    UserTo findUserByUserName(String userName);
}

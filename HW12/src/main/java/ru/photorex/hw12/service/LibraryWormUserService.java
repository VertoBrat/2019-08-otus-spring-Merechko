package ru.photorex.hw12.service;

import ru.photorex.hw12.to.RegUser;
import ru.photorex.hw12.to.UserTo;

public interface LibraryWormUserService {

    UserTo saveNewUser(RegUser user);

    UserTo findUserByUserName(String userName);
}

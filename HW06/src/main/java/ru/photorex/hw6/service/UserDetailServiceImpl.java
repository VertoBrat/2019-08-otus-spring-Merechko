package ru.photorex.hw6.service;

import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    private String userName;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}

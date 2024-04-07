package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.User;

import java.util.List;

public interface UserMapper {
    List<User> getByopenId(String openId);

    void addUser(User user);
}

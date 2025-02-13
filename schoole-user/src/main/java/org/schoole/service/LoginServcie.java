package org.schoole.service;

import org.schoole.domain.ResponseResult;
import org.schoole.domain.User;

public interface LoginServcie {
    ResponseResult login(User user);

    ResponseResult logout();

}

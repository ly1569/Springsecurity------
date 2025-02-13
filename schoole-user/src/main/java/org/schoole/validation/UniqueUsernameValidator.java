package org.schoole.validation;

import org.schoole.domain.User ;
import org.schoole.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Autowired
    UserMapper userMapper ;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 在这里实现具体的验证逻辑，例如检查数据库中用户名是否唯一
        User user = userMapper.getUserInfoIndex(value) ;
        if (user != null ){
            return false ;
        }
        // 如果唯一，返回 true；否则返回 false
        return true; // 这里需要替换为实际的验证逻辑
    }
}

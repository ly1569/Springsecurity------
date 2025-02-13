package org.schoole.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password,String> {

    private static final String REGEX = "^1[3456789]\\\\d{9}$";

    /**
     *
     * @param value
     * @param context
     * @return：返回 true 表示效验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 检验 value => password 中是否含有 ' '
        if (value.contains(" ")){
            return false ;
        }
        return true;
    }
}

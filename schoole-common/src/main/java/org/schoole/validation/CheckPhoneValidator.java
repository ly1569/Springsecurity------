package org.schoole.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPhoneValidator implements ConstraintValidator<CheckPhone,String> {

//    private static final String REGEX = "^1[3456789]\\\\d{9}$";
    private static final String REGEX = "^1[3456789]\\d{9}$";


    /**
     *
     * @param value
     * @param context
     * @return：返回 true 表示效验通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 不为null才进行校验
        if (value != null) {
            return value.matches(REGEX);
        }
        return true;
    }
}

package org.schoole.validation;


import org.springframework.context.annotation.Lazy;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

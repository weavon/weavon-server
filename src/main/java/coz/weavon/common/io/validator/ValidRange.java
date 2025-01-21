package coz.weavon.common.io.validator;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface ValidRange {

    boolean required() default true;

    String startFieldName() default "startDate";

    String endFieldName() default "endDate";
}

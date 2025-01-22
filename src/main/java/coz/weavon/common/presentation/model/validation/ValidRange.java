package coz.weavon.common.presentation.model.validation;

import coz.weavon.common.presentation.validator.RangeValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface ValidRange {

    boolean required() default true;

    String startFieldName() default "startDate";

    String endFieldName() default "endDate";
}

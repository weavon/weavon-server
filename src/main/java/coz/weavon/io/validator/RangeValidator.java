package coz.weavon.io.validator;

import coz.weavon.util.DateTimeUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;

public class RangeValidator implements ConstraintValidator<ValidRange, Object> {

    private String startFieldName;
    private String endFieldName;
    private boolean required;

    @Override
    public void initialize(ValidRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.startFieldName();
        this.endFieldName = constraintAnnotation.endFieldName();
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate startDate, endDate;
        try {
            Field startField = value.getClass().getDeclaredField(startFieldName);
            Field endField = value.getClass().getDeclaredField(endFieldName);
            startField.setAccessible(true);
            endField.setAccessible(true);

            startDate = (LocalDate) startField.get(value);
            endDate = (LocalDate) endField.get(value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (required && (Objects.isNull(startDate) || Objects.isNull(endDate))) {
            return false;
        }

        if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
            return true;
        }

        if (Objects.isNull(startDate) ^ Objects.isNull(endDate)) {
            return false;
        }

        if (!DateTimeUtils.isValidDateRange(startDate, endDate)) {
            return false;
        }

        return true;
    }
}

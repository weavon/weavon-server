package coz.weavon.common.validator;

import coz.weavon.common.exception.ClientException;
import coz.weavon.constant.Label;
import coz.weavon.constant.Message;
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

        if (required && Objects.isNull(startDate)) {
            throw new ClientException(Message.Validation.IS_REQUIRED, Label.Common.START_DATE);
        }

        if (required && Objects.isNull(endDate)) {
            throw new ClientException(Message.Validation.IS_REQUIRED, Label.Common.END_DATE);
        }

        if (Objects.isNull(startDate) ^ Objects.isNull(endDate)) {
            throw new ClientException(Message.Validation.RANGE_MISS_START_END_DATE);
        }

        if (!DateTimeUtils.isValidDateRange(startDate, endDate)) {
            throw new ClientException(Message.Validation.INVALID_RANGE);
        }

        return true;
    }
}

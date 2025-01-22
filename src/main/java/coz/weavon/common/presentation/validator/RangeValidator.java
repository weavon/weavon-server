package coz.weavon.common.presentation.validator;

import coz.weavon.common.application.util.DateTimeUtils;
import coz.weavon.common.presentation.model.exception.ClientException;
import coz.weavon.common.presentation.model.validation.ValidRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Objects;

public class RangeValidator implements ConstraintValidator<ValidRange, Object> {

    private static final String MSG_VLD_REQ_REQ = "message.validation.required";
    private static final String MSG_VLD_REQ_RANGE_OMIT = "message.validation.rangeOmit";
    private static final String MSG_VLD_REQ_INVALID_RANGE = "message.validation.invalid.range";

    private static final String LBL_DATE_START_DATE = "label.date.startDate";
    private static final String LBL_DATE_END_DATE = "label.date.endDate";

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
            throw new ClientException(MSG_VLD_REQ_REQ, LBL_DATE_START_DATE);
        }

        if (required && Objects.isNull(endDate)) {
            throw new ClientException(MSG_VLD_REQ_REQ, LBL_DATE_END_DATE);
        }

        if (Objects.isNull(startDate) ^ Objects.isNull(endDate)) {
            throw new ClientException(MSG_VLD_REQ_RANGE_OMIT);
        }

        if (!DateTimeUtils.isValidDateRange(startDate, endDate)) {
            throw new ClientException(MSG_VLD_REQ_INVALID_RANGE);
        }

        return true;
    }
}

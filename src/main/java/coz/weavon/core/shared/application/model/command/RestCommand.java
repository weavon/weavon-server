package coz.weavon.core.shared.application.model.command;

import coz.weavon.exception.model.BusinessException;
import coz.weavon.utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.ObjectUtils;

public abstract class RestCommand {

    private static final String MSG_VLD_REQ = "message.validation.required";

    private static final String MSG_VLD_INVALID_RANGE = "message.validation.invalid.range";

    private static final String LBL_DATE_START_DATE = "label.date.startDate";

    private static final String LBL_DATE_END_DATE = "label.date.endDate";

    public abstract void validate();

    protected void validateNonNull(Map<String, Object> labelPropertyMap) {
        for (Map.Entry<String, Object> labelProperty : labelPropertyMap.entrySet()) {
            String propertyLabelCode = labelProperty.getKey();
            Object property = labelProperty.getValue();

            if (ObjectUtils.isEmpty(property)) {
                throw new BusinessException(MSG_VLD_REQ, propertyLabelCode);
            }
        }
    }

    protected void validateDateRange(LocalDate startDate, LocalDate endDate, boolean required) {
        if (required) {
            Map<String, Object> requiredParamMap = new HashMap<>();
            requiredParamMap.put(LBL_DATE_START_DATE, startDate);
            requiredParamMap.put(LBL_DATE_END_DATE, endDate);

            this.validateNonNull(requiredParamMap);
        }

        if (Objects.isNull(startDate) || Objects.isNull(endDate)) return;

        if (DateTimeUtils.isValidDateRange(startDate, endDate)) {
            throw new BusinessException(MSG_VLD_INVALID_RANGE);
        }
    }
}

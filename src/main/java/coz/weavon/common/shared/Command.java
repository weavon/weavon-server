package coz.weavon.common.shared;

import coz.weavon.common.exception.BusinessException;
import coz.weavon.constant.Label;
import coz.weavon.util.DateTimeUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.util.ObjectUtils;

public abstract class Command {

    private static final String MSG_VLD_REQ = "message.validation.required";

    private static final String MSG_VLD_INVALID_RANGE = "message.validation.invalid.range";

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
            requiredParamMap.put(Label.Common.START_DATE, startDate);
            requiredParamMap.put(Label.Common.END_DATE, endDate);

            this.validateNonNull(requiredParamMap);
        }

        if (Objects.isNull(startDate) || Objects.isNull(endDate)) return;

        if (DateTimeUtils.isValidDateRange(startDate, endDate)) {
            throw new BusinessException(MSG_VLD_INVALID_RANGE);
        }
    }
}

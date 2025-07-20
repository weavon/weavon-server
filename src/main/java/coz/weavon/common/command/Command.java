package coz.weavon.common.command;

import coz.weavon.constant.Label;
import coz.weavon.constant.Message;
import coz.weavon.exception.BusinessException;
import coz.weavon.util.DateTimeUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.SuperBuilder;
import org.springframework.util.ObjectUtils;

@SuperBuilder
public abstract class Command {

    public abstract void validate();

    protected void validateNonNull(Map<String, Object> labelPropertyMap) {
        for (Map.Entry<String, Object> labelProperty : labelPropertyMap.entrySet()) {
            String propertyLabelCode = labelProperty.getKey();
            Object property = labelProperty.getValue();

            if (ObjectUtils.isEmpty(property)) {
                throw new BusinessException(Message.Validation.IS_REQUIRED, propertyLabelCode);
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
            throw new BusinessException(Message.Validation.INVALID_RANGE);
        }
    }
}

package coz.weavon.common.application.util;

import coz.weavon.common.presentation.model.exception.BusinessException;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommandUtils {
    public static void validateNonNull(Map<String, Object> labelPropertyMap) {
        for (Map.Entry<String, Object> labelProperty : labelPropertyMap.entrySet()) {
            String propertyLabelCode = labelProperty.getKey();
            Object property = labelProperty.getValue();

            if (Objects.isNull(property)) {
                throw new BusinessException(propertyLabelCode);
            }
        }
    }
}

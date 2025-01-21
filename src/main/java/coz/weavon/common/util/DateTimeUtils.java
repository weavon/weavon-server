package coz.weavon.common.util;

import java.time.LocalDate;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtils {
    public static boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return false;
        }

        return !startDate.isAfter(endDate);
    }
}

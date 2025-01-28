package coz.weavon.common.application.model.condition;

import java.util.Collection;
import java.util.Objects;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class RestCondition {

    public abstract void validate();

    protected boolean isInvalidInCondition(Collection<?> condition) {
        return CollectionUtils.isEmpty(condition);
    }

    protected boolean isInvalidEqualCondition(Object condition) {
        return Objects.isNull(condition);
    }

    protected boolean isInvalidLikeCondition(String condition) {
        return !StringUtils.hasText(condition);
    }
}

package coz.weavon.common.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import java.util.List;
import java.util.Objects;

public interface QueryRepository {

    default <T> BooleanExpression in(SimpleExpression<T> expression, List<T> inValues) {
        if (Objects.isNull(inValues) || inValues.isEmpty()) {
            return null;
        }

        return expression.in(inValues.stream().distinct().toList());
    }

    default BooleanExpression like(StringExpression expression, String likeValue) {
        if (Objects.isNull(likeValue) || likeValue.isBlank()) {
            return null;
        }

        return expression.like("%" + likeValue + "%");
    }
}

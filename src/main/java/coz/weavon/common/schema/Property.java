package coz.weavon.common.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface Property {

    boolean unique() default false;

    boolean nullable() default true;

    boolean updatable() default true;
}

package org.steingen.simple.cdi.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that specifies how many retries may be performed.
 * (Default 1).
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxRetries {
    /**
     * Maximum retry amount, defaults to 1.
     *
     * @return amount.
     */
    int value() default 1;
}

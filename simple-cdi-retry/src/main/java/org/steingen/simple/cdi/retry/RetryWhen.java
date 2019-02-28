package org.steingen.simple.cdi.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies which type of {@link Exception} will be allowed for retrying.
 * Must inherit from {@link Exception}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryWhen {
    /**
     * The concnrete Exception type to handle with retrying.
     *
     * @return the type.
     */
    Class<? extends Exception> value();
}

package org.steingen.simple.cdi.retry;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual interceptor using {@link Retryable} binding in conjunction with {@link MaxRetries} and {@link RetryWhen}.
 */
@Interceptor
@Retryable
public class Retrier {

    private static final Logger LOG = LoggerFactory.getLogger(Retrier.class);

    /**
     * Retry the given invocation context if applicable.
     *
     * @param ic the context to retry.
     * @return the context's result.
     * @throws Exception in case of any unhandled errors from within the invocation.
     */
    @AroundInvoke
    public Object retry(InvocationContext ic) throws Exception {
        LOG.trace("Intercepted invocation {}", ic.getMethod().getName());
        // fetch config from invocation's annotations.
        int retries = getMaxRetries(ic);
        Class retryWhen = getExceptionType(ic);
        if (retryWhen == null) {
            // no exception specified. so ignore retry policy.
            LOG.warn("No configured exception for retrying. Proceed without retry.");
            return ic.proceed();
        }
        LOG.trace("Retries possible on this method: {}", retries);
        for (int i = 0; i < retries; i++) {
            // perform looped retry.
            try {
                // invoke. if success, nothing more happens here.
                return ic.proceed();
            } catch (Exception ex) {
                // in case of any exception, check type.
                if (ex.getClass() == retryWhen) {
                    // if configured type, allow next iteration.
                    LOG.trace("Got an {} for the {}. time. Retry once more.", ex.getClass().getSimpleName(), (i + 1));
                } else {
                    // else throw the exception to the caller.
                    LOG.trace("Caught an exception that is not allowed for retrying: {}", ex.getClass().getSimpleName());
                    throw ex;
                }
            }
        }
        // max retries (-1) used up, so make last try with potential exception for the caller.
        return ic.proceed();
    }

    private Class getExceptionType(InvocationContext ic) {
        RetryWhen annotation = ic.getMethod().getAnnotation(RetryWhen.class);
        return annotation == null ? null : annotation.value();
    }

    private int getMaxRetries(InvocationContext ic) {
        MaxRetries annotation = ic.getMethod().getAnnotation(MaxRetries.class);
        return annotation == null ? 0 : annotation.value();
    }
}

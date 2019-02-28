package org.steingen.simple.cdi.retry.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.steingen.simple.cdi.retry.MaxRetries;
import org.steingen.simple.cdi.retry.RetryWhen;
import org.steingen.simple.cdi.retry.Retryable;

@ApplicationScoped
public class RetryMe {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryMe.class);

    @Inject
    SometimeFailingService failingService;

    @Retryable
    @MaxRetries(4)
    @RetryWhen(SuperAnnoyingException.class)
    public void doSomething() throws SuperAnnoyingException, SuperEvilException {
        LOGGER.info("Invoking service...");
        failingService.trySomething();
        LOGGER.info("Invoked service successfully!");
    }


}

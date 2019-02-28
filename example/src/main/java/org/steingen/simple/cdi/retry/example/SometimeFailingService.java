package org.steingen.simple.cdi.retry.example;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SometimeFailingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryMe.class);

    private static int LUCK = 4;
    private static int MAX = 9;

    public void trySomething() throws SuperAnnoyingException, SuperEvilException {
        int luck = new Random(System.currentTimeMillis()).nextInt(MAX + 1);
        LOGGER.info("Invoking something. Luck to succeed is {}", luck);
        if (luck == 0) {
            LOGGER.info("Dang. Something evil happened!");
            throw new SuperEvilException();
        }
        if (luck > LUCK) {
            LOGGER.info("Something worked.");
        } else {
            throw new SuperAnnoyingException();
        }

    }


}

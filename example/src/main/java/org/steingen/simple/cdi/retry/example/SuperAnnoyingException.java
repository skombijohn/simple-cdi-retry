package org.steingen.simple.cdi.retry.example;

public class SuperAnnoyingException extends Exception {

    SuperAnnoyingException() {
        super("Oh. Something annoying broke.");
    }
}

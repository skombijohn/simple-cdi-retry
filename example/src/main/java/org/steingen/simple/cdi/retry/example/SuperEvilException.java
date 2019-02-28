package org.steingen.simple.cdi.retry.example;

public class SuperEvilException extends Exception {


    public SuperEvilException() {
        super("Cant stop me!");
    }

}

package com.chertov.coffeemachine.exception;

public class OutOfCoffeeException extends RuntimeException {

    public OutOfCoffeeException(String message) {
        super(message);
    }
}

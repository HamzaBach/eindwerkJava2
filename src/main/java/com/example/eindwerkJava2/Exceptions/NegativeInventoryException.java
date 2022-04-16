package com.example.eindwerkJava2.Exceptions;

public class NegativeInventoryException extends Exception {

    private static final long serialVersionUID = 1L;

    public NegativeInventoryException(String cause){
        super(cause);
    }

}

package com.example.eindwerkJava2.wrappers;

/**
 * Abstract class to inherit its fields to its inherited classes for giving info whether a method was successful or not.
 *
 * @author Hamza Bachiri
 * @version 1.0
 */
public abstract class SuccessObject {
    private String message;
    private Boolean isSuccessfull;

    /**
     * Constructor for creating a SuccessObject.
     */
    public SuccessObject() {
    }

    /**
     * Method to retrieve the success-/error message.
     *
     * @return The success-error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method to set the success-/error message.
     *
     * @param message The to be set success-/error message.
     */
    public void setMessage(String message) {
        if (this.message == null) {
            this.message = message;
        } else {
            this.message += "\n " + message;
        }
    }

    /**
     * Method to retrieve whether or not a method was successful.
     *
     * @return Boolean to indicate if it was a success.
     */
    public Boolean getIsSuccessfull() {
        return isSuccessfull;
    }

    /**
     * Method to set whether or not a method was successful.
     *
     * @param successfull The to be set boolean to indicate if it was a success.
     */
    public void setIsSuccessfull(Boolean successfull) {
        isSuccessfull = successfull;
    }

}


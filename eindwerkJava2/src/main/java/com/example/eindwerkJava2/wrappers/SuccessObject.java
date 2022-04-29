package com.example.eindwerkJava2.wrappers;

public abstract class SuccessObject {
    private String message;
    private Boolean isSuccessfull;

    public SuccessObject() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(this.message==null){
            this.message=message;
        }else{
            this.message+="\n "+message;
        }
    }

    public Boolean getIsSuccessfull() {
        return isSuccessfull;
    }

    public void setIsSuccessfull(Boolean successfull) {
        isSuccessfull = successfull;
    }

}


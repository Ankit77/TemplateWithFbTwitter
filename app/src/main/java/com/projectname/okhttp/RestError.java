package com.projectname.okhttp;

public class RestError {

    private boolean success;
    private String message;
    private int status;

    public RestError(String strMessage) {
        this.message = strMessage;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

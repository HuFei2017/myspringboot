package com.learning.schema.output;

public class DDLOperation {
    private int status;
    private String code;
    private String message;

    public DDLOperation getResult(String state, String message){
        if(state.equals("OK"))
            return new DDLOperation(200,state,message);
        else
            return new DDLOperation(500,state,message);
    }

    public DDLOperation() {
    }

    DDLOperation(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

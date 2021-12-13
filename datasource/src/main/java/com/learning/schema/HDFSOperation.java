package com.learning.schema;

import com.alibaba.fastjson.JSON;

public class HDFSOperation {

    private int status;
    private String content="";
    private String error;
    private JSON result;

    public HDFSOperation(int status, String content, String error, JSON result) {
        this.status = status;
        this.content = content;
        this.error = error;
        this.result = result;
    }

    public HDFSOperation() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public JSON getResult() {
        return result;
    }

    public void setResult(JSON result) {
        this.result = result;
    }
}

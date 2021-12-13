package com.learning.schema.input;

public class Changing {

    private String Path;
    private String after;

    public String getPath() {
        return Path;
    }

    public String getAfter() {
        return after;
    }

    public void setPath(String path) {
        this.Path = path;
    }

    public Changing(String path, String after) {
        this.Path = path;
        this.after = after;
    }

    public Changing() {
    }

    public void setAfter(String after) {
        this.after = after;
    }
}

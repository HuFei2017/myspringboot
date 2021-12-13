package com.learning.schema;

public class KeyMap {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public KeyMap() {
    }

    public KeyMap(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

package com.learning.schema.input;

import com.learning.schema.KeyMap;

import java.util.List;

public class HDFSInput {

    private String path;
    private List<KeyMap> list;

    public HDFSInput() {
    }

    public HDFSInput(String path, List<KeyMap> list) {
        this.path = path;
        this.list = list;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setList(List<KeyMap> list) {
        this.list = list;
    }

    public String getPath() {
        return path;
    }

    public List<KeyMap> getList() {
        return list;
    }
}

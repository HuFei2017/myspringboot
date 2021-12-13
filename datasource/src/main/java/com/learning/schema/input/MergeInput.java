package com.learning.schema.input;

import java.util.List;

public class MergeInput {

    private String path;
    private List<String> list;

    public void setPath(String path) {
        this.path = path;
    }

    public MergeInput(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    public MergeInput() {
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getPath() {
        return path;
    }

    public List<String> getList() {
        return list;
    }
}

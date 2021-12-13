package com.learning.schema;

import java.util.ArrayList;
import java.util.List;

public class PartitionItem {
    private List<KeyMap> spec=new ArrayList<>();
    private String location;

    public PartitionItem() {
    }

    public PartitionItem(List<KeyMap> spec, String location) {
        this.spec = spec;
        this.location = location;
    }

    public List<KeyMap> getSpec() {
        return spec;
    }

    public void setSpec(List<KeyMap> spec) {
        this.spec = spec;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package com.learning.schema;

public class DatabaseInfo {
    private String name;
    private String comment;
    private String location;
    private String ownerName;
    private String ownerType;
    private String parameters;

    public DatabaseInfo() {
    }

    public DatabaseInfo(String name, String comment, String location, String ownerName, String ownerType, String parameters) {
        this.name = name;
        this.comment = comment;
        this.location = location;
        this.ownerName = ownerName;
        this.ownerType = ownerType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}

package com.learning.schema;

public class FileFormat {

    private String owner;
    private int replication;
    private int length;
    private String permission;
    private int storagePolicy;
    private String type;
    private int blockSize;
    private String pathSuffix;
    private int childrenNum;
    private int modificationTime;
    private int accessTime;
    private int fileId;
    private String group;

    public FileFormat() {
    }

    public FileFormat(String owner, int replication, int length, String permission,
                      int storagePolicy, String type, int blockSize, String pathSuffix,
                      int childrenNum, int modificationTime, int accessTime, int fileId, String group) {
        this.owner = owner;
        this.replication = replication;
        this.length = length;
        this.permission = permission;
        this.storagePolicy = storagePolicy;
        this.type = type;
        this.blockSize = blockSize;
        this.pathSuffix = pathSuffix;
        this.childrenNum = childrenNum;
        this.modificationTime = modificationTime;
        this.accessTime = accessTime;
        this.fileId = fileId;
        this.group = group;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getReplication() {
        return replication;
    }

    public void setReplication(int replication) {
        this.replication = replication;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getStoragePolicy() {
        return storagePolicy;
    }

    public void setStoragePolicy(int storagePolicy) {
        this.storagePolicy = storagePolicy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public String getPathSuffix() {
        return pathSuffix;
    }

    public void setPathSuffix(String pathSuffix) {
        this.pathSuffix = pathSuffix;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(int childrenNum) {
        this.childrenNum = childrenNum;
    }

    public int getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(int modificationTime) {
        this.modificationTime = modificationTime;
    }

    public int getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(int accessTime) {
        this.accessTime = accessTime;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

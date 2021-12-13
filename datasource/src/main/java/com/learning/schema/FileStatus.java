package com.learning.schema;

import java.util.List;

public class FileStatus {

    private List<FileFormat> FileStatus;

    public void setFileStatus(List<FileFormat> fileStatus) {
        FileStatus = fileStatus;
    }

    public FileStatus(List<FileFormat> fileStatus) {
        FileStatus = fileStatus;
    }

    public FileStatus() {
    }

    public List<FileFormat> getFileStatus() {
        return FileStatus;
    }
}

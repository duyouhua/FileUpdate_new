package com.leng.fileupdate_new.upload;

/**
 * Created by liuguodong on 2017/10/1.
 */

public class TestBean {
    public String getCrateFileType() {
        return crateFileType;
    }

    public void setCrateFileType(String crateFileType) {
        this.crateFileType = crateFileType;
    }

    private String crateFileType;

    public int getCrateFileTypenums() {
        return crateFileTypenums;
    }

    public void setCrateFileTypenums(int crateFileTypenums) {
        this.crateFileTypenums = crateFileTypenums;
    }

    private int crateFileTypenums;
    private String locfilepath;

    public String getLocfileName() {
        return locfileName;
    }

    public void setLocfileName(String locfileName) {
        this.locfileName = locfileName;
    }

    private String locfileName;

    public int getUploadProgress() {
        return UploadProgress;
    }

    public void setUploadProgress(int uploadProgress) {
        UploadProgress = uploadProgress;
    }

    private int UploadProgress;

    public String getUpLoadStatus() {
        return UpLoadStatus;
    }

    public void setUpLoadStatus(String upLoadStatus) {
        UpLoadStatus = upLoadStatus;
    }

    private String UpLoadStatus;

    public String getLocfilepath() {
        return locfilepath;
    }

    public void setLocfilepath(String locfilepath) {
        this.locfilepath = locfilepath;
    }

    public String getRemotefilepath() {
        return remotefilepath;
    }

    public void setRemotefilepath(String remotefilepath) {
        this.remotefilepath = remotefilepath;
    }

    private String remotefilepath;
}

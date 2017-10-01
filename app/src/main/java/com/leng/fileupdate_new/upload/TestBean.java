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
    private String locfilepath;

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

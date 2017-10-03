package com.leng.fileupdate_new.Bean;

/**
 * Created by liuguodong on 2017/10/1.
 */

public class FileUpdateStatus {
    private  String pv;
    private String name;
    private boolean isDownload;
    private int progress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }
}

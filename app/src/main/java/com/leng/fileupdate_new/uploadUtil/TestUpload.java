package com.leng.fileupdate_new.uploadUtil;

/**
 * Created by Administrator on 2017/9/27.
 */

public class TestUpload {
    String FilePath;

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public void setFileNameFromFtp(String fileNameFromFtp) {
        FileNameFromFtp = fileNameFromFtp;
    }

    public String getFileNameFromFtp() {
        return FileNameFromFtp;
    }

    String FileNameFromFtp;
//    an.getFilePath();
//    String remoteFileName= bean.getFileNameFromFtp() ;
}

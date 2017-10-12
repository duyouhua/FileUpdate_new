package com.leng.fileupdate_new.utils;

import com.leng.fileupdate_new.upload.TestBean;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/16.
 */

public class Constanxs {
    /**
     * 确定之后保存本地的key
     */
    public static final String yesDir = "quedinglujing";

    //    public static  final  String INFO1="INFO1",INFO2="INFO2",INFO3="INFO3",INFO4="INFO4",INFO5="INFO5",INFO6="INFO6";
    public static final int INFO1 = 1, INFO2 = 2, INFO3 = 3, INFO4 = 4, INFO5 = 5, INFO6 = 6;
    public static final int INFO1arg = 1, INFO2arg = 2, INFO3arg = 3, INFO4arg = 4, INFO5arg = 5, INFO6arg = 6;

    public static final HashMap<Integer, String> isMfile = new HashMap<>();
    public static final String FTPIP = "218.246.35.197";
    public static final int FTPPORT = 21;
    public static final String FTPUSER = "FTPuser";
    public static final String FTPPASS = "Ftp1029384756";

    public static boolean isftpconnet = false;
    public static HashMap<String, String> updingMap = new HashMap<String, String>();
    public static HashMap<String, TestBean> fileStatusBeanMap = new HashMap<String, TestBean>();

    public static boolean isUplodFirstone;

    public static final String FOTERNUMSONE = "FOTERNUMSONE";
    public static final String FOTERNUMSTWO = "FOTERNUMSTWO";
    public static final String FOTERNUMSTHREE = "FOTERNUMSTHREE";
}

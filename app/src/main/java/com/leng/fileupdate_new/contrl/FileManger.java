package com.leng.fileupdate_new.contrl;

import android.content.Context;
import android.util.Log;

import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.utils.MimeType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/22.
 */

public class FileManger {
    private static final String TAG = "FileManger";
    public  final Map<String, MimeType> map;
    public  final Map<MimeType, Integer> resMap;
    private Context mContext;
    private volatile static FileManger fm = null;

    private FileManger() {
        map = new HashMap<String, MimeType>();
        map.put("amr", MimeType.MUSIC);
        map.put("mp3", MimeType.MUSIC);
        map.put("m4a", MimeType.MUSIC);
        map.put("aac", MimeType.MUSIC);
        map.put("ogg", MimeType.MUSIC);
        map.put("wav", MimeType.MUSIC);
        map.put("mkv", MimeType.MUSIC);
        map.put("flac", MimeType.MUSIC);

        map.put("3gp", MimeType.VIDEO);
        map.put("mp4", MimeType.VIDEO);
        map.put("rmvb", MimeType.VIDEO);
        map.put("mpeg", MimeType.VIDEO);
        map.put("mpg", MimeType.VIDEO);
        map.put("asf", MimeType.VIDEO);
        map.put("avi", MimeType.VIDEO);
        map.put("wmv", MimeType.VIDEO);

//        map.put("apk", MimeType.APK);

        map.put("bmp", MimeType.IMAGE);
        map.put("gif", MimeType.IMAGE);
        map.put("jpeg", MimeType.IMAGE);
        map.put("jpg", MimeType.IMAGE);
        map.put("png", MimeType.IMAGE);

//        map.put("doc", MimeType.DOC);
//        map.put("docx", MimeType.DOC);
//        map.put("rtf", MimeType.DOC);
//        map.put("wps", MimeType.DOC);
//        map.put("xls", MimeType.XLS);
//        map.put("xlsx", MimeType.XLS);
//        map.put("gtar", MimeType.RAR);
//        map.put("gz", MimeType.RAR);
//        map.put("zip", MimeType.RAR);
//        map.put("tar", MimeType.RAR);
//        map.put("rar", MimeType.RAR);
//        map.put("jar", MimeType.RAR);
//        map.put("htm", MimeType.HTML);
//        map.put("html", MimeType.HTML);
//        map.put("xhtml", MimeType.HTML);
//        map.put("java", MimeType.TXT);
//        map.put("txt", MimeType.TXT);
//        map.put("xml", MimeType.TXT);
//        map.put("log", MimeType.TXT);
//        map.put("pdf", MimeType.PDF);
//        map.put("ppt", MimeType.PPT);
//        map.put("pptx", MimeType.PPT);

        resMap = new HashMap<MimeType, Integer>();
        resMap.put(MimeType.APK, R.drawable.bxfile_file_apk);
        resMap.put(MimeType.DOC, R.drawable.bxfile_file_doc);
        resMap.put(MimeType.HTML, R.drawable.bxfile_file_html);
        resMap.put(MimeType.IMAGE, R.drawable.bxfile_file_unknow);
        resMap.put(MimeType.MUSIC, R.drawable.bxfile_file_mp3);
        resMap.put(MimeType.VIDEO, R.drawable.bxfile_file_video);
        resMap.put(MimeType.PDF, R.drawable.bxfile_file_pdf);
        resMap.put(MimeType.PPT, R.drawable.bxfile_file_ppt);
        resMap.put(MimeType.RAR, R.drawable.bxfile_file_zip);
        resMap.put(MimeType.TXT, R.drawable.bxfile_file_txt);
        resMap.put(MimeType.XLS, R.drawable.bxfile_file_xls);
        resMap.put(MimeType.UNKNOWN, R.drawable.bxfile_file_unknow);
    }

    public static FileManger getSingleton() {
        //先检查实例是否存在，如果不存在进入下面代码块
        if (fm == null) {
            //同步块，线程安全的创建实例
            synchronized (FileManger.class) {
                fm = new FileManger();
            }
        }
        return fm;
    }

    public int getFileNun(String path) {
        int arr=0;
        int a = 1;
        if (path.length() > 0) {
            File file = new File(path);
            File[] filearr = file.listFiles();
            if (filearr!=null&&filearr.length > 0) {
                for (int i = 0; i < filearr.length; i++) {
                    String filetype = getExtension(filearr[i]);
                    if (map.containsKey(filetype)) {  //获取当前文件下所有音视频文件
                        arr = a++;
                        Log.i(TAG, filetype + arr);
                    }

                }

            } else {
                arr=0;
//                Toast.makeText(mContext, "该目录下没有文件", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(mContext, "路径不正确", Toast.LENGTH_SHORT).show();
        }
        return arr;
    }


    private   static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }
}

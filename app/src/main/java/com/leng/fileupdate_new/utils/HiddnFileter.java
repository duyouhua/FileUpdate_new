package com.leng.fileupdate_new.utils;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Administrator on 2017/10/10.
 */

public class HiddnFileter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return !pathname.isHidden();
    }
}
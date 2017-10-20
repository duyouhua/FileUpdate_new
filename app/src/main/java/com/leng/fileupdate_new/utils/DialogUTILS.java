package com.leng.fileupdate_new.utils;

import android.content.Context;

import com.leng.other.loadingdialog.view.LoadingDialog;

/**
 * Created by liuguodong on 2017/10/7.
 */

public class DialogUTILS {
    private Context context;
    private LoadingDialog ld;

    public DialogUTILS(Context contextx) {
        this.context = contextx;
    }

    public void dialogshow() {
        ld = new LoadingDialog(context);
        ld.setLoadingText("加载中")
                .setSuccessText("加载成功")//显示加载成功时的文字
                //.setFailedText("加载失败")
                //                        .setInterceptBack(intercept_back_event)
                //                        .setLoadSpeed(speed)
                //                        .setRepeatCount(repeatTime)
                //                        .setDrawColor(color)
               ;

        ld.show();
    }

    public void dialogdismis() {
        if (ld != null) {
            ld.loadSuccess();
        }
    }
}

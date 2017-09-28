package com.leng.fileupdate_new.contrl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.NetUtils;

import java.io.IOException;

import static com.leng.fileupdate_new.utils.Constanxs.FTPIP;
import static com.leng.fileupdate_new.utils.Constanxs.FTPPASS;
import static com.leng.fileupdate_new.utils.Constanxs.FTPPORT;
import static com.leng.fileupdate_new.utils.Constanxs.FTPUSER;
import static com.leng.fileupdate_new.utils.Constanxs.isftpconnet;

/**
 * Created by Administrator on 2017/9/27.
 */

public class FTPmanger {
    private Context mContext;
    private ContinueFTP2 continueFTP2;
    private final static String TAG = "";

    public FTPmanger(Context context) {
        if (!isftpconnet) {
            connetFTP();
        }
        this.mContext = context;
    }

    private void connetFTP() {
        if (NetUtils.ping()) {
                continueFTP2 = new ContinueFTP2(mContext);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Constanxs.isftpconnet = continueFTP2.connect(FTPIP, FTPPORT, FTPUSER, FTPPASS);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }).start();
//            mHandler.sendEmptyMessageDelayed(4578, 1000);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mContext.sendBroadcast(new Intent("a123asd"));
                }
            }).start();
        } else {
            Toast.makeText(mContext, "您的网络有点小问题哦", Toast.LENGTH_SHORT).show();
        }

    }
}

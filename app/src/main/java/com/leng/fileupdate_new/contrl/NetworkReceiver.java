package com.leng.fileupdate_new.contrl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.leng.fileupdate_new.utils.NetUtils;

import static com.leng.fileupdate_new.utils.Constanxs.isftpconnet;


/**
 * Created by Administrator on 2017/9/26.
 */

public class NetworkReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkReceiverx";
    public static final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(netACTION)){
             if (!NetUtils.ping()){
                 isftpconnet=false;
                 Log.i(TAG, "Receiver come"+isftpconnet);
             }else {
//                 new FTPmanger(context);
                 Log.i(TAG, "Receiver come"+isftpconnet);
             }
        }
    }
}
package com.leng.fileupdate_new.contrl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.leng.fileupdate_new.utils.Constanxs;

/**
 * Created by Administrator on 2017/9/27.
 */

public class connetBROADCAST extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "连接成功了吗 ？"+ Constanxs.isftpconnet, Toast.LENGTH_SHORT).show();
    }
}

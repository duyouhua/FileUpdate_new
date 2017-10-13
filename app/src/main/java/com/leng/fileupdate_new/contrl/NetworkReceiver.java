package com.leng.fileupdate_new.contrl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.Bean.FileUserRevocation;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.NetUtils;

import java.io.File;
import java.util.List;

import static com.leng.fileupdate_new.utils.Constanxs.isftpconnet;


/**
 * Created by Administrator on 2017/9/26.
 */

public class NetworkReceiver extends BroadcastReceiver {
    private CallbackNetChanged callbackNetChanged;
    private static final String TAG = "NetworkReceiverx";
    public static final String netACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public void onReceive(Context context, Intent intent) {
//        callbackNetChanged= (CallbackNetChanged) context;
        if (intent.getAction().equals(netACTION)) {
            if (!NetUtils.ping()) {
//                Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Receiver come1" + isftpconnet);
                disconnect(context);
            } else {
//                Toast.makeText(context, "网络恢复", Toast.LENGTH_SHORT).show();
                connection();
                Log.i(TAG, "Receiver come2" + isftpconnet);
            }
        }
    }

    private void connection() {

    }

    private void disconnect(Context context) {
        List<FileUser> fileUsers = DaoUtils.FileUserDaoQuerywhere("6");
        if (fileUsers != null && fileUsers.size() > 0) {
            for (int i1 = 0; i1 < fileUsers.size(); i1++) {
                String name = fileUsers.get(i1).getMFileNamedao();
                String path = fileUsers.get(i1).getMFilePathdao();
                //获取文件来自哪个目录
                String revocationType = DaoUtils.FileUserDaoQuerypathAderesswhere(name);

                String renameto = changename(name);
                String renamepath = changpath(path, renameto);
                renameFile(path, renamepath);//更改文件名
                DaoUtils.FilUserDaoDel(name);//删除数据库原存档

                FileUser fileUser = new FileUser();
                fileUser.setId(FileUtils.longPressLong(renameto));
                fileUser.setMFileTypedao(revocationType);
                fileUser.setMFileProgresdao(0);
                fileUser.setMFileNamedao(renameto);
                fileUser.setMFilePathdao(renamepath);
                APP.getDaoInstant().getFileUserDao().update(fileUser);
                updateRevocation(name, revocationType);


                Log.i(TAG, name + "==" + path + "===" + revocationType + "更改后的名字是 " + renameto + " 路径是" + renamepath);
            }
            //完事后发送消息被
            sendBroadCastToCenter(context, 1);
        } else {
            Log.i(TAG, "没有上传中的文件");
        }
    }

    private void updateRevocation(String name, String type) {
        FileUserRevocation revocation = new FileUserRevocation();
        revocation.setId(FileUtils.longPressLong(name));
        revocation.setMFileTypedao(type);
        APP.getDaoInstant().getFileUserRevocationDao().insertOrReplace(revocation);
    }

    public void sendBroadCastToCenter(Context mContext, int type) {
        Intent mIntent = new Intent("com.fileupdate.networkChange");
        mIntent.putExtra("Type", type);
        //发送广播
        mContext.sendBroadcast(mIntent);
    }

    private String changename(String name) {
        //改名
        String renametoQIAN;
        java.util.Random r = new java.util.Random();
        String renameSUI = String.valueOf(r.nextInt()).substring(3, 5);//取得两位随机数
        String renamesplit[] = name.split("\\.");
        renametoQIAN = renamesplit[0];//前半部分
        if (renametoQIAN.length() > 2) {//一般都是英文加字母的名字  中文名字会有问题
            renametoQIAN = renametoQIAN.substring(0,renametoQIAN.length() - 2 );
        }
        String renametoHOU = renamesplit[1];//后半部分
        String renameto = renametoQIAN + renameSUI + "." + renametoHOU;
        return renameto;
    }

    /**
     * 返回截取路径后的文件名字
     */
    private String changpath(String path, String renameto) {
        String pathxx[] = path.split("/");
        int pathlenth = pathxx.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pathlenth - 1; i++) {

            sb.append("/" + pathxx[i]);
        }
        String sa = sb.toString();
        String sasds = sa.substring(1, sb.length());
        return sasds + "/" + renameto;
    }

    public boolean renameFile(String file, String toFile) {
        File toBeRenamed = new File(file);
        Log.i(TAG, "文件路径: " + file + toFile);
        // 检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            Log.i(TAG, "文件不存在: " + file);
            return false;
        }
        File newFile = new File(toFile);
        // 修改文件名
        if (toBeRenamed.renameTo(newFile)) {
            Log.i(TAG, "重命名成功.名字是" + toBeRenamed.getName() + "路径是：" + toBeRenamed.getAbsolutePath());
            return true;
        } else {
            Log.i(TAG, "重命名失败");
            return false;
        }

    }
}
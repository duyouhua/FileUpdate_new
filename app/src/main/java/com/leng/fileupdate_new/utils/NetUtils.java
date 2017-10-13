package com.leng.fileupdate_new.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/9/26.
 */

public class NetUtils {
    /**
     * 判断当前的网络连接状态是否能用
     * return ture  可用   flase不可用
     */
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;

    }


    public static boolean properDetection(Context mContext) {
        if (!isNetworkAvailable(mContext)) {
            Toast.makeText(mContext, "您的网络似乎有点问题哦", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean c6 = (Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox6", false);
        boolean c7 = (Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox7", false);
        if (c6 == false && c7 == false) {
            Toast.makeText(mContext, "请到设置页面选择您要使用的网络", Toast.LENGTH_SHORT).show();
            return false;
        } else if (c6 == true && c7 == false) {
            Toast.makeText(mContext, "您正在使用移动网络，请注意！", Toast.LENGTH_SHORT).show();

        } else if (c6 == true && c7 == true) {
            //优先使用wifi网络

        } else if (c7 == true && c6 == false) {
            //wifi网络

        }
        return true;
    }

    private void startDetection() {

    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}

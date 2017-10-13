package com.leng.fileupdate_new.moudle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingServerIP extends Activity {

    private EditText ftpIP, ftpPort, dataBaseAddress;
    private Button settingOK, settingCancle;
    private String ftpIPString, ftpPortString, dataBaseAddressString;
   private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_server_ip);
        this.setFinishOnTouchOutside(false);
         mContext=this;
        init();
        fillEditTextWithSavedData();
    }

    private boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }

    private void init() {
        ftpIP = (EditText) findViewById(R.id.ftp_ip);
        ftpPort = (EditText) findViewById(R.id.ftp_port);
        dataBaseAddress = (EditText) findViewById(R.id.database);

        settingOK = (Button) findViewById(R.id.setting_ok);
        settingCancle = (Button) findViewById(R.id.setting_cancle);

        settingCancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

        settingOK.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ftpIPString = ftpIP.getText().toString().trim();
                ftpPortString = ftpPort.getText().toString().trim();
                dataBaseAddressString = dataBaseAddress.getText().toString().trim();

                if (!isIPAddress(ftpIPString)) {
                    Toast.makeText(getApplicationContext(),
                           "IP输入格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 判断都不能为空
                if (ftpIPString.equals("") || ftpPortString.equals("") || dataBaseAddressString.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            R.string.inputError, Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferencesUtils.setParam(mContext,"ftpIpX",ftpIPString);
                    SharedPreferencesUtils.setParam(mContext,"ftpPortX",ftpPortString);
                    SharedPreferencesUtils.setParam(mContext,"databaseIpX",dataBaseAddressString);
                    SharedPreferencesUtils.setParam(mContext,"databasePortX","80");
                    Log.i("QWEQWEASD",ftpIPString+"=="+ftpPortString+"=="+dataBaseAddressString+"==");

                    SharedPreferences ServerSetting = getSharedPreferences(
                            "ServerSetting", 0);
                    SharedPreferences.Editor editor = ServerSetting.edit();
                    editor.putString("ftpIpa", ftpIPString);
                    editor.putString("ftpPorta", ftpPortString);
                    editor.putString("databaseIpa", dataBaseAddressString);
                    editor.putString("databasePorta", "80");
                    editor.commit();
                    SharedPreferences ServerSetting2 = getSharedPreferences(
                            "ServerSetting2", 0);
                    SharedPreferences.Editor editor2 = ServerSetting2.edit();
                    editor2.putString("ftpIp2", ftpIPString);
                    editor2.putString("ftpPort2", ftpPortString);
                    editor2.putString("databaseIp2", dataBaseAddressString);
                    editor2.putString("databasePort2", "80");
                    editor2.commit();





                    finish();

                }

            }
        });
    }

    /**
     * 要是之前保存了的话再进去的话会读取已经保存的数据展示在edittext上面
     */
    private void fillEditTextWithSavedData() {
        SharedPreferences ServerSetting = getSharedPreferences("ServerSetting2",
                0);

        String savedFtpIp = ServerSetting.getString("ftpIp2", null);

        // 如果为空的话就不读取了
        if (!(savedFtpIp == null)) {
            ftpIP.setText(savedFtpIp);
            ftpPort.setText(ServerSetting.getString("ftpPort2", null));
            dataBaseAddress.setText(ServerSetting.getString("databaseIp2", null));


        } else {
            return;
        }

    }

}

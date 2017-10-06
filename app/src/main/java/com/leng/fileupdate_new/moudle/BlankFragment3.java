package com.leng.fileupdate_new.moudle;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.services.connectServvice;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;


public class BlankFragment3 extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private String TAG = "BlankFragment3";
    private View view;
    private Button mSettingBtn1;
    private Button mSettingBtn2;
    private Button mSettingBtn3;
    private Button mSettingBtn4;
    private Button mSettingBtn5;
    /**
     * 上传完成后是否删除源文件
     */
    private TextView mShezhimorenYesorno;
    /**
     * Server IP设置
     */
    private TextView mSettingServeripBtn;
    /**
     * 更改密码
     */
    private TextView mSettingChangepwdBtn;
    private CheckBox mCheckbox1, mCheckbox2, mCheckbox3, mCheckbox4, mCheckbox5, mCheckbox6, mCheckbox7;

    private Context mContext;
    private RelativeLayout mSettingRelativeMobnet;
    private RelativeLayout mSettingRelativeWifinet;
    private boolean check6boolean = true;
    private boolean check7boolean = true;
    private boolean check8boolean = true;
    private int startType1, startType2, startType3, startType4, startType5;
    private MainActivity mActivity;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String resultPath = msg.obj.toString();
            switch (msg.what) {
                case 1:
                    String r1 = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath1", "null");
                    if (r1.equals("null")) {
                    } else {
                        if (!r1.equals(resultPath)) {
                            SharedPreferencesUtils.setParam(mContext, "lujinggaibianl1", "no");
                        }
                    }
                    SharedPreferencesUtils.setParam(mContext, "mSettingPath1", resultPath);
                    mSettingPath1.setText(resultPath + "");
                    break;
                case 2:


                    String r2 = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath2", "null");
                    if (r2.equals("null")) {
                    } else {
                        if (!r2.equals(resultPath)) {
                            SharedPreferencesUtils.setParam(mContext, "lujinggaibianl2", "no");
                        }
                    }
                    SharedPreferencesUtils.setParam(mContext, "mSettingPath2", resultPath);
                    mSettingPath2.setText(resultPath + "");


//                    mSettingPath2.setText(resultPath + "");
//                    SharedPreferencesUtils.setParam(mContext, "mSettingPath2", resultPath);
                    break;
                case 3:
                    String r3 = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath3", "null");
                    if (r3.equals("null")) {
                    } else {
                        if (!r3.equals(resultPath)) {
                            SharedPreferencesUtils.setParam(mContext, "lujinggaibianl3", "no");
                        }
                    }
                    SharedPreferencesUtils.setParam(mContext, "mSettingPath3", resultPath);
                    mSettingPath3.setText(resultPath + "");
                    break;
                case 4:
                    String r4 = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath4", "null");
                    if (r4.equals("null")) {
                    } else {
                        if (!r4.equals(resultPath)) {
                            SharedPreferencesUtils.setParam(mContext, "lujinggaibianl4", "no");
                        }
                    }
                    SharedPreferencesUtils.setParam(mContext, "mSettingPath4", resultPath);
                    mSettingPath4.setText(resultPath + "");
                    break;
                case 5:
                    String r5 = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath5", "null");
                    if (r5.equals("null")) {
                    } else {
                        if (!r5.equals(resultPath)) {
                            SharedPreferencesUtils.setParam(mContext, "lujinggaibianl5", "no");
                        }
                    }
                    SharedPreferencesUtils.setParam(mContext, "mSettingPath5", resultPath);
                    mSettingPath5.setText(resultPath + "");
                    break;
                default:
                    Log.i(TAG, "BlankFragment3 mHandler default");
                    break;
            }
        }
    };
    private TextView mSettingPath1, mSettingPath2, mSettingPath3, mSettingPath4, mSettingPath5;
    private RelativeLayout mSettingRelativeYesno;
    private CheckBox mCheckbox8;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
        mActivity.setHandler3(mHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
        mContext = getActivity();
        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        initSetPathTxt();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    private void initSetPathTxt() {
        mSettingPath1.setText((String) SharedPreferencesUtils.getParam(mContext, "mSettingPath1", ""));
        mSettingPath2.setText((String) SharedPreferencesUtils.getParam(mContext, "mSettingPath2", ""));
        mSettingPath3.setText((String) SharedPreferencesUtils.getParam(mContext, "mSettingPath3", ""));
        mSettingPath4.setText((String) SharedPreferencesUtils.getParam(mContext, "mSettingPath4", ""));
        mSettingPath5.setText((String) SharedPreferencesUtils.getParam(mContext, "mSettingPath5", ""));
        mCheckbox1.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox1", false));
        mCheckbox2.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox2", false));
        mCheckbox3.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox3", false));
        mCheckbox4.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox4", false));
        mCheckbox5.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox5", false));
        mCheckbox6.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox6", false));
        mCheckbox7.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox7", false));
        mCheckbox8.setChecked((Boolean) SharedPreferencesUtils.getParam(mContext, "checkbox8", false));
    }

    private void initView(View view) {

        mSettingBtn1 = (Button) view.findViewById(R.id.setting_btn1);
        mSettingBtn1.setOnClickListener(this);
        mSettingBtn2 = (Button) view.findViewById(R.id.setting_btn2);
        mSettingBtn2.setOnClickListener(this);
        mSettingBtn3 = (Button) view.findViewById(R.id.setting_btn3);
        mSettingBtn3.setOnClickListener(this);
        mSettingBtn4 = (Button) view.findViewById(R.id.setting_btn4);
        mSettingBtn4.setOnClickListener(this);
        mSettingBtn5 = (Button) view.findViewById(R.id.setting_btn5);
        mSettingBtn5.setOnClickListener(this);
//        mShezhimorenYesorno = (TextView) view.findViewById(R.id.shezhimoren_yesorno);
//        mShezhimorenYesorno.setOnClickListener(this);
        mSettingServeripBtn = (TextView) view.findViewById(R.id.setting_serverip_btn);
        mSettingServeripBtn.setOnClickListener(this);
        mSettingChangepwdBtn = (TextView) view.findViewById(R.id.setting_changepwd_btn);
        mSettingChangepwdBtn.setOnClickListener(this);

        mCheckbox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        mCheckbox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        mCheckbox3 = (CheckBox) view.findViewById(R.id.checkbox3);
        mCheckbox4 = (CheckBox) view.findViewById(R.id.checkbox4);
        mCheckbox5 = (CheckBox) view.findViewById(R.id.checkbox5);
        mCheckbox6 = (CheckBox) view.findViewById(R.id.checkbox6);
        mCheckbox7 = (CheckBox) view.findViewById(R.id.checkbox7);
        mCheckbox1.setOnCheckedChangeListener(this);
        mCheckbox2.setOnCheckedChangeListener(this);
        mCheckbox3.setOnCheckedChangeListener(this);
        mCheckbox4.setOnCheckedChangeListener(this);
        mCheckbox5.setOnCheckedChangeListener(this);
        mCheckbox6.setOnCheckedChangeListener(this);
        mCheckbox7.setOnCheckedChangeListener(this);
        mSettingRelativeMobnet = (RelativeLayout) view.findViewById(R.id.setting_relative_mobnet);
        mSettingRelativeMobnet.setOnClickListener(this);
        mSettingRelativeWifinet = (RelativeLayout) view.findViewById(R.id.setting_relative_wifinet);
        mSettingRelativeWifinet.setOnClickListener(this);
        mSettingPath1 = (TextView) view.findViewById(R.id.setting_path_1);
        mSettingPath2 = (TextView) view.findViewById(R.id.setting_path_2);
        mSettingPath3 = (TextView) view.findViewById(R.id.setting_path_3);
        mSettingPath4 = (TextView) view.findViewById(R.id.setting_path_4);
        mSettingPath5 = (TextView) view.findViewById(R.id.setting_path_5);
        mSettingRelativeYesno = (RelativeLayout) view.findViewById(R.id.setting_relative_yesno);
        mSettingRelativeYesno.setOnClickListener(this);
        mCheckbox8 = (CheckBox) view.findViewById(R.id.checkbox8);
    }


    @Override
    public void onClick(View v) {
        Intent i1, i2, i3, i4, i5;
        switch (v.getId()) {
            case R.id.setting_btn1:
                i1 = new Intent(mContext, LiuLanActivity.class);
                startType1 = 1;
                i1.putExtra("startType", startType1);
                startActivityForResult(i1, 1);
                break;
            case R.id.setting_btn2:
                i2 = new Intent(mContext, LiuLanActivity.class);
                startType2 = 2;
                i2.putExtra("startType", startType2);
                startActivityForResult(i2, 2);
                break;
            case R.id.setting_btn3:
                i3 = new Intent(mContext, LiuLanActivity.class);
                startType3 = 3;
                i3.putExtra("startType", startType3);
                startActivityForResult(i3, 3);
                break;
            case R.id.setting_btn4:
                i4 = new Intent(mContext, LiuLanActivity.class);
                startType4 = 4;
                i4.putExtra("startType", startType4);
                startActivityForResult(i4, 4);
                break;
            case R.id.setting_btn5:
                i5 = new Intent(mContext, LiuLanActivity.class);
                startType5 = 5;
                i5.putExtra("startType", startType5);
                startActivityForResult(i5, 5);
                break;

            case R.id.setting_serverip_btn:
                new HideClick().start();
                if (HideClick.sIsAlive >= 5) {
                    Intent intent = new Intent(getActivity(), SettingServerIP.class);
                    startActivity(intent);
                }
                break;
            case R.id.setting_changepwd_btn:
                showPasswordSetDialog();
                break;
            case R.id.setting_relative_mobnet:
                relativeCheckStatus(mCheckbox6, check6boolean);
                break;
            case R.id.setting_relative_wifinet:
                relativeCheckStatus(mCheckbox7, check7boolean);
                break;
            case R.id.setting_relative_yesno:
                relativeCheckStatus(mCheckbox8, check8boolean);
                break;
        }
    }

    static class HideClick extends Thread {
        public static volatile int sIsAlive = 0;

        @Override
        public void run() {
            sIsAlive++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sIsAlive > 0) {
                sIsAlive--;
            }
            super.run();

        }
    }


    /**
     * 设置密码的弹窗
     */
    private void showPasswordSetDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(mContext, R.layout.change_pwd, null);
        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        final EditText edPassWord = (EditText) view
                .findViewById(R.id.pwd_et1);
        final EditText edPassWordConfirm = (EditText) view
                .findViewById(R.id.pwd_et2);

        TextView OK = (TextView) view.findViewById(R.id.pwd_tv1);
        TextView Cancle = (TextView) view.findViewById(R.id.pwd_tv2);

        OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String password = edPassWord.getText().toString().trim();
                String passwordConfirm = edPassWordConfirm.getText().toString()
                        .trim();
                if (!TextUtils.isEmpty(password) && !passwordConfirm.isEmpty()) {
                    // 当输入的2个内容相同
                    if (password.equals(passwordConfirm)) {
                        Toast.makeText(mContext, "设置成功",
                                Toast.LENGTH_SHORT).show();

                        SharedPreferences savedPasswordPref = mContext.getSharedPreferences(
                                "savedPassword", 0);

                        SharedPreferences.Editor editor = savedPasswordPref
                                .edit();
                        editor.putString("savedPassword", password);
                        editor.commit();
                        // editor.putString("password",
                        // MD5Utils.encode(password)).commit();
                        dialog.dismiss();

                    } else {
                        Toast.makeText(mContext, "两次输入密码不一致",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "输入内容不能为空",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void relativeCheckStatus(View v, boolean b) {
        switch (v.getId()) {
            case R.id.checkbox6:
                if (b) {
                    mCheckbox6.setChecked(b);
                    check6boolean = false;
                    SharedPreferencesUtils.setParam(mContext, "checkbox6", true);
                } else {
                    mCheckbox6.setChecked(b);
                    check6boolean = true;
                    SharedPreferencesUtils.setParam(mContext, "checkbox6", false);
                }
                break;
            case R.id.checkbox7:
                if (b) {
                    mCheckbox7.setChecked(b);
                    check7boolean = false;
                    SharedPreferencesUtils.setParam(mContext, "checkbox7", true);
                } else {
                    mCheckbox7.setChecked(b);
                    check7boolean = true;
                    SharedPreferencesUtils.setParam(mContext, "checkbox7", false);
                }
                break;
            case R.id.checkbox8:
                if (b) {
                    mCheckbox8.setChecked(b);
                    check8boolean = false;
                    SharedPreferencesUtils.setParam(mContext, "checkbox8", true);
                } else {
                    mCheckbox8.setChecked(b);
                    check8boolean = true;
                    SharedPreferencesUtils.setParam(mContext, "checkbox8", false);
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.checkbox1:
                saveCheckStatus(mCheckbox1, b);
                startServicesx("1", b);
                break;
            case R.id.checkbox2:
                saveCheckStatus(mCheckbox2, b);
                startServicesx("2", b);
                break;
            case R.id.checkbox3:
                saveCheckStatus(mCheckbox3, b);
                startServicesx("3", b);
                break;
            case R.id.checkbox4:
                saveCheckStatus(mCheckbox4, b);
                startServicesx("4", b);
                break;
            case R.id.checkbox5:
                saveCheckStatus(mCheckbox5, b);
                startServicesx("5", b);
                break;

        }
    }

    private void startServicesx(String mode, boolean onoff) {
        Intent intentxx = new Intent(mContext, connectServvice.class);
        intentxx.putExtra("setmode", mode);
        intentxx.putExtra("setstartORstop", onoff);
        mContext.startService(intentxx);
    }

    private void saveCheckStatus(View v, boolean b) {
        switch (v.getId()) {
            case R.id.checkbox1:
                if (b)
                    SharedPreferencesUtils.setParam(mContext, "checkbox1", true);
                else
                    SharedPreferencesUtils.setParam(mContext, "checkbox1", false);
                break;
            case R.id.checkbox2:
                if (b)
                    SharedPreferencesUtils.setParam(mContext, "checkbox2", true);
                else
                    SharedPreferencesUtils.setParam(mContext, "checkbox2", false);
                break;
            case R.id.checkbox3:
                if (b)
                    SharedPreferencesUtils.setParam(mContext, "checkbox3", true);
                else
                    SharedPreferencesUtils.setParam(mContext, "checkbox3", false);
                break;
            case R.id.checkbox4:
                if (b)
                    SharedPreferencesUtils.setParam(mContext, "checkbox4", true);
                else
                    SharedPreferencesUtils.setParam(mContext, "checkbox4", false);
                break;
            case R.id.checkbox5:
                if (b)
                    SharedPreferencesUtils.setParam(mContext, "checkbox5", true);
                else
                    SharedPreferencesUtils.setParam(mContext, "checkbox5", false);
                break;

        }
    }
}

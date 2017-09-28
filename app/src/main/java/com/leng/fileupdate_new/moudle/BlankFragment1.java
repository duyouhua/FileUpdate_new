package com.leng.fileupdate_new.moudle;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.ChangeModeFile;
import com.leng.fileupdate_new.contrl.FileManger;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;
import com.leng.other.CommomDialog;
import com.leng.other.LoadingDialog;

import static com.leng.fileupdate_new.MainActivity.fragmentManager;
import static com.leng.fileupdate_new.utils.Constanxs.INFO1;

public class BlankFragment1 extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout mDefaultOne;
    private LinearLayout mDefaultTwo;
    private LinearLayout mDefaultThree;
    private LinearLayout mDefaultFour;
    private LinearLayout mDefaultFive;
    private Context mContext;
    private FragmentTransaction fragmentTransaction;

    private ChangeModeFile cmf;
    private static final String TAG = "BlankFragment1";
    /**
     * 文件夹名
     */
    private TextView mDirnameOne;
    /**
     * 文件夹名
     */
    private TextView mDirnameTwo;
    /**
     * 文件夹名
     */
    private TextView mDirnameThree;
    /**
     * 文件夹名
     */
    private TextView mDirnameFour;
    /**
     * 文件夹名
     */
    private TextView mDirnameFive;
    /**
     * 文件个数
     */
    private TextView mNumOne;
    /**
     * 文件个数
     */
    private TextView mNumTwo;
    /**
     * 文件个数
     */
    private TextView mNumThree;
    /**
     * 文件个数
     */
    private TextView mNumFour;
    /**
     * 文件个数
     */
    private TextView mNumFive;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        cmf = (ChangeModeFile) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_blank_fragment1, container, false);
        initView(view);
        Log.i(TAG,"DASDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        return view;
    }

    private void initView(View view) {

        mDefaultOne = (LinearLayout) view.findViewById(R.id.default_one);
        mDefaultOne.setOnClickListener(this);
        mDefaultTwo = (LinearLayout) view.findViewById(R.id.default_two);
        mDefaultTwo.setOnClickListener(this);
        mDefaultThree = (LinearLayout) view.findViewById(R.id.default_three);
        mDefaultThree.setOnClickListener(this);
        mDefaultFour = (LinearLayout) view.findViewById(R.id.default_four);
        mDefaultFour.setOnClickListener(this);
        mDefaultFive = (LinearLayout) view.findViewById(R.id.default_five);
        mDefaultFive.setOnClickListener(this);
        mDirnameOne = (TextView) view.findViewById(R.id.dirname_one);
        mDirnameTwo = (TextView) view.findViewById(R.id.dirname_two);
        mDirnameThree = (TextView) view.findViewById(R.id.dirname_three);
        mDirnameFour = (TextView) view.findViewById(R.id.dirname_four);
        mDirnameFive = (TextView) view.findViewById(R.id.dirname_five);
        mNumOne = (TextView) view.findViewById(R.id.num_one);
        mNumTwo = (TextView) view.findViewById(R.id.num_two);
        mNumThree = (TextView) view.findViewById(R.id.num_three);
        mNumFour = (TextView) view.findViewById(R.id.num_four);
        mNumFive = (TextView) view.findViewById(R.id.num_five);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onResume();
        } else {
            onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDeaufutPath();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 获取默认设置的路径
     */
    private void getDeaufutPath() {
        String a = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath1", "null");
        String b = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath2", "null");
        String c = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath3", "null");
        String d = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath4", "null");
        String e = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath5", "null");

        if (!a.equals("null") || !b.equals("null") || !c.equals("null") || !d.equals("null") || !e.equals("null")) {
            String a11 = null, a22 = null, a33 = null, a44 = null, a55 = null;
            if (!a.equals("null")) {
                String a1[] = a.split("/");
                a11 = a1[a1.length - 1];
                mDirnameOne.setText(a11);
                int NUM = FileManger.getSingleton().getFileNun(a);
                mNumOne.setText(NUM + getResources().getString(R.string.ge));
                Log.i(TAG, NUM + "");
            }
            if (!b.equals("null")) {
                String a2[] = b.split("/");
                a22 = a2[a2.length - 1];
                mDirnameTwo.setText(a22);
                int NUM = FileManger.getSingleton().getFileNun(b);
                mNumTwo.setText(NUM + getResources().getString(R.string.ge));
                Log.i(TAG, NUM + "");
            }
            if (!c.equals("null")) {
                String a3[] = c.split("/");
                a33 = a3[a3.length - 1];
                mDirnameThree.setText(a33);
                int NUM = FileManger.getSingleton().getFileNun(c);
                mNumThree.setText(NUM + getResources().getString(R.string.ge));
                Log.i(TAG, NUM + "");
            }
            if (!d.equals("null")) {
                String a4[] = d.split("/");
                a44 = a4[a4.length - 1];
                mDirnameFour.setText(a44);
                int NUM = FileManger.getSingleton().getFileNun(d);
                mNumFour.setText(NUM + getResources().getString(R.string.ge));
                Log.i(TAG, NUM + "");
            }
            if (!e.equals("null")) {
                String a5[] = e.split("/");
                a55 = a5[a5.length - 1];
                mDirnameFive.setText(a55);
                int NUM = FileManger.getSingleton().getFileNun(e);
                mNumFive.setText(NUM + getResources().getString(R.string.ge));
                Log.i(TAG, NUM + "");
            }
            Log.i(TAG, a11 + "\n" + a22 + "\n" + a33 + "\n" + a44 + "\n" + a55);

        }
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.default_one:
                String a = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath1", "null");
                if (a.equals("null")) {
                    showDialog();
                    return;
                }
                cmf.showFile(1, INFO1);
                break;
            case R.id.default_two:
                String b = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath2", "null");
                if (b.equals("null")) {
                    showDialog();
                    return;
                }
                cmf.showFile(2, INFO1);
                break;
            case R.id.default_three:
                String c = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath3", "null");
                if (c.equals("null")) {
                    showDialog();
                    return;
                }
                cmf.showFile(3, INFO1);
                break;
            case R.id.default_four:
                String d = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath4", "null");
                if (d.equals("null")) {
                    showDialog();
                    return;
                }
                cmf.showFile(4, INFO1);
                break;
            case R.id.default_five:
                String e = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath5", "null");
                if (e.equals("null")) {
                    showDialog();
                    return;
                }
                cmf.showFile(5, INFO1);
                break;
            default:
                break;
        }


    }

    private void showDialog() {
        new CommomDialog(mContext, R.style.dialog, "请先设置默认目录", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        }).setTitle("提示").show();
    }

    private void showLoding() {

        new LoadingDialog(mContext).setMessage("正在加载...").show();
    }
}

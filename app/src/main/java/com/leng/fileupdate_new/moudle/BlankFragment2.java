package com.leng.fileupdate_new.moudle;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.CallbackChild;
import com.leng.fileupdate_new.contrl.CallbackLocFiel;
import com.leng.fileupdate_new.contrl.ChangeModeFile;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;

import static com.leng.fileupdate_new.MainActivity.fragmentManager;
import static com.leng.fileupdate_new.utils.Constanxs.INFO2arg;
import static com.leng.fileupdate_new.utils.Constanxs.WEISHANGCHUAN_P;


public class BlankFragment2 extends Fragment implements View.OnClickListener {
    private final static String TAG = "BlankFragment2";
    private View view;
    private Button mBtnWei;
    private Button mBtnShang;
    private Button mBtnYi;
    private LinearLayout mContentContainer2;
    private BlankFragmentChild1 blankFragmentChild1;
    private BlankFragmentChild2 blankFragmentChild2;
    private BlankFragmentChild3 blankFragmentChild3;
    private BlankFragmentLocFile blankFragmentLocFile;
    private FragmentTransaction fragmentTransaction;
    private static final int TITLE1 = 1, TITLE2 = 2, TITLE3 = 3;
    private ChangeModeFile cmf;
    private Context mContext;
    private MainActivity may;
    private String mations;

    private boolean isLoadLocFile;

    private CallbackLocFiel clf;

    CallbackChild cd;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == INFO2arg) {
                switch (msg.what) {
                    case 1:
                        SharedPreferencesUtils.setParam(mContext, "defultpage", "1");
                        mations = "1";
                        cd.setNums(mations);
                        break;
                    case 2:
                        SharedPreferencesUtils.setParam(mContext, "defultpage", "2");
                        mations = "2";
                        cd.setNums(mations);
                        Toast.makeText(mContext, "第二个页面", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        SharedPreferencesUtils.setParam(mContext, "defultpage", "3");
                        mations = "3";
                        cd.setNums(mations);
                        break;
                    case 4:
                        SharedPreferencesUtils.setParam(mContext, "defultpage", "4");
                        mations = "4";
                        cd.setNums(mations);
                        break;
                    case 5:
                        SharedPreferencesUtils.setParam(mContext, "defultpage", "5");
                        mations = "5";
                        cd.setNums(mations);
                        Toast.makeText(mContext, "第五个页面", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                startLoding2();
            }
        }
    };

    /**
     * 展示本地文件夹
     */
    private Handler mHandlerLoc = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadFragmentLocFile();
//            Toast.makeText(mContext, "点击TAB2过来的信息", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        may = (MainActivity) activity;
        may.setHandler2(mHandler);
        may.setHandlerLocFile(mHandlerLoc);
        clf = (CallbackLocFiel) getActivity();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i(TAG, "onHiddenChanged==fragment2===显示");
        } else {
            Log.i(TAG, "onHiddenChanged==fragment2===隐藏");
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume==fragment2");
//        startLoding();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause==fragment2");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        cmf = (ChangeModeFile) getActivity();
        cd = (CallbackChild) getActivity();
        mBtnWei = (Button) view.findViewById(R.id.btn_wei);
        mBtnWei.setOnClickListener(this);
        mBtnShang = (Button) view.findViewById(R.id.btn_shang);
        mBtnShang.setOnClickListener(this);
        mBtnYi = (Button) view.findViewById(R.id.btn_yi);
        mBtnYi.setOnClickListener(this);
        mContentContainer2 = (LinearLayout) view.findViewById(R.id.content_container2);
        startLoding();
    }

    private void startLoding() {
        fragmentTransaction = fragmentManager.beginTransaction();
        HideFrament();
        blankFragmentChild1 = new BlankFragmentChild1();
        fragmentTransaction.add(R.id.content_container2, blankFragmentChild1);
        fragmentTransaction.commit();
        changeButton(TITLE1);
    }

    private void startLoding2() {
        isLoadLocFile = true;
        fragmentTransaction = fragmentManager.beginTransaction();
        if (blankFragmentLocFile != null && blankFragmentLocFile.isResumed()) {
            fragmentTransaction.hide(blankFragmentLocFile);
        }
        if (blankFragmentChild1 == null) {
            blankFragmentChild1 = new BlankFragmentChild1();
            fragmentTransaction.add(R.id.content_container2, blankFragmentChild1);
        } else {
            fragmentTransaction.show(blankFragmentChild1);

        }

        fragmentTransaction.commit();
    }

    private void loadFragmentLocFile() {
        isLoadLocFile = false;
        fragmentTransaction = fragmentManager.beginTransaction();
        if (blankFragmentChild1 != null && blankFragmentChild1.isResumed()) {
            fragmentTransaction.hide(blankFragmentChild1);
        }
        if (blankFragmentLocFile == null) {
            blankFragmentLocFile = new BlankFragmentLocFile();
            fragmentTransaction.add(R.id.content_container2, blankFragmentLocFile);
        } else {
            fragmentTransaction.show(blankFragmentLocFile);

        }

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

        fragmentTransaction = fragmentManager.beginTransaction();
        HideFrament();
        switch (v.getId()) {
            case R.id.btn_wei:
                if (isLoadLocFile) {
                    if (blankFragmentChild1 == null) {
                        blankFragmentChild1 = new BlankFragmentChild1();
                        fragmentTransaction.add(R.id.content_container2, blankFragmentChild1);
                    } else {
                        fragmentTransaction.show(blankFragmentChild1);
                    }
                    clf.setModeLoc(1);
                } else {
                    if (blankFragmentLocFile == null) {
                        blankFragmentLocFile = new BlankFragmentLocFile();
                        fragmentTransaction.add(R.id.content_container2, blankFragmentLocFile);
                    } else {
                        fragmentTransaction.show(blankFragmentLocFile);
                    }
                    clf.setModeLoc(2);//加载本地设置2
                }
                changeButton(TITLE1);
                break;
            case R.id.btn_shang:
                if (blankFragmentChild2 == null) {
                    blankFragmentChild2 = new BlankFragmentChild2();
                    fragmentTransaction.add(R.id.content_container2, blankFragmentChild2);
                } else {
                    fragmentTransaction.show(blankFragmentChild2);
                }
                changeButton(TITLE2);
                break;
            case R.id.btn_yi:
                if (blankFragmentChild3 == null) {
                    blankFragmentChild3 = new BlankFragmentChild3();
                    fragmentTransaction.add(R.id.content_container2, blankFragmentChild3);
                } else {
                    fragmentTransaction.show(blankFragmentChild3);
                }
                changeButton(TITLE3);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }


    private void HideFrament() {
        if (blankFragmentChild1 != null) {
            fragmentTransaction.hide(blankFragmentChild1);
        }
        if (blankFragmentChild2 != null) {
            fragmentTransaction.hide(blankFragmentChild2);
        }
        if (blankFragmentChild3 != null) {
            fragmentTransaction.hide(blankFragmentChild3);
        }
        if (blankFragmentLocFile != null) {
            fragmentTransaction.hide(blankFragmentLocFile);
        }

    }

    private void changeButton(int p) {
        switch (p) {
            case TITLE1:
                mBtnWei.setBackgroundColor(Color.parseColor("#009CFF"));
                mBtnWei.setText("(未上传" + WEISHANGCHUAN_P + ")");
                mBtnShang.setBackgroundColor(Color.WHITE);
                mBtnYi.setBackgroundColor(Color.WHITE);


                break;
            case TITLE2:
                mBtnShang.setBackgroundColor(Color.parseColor("#009CFF"));
                mBtnShang.setText("(上传中" + WEISHANGCHUAN_P + ")");
                mBtnWei.setBackgroundColor(Color.WHITE);
                mBtnYi.setBackgroundColor(Color.WHITE);
                break;
            case TITLE3:
                mBtnYi.setBackgroundColor(Color.parseColor("#009CFF"));
                mBtnYi.setText("(已完成" + WEISHANGCHUAN_P + ")");
                mBtnWei.setBackgroundColor(Color.WHITE);
                mBtnShang.setBackgroundColor(Color.WHITE);
                break;
        }
    }
}

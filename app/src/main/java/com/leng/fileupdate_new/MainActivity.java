package com.leng.fileupdate_new;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.leng.fileupdate_new.contrl.CabackInfoNums;
import com.leng.fileupdate_new.contrl.CabackPv;
import com.leng.fileupdate_new.contrl.CallBacklistview;
import com.leng.fileupdate_new.contrl.CallbackChild;
import com.leng.fileupdate_new.contrl.CallbackChild2;
import com.leng.fileupdate_new.contrl.CallbackLocFiel;
import com.leng.fileupdate_new.contrl.ChangeModeFile;
import com.leng.fileupdate_new.contrl.ContinueFTP2;
import com.leng.fileupdate_new.contrl.ContinueFtp;
import com.leng.fileupdate_new.moudle.BlankFragment1;
import com.leng.fileupdate_new.moudle.BlankFragment2;
import com.leng.fileupdate_new.moudle.BlankFragment3;
import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.RegUtil;

import java.io.IOException;

import static com.leng.fileupdate_new.utils.Constanxs.INFO1;
import static com.leng.fileupdate_new.utils.Constanxs.INFO2arg;
import static com.leng.fileupdate_new.utils.Constanxs.INFO4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ChangeModeFile, CallbackChild, CallbackLocFiel, CallbackChild2, CabackPv, CabackInfoNums {
    private BlankFragment1 fragment1;

    public static MainActivity mActivityContext;
    private BlankFragment2 fragment2;
    private BlankFragment3 fragment3;
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;

    private Button mTabBt1;
    private Button mTabBt2;
    private Button mTabBt3;
    private ImageView mTabBgView;
    private LinearLayout mTabItemContainer;
    private int mSelectIndex = 0;
    private View last, now;
    private String TAG = "MainActivity";

    public Handler mHandler3, mHandler2, mHandler4, mHandlerChid1, mHandlerLocFile, mHandlerLocFileFragment, mHandlerUpding;
    public Handler mHandlerActive;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                Toast.makeText(MainActivity.this, "" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivityContext = this;
//        initRegUtil();
        initView();
        startLoding();
//        threadconnec();
    }

    private void threadconnec() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isx = false;
                try {
                    ContinueFTP2 continueFtp = new ContinueFTP2(mActivityContext);
                    isx = continueFtp.connect(Constanxs.FTPIP, Constanxs.FTPPORT, Constanxs.FTPUSER, Constanxs.FTPPASS);
                    Thread.sleep(2000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message me = new Message();
                me.obj = isx;
                me.what = 111;
                mHandler.sendMessage(me);
            }
        }).start();


    }

    private void initRegUtil() {
        RegUtil regUtil = new RegUtil(this);
        regUtil.SetDialogCancelCallBack(new RegUtil.DialogCancelInterface() {
            @Override
            public void ToFinishActivity() {
                finish();
            }

            @Override
            public void ToFinishActivity_pwd() {
                finish();
            }
        });
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        mTabBt1 = (Button) findViewById(R.id.tab_bt_1);
        mTabBt1.setOnClickListener(this);
        mTabBt2 = (Button) findViewById(R.id.tab_bt_2);
        mTabBt2.setOnClickListener(this);
        mTabBt3 = (Button) findViewById(R.id.tab_bt_3);
        mTabBt3.setOnClickListener(this);

        mTabBgView = (ImageView) findViewById(R.id.tab_bg_view);
        mTabItemContainer = (LinearLayout) findViewById(R.id.tab_item_container);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();
        HideFrament();
        switch (v.getId()) {
            case R.id.tab_bt_1:
                if (fragment1 == null) {
                    fragment1 = new BlankFragment1();
                    fragmentTransaction.add(R.id.content_container, fragment1);
                } else {
                    fragmentTransaction.show(fragment1);
                }
                last = mTabItemContainer.getChildAt(mSelectIndex);
                now = mTabItemContainer.getChildAt(0);
                startAnimation(last, now);
                mSelectIndex = 0;
                break;
            case R.id.tab_bt_2:
                if (fragment2 == null) {
                    fragment2 = new BlankFragment2();
                    fragmentTransaction.add(R.id.content_container, fragment2);
                } else {
                    fragmentTransaction.show(fragment2);
                }
                last = mTabItemContainer.getChildAt(mSelectIndex);
                now = mTabItemContainer.getChildAt(1);
                startAnimation(last, now);
                mSelectIndex = 1;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                            Message message = new Message();
                            message.what = 1234;
                            mHandlerLocFile.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;

            case R.id.tab_bt_3:
                if (fragment3 == null) {
                    fragment3 = new BlankFragment3();
                    fragmentTransaction.add(R.id.content_container, fragment3);
                } else {
                    fragmentTransaction.show(fragment3);
                }
                last = mTabItemContainer.getChildAt(mSelectIndex);
                now = mTabItemContainer.getChildAt(2);
                startAnimation(last, now);
                mSelectIndex = 2;
                break;
        }
        fragmentTransaction.commit();
    }


    private void startLoding() {
        fragmentTransaction = fragmentManager.beginTransaction();
        HideFrament();
        fragment1 = new BlankFragment1();
        fragmentTransaction.add(R.id.content_container, fragment1);
        fragmentTransaction.commit();
    }

    private void startAnimation(View last, View now) {
        TranslateAnimation ta = new TranslateAnimation(last.getLeft(),
                now.getLeft(), 0, 0);
        ta.setDuration(300);
        ta.setFillAfter(true);
        mTabBgView.startAnimation(ta);
    }

    private void HideFrament() {
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }
        if (fragment3 != null) {
            fragmentTransaction.hide(fragment3);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            switch (resultCode) {
                case 1:
                    confimPath(data, resultCode);
                    break;
                case 2:
                    confimPath(data, resultCode);
                    break;
                case 3:
                    confimPath(data, resultCode);
                    break;
                case 4:
                    confimPath(data, resultCode);
                    break;
                case 5:
                    confimPath(data, resultCode);
                    break;
                case 99:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(50);
                                Message message = new Message();
                                message.what = 1231234;
                                mHandlerLocFileFragment.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case 98:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(50);
                                Message message = new Message();
                                message.what = 12312345;
                                mHandlerLocFileFragment.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                default:
                    Log.i(TAG, "onActivityResult default");
                    break;

            }

        }


    }

    private void confimPath(Intent data, int resultCode) {
        Message msg = new Message();
        msg.obj = data.getStringExtra("strPATH");// 传输的内容
        msg.what = resultCode;
        Toast.makeText(this, "" + resultCode, Toast.LENGTH_SHORT).show();
        mHandler3.sendMessage(msg);
    }

    public void setHandler3(Handler handler3) {
        mHandler3 = handler3;

    }

    /**
     * fragment2
     */
    public void setHandler2(Handler handler2) {
        mHandler2 = handler2;

    }

    public void setmHandlerChid1(Handler handlerChid1) {
        mHandlerChid1 = handlerChid1;

    }

    public void setmHandlerUpding(Handler handlerChid2) {
        mHandlerUpding = handlerChid2;

    }

    public void setHandlerLocFile(Handler mHandlerLocFile1) {
        mHandlerLocFile = mHandlerLocFile1;

    }

    public void setmHandlerLocFileFragment(Handler mHandlerLocFileFragment1) {
        mHandlerLocFileFragment = mHandlerLocFileFragment1;

    }

    /**
     * fragment2
     */
    public void setHandler4(Handler handler4) {
        mHandler4 = handler4;

    }

    /**
     * fragment2
     */
    public void setmHandlerActivex(Handler mHandlerActivex) {
        mHandlerActive = mHandlerActivex;

    }

    @Override
    public void showFile(final int size, int mode) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        HideFrament();

        if (mode == INFO1) {
            if (fragment2 == null) {
                fragment2 = new BlankFragment2();
                fragmentTransaction.add(R.id.content_container, fragment2);
            } else {
                fragmentTransaction.show(fragment2);
            }
            fragmentTransaction.commit();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msgINFO1 = new Message();
                    msgINFO1.arg1 = INFO2arg;// 传输的内容
                    msgINFO1.what = size;
                    mHandler2.sendMessage(msgINFO1);
                }
            }).start();

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setNums(String nums) {
        Message msgINFO4 = new Message();
        msgINFO4.arg1 = INFO4;// 传输的内容
        msgINFO4.obj = nums + "";
        mHandler4.sendMessage(msgINFO4);
    }

    /**
     * 判断本地与 默认目录的接口
     */
    @Override
    public void setModeLoc(int mode) {
        if (mode == 2) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                        Message message = new Message();
                        message.what = 123123;
                        mHandlerLocFileFragment.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(50);
                        Message message = new Message();
                        message.arg1 = 7536;
                        mHandler4.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }
        Toast.makeText(this, "++" + mode, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setMsg(int msg) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Message ms = new Message();
//                ms.what = 9876;
//                mHandlerUpding.sendMessageDelayed(ms,100);
//            }
//        }).start();
        Log.i(TAG, "ADADAD");

    }

    @Override
    public void setProgresValues(String pathname, String progress, String pathoth) {
        Log.i(TAG, pathname + "的上传进度:" + progress);
        Message message = new Message();
        message.what = 4560;
        Bundle bundle = new Bundle();
        bundle.putString("pathname", pathname);
        bundle.putString("progress", progress);
        bundle.putString("pathpath", pathoth);
        message.setData(bundle);
        mHandlerUpding.sendMessage(message);
    }

    /**
     * 三个界面的数量回调 发送消息到fragment2
     */
    @Override
    public void setInfoNums() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Message message = new Message();
//                message.arg2 = 5690;
//                mHandler2.sendMessage(message);
//
//                //更新上传完成页面数量变化
//                Message message1 = new Message();
//                message1.what = 4571;
//
//                mHandlerActive.sendMessage(message1);
//            }
//        }).start();
//
    }


}

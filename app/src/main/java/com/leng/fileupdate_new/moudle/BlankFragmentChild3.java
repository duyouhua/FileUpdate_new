package com.leng.fileupdate_new.moudle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.ThreadPoolProxy;

public class BlankFragmentChild3 extends Fragment {
    ThreadPoolProxy threadPoolProxy = new ThreadPoolProxy(2, 4, 6000);
    private View view;
    private static final String TAG = "BlankFragmentChild3";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank_fragment_child3, container, false);
        initView();
        return view;

    }

    private void initView() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "线程一" + i);

                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "线程二" + i);

                }
            }
        };
        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "线程仨" + i);

                }
            }
        };   Runnable r4 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "线程四" + i);

                }
            }
        };

        threadPoolProxy.executeTask(r1);
        threadPoolProxy.removeTask(r1);
        threadPoolProxy.executeTask(r2);
        threadPoolProxy.executeTask(r3);
        threadPoolProxy.executeTask(r4);
    }

}

package com.leng.fileupdate_new.moudle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Adapter.Child2Adapter;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;

import java.util.ArrayList;
import java.util.List;

import static com.leng.fileupdate_new.moudle.BlankFragmentChild1.mapShangchuanNUMname;
import static com.leng.fileupdate_new.moudle.BlankFragmentChild1.mapShangchuanNUMpath;

public class BlankFragmentChild2 extends Fragment {
    private View view;
    private ListView mChild2Listview;
    private ArrayList<String> mListname = new ArrayList<>();
    private ArrayList<String> mListpath = new ArrayList<>();
    private MainActivity ma;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9876:
//                    showFile();
                    break;
            }
        }
    };
    private RelativeLayout mChild2RelativeList;
    private RelativeLayout mChild2RelativeEmpty;
    private static final String TAG = "BlankFragmentChild2";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ma = (MainActivity) activity;
        ma.setmHandlerUpding(mHandler);
    }

    //线程管理对象
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_blank_fragment_child2, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mChild2Listview = (ListView) view.findViewById(R.id.child2_listview);
        mChild2RelativeList = (RelativeLayout) view.findViewById(R.id.child2Relative_list);
        mChild2RelativeEmpty = (RelativeLayout) view.findViewById(R.id.child2Relative_empty);
//        showFile();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showFile();
        } else {
        }
    }

    private void showFile() {
        mListname.clear();
        mListpath.clear();

        for (int i = 0; i < FileUserDaoQuery().size(); i++) {
            Log.i(TAG, FileUserDaoQuery().get(0).getMFileNamedao() + "==" + FileUserDaoQuery().get(0).getMFilePathdao());
            mListname.add(FileUserDaoQuery().get(i).getMFileNamedao());
            mListpath.add(FileUserDaoQuery().get(i).getMFilePathdao());
        }
        if (FileUserDaoQuery() .size() > 0) {
            for (int i = 0; i < mapShangchuanNUMname.size(); i++) {
                mListname.add(mapShangchuanNUMname.get(i));
                mListpath.add(mapShangchuanNUMpath.get(i));
            }
            mChild2RelativeEmpty.setVisibility(View.GONE);
            mChild2RelativeList.setVisibility(View.VISIBLE);
            Log.i(TAG, mListname.size() + "----" + mListpath.get(0));

            mChild2Listview.setAdapter(new Child2Adapter(mContext, mListname, mListpath));
        } else {
            mChild2RelativeEmpty.setVisibility(View.VISIBLE);
            mChild2RelativeList.setVisibility(View.GONE);
        }
    }


    public static List<FileUser> FileUserDaoQuery() {
        List<FileUser> liss = APP.getDaoInstant().getFileUserDao().queryBuilder().list();
        return liss;
    }


}

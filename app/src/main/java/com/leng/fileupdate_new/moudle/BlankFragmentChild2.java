package com.leng.fileupdate_new.moudle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Adapter.Child2Adapter;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.other.CommomDialog2;

import java.io.File;
import java.util.ArrayList;

public class BlankFragmentChild2 extends Fragment implements View.OnClickListener {
    private View view;
    private ListView mChild2Listview;
    private ArrayList<String> mListname = new ArrayList<>();
    private ArrayList<String> mListpath = new ArrayList<>();
    private MainActivity ma;
    private Context mContext;
    private boolean isSelectdAll = true;
    private Child2Adapter mAdapter;
    private int checktrueNums = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9876:
//                    showFile();

                    break;
                case 4579:
                    showFile();
                    break;
            }
        }


    };


    private RelativeLayout mChild2RelativeList;
    private RelativeLayout mChild2RelativeEmpty;
    private static final String TAG = "BlankFragmentChild2";
    /**
     * 全选
     */
    private Button mSelectAllUpding;
    /**
     * 撤销
     */
    private Button mUpdateButtonUpding;
    /**
     * 删除
     */
    private Button mDeletButtonUpding;

    int checkNum = 0;


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
        mChild2Listview.setOnItemClickListener(clickListener);
        mChild2RelativeList = (RelativeLayout) view.findViewById(R.id.child2Relative_list);
        mChild2RelativeEmpty = (RelativeLayout) view.findViewById(R.id.child2Relative_empty);


        mSelectAllUpding = (Button) view.findViewById(R.id.select_all_upding);
        mSelectAllUpding.setOnClickListener(this);
        mUpdateButtonUpding = (Button) view.findViewById(R.id.update_button_upding);
        mUpdateButtonUpding.setOnClickListener(this);
        mDeletButtonUpding = (Button) view.findViewById(R.id.delet_button_upding);
        mDeletButtonUpding.setOnClickListener(this);
        showFile();


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
        checkNum = 0;
        checktrueNums = 0;
        isSelectdAll = true;//标记删除后不能在全选
        if (DaoUtils.FileUserDaoQuerywhere("2") != null && DaoUtils.FileUserDaoQuerywhere("2").size() > 0) {
            for (int i1 = 0; i1 < DaoUtils.FileUserDaoQuerywhere("2").size(); i1++) {
                mListname.add(DaoUtils.FileUserDaoQuerywhere("2").get(i1).getMFileNamedao());
                mListpath.add(DaoUtils.FileUserDaoQuerywhere("2").get(i1).getMFilePathdao());
                mChild2RelativeEmpty.setVisibility(View.GONE);
                mChild2RelativeList.setVisibility(View.VISIBLE);
                mAdapter = new Child2Adapter(mContext, mListname, mListpath);
                mChild2Listview.setAdapter(mAdapter);
                Log.i(TAG, "查询到的数据是 ：" + DaoUtils.FileUserDaoQuerywhere("2").get(i1).getMFileNamedao() + "");
            }
        } else {
            Log.i(TAG, "没有查询到数据");
            mChild2RelativeEmpty.setVisibility(View.VISIBLE);
            mChild2RelativeList.setVisibility(View.GONE);
        }
        setBtnSelectAllYes();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all_upding:


                if (mListname.size() > 0) {
                    if (isSelectdAll) {
                        selectAllfile(true);
                        isSelectdAll = false;
                        setBtnSelectAllNo();
                        checktrueNums++;
                    } else {
                        selectAllfile(false);
                        isSelectdAll = true;
                        setBtnSelectAllYes();
                        checktrueNums--;
                    }
                } else {
                    Toast.makeText(mContext, "当前文件下没有文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_button_upding:
                revocationFile(checkNum);
                break;
            case R.id.delet_button_upding:
                delfile(checkNum);
                break;
        }
    }


    private void showDialog() {
        new CommomDialog2(mContext, R.style.dialog, "请选择目标文件", new CommomDialog2.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        }).show();
    }

    private void showDialogDelFile() {
        new CommomDialog2(mContext, R.style.dialog, "确认删除文件", new CommomDialog2.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    for (int i = 0; i < Child2Adapter.getIsSelectedChild2().size(); i++) {
                        if (Child2Adapter.getIsSelectedChild2().get(i)) {
                            Log.i(TAG, "删除的文件是 ：" + mListpath.get(i));
                            FileUtils.delete(mListpath.get(i));
                            DaoUtils.FilUserDaoDel(mListname.get(i));

                        }
                    }
                    mHandler.sendEmptyMessage(4579);
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void showDialogRmeFile() {
        new CommomDialog2(mContext, R.style.dialog, "确认撤销文件", new CommomDialog2.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    for (int i = 0; i < Child2Adapter.getIsSelectedChild2().size(); i++) {
                        if (Child2Adapter.getIsSelectedChild2().get(i)) {
                            Log.i(TAG, "撤销的文件是 ：" + mListpath.get(i));
//                            FileUtils.delete(mListpath.get(i));
//                            DaoUtils.FilUserDaoDel(mListname.get(i));
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mListname.get(i)));
                            fileUser.setMFileTypedao("1");
                            fileUser.setMFileNamedao(mListname.get(i));
                            fileUser.setMFilePathdao(mListpath.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                        }
                    }
                    mHandler.sendEmptyMessage(4579);
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void revocationFile(int num) {
        if (num != 0) {

            showDialogRmeFile();
        } else {
            showDialog();
        }
    }

    private void delfile(int num) {
        if (num != 0) {

            showDialogDelFile();
        } else {
            showDialog();
        }
    }

    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllYes() {
        mSelectAllUpding.setText(R.string.select_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAllUpding.getResources().getDrawable(
                R.drawable.select_all);

        mSelectAllUpding.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }

    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllNo() {
        mSelectAllUpding.setText(R.string.desselect_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAllUpding.getResources().getDrawable(
                R.drawable.desselect_all);

        mSelectAllUpding.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            boolean checkIS = true;
            CheckBox cb = view.findViewById(R.id.dir_list_Check);
            chcnlSels(i);
            if (checkNum == mListname.size()) {
                setBtnSelectAllNo();
                isSelectdAll = false;
            } else {
                setBtnSelectAllYes();
                isSelectdAll = true;
            }
            Toast.makeText(mContext, "==" + checkNum, Toast.LENGTH_SHORT).show();
        }
    };


    private static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    private void dataChanged() {

        mAdapter.notifyDataSetChanged();


    }

    private void chcnlSels(int a) {

        if (Child2Adapter.getIsSelectedChild2().get(a)) {
            Child2Adapter.getIsSelectedChild2().put(a, false);
            checkNum--;
            checktrueNums--;
            Toast.makeText(mContext, "取消" + a, Toast.LENGTH_SHORT).show();
        } else {
            Child2Adapter.getIsSelectedChild2().put(a, true);
            checkNum++;
            checktrueNums++;
            Toast.makeText(mContext, "选中" + a, Toast.LENGTH_SHORT).show();
        }
        dataChanged();


    }


    private void selectAllfile(boolean is) {
        // 遍历list的长度，将MyAdapter中的map值全部设为true
        for (int i = 0; i < mListname.size(); i++) {
            Child2Adapter.getIsSelectedChild2().put(i, is);
        }
        if (is) {
            // 数量设为list的长度
            checkNum = mListname.size();
        } else {
            checkNum = 0;
        }
        // 刷新listview和TextView的显示
        dataChanged();
    }
}

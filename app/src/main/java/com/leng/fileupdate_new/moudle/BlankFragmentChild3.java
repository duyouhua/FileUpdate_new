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

import com.leng.fileupdate_new.Adapter.Child3Adapter;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.CabackInfoNums;
import com.leng.fileupdate_new.contrl.ThreadPoolProxy;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;
import com.leng.other.CommomDialog2;

import java.util.ArrayList;

import static com.leng.fileupdate_new.greendao.gen.DaoUtils.FileUserDaoQueryPrgressAchieve;

public class BlankFragmentChild3 extends Fragment implements View.OnClickListener {
    ThreadPoolProxy threadPoolProxy = new ThreadPoolProxy(2, 4, 6000);
    private View view;
    private static final String TAG = "BlankFragmentChild3";
    private ListView mChild3Listview;
    private RelativeLayout mChild3RelativeList;
    private RelativeLayout mChild3RelativeEmpty;
    /**
     * 全选
     */
    private Button mSelectAllUpding;

    /**
     * 删除
     */
    private Button mDeletButtonUpding;
    private ArrayList<String> mListname = new ArrayList<>();
    private ArrayList<String> mListpath = new ArrayList<>();
    int checkNum = 0;
    private Context mContext;
    private int checktrueNums = 0;
    private boolean isSelectdAll = true;
    private Child3Adapter mAdapter;
    private CabackInfoNums cabackInfoNums;
    private MainActivity ma;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 4579:
                    showFile();
                    break;
                case 4571:
//                    showFile2();
                    Toast.makeText(mContext, "GUOLAI MA ", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ma = (MainActivity) activity;
        ma.setmHandlerActivex(mHandler);
        cabackInfoNums = ma;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getContext();
        view = inflater.inflate(R.layout.fragment_blank_fragment_child3, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        mChild3Listview = (ListView) view.findViewById(R.id.child3_listview);
        mChild3Listview.setOnItemClickListener(clickListener);
        mChild3Listview.setOnItemLongClickListener(clickListenerlong);
        mChild3RelativeList = (RelativeLayout) view.findViewById(R.id.child3Relative_list);
        mChild3RelativeEmpty = (RelativeLayout) view.findViewById(R.id.child3Relative_empty);
        mSelectAllUpding = (Button) view.findViewById(R.id.select_all_upding);
        mSelectAllUpding.setOnClickListener(this);

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
        if (FileUserDaoQueryPrgressAchieve() != null && FileUserDaoQueryPrgressAchieve().size() > 0) {
            for (int i1 = 0; i1 < DaoUtils.FileUserDaoQueryPrgressAchieve().size(); i1++) {
                String name = DaoUtils.FileUserDaoQueryPrgressAchieve().get(i1).getMFileNamedao();
                String path = DaoUtils.FileUserDaoQueryPrgressAchieve().get(i1).getMFilePathdao();
                mListname.add(name);
                mListpath.add(path);
                mChild3RelativeEmpty.setVisibility(View.GONE);
                mChild3RelativeList.setVisibility(View.VISIBLE);
                mAdapter = new Child3Adapter(mContext, mListname, mListpath);

                mChild3Listview.setAdapter(mAdapter);
                //保存正在上传的数量
                SharedPreferencesUtils.setParam(mContext, Constanxs.FOTERNUMSTHREE, mListname.size() + "");
                Log.i(TAG, "查询到的数据是 ：" + FileUserDaoQueryPrgressAchieve().size() + name + path);


            }
        } else {
            Log.i(TAG, "没有查询到数据");
            mChild3RelativeEmpty.setVisibility(View.VISIBLE);
            mChild3RelativeList.setVisibility(View.GONE);
        }
        setBtnSelectAllYes();
//        cabackInfoNums.setInfoNums();
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
                break;
            case R.id.delet_button_upding:
                delfile(checkNum);
                break;
        }
    }

    /**
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

    AdapterView.OnItemLongClickListener clickListenerlong = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
           FileUtils.openFiles(mContext,mListpath.get(position));
            return false;
        }
    };

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            boolean checkIS = true;
            CheckBox cb = (CheckBox) view.findViewById(R.id.dir_list_Check);


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

    private void dataChanged() {

        mAdapter.notifyDataSetChanged();


    }

    private void chcnlSels(int a) {

        if (Child3Adapter.getIsSelectedchilld3().get(a)) {
            Child3Adapter.getIsSelectedchilld3().put(a, false);
            checkNum--;
            checktrueNums--;
            Toast.makeText(mContext, "取消" + a, Toast.LENGTH_SHORT).show();
        } else {
            Child3Adapter.getIsSelectedchilld3().put(a, true);
            checkNum++;
            checktrueNums++;
            Toast.makeText(mContext, "选中" + a, Toast.LENGTH_SHORT).show();
        }
        dataChanged();


    }


    private void selectAllfile(boolean is) {
        // 遍历list的长度，将MyAdapter中的map值全部设为true
        for (int i = 0; i < mListname.size(); i++) {
            Child3Adapter.getIsSelectedchilld3().put(i, is);
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

    private void showDialogDelFile() {
        new CommomDialog2(mContext, R.style.dialog, "确认删除文件", new CommomDialog2.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    for (int i = 0; i < Child3Adapter.getIsSelectedchilld3().size(); i++) {
                        if (Child3Adapter.getIsSelectedchilld3().get(i)) {
                            Log.i(TAG, "删除的文件是 ：" + mListpath.get(i));
                            FileUtils.delete(mListpath.get(i));
                            DaoUtils.FilUser2DaoDel(mListname.get(i));

                        }
                    }
                    mHandler.sendEmptyMessage(4579);
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void delfile(int num) {
        if (num != 0) {

            showDialogDelFile();
        } else {
            showDialog();
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

}

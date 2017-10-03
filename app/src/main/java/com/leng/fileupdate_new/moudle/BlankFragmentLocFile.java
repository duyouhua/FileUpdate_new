package com.leng.fileupdate_new.moudle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leng.fileupdate_new.Adapter.LocFileAdapter;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.FileManger;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;
import com.leng.other.CommomDialog2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class BlankFragmentLocFile extends Fragment implements View.OnClickListener {

    private LocFileAdapter myAdapterFile;
    private View view;
    private Context mContext;
    private boolean isSelectdAll = true;
    /**
     * asdas
     */
    private ListView mListviewLocfile;
    private static final String ROOT_PATH = "/mnt/";
    //存储文件名称
    private ArrayList<String> mFileName = null;
    //存储文件路径
    private ArrayList<String> mFilePath = null;
    private MainActivity ma;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 123123:
                    Toast.makeText(mContext, "返回根目录", Toast.LENGTH_SHORT).show();
                    showFileDir2(ROOT_PATH);
                    break;
                case 1231234:
                    String paht = (String) SharedPreferencesUtils.getParam(mContext, "tabkeypath", "null");
                    if (!paht.equals("null")) {
                        for (int i = 0; i < LocFileAdapter.getIsSelectedLocFile().size(); i++) {
                            if (LocFileAdapter.getIsSelectedLocFile().get(i)) {
                                listSelect.clear();
                                listSelect.add(i);
                                for (int i1 = 0; i1 < listSelect.size(); i1++) {
                                    Log.i(TAG, listSelect.get(i1) + "选中的文件名是" + mFilePath.get(i));

                                    FileUtils.moveFile(mFilePath.get(i), paht);}

                            }
                        }
                        mHandler.sendEmptyMessage(4444);
                        Toast.makeText(mContext, "文件剪切成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "获取剪切路径失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4444:
                    String reslut = (String) SharedPreferencesUtils.getParam(mContext, "jiluFilePath", "null");
                    if (reslut.equals("null"))
                        showFileDir2(ROOT_PATH);
                    else
                        showFileDir2(reslut);
                    break;
                default:
                    break;
            }


        }
    };
    /**
     * 全选
     */
    private Button mSelectAllLoc;
    /**
     * 手动上传
     */
    private Button mUpdateButtonLoc;
    /**
     * 移动到
     */
    private Button mCutButtonLoc;
    /**
     * 删除
     */
    private Button mDeletButtonLoc;
    /**
     * 判断当前选中的条目数据  用来处理删除dialog
     */
    private int checktrueNums;
    private int checktrueNumsmove;
    int checkNum = 0;
    /**
     * 记录选中的那一条
     */

    private ArrayList<Integer> listSelect = new ArrayList<>();

    private String TAG = "BlankFragmentLocFile";

    private LocFileAdapter mAdapter;
    private RelativeLayout mLl;
    /**
     * 当前文件夹没有文件
     */
    private TextView mEmptyView;

    /**
     * 解决在文件夹中包含音视频文件
     */
    private ArrayList<Integer> mListBaoHanYSP = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ma = (MainActivity) activity;
        ma.setmHandlerLocFileFragment(mHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank_fragment_loc_file, container, false);
        mContext = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {

        mListviewLocfile = (ListView) view.findViewById(R.id.listview_locfile);
        mListviewLocfile.setOnItemClickListener(itemchick);

        mSelectAllLoc = (Button) view.findViewById(R.id.select_all_loc);
        mSelectAllLoc.setOnClickListener(this);
        mUpdateButtonLoc = (Button) view.findViewById(R.id.update_button_loc);
        mUpdateButtonLoc.setOnClickListener(this);
        mCutButtonLoc = (Button) view.findViewById(R.id.cut_button_loc);
        mCutButtonLoc.setOnClickListener(this);
        mDeletButtonLoc = (Button) view.findViewById(R.id.delet_button_loc);
        mDeletButtonLoc.setOnClickListener(this);
        mLl = (RelativeLayout) view.findViewById(R.id.ll);
        mEmptyView = (TextView) view.findViewById(R.id.emptyView);
        showFileDir2(ROOT_PATH);
    }


    AdapterView.OnItemClickListener itemchick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String path = mFilePath.get(i);
            File file = new File(path);
            // 文件存在并可读
            if (file.exists() && file.canRead()) {
                if (file.isDirectory()) {
                    //显示子目录及文件
                    showFileDir2(path);
                    SharedPreferencesUtils.setParam(mContext, "jiluFilePath", path);
                    Log.i(TAG, path);
                } else {
                    chcnlSels(i);
                    if (checkNum == mListBaoHanYSP.size()) {
                        setBtnSelectAllNo();
                        isSelectdAll = false;
                    } else {
                        setBtnSelectAllYes();
                        isSelectdAll = true;
                    }
                    Log.i(TAG, mListBaoHanYSP.size() + "---------");
                }
            }
            //没有权限

        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all_loc:
                if (mListBaoHanYSP.size() > 0) {
                    if (isSelectdAll) {
                        selectAllfile(true);
                        isSelectdAll = false;
                        setBtnSelectAllNo();
                        checktrueNums = 1;
                        checktrueNumsmove = 1;
                    } else {

                        selectAllfile(false);
                        isSelectdAll = true;
                        setBtnSelectAllYes();
                        checktrueNums = 0;
                        checktrueNumsmove = 0;
                    }
                } else {
                    Toast.makeText(mContext, "当前文件下没有文件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_button_loc:
                break;
            case R.id.cut_button_loc:
//                for (int i = 0; i < LocFileAdapter.getIsSelectedLocFile().size(); i++) {
//                    if (LocFileAdapter.getIsSelectedLocFile().get(i)) {
//                        checktrueNumsmove = 1;
//                        Log.i(TAG, "checktrueNums");
//                    } else {
//                        checktrueNumsmove = 0;
//                    }
//                }
                moveFile(checktrueNumsmove);
                break;
            case R.id.delet_button_loc:

//                for (int i = 0; i < LocFileAdapter.getIsSelectedLocFile().size(); i++) {
//                    if (LocFileAdapter.getIsSelectedLocFile().get(i)) {
//                        checktrueNums = 1;
//
//                    }
//                }
                delfile(checktrueNums);

                break;
        }
    }


    /**
     * 扫描显示文件列表
     *
     * @param path
     */
    private void showFileDir2(String path) {
//        FileUtils.delHindenFile(path);
        setBtnSelectAllYes();
        checkNum = 0;
        isSelectdAll = true;//标记删除后不能在全选
        if (mFilePath != null && mFileName != null) {
            mFileName.clear();
            mFilePath.clear();
        }
        mFileName = new ArrayList<String>();
        mFilePath = new ArrayList<String>();
        File file = new File(path);

        File[] files = file.listFiles();
        //如果当前目录不是根目录
//        if (!ROOT_PATH.equals(path)) {
//            mFileName.add("@1");
//            mFilePath.add(ROOT_PATH);
//            mFileName.add("@2");
//            mFilePath.add(file.getParent());
//        }
        //添加所有文件
            mListBaoHanYSP.clear();
        if (files != null && files.length > 0) {
            mLl.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            for (File f : files) {
                if (f.isDirectory()) {
                    mFileName.add(f.getName());
                    mFilePath.add(f.getPath());
                } else {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getPath());
                        //添加音视频文件
//
                        mListBaoHanYSP.add(1);
                    }
                }
            }
            if (mFileName.size() > 0) {
                mLl.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
            } else {
                mLl.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                setBtnSelectAllYes();
            }
            Collections.sort(mFileName);
            Collections.sort(mFilePath);
            Toast.makeText(mContext, "MEIFANGING   " + files.length + " mListBaoHanYSP.size()" + mListBaoHanYSP.size(), Toast.LENGTH_SHORT).show();
            mAdapter = new LocFileAdapter(mContext, mFileName, mFilePath);
            mListviewLocfile.setAdapter(mAdapter);
        } else {
            mLl.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            mFileName.clear();
            mFilePath.clear();
            mAdapter = new LocFileAdapter(mContext, mFileName, mFilePath);
            mListviewLocfile.setAdapter(mAdapter);
            setBtnSelectAllYes();
        }


    }

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

        if (LocFileAdapter.getIsSelectedLocFile().get(a)) {
            LocFileAdapter.getIsSelectedLocFile().put(a, false);
            checktrueNums = 0;
            checktrueNumsmove = 0;
            checkNum--;
            Toast.makeText(mContext, "取消" + a, Toast.LENGTH_SHORT).show();
        } else {
            LocFileAdapter.getIsSelectedLocFile().put(a, true);
            checkNum++;
            checktrueNums = 1;
            checktrueNumsmove = 1;
            Toast.makeText(mContext, "选中" + a, Toast.LENGTH_SHORT).show();
        }
        dataChanged();


    }


    private void selectAllfile(boolean is) {
        // 遍历list的长度，将MyAdapter中的map值全部设为true
        for (int i = 0; i < mFileName.size(); i++) {
            LocFileAdapter.getIsSelectedLocFile().put(i, is);
        }
        if (is) {
            // 数量设为list的长度
            checkNum = mListBaoHanYSP.size();
        } else {
            checkNum = 0;
        }
        // 刷新listview和TextView的显示
        dataChanged();
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
                    for (int i = 0; i < LocFileAdapter.getIsSelectedLocFile().size(); i++) {
                        if (LocFileAdapter.getIsSelectedLocFile().get(i)) {
                            listSelect.clear();
                            listSelect.add(i);
                            for (int i1 = 0; i1 < listSelect.size(); i1++) {
                                Log.i(TAG, listSelect.get(i1) + "选中的文件名是" + mFileName.get(i));
                                FileUtils.delete(mFilePath.get(i));
                            }
                        }
                    }
                    mHandler.sendEmptyMessage(4444);
                }
                dialog.dismiss();
            }
        }).show();
    }

    private void showDialogMoveFile() {
        new CommomDialog2(mContext, R.style.dialog, "确认移动文件", new CommomDialog2.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
//                    for (int i = 0; i < LocFileAdapter.getIsSelectedLocFile().size(); i++) {
//                        if (LocFileAdapter.getIsSelectedLocFile().get(i)) {
//                            listSelect.clear();
//                            listSelect.add(i);
//                            for (int i1 = 0; i1 < listSelect.size(); i1++) {
//                                Log.i(TAG, listSelect.get(i1) + "选中的文件名是" + mFilePath.get(i));
//                                FileUtils.moveFile(mFilePath.get(i), "/sdcard/DCIM");
//                            }
//                        }
//                    }
//                    mHandler.sendEmptyMessage(4444);


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

    private void moveFile(int num) {
        if (num != 0) {

            Intent intent = new Intent(mContext, MoveFileActivity.class);
            startActivityForResult(intent, 99);
        } else {
            showDialog();
        }
    }


    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllYes() {

        mSelectAllLoc.setText(R.string.select_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAllLoc.getResources().getDrawable(
                R.drawable.select_all);

        mSelectAllLoc.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }

    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllNo() {

        mSelectAllLoc.setText(R.string.desselect_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAllLoc.getResources().getDrawable(
                R.drawable.desselect_all);

        mSelectAllLoc.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }
}

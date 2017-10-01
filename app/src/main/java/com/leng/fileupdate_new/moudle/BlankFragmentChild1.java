package com.leng.fileupdate_new.moudle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
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
import com.leng.fileupdate_new.Adapter.Child1Adapter;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.CabackPv;
import com.leng.fileupdate_new.contrl.CallbackChild2;
import com.leng.fileupdate_new.contrl.ContinueFTP2;
import com.leng.fileupdate_new.contrl.FileManger;
import com.leng.fileupdate_new.contrl.ThreadPoolProxy;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.upload.TestBean;
import com.leng.fileupdate_new.upload.UploadFileManager;
import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;
import com.leng.other.CommomDialog2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.leng.fileupdate_new.utils.Constanxs.INFO4;
import static com.leng.fileupdate_new.utils.Constanxs.isftpconnet;

public class BlankFragmentChild1 extends ListFragment implements View.OnClickListener {

    private View view;
    private static final String TAG = "BlankFragmentChild1";
    private Context mContext;

    private MainActivity acc;
    int checkNum = 0;

    private boolean isSelectdAll = true;
    private ThreadPoolProxy ty;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == INFO4) {
                String as = msg.obj.toString();
                Toast.makeText(mContext, "CHID  " + msg.obj.toString(), Toast.LENGTH_SHORT).show();
                switch (as) {
                    case "1":
                        break;
                    default:
                        Log.i(TAG, "BlankFragmentChild1  mahandler   deauflt  handler");
                        break;
                }
                String a = (String) SharedPreferencesUtils.getParam(mContext, "defultpage", "null");
                Log.i(TAG, "==================" + a);
                showDeaufltPageFile(a);
            } else if (msg.arg1 == 7536) { //撤销更新文件列表
                mHandler.sendEmptyMessage(4444);
            }
            switch (msg.what) {
                case 4444:
                    String a = (String) SharedPreferencesUtils.getParam(mContext, "defultpage", "null");
                    Log.i(TAG, " 本利 大宋 ==================" + a);
                    showDeaufltPageFile(a);
                    break;
                case 4578:
                    Toast.makeText(mContext, "连接成功了吗 " + isftpconnet, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };
    private ListView mList;
    /**
     * 全选
     */
    private Button mSelectAll;
    /**
     * 手动上传
     */
    private Button mUpdateButton;
    /**
     * 移动到
     */
    private Button mCutButton;
    /**
     * 删除
     */
    private Button mDeletButton;
    private ListView mListview1;
    //存储文件名称
    private ArrayList<String> mFileName = null;
    //存储文件路径
    private ArrayList<String> mFilePath = null;
    private ArrayList<String> mFileName2 = null;
    //存储文件路径
    private ArrayList<String> mFilePath2 = null;
    private RelativeLayout rr;
    private RelativeLayout mRLISTVIEW;
    private Child1Adapter mAdapter;

    public static HashMap<String, String> mapShangchuan = new HashMap<>();
    public static HashMap<Integer, String> mapShangchuanNUMname = new HashMap<>();
    public static HashMap<Integer, String> mapShangchuanNUMpath = new HashMap<>();

    /**
     * 判断当前选中的条目数据  用来处理删除dialog
     */
    private int checktrueNums = 0;
    private ContinueFTP2 cf;
    private CallbackChild2 cc2;
    /**
     * 记录选中的那一条
     */

    private ArrayList<Integer> listSelect = new ArrayList<>();
    private UploadFileManager uploadFileManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        acc = (MainActivity) activity;
        acc.setHandler4(mHandler);
        cc2 = acc;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_blank_fragment_child1, container, false);
        initView(view);
        cf = new ContinueFTP2(mContext);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Constanxs.isftpconnet = cf.connect(Constanxs.FTPIP, Constanxs.FTPPORT, Constanxs.FTPUSER, Constanxs.FTPPASS);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return view;
    }

    private void initView(View view) {

        uploadFileManager = new UploadFileManager(mContext);
        rr = view.findViewById(R.id.shouKONG);
//        rr = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.kongbaibuju, null);
        mList = view.findViewById(android.R.id.list);
        mList.setOnItemLongClickListener(longchick);
        mSelectAll = (Button) view.findViewById(R.id.select_all);
        mSelectAll.setOnClickListener(this);
        mUpdateButton = (Button) view.findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(this);
//        mCutButton = (Button) view.findViewById(R.id.cut_button);
//        mCutButton.setOnClickListener(this);
        mDeletButton = (Button) view.findViewById(R.id.delet_button);
        mDeletButton.setOnClickListener(this);
        mRLISTVIEW = (RelativeLayout) view.findViewById(R.id.rLISTVIEW);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i(TAG, "onResume===子view显示");

        } else {
            Log.i(TAG, "onPause====子view隐藏");
        }
    }

    private void showDeaufltPageFile(String a) {
        switch (a) {
            case "1":
                showFile("1");
                break;
            case "2":
                showFile("2");
                break;
            case "3":
                showFile("3");
                break;
            case "4":
                showFile("4");
                break;
            case "5":
                showFile("5");
                break;
            default:
                break;
        }
    }


    private void showFile(String a) {
        String path = (String) SharedPreferencesUtils.getParam(mContext, "mSettingPath" + a, "null");
        if (!path.equals("null")) {
            showFileDir(path);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume ===chid1____11111");
//        mHandler.sendEmptyMessageDelayed(111, 100);


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void showFileDir(String path) {
        checkNum = 0;
        checktrueNums = 0;

        isSelectdAll = true;//标记删除后不能在全选
        if (mFilePath != null && mFileName != null) {
            mFileName.clear();
            mFilePath.clear();
        }
        if (mFilePath2 != null && mFileName2 != null) {
            mFileName2.clear();
            mFilePath2.clear();
        }
        mFileName = new ArrayList<String>();
        mFilePath = new ArrayList<String>();
        mFileName2 = new ArrayList<String>();
        mFilePath2 = new ArrayList<String>();
        File file = new File(path);

        File[] files = file.listFiles();
        Log.i(TAG, files.length + "@@@@@@@@");
        if (files != null && files.length > 0) {
            mRLISTVIEW.setVisibility(View.VISIBLE);
            rr.setVisibility(View.GONE);
            //添加所有文件
//            for (File f : files) {
//                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
//                    if (mapShangchuan.size() > 0) {
//                        if (f.getAbsolutePath().equals(mapShangchuan.get(f.getAbsolutePath()))) {
//
//                        } else {
//                            mFileName.add(f.getName());
//                            mFilePath.add(f.getAbsolutePath());
//                        }
//                    } else {
//                        mFileName.add(f.getName());
//                        mFilePath.add(f.getAbsolutePath());
//                    }
//                }
//            }

            for (File f : files) {
                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                    mFileName.add(f.getName());
                    mFilePath.add(f.getAbsolutePath());

                    if (mFilePath.size() >= DaoUtils.FileUserDaoQuery().size()) {
                        //更新数据库   将新文件添加进去
                        FileUser fileUser = new FileUser();
                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                        fileUser.setMFileTypedao("1");
                        fileUser.setMFileNamedao(f.getName());
                        fileUser.setMFilePathdao(f.getAbsolutePath());
                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                        Log.i(TAG, "插入数据库==child1");
                    } else {
                        Log.i(TAG, "没有插入数据库==child1");
                    }


                }
            }

            if (DaoUtils.FileUserDaoQuerywhere("1") != null && DaoUtils.FileUserDaoQuerywhere("1").size() > 0) {

                for (int i = 0; i < DaoUtils.FileUserDaoQuerywhere("1").size(); i++) {
                    mFileName2.add(DaoUtils.FileUserDaoQuerywhere("1").get(i).getMFileNamedao());
                    mFilePath2.add(DaoUtils.FileUserDaoQuerywhere("1").get(i).getMFilePathdao());
                }
                Toast.makeText(mContext, "MEIFANGING   " + files.length, Toast.LENGTH_SHORT).show();
                mAdapter = new Child1Adapter(mContext, mFileName2, mFilePath2);
                mList.setAdapter(mAdapter);
            } else {
                Log.i(TAG, "没有查询到类型为1的数据==child1");
                showEmpty();
            }


        } else {
            showEmpty();
        }
        setBtnSelectAllYes();
    }

    private void showEmpty() {
        mRLISTVIEW.setVisibility(View.GONE);
        rr.setVisibility(View.VISIBLE);
        mFileName.clear();
        mFilePath.clear();
        mAdapter = new Child1Adapter(mContext, mFileName, mFilePath);
        mList.setAdapter(mAdapter);
        setBtnSelectAllYes();
        Toast.makeText(mContext, "显示没有文件的布局", Toast.LENGTH_SHORT).show();
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

        if (Child1Adapter.getIsSelected().get(a)) {
            Child1Adapter.getIsSelected().put(a, false);
            checkNum--;
            checktrueNums--;
            Toast.makeText(mContext, "取消" + a, Toast.LENGTH_SHORT).show();
        } else {
            Child1Adapter.getIsSelected().put(a, true);
            checkNum++;
            checktrueNums++;
            Toast.makeText(mContext, "选中" + a, Toast.LENGTH_SHORT).show();
        }
        dataChanged();


    }


    private void selectAllfile(boolean is) {
        // 遍历list的长度，将MyAdapter中的map值全部设为true
        for (int i = 0; i < mFileName2.size(); i++) {
            Child1Adapter.getIsSelected().put(i, is);
        }
        if (is) {
            // 数量设为list的长度
            checkNum = mFileName2.size();
        } else {
            checkNum = 0;
        }
        // 刷新listview和TextView的显示
        dataChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                if (mFileName.size() > 0) {
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
//                Toast.makeText(mContext, "===" + checkNum, Toast.LENGTH_SHORT).show();

                break;
            case R.id.update_button:
                if (!isftpconnet) {
                    Toast.makeText(mContext, "未连接", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkNum == 0) {
                    showDialog();
                    return;
                }
                for (int i = 0; i < Child1Adapter.getIsSelected().size(); i++) {
                    if (Child1Adapter.getIsSelected().get(i)) {

                        // 文件上传格式
                        String famt = Child1Adapter.getMapFamt().get(mFilePath.get(i));
                        String type = "";
                        if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = "1";
                            } else if (famt.equals("/Audios/")) {
                                type = "2";
                            } else if (famt.equals("/Videos/")) {
                                type = "3";
                            }
                            if (type != null) {
                                String remote = "2bgz12yp0_0" + mFileName.get(i);
//                                startUpdataFile(mFilePath.get(i), remote, type);
                                Log.i(TAG, "选中的文件名是" + "i==" + i + mFilePath.get(i) + "文件格式是：" + type + "服务端路径是：" + remote);

                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(mFileName.get(i)));
                                fileUser.setMFileTypedao("2");
                                fileUser.setMFileNamedao(mFileName.get(i));
                                fileUser.setMFilePathdao(mFilePath.get(i));
                                APP.getDaoInstant().getFileUserDao().update(fileUser);


                                TestBean testBean = new TestBean();
                                testBean.setLocfilepath(mFilePath.get(i));
                                testBean.setRemotefilepath(remote);
                                testBean.setCrateFileType(famt);
                                uploadFileManager.startUpLoad(testBean);
                                Log.i(TAG, "开始上传");
                                cc2.setMsg(9876);
                                mHandler.sendEmptyMessage(4444);
                            } else {
                                Log.i(TAG, "类型与格式错误");
                            }
                        } else {
                            Log.i(TAG, "视频格式异常");
                        }

                    } else {

                        Log.i(TAG, "没有选中的文件");
                    }

                }

                break;

            case R.id.delet_button:

                delfile(checkNum);
                break;
        }
    }


    private void startUpdataFile(final String path, final String remote, final String type) {
//        if (ty == null) {
//            ty = new ThreadPoolProxy(4, 4, 10000);
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "提交了一个任务线程 " + "本地名称 ：" + path + "服务端名称 ：" + remote + " 类型是：" + type);
                    cf.upload(path, remote, type);
                    Log.i(TAG, "提交了一个任务线程2222222 " + "本地名称 ：" + path + "服务端名称 ：" + remote + " 类型是：" + type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//        ty.commitTask(runnable);
        Toast.makeText(mContext, "开始上传", Toast.LENGTH_SHORT).show();
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
                    for (int i = 0; i < Child1Adapter.getIsSelected().size(); i++) {
                        if (Child1Adapter.getIsSelected().get(i)) {
                            Log.i(TAG, "删除的文件是 ：" + mFilePath.get(i));
                            FileUtils.delete(mFilePath.get(i));
                            DaoUtils.FilUserDaoDel(mFileName.get(i));

                        }
                    }
                    mHandler.sendEmptyMessage(4444);
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

    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllYes() {
        mSelectAll.setText(R.string.select_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAll.getResources().getDrawable(
                R.drawable.select_all);

        mSelectAll.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }

    /**
     * v
     * 把全选按钮设置成为初始状态
     */
    public void setBtnSelectAllNo() {
        mSelectAll.setText(R.string.desselect_all); // 点击全选Button然后文字变成
        Drawable top = mSelectAll.getResources().getDrawable(
                R.drawable.desselect_all);

        mSelectAll.setCompoundDrawablesWithIntrinsicBounds(null, top,
                null, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        boolean checkIS = true;
        CheckBox cb = v.findViewById(R.id.dir_list_Check);
        chcnlSels(position);
        if (checkNum == mFileName2.size()) {
            setBtnSelectAllNo();
            isSelectdAll = false;
        } else {
            setBtnSelectAllYes();
            isSelectdAll = true;
        }
        Toast.makeText(mContext, "==" + checkNum, Toast.LENGTH_SHORT).show();
    }


    AdapterView.OnItemLongClickListener longchick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(mContext, "chang  AN" + i, Toast.LENGTH_SHORT).show();
            return false;
        }
    };


//http://blog.csdn.net/jdsjlzx/article/details/7318659

}

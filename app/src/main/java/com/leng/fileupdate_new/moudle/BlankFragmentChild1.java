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
import com.leng.fileupdate_new.Bean.FileUser2;
import com.leng.fileupdate_new.Bean.FileUserRevocation;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.CabackInfoNums;
import com.leng.fileupdate_new.contrl.CallbackChild2;
import com.leng.fileupdate_new.contrl.ContinueFTP2;
import com.leng.fileupdate_new.contrl.FileManger;
import com.leng.fileupdate_new.contrl.ThreadPoolProxy;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.upload.TestBean;
import com.leng.fileupdate_new.upload.UploadFileManager;
import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.NetUtils;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;
import com.leng.other.CommomDialog2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.leng.fileupdate_new.utils.Constanxs.INFO4;
import static com.leng.fileupdate_new.utils.Constanxs.isUplodFirstone;

public class BlankFragmentChild1 extends ListFragment implements View.OnClickListener {

    private View view;
    private static final String TAG = "BlankFragmentChild1";
    private Context mContext;

    private MainActivity acc;
    int checkNum = 0;

    private boolean isSelectdAll = true;
    private ThreadPoolProxy ty = new ThreadPoolProxy(1, 3, 10000);
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == INFO4) {
                String as = msg.obj.toString();
//                Toast.makeText(mContext, "CHID  " + msg.obj.toString(), Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(mContext, "连接成功了吗 " + isftpconnet, Toast.LENGTH_SHORT).show();
                    break;
                case 4579:
                    String path = msg.obj.toString();

                    break;
                case 14561:
//                    uploadFileManager.pause(testBean);
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
    private String regCodex;
    private ListView mListview1;
    //存储文件名称
    private ArrayList<String> mFileName = new ArrayList<String>();
    //存储文件路径
    private ArrayList<String> mFilePath = new ArrayList<String>();
    private ArrayList<String> mFileName2 = new ArrayList<String>();
    //存储文件路径
    private ArrayList<String> mFilePath2 = new ArrayList<String>();
    private ArrayList<String> mUpdingbioashi = new ArrayList<String>();

    private RelativeLayout rr;
    private RelativeLayout mRLISTVIEW;
    private Child1Adapter mAdapter;

    public static HashMap<String, String> mapShangchuan = new HashMap<>();
    public static HashMap<Integer, String> mapShangchuanNUMname = new HashMap<>();
    public static HashMap<Integer, String> mapShangchuanNUMpath = new HashMap<>();

    private boolean isconnet;
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
    public static UploadFileManager uploadFileManager;
    private CabackInfoNums cabackInfoNums;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        acc = (MainActivity) activity;
        acc.setHandler4(mHandler);
        cc2 = acc;
        cabackInfoNums = acc;
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
//        xxxxx();
        return view;
    }

    private void xxxxx() {
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
    }

    private void initView(View view) {
        regCodex = (String) SharedPreferencesUtils.getParam(mContext, "regcode", "null");
        uploadFileManager = new UploadFileManager(mContext);
        rr = (RelativeLayout) view.findViewById(R.id.shouKONG);
//        rr = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.kongbaibuju, null);
        mList = (ListView) view.findViewById(android.R.id.list);
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
//        SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "1");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i(TAG, "onResume===子view显示");
//            isconnet=NetUtils.properDetection(mContext);
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
            showFileDir2(path, a);
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

    private void listClear() {
        if (mFileName.size() > 0) {
            mFileName.clear();

        }
        if (mFilePath.size() > 0) {
            mFilePath.clear();

        }
        if (mFilePath2.size() > 0) {
            mFilePath2.clear();

        }
        if (mFileName2.size() > 0) {
            mFileName2.clear();

        }
    }

    /**
     * 去数据库作比对
     */
    private boolean isDaoFileExits(File f) {
        if (mFileName.size() > 0) {
            int sizee = DaoUtils.FileUserDaoQuery().size();
            for (int i = 0; i < sizee; i++) {
                if (DaoUtils.FileUserDaoQuery().get(i).getId().equals(FileUtils.longPressLong(f.getName()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 按时间排序
     */

    public static void orderByDate(File[] fs) {
        Arrays.sort(fs, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        for (int i = fs.length - 1; i > -1; i--) {
            System.out.println(fs[i].getName());
            System.out.println(new Date(fs[i].lastModified()));
        }
    }

    private void showFileDir2(final String path, final String title) {


        checkNum = 0;
        checktrueNums = 0;
        isSelectdAll = true;//标记删除后不能在全选
        final File file = new File(path);
        final File[] files = file.listFiles();
//        orderByDate(files);

        if (title.equals("1")) {
            //暂且先放着
//            if (DaoUtils.FileUserDaoQuerywhere(title) == null) {
//                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "1");
//            }

            if (SharedPreferencesUtils.getParam(mContext, "lujinggaibianl1", "null").equals("no")) {
                Log.i(TAG, "路径改变删除原来路径下的数据库文件");
                for (int i = 0; i < mFileName2.size(); i++) {
                    DaoUtils.FilUserDaoDel(mFileName2.get(i));
                }
                SharedPreferencesUtils.setParam(mContext, "lujinggaibianl1", "yes");//处理之后复位
            }
            listClear();//清空数据集合
            if (files != null && files.length > 0) {
                mRLISTVIEW.setVisibility(View.VISIBLE);
                rr.setVisibility(View.GONE);
                String isd = (String) SharedPreferencesUtils.getParam(mContext, "iffirstAdd", "null");
                for (File f : files) {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getAbsolutePath());
                        if (isd.equals("null")) {
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                            fileUser.setMFileTypedao(title);
                            //匹配撤销数据库
                            updateRevocation(f.getName(), title);
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(f.getName());
                            fileUser.setMFilePathdao(f.getAbsolutePath());
                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                        } else {
                            if (!isDaoFileExits(f)) {
                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(f.getName()));
                                fileUser.setMFileTypedao(title);
                                //匹配撤销数据库
                                updateRevocation(f.getName(), title);
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(f.getName());
                                fileUser.setMFilePathdao(f.getAbsolutePath());
                                APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            }

                        }
                    }
                }
                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "2");
                if (DaoUtils.FileUserDaoQuerywhere(title) != null) {
                    queryAllFile2(title);
                } else {
                    showEmpty();
                }

            } else {
                showEmpty();
            }
        } else if (title.equals("2")) {
            if (SharedPreferencesUtils.getParam(mContext, "lujinggaibianl2", "null").equals("no")) {
                Log.i(TAG, "路径改变删除原来路径下的数据库文件");
                for (int i = 0; i < mFileName2.size(); i++) {
                    DaoUtils.FilUserDaoDel(mFileName2.get(i));
                }
                SharedPreferencesUtils.setParam(mContext, "lujinggaibianl2", "yes");//处理之后复位
            }
            listClear();//清空数据集合
            if (files != null && files.length > 0) {
                mRLISTVIEW.setVisibility(View.VISIBLE);
                rr.setVisibility(View.GONE);
                String isd = (String) SharedPreferencesUtils.getParam(mContext, "iffirstAdd", "null");
                for (File f : files) {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getAbsolutePath());
                        if (isd.equals("null")) {
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                            fileUser.setMFileTypedao(title);
                            //匹配撤销数据库
                            updateRevocation(f.getName(), title);
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(f.getName());
                            fileUser.setMFilePathdao(f.getAbsolutePath());
                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            Log.i(TAG, "第2次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                        } else {
                            if (!isDaoFileExits(f)) {//大量对比消耗cpu资源
                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(f.getName()));
                                fileUser.setMFileTypedao(title);
                                //匹配撤销数据库
                                updateRevocation(f.getName(), title);
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(f.getName());
                                fileUser.setMFilePathdao(f.getAbsolutePath());
                                APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            }

                        }
                    }
                }
                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "2");
                if (DaoUtils.FileUserDaoQuerywhere(title) != null) {
                    queryAllFile2(title);
                } else {
                    showEmpty();
                }

            } else {
                showEmpty();
            }
        } else if (title.equals("3")) {
            if (SharedPreferencesUtils.getParam(mContext, "lujinggaibianl3", "null").equals("no")) {
                Log.i(TAG, "路径改变删除原来路径下的数据库文件");
                for (int i = 0; i < mFileName2.size(); i++) {
                    DaoUtils.FilUserDaoDel(mFileName2.get(i));
                }
                SharedPreferencesUtils.setParam(mContext, "lujinggaibianl3", "yes");//处理之后复位
            }
            listClear();//清空数据集合
            if (files != null && files.length > 0) {
                mRLISTVIEW.setVisibility(View.VISIBLE);
                rr.setVisibility(View.GONE);
                String isd = (String) SharedPreferencesUtils.getParam(mContext, "iffirstAdd", "null");
                for (File f : files) {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getAbsolutePath());
                        if (isd.equals("null")) {
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                            fileUser.setMFileTypedao(title);
                            //匹配撤销数据库
                            updateRevocation(f.getName(), title);
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(f.getName());
                            fileUser.setMFilePathdao(f.getAbsolutePath());
                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            Log.i(TAG, "第3次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                        } else {
                            if (!isDaoFileExits(f)) {
                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(f.getName()));
                                fileUser.setMFileTypedao(title);
                                //匹配撤销数据库
                                updateRevocation(f.getName(), title);
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(f.getName());
                                fileUser.setMFilePathdao(f.getAbsolutePath());
                                APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            }

                        }
                    }
                }
                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "2");
                if (DaoUtils.FileUserDaoQuerywhere(title) != null) {
                    queryAllFile2(title);
                } else {
                    showEmpty();
                }

            } else {
                showEmpty();
            }

        } else if (title.equals("4")) {
            if (SharedPreferencesUtils.getParam(mContext, "lujinggaibianl4", "null").equals("no")) {
                Log.i(TAG, "路径改变删除原来路径下的数据库文件");
                for (int i = 0; i < mFileName2.size(); i++) {
                    DaoUtils.FilUserDaoDel(mFileName2.get(i));
                }
                SharedPreferencesUtils.setParam(mContext, "lujinggaibianl4", "yes");//处理之后复位
            }
            listClear();//清空数据集合
            if (files != null && files.length > 0) {
                mRLISTVIEW.setVisibility(View.VISIBLE);
                rr.setVisibility(View.GONE);
                String isd = (String) SharedPreferencesUtils.getParam(mContext, "iffirstAdd", "null");
                for (File f : files) {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getAbsolutePath());
                        if (isd.equals("null")) {
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                            fileUser.setMFileTypedao(title);
                            //匹配撤销数据库
                            updateRevocation(f.getName(), title);
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(f.getName());
                            fileUser.setMFilePathdao(f.getAbsolutePath());
                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            Log.i(TAG, "第4次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                        } else {
                            if (!isDaoFileExits(f)) {
                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(f.getName()));
                                fileUser.setMFileTypedao(title);
                                //匹配撤销数据库
                                updateRevocation(f.getName(), title);
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(f.getName());
                                fileUser.setMFilePathdao(f.getAbsolutePath());
                                APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            }

                        }
                    }
                }
                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "2");
                if (DaoUtils.FileUserDaoQuerywhere(title) != null) {
                    queryAllFile2(title);
                } else {
                    showEmpty();
                }

            } else {
                showEmpty();
            }

        } else if (title.equals("5")) {

            if (SharedPreferencesUtils.getParam(mContext, "lujinggaibianl5", "null").equals("no")) {
                Log.i(TAG, "路径改变删除原来路径下的数据库文件");
                for (int i = 0; i < mFileName2.size(); i++) {
                    DaoUtils.FilUserDaoDel(mFileName2.get(i));
                }
                SharedPreferencesUtils.setParam(mContext, "lujinggaibianl5", "yes");//处理之后复位
            }
            listClear();//清空数据集合
            if (files != null && files.length > 0) {
                mRLISTVIEW.setVisibility(View.VISIBLE);
                rr.setVisibility(View.GONE);
                String isd = (String) SharedPreferencesUtils.getParam(mContext, "iffirstAdd", "null");
                for (File f : files) {
                    if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                        mFileName.add(f.getName());
                        mFilePath.add(f.getAbsolutePath());
                        if (isd.equals("null")) {
                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                            fileUser.setMFileTypedao(title);
                            //匹配撤销数据库
                            updateRevocation(f.getName(), title);
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(f.getName());
                            fileUser.setMFilePathdao(f.getAbsolutePath());
                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            Log.i(TAG, "第5次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                        } else {
                            if (!isDaoFileExits(f)) {
                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(f.getName()));
                                fileUser.setMFileTypedao(title);
                                //匹配撤销数据库
                                updateRevocation(f.getName(), title);
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(f.getName());
                                fileUser.setMFilePathdao(f.getAbsolutePath());
                                APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                            }

                        }
                    }
                }
                SharedPreferencesUtils.setParam(mContext, "iffirstAdd", "2");
                if (DaoUtils.FileUserDaoQuerywhere(title) != null) {
                    queryAllFile2(title);
                } else {
                    showEmpty();
                }

            } else {
                showEmpty();
            }

        }

        SharedPreferencesUtils.setParam(mContext, Constanxs.FOTERNUMSONE, DaoUtils.FileQueryWsc() + "");
        setBtnSelectAllYes();
//        cabackInfoNums.setInfoNums();

    }

    /**
     * 更新撤销类型
     */
    private void updateRevocation(String name, String type) {
        FileUserRevocation revocation = new FileUserRevocation();
        revocation.setId(FileUtils.longPressLong(name));
        revocation.setMFileTypedao(type);
        APP.getDaoInstant().getFileUserRevocationDao().insertOrReplace(revocation);
    }

    private void queryAllFile(String type) {
        //查询所有类型为1的数据
        int sizeq = DaoUtils.FileUserDaoQuerywhere(type).size();

        if (DaoUtils.FileUserDaoQuerywhere(type) != null && sizeq > 0) {
            for (int i = 0; i < sizeq; i++) {
                mFileName2.add(DaoUtils.FileUserDaoQuerywhere(type).get(i).getMFileNamedao());
                mFilePath2.add(DaoUtils.FileUserDaoQuerywhere(type).get(i).getMFilePathdao());
            }
//            Collections.sort(mFileName2);
//            Collections.sort(mFilePath2);

            Log.i(TAG, "查询出来类型为 的数据大小是" + sizeq);
            mAdapter = new Child1Adapter(mContext, mFileName2, mFilePath2);
            mList.setAdapter(mAdapter);
        } else {
            Log.i(TAG, "没有查询到类型为1的数据==child1");
            showEmpty();
        }
    }
    private void queryAllFile2(String type) {
        //查询所有类型为1的数据
        List<FileUser> fileUsersz = DaoUtils.FileUserDaoQuerywhere(type)  ;

        if (fileUsersz != null && fileUsersz.size() > 0) {
            for (int i = 0; i < fileUsersz.size(); i++) {
                mFileName2.add(fileUsersz.get(i).getMFileNamedao());
                mFilePath2.add(fileUsersz.get(i).getMFilePathdao());
            }
//            Collections.sort(mFileName2);
//            Collections.sort(mFilePath2);

            Log.i(TAG, "查询出来类型为 的数据大小是" + fileUsersz.size());
            mAdapter = new Child1Adapter(mContext, mFileName2, mFilePath2);
            mList.setAdapter(mAdapter);
        } else {
            Log.i(TAG, "没有查询到类型为1的数据==child1");
            showEmpty();
        }
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
//            Toast.makeText(mContext, "取消" + a, Toast.LENGTH_SHORT).show();
        } else {
            Child1Adapter.getIsSelected().put(a, true);
            checkNum++;
            checktrueNums++;
//            Toast.makeText(mContext, "选中" + a, Toast.LENGTH_SHORT).show();
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
//                if (!isftpconnet) {
//                    Toast.makeText(mContext, "未连接", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                if (checkNum == 0) {
                    showDialog();
                    return;
                }
                if (!NetUtils.properDetection(mContext)) {
                    return;
                }
                isUplodFirstone = true;
                for (int i = 0; i < Child1Adapter.getIsSelected().size(); i++) {
                    if (Child1Adapter.getIsSelected().get(i)) {
                        String pathsa = mFilePath2.get(i);
                        String namesa = mFileName2.get(i);
                        // 文件上传格式
                        String famt = Child1Adapter.getMapFamt().get(pathsa);
                        int type = 0;
                        if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }
                            if (type != 0) {
                                String remote;
                                if (type != 2 && FileUtils.isPictureIsRotated(pathsa)) {
                                    remote = regCodex + "_0_" + namesa;
                                } else {
                                    remote = regCodex + "_1_" + namesa;
                                }

//                                startUpdataFile(mFilePath.get(i), remote, type);
                                Log.i(TAG, "选中的文件名是" + "i==" + i +pathsa + "文件格式是：" + type + "服务端路径是：" + remote);

                                FileUser fileUser = new FileUser();
                                fileUser.setId(FileUtils.longPressLong(namesa));
                                fileUser.setMFileTypedao("6");
                                fileUser.setMFileProgresdao(0);
                                fileUser.setMFileNamedao(namesa);
                                fileUser.setMFilePathdao(pathsa);
                                APP.getDaoInstant().getFileUserDao().update(fileUser);

                                FileUser2 ff = new FileUser2();
                                ff.setId(FileUtils.longPressLong(namesa));
                                ff.setMFileProgresdao(0);
                                ff.setMFileNamedao(namesa);
                                ff.setMFilePathdao(pathsa);
                                APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                                final TestBean testBean = new TestBean();
                                testBean.setLocfilepath(pathsa);
                                testBean.setRemotefilepath(remote);
                                testBean.setLocfileName(namesa);
                                testBean.setCrateFileType(famt);
                                testBean.setCrateFileTypenums(type);
                                testBean.setUpLoadStatus("1");
                                uploadFileManager.startUpLoad(testBean);


//                                xxxxx();
//                                startUpdataFile(mFilePath2.get(i),remote,"1");

                                Log.i(TAG, "开始上传" + "   名字是：" + namesa + "id :" + FileUtils.longPressLong(namesa));

                            } else {
                                Log.i(TAG, "类型与格式错误");
                            }
                        } else {
                            Log.i(TAG, "视频格式异常");
                        }

                    } else {

//                        Log.i(TAG, "没有选中的文件");
                    }

                }
                cc2.setMsg(9876);
                mHandler.sendEmptyMessage(4444);
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
//        Toast.makeText(mContext, "开始上传", Toast.LENGTH_SHORT).show();
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
                            Log.i(TAG, "删除的文件是 ：" + mFilePath2.get(i));
                            FileUtils.delete(mFilePath2.get(i));
                            DaoUtils.FilUserDaoDel(mFileName2.get(i));

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
        CheckBox cb = (CheckBox) v.findViewById(R.id.dir_list_Check);
        chcnlSels(position);
        if (checkNum == mFileName2.size()) {
            setBtnSelectAllNo();
            isSelectdAll = false;
        } else {
            setBtnSelectAllYes();
            isSelectdAll = true;
        }
//        Toast.makeText(mContext, "==" + checkNum, Toast.LENGTH_SHORT).show();
    }


    AdapterView.OnItemLongClickListener longchick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            FileUtils.openFiles(mContext, mFilePath2.get(i));
            return false;
        }
    };
//    AdapterView.OnItemLongClickListener clickListenerlong = new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            FileUtils.openFiles(mContext,mListpath.get(position));
//            return false;
//        }
//    };

//http://blog.csdn.net/jdsjlzx/article/details/7318659

}
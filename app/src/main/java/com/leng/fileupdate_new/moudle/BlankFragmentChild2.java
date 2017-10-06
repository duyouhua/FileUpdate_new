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
import com.leng.fileupdate_new.Bean.FileUpdateStatus;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.Bean.FileUser2;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.CabackInfoNums;
import com.leng.fileupdate_new.contrl.CallBacklistview;
import com.leng.fileupdate_new.contrl.ContinueFtp;
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

import static com.leng.fileupdate_new.greendao.gen.DaoUtils.FileUserDaoQueryPrgresswhere;
import static com.leng.fileupdate_new.moudle.BlankFragmentChild1.uploadFileManager;
import static com.leng.fileupdate_new.utils.Constanxs.isUplodFirstone;
import static com.leng.fileupdate_new.utils.Constanxs.updingMap;

public class BlankFragmentChild2 extends Fragment implements View.OnClickListener, CallBacklistview {
    private View view;
    private ListView mChild2Listview;
    private ArrayList<String> mListname = new ArrayList<>();
    private ArrayList<String> mListpath = new ArrayList<>();
    private MainActivity ma;
    private Context mContext;
    private boolean isSelectdAll = true;
    private Child2Adapter mAdapter;
    private int checktrueNums = 0;
    private RelativeLayout mChild2RelativeList;
    private RelativeLayout mChild2RelativeEmpty;
    private static final String TAG = "BlankFragmentChild2";
    private boolean isbtnchick = true;
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
    //初始化回调接口
    private CabackInfoNums cabackInfoNums;

    private HashMap<String, Integer> mapIndex = new HashMap<>();
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
                case 4560:
                    if (isUplodFirstone) {
                        showFile();
                        isUplodFirstone = false;
                        Log.i(TAG, "只允许走一遍");
                    }

                    Bundle bundle = msg.getData();
                    String pathname = bundle.getString("pathname");
                    String progress = bundle.getString("progress");
                    String path = bundle.getString("pathpath");
                    int progressv = Integer.parseInt(progress);//upload发送过来的值
//                    String uploadfilename = spitContDBfilename(pathname);//正在上传中的文件的名字
                    FileUser2 ff = new FileUser2();
                    ff.setId(FileUtils.longPressLong(pathname));
                    ff.setMFileProgresdao(progressv);
                    ff.setMFileNamedao(pathname);
                    ff.setMFilePathdao(path);

                    APP.getDaoInstant().getFileUser2Dao().update(ff);//更新数据库

                    int prgressValue = FileUserDaoQueryPrgresswhere(pathname);

                    if (mapIndex.size() > 0) {
                        int dexwen = mapIndex.get(pathname);
                        Child2Adapter.updataView(dexwen, mChild2Listview, prgressValue);
                        if (prgressValue == 100) {
                            String activefileName = mListname.get(dexwen);
                            Log.i(TAG, " 谁 ：" + activefileName + " 移除列表");

                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(activefileName));
                            fileUser.setMFileTypedao("7");
                            APP.getDaoInstant().getFileUserDao().update(fileUser);
                            mHandler.sendEmptyMessage(4579);
                        }
                        Log.i(TAG, "mapIndex不为空 " + "要更新的下标是 ：" + dexwen + "  更新的值是 ：" + prgressValue);
                    } else {
                        Log.i(TAG, "mapIndex ==null");
                    }


                    Log.i(TAG, pathname + "==" + progress + "int值 ：" + progressv + "名字是 ：" + spitContDBfilename(pathname) + "读取到的值是");
                    break;
            }
        }


    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ma = (MainActivity) activity;
        ma.setmHandlerUpding(mHandler);
        cabackInfoNums = ma;
    }


    //线程管理对象
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
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

    /**
     * 去数据库作比对
     */
    private boolean isDaoFileExits(String filename) {
        if (mListname.size() > 0) {
            for (int i = 0; i < DaoUtils.FileUserDaoQuerywhere("6").size(); i++) {
                if (DaoUtils.FileUserDaoQuerywhere("6").get(i).getId().equals(FileUtils.longPressLong(filename))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 截取远程服务端的名字
     */


    private String spitContDBfilename(String name) {
        String[] ass = name.split("&");
        return ass[ass.length - 1];
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            showFile();
        } else {
        }
    }

    private int ifIndexWhen(String name) {
        if (mListname.size() > 0) {
            for (int i = 0; i < mListname.size(); i++) {
                if (mListname.get(i).equals(name)) {
                    return i;
                }
            }
        }
        return 11111;
    }

    private void showFile() {
        mListname.clear();
        mListpath.clear();
        checkNum = 0;
        checktrueNums = 0;
        isSelectdAll = true;//标记删除后不能在全选
        if (DaoUtils.FileUserDaoQuerywhere("6") != null && DaoUtils.FileUserDaoQuerywhere("6").size() > 0) {
            mapIndex.clear();
            for (int i1 = 0; i1 < DaoUtils.FileUserDaoQuerywhere("6").size(); i1++) {
                String name = DaoUtils.FileUserDaoQuerywhere("6").get(i1).getMFileNamedao();
                String path = DaoUtils.FileUserDaoQuerywhere("6").get(i1).getMFilePathdao();
                mListname.add(name);
                mListpath.add(path);
                mapIndex.put(name, mapIndex.size());
                mChild2RelativeEmpty.setVisibility(View.GONE);
                mChild2RelativeList.setVisibility(View.VISIBLE);
                mAdapter = new Child2Adapter(mContext, mListname, mListpath, this);
                mChild2Listview.setAdapter(mAdapter);
                //保存正在上传的数量
                SharedPreferencesUtils.setParam(mContext, Constanxs.FOTERNUMSTWO, mListname.size() + "");
                Log.i(TAG, "查询到的数据是 ：" + DaoUtils.FileUserDaoQuerywhere("6").get(i1).getMFileNamedao() + "  map的size是： " + mapIndex.size());
            }
        } else {
            Log.i(TAG, "没有查询到数据");
            mChild2RelativeEmpty.setVisibility(View.VISIBLE);
            mChild2RelativeList.setVisibility(View.GONE);
        }

        setBtnSelectAllYes();

        //回调发送消息
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
                            FileUser fileUser = new FileUser();
                            fileUser.setId(null);
                            fileUser.setMFileTypedao(null);
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

    /**
     * 撤销
     */
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
        public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
            boolean checkIS = true;
//            CheckBox cb = view.findViewById(R.id.dir_list_Check);
//            final  Button pause = view.findViewById(R.id.pause_button);
//            final Button start = view.findViewById(R.id.start_button);
//            pause.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "点击暂停按钮", Toast.LENGTH_SHORT).show();
//                    pause.setVisibility(View.GONE);
//                    start.setVisibility(View.VISIBLE);
//                }
//            });
//            start.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "点击开始按钮", Toast.LENGTH_SHORT).show();
//                    pause.setVisibility(View.VISIBLE);
//                    start.setVisibility(View.GONE);
//                }
//            });


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

    @Override
    public void click(View view) {
        int postion= (int) view.getTag();
        Button pbutton = view.findViewById(R.id.pause_button);
//        Button pbutton = LayoutInflater.from(mContext).inflate(R.layout.child2_item,null).findViewById(R.id.pause_button);
//        Button sbutton = LayoutInflater.from(mContext).inflate(R.layout.child2_item,null).findViewById(R.id.start_button);
        Button sbutton = view.findViewById(R.id.start_button);

        if (uploadFileManager == null) {
            uploadFileManager = new UploadFileManager(mContext);
        }
        if (isbtnchick) {

            pbutton.setText("开始");
            isbtnchick = false;
//            TestBean testBean=new TestBean();
//            testBean.setRemotefilepath("2bgz12yp0_0"+mListname.get(postion));
//            testBean.setLocfilepath(mListpath.get(postion));
//            testBean.setLocfileName(mListname.get(postion));
//            uploadFileManager.pause(testBean);
        } else {
            pbutton.setText("暂停");
            isbtnchick = true;
        }
    }
}

package com.leng.fileupdate_new.moudle;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leng.fileupdate_new.Adapter.MyAdapter;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.utils.Constanxs;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LiuLanActivity extends ListActivity implements View.OnClickListener {
    private int mAction;
    /**
     * 文件（夹）名字
     */
    private List<String> items = null;
    /**
     * 文件（夹）路径
     */
    private List<String> paths = null;
    /**
     * 根目录
     **/
    private String rootPath = "/";
    /**
     * 当前目录
     **/
    private String curPath = "/mnt/";
    /**
     * 显示当前目录
     **/
    private TextView mPath;
    private String TAG = "LiuLanActivity";
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_lan);


        mPath = (TextView) findViewById(R.id.mPath);
        findViewById(R.id.buttonConfirm).setOnClickListener(this);
        findViewById(R.id.buttonCancle).setOnClickListener(this);

        getFileDir(curPath);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoding();
    }

    private void initLoding() {
        Intent intent = getIntent();
        mAction = intent.getIntExtra("startType", 0);
        switch (mAction) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }
    }


    /**
     * 获取指定目录下的所有文件(夹)
     *
     * @param filePath
     */
    private void getFileDir(String filePath) {
        mPath.setText(filePath);
        items = new ArrayList<String>();
        paths = new ArrayList<String>();
        File f = new File(filePath);
        File[] files = f.listFiles();

        //用来显示 “返回根目录”+"上级目录"
        if (!filePath.equals(rootPath)) {
            items.add("rootPath");
            paths.add(rootPath);

            items.add("parentPath");
            paths.add(f.getParent());
        }


        //先排序
        List<File> resultList = null;
        if (files != null) {
            Log.i("hnyer", files.length + " " + filePath);
            resultList = new ArrayList<File>();
            int DirectoryCount = 0;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                //测试
                if (file.isDirectory()) {
                    DirectoryCount++;
                }
                if (!file.getName().startsWith(".")) {
                    resultList.add(file);
                }
            }

            if (DirectoryCount == 0) {
                Log.d("输出", "最后一层");
                Toast.makeText(this, "已经是最后一个文件夹", Toast.LENGTH_SHORT).show();
            }

            //
            Collections.sort(resultList, new Comparator<File>() {
                @Override
                public int compare(File bean1, File bean2) {
                    return bean1.getName().toLowerCase().compareTo(bean2.getName().toLowerCase());

                }
            });

            for (int i = 0; i < resultList.size(); i++) {
                File file = resultList.get(i);
                items.add(file.getName());
                paths.add(file.getPath());
            }
        } else {
            Log.i("hnyer", filePath + "无子文件");
            Toast.makeText(this, "已经是最后一个文件夹", Toast.LENGTH_SHORT).show();
        }

        setListAdapter(new MyAdapter(this, items, paths));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(paths.get(position));
        if (file.isDirectory()) {
            curPath = paths.get(position);
            getFileDir(paths.get(position));
        } else {
            openFile(file);
        }
    }

    private void openFile(File f) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        String type = getMIMEType(f);
        intent.setDataAndType(Uri.fromFile(f), type);
        startActivity(intent);
    }

    private String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else {
            type = "*";
        }
        type += "/*";
        return type;
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.buttonConfirm:
                String as = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, Constanxs.yesDir + mAction, "null");
                Log.i(TAG, "选中的文件夹路径是 ：" + curPath);
                if (isFileoreden()) {
                    Intent intent = new Intent();
                    intent.putExtra("strPATH", curPath);
                    setResult(mAction, intent);
                    Log.i(TAG, "选中的文件夹路径是daskldalskdasd ：" + mList.size());
                    finish();
                }
                break;
            case R.id.buttonCancle:
                finish();
                break;
        }
    }
    private boolean isFileoreden() {
        String a = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, "mSettingPath1", "NULL");
        String b = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, "mSettingPath2", "NULL");
        String c = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, "mSettingPath3", "NULL");
        String d = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, "mSettingPath4", "NULL");
        String e = (String) SharedPreferencesUtils.getParam(LiuLanActivity.this, "mSettingPath5", "NULL");
        mList.clear();
        mList.add(a);
        mList.add(b);
        mList.add(c);
        mList.add(d);
        mList.add(e);
        if (a.equals("NULL") && b.equals("NULL") && c.equals("NULL") && d.equals("NULL") && e.equals("NULL")) {
            return true;
        } else {

            if (!curPath.equals(a)&&!curPath.equals(b)&&!curPath.equals(c)&&!curPath.equals(d)&&!curPath.equals(e)){

                 return true;
            }else {
                Toast.makeText(this, "已经添加过了", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }
}
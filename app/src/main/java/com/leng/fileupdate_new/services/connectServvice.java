package com.leng.fileupdate_new.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.Bean.FileUser2;
import com.leng.fileupdate_new.MainActivity;
import com.leng.fileupdate_new.contrl.FileManger;
import com.leng.fileupdate_new.greendao.gen.DaoUtils;
import com.leng.fileupdate_new.upload.TestBean;
import com.leng.fileupdate_new.upload.UploadFileManager;
import com.leng.fileupdate_new.utils.FileUtils;
import com.leng.fileupdate_new.utils.SharedPreferencesUtils;

import java.io.File;
import java.util.ArrayList;

import static com.leng.fileupdate_new.utils.Constanxs.isUplodFirstone;
import static com.leng.fileupdate_new.utils.FileUtils.getFmat;


public class connectServvice extends Service {
    private static UploadFileManager uploadFileManager;
    private final static String TAG = "connectServvice";
    //存储文件名称
    private ArrayList<String> mFileName = new ArrayList<String>();
    //存储文件路径
    private ArrayList<String> mFilePath = new ArrayList<String>();
    private ArrayList<String> mFileName2 = new ArrayList<String>();
    //存储文件路径
    private ArrayList<String> mFilePath2 = new ArrayList<String>();
    private Context mContext;
    private String regCodex;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String path = (String) SharedPreferencesUtils.getParam(getBaseContext(), "mSettingPath1", "null");
                    if (!path.equals("null")) {
                        Log.i(TAG, "1 每隔两秒循环一次 。" + path);

                        final File file = new File(path);
                        final File[] files = file.listFiles();
                        listClear();//清空数据集合
                        if (files != null && files.length > 0) {
                            String isd = (String) SharedPreferencesUtils.getParam(getBaseContext(), "iffirstAdd", "null");
                            for (File f : files) {
                                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                                    mFileName.add(f.getName());
                                    mFilePath.add(f.getAbsolutePath());
                                    if (isd.equals("null")) {
                                        FileUser fileUser = new FileUser();
                                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                                        fileUser.setMFileTypedao("1");
                                        fileUser.setMFileProgresdao(0);
                                        fileUser.setMFileNamedao(f.getName());
                                        fileUser.setMFilePathdao(f.getAbsolutePath());
                                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                                    } else {
                                        if (!isDaoFileExits(f)) {
                                            FileUser fileUser = new FileUser();
                                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                                            fileUser.setMFileTypedao("1");
                                            fileUser.setMFileProgresdao(0);
                                            fileUser.setMFileNamedao(f.getName());
                                            fileUser.setMFilePathdao(f.getAbsolutePath());
                                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        }
                                    }
                                }
                            }
                            SharedPreferencesUtils.setParam(getBaseContext(), "iffirstAdd", "2");
                            if (DaoUtils.FileUserDaoQuerywhere("1") != null) {
                                queryAllFile("1");
                            } else {
                                Log.i(TAG, "没有 可上传的文件");
                            }

                        } else {
                            Log.i(TAG, "没有 可上传的文件");
                        }

                        for (int i = 0; i < mFileName2.size(); i++) {
                            //服务端文件夹名称
                            String famt = getFmat(getExtension2(mFileName2.get(i)));
                            //服务端路径


                            int type = 0;
//                            if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }


                            String remote;
                            if (type != 2&&FileUtils.isPictureIsRotated(mFilePath2.get(i))) {
                                remote = regCodex + "_0_" + mFileName2.get(i);
                            } else {
                                remote = regCodex+"_1_" + mFileName2.get(i);
                            }




                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            fileUser.setMFileTypedao("6");
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(mFileName2.get(i));
                            fileUser.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                            FileUser2 ff = new FileUser2();
                            ff.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            ff.setMFileProgresdao(0);
                            ff.setMFileNamedao(mFileName2.get(i));
                            ff.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                            final TestBean testBean = new TestBean();
                            testBean.setLocfilepath(mFilePath2.get(i));
                            testBean.setRemotefilepath(remote);
                            testBean.setLocfileName(mFileName2.get(i));
                            testBean.setCrateFileType(famt);
                            testBean.setUpLoadStatus("1");
                            testBean.setCrateFileTypenums(type);
                            if (uploadFileManager == null) {
                                uploadFileManager = new UploadFileManager(mContext);
                                Log.i(TAG,"难道上传的管理者为空了？？？");
                            }
                            uploadFileManager.startUpLoad(testBean);
                            Log.i(TAG, "自动上传 ：" + "服务端名字是" + remote + " 文件格式是： " + famt);
                        }

                        isUplodFirstone = true;
                        mHandler.sendEmptyMessageDelayed(1, 60000);
                    } else {
                        Log.i(TAG, "没有获取到默认目录的路径 请先设置");
                    }
                    break;
                case 2:
                    String path2 = (String) SharedPreferencesUtils.getParam(getBaseContext(), "mSettingPath2", "null");
                    if (!path2.equals("null")) {
                        Log.i(TAG, "1 每隔两秒循环一次 。" + path2);

                        final File file = new File(path2);
                        final File[] files = file.listFiles();
                        listClear();//清空数据集合
                        if (files != null && files.length > 0) {
                            String isd = (String) SharedPreferencesUtils.getParam(getBaseContext(), "iffirstAdd", "null");
                            for (File f : files) {
                                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                                    mFileName.add(f.getName());
                                    mFilePath.add(f.getAbsolutePath());
                                    if (isd.equals("null")) {
                                        FileUser fileUser = new FileUser();
                                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                                        fileUser.setMFileTypedao("2");
                                        fileUser.setMFileProgresdao(0);
                                        fileUser.setMFileNamedao(f.getName());
                                        fileUser.setMFilePathdao(f.getAbsolutePath());
                                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                                    } else {
                                        if (!isDaoFileExits(f)) {
                                            FileUser fileUser = new FileUser();
                                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                                            fileUser.setMFileTypedao("2");
                                            fileUser.setMFileProgresdao(0);
                                            fileUser.setMFileNamedao(f.getName());
                                            fileUser.setMFilePathdao(f.getAbsolutePath());
                                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        }
                                    }
                                }
                            }
                            SharedPreferencesUtils.setParam(getBaseContext(), "iffirstAdd", "2");
                            if (DaoUtils.FileUserDaoQuerywhere("2") != null) {
                                queryAllFile("2");
                            } else {
                                Log.i(TAG, "没有2 可上传的文件");
                            }

                        } else {
                            Log.i(TAG, "没有 2可上传的文件");
                        }

                        for (int i = 0; i < mFileName2.size(); i++) {
                            //服务端文件夹名称
                            String famt = getFmat(getExtension2(mFileName2.get(i)));
                            //服务端路径

                            int type = 0;
//                            if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }
                            String remote;
                            if (type != 2&&FileUtils.isPictureIsRotated(mFilePath2.get(i))) {
                                remote = regCodex + "_0_" + mFileName2.get(i);
                            } else {
                                remote = regCodex+"_1_" + mFileName2.get(i);
                            }

                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            fileUser.setMFileTypedao("6");
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(mFileName2.get(i));
                            fileUser.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                            FileUser2 ff = new FileUser2();
                            ff.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            ff.setMFileProgresdao(0);
                            ff.setMFileNamedao(mFileName2.get(i));
                            ff.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                            final TestBean testBean = new TestBean();
                            testBean.setLocfilepath(mFilePath2.get(i));
                            testBean.setRemotefilepath(remote);
                            testBean.setLocfileName(mFileName2.get(i));
                            testBean.setCrateFileType(famt);
                            testBean.setUpLoadStatus("1");
                            testBean.setCrateFileTypenums(type);
                            if (uploadFileManager == null) {
                                uploadFileManager = new UploadFileManager(mContext);
                            }
                            uploadFileManager.startUpLoad(testBean);
                            Log.i(TAG, "自动上传 ：" + "服务端名字是" + remote + " 文件格式是： " + famt);
                        }

                        isUplodFirstone = true;
                        mHandler.sendEmptyMessageDelayed(2, 60000);
                    } else {
                        Log.i(TAG, "没有获取到默认目录的路径 请先设置");
                    }
                    break;
                case 3:
                    String path3 = (String) SharedPreferencesUtils.getParam(getBaseContext(), "mSettingPath3", "null");
                    if (!path3.equals("null")) {
                        Log.i(TAG, "3 每隔两秒循环一次 。" + path3);

                        final File file = new File(path3);
                        final File[] files = file.listFiles();
                        listClear();//清空数据集合
                        if (files != null && files.length > 0) {
                            String isd = (String) SharedPreferencesUtils.getParam(getBaseContext(), "iffirstAdd", "null");
                            for (File f : files) {
                                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                                    mFileName.add(f.getName());
                                    mFilePath.add(f.getAbsolutePath());
                                    if (isd.equals("null")) {
                                        FileUser fileUser = new FileUser();
                                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                                        fileUser.setMFileTypedao("3");
                                        fileUser.setMFileProgresdao(0);
                                        fileUser.setMFileNamedao(f.getName());
                                        fileUser.setMFilePathdao(f.getAbsolutePath());
                                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                                    } else {
                                        if (!isDaoFileExits(f)) {
                                            FileUser fileUser = new FileUser();
                                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                                            fileUser.setMFileTypedao("3");
                                            fileUser.setMFileProgresdao(0);
                                            fileUser.setMFileNamedao(f.getName());
                                            fileUser.setMFilePathdao(f.getAbsolutePath());
                                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        }
                                    }
                                }
                            }
                            SharedPreferencesUtils.setParam(getBaseContext(), "iffirstAdd", "2");
                            if (DaoUtils.FileUserDaoQuerywhere("3") != null) {
                                queryAllFile("3");
                            } else {
                                Log.i(TAG, "没有 可上传的文件");
                                return;
                            }

                        } else {
                            Log.i(TAG, "没有 可上传的文件");
                            return;
                        }

                        for (int i = 0; i < mFileName2.size(); i++) {
                            //服务端文件夹名称
                            String famt = getFmat(getExtension2(mFileName2.get(i)));
                            //服务端路径

                            int type = 0;
//                            if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }
                            String remote;
                            if (type != 2&&FileUtils.isPictureIsRotated(mFilePath2.get(i))) {
                                remote = regCodex + "_0_" + mFileName2.get(i);
                            } else {
                                remote = regCodex+"_1_" + mFileName2.get(i);
                            }

                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            fileUser.setMFileTypedao("6");
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(mFileName2.get(i));
                            fileUser.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                            FileUser2 ff = new FileUser2();
                            ff.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            ff.setMFileProgresdao(0);
                            ff.setMFileNamedao(mFileName2.get(i));
                            ff.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                            final TestBean testBean = new TestBean();
                            testBean.setLocfilepath(mFilePath2.get(i));
                            testBean.setRemotefilepath(remote);
                            testBean.setLocfileName(mFileName2.get(i));
                            testBean.setCrateFileType(famt);
                            testBean.setUpLoadStatus("1");
                            testBean.setCrateFileTypenums(type);
                            if (uploadFileManager == null) {
                                uploadFileManager = new UploadFileManager(mContext);
                                Log.i(TAG,"难道上传的管理者为空了？？？");
                            }
                            uploadFileManager.startUpLoad(testBean);
                            Log.i(TAG, "自动上传 ：" + "服务端名字是" + remote + " 文件格式是： " + famt);
                        }

                        isUplodFirstone = true;
                        mHandler.sendEmptyMessageDelayed(3, 60000);
                    } else {
                        Log.i(TAG, "没有获取到默认目录的路径 请先设置");
                    }
                    break;
                case 4:
                    String path4 = (String) SharedPreferencesUtils.getParam(getBaseContext(), "mSettingPath4", "null");
                    if (!path4.equals("null")) {
                        Log.i(TAG, "1 每隔两秒循环一次 。" + path4);

                        final File file = new File(path4);
                        final File[] files = file.listFiles();
                        listClear();//清空数据集合
                        if (files != null && files.length > 0) {
                            String isd = (String) SharedPreferencesUtils.getParam(getBaseContext(), "iffirstAdd", "null");
                            for (File f : files) {
                                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                                    mFileName.add(f.getName());
                                    mFilePath.add(f.getAbsolutePath());
                                    if (isd.equals("null")) {
                                        FileUser fileUser = new FileUser();
                                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                                        fileUser.setMFileTypedao("4");
                                        fileUser.setMFileProgresdao(0);
                                        fileUser.setMFileNamedao(f.getName());
                                        fileUser.setMFilePathdao(f.getAbsolutePath());
                                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                                    } else {
                                        if (!isDaoFileExits(f)) {
                                            FileUser fileUser = new FileUser();
                                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                                            fileUser.setMFileTypedao("4");
                                            fileUser.setMFileProgresdao(0);
                                            fileUser.setMFileNamedao(f.getName());
                                            fileUser.setMFilePathdao(f.getAbsolutePath());
                                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        }
                                    }
                                }
                            }
                            SharedPreferencesUtils.setParam(getBaseContext(), "iffirstAdd", "2");
                            if (DaoUtils.FileUserDaoQuerywhere("4") != null) {
                                queryAllFile("4");
                            } else {
                                Log.i(TAG, "没有 可上传的文件");
                            }

                        } else {
                            Log.i(TAG, "没有 可上传的文件");
                        }

                        for (int i = 0; i < mFileName2.size(); i++) {
                            //服务端文件夹名称
                            String famt = getFmat(getExtension2(mFileName2.get(i)));
                            //服务端路径

                            int type = 0;
//                            if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }
                            String remote;
                            if (type != 2&&FileUtils.isPictureIsRotated(mFilePath2.get(i))) {
                                remote = regCodex + "_0_" + mFileName2.get(i);
                            } else {
                                remote = regCodex+"_1_" + mFileName2.get(i);
                            }

                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            fileUser.setMFileTypedao("6");
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(mFileName2.get(i));
                            fileUser.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                            FileUser2 ff = new FileUser2();
                            ff.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            ff.setMFileProgresdao(0);
                            ff.setMFileNamedao(mFileName2.get(i));
                            ff.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                            final TestBean testBean = new TestBean();
                            testBean.setLocfilepath(mFilePath2.get(i));
                            testBean.setRemotefilepath(remote);
                            testBean.setLocfileName(mFileName2.get(i));
                            testBean.setCrateFileType(famt);
                            testBean.setUpLoadStatus("1");
                            testBean.setCrateFileTypenums(type);
                            if (uploadFileManager == null) {
                                uploadFileManager = new UploadFileManager(mContext);
                            }
                            uploadFileManager.startUpLoad(testBean);
                            Log.i(TAG, "自动上传 ：" + "服务端名字是" + remote + " 文件格式是： " + famt);
                        }

                        isUplodFirstone = true;
                        mHandler.sendEmptyMessageDelayed(4, 60000);
                    } else {
                        Log.i(TAG, "没有获取到默认目录的路径 请先设置");
                    }
                    break;
                case 5:
                    String path5 = (String) SharedPreferencesUtils.getParam(getBaseContext(), "mSettingPath5", "null");
                    if (!path5.equals("null")) {
                        Log.i(TAG, "1 每隔两秒循环一次 。" + path5);

                        final File file = new File(path5);
                        final File[] files = file.listFiles();
                        listClear();//清空数据集合
                        if (files != null && files.length > 0) {
                            String isd = (String) SharedPreferencesUtils.getParam(getBaseContext(), "iffirstAdd", "null");
                            for (File f : files) {
                                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                                    mFileName.add(f.getName());
                                    mFilePath.add(f.getAbsolutePath());
                                    if (isd.equals("null")) {
                                        FileUser fileUser = new FileUser();
                                        fileUser.setId(FileUtils.longPressLong(f.getName()));
                                        fileUser.setMFileTypedao("5");
                                        fileUser.setMFileProgresdao(0);
                                        fileUser.setMFileNamedao(f.getName());
                                        fileUser.setMFilePathdao(f.getAbsolutePath());
                                        APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        Log.i(TAG, "第一次添加 " + FileUtils.longPressLong(f.getName()) + "名字是 ：" + f.getName());
                                    } else {
                                        if (!isDaoFileExits(f)) {
                                            FileUser fileUser = new FileUser();
                                            fileUser.setId(FileUtils.longPressLong(f.getName()));
                                            fileUser.setMFileTypedao("5");
                                            fileUser.setMFileProgresdao(0);
                                            fileUser.setMFileNamedao(f.getName());
                                            fileUser.setMFilePathdao(f.getAbsolutePath());
                                            APP.getDaoInstant().getFileUserDao().insertOrReplace(fileUser);
                                        }
                                    }
                                }
                            }
                            SharedPreferencesUtils.setParam(getBaseContext(), "iffirstAdd", "2");
                            if (DaoUtils.FileUserDaoQuerywhere("5") != null) {
                                queryAllFile("5");
                            } else {
                                Log.i(TAG, "没有 可上传的文件");
                            }

                        } else {
                            Log.i(TAG, "没有 可上传的文件");
                        }

                        for (int i = 0; i < mFileName2.size(); i++) {
                            //服务端文件夹名称
                            String famt = getFmat(getExtension2(mFileName2.get(i)));
                            //服务端路径

                            int type = 0;
//                            if (famt != null) {
                            if (famt.equals("/Images/")) {
                                type = 1;
                            } else if (famt.equals("/Audios/")) {
                                type = 2;
                            } else if (famt.equals("/Videos/")) {
                                type = 3;
                            }
                            String remote;
                            if (type != 2&&FileUtils.isPictureIsRotated(mFilePath2.get(i))) {
                                remote = regCodex + "_0_" + mFileName2.get(i);
                            } else {
                                remote = regCodex+"_1_" + mFileName2.get(i);
                            }

                            FileUser fileUser = new FileUser();
                            fileUser.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            fileUser.setMFileTypedao("6");
                            fileUser.setMFileProgresdao(0);
                            fileUser.setMFileNamedao(mFileName2.get(i));
                            fileUser.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUserDao().update(fileUser);

                            FileUser2 ff = new FileUser2();
                            ff.setId(FileUtils.longPressLong(mFileName2.get(i)));
                            ff.setMFileProgresdao(0);
                            ff.setMFileNamedao(mFileName2.get(i));
                            ff.setMFilePathdao(mFilePath2.get(i));
                            APP.getDaoInstant().getFileUser2Dao().insertOrReplace(ff);//更新数据库


                            final TestBean testBean = new TestBean();
                            testBean.setLocfilepath(mFilePath2.get(i));
                            testBean.setRemotefilepath(remote);
                            testBean.setLocfileName(mFileName2.get(i));
                            testBean.setCrateFileType(famt);
                            testBean.setUpLoadStatus("1");
                            testBean.setCrateFileTypenums(type);
                            if (uploadFileManager == null) {
                                uploadFileManager = new UploadFileManager(mContext);
                            }
                            uploadFileManager.startUpLoad(testBean);
                            Log.i(TAG, "自动上传 ：" + "服务端名字是" + remote + " 文件格式是： " + famt);
                        }

                        isUplodFirstone = true;
                        mHandler.sendEmptyMessageDelayed(5, 60000);
                    } else {
                        Log.i(TAG, "没有获取到默认目录的路径 请先设置");
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mContext = MainActivity.mActivityContext;
        uploadFileManager = new UploadFileManager(mContext);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mContext==null){
            mContext=new MainActivity();
            Log.i(TAG,"引用了一个空对想");
        }
        Log.i(TAG,"引用了一个空对想zoujibian");
        regCodex = (String) SharedPreferencesUtils.getParam(getBaseContext(), "regcode", "null");
        if (regCodex.equals("null")) {
            regCodex = "2bgz12yp";
        }
        if (intent != null) {
            String mode = intent.getStringExtra("setmode");
            boolean onoff = intent.getBooleanExtra("setstartORstop", false);
            switch (mode) {
                case "1":
                    if (onoff) {
                        mHandler.sendEmptyMessage(1);
                        isUplodFirstone = true;
                    } else {

                        mHandler.removeMessages(1);
                        Log.i(TAG, "关闭默认目录1的自动上传");
                    }
                    break;
                case "2":
                    if (onoff) {
                        mHandler.sendEmptyMessage(2);
                        isUplodFirstone = true;
                    } else {

                        mHandler.removeMessages(2);
                        Log.i(TAG, "关闭默认目录1的自动上传");
                    }
                    break;
                case "3":
                    if (onoff) {
                        mHandler.sendEmptyMessage(3);
                        isUplodFirstone = true;
                    } else {

                        mHandler.removeMessages(3);
                        Log.i(TAG, "关闭默认目录1的自动上传");
                    }
                    break;
                case "4":
                    if (onoff) {
                        mHandler.sendEmptyMessage(4);
                        isUplodFirstone = true;
                    } else {

                        mHandler.removeMessages(4);
                        Log.i(TAG, "关闭默认目录1的自动上传");
                    }
                    break;
                case "5":
                    if (onoff) {
                        isUplodFirstone = true;
                        mHandler.sendEmptyMessage(5);
                    } else {

                        mHandler.removeMessages(5);
                        Log.i(TAG, "关闭默认目录1的自动上传");
                    }

                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(this, "Itent =null", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //根据文件
    private static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    //根据名称
    private static String getExtension2(final String name) {
        String suffix = "";
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
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
        } else {
            Log.i(TAG, "没有查询到类型为1的数据==child1");
        }
    }
}

package com.leng.fileupdate_new;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.leng.fileupdate_new.greendao.gen.DaoMaster;
import com.leng.fileupdate_new.greendao.gen.DaoSession;
import com.leng.other.loadingdialog.manager.StyleManager;
import com.leng.other.loadingdialog.view.LoadingDialog;

/**
 * Created by Administrator on 2017/9/21.
 */

public class APP extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();

        StyleManager s = new StyleManager();

            //在这里调用方法设置s的属性
            //code here...
        s.Anim(false).repeatTime(0).contentSize(-1).intercept(true);

        LoadingDialog.initStyle(s);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db" 创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象  GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者  管理所有的Dao对象，Dao对象中存在着增删改查等API
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}

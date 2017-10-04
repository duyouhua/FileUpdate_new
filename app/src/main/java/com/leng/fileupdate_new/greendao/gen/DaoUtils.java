package com.leng.fileupdate_new.greendao.gen;

import android.util.Log;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.utils.FileUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class DaoUtils {
    public static List<FileUser> FileUserDaoQuery() {
        List<FileUser> liss = APP.getDaoInstant().getFileUserDao().queryBuilder().list();
        return liss;
    }


    /**
     * 存入指定键  根据指定建删除文件
     */
    public static void FilUserDaoDel(String id) {
        APP.getDaoInstant().getFileUserDao().deleteByKey(FileUtils.longPressLong(id));
    }

    /**
     * 根据查询条件,返回数据列表
     * 类型查询
     *
     * @return 数据列表
     */
    public static List<FileUser> FileUserDaoQuerywhere(String as) {
        List<FileUser> joes = APP.getDaoInstant().getFileUserDao().queryBuilder()
                .where(FileUserDao.Properties.MFileTypedao.eq(as))
                .orderDesc(FileUserDao.Properties.MFileNamedao)
                .list();
        if (joes.size() > 0) {
            return joes;
        } else {
            return null;
        }
    }

    /**
     * 根据查询条件,返回数据列表
     * 进度值查询
     *
     * @return 数据列表
     */

    public static int FileUserDaoQueryPrgresswhere(String name) {
        long id = FileUtils.longPressLong(name);
        return APP.getDaoInstant().getFileUser2Dao().load(id).getMFileProgresdao();
    }
}
package com.leng.fileupdate_new.greendao.gen;

import android.util.Log;

import com.leng.fileupdate_new.APP;
import com.leng.fileupdate_new.Bean.FileUser;
import com.leng.fileupdate_new.Bean.FileUser2;
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
     * 存入指定键  根据指定建删除文件
     */
    public static void FilUser2DaoDel(String id) {
        APP.getDaoInstant().getFileUser2Dao().deleteByKey(FileUtils.longPressLong(id));
    }

    /**
     * 存入指定键  根据指定建删除文件
     */
    public static void FilUserRevocationDaoDel(String name) {
        APP.getDaoInstant().getFileUserRevocationDao().deleteByKey(FileUtils.longPressLong(name));
    }

    /**
     * 根据查询条件,返回数据列表
     * 类型查询
     * 倒序列  日期大的在上面  类型查询  所有的数据
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

    /**
     * 根据查询条件,返回数据列表
     * 当前文件从哪个目录进来的
     *
     * @return 数据列表
     */

    public static String FileUserDaoQuerypathAderesswhere(String name) {
        long id = FileUtils.longPressLong(name);
        return APP.getDaoInstant().getFileUserRevocationDao().load(id).getMFileTypedao();
    }
    public static String FileUserDaoQuerypathFmatswhere(String name) {
        long id = FileUtils.longPressLong(name);
        return APP.getDaoInstant().getFileUserRevocationDao().load(id).getMFileOther1();
    }
  public static String FileUserDaoQuerypathFmattypeswhere(String name) {
        long id = FileUtils.longPressLong(name);
        return APP.getDaoInstant().getFileUserRevocationDao().load(id).getMFileOther2();
    }

    /**
     * 查询user2
     * 的所有进度值为100的列表
     */
    public static List<FileUser2> FileUserDaoQueryPrgressAchieve() {
        List<FileUser2> joes = APP.getDaoInstant().getFileUser2Dao().queryBuilder()
                .where(FileUser2Dao.Properties.MFileProgresdao.eq(100))
                .orderDesc(FileUser2Dao.Properties.MFileNamedao)
                .list();
        if (joes.size() > 0) {
            return joes;
        } else {
            return null;
        }
    }

    /**
     * 获取五个默认目录所有未上传文个数
     */
    public static int FileQueryWsc() {
        int nums = 0;
        int nums2 = 0;

        for (int i = 1; i < 6; i++) {
            nums = APP.getDaoInstant().getFileUserDao().queryBuilder().where(FileUserDao.Properties.MFileTypedao.eq(i)).list().size();
            nums2 = nums + nums2;
            Log.i("qweqwea", "多少个。。。" + nums2);
        }
        return nums2;

    }
}
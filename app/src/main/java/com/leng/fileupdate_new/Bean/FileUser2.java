package com.leng.fileupdate_new.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2017/9/28.
 */
@Entity
public class FileUser2 {
    @Id
    private Long id;
    @Property
    private String mFileNamedao;
    @Property
    private String mFilePathdao;
    @Property
    private String mFileTypedao;
    @Property
    private int mFileProgresdao;
    private int mFilexOther1;
    private int mFilexOther2;
    private int mFilexOther3;
    private String mFileOther1dao;
    private String mFileOther2dao;
    private String mFileOther3dao;
    private String mFileOther4dao;
    public String getMFileOther4dao() {
        return this.mFileOther4dao;
    }
    public void setMFileOther4dao(String mFileOther4dao) {
        this.mFileOther4dao = mFileOther4dao;
    }
    public String getMFileOther3dao() {
        return this.mFileOther3dao;
    }
    public void setMFileOther3dao(String mFileOther3dao) {
        this.mFileOther3dao = mFileOther3dao;
    }
    public String getMFileOther2dao() {
        return this.mFileOther2dao;
    }
    public void setMFileOther2dao(String mFileOther2dao) {
        this.mFileOther2dao = mFileOther2dao;
    }
    public String getMFileOther1dao() {
        return this.mFileOther1dao;
    }
    public void setMFileOther1dao(String mFileOther1dao) {
        this.mFileOther1dao = mFileOther1dao;
    }
    public int getMFilexOther3() {
        return this.mFilexOther3;
    }
    public void setMFilexOther3(int mFilexOther3) {
        this.mFilexOther3 = mFilexOther3;
    }
    public int getMFilexOther2() {
        return this.mFilexOther2;
    }
    public void setMFilexOther2(int mFilexOther2) {
        this.mFilexOther2 = mFilexOther2;
    }
    public int getMFilexOther1() {
        return this.mFilexOther1;
    }
    public void setMFilexOther1(int mFilexOther1) {
        this.mFilexOther1 = mFilexOther1;
    }
    public int getMFileProgresdao() {
        return this.mFileProgresdao;
    }
    public void setMFileProgresdao(int mFileProgresdao) {
        this.mFileProgresdao = mFileProgresdao;
    }
    public String getMFileTypedao() {
        return this.mFileTypedao;
    }
    public void setMFileTypedao(String mFileTypedao) {
        this.mFileTypedao = mFileTypedao;
    }
    public String getMFilePathdao() {
        return this.mFilePathdao;
    }
    public void setMFilePathdao(String mFilePathdao) {
        this.mFilePathdao = mFilePathdao;
    }
    public String getMFileNamedao() {
        return this.mFileNamedao;
    }
    public void setMFileNamedao(String mFileNamedao) {
        this.mFileNamedao = mFileNamedao;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 4586974)
    public FileUser2(Long id, String mFileNamedao, String mFilePathdao,
            String mFileTypedao, int mFileProgresdao, int mFilexOther1,
            int mFilexOther2, int mFilexOther3, String mFileOther1dao,
            String mFileOther2dao, String mFileOther3dao, String mFileOther4dao) {
        this.id = id;
        this.mFileNamedao = mFileNamedao;
        this.mFilePathdao = mFilePathdao;
        this.mFileTypedao = mFileTypedao;
        this.mFileProgresdao = mFileProgresdao;
        this.mFilexOther1 = mFilexOther1;
        this.mFilexOther2 = mFilexOther2;
        this.mFilexOther3 = mFilexOther3;
        this.mFileOther1dao = mFileOther1dao;
        this.mFileOther2dao = mFileOther2dao;
        this.mFileOther3dao = mFileOther3dao;
        this.mFileOther4dao = mFileOther4dao;
    }
    @Generated(hash = 1818973172)
    public FileUser2() {
    }
  

}

package com.leng.fileupdate_new.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/9.
 */
@Entity
public class FileUserRevocation {
    @Id
    private Long id;
    @Property
    private String mFileNamedao;
    @Property
    private String mFilePathdao;
    @Property
    private String mFileTypedao;
    @Property
    private String mFileOther1;
    private String mFileOther2;
    public String getMFileOther2() {
        return this.mFileOther2;
    }
    public void setMFileOther2(String mFileOther2) {
        this.mFileOther2 = mFileOther2;
    }
    public String getMFileOther1() {
        return this.mFileOther1;
    }
    public void setMFileOther1(String mFileOther1) {
        this.mFileOther1 = mFileOther1;
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
    @Generated(hash = 1806131841)
    public FileUserRevocation(Long id, String mFileNamedao, String mFilePathdao,
            String mFileTypedao, String mFileOther1, String mFileOther2) {
        this.id = id;
        this.mFileNamedao = mFileNamedao;
        this.mFilePathdao = mFilePathdao;
        this.mFileTypedao = mFileTypedao;
        this.mFileOther1 = mFileOther1;
        this.mFileOther2 = mFileOther2;
    }
    @Generated(hash = 852912608)
    public FileUserRevocation() {
    }

}

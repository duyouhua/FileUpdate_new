package com.leng.fileupdate_new.contrl;

/**
 * Created by Administrator on 2017/9/21.
 */

public class MessageEvent {

    private int  message;

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    private  int T;
    public MessageEvent(int message,int T) {
        this.message = message;
        this.T=T;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;

    }
}
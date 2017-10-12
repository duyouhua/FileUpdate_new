package com.leng.fileupdate_new.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leng.fileupdate_new.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/26.
 */

public class TABadapter extends BaseAdapter {
    private ArrayList<String> mList = new ArrayList<>();
    private Context mContext;

    public TABadapter(ArrayList<String> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler v = null;
        if (v == null) {
            v = new ViewHodler();

            view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
            v.imageView = (ImageView) view.findViewById(R.id.tab_item_image);
            v.textView = (TextView) view.findViewById(R.id.tab_item_text);
            v.textView2 = (TextView) view.findViewById(R.id.tab_item_text_2);
            view.setTag(v);
        } else {
            v = (ViewHodler) view.getTag();

        }
        v.textView.setText(mList.get(i));
        int a=i+1;
        v.textView2.setText("默认目录" + a);

        Glide.with(mContext)
                .load(R.drawable.ic_tab_deauflt_path)
                .placeholder(R.drawable.ic_tab_deauflt_path)
                .error(R.drawable.ic_tab_deauflt_path)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(50, 50)
                .into(v.imageView);
        return view;
    }

    class ViewHodler {
        ImageView imageView;
        TextView textView;
        TextView textView2;
    }
}

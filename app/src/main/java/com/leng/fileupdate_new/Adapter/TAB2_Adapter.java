package com.leng.fileupdate_new.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leng.fileupdate_new.R;

import java.io.File;
import java.util.ArrayList;


public class TAB2_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Bitmap directory, file,back1,back2;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private Context mContext;

    //参数初始化
    public TAB2_Adapter(Context context, ArrayList<String> na, ArrayList<String> pa) {
        mContext=context;
        names = na;
        paths = pa;
        directory = BitmapFactory.decodeResource(context.getResources(), R.mipmap.wenjianjia_qian);
        file = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tab2_wenjian);
        back1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tab_fanhui_1);
        back2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tab_fanhui_2);
        //缩小图片
        directory = small(directory, 1.16f);
        file = small(file, 1.1f);
        back1 = small(back1, 1.1f);
        back2 = small(back2, 1.1f);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.tab2_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.textView);
            holder.image = (ImageView) convertView.findViewById(R.id.imageview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File f = new File(paths.get(position).toString());
        if (names.get(position).equals("@1")) {
            holder.text.setText("返回根目录");
//            holder.text.setTextColor(Color.BLACK);
            holder.image.setImageBitmap(back1);
        } else if (names.get(position).equals("@2")) {
            holder.text.setText("返回上一层");
//            holder.text.setTextColor(Color.BLACK);
            holder.image.setImageBitmap(back2);
        } else {
            holder.text.setText(f.getName());
            if (f.isDirectory()) {
                holder.image.setImageBitmap(directory);
            } else if (f.isFile()) {
                holder.image.setImageBitmap(file);
            } else {
                System.out.println(f.getName());
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView text;
        private ImageView image;
    }

    private Bitmap small(Bitmap map, float num) {
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
    }
}
package com.leng.fileupdate_new.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leng.fileupdate_new.R;
import com.leng.fileupdate_new.contrl.FileManger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/9/22.
 */

public class Child1Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Bitmap directory, file;
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private Context mContext;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    private static HashMap<String, String> mapFamt;
//    private static HashMap<String, String> mapType;

    //参数初始化
    public Child1Adapter(Context context, ArrayList<String> na, ArrayList<String> pa) {
        names = na;
        paths = pa;
        mContext = context;
        directory = BitmapFactory.decodeResource(context.getResources(), R.mipmap.wenjianjia);
//        file = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_weishibie);
        //缩小图片
//        directory = small(directory, 1.16f);
//        file = small(file, 1.1f);
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        mapFamt = new HashMap<>();
//        mapType = new HashMap<>();

        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < paths.size(); i++) {

            getIsSelected().put(i, false);
//            Toast.makeText(mContext, "SDFSD", Toast.LENGTH_SHORT).show();
        }
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
            convertView = inflater.inflate(R.layout.file_item, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.icon_text);
            holder.image = (ImageView) convertView.findViewById(R.id.icon_image);
            holder.cb = (CheckBox) convertView.findViewById(R.id.dir_list_Check);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File f = new File(paths.get(position).toString());
        if (names.get(position).equals("@1")) {
            holder.text.setText("/返回跟目录");
            holder.image.setImageBitmap(directory);
        } else if (names.get(position).equals("@2")) {
            holder.text.setText(".返回上级目录");
            holder.image.setImageBitmap(directory);
        } else {
            holder.text.setText(f.getName());
            if (f.isDirectory()) {
                holder.image.setImageBitmap(directory);
            } else if (f.isFile()) {
                if (FileManger.getSingleton().map.containsKey(getExtension(f))) {
                    String famt = getExtension(f);
                    if (famt != null) {

                        if (famt.equals("jpg") || famt.equals("png") || famt.equals("bmp") || famt.equals("jpeg") || famt.equals("gif")) {
                            Glide.with(mContext)
                                    .load(f.getAbsoluteFile())
                                    .placeholder(R.drawable.ic_zhanweitu)
                                    .error(R.drawable.ic_tupian_shibai)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .override(50, 50)
                                    .into(holder.image);
                            mapFamt.put(f.getAbsolutePath(), "/Images/");
//                            mapType.put(f.getAbsolutePath(), "1");
                        } else if (famt.equals("3gp") || famt.equals("mp4") || famt.equals("rmvb") || famt.equals("mpeg") || famt.equals("mpg") || famt.equals("asf") || famt.equals("avi") || famt.equals("wmv")) {

                            Glide.with(mContext)
                                    .load(f.getAbsoluteFile())
                                    .placeholder(R.drawable.ic_video_weijiazai_svg)
                                    .error(R.drawable.ic_video_svg)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .override(50, 50)
                                    .into(holder.image);
                            mapFamt.put(f.getAbsolutePath(), "/Videos/");
//                            mapType.put(f.getAbsolutePath(), "3");
                        } else if (famt.equals("amr") || famt.equals("mp3") || famt.equals("m4a") || famt.equals("aac") || famt.equals("ogg") || famt.equals("wav") || famt.equals("mkv") || famt.equals("flac")) {
                            Glide.with(mContext)
                                    .load(f.getAbsoluteFile())
                                    .placeholder(R.drawable.ic_music_weijiazai_svg)
                                    .error(R.drawable.ic_music_svg)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .override(50, 50)
                                    .into(holder.image);
                            mapFamt.put(f.getAbsolutePath(), "/Audios/");
//                            mapType.put(f.getAbsolutePath(), "2");
                        }
                        holder.cb.setChecked(getIsSelected().get(position));
                        Log.i("QWEQWE", getIsSelected().get(position) + "");
                    } else {
                        Toast.makeText(mContext, "famt GESHI  KONG", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Glide.with(mContext)
                            .load(R.drawable.ic_weishibie).error(R.drawable.ic_weishibie2).override(50, 50).into(holder.image);
                }
            } else {
                System.out.println(f.getName());
            }
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView text;
        private ImageView image;
        private CheckBox cb;
    }

    private Bitmap small(Bitmap map, float num) {
        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
    }

    private static String getExtension(final File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
//        isSelected.clear();
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        Child1Adapter.isSelected = isSelected;
    }

    public static HashMap<String, String> getMapFamt() {

        return mapFamt;
    }

    public static void setMapFamt(HashMap<String, String> mapFamt2) {
        mapFamt = mapFamt2;
    }

//    public static HashMap<String, String> getMapType() {
//
//        return mapType;
//    }
//
//    public static void setMapType(HashMap<String, String> mapType2) {
//        mapType  = mapType2;
//    }
}
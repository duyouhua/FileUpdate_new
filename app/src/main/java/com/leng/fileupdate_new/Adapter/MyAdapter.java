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
import java.util.List;

public class MyAdapter extends BaseAdapter
{
  private LayoutInflater mInflater;
  private Bitmap mIcon1;
  private Bitmap mIcon2;
  private Bitmap mIcon3;
  private Bitmap mIcon4;
  private List<String> items;
  private List<String> paths;
  public MyAdapter(Context context, List<String> it, List<String> pa)
  {
    mInflater = LayoutInflater.from(context);
    items = it;
    paths = pa;
    mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tab_fanhui_1);
    mIcon2 = BitmapFactory.decodeResource(context.getResources(),R.mipmap.tab_fanhui_2);
    mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.wenjianjia_qian);  //把folder变成了
    mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.tab2_wenjian);
    mIcon1=small(mIcon1,0.9f);
    mIcon2=small(mIcon2,0.85f);
    mIcon3=small(mIcon3,0.95f);
    mIcon4=small(mIcon4,0.95f);
  }
  
  public int getCount()
  {
    return items.size();
  }

  public Object getItem(int position)
  {
    return items.get(position);
  }
  
  public long getItemId(int position)
  {
    return position;
  }
  
  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder holder;
    
    if(convertView == null)
    {
      convertView = mInflater.inflate(R.layout.file_item_liulan, null);  //R.layout.file_row
      holder = new ViewHolder();
      holder.text = (TextView) convertView.findViewById(R.id.text);
      holder.icon = (ImageView) convertView.findViewById(R.id.icon);
      
      convertView.setTag(holder);
    }
    else
    {
      holder = (ViewHolder) convertView.getTag();
    }

    File f=new File(paths.get(position).toString());
    if(items.get(position).toString().equals("rootPath"))
    {
      holder.text.setText("返回根目录..");
      holder.icon.setImageBitmap(mIcon1);
    }
    else if(items.get(position).toString().equals("parentPath"))
    {
      holder.text.setText("返回上一层..");
      holder.icon.setImageBitmap(mIcon2);
    }
    else
    {
      holder.text.setText(f.getName());
      if(f.isDirectory())
      {
        holder.icon.setImageBitmap(mIcon3);
      }
      else
      {
        holder.icon.setImageBitmap(mIcon4);
      }
    }
    return convertView;
  }
  private class ViewHolder
  {
    TextView text;
    ImageView icon;
  } private Bitmap small(Bitmap map, float num) {
  Matrix matrix = new Matrix();
  matrix.postScale(num, num);
  return Bitmap.createBitmap(map, 0, 0, map.getWidth(), map.getHeight(), matrix, true);
}
}

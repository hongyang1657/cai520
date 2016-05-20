package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.ShareData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ShareGridAdapter extends MyBaseAdapter {
    List<ShareData> list;
    Context context;
    LayoutInflater inflater;
    public ShareGridAdapter(Context context, List list) {
        super(context, list);
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_sharepart,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.share_image);
            holder.tv_title= (TextView) convertView.findViewById(R.id.share_title);
            holder.btn_favour= (Button) convertView.findViewById(R.id.share_favour);
            holder.btn_collec= (Button) convertView.findViewById(R.id.share_collec);
            holder.btn_share= (Button) convertView.findViewById(R.id.share_share);
            holder.btn_position= (Button) convertView.findViewById(R.id.share_position);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.tv_title.setText(list.get(position).getTitle());
        holder.image.setBackground(list.get(position).getImage());
        Picasso.with(context).load(list.get(position).getImage_url()).into(holder.image);
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        return convertView;
    }


    public void refresh(){
        notifyDataSetChanged();
    }
    private class ViewHolder{
        ImageView image;
        TextView tv_title;
        Button btn_favour;
        Button btn_collec;
        Button btn_share;
        Button btn_position;
    }

}

package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.ShareData;
import com.bhz.android.caiyoubang.utils.MyOKHttpUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class HotAdapter extends MyBaseAdapter implements MyOKHttpUtils.OKHttpHelper {
    Context context;
    List<ShareData> list;
    LayoutInflater inflater;
    Drawable drawable;
    Handler myhandler;
    MyOKHttpUtils utils;
    int judge = 0;


    public HotAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        utils = MyOKHttpUtils.getinit();
    }

    @Override
    public int getCount() {
        return list==null?0:(list.size())*25;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if ((convertView == null)) {
            convertView = inflater.inflate(R.layout.item_gallery_hot, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.hot_gallery);
            holder.tv_content = (TextView) convertView.findViewById(R.id.hot_tv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_content.setText(list.get(position%4).getTitle());
/*        Picasso.with(context).load(list.get(position).getImage_url()).into(holder.image);*/
        utils.getDrawable(list.get(position%4).getImage_url(), this);
        holder.image.setBackground(drawable);
        return convertView;
    }

    @Override
    public void OnFailure(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnResponse(String message) {

    }

    @Override
    public void getDrawable(Drawable drawable) {
        this.drawable = drawable;
        if (judge == 0) {
            notifyDataSetChanged();
            judge = 1;
        }
    }

    @Override
    public void getdetail(String menuname, String menuabstract, String menustuff, String menutips, String menuimagehead, String[] imalist, String[] detailist) {

    }


    private final class ViewHolder {
        ImageView image;
        TextView tv_content;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}

package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.ShareData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class HotAdapter extends MyBaseAdapter {
    Context context;
    List<ShareData> list;
    LayoutInflater inflater;

    public HotAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if ((convertView == null)) {
            convertView = inflater.inflate(R.layout.item_gallery_hot, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.hot_gallery);
            holder.tv_content= (TextView) convertView.findViewById(R.id.hot_tv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_content.setText(list.get(position).getTitle());
        Picasso.with(context).load(list.get(position).getImage_url()).into(holder.image);
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView tv_content;
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}

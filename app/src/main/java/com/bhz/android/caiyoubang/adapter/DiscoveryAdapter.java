package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.EventSummary;

import java.util.List;

/**
 * Created by Administrator on 2016/5/25.
 */
public class DiscoveryAdapter extends MyBaseAdapter {
    List<EventSummary> list;
    Context context;
    LayoutInflater inflater;
    public DiscoveryAdapter(Context context, List list) {
        super(context, list);
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_discovery,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.image_discovery);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.image.setImageResource(list.get(position).getResID());
        return convertView;
    }

    private class ViewHolder{
        ImageView image;
    }

    public void refresh(){
        notifyDataSetChanged();
    }
}

package com.bhz.android.caiyoubang.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.DiscoveryAdapter;
import com.bhz.android.caiyoubang.data.EventSummary;
import com.bhz.android.caiyoubang.utils.EventDataPass;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/19.
 */
public class DiscoveryFragment extends Fragment{
    List<EventSummary> list;
    ListView listview;
    DiscoveryAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.item_fragment_discovery,container,false);
        init(view);
        return view;
    }

    private void init(View v) {
        listview= (ListView) v.findViewById(R.id.mainpage_discovery_listview);
        list=new ArrayList<>();
        setlist();
        adapter=new DiscoveryAdapter(getActivity(),list);
        listview.setAdapter(adapter);
    }

    private void setlist() {
        for (int i = 1; i <= 8; i++) {
            list.add(setEventSummary(i));
        }
    }
    private EventSummary setEventSummary(int i) {  //将图片RID填入对应list
        EventSummary eventsummary = new EventSummary();
        eventsummary.setResID(datachange(i));
        return eventsummary;
    }

    private Integer datachange(int i) {
        Integer resID = 0;
        switch (i) {
            case 1:
                resID = R.mipmap.event1;
                break;
            case 2:
                resID = R.mipmap.event2;
                break;
            case 3:
                resID = R.mipmap.event3;
                break;
            case 4:
                resID = R.mipmap.event4;
                break;
            case 5:
                resID = R.mipmap.event5;
                break;
            case 6:
                resID = R.mipmap.event6;
                break;
            case 7:
                resID = R.mipmap.event7;
                break;
            case 8:
                resID = R.mipmap.event8;
                break;
        }
        return resID;
    }


}

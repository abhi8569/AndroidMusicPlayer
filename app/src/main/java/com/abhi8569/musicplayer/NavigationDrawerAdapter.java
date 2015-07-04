package com.abhi8569.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abishek on 30-06-2015.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    private Activity activity;
    private String[] data;
    private int[] imgRes;
    private static LayoutInflater inflater = null;
    int i = 0;

    public NavigationDrawerAdapter(Activity a, String[] name, int[] resID) {
        activity = a;
        data = name;
        imgRes = resID;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data.length <= 0)
            return 1;
        return data.length;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        public ImageView img;
        public TextView tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v1 = convertView;
        ViewHolder holder;

        if (convertView == null) {
            v1 = inflater.inflate(R.layout.item_row, null);

            holder = new ViewHolder();
            holder.tv = (TextView) v1.findViewById(R.id.navigationDrawerRowText);
            holder.img = (ImageView) v1.findViewById(R.id.navigationDrawerRowIcon);
            v1.setTag(holder);
        } else
            holder = (ViewHolder) v1.getTag();

        if (data.length <= 0) {
            holder.tv.setText("No Data");
        } else {
            holder.tv.setText(data[position]);
            holder.img.setImageResource(imgRes[position]);
        }
        return v1;
    }

}

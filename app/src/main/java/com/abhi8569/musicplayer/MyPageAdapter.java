package com.abhi8569.musicplayer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abishek on 01-07-2015.
 */
public class MyPageAdapter extends PagerAdapter{
    @Override
    public int getCount() {
        return 3;
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int resId = 0;
        switch (position) {
            case 0:
                resId = R.layout.activity_album;
                break;
            case 1:
                resId = R.layout.activity_artist;
                break;
            case 2:
                resId = R.layout.activity_songs;
                break;
        }
        View view = inflater.inflate(resId, null);
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Album";
            case 1:
                return "Artist";
            case 2:
                return "Songs";
        }

        return null;
    }
}

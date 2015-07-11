package com.abhi8569.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abishek on 11-07-2015.
 */
public class NowPlayingQueueAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<SongInformation> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    SongInformation tempValues=null;
    int i=0;

    public class ViewHolder{
        public TextView txtVw;
        public ImageView imgVw;

    }

    public NowPlayingQueueAdapter(Activity a,ArrayList<SongInformation> _data,Resources locRes)
    {
        activity=a;
        data=_data;
        res=locRes;
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.song_listview_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.txtVw = (TextView) vi.findViewById(R.id.song_lisyview_textView);
            holder.imgVw=(ImageView)vi.findViewById(R.id.song_listview_imageView);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.txtVw.setText("No Songs");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( SongInformation ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.txtVw.setText( tempValues.getQueryTitle() );
            holder.imgVw.setImageBitmap(SongManager.getAlbumArt(activity.getApplicationContext(),tempValues.getAlbumID()));

        }
        return vi;
    }
}

package com.abhi8569.musicplayer;

/**
 * Created by abishek on 07-07-2015.
 */

import com.abhi8569.musicplayer.MainActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by hp1 on 21-01-2015.
 */
public class SongsTab extends Fragment implements AdapterView.OnItemClickListener {

    ListView lv;
    Cursor cu;
    View v;
    ArrayList<SongInformation> sendSongsArrayToPlayNow;
    SongListViewAdapter adapt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_songs_tab, container, false);
        sendSongsArrayToPlayNow=new ArrayList<SongInformation>();
        lv = (ListView) v.findViewById(R.id.songs_tab_listView);
        new MyTask().execute();
        lv.setOnItemClickListener(this);

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pos = position;
        cu.moveToFirst();
        while (cu.moveToNext()) {
            sendSongsArrayToPlayNow.add(new SongInformation(cu));
        }
        Intent i = new Intent(getActivity(), PlayingNow.class);
        i.putExtra("type", "songs");
        i.putParcelableArrayListExtra("data", sendSongsArrayToPlayNow);
        i.putExtra("position", pos);
        startActivity(i);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            cu = MainActivity.getMusicCursorFromMainActivity();
            adapt = new SongListViewAdapter(v.getContext(), cu);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lv.setAdapter(adapt);
        }

    }
}
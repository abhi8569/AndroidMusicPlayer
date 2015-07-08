package com.abhi8569.musicplayer;

/**
 * Created by abishek on 07-07-2015.
 */
import com.abhi8569.musicplayer.MainActivity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by hp1 on 21-01-2015.
 */
public class SongsTab extends Fragment {

    ListView lv;
    Cursor cu;
    View v;
    SongListViewAdapter adapt;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_songs_tab, container, false);

        lv = (ListView) v.findViewById(R.id.songs_tab_listView);
        new MyTask().execute();

        return v;
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            adapt = new SongListViewAdapter(v.getContext(), MainActivity.getMusicCursorFromMainActivity());
            return null;
        }

        @Override
        protected void  onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lv.setAdapter(adapt);
        }

    }
}
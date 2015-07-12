package com.abhi8569.musicplayer;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by abishek on 12-07-2015.
 */
public class BackgroundMusicServices extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    //song list
    private ArrayList<SongInformation> songs;
    //current position
    private int songPosn;
    private final IBinder musicBind = new MusicBinder();

    public void onCreate() {
        //create the service
        super.onCreate();
        //initialize position
        songPosn = 0;
        //create player
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void setIncommingData(ArrayList<SongInformation> theSongs,int position){
        songs=theSongs;
        songPosn=position;
    }

    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    public class MusicBinder extends Binder {
        BackgroundMusicServices getService() {
            return BackgroundMusicServices.this;
        }
    }

    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void playSong(){
        player.reset();
        PlayingNow pn=new PlayingNow();
        //get song
        SongInformation playSong = songs.get(songPosn);
        //get id
        long currSong = Long.parseLong(playSong.get_id());
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try {
            player.setDataSource(getApplicationContext(), trackUri);
            pn.setImageBackground(playSong.getAlbumID());
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    public void playPrev(){
        songPosn--;
        if(songPosn<0) songPosn=songs.size()-1;
        playSong();
    }

    //skip to next
    public void playNext(){
        songPosn++;
        if(songPosn>=songs.size()) songPosn=0;
        playSong();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //start playback
        mp.start();
    }
}

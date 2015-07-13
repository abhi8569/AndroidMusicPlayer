package com.abhi8569.musicplayer;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.os.Handler;


public class PlayingNow extends ActionBarActivity {

    Handler handler=new Handler();
    ImageView albumArtNowPlaying;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    int albumartID = R.drawable.speaker;
    CircularSeekBar c;
    ArrayList<SongInformation> receivedList;
    NowPlayingQueueAdapter queueAdapt = null;

    //Music Control
    ImageButton nextTrack, previousTrack, playButton;
    TextView titleView, albumArtView;

    //Services Init
    private BackgroundMusicServices musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;
    int songPosition = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_now);
        titleView = (TextView) findViewById(R.id.songName);
        albumArtView = (TextView) findViewById(R.id.artistAlbumName);
        receivedList = new ArrayList<SongInformation>();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        albumArtNowPlaying = (ImageView) findViewById(R.id.albumArtNowPlaying);
        albumArtNowPlaying.setImageBitmap(getRefelection(BitmapFactory.decodeResource(getResources(), albumartID)));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout_PlayNow);
        mDrawerList = (ListView) findViewById(R.id.playnow_queuelist);
        //todo: Add adapter for listView

        Resources res = getResources();
        if (getIntent().getExtras() != null) {
            Bundle receivedData = getIntent().getExtras();
            songPosition = receivedData.getInt("position");
            receivedList = receivedData.getParcelableArrayList("data");
            queueAdapt = new NowPlayingQueueAdapter(this, receivedList, res);
            mDrawerList.setAdapter(queueAdapt);
        }

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PlayingNow.this, "POsition is " + Integer.toString(position), Toast.LENGTH_LONG).show();
                musicSrv.setSong(position);
                musicSrv.playSong();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //Music Control
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nextTrack = (ImageButton) findViewById(R.id.nextTrackButton);
        nextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playNext();
            }
        });
        previousTrack = (ImageButton) findViewById(R.id.previousTrackButton);
        previousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPrev();
            }
        });
        c = (CircularSeekBar) findViewById(R.id.cBar);
        c.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });

    }

    Runnable updateTextRunnable = new Runnable() {
        public void run() {
            try {
                titleView.setText(musicSrv.getTitle());
                albumArtView.setText(musicSrv.getArtist() + " | "+musicSrv.getAlbum());
                setImageBackground(musicSrv.getAlbumID());
            }
            catch (Exception e)
            {
                Log.e("Music PLayer",e.getMessage());
            }
            handler.postDelayed(this, 500);
        }
    };

    public void setSongInformation(String _title, String _artist, String _album) {
        titleView.setText(_title);
        albumArtView.setText(_artist + " | " + _album);
    }

    public void setImageBackground(int _albumID) {
        albumArtNowPlaying.setImageBitmap(getRefelection(SongManager.getAlbumArt(PlayingNow.this, _albumID)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null) {
            playIntent = new Intent(this, BackgroundMusicServices.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(updateTextRunnable);
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundMusicServices.MusicBinder binder = (BackgroundMusicServices.MusicBinder) service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setIncommingData(receivedList, songPosition);
            musicBound = true;
            musicSrv.setSong(songPosition);
            musicSrv.playSong();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public Bitmap getRefelection(Bitmap image) {
        // The gap we want between the reflection and the original image
        final int reflectionGap = 0;

        // Get your bitmap from drawable folder
        Bitmap originalImage = image;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

  /*Create a Bitmap with the flip matix applied to it.
   We only want the bottom half of the image*/

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);

        // Create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);
        // Create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // Draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        //Draw the reflection Image
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        // Create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x99ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        // Set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        if (originalImage != null && originalImage.isRecycled()) {
            originalImage.recycle();
            originalImage = null;
        }
        if (reflectionImage != null && reflectionImage.isRecycled()) {
            reflectionImage.recycle();
            reflectionImage = null;
        }
        return bitmapWithReflection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playing_now, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.queue_drawer_opener) {
            if (mDrawerLayout.isDrawerOpen(Gravity.END))
                mDrawerLayout.closeDrawer(Gravity.START);
            else
                mDrawerLayout.openDrawer(Gravity.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

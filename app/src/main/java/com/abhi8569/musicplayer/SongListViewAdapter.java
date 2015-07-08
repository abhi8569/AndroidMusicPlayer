package com.abhi8569.musicplayer;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.graphics.BitmapFactory.*;

/**
 * Created by abishek on 06-07-2015.
 */
public class SongListViewAdapter extends CursorAdapter {
    int albumartID = R.drawable.queen;

    public SongListViewAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.song_listview_layout, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView textView = (TextView) view.findViewById(R.id.song_lisyview_textView);
        ImageView songImage = (ImageView) view.findViewById(R.id.song_listview_imageView);
        // Extract properties from cursor
        String title = cursor.getString(1);
        long albumID = cursor.getInt(0);

        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        Uri albumArtUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumID);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), albumArtUri);
        } catch (Exception exception) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.search);
        }


        // Populate fields with extracted properties
        textView.setText(title);
        songImage.setImageBitmap(bitmap);
    }
}

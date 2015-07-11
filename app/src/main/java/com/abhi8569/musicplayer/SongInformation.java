package com.abhi8569.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.InputStream;

/**
 * Created by abishek on 06-07-2015.
 */
public class SongInformation implements Parcelable {

    private int albumID;
    private String queryTitle;
    private String queryAlbum;
    private String queryArtist;
    private int queryDuration;
    private String filepath;
    private String _id;


    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public SongInformation(Cursor cursor) {
        albumID=cursor.getInt(0);
        queryTitle = cursor.getString(1);
        queryArtist = cursor.getString(2);

        queryAlbum = cursor.getString(3);
        queryDuration = cursor.getInt(4);
        filepath = cursor.getString(5);
        _id = cursor.getString(6);

    }
    public String getQueryAlbum() {
        return queryAlbum;
    }

    public void setQueryAlbum(String queryAlbum) {
        this.queryAlbum = queryAlbum;
    }

    public String getQueryArtist() {
        return queryArtist;
    }

    public void setQueryArtist(String queryArtist) {
        this.queryArtist = queryArtist;
    }

    public int getQueryDuration() {
        return queryDuration;
    }

    public void setQueryDuration(int queryDuration) {
        this.queryDuration = queryDuration;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public String getQueryTitle() {
        return queryTitle;
    }

    public void setQueryTtile(String queryTitle) {
        this.queryTitle = queryTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.albumID);
        dest.writeString(this.queryTitle);
        dest.writeString(this.queryAlbum);
        dest.writeString(this.queryArtist);
        dest.writeInt(this.queryDuration);
        dest.writeString(this.filepath);
        dest.writeString(this._id);
    }

    protected SongInformation(Parcel in) {
        this.albumID = in.readInt();
        this.queryTitle = in.readString();
        this.queryAlbum = in.readString();
        this.queryArtist = in.readString();
        this.queryDuration = in.readInt();
        this.filepath = in.readString();
        this._id = in.readString();
    }

    public static final Parcelable.Creator<SongInformation> CREATOR = new Parcelable.Creator<SongInformation>() {
        public SongInformation createFromParcel(Parcel source) {
            return new SongInformation(source);
        }

        public SongInformation[] newArray(int size) {
            return new SongInformation[size];
        }
    };
}

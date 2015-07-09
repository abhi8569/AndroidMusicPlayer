package com.abhi8569.musicplayer;

import android.graphics.Bitmap;

/**
 * Created by abishek on 09-07-2015.
 */
public class AlbumArt {

    Bitmap image;
    long albumID;

    public AlbumArt(Bitmap image, long albumID) {
        this.image = image;
        this.albumID = albumID;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }
}

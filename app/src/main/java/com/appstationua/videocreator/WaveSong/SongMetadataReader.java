package com.appstationua.videocreator.WaveSong;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.HashMap;

public class SongMetadataReader {
    private Uri GENRES_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    private Activity mActivity = null;
    private String mFilename = "";
    public String mTitle = "";
    public String mArtist = "";
    private String mAlbum = "";
    private String mGenre = "";
    private int mYear = -1;

    public SongMetadataReader(Activity activity, String filename) {
        mActivity = activity;
        mFilename = filename;
        mTitle = getBasename(filename);
        try {
            ReadMetadata();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReadMetadata() {
        String GENRE_ID = MediaStore.Audio.Genres._ID;
        String GENRE_NAME = MediaStore.Audio.Genres.NAME;
        String AUDIO_ID = MediaStore.Audio.Media._ID;

        // Get a map from genre ids to names
        HashMap<String, String> genreIdMap = new HashMap<String, String>();
        Cursor c = mActivity.managedQuery(
                GENRES_URI,
                new String[]{GENRE_ID, GENRE_NAME},
                null, null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            genreIdMap.put(c.getString(0), c.getString(1));
        }
        mGenre = "";
        for (String genreId : genreIdMap.keySet()) {
            c = mActivity.managedQuery(
                    makeGenreUri(genreId),
                    new String[]{MediaStore.Audio.Media.DATA},
                    MediaStore.Audio.Media.DATA + " LIKE \"" + mFilename + "\"",
                    null, null);
            if (c.getCount() != 0) {
                mGenre = genreIdMap.get(genreId);
                break;
            }
            c = null;
        }

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(mFilename);
        c = mActivity.managedQuery(
                uri,
                new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.DATA},
                MediaStore.Audio.Media.DATA + " LIKE \"" + mFilename + "\"",
                null, null);
        if (c.getCount() == 0) {
            mTitle = getBasename(mFilename);
            mArtist = "";
            mAlbum = "";
            mYear = -1;
            return;
        }
        c.moveToFirst();
        mTitle = getStringFromColumn(c, MediaStore.Audio.Media.TITLE);
        if (mTitle == null || mTitle.length() == 0) {
            mTitle = getBasename(mFilename);
        }
        mArtist = getStringFromColumn(c, MediaStore.Audio.Media.ARTIST);
        mAlbum = getStringFromColumn(c, MediaStore.Audio.Media.ALBUM);
        mYear = getIntegerFromColumn(c, MediaStore.Audio.Media.YEAR);
    }

    private Uri makeGenreUri(String genreId) {
        String CONTENTDIR = MediaStore.Audio.Genres.Members.CONTENT_DIRECTORY;
        return Uri.parse(
                GENRES_URI.toString() + "/" + genreId + "/" + CONTENTDIR);
    }

    private String getStringFromColumn(Cursor c, String columnName) {
        int index = c.getColumnIndexOrThrow(columnName);
        String value = c.getString(index);
        if (value != null && value.length() > 0) {
            return value;
        } else {
            return null;
        }
    }

    private int getIntegerFromColumn(Cursor c, String columnName) {
        int index = c.getColumnIndexOrThrow(columnName);
        Integer value = c.getInt(index);
        if (value != null) {
            return value;
        } else {
            return -1;
        }
    }

    private String getBasename(String filename) {
        return filename.substring(filename.lastIndexOf('/') + 1,
                filename.lastIndexOf('.'));
    }
}
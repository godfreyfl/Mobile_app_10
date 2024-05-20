package com.example.practice_10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "audiotracks";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ALBUM_NAME = "album_name";
    private static final String COLUMN_AUTHOR_NAME = "author_name";

    public DatabaseHelper(Context context) {
        // конструктор суперкласса
        super(context, "tracks_lib", null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ALBUM_NAME + " TEXT, " +
                COLUMN_AUTHOR_NAME + " TEXT)";
        db.execSQL(createTable);
    }

    public boolean addTrack(Track track) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, track.getName());
        cv.put(COLUMN_ALBUM_NAME, track.getAlbum_name());
        cv.put(COLUMN_AUTHOR_NAME, track.getAuthor_name());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }

    public boolean deleteTrack(String track) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_NAME + " = ?",
                new String[]{track});
        db.close();
        return result > 0;
    }

    public Track findTrack(String track) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME, COLUMN_ALBUM_NAME, COLUMN_AUTHOR_NAME},
                COLUMN_NAME + " = ?", new String[]{track}, null,
                null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Track track_temp = new Track(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));
            cursor.close();
            db.close();
            return track_temp;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public List<Track> getAllContacts() {
        List<Track> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Track track_temp = new Track(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2));
                contactList.add(track_temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public boolean updateTrack(String oldAlbumName, String newName, String newAlbumName, String newAuthorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newName);
        cv.put(COLUMN_ALBUM_NAME, newAlbumName);
        cv.put(COLUMN_AUTHOR_NAME, newAuthorName);
        // Обновляем запись, где номер телефона равен oldPhone
        int result = db.update(TABLE_NAME, cv, COLUMN_ALBUM_NAME + " = ?", new String[]{oldAlbumName});
        db.close();
        return result > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


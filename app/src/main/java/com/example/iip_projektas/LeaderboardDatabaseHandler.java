package com.example.iip_projektas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LeaderboardDatabaseHandler extends  SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "leaderboard.db";

    private static String SCORES_TABLE_NAME = "scores";
    private final static String KEY_NAME = "name";
    private final static String KEY_SCORE = "score";

    public LeaderboardDatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + SCORES_TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,score INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + SCORES_TABLE_NAME);
        onCreate(db);
    }

    public boolean addEntry(String name, String score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SCORE, score);
        long result = db.insert(SCORES_TABLE_NAME,null,contentValues);
        if(result == -1) return false;
        else return true;
    }

    public Cursor getEntries()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SCORES_TABLE_NAME+" order by "+KEY_SCORE+" desc",null);
        return res;
    }

    public Integer deleteEntries()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCORES_TABLE_NAME,"",null);
    }
}

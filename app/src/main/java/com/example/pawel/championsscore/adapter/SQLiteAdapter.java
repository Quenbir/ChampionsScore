package com.example.pawel.championsscore.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pawel.championsscore.model.DBContract;

public class SQLiteAdapter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "championsscore_database";

    public SQLiteAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.Competition.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.Round.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.Team.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.Match.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.Player.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.Event.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.MatchEvents.CREATE_TABLE);
        sqLiteDatabase.execSQL(DBContract.MatchPlayer.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Round.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Competition.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.MatchEvents.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.MatchPlayer.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Team.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Match.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Player.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Event.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}

package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Competition;


/**
 * Created by Mateusz on 31.05.2017.
 */

public class CompetitionDAO {
    private final Activity context;
    private final SQLiteDatabase database;

    public CompetitionDAO(Activity context) {
        this.context = context;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Competition competition) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Competition._ID, competition.getId());
        values.put(DBContract.Competition.COLUMN_NAME, competition.getName());
        values.put(DBContract.Competition.COLUMN_FLAG_URL, competition.getFlagUrl());
        database.insertWithOnConflict(DBContract.Competition.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public Competition find(int id) {

        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Competition._ID,
                DBContract.Competition.COLUMN_NAME,
                DBContract.Competition.COLUMN_FLAG_URL
        };

        String selection =
                DBContract.Competition._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query(
                DBContract.Competition.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        if (cursor.moveToFirst()) {
            Competition competition = new Competition();
            competition.setId(Integer.parseInt(cursor.getString(0)));
            competition.setName(cursor.getString(1));
            competition.setFlagUrl(cursor.getString(2));

            return competition;
        }
        return null;
    }
}

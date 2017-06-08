package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Team;

/**
 * Created by Mateusz on 03.06.2017.
 */

public class TeamDAO {

    private final Activity context;
    private final SQLiteDatabase database;

    public TeamDAO(Activity context) {
        this.context = context;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Team team) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Team._ID, team.getId());
        values.put(DBContract.Team.COLUMN_NAME, team.getName());
        database.insertWithOnConflict(DBContract.Team.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public Team find(int teamId) {
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Team._ID,
                DBContract.Team.COLUMN_NAME
        };

        String selection =
                DBContract.Team._ID + " = ?";
        String[] selectionArgs = {String.valueOf(teamId)};

        Cursor cursor = database.query(
                DBContract.Team.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            Team team = new Team();
            team.setId(Integer.parseInt(cursor.getString(0)));
            team.setName(cursor.getString(1));
            return team;
        }
        return null;
    }
}

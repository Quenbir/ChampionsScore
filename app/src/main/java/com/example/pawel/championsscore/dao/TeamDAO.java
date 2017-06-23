package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Team;

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
                DBContract.Team.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Team team = new Team();
            team.setId(Integer.parseInt(cursor.getString(0)));
            team.setName(cursor.getString(1));
            cursor.close();
            return team;
        }
        cursor.close();
        return null;
    }
}

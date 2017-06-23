package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Round;

import java.util.ArrayList;
import java.util.List;


public class RoundDAO {


    private final Activity context;
    private final SQLiteDatabase database;
    private final CompetitionDAO competitionDAO;

    public RoundDAO(Activity context, CompetitionDAO competitionDAO) {
        this.context = context;
        this.competitionDAO = competitionDAO;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Round round) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Round._ID, round.getId());
        values.put(DBContract.Round.COLUMN_NAME, round.getName());
        values.put(DBContract.Round.COLUMN_COMPETITION_ID, round.getCompetition().getId());
        database.insertWithOnConflict(DBContract.Round.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public List<Round> findByCompetitionId(int competitionId) {
        List<Round> rounds = new ArrayList<>();
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Round._ID,
                DBContract.Round.COLUMN_NAME,
                DBContract.Round.COLUMN_COMPETITION_ID
        };

        String selection =
                DBContract.Round.COLUMN_COMPETITION_ID + " = ?";
        String[] selectionArgs = {String.valueOf(competitionId)};

        Cursor cursor = database.query(
                DBContract.Round.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Round round = new Round();
                round.setId(Long.parseLong(cursor.getString(0)));
                round.setName(cursor.getString(1));
                round.setCompetition(competitionDAO.find(Integer.parseInt(cursor.getString(2))));
                rounds.add(round);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rounds;
    }

    public Round find(int roundId) {
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Round._ID,
                DBContract.Round.COLUMN_NAME,
                DBContract.Round.COLUMN_COMPETITION_ID
        };

        String selection =
                DBContract.Round._ID + " = ?";
        String[] selectionArgs = {String.valueOf(roundId)};

        Cursor cursor = database.query(
                DBContract.Round.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Round round = new Round();
            round.setId(Long.parseLong(cursor.getString(0)));
            round.setName(cursor.getString(1));
            round.setCompetition(competitionDAO.find(Integer.parseInt(cursor.getString(2))));
            cursor.close();
            return round;
        }
        cursor.close();
        return null;
    }
}

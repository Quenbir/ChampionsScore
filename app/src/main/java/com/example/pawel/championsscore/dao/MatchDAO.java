package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Match;

import java.util.ArrayList;
import java.util.List;


public class MatchDAO {

    private final Activity context;
    private final SQLiteDatabase database;
    private final RoundDAO roundDAO;
    private final TeamDAO teamDAO;

    public MatchDAO(Activity context, RoundDAO roundDAO, TeamDAO teamDAO) {
        this.context = context;
        this.roundDAO = roundDAO;
        this.teamDAO = teamDAO;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Match match) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Match._ID, match.getId());
        values.put(DBContract.Match.COLUMN_AWAY_GOAL, match.getAwayGoals());
        values.put(DBContract.Match.COLUMN_HOME_GOAL, match.getHomeGoals());
        values.put(DBContract.Match.COLUMN_AWAY_TEAM_ID, match.getAwayTeam().getId());
        values.put(DBContract.Match.COLUMN_HOME_TEAM_ID, match.getHomeTeam().getId());
        values.put(DBContract.Match.COLUMN_ROUND_ID, match.getRound().getId());
        values.put(DBContract.Match.COLUMN_DATE, match.getDate());
        database.insertWithOnConflict(DBContract.Match.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public List<Match> findByRoundId(int roundId) {
        List<Match> matches = new ArrayList<>();
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Match._ID,
                DBContract.Match.COLUMN_AWAY_GOAL,
                DBContract.Match.COLUMN_HOME_GOAL,
                DBContract.Match.COLUMN_AWAY_TEAM_ID,
                DBContract.Match.COLUMN_HOME_TEAM_ID,
                DBContract.Match.COLUMN_ROUND_ID,
                DBContract.Match.COLUMN_DATE
        };

        String selection =
                DBContract.Match.COLUMN_ROUND_ID + " = ?";
        String[] selectionArgs = {String.valueOf(roundId)};

        Cursor cursor = database.query(
                DBContract.Match.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(Long.parseLong(cursor.getString(0)));
                match.setAwayGoals(Integer.parseInt(cursor.getString(1)));
                match.setHomeGoals(Integer.parseInt(cursor.getString(2)));
                match.setAwayTeam(teamDAO.find(Integer.parseInt(cursor.getString(3))));
                match.setHomeTeam(teamDAO.find(Integer.parseInt(cursor.getString(4))));
                match.setRound(roundDAO.find(Integer.parseInt(cursor.getString(5))));
                match.setDate(Long.parseLong(cursor.getString(6)));
                matches.add(match);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return matches;
    }


}

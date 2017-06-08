package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Match;
import com.example.pawel.championsscore.model.webservice.Player;
import com.example.pawel.championsscore.model.webservice.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 03.06.2017.
 */

public class MatchDAO {

    private final Activity context;
    private final SQLiteDatabase database;
    private final RoundDao roundDAO;
    private final TeamDAO teamDAO;

    public MatchDAO(Activity context, RoundDao roundDao, TeamDAO teamDAO) {
        this.context = context;
        this.roundDAO = roundDao;
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
                DBContract.Match.COLUMN_ROUND_ID
        };

        String selection =
                DBContract.Match.COLUMN_ROUND_ID + " = ?";
        String[] selectionArgs = {String.valueOf(roundId)};

        Cursor cursor = database.query(
                DBContract.Match.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Match match = new Match();
                match.setId(Long.parseLong(cursor.getString(0)));
                match.setAwayGoals(Integer.parseInt(cursor.getString(1)));
                match.setHomeGoals(Integer.parseInt(cursor.getString(2)));
                match.setAwayTeam(teamDAO.find(Integer.parseInt(cursor.getString(3))));
                match.setHomeTeam(teamDAO.find(Integer.parseInt(cursor.getString(4))));
                match.setRound(roundDAO.find(Integer.parseInt(cursor.getString(4))));
                // Adding contact to list
                matches.add(match);
            } while (cursor.moveToNext());
        }
        return matches;
    }


}

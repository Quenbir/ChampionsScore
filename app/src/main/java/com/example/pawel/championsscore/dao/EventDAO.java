package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Event;
import com.example.pawel.championsscore.model.webservice.MatchEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 07.06.2017.
 */

public class EventDAO {

    private final Activity context;
    private final SQLiteDatabase database;
    private final PlayerDAO playerDAO;
    private final TeamDAO teamDAO;

    public EventDAO(Activity context, PlayerDAO playerDAO, TeamDAO teamDAO) {
        this.context = context;
        this.playerDAO = playerDAO;
        this.teamDAO = teamDAO;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Event event) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Event._ID, event.getId());
        values.put(DBContract.Event.COLUMN_CARD_TYPE, event.getCardType());
        values.put(DBContract.Event.COLUMN_HAPPENED_AT, event.getHappenedAt());
        values.put(DBContract.Event.COLUMN_PENALTY, event.getPenalty());
        if (event.getPlayer() != null) {
            playerDAO.save(event.getPlayer());
            values.put(DBContract.Event.COLUMN_PLAYER_ID, event.getPlayer().getId());
        }
        values.put(DBContract.Event.COLUMN_OWN_GOAL, event.getOwnGoal());
        if (event.getPlayerOff() != null) {
            playerDAO.save(event.getPlayerOff());
            values.put(DBContract.Event.COLUMN_PLAYER_OFF_ID, event.getPlayerOff().getId());
        }
        if (event.getPlayerOn() != null) {
            playerDAO.save(event.getPlayerOn());
            values.put(DBContract.Event.COLUMN_PLAYER_ON_ID, event.getPlayerOn().getId());
        }
        if (event.getScoringPlayer() != null) {
            playerDAO.save(event.getScoringPlayer());
            values.put(DBContract.Event.COLUMN_SCORING_PLAYER_ID, event.getScoringPlayer().getId());
        }
        values.put(DBContract.Event.COLUMN_SIDE, event.getSide());
        values.put(DBContract.Event.COLUMN_TIME, event.getTime());
        values.put(DBContract.Event.COLUMN_SCORING_SIDE, event.getScoringSide());
        values.put(DBContract.Event.COLUMN_MATCH_ID, event.getMatchId());
        values.put(DBContract.Event.COLUMN_TYPE, event.getType());
        database.insertWithOnConflict(DBContract.Event.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public void save(List<Event> events) {
        for (Event event : events) {
            save(event);
        }
    }

    public List<Event> findByMatchId(int matchId) {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Event._ID,
                DBContract.Event.COLUMN_CARD_TYPE,
                DBContract.Event.COLUMN_HAPPENED_AT,
                DBContract.Event.COLUMN_PENALTY,
                DBContract.Event.COLUMN_PLAYER_ID,
                DBContract.Event.COLUMN_OWN_GOAL,
                DBContract.Event.COLUMN_PLAYER_OFF_ID,
                DBContract.Event.COLUMN_PLAYER_ON_ID,
                DBContract.Event.COLUMN_SCORING_PLAYER_ID,
                DBContract.Event.COLUMN_SIDE,
                DBContract.Event.COLUMN_TIME,
                DBContract.Event.COLUMN_SCORING_SIDE,
                DBContract.Event.COLUMN_MATCH_ID,
                DBContract.Event.COLUMN_TYPE

        };

        String selection =
                DBContract.Event.COLUMN_MATCH_ID + " = ?";
        String[] selectionArgs = {String.valueOf(matchId)};

        Cursor cursor = database.query(
                DBContract.Event.TABLE_NAME,     // The table to query
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
                Event event = new Event();
                event.setId(Long.parseLong(cursor.getString(0)));
                event.setCardType(cursor.getString(1));
                event.setHappenedAt(Long.parseLong(cursor.getString(2)));
                event.setPenalty(Boolean.parseBoolean(cursor.getString(3)));
                event.setPlayer(cursor.getString(4) != null ? playerDAO.find(Integer.parseInt(cursor.getString(4))) : null);
                event.setOwnGoal(Boolean.parseBoolean(cursor.getString(5)));
                event.setPlayerOff(cursor.getString(6) != null ? playerDAO.find(Integer.parseInt(cursor.getString(6))) : null);
                event.setPlayerOn(cursor.getString(7) != null ? playerDAO.find(Integer.parseInt(cursor.getString(7))) : null);
                event.setScoringPlayer(cursor.getString(8) != null ? playerDAO.find(Integer.parseInt(cursor.getString(8))) : null);
                event.setSide(cursor.getString(9));
                event.setTime(cursor.getString(10));
                event.setScoringSide(cursor.getString(11));
                event.setMatchId(Integer.parseInt(cursor.getString(12)));
                event.setType(cursor.getString(13));
                events.add(event);
            } while (cursor.moveToNext());
        }
        return events;
    }

    public void saveMatchEvents(MatchEvents matchEvents) {
        ContentValues values = new ContentValues();
        values.put(DBContract.MatchEvents._ID, matchEvents.getId());
        values.put(DBContract.MatchEvents.COLUMN_AWAY_GOALS, matchEvents.getAwayGoals());
        values.put(DBContract.MatchEvents.COLUMN_AWAY_TEAM_ID, matchEvents.getAwayTeam().getId());
        values.put(DBContract.MatchEvents.COLUMN_CURRENT_STATE_START, matchEvents.getCurrentStateStart());
        values.put(DBContract.MatchEvents.COLUMN_GO_TO_EXTRA_TIME, matchEvents.getGoToExtraTime());
        values.put(DBContract.MatchEvents.COLUMN_HOME_GOALS, matchEvents.getHomeGoals());
        values.put(DBContract.MatchEvents.COLUMN_HOME_TEAM_ID, matchEvents.getHomeTeam().getId());
        values.put(DBContract.MatchEvents.COLUMN_IS_RESULT, matchEvents.getResult());
        values.put(DBContract.MatchEvents.COLUMN_START, matchEvents.getStart());
        database.insertWithOnConflict(DBContract.MatchEvents.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        playerDAO.saveMatchPlayer((int) matchEvents.getId(), matchEvents.getHomeTeam().getId(), matchEvents.getHomePlayers());
        playerDAO.saveMatchPlayer((int) matchEvents.getId(), matchEvents.getAwayTeam().getId(), matchEvents.getAwayPlayers());
        save(matchEvents.getEvents());
    }

    public MatchEvents findMatchEvents(int matchId) {

        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.MatchEvents._ID,
                DBContract.MatchEvents.COLUMN_AWAY_GOALS,
                DBContract.MatchEvents.COLUMN_AWAY_TEAM_ID,
                DBContract.MatchEvents.COLUMN_CURRENT_STATE_START,
                DBContract.MatchEvents.COLUMN_GO_TO_EXTRA_TIME,
                DBContract.MatchEvents.COLUMN_HOME_GOALS,
                DBContract.MatchEvents.COLUMN_HOME_TEAM_ID,
                DBContract.MatchEvents.COLUMN_IS_RESULT,
                DBContract.MatchEvents.COLUMN_START,
        };

        String selection =
                DBContract.MatchEvents._ID + " = ?";
        String[] selectionArgs = {String.valueOf(matchId)};

        Cursor cursor = database.query(
                DBContract.MatchEvents.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        if (cursor.moveToFirst()) {
            MatchEvents matchEvents = new MatchEvents();
            matchEvents.setId(Integer.parseInt(cursor.getString(0)));
            matchEvents.setAwayGoals(Integer.parseInt(cursor.getString(1)));
            matchEvents.setAwayTeam(teamDAO.find(Integer.parseInt(cursor.getString(2))));
            matchEvents.setCurrentStateStart(Long.parseLong(cursor.getString(3)));
            matchEvents.setGoToExtraTime(Boolean.parseBoolean(cursor.getString(4)));
            matchEvents.setHomeGoals(Integer.parseInt(cursor.getString(5)));
            matchEvents.setHomeTeam(teamDAO.find(Integer.parseInt(cursor.getString(6))));
            matchEvents.setResult(Boolean.parseBoolean(cursor.getString(7)));
            matchEvents.setStart(cursor.getString(8) != null ? Long.parseLong(cursor.getString(8)) : 0);
            matchEvents.setEvents(findByMatchId(matchId));
            matchEvents.setHomePlayers(playerDAO.findByMatchAndTeam(matchId, Integer.parseInt(cursor.getString(6))));
            matchEvents.setAwayPlayers(playerDAO.findByMatchAndTeam(matchId, Integer.parseInt(cursor.getString(2))));
            return matchEvents;
        }
        return null;
    }
}

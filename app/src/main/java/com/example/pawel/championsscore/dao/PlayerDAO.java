package com.example.pawel.championsscore.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pawel.championsscore.adapter.SQLiteAdapter;
import com.example.pawel.championsscore.model.DBContract;
import com.example.pawel.championsscore.model.webservice.Player;

import java.util.ArrayList;
import java.util.List;


public class PlayerDAO {

    private final Activity context;
    private final SQLiteDatabase database;


    public PlayerDAO(Activity context) {
        this.context = context;
        database = new SQLiteAdapter(context).getWritableDatabase();
    }

    public void save(Player player) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Player._ID, player.getId());
        values.put(DBContract.Player.COLUMN_NAME, player.getName());
        values.put(DBContract.Player.COLUMN_NUMBER, player.getNumber());
        values.put(DBContract.Player.COLUMN_SHORT_NAME, player.getShortName());
        values.put(DBContract.Player.COLUMN_SQUAD_ROLE, player.getSquadRole());
        database.insertWithOnConflict(DBContract.Player.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

    }

    public void save(List<Player> players) {
        for (Player player : players) {
            save(player);
        }
    }

    public Player find(int id) {

        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.Player._ID,
                DBContract.Player.COLUMN_NAME,
                DBContract.Player.COLUMN_NUMBER,
                DBContract.Player.COLUMN_SHORT_NAME,
                DBContract.Player.COLUMN_SQUAD_ROLE
        };

        String selection =
                DBContract.Player._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query(
                DBContract.Player.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Player player = new Player();
            player.setId(Integer.parseInt(cursor.getString(0)));
            player.setName(cursor.getString(1));
            player.setNumber(cursor.getString(2) != null ? Integer.parseInt(cursor.getString(2)) : null);
            player.setShortName(cursor.getString(3));
            player.setSquadRole(cursor.getString(4));
            cursor.close();
            return player;
        }
        cursor.close();
        return null;
    }

    public void saveMatchPlayer(int matchId, int teamId, List<Player> players) {
        for (Player player : players) {
            ContentValues values = new ContentValues();
            values.put(DBContract.MatchPlayer.COLUMN_MATCH_ID, matchId);
            values.put(DBContract.MatchPlayer.COLUMN_PLAYER_ID, player.getId());
            values.put(DBContract.MatchPlayer.COLUMN_TEAM_ID, teamId);
            database.insertWithOnConflict(DBContract.MatchPlayer.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

    }

    public List<Player> findByMatchAndTeam(int matchId, int teamId) {
        List<Player> players = new ArrayList<>();
        SQLiteDatabase database = new SQLiteAdapter(context).getReadableDatabase();

        String[] projection = {
                DBContract.MatchPlayer.COLUMN_MATCH_ID,
                DBContract.MatchPlayer.COLUMN_PLAYER_ID,
                DBContract.MatchPlayer.COLUMN_TEAM_ID
        };

        String selection =
                DBContract.MatchPlayer.COLUMN_MATCH_ID + " = ? AND " + DBContract.MatchPlayer.COLUMN_TEAM_ID + " = ?";
        String[] selectionArgs = {String.valueOf(matchId), String.valueOf(teamId)};

        Cursor cursor = database.query(
                DBContract.MatchPlayer.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Player player = find(Integer.parseInt(cursor.getString(1)));
                players.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return players;
    }
}

package com.example.pawel.championsscore.model;

import android.provider.BaseColumns;

/**
 * Created by Mateusz on 31.05.2017.
 */

public final class DBContract {
    private DBContract() {
    }

    public static class Competition implements BaseColumns {
        public static final String TABLE_NAME = "competition";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FLAG_URL = "flag_url";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_FLAG_URL + " TEXT)";
    }

    public static class Round implements BaseColumns {
        public static final String TABLE_NAME = "round";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COMPETITION_ID = "competition_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_COMPETITION_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_COMPETITION_ID + ") REFERENCES " +
                Competition.TABLE_NAME + "(" + Competition._ID + "))";
    }

    public static class Team implements BaseColumns {
        public static final String TABLE_NAME = "team";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT)";
    }

    public static class Match implements BaseColumns {
        public static final String TABLE_NAME = "match";
        public static final String _ID = "id";
        public static final String COLUMN_HOME_GOAL = "home_goal";
        public static final String COLUMN_AWAY_GOAL = "away_goal";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ROUND_ID = "round_id";
        public static final String COLUMN_HOME_TEAM_ID = "home_team_id";
        public static final String COLUMN_AWAY_TEAM_ID = "away_team_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_HOME_GOAL + " INTEGER, " +
                COLUMN_AWAY_GOAL + " INTEGER, " +
                COLUMN_DATE + " LONG, " +
                COLUMN_ROUND_ID + " INTEGER, " +
                COLUMN_HOME_TEAM_ID + " INTEGER, " +
                COLUMN_AWAY_TEAM_ID + " INTEGER, " +

                "FOREIGN KEY(" + COLUMN_ROUND_ID + ") REFERENCES " +
                Round.TABLE_NAME + "(" + Round._ID + ")" +
                "FOREIGN KEY(" + COLUMN_HOME_TEAM_ID + ") REFERENCES " +
                Team.TABLE_NAME + "(" + Team._ID + ")" +
                "FOREIGN KEY(" + COLUMN_AWAY_TEAM_ID + ") REFERENCES " +
                Team.TABLE_NAME + "(" + Team._ID + "))";
    }

    public static class Player implements BaseColumns {

        public static final String TABLE_NAME = "player";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SHORT_NAME = "short_name";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_SQUAD_ROLE = "squad_role";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SHORT_NAME + " TEXT, " +
                COLUMN_NUMBER + " INTEGER, " +
                COLUMN_SQUAD_ROLE + " TEXT)";
    }

    public static class Event implements BaseColumns {

        public static final String TABLE_NAME = "event";
        public static final String _ID = "id";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_SIDE = "side";
        public static final String COLUMN_CARD_TYPE = "card_type";
        public static final String COLUMN_PENALTY = "penalty";
        public static final String COLUMN_OWN_GOAL = "own_goal";
        public static final String COLUMN_SCORING_SIDE = "scoring_side";
        public static final String COLUMN_HAPPENED_AT = "happened_at";
        public static final String COLUMN_PLAYER_OFF_ID = "player_off_id";
        public static final String COLUMN_PLAYER_ON_ID = "player_on_id";
        public static final String COLUMN_PLAYER_ID = "player_id";
        public static final String COLUMN_SCORING_PLAYER_ID = "scoring_player_id";
        public static final String COLUMN_MATCH_ID = "match_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_SIDE + " TEXT, " +
                COLUMN_CARD_TYPE + " TEXT, " +
                COLUMN_PENALTY + " BOOLEAN, " +
                COLUMN_OWN_GOAL + " BOOLEAN, " +
                COLUMN_SCORING_SIDE + " TEXT, " +
                COLUMN_HAPPENED_AT + " LONG, " +
                COLUMN_PLAYER_OFF_ID + " INTEGER, " +
                COLUMN_PLAYER_ON_ID + " INTEGER, " +
                COLUMN_PLAYER_ID + " INTEGER, " +
                COLUMN_SCORING_PLAYER_ID + " INTEGER, " +
                COLUMN_MATCH_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PLAYER_OFF_ID + ") REFERENCES " +
                Player.TABLE_NAME + "(" + Player._ID + ")" +
                "FOREIGN KEY(" + COLUMN_PLAYER_ON_ID + ") REFERENCES " +
                Player.TABLE_NAME + "(" + Player._ID + ")" +
                "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES " +
                Player.TABLE_NAME + "(" + Player._ID + ")" +
                "FOREIGN KEY(" + COLUMN_SCORING_PLAYER_ID + ") REFERENCES " +
                Player.TABLE_NAME + "(" + Player._ID + "))";
    }

    public static class MatchEvents {

        public static final String TABLE_NAME = "match_events";
        public static final String _ID = "id";
        public static final String COLUMN_HOME_GOALS = "home_goals";
        public static final String COLUMN_AWAY_GOALS = "away_goals";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_CURRENT_STATE_START = "current_state_start";
        public static final String COLUMN_IS_RESULT = "is_result";
        public static final String COLUMN_GO_TO_EXTRA_TIME = "go_to_extra_time";
        public static final String COLUMN_HOME_TEAM_ID = "home_team_id";
        public static final String COLUMN_AWAY_TEAM_ID = "away_team_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_HOME_GOALS + " INTEGER, " +
                COLUMN_AWAY_GOALS + " INTEGER, " +
                COLUMN_START + " LONG, " +
                COLUMN_CURRENT_STATE_START + " LONG, " +
                COLUMN_IS_RESULT + " BOOLEAN, " +
                COLUMN_GO_TO_EXTRA_TIME + " BOOLEAN, " +
                COLUMN_HOME_TEAM_ID + " INTEGER, " +
                COLUMN_AWAY_TEAM_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_HOME_TEAM_ID + ") REFERENCES " +
                Team.TABLE_NAME + "(" + Team._ID + ")" +
                "FOREIGN KEY(" + COLUMN_AWAY_TEAM_ID + ") REFERENCES " +
                Team.TABLE_NAME + "(" + Team._ID + "))";
    }

    public static class MatchPlayer implements BaseColumns {
        public static final String TABLE_NAME = "match_player";
        public static final String _ID = "id";
        public static final String COLUMN_MATCH_ID = "match_id";
        public static final String COLUMN_PLAYER_ID = "player_id";
        public static final String COLUMN_TEAM_ID = "team_id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MATCH_ID + " INTEGER, " +
                COLUMN_PLAYER_ID + " INTEGER, " +
                COLUMN_TEAM_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PLAYER_ID + ") REFERENCES " +
                Player.TABLE_NAME + "(" + Player._ID + "))";


    }

}
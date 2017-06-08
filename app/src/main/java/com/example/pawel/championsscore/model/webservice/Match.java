package com.example.pawel.championsscore.model.webservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    @JsonProperty("dbid")
    private long id;
    private int homeGoals;
    private int awayGoals;
    @JsonProperty("start")
    private long date;
    private Team homeTeam;
    private Team awayTeam;
    private Round round;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", date=" + date +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", round=" + round +
                '}';
    }
}
package com.example.pawel.championsscore.model.webservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 04.06.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchEvents {
    @JsonProperty("dbid")
    private long id;
    private Team homeTeam;
    private Team awayTeam;
    private Integer homeGoals;
    private Integer awayGoals;
    private Long start;
    private Long currentStateStart;
    private Boolean isResult;
    private Boolean goToExtraTime;

    List<Player> homePlayers = new ArrayList<>();
    List<Player> awayPlayers = new ArrayList<>();
    @JsonProperty("matchevents")
    List<Event> events = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Integer awayGoals) {
        this.awayGoals = awayGoals;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getCurrentStateStart() {
        return currentStateStart;
    }

    public void setCurrentStateStart(Long currentStateStart) {
        this.currentStateStart = currentStateStart;
    }

    public Boolean getResult() {
        return isResult;
    }

    public void setResult(Boolean result) {
        isResult = result;
    }

    public Boolean getGoToExtraTime() {
        return goToExtraTime;
    }

    public void setGoToExtraTime(Boolean goToExtraTime) {
        this.goToExtraTime = goToExtraTime;
    }

    public List<Player> getHomePlayers() {
        return homePlayers;
    }

    public void setHomePlayers(List<Player> homePlayers) {
        this.homePlayers = homePlayers;
    }

    public List<Player> getAwayPlayers() {
        return awayPlayers;
    }

    public void setAwayPlayers(List<Player> awayPlayers) {
        this.awayPlayers = awayPlayers;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "MatchEvents{" +
                "id=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", homeGoals=" + homeGoals +
                ", awayGoals=" + awayGoals +
                ", start=" + start +
                ", currentStateStart=" + currentStateStart +
                ", isResult=" + isResult +
                ", goToExtraTime=" + goToExtraTime +
                ", homePlayers=" + homePlayers +
                ", awayPlayers=" + awayPlayers +
                ", events=" + events +
                '}';
    }
}
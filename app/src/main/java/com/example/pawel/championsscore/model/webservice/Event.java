package com.example.pawel.championsscore.model.webservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    @JsonProperty("dbid")
    private long id;
    @JsonProperty("matchTimeString")
    private String time;
    private String type;
    private String side;
    private Player playerOff;
    private Player playerOn;
    private String cardType;
    private Player player;
    private Player scoringPlayer;
    private Boolean penalty;
    private Boolean ownGoal;
    private String scoringSide;
    private Long happenedAt;
    private Integer matchId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Player getPlayerOff() {
        return playerOff;
    }

    public void setPlayerOff(Player playerOff) {
        this.playerOff = playerOff;
    }

    public Player getPlayerOn() {
        return playerOn;
    }

    public void setPlayerOn(Player playerOn) {
        this.playerOn = playerOn;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getScoringPlayer() {
        return scoringPlayer;
    }

    public void setScoringPlayer(Player scoringPlayer) {
        this.scoringPlayer = scoringPlayer;
    }

    public Boolean getPenalty() {
        return penalty;
    }

    public void setPenalty(Boolean penalty) {
        this.penalty = penalty;
    }

    public Boolean getOwnGoal() {
        return ownGoal;
    }

    public void setOwnGoal(Boolean ownGoal) {
        this.ownGoal = ownGoal;
    }

    public String getScoringSide() {
        return scoringSide;
    }

    public void setScoringSide(String scoringSide) {
        this.scoringSide = scoringSide;
    }

    public Long getHappenedAt() {
        return happenedAt;
    }

    public void setHappenedAt(Long happenedAt) {
        this.happenedAt = happenedAt;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", side='" + side + '\'' +
                ", playerOff=" + playerOff +
                ", playerOn=" + playerOn +
                ", cardType='" + cardType + '\'' +
                ", player=" + player +
                ", scoringPlayer=" + scoringPlayer +
                ", penalty=" + penalty +
                ", ownGoal=" + ownGoal +
                ", scoringSide='" + scoringSide + '\'' +
                ", happenedAt=" + happenedAt +
                ", matchId=" + matchId +
                '}';
    }
}

package com.example.pawel.championsscore.model;


public class MatchEvent {
    private String info;
    private int time;
    private int idTeam;
    private String type;

    public MatchEvent(String info, int time, int idTeam, String type) {
        this.info = info;
        this.time = time;
        this.idTeam = idTeam;
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

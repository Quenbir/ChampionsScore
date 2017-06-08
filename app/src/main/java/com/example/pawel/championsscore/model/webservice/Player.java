package com.example.pawel.championsscore.model.webservice;

import com.example.pawel.championsscore.model.PlayerInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mateusz on 04.06.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements PlayerInterface {
    @JsonProperty("dbid")
    private long id;
    private String name;
    private String shortName;
    private Integer number;
    private String squadRole;
    private Integer teamId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSquadRole() {
        return squadRole;
    }

    public void setSquadRole(String squadRole) {
        this.squadRole = squadRole;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", number=" + number +
                ", squadRole='" + squadRole + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}

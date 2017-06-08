package com.example.pawel.championsscore.model.webservice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Mateusz on 06.05.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Round {
    @JsonProperty("dbid")
    private Long id;
    private String name;
    private Competition competition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "Round{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", competition=" + competition +
                '}';
    }
}

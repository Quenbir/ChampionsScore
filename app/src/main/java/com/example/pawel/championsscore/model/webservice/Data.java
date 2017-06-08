package com.example.pawel.championsscore.model.webservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 08.06.2017.
 */

public class Data {
    private List<Match> matches = new ArrayList<>();
    private List<Round> rounds = new ArrayList<>();
    private List<MatchEvents> events = new ArrayList<>();

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public List<MatchEvents> getEvents() {
        return events;
    }

    public void setEvents(List<MatchEvents> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Data{" +
                "matches=" + matches +
                ", rounds=" + rounds +
                ", events=" + events +
                '}';
    }
}

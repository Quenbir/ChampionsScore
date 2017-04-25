package com.example.pawel.championsscore.service;

import com.example.pawel.championsscore.model.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 25.04.2017.
 */

public class MatchService {

    public List<Match> getAllMatches() {
        Match match = new Match();
        match.setHomeTeam("Barcelona");
        match.setAwayTeam("Real");
        match.setHomeGoal(5);
        match.setAwayGoal(0);
        match.setDate(new Date());
        match.setStage_id(1);

        Match match2 = new Match();
        match2.setHomeTeam("Bayern");
        match2.setAwayTeam("Arsenal");
        match2.setHomeGoal(5);
        match2.setAwayGoal(0);
        match2.setDate(new Date());
        match2.setStage_id(1);

        return new ArrayList<Match>(Arrays.asList(match, match2));
    }


    public List<Match> getMatches(int stageId) {
        List<Match> matches = new ArrayList<Match>();
        for (Match match : getAllMatches()) {
            if (match.getStage_id() == stageId)
                matches.add(match);
        }
        return matches;
    }
}

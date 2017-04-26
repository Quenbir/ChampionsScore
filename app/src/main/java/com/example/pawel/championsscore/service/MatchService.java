package com.example.pawel.championsscore.service;

import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.model.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MatchService {


    public List<Match> getAllMatches() {

        Team barcelona = new Team();
        barcelona.setId(1);
        barcelona.setName("Barcelona");
        Team real = new Team();
        real.setId(1);
        real.setName("Real");
        barcelona.setName("Barcelona");
        Match match = new Match();
        match.setHomeTeam(barcelona);
        match.setAwayTeam(real);
        match.setHomeGoal(5);
        match.setAwayGoal(0);
        match.setDate(new Date());
        match.setStage_id(1);


        Team bayern = new Team();
        bayern.setId(1);
        bayern.setName("Bayern");
        Team arsenal = new Team();
        arsenal.setId(1);
        arsenal.setName("Arsenal");
        Match match2 = new Match();
        match2.setHomeTeam(bayern);
        match2.setAwayTeam(arsenal);
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

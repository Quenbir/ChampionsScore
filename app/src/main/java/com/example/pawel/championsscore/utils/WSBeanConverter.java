package com.example.pawel.championsscore.utils;

import com.example.pawel.championsscore.model.db.Competition;
import com.example.pawel.championsscore.model.db.Round;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 22.05.2017.
 */

public class WSBeanConverter {
    public static List<com.example.pawel.championsscore.model.webservice.Round> convertRoundsToWS(List<Round> roundsDB) {
        List<com.example.pawel.championsscore.model.webservice.Round> roundsWS = new ArrayList<>();
        for (Round round : roundsDB) {
            roundsWS.add(convertRoundToWS(round));
        }
        return roundsWS;
    }

    public static com.example.pawel.championsscore.model.webservice.Round convertRoundToWS(Round round) {
        com.example.pawel.championsscore.model.webservice.Round r = new com.example.pawel.championsscore.model.webservice.Round();
        r.setId(round.getId());
        r.setName(round.getName());
        r.setCompetition(convertCompetitionToWS(round.getCompetition()));
        return r;

    }

    public static com.example.pawel.championsscore.model.webservice.Competition convertCompetitionToWS(Competition competition) {
        if (competition == null)
            return null;
        com.example.pawel.championsscore.model.webservice.Competition c = new com.example.pawel.championsscore.model.webservice.Competition();
        c.setName(competition.getName());
        c.setFlagUrl(competition.getFlagUrl());
        c.setId(competition.getId());
        return c;
    }
}

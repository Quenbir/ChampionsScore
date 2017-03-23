package com.example.pawel.championsscore;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.pawel.championsscore.adapter.MatchAdapter;
import com.example.pawel.championsscore.adapter.StageAdapter;
import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.model.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 23.03.2017.
 */

public class MatchesActivity  extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        Match match = new Match();
        match.setHomeTeam("Barcelona");
        match.setAwayTeam("Real");
        match.setHomeGoal(5);
        match.setAwayGoal(0);
        match.setDate(new Date());

        Match match2 = new Match();
        match2.setHomeTeam("Bayern");
        match2.setAwayTeam("Arsenal");
        match2.setHomeGoal(5);
        match2.setAwayGoal(0);
        match2.setDate(new Date());

        List<Match> matches = new ArrayList<>(Arrays.asList(match, match2));

        MatchAdapter adapter = new MatchAdapter(this, R.layout.activity_match, matches);
        setListAdapter(adapter);

    }
}
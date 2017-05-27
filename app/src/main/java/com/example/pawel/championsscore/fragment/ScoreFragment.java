package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.service.MatchService;

public class ScoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_score, container, false);
        Match match = new MatchService().getAllMatches().iterator().next();

        TextView textHomeTeam = (TextView) view.findViewById(R.id.textHomeTeam);
        textHomeTeam.setText(match.getHomeTeam().getName());
        TextView textAwayTeam = (TextView) view.findViewById(R.id.textAwayTeam);
        textAwayTeam.setText(match.getAwayTeam().getName());
        TextView textHomeScore = (TextView) view.findViewById(R.id.textHomeScore);
        textHomeScore.setText(String.valueOf(match.getHomeGoal()));
        TextView textAwayScore = (TextView) view.findViewById(R.id.textAwayScore);
        textAwayScore.setText(String.valueOf(match.getAwayGoal()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}

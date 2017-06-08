package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.MatchInfoAdapter;
import com.example.pawel.championsscore.model.webservice.Event;

import java.util.List;

public class ScoreFragment extends Fragment {

    private ListView ls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.view_score, container, false);
        /*Match match = new MatchService().getAllMatches().iterator().next();

        TextView textHomeTeam = (TextView) view.findViewById(R.id.textHomeTeam);
        textHomeTeam.setText(match.getHomeTeam().getName());
        TextView textAwayTeam = (TextView) view.findViewById(R.id.textAwayTeam);
        textAwayTeam.setText(match.getAwayTeam().getName());
        TextView textHomeScore = (TextView) view.findViewById(R.id.textHomeScore);
        textHomeScore.setText(String.valueOf(match.getHomeGoals()));
        TextView textAwayScore = (TextView) view.findViewById(R.id.textAwayScore);
        textAwayScore.setText(String.valueOf(match.getAwayGoals()));*/
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateView(String homeTeam, String awayTeam, int homeScore, int awayScore) {

        View view = getView();
        TextView textHomeTeam = (TextView) view.findViewById(R.id.textHomeTeam);
        textHomeTeam.setText(homeTeam);
        TextView textAwayTeam = (TextView) view.findViewById(R.id.textAwayTeam);
        textAwayTeam.setText(awayTeam);
        TextView textHomeScore = (TextView) view.findViewById(R.id.textHomeScore);
        textHomeScore.setText(String.valueOf(homeScore));
        TextView textAwayScore = (TextView) view.findViewById(R.id.textAwayScore);
        textAwayScore.setText(String.valueOf(awayScore));

    }
}

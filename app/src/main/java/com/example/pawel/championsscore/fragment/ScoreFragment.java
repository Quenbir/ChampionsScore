package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawel.championsscore.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ScoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_score, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateView(String homeTeam, String awayTeam, int homeScore, int awayScore, Long date) {

        View view = getView();
        if (view != null) {
            TextView textHomeTeam = (TextView) view.findViewById(R.id.textHomeTeam);
            textHomeTeam.setText(homeTeam);
            TextView textAwayTeam = (TextView) view.findViewById(R.id.textAwayTeam);
            textAwayTeam.setText(awayTeam);
            TextView textHomeScore = (TextView) view.findViewById(R.id.textHomeScore);
            textHomeScore.setText(String.valueOf(homeScore));
            TextView textAwayScore = (TextView) view.findViewById(R.id.textAwayScore);
            textAwayScore.setText(String.valueOf(awayScore));
            TextView textDate = (TextView) view.findViewById(R.id.textDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            textDate.setText(sdf.format(new Date(date)));
        }

    }
}

package com.example.pawel.championsscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.model.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 23.03.2017.
 */

public class MatchAdapter extends ArrayAdapter<Match> {
    private Context context;
    @SuppressWarnings("unused")
    private List<Match> matches;

    private List<Button> buttons = new ArrayList<>();

    public MatchAdapter(Context context, int resource, List <Match> objects) {
        super(context, resource, objects);
        this.context = context;
        this.matches = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_match, parent, false);

        final Match match = matches.get(position);

        TextView textView = (TextView) view.findViewById(R.id.textHomeTeam);
        textView.setText(match.getHomeTeam());

        TextView textView2 = (TextView) view.findViewById(R.id.textHomeScore);
        textView2.setText(String.valueOf(match.getHomeGoal()));

        TextView textView3 = (TextView) view.findViewById(R.id.textAwayScore);
        textView3.setText(String.valueOf(match.getAwayGoal()));

        TextView textView4 = (TextView) view.findViewById(R.id.textAwayTeam);
        textView4.setText(match.getAwayTeam());


        TextView textView5 = (TextView) view.findViewById(R.id.textTime);
        textView5.setText(android.text.format.DateFormat.format("HH:mm", match.getDate()));
        return view;
    }
}
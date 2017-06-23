package com.example.pawel.championsscore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.webservice.Match;

import java.util.Date;
import java.util.List;


public class MatchAdapter extends ArrayAdapter<Match> {
    private Context context;
    private List<Match> matches;
    private int resource;

    public MatchAdapter(Context context, int resource, List<Match> objects) {
        super(context, resource, objects);
        this.context = context;
        this.matches = objects;
        this.resource = resource;
    }

    @Override
    public
    @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {


        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);


        final Match match = matches.get(position);

        TextView textView = (TextView) convertView.findViewById(R.id.textHomeTeam);
        textView.setText(match.getHomeTeam().getName());

        TextView textView2 = (TextView) convertView.findViewById(R.id.textHomeScore);
        textView2.setText(String.valueOf(match.getHomeGoals()));

        TextView textView3 = (TextView) convertView.findViewById(R.id.textAwayScore);
        textView3.setText(String.valueOf(match.getAwayGoals()));

        TextView textView4 = (TextView) convertView.findViewById(R.id.textAwayTeam);
        textView4.setText(match.getAwayTeam().getName());

        TextView textView5 = (TextView) convertView.findViewById(R.id.textTime);
        Long matchDate = match.getDate();
        if (new Date().getTime() < matchDate) {
            String date = android.text.format.DateFormat.format("HH:mm", match.getDate()).toString().replace(":", "\n");
            textView5.setText(date);
        }
        return convertView;
    }
}

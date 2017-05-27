package com.example.pawel.championsscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.model.MatchEvent;

import java.util.List;

public class MatchInfoAdapter extends ArrayAdapter<MatchEvent> {


    private Context context;
    @SuppressWarnings("unused")
    private List<MatchEvent> events;
    private Match match;

    public MatchInfoAdapter(Context context, List<MatchEvent> objects, Match match) {
        super(context, 0, objects);
        this.context = context;
        this.events = objects;
        this.match = match;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_event, parent, false);

        final MatchEvent matchInfo = events.get(position);

        TextView textTime = (TextView) view.findViewById(R.id.textTime);
        TextView textHomeEvent = (TextView) view.findViewById(R.id.textHomeEvent);
        TextView textAwayEvent = (TextView) view.findViewById(R.id.textAwayEvent);
        ImageView homeImage = (ImageView) view.findViewById(R.id.homeImage);
        ImageView awayImage = (ImageView) view.findViewById(R.id.awayImage);

        textTime.setText(String.valueOf(matchInfo.getTime()));
        if (matchInfo.getIdTeam() == match.getHomeTeam().getId()) {
            textHomeEvent.setText(matchInfo.getInfo());
            switch (matchInfo.getType()) {
                case "goal":
                    homeImage.setImageResource(R.drawable.ball);
                    break;
                case "substitute":
                    homeImage.setImageResource(R.drawable.substitute);
                    break;
                case "red":
                    homeImage.setImageResource(R.drawable.red);
                    break;
                case "yellow":
                    homeImage.setImageResource(R.drawable.yellow);
                    break;
            }

        } else {
            textAwayEvent.setText(matchInfo.getInfo());
            switch (matchInfo.getType()) {
                case "goal":
                    awayImage.setImageResource(R.drawable.ball);
                    break;
                case "substitute":
                    awayImage.setImageResource(R.drawable.substitute);
                    break;
                case "red":
                    awayImage.setImageResource(R.drawable.red);
                    break;
                case "yellow":
                    awayImage.setImageResource(R.drawable.yellow);
                    break;
            }
        }
        return view;

    }

}

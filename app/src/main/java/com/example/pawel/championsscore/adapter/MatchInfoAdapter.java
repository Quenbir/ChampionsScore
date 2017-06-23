package com.example.pawel.championsscore.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.enums.CardType;
import com.example.pawel.championsscore.model.enums.EventType;
import com.example.pawel.championsscore.model.enums.Side;
import com.example.pawel.championsscore.model.webservice.Event;

import java.util.List;

public class MatchInfoAdapter extends ArrayAdapter<Event> {


    private Context context;
    private List<Event> events;
    private int resource;

    public MatchInfoAdapter(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
        this.context = context;
        this.events = objects;
        this.resource = resource;
    }

    @Override
    public
    @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);

        final Event event = events.get(position);

        TextView textTime = (TextView) view.findViewById(R.id.textTime);
        TextView textHomeEvent = (TextView) view.findViewById(R.id.textHomeEvent);
        TextView textAwayEvent = (TextView) view.findViewById(R.id.textAwayEvent);
        ImageView homeImage = (ImageView) view.findViewById(R.id.homeImage);
        ImageView awayImage = (ImageView) view.findViewById(R.id.awayImage);

        textTime.setText(String.valueOf(event.getTime()));
        if (event.getType().equals(EventType.GOAL.getType())) {
            if (event.getScoringSide().equals(Side.HOME.getSide())) {
                textHomeEvent.setText(event.getScoringPlayer().getName());
                homeImage.setImageResource(R.drawable.ball);
            } else if (event.getScoringSide().equals(Side.AWAY.getSide())) {
                textAwayEvent.setText(event.getScoringPlayer().getName());
                awayImage.setImageResource(R.drawable.ball);
            }
        } else if (event.getType().equals(EventType.SUBSTITUTION.getType())) {
            if (event.getSide().equals(Side.HOME.getSide())) {
                textHomeEvent.setText(event.getPlayerOn().getNumber() + "." + event.getPlayerOn().getShortName() + "(" + event.getPlayerOff().getShortName() + ")");
                homeImage.setImageResource(R.drawable.substitution);
            } else if (event.getSide().equals(Side.AWAY.getSide())) {
                textAwayEvent.setText(event.getPlayerOn().getNumber() + "." + event.getPlayerOn().getShortName() + "(" + event.getPlayerOff().getShortName() + ")");
                awayImage.setImageResource(R.drawable.substitution);

            }
        } else if (event.getType().equals(EventType.CARD.getType())) {
            if (event.getSide().equals(Side.HOME.getSide())) {
                textHomeEvent.setText(event.getPlayer().getNumber() + "." + event.getPlayer().getShortName());
                switch (event.getCardType()) {
                    case "first-yellow":
                        homeImage.setImageResource(CardType.FIRST_YELLOW.getPicture());
                        break;
                    case "second-yellow":
                        homeImage.setImageResource(CardType.SECOND_YELLOW.getPicture());
                        break;
                    case "red":
                        homeImage.setImageResource(CardType.RED.getPicture());
                        break;
                }
            } else if (event.getSide().equals(Side.AWAY.getSide())) {
                textAwayEvent.setText(event.getPlayer().getNumber() + "." + event.getPlayer().getShortName());
                switch (event.getCardType()) {
                    case "first-yellow":
                        awayImage.setImageResource(CardType.FIRST_YELLOW.getPicture());
                        break;
                    case "second-yellow":
                        awayImage.setImageResource(CardType.SECOND_YELLOW.getPicture());
                        break;
                    case "red":
                        awayImage.setImageResource(CardType.RED.getPicture());
                        break;
                }
            }
        }
        return view;

    }

}

package com.example.pawel.championsscore.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.Player;
import com.example.pawel.championsscore.model.PlayerInterface;
import com.example.pawel.championsscore.model.SectionItem;

import java.util.ArrayList;

public class LineupsAdapter extends ArrayAdapter<PlayerInterface> {

    private Context context;
    private ArrayList<PlayerInterface> homeLineup;
    private ArrayList<PlayerInterface> awayLineup;

    public LineupsAdapter(Context context, ArrayList<PlayerInterface> homeLineup, ArrayList<PlayerInterface> awayLineup) {


        super(context, 0, homeLineup);
        this.context = context;
        this.homeLineup = homeLineup;
        this.awayLineup = awayLineup;


        if (homeLineup.size() > awayLineup.size()) {
            for (int i = 0; i < homeLineup.size() - awayLineup.size(); i++) {
                awayLineup.add(null);
            }
        } else if (homeLineup.size() < awayLineup.size()) {
            for (int i = 0; i < awayLineup.size() - homeLineup.size(); i++) {
                homeLineup.add(null);
            }
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (homeLineup.size() > awayLineup.size()) {
            for (int i = 0; i < homeLineup.size() - awayLineup.size(); i++) {
                awayLineup.add(null);
            }
        } else if (homeLineup.size() < awayLineup.size()) {
            for (int i = 0; i < awayLineup.size() - homeLineup.size(); i++) {
                homeLineup.add(null);
            }
        }

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view = convertView;
        final PlayerInterface home = homeLineup.get(position);
        final PlayerInterface away = awayLineup.get(position);
        if (home != null && home.isSection()) {
            SectionItem si = (SectionItem) home;

            view.setOnClickListener(null);
            view.setOnLongClickListener(null);
            view.setLongClickable(false);

            TextView sectionView = (TextView) view.findViewById(R.id.textCenter);
            sectionView.setText(si.getTitle());

        } else {
            Player homePlayer = (Player) home;
            view = inflater.inflate(R.layout.activity_player, null);
            final TextView title = (TextView) view.findViewById(R.id.homePlayer);

            if (title != null && homePlayer != null)
                title.setText(String.valueOf(homePlayer.getNumber()) + "." + homePlayer.getLastName());

            Player awayPlayer = (Player) away;

            final TextView title2 = (TextView) view.findViewById(R.id.awayPlayer);

            if (title2 != null)
                title2.setText(String.valueOf(awayPlayer.getNumber()) + "." + awayPlayer.getLastName());

        }


        return view;

    }
}


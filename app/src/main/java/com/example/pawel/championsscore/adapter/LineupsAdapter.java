package com.example.pawel.championsscore.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.model.SectionItem;
import com.example.pawel.championsscore.model.webservice.Player;
import com.example.pawel.championsscore.model.PlayerInterface;

import java.util.List;

public class LineupsAdapter extends ArrayAdapter<PlayerInterface> {

    private Context context;
    private List<PlayerInterface> homeLineup;
    private List<PlayerInterface> awayLineup;

    public LineupsAdapter(Context context, List<PlayerInterface> homeLineup, List<PlayerInterface> awayLineup) {


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
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View view;
        final PlayerInterface home = homeLineup.get(position);
        final PlayerInterface away = awayLineup.get(position);

        if (home != null && home.isSection()) {
            SectionItem si = (SectionItem) home;
            view = inflater.inflate(R.layout.header_bench, null);
            TextView sectionView = (TextView) view.findViewById(R.id.textCenter);
            sectionView.setText(si.getTitle());

        } else {
            view = inflater.inflate(R.layout.view_player, null);

            if (home instanceof Player) {
                Player homePlayer = (Player) home;
                final TextView title = (TextView) view.findViewById(R.id.homePlayer);

                if (title != null)
                    title.setText(homePlayer.getNumber() != null ? String.valueOf(homePlayer.getNumber()) + "." + homePlayer.getShortName() : homePlayer.getShortName());
            }
            if (away instanceof Player) {
                Player awayPlayer = (Player) away;

                final TextView title2 = (TextView) view.findViewById(R.id.awayPlayer);

                if (title2 != null)
                    title2.setText(awayPlayer.getNumber() != null ? String.valueOf(awayPlayer.getNumber()) + "." + awayPlayer.getShortName() : awayPlayer.getShortName());
            }
        }
        return view;


    }
}


package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.LineupsAdapter;
import com.example.pawel.championsscore.model.PlayerInterface;

import java.util.List;

public class LineupsTab extends Fragment {

    private ListView ls;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_lineups, container, false);
        return view;
    }

    public void updateView(List<PlayerInterface> homeLineups, List<PlayerInterface> awayLineups, String homeTeamName, String awayTeamName) {
        View view = getView();
        ls = (ListView) view.findViewById(R.id.lineupsList);

        View header = getActivity().getLayoutInflater().inflate(R.layout.header_teams, null);
        TextView homeTeam = (TextView) header.findViewById(R.id.textHomeTeam);
        homeTeam.setText(homeTeamName);

        TextView awayTeam = (TextView) header.findViewById(R.id.textAwayTeam);
        awayTeam.setText(awayTeamName);

        if (ls.getHeaderViewsCount() < 1)
            ls.addHeaderView(header);
        ls.setAdapter(new LineupsAdapter(getContext(), homeLineups, awayLineups));
        ls.setClickable(false);
    }
}
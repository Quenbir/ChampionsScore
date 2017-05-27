package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.LineupsAdapter;
import com.example.pawel.championsscore.model.Player;
import com.example.pawel.championsscore.model.PlayerInterface;

import java.util.ArrayList;

public class LineupsTab extends Fragment {

    private ListView ls;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_lineups, container, false);

        ArrayList<PlayerInterface> homeLineup = new ArrayList<>();
        Player player1 = new Player();
        player1.setFirstName("Tomasz");
        player1.setLastName("Hajto");
        player1.setId(1);
        player1.setIdTeam(1);
        player1.setNumber(5);
        player1.setPosition("Obrońca");
        homeLineup.add(player1);

        Player player2 = new Player();
        player2.setFirstName("Tomasz");
        player2.setLastName("Hajto");
        player2.setId(1);
        player2.setIdTeam(1);
        player2.setNumber(5);
        player2.setPosition("Obrońca");
        ArrayList<PlayerInterface> awayLineup = new ArrayList<>();
        awayLineup.add(player2);

        LineupsAdapter adapter = new LineupsAdapter(getContext(), homeLineup, awayLineup);

        ls = (ListView) view.findViewById(R.id.lineupsList);
        ls.setAdapter(adapter);

        return view;
    }

}
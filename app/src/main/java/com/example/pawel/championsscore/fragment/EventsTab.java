package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.MatchInfoAdapter;
import com.example.pawel.championsscore.model.Match;
import com.example.pawel.championsscore.model.MatchEvent;
import com.example.pawel.championsscore.service.MatchService;

import java.util.ArrayList;
import java.util.List;

public class EventsTab extends Fragment {
    private ListView ls;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Match match = new MatchService().getMatches(1).iterator().next();
        List<MatchEvent> events = new ArrayList<>();
        MatchEvent event = new MatchEvent("T. Hajto", 1, 1, "goal");
        MatchEvent event2 = new MatchEvent("T. Hajto", 1, 2, "red");
        events.add(event);
        events.add(event2);
        View view = inflater.inflate(R.layout.tab_events, container, false);
        ls = (ListView) view.findViewById(R.id.eventsList);
        ls.setAdapter(new MatchInfoAdapter(getContext(), events, match));
        return view;
    }


}

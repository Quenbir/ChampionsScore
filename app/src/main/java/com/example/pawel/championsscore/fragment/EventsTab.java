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
import com.example.pawel.championsscore.model.webservice.Event;

import java.util.List;

public class EventsTab extends Fragment {
    private ListView ls;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.tab_events, container, false);
        return view;
    }

    public void updateView(List<Event> events) {
        View view = getView();
        ls = (ListView) view.findViewById(R.id.eventsList);
        ls.setAdapter(new MatchInfoAdapter(getContext(), events));
    }


}

package com.example.pawel.championsscore.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.ViewPagerAdapter;
import com.example.pawel.championsscore.layout.SlidingTabLayout;
import com.example.pawel.championsscore.model.PlayerInterface;
import com.example.pawel.championsscore.model.webservice.Event;

import java.util.List;

public class InfoFragment extends Fragment {

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private final CharSequence Titles[] = {"Przebieg", "Składy"};
    private final int Numboftabs = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.view_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        pager = (ViewPager) getActivity().findViewById(R.id.pager);

        adapter = new ViewPagerAdapter(getChildFragmentManager(), Titles, Numboftabs);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) getActivity().findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });

        tabs.setViewPager(pager);
    }


    public void updateView(List<Event> events, List<PlayerInterface> homeLineups, List<PlayerInterface> awayLineups, String homeTeamName, String awayTeamName) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof EventsTab) {
                EventsTab eventsTab = (EventsTab) fragment;
                eventsTab.updateView(events);
            } else if (fragment instanceof LineupsTab) {
                LineupsTab lineupsTab = (LineupsTab) fragment;
                lineupsTab.updateView(homeLineups, awayLineups, homeTeamName, awayTeamName);
            }
        }
    }

}

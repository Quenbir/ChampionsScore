package com.example.pawel.championsscore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.pawel.championsscore.dao.CompetitionDAO;
import com.example.pawel.championsscore.dao.EventDAO;
import com.example.pawel.championsscore.dao.MatchDAO;
import com.example.pawel.championsscore.dao.PlayerDAO;
import com.example.pawel.championsscore.dao.RoundDao;
import com.example.pawel.championsscore.dao.TeamDAO;
import com.example.pawel.championsscore.fragment.MatchFragment;
import com.example.pawel.championsscore.fragment.MatchesFragment;
import com.example.pawel.championsscore.fragment.StageFragment;

public class MainActivity extends FragmentActivity
        implements StageFragment.OnStageSelectedListener, MatchesFragment.OnMatchSelectedListener {
    public static String API_KEY = "6ee2bfc2c80641ba9eb9c2105a15f4ec";
    public static boolean isConnected;
    private CompetitionDAO competitionDAO;
    private RoundDao roundDao;
    private TeamDAO teamDAO;
    private MatchDAO matchDAO;
    private EventDAO eventDAO;
    private PlayerDAO playerDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        competitionDAO = new CompetitionDAO(this);
        roundDao = new RoundDao(this, competitionDAO);
        teamDAO = new TeamDAO(this);
        matchDAO = new MatchDAO(this, roundDao, teamDAO);
        playerDAO = new PlayerDAO(this);
        eventDAO = new EventDAO(this, playerDAO, teamDAO);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {
            isConnected = true;
        }

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            StageFragment firstFragment = new StageFragment(roundDao, competitionDAO);
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onStageSelected(int position) {
        MatchesFragment matchesFragment = (MatchesFragment)
                getSupportFragmentManager().findFragmentById(R.id.matches_fragment);

        if (matchesFragment != null) {
            matchesFragment.updateMachtesView(position, null);
        } else {
            MatchesFragment newFragment = new MatchesFragment(matchDAO, teamDAO);
            Bundle args = new Bundle();
            args.putInt(MatchesFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void onMatchSelected(int position) {
        MatchFragment matchFragment = (MatchFragment) getSupportFragmentManager().findFragmentById(R.id.match_fragment);

        if (matchFragment != null) {
            matchFragment.updateView();
        } else {
            MatchFragment newFragment = new MatchFragment(playerDAO, eventDAO, teamDAO);
            Bundle args = new Bundle();
            args.putInt(MatchFragment.ARG_MATCH, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

}

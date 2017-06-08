package com.example.pawel.championsscore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import com.example.pawel.championsscore.model.webservice.Data;
import com.example.pawel.championsscore.model.webservice.Match;
import com.example.pawel.championsscore.model.webservice.MatchEvents;
import com.example.pawel.championsscore.model.webservice.Round;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements StageFragment.OnStageSelectedListener, MatchesFragment.OnMatchSelectedListener {
    private static final int COMPETITION_ID = 36;


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

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous STATE,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            StageFragment firstFragment = new StageFragment(roundDao, competitionDAO);

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onStageSelected(int position) {
        // The user selected the headline of an article from the StageFragment

        // Capture the article fragment from the activity layout
        MatchesFragment matchesFragment = (MatchesFragment)
                getSupportFragmentManager().findFragmentById(R.id.matches_fragment);

        if (matchesFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            matchesFragment.updateMachtesView(position, null);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            MatchesFragment newFragment = new MatchesFragment(matchDAO, teamDAO);
            Bundle args = new Bundle();
            args.putInt(MatchesFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    public void onMatchSelected(int position) {
        MatchFragment newFragment = new MatchFragment(playerDAO, eventDAO, teamDAO);
        Bundle args = new Bundle();
        args.putInt(MatchFragment.ARG_MATCH, position);
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

}

package com.example.pawel.championsscore.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawel.championsscore.MainActivity;
import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.dao.EventDAO;
import com.example.pawel.championsscore.dao.PlayerDAO;
import com.example.pawel.championsscore.dao.TeamDAO;
import com.example.pawel.championsscore.model.PlayerInterface;
import com.example.pawel.championsscore.model.SectionItem;
import com.example.pawel.championsscore.model.enums.EventType;
import com.example.pawel.championsscore.model.webservice.Event;
import com.example.pawel.championsscore.model.webservice.MatchEvents;
import com.example.pawel.championsscore.model.webservice.Player;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchFragment extends Fragment {

    public final static String ARG_MATCH = "match";
    private int matchId = -1;
    private String URL = MainActivity.URL + "matches/";
    private List<Event> events = new ArrayList<>();
    private List<PlayerInterface> homePlayers;
    private List<PlayerInterface> awayPlayers;
    private String homeTeamName;
    private String awayTeamName;
    private Integer homeScore;
    private Integer awayScore;
    private boolean viewed;
    private PlayerDAO playerDAO;
    private EventDAO eventDAO;
    private TeamDAO teamDAO;
    private Long date;
    private ProgressDialog progress;

    public MatchFragment() {
    }

    public MatchFragment(PlayerDAO playerDAO, EventDAO eventDAO, TeamDAO teamDAO) {
        this.playerDAO = playerDAO;
        this.eventDAO = eventDAO;
        this.teamDAO = teamDAO;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        if (toolbar != null)
            toolbar.setTitle(getString(R.string.app_name) + " - " + getString(R.string.match));
        
        progress = ProgressDialog.show(getContext(), getString(R.string.loading),
                getString(R.string.loading_text), true);
        if (savedInstanceState != null) {
            matchId = savedInstanceState.getInt(ARG_MATCH);
        }
        Bundle args = getArguments();
        if (args != null) {
            matchId = args.getInt(ARG_MATCH);
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL + args.getInt(ARG_MATCH));
        } else if (matchId != -1) {
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL + matchId);
        }
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        progress.show();
        if (viewed)
            updateView();
    }

    public void updateView() {
        ((InfoFragment) getChildFragmentManager().findFragmentById(R.id.info_fragment)).updateView(events, homePlayers, awayPlayers, homeTeamName, awayTeamName);
        ((ScoreFragment) getChildFragmentManager().findFragmentById(R.id.score_fragment)).updateView(homeTeamName, awayTeamName, homeScore, awayScore, date);
        progress.dismiss();
    }

    private class HttpRequestTask extends AsyncTask<String, Void, MatchEvents> {
        @Override
        protected MatchEvents doInBackground(String... params) {
            if (isConnected()) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.set("x-crowdscores-api-key", MainActivity.API_KEY);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<MatchEvents> respEntity = restTemplate.exchange(params[0], HttpMethod.GET, entity, MatchEvents.class);
                return respEntity.getBody();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MatchEvents matchEvents) {
            if (matchEvents != null) {
                teamDAO.save(matchEvents.getHomeTeam());
                teamDAO.save(matchEvents.getAwayTeam());
                playerDAO.save(matchEvents.getHomePlayers());
                playerDAO.save(matchEvents.getAwayPlayers());
                eventDAO.saveMatchEvents(matchEvents);

            } else matchEvents = eventDAO.findMatchEvents(matchId);
            if (matchEvents != null) {
                homeTeamName = matchEvents.getHomeTeam().getName();
                awayTeamName = matchEvents.getAwayTeam().getName();
                homeScore = matchEvents.getHomeGoals();
                awayScore = matchEvents.getAwayGoals();
                date = matchEvents.getStart();
                Set<String> eventTypes = new HashSet<>(Arrays.asList(EventType.GOAL.getType(), EventType.CARD.getType(), EventType.SUBSTITUTION.getType()));
                for (Event event : matchEvents.getEvents()) {
                    if (eventTypes.contains(event.getType().toLowerCase()))
                        events.add(event);
                }
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(final Event object1, final Event object2) {
                        return object1.getHappenedAt().compareTo(object2.getHappenedAt());
                    }
                });

                homePlayers = filterPlayers(matchEvents.getHomePlayers());
                awayPlayers = filterPlayers(matchEvents.getAwayPlayers());
                updateView();

                viewed = true;
            } else {
                progress.dismiss();
                showAlertDialog(getActivity());
            }
        }
    }


    private List<PlayerInterface> filterPlayers(List<Player> players) {
        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player o1, Player o2) {
                return Integer.compare(o1.getNumber() != null ? o1.getNumber() : 0, o2.getNumber() != null ? o2.getNumber() : 0);
            }
        });
        List<PlayerInterface> filteredPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getSquadRole().equals("starting"))
                filteredPlayers.add(player);
        }
        filteredPlayers.add(new SectionItem(getString(R.string.bench)));
        for (Player player : players) {
            if (player.getSquadRole().equals("sub"))
                filteredPlayers.add(player);
        }
        return filteredPlayers;
    }


    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getApplication().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    public void showAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(getString(R.string.try_again), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progress.show();
                Bundle args = getArguments();
                if (args != null) {
                    matchId = args.getInt(ARG_MATCH);
                    HttpRequestTask httpRequestTask = new HttpRequestTask();
                    httpRequestTask.execute(URL + args.getInt(ARG_MATCH));
                } else if (matchId != -1) {
                    HttpRequestTask httpRequestTask = new HttpRequestTask();
                    httpRequestTask.execute(URL + matchId);
                }
            }
        });
        builder.setMessage(getString(R.string.no_internet_connection));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

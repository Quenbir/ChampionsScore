package com.example.pawel.championsscore.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private String URL = "https://api.crowdscores.com/v1/matches/";
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
        if (savedInstanceState != null) {
            matchId = savedInstanceState.getInt(ARG_MATCH);
        }
        //View view = inflater.inflate(R.layout.activity_matches, container, false);
        //ls = (ListView) view.findViewById(android.R.id.list);

        Bundle args = getArguments();
        if (args != null) {
            matchId = args.getInt(ARG_MATCH);
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL + args.getInt(ARG_MATCH));
        } else if (matchId != -1) {
            HttpRequestTask httpRequestTask = new HttpRequestTask();
            httpRequestTask.execute(URL + matchId);
        }
        return inflater.inflate(R.layout.activity_matchview, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewed) {
            ((InfoFragment) getChildFragmentManager().findFragmentById(R.id.info_fragment)).updateView(events, homePlayers, awayPlayers, homeTeamName, awayTeamName);
            ((ScoreFragment) getChildFragmentManager().findFragmentById(R.id.score_fragment)).updateView(homeTeamName, awayTeamName, homeScore, awayScore);
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, MatchEvents> {
        @Override
        protected MatchEvents doInBackground(String... params) {
            if (MainActivity.isConnected) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                HttpHeaders headers = new HttpHeaders();
                headers.set("x-crowdscores-api-key", MainActivity.API_KEY);
                HttpEntity<?> entity = new HttpEntity<Object>(headers);
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

            } else {
                matchEvents = eventDAO.findMatchEvents(matchId);
            }
            homeTeamName = matchEvents.getHomeTeam().getName();
            awayTeamName = matchEvents.getAwayTeam().getName();
            homeScore = matchEvents.getHomeGoals();
            awayScore = matchEvents.getAwayGoals();
            final Set<String> eventTypes = new HashSet<>(Arrays.asList("GOAL", "CARD", "substitution"));
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
            ((InfoFragment) getChildFragmentManager().findFragmentById(R.id.info_fragment)).updateView(events, homePlayers, awayPlayers, homeTeamName, awayTeamName);
            ((ScoreFragment) getChildFragmentManager().findFragmentById(R.id.score_fragment)).updateView(homeTeamName, awayTeamName, homeScore, awayScore);

            viewed = true;
        }
    }

    private List<PlayerInterface> filterPlayers(List<Player> players) {
        List<PlayerInterface> filteredPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getSquadRole().equals("starting"))
                filteredPlayers.add(player);
        }
        filteredPlayers.add(new SectionItem("Rezerwowi"));
        for (Player player : players) {
            if (player.getSquadRole().equals("sub"))
                filteredPlayers.add(player);
        }
        return filteredPlayers;
    }
}

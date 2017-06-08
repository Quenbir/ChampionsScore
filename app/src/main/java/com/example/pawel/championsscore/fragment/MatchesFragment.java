package com.example.pawel.championsscore.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pawel.championsscore.MainActivity;
import com.example.pawel.championsscore.R;
import com.example.pawel.championsscore.adapter.MatchAdapter;
import com.example.pawel.championsscore.dao.MatchDAO;
import com.example.pawel.championsscore.dao.TeamDAO;
import com.example.pawel.championsscore.model.webservice.Match;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class MatchesFragment extends Fragment {
    public final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;
    private boolean isConnected = false;
    private OnMatchSelectedListener mCallback;
    private ListView ls = null;
    private String URL = "https://api.crowdscores.com/v1/matches?round_ids=";
    private MatchDAO matchDAO;
    private TeamDAO teamDAO;
    private List<Match> matches;

    public MatchesFragment(MatchDAO matchDAO, TeamDAO teamDAO) {
        this.matchDAO = matchDAO;
        this.teamDAO = teamDAO;
    }

    public MatchesFragment() {
    }

    public interface OnMatchSelectedListener {
        public void onMatchSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.activity_matches, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);

        Bundle args = getArguments();
        if (args != null) {
            if (MainActivity.isConnected) {
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                httpRequestTask.execute(URL + args.getInt(ARG_POSITION));
            } else
                updateMachtesView(args.getInt(ARG_POSITION), null);
        } else if (mCurrentPosition != -1) {
            if (isConnected) {
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                httpRequestTask.execute(URL + mCurrentPosition);
            } else
                updateMachtesView(mCurrentPosition, null);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnMatchSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMatchSelectedListener");
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, List<Match>> {
        @Override
        protected List<Match> doInBackground(String... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-crowdscores-api-key", MainActivity.API_KEY);
            HttpEntity<?> entity = new HttpEntity<Object>(headers);
            ResponseEntity<Match[]> respEntity = restTemplate.exchange(params[0], HttpMethod.GET, entity, Match[].class);
            return Arrays.asList(respEntity.getBody());
        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            updateMachtesView(null, matches);
            for (Match match : matches) {
                teamDAO.save(match.getAwayTeam());
                teamDAO.save(match.getHomeTeam());
                matchDAO.save(match);
            }
        }
    }

    public void updateMachtesView(Integer position, List<Match> matches) {
        if (matches != null && !matches.isEmpty()) {
            ls.setAdapter(new MatchAdapter(getActivity(), R.layout.activity_match, matches));
        } else if (position != null) {
            ls.setAdapter(new MatchAdapter(getActivity(), R.layout.activity_match, matchDAO.findByRoundId(position)));
            mCurrentPosition = position;
        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int matchId = (int) ((Match) parent.getAdapter().getItem(position)).getId();
                mCallback.onMatchSelected(matchId);
                ls.setItemChecked(position, true);
            }
        });
    }

}
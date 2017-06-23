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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MatchesFragment extends Fragment {
    public final static String ARG_POSITION = "position";
    private int roundId = -1;
    private OnMatchSelectedListener mCallback;
    private ListView ls = null;
    private String URL = MainActivity.URL + "matches?round_ids=";
    private MatchDAO matchDAO;
    private TeamDAO teamDAO;
    private ProgressDialog progress;

    public MatchesFragment(MatchDAO matchDAO, TeamDAO teamDAO) {
        this.matchDAO = matchDAO;
        this.teamDAO = teamDAO;
    }

    public MatchesFragment() {
    }

    public interface OnMatchSelectedListener {
        void onMatchSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        if (toolbar != null)
            toolbar.setTitle(getString(R.string.app_name) + " - " + getString(R.string.matches));

        progress = ProgressDialog.show(getContext(), getString(R.string.loading),
                getString(R.string.loading_text), true);
        if (savedInstanceState != null) {
            roundId = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        ls = (ListView) view.findViewById(android.R.id.list);

        Bundle args = getArguments();
        if (args != null) {
            if (isConnected()) {
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                httpRequestTask.execute(URL + args.getInt(ARG_POSITION));
            } else
                updateMatchesView(args.getInt(ARG_POSITION), null);
        } else if (roundId != -1) {
            if (isConnected()) {
                HttpRequestTask httpRequestTask = new HttpRequestTask();
                httpRequestTask.execute(URL + roundId);
            } else
                updateMatchesView(roundId, null);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnMatchSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<Match[]> respEntity = restTemplate.exchange(params[0], HttpMethod.GET, entity, Match[].class);
            return Arrays.asList(respEntity.getBody());

        }

        @Override
        protected void onPostExecute(List<Match> matches) {
            updateMatchesView(null, matches);
        }
    }

    public void updateMatchesView(Integer roundId, List<Match> matches) {
        if ((matches == null || matches.isEmpty()) && roundId != null) {
            matches = matchDAO.findByRoundId(roundId);
            this.roundId = roundId;
        }

        if (matches != null && !matches.isEmpty()) {
            Collections.sort(matches, new Comparator<Match>() {
                public int compare(Match o1, Match o2) {
                    return Long.compare(o1.getDate(), o2.getDate());
                }

            });
            ls.setAdapter(new MatchAdapter(getActivity(), R.layout.view_match, matches));


            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Match match = ((Match) parent.getAdapter().getItem(position));
                    mCallback.onMatchSelected((int) match.getId());
                    ls.setItemChecked(position, true);
                    if (isConnected()) {
                        teamDAO.save(match.getAwayTeam());
                        teamDAO.save(match.getHomeTeam());
                        matchDAO.save(match);
                    }
                }
            });
        } else if (!isConnected()) showAlertDialog(getActivity());
        progress.dismiss();
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
                    if (isConnected()) {
                        HttpRequestTask httpRequestTask = new HttpRequestTask();
                        httpRequestTask.execute(URL + args.getInt(ARG_POSITION));
                    } else
                        updateMatchesView(args.getInt(ARG_POSITION), null);
                } else if (roundId != -1) {
                    if (isConnected()) {
                        HttpRequestTask httpRequestTask = new HttpRequestTask();
                        httpRequestTask.execute(URL + roundId);
                    } else
                        updateMatchesView(roundId, null);
                }
            }
        });
        builder.setMessage(getString(R.string.no_internet_connection));
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}